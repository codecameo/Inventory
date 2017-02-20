package wit.bytes.inventory.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wit.bytes.inventory.apiConfigs.ApiClient;
import wit.bytes.inventory.apiConfigs.IApiTracker;
import wit.bytes.inventory.apiConfigs.IApiUserAuth;
import wit.bytes.inventory.models.api_models.LoginResponseModel;
import wit.bytes.inventory.receivers.LocationReceiver;
import wit.bytes.inventory.utils.Constants;
import wit.bytes.inventory.wrappers.LoginAdapter;

/**
 * Created by Md. Sifat-Ul Haque on 2/21/2017.
 */

public class LoginService extends IntentService {

    private WeakReference<ResultReceiver> resultReceiver;
    private String mUsername, mPassword;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public LoginService() {
        super("LoginService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        resultReceiver = new WeakReference<>((ResultReceiver) intent.getParcelableExtra(LoginAdapter.KEY_LOGIN_RESULT_RECEIVER));
        mUsername = intent.getStringExtra(LoginAdapter.KEY_LOGIN_USERNAME);
        mPassword = intent.getStringExtra(LoginAdapter.KEY_LOGIN_PASSWORD);

        IApiUserAuth apiService = ApiClient.getClient().create(IApiUserAuth.class);

        Call<LoginResponseModel> call = apiService.login(mUsername, mPassword);
        call.enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
               if (resultReceiver.get() != null){
                   LoginResponseModel loginResponseModel = response.body();
                   if (loginResponseModel != null && loginResponseModel.isSuccess()){
                       Bundle bundle = new Bundle();
                       bundle.putSerializable(LoginAdapter.KEY_LOGIN_RESPONSE,loginResponseModel);
                       resultReceiver.get().send(Constants.API_RESPONSE_SUCCESSFUL,bundle);
                   }
                   else{
                       resultReceiver.get().send(Constants.API_RESPONSE_ERROR,null);
                   }
               }
            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                if (resultReceiver.get() != null){
                    resultReceiver.get().send(Constants.API_RESPONSE_ERROR,null);
                }
            }
        });

    }
}
