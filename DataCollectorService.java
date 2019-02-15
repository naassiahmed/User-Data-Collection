package bouzid.spyme.mosaic.bouzid;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.jaredrummler.android.processes.AndroidProcesses;
import com.jaredrummler.android.processes.models.AndroidAppProcess;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DataCollectorService extends Service {

    private Timer timer;
    private TimerTask timerTask;
    private DataCollector dataCollector;
    private LocationListener locationListener = null;

    @SuppressLint("MissingPermission")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        locationListener = new MobilityListener();
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        1000,
                        0,
                        locationListener
                );
                Log.i("MobilityListener", "LocationManager Update launched");


        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Log.i("DataCollectorService", "i'm alive");
                for(String packageName : getCurrentlyRunningApplications()){
                    Log.i("RunningApps", packageName);
                }
            }
        };

        timer.schedule(timerTask, 1000,1000);

        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent broadcastIntent = new Intent("bouzid.spyme.mosaic.bouzid.RestartDataCollector");
        sendBroadcast(broadcastIntent);
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public ArrayList<String> getCurrentlyRunningApplications(){

        ArrayList<String> apps = new ArrayList<>();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) { //For versions less than lollipop
            ActivityManager am = ((ActivityManager) getSystemService(ACTIVITY_SERVICE));
            List<ActivityManager.RunningTaskInfo> taskInfos = am.getRunningTasks(5);

            for(ActivityManager.RunningTaskInfo runningTaskInfo : taskInfos){

                apps.add(runningTaskInfo.baseActivity.getPackageName());

            }

        }else{ //For versions Lollipop and above
            List<AndroidAppProcess> processes = AndroidProcesses.getRunningAppProcesses();
            for( AndroidAppProcess process : processes ){
                apps.add(process.getPackageName());
            }
        }

        return apps;
    }

}
