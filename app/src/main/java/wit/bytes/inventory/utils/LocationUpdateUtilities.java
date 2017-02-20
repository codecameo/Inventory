package wit.bytes.inventory.utils;

import android.content.Context;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobTrigger;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import wit.bytes.inventory.services.LocationUpdateJobService;

/**
 * Created by Sharifur Rahaman on 2/19/2017.
 * Email: sharif.iit.du@gmail.com
 */

public class LocationUpdateUtilities {

    private static final int LOCATION_UPDATE_INTERVAL_MINUTE = 1;

    //private static final int LOCATION_UPDATE_INTERVAL_SECONDS = (int) TimeUnit.MINUTES.toSeconds(LOCATION_UPDATE_INTERVAL_MINUTE);

    private static final int LOCATION_UPDATE_INTERVAL_SECONDS = 20;

    private static final String LOCATION_JOB_TAG = "location_job_tag";

    synchronized public static void scheduleLocationUpdate(final Context context, boolean isStartNow){

        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        JobTrigger jobTrigger = Trigger.executionWindow(0, LOCATION_UPDATE_INTERVAL_SECONDS);
        if (isStartNow){
            jobTrigger = Trigger.NOW;
        }

        Job locationUpdateJob = dispatcher.newJobBuilder()
                .setService(LocationUpdateJobService.class)
                .setTag(LOCATION_JOB_TAG)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(!isStartNow)
                // starts between 0 seconds and interval seconds from now.
                .setTrigger(jobTrigger)
                .setReplaceCurrent(true)
                .build();
        dispatcher.schedule(locationUpdateJob);
    }


    public static void stopLocationUpdateScheduler(Context context){
        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);
        dispatcher.cancel(LOCATION_JOB_TAG);
    }


}
