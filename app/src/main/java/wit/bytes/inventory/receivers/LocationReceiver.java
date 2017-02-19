package wit.bytes.inventory.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationResult;

import java.util.Iterator;
import java.util.Set;

import wit.bytes.inventory.interfaces.ILocationTracker;
import wit.bytes.inventory.providers.ProviderFactory;
import wit.bytes.inventory.services.SendEmployeeLocationService;

import static android.content.Context.POWER_SERVICE;

/**
 * Created by Md. Sifat-Ul Haque on 2/19/2017.
 */

public class LocationReceiver extends WakefulBroadcastReceiver {

    public static final String KEY_LOCATION_CHANGED = "Location_Changed";
    private ProviderFactory mProviderFactory;
    private ILocationTracker mILocationProvider;

    @Override
    public void onReceive(Context context, Intent intent) {
        //Do this when the system sends the intent
      /*Set<String> set = b.keySet();
        Iterator<String> iterator = set.iterator();
        while(iterator.hasNext()) {
            String setElement = iterator.next();
            Log.d("Location","Key"+setElement);
        }*/


        if (LocationResult.hasResult(intent)) {
            mProviderFactory = ProviderFactory.getProviderInstance();
            mILocationProvider = mProviderFactory.getLocationSendProvider(context, null);
            LocationResult locationResult = LocationResult.extractResult(intent);
            Location location = locationResult.getLastLocation();
            if (location != null) {
                mILocationProvider.sendTrackData(location,this);
            }
        }

/*        if (b.containsKey(FusedLocationProviderApi.KEY_LOCATION_CHANGED)){
            Location loc = (Location)b.get(FusedLocationProviderApi.KEY_LOCATION_CHANGED);
            Toast.makeText(context, "Lat "+loc.getLatitude()+"\nLng "+loc.getLongitude(), Toast.LENGTH_SHORT).show();
        }*/
    }
}
