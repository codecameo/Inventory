package wit.bytes.inventory.services;

import android.os.AsyncTask;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import wit.bytes.inventory.interfaces.LocationAPI;
import wit.bytes.inventory.models.Location;

/**
 * Created by Sharifur Rahaman on 2/19/2017.
 * Email: sharif.iit.du@gmail.com
 */

public class LocationUpdateJobService extends JobService {

    private static final String TAG = "LocationUpdateJobServic";

    public static final String LOCATION_URL = "http://aimsil.com/";

    private JobParameters mJobParameters;

    private AsyncTask mLocationUpdateTask = new AsyncTask() {
        @Override
        protected List<Location> doInBackground(Object[] params) {

            Log.d(TAG, "retrofit initialization");

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(LOCATION_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            LocationAPI locationAPI = retrofit.create(LocationAPI.class);


            locationAPI.getLocations(new Callback<List<Location>>() {
                @Override
                public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                    Log.d(TAG, "onResponse: " + response.body().size());
                }

                @Override
                public void onFailure(Call<List<Location>> call, Throwable t) {

                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            jobFinished(mJobParameters, false);
        }
    };

    @Override
    public boolean onStartJob(JobParameters job) {
        mJobParameters = job;
        mLocationUpdateTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        if (mLocationUpdateTask != null)
            mLocationUpdateTask.cancel(true);

        return true;
    }
}
