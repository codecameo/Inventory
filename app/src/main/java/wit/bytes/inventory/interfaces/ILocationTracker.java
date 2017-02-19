package wit.bytes.inventory.interfaces;

import android.location.Location;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by Md. Sifat-Ul Haque on 2/19/2017.
 */
public interface ILocationTracker {
    void sendTrackData(Location location, WakefulBroadcastReceiver broadcastReceiver);
}
