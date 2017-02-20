package wit.bytes.inventory.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import wit.bytes.inventory.obserables.LocationUpdateObservable;

/**
 * Created by Md. Sifat-Ul Haque on 2/20/2017.
 */

public class LocationUpdateReceiver extends BroadcastReceiver {

    public static final String KEY_UPDATE_LOCATION = "update_location";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle.containsKey(KEY_UPDATE_LOCATION)){
            LocationUpdateObservable.getInstance().updateValue(bundle.get(KEY_UPDATE_LOCATION));
        }
    }
}
