package wit.bytes.inventory.services;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wit.bytes.inventory.apiConfigs.ApiClient;
import wit.bytes.inventory.apiConfigs.IApiTracker;
import wit.bytes.inventory.models.api_models.LocationSenderResponse;
import wit.bytes.inventory.receivers.LocationReceiver;
import wit.bytes.inventory.utils.Constants;
import wit.bytes.inventory.utils.PreferenceUtil;

import static wit.bytes.inventory.receivers.LocationReceiver.KEY_LOCATION_CHANGED;

/**
 * Created by Md. Sifat-Ul Haque on 2/19/2017.
 */

public class SendEmployeeLocationService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    private Location mLocation;
    private PreferenceUtil mPreferenceUtil;
    private String mAccessToken;
    private int mUserId;

    public SendEmployeeLocationService() {
        super("SendEmployeeLocationService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mPreferenceUtil = PreferenceUtil.getInstance(this);
        mAccessToken = mPreferenceUtil.getString(Constants.KEY_ACCESS_TOKEN,"");
        mUserId = mPreferenceUtil.getInt(Constants.KEY_USER_ID,-1);

    }

    @Override
    protected void onHandleIntent(final Intent intent) {

        mLocation = intent.getExtras().getParcelable(KEY_LOCATION_CHANGED);

        if (mLocation != null){
            long time = System.currentTimeMillis();
            Date date=new Date(time);
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String dateText = df2.format(date);

            TimeZone timeZone = TimeZone.getDefault();

            Log.d("Location","Lat "+mLocation.getLatitude()+"\nLng "+mLocation.getLongitude()
                    +"\nDate "+dateText+"\nTimezone "+timeZone.getDisplayName(false, TimeZone.SHORT));

            IApiTracker apiService = ApiClient.getClient().create(IApiTracker.class);

            Call<LocationSenderResponse> call = apiService.sendEmployeeLocation(mUserId,
                    mLocation.getLatitude(),
                    mLocation.getLongitude(),
                    dateText,
                    timeZone.getDisplayName(false, TimeZone.SHORT),
                    mAccessToken);

            call.enqueue(new Callback<LocationSenderResponse>() {
                @Override
                public void onResponse(Call<LocationSenderResponse> call, Response<LocationSenderResponse> response) {
                    //Log.d("Location",response.body().getMessage());
                    LocationReceiver.completeWakefulIntent(intent);
                }

                @Override
                public void onFailure(Call<LocationSenderResponse> call, Throwable t) {
                    Log.d("Location","Send LoginData Error");
                    LocationReceiver.completeWakefulIntent(intent);
                }
            });
        }
    }
}
