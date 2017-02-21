package wit.bytes.inventory.services;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wit.bytes.inventory.apiConfigs.ApiClient;
import wit.bytes.inventory.apiConfigs.IApiTracker;
import wit.bytes.inventory.models.Location;
import wit.bytes.inventory.models.api_models.LocationGetterResponse;
import wit.bytes.inventory.utils.Constants;
import wit.bytes.inventory.utils.PreferenceUtil;

import static wit.bytes.inventory.receivers.LocationUpdateReceiver.KEY_UPDATE_LOCATION;

/**
 * Created by Sharifur Rahaman on 2/19/2017.
 * Email: sharif.iit.du@gmail.com
 */

public class LocationUpdateJobService extends JobService {

    private static final String TAG = "LocationUpdateJobServic";

    private JobParameters mJobParameters;

    private LocationUpdateTask mLocationUpdateTask;
    private String mAccessToken;

    @Override
    public boolean onStartJob(JobParameters job) {
        mJobParameters = job;
        mAccessToken = PreferenceUtil.getInstance(this).getString(Constants.KEY_ACCESS_TOKEN,"");
        new LocationUpdateTask().execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        if (mLocationUpdateTask != null)
            mLocationUpdateTask.cancel(true);

        return true;
    }

    private class LocationUpdateTask extends AsyncTask<Void, Void, List<Location>> {
        protected List<Location> doInBackground(Void... voids) {
            Log.d(TAG, "retrofit initialization");


            LocationGetterResponse locationGetterResponse;
            ArrayList<Location> locations = new ArrayList<>();

            IApiTracker apiService = ApiClient.getClient().create(IApiTracker.class);

            //final LocationAPI locationAPI = retrofit.create(LocationAPI.class);

            try {
                locationGetterResponse = apiService.getLocations(mAccessToken).execute().body();
                if (locationGetterResponse != null){
                    locations = locationGetterResponse.getData();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "onResponse: failure");
            }

            /*apiService.getLocations(mAccessToken).enqueue(new Callback<LocationGetterResponse>() {
                @Override
                public void onResponse(Call<LocationGetterResponse> call, Response<LocationGetterResponse> response) {

                    Log.d(TAG,"URL "+call.request().url());

                    if (response.isSuccessful()){
                        locations.clear();
                        locations.addAll(response.body().getData());
                        Log.d(TAG, "onResponse: " + locations.size());
                    }
                }

                @Override
                public void onFailure(Call<LocationGetterResponse> call, Throwable t) {
                    Log.d(TAG, "onResponse: failure" );
                }
            });*/

            return locations;
        }

        protected void onPostExecute(List<Location> locations) {
            super.onPostExecute(locations);

            if (locations != null){

                Bundle bundle = new Bundle();
                bundle.putSerializable(KEY_UPDATE_LOCATION, (Serializable) locations);

                Intent intent = new Intent();
                intent.setAction("wit.bytes.inventory.receivers.LocationUpdate");
                intent.putExtras(bundle);
                sendBroadcast(intent);

                //Toast.makeText(LocationUpdateJobService.this,"Size "+locations.size(),Toast.LENGTH_SHORT).show();
            }
            jobFinished(mJobParameters, false);
        }
    }
}
