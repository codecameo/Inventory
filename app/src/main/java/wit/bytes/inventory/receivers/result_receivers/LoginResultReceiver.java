package wit.bytes.inventory.receivers.result_receivers;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import java.lang.ref.WeakReference;

import wit.bytes.inventory.utils.Constants;

/**
 * Created by Md. Sifat-Ul Haque on 2/21/2017.
 */

public class LoginResultReceiver extends ResultReceiver {

    private WeakReference<Receiver> mReceiver;

    public LoginResultReceiver(Handler handler) {
        super(handler);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (mReceiver.get() != null) {
            mReceiver.get().onReceiveResult(resultCode, resultData);
        }
    }

    public interface Receiver {
        void onReceiveResult(int resultCode, Bundle resultData);
    }

    public void setReceiver(Receiver receiver) {
        mReceiver = new WeakReference<>(receiver);
    }
}
