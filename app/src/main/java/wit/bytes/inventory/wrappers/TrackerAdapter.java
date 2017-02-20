package wit.bytes.inventory.wrappers;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v4.content.WakefulBroadcastReceiver;

import java.lang.ref.WeakReference;

import wit.bytes.inventory.interfaces.ILocationTracker;
import wit.bytes.inventory.services.SendEmployeeLocationService;

import static android.support.v4.content.WakefulBroadcastReceiver.startWakefulService;
import static wit.bytes.inventory.receivers.LocationReceiver.KEY_LOCATION_CHANGED;

/**
 * Created by Md. Sifat-Ul Haque on 2/19/2017.
 */

public class TrackerAdapter implements ILocationTracker {

    private WeakReference<Context> mContextWeakReference;
    private WeakReference<ResultReceiver> mResultReceiverWeakReference;

    public TrackerAdapter(Context context, ResultReceiver receiver) {
        mContextWeakReference = new WeakReference<>(context);
        mResultReceiverWeakReference = new WeakReference<>(receiver);
    }

    @Override
    public void sendTrackData(Location location, WakefulBroadcastReceiver broadcastReceiver) {
        Intent sendLocIntent = new Intent(mContextWeakReference.get(), SendEmployeeLocationService.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_LOCATION_CHANGED,location);
        sendLocIntent.putExtras(bundle);
        broadcastReceiver.startWakefulService(mContextWeakReference.get(),sendLocIntent);
    }
}
