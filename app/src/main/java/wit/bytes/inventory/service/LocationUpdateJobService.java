package wit.bytes.inventory.service;

import android.os.AsyncTask;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by Sharifur Rahaman on 2/19/2017.
 * Email: sharif.iit.du@gmail.com
 */

public class LocationUpdateJobService extends JobService {

    private static final String TAG = "LocationUpdateJobServic";

    private JobParameters mJobParameters;

    private AsyncTask mLocationUpdateTask = new AsyncTask() {
        @Override
        protected Object doInBackground(Object[] params) {



            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Log.d(TAG, "scheduler is called");

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
