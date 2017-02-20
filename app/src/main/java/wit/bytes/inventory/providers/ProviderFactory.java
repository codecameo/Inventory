package wit.bytes.inventory.providers;

import android.content.Context;
import android.os.ResultReceiver;

import wit.bytes.inventory.interfaces.ILocationTracker;
import wit.bytes.inventory.interfaces.ILoginProvider;
import wit.bytes.inventory.wrappers.LoginAdapter;
import wit.bytes.inventory.wrappers.TrackerAdapter;

/**
 * Created by Md. Sifat-Ul Haque on 2/19/2017.
 */

public class ProviderFactory {
    private static volatile ProviderFactory mProviderFactory;

    private ProviderFactory() {
    }

    public static ProviderFactory getProviderInstance() {
        if (mProviderFactory == null) {
            synchronized (ProviderFactory.class) {
                if (mProviderFactory == null)
                    mProviderFactory = new ProviderFactory();
            }
        }

        return mProviderFactory;
    }


    public ILocationTracker getLocationSendProvider(Context context, ResultReceiver receiver) {
        return (ILocationTracker) new TrackerAdapter(context, receiver);
    }

    public ILoginProvider getProductProvider(Context context, ResultReceiver receiver) {
        return (ILoginProvider) new LoginAdapter(context, receiver);
    }
}
