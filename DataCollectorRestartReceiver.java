package bouzid.spyme.mosaic.bouzid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class DataCollectorRestartReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("DataCollectorReceiver", "Service Stopped, restarting the service");
        context.startService(new Intent(context, DataCollectorService.class));
    }
}
