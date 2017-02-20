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
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wit.bytes.inventory.apiConfigs.ApiClient;
import wit.bytes.inventory.apiConfigs.IApiTracker;
import wit.bytes.inventory.models.Location;
import wit.bytes.inventory.obserables.LocationUpdateObservable;
import wit.bytes.inventory.receivers.LocationReceiver;

import static wit.bytes.inventory.receivers.LocationUpdateReceiver.KEY_UPDATE_LOCATION;

/**
 * Created by Sharifur Rahaman on 2/19/2017.
 * Email: sharif.iit.du@gmail.com
 */

public class LocationUpdateJobService extends JobService {

    private static final String TAG = "LocationUpdateJobServic";

    private JobParameters mJobParameters;

    private AsyncTask mLocationUpdateTask = new AsyncTask<Void, Void, List<Location>>() {
        @Override
        protected List<Location> doInBackground(Void... voids) {

            Log.d(TAG, "retrofit initialization");

            List<Location> locations = null;

            IApiTracker apiService = ApiClient.getClient().create(IApiTracker.class);

            //final LocationAPI locationAPI = retrofit.create(LocationAPI.class);

            try {
                locations = apiService.getLocations().execute().body();
                Log.d(TAG, "onResponse: " + locations.size());
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "onResponse: failure");
            }

            /*apiService.getLocations().enqueue(new Callback<List<Location>>() {
                @Override
                public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                    locations = response.body();
                    Log.d(TAG, "onResponse: " + locations.size());
                }

                @Override
                public void onFailure(Call<List<Location>> call, Throwable t) {
                    Log.d(TAG, "onResponse: failure" );
                }
            });*/

            /*apiService.getLocations().enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.d(TAG, "onResponse: " + response.body());
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d(TAG, "onResponse: failure" );
                }
            });*/


            return locations;
        }

        @Override
        protected void onPostExecute(List<Location> locations) {
            super.onPostExecute(locations);
            jobFinished(mJobParameters, false);
        }
    };

    @Override
    public boolean onStartJob(JobParameters job) {
        mJobParameters = job;
        //mLocationUpdateTask.execute();
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

            List<Location> locations = null;

            IApiTracker apiService = ApiClient.getClient().create(IApiTracker.class);

            //final LocationAPI locationAPI = retrofit.create(LocationAPI.class);

            try {
                locations = apiService.getLocations().execute().body();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "onResponse: failure");
            }

            /*apiService.getLocations().enqueue(new Callback<List<Location>>() {
                @Override
                public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                    locations = response.body();
                    Log.d(TAG, "onResponse: " + locations.size());
                }

                @Override
                public void onFailure(Call<List<Location>> call, Throwable t) {
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

                Toast.makeText(LocationUpdateJobService.this,"Size "+locations.size(),Toast.LENGTH_SHORT).show();
            }
            jobFinished(mJobParameters, false);
        }
    }
}
