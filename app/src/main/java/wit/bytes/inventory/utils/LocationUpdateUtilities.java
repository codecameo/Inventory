package wit.bytes.inventory.utils;

import android.content.Context;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

import wit.bytes.inventory.services.LocationUpdateJobService;

/**
 * Created by Sharifur Rahaman on 2/19/2017.
 * Email: sharif.iit.du@gmail.com
 */

public class LocationUpdateUtilities {

    private static final int LOCATION_UPDATE_INTERVAL_MINUTE = 1;

    private static final int LOCATION_UPDATE_INTERVAL_SECONDS = (int) TimeUnit.MINUTES.toSeconds(LOCATION_UPDATE_INTERVAL_MINUTE);

    private static final String LOCATION_JOB_TAG = "location_job_tag";

    synchronized public static void scheduleLocationUpdate(final Context context){

        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);
        Job locationUpdateJob = dispatcher.newJobBuilder()
                .setService(LocationUpdateJobService.class)
                .setTag(LOCATION_JOB_TAG)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                // starts between 0 seconds and interval seconds from now.
                .setTrigger(Trigger.executionWindow(0, LOCATION_UPDATE_INTERVAL_SECONDS))
                .setReplaceCurrent(true)
                .build();
        dispatcher.schedule(locationUpdateJob);
    }



}
