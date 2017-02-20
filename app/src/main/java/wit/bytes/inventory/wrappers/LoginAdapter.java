package wit.bytes.inventory.wrappers;

import android.content.Context;
import android.content.Intent;
import android.os.ResultReceiver;

import java.lang.ref.WeakReference;

import wit.bytes.inventory.interfaces.ILoginProvider;
import wit.bytes.inventory.services.LoginService;

/**
 * Created by Md. Sifat-Ul Haque on 2/21/2017.
 */

public class LoginAdapter implements ILoginProvider {

    public static final String KEY_LOGIN_RESULT_RECEIVER = "login_result_receiver";
    public static final String KEY_LOGIN_USERNAME = "login_username";
    public static final String KEY_LOGIN_PASSWORD = "login_password";
    public static final String KEY_LOGIN_RESPONSE = "login_response";

    private WeakReference<Context> mContextWeakReference;
    private WeakReference<ResultReceiver> mResultReceiverWeakReference;

    public LoginAdapter(Context context, ResultReceiver receiver) {
        mContextWeakReference = new WeakReference<>(context);
        mResultReceiverWeakReference = new WeakReference<>(receiver);
    }

    @Override
    public void login(String username, String password) {
        Intent intent = new Intent(mContextWeakReference.get(), LoginService.class);
        intent.putExtra(KEY_LOGIN_RESULT_RECEIVER, mResultReceiverWeakReference.get());
        intent.putExtra(KEY_LOGIN_PASSWORD,password);
        intent.putExtra(KEY_LOGIN_USERNAME,username);
        mContextWeakReference.get().startService(intent);
    }
}
