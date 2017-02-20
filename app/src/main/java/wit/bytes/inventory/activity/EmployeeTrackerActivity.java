package wit.bytes.inventory.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import wit.bytes.inventory.R;
import wit.bytes.inventory.services.LocationTrackerService;

/**
 * Created by Md. Sifat-Ul Haque on 2/19/2017.
 */

public class EmployeeTrackerActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvTrackingStatus;
    private boolean isOnline;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_tracker);
        initViews();
        setupToolBar(R.id.employee_tracker_toolbar);
        setupView();
        initListeners();
    }

    private void initListeners() {
        mTvTrackingStatus.setOnClickListener(this);
    }

    private void setupView() {
        isOnline = isMyServiceRunning(LocationTrackerService.class);
        if (isOnline){
            setOnlineStatus();
        }else {
            setOfflineStatus();
        }
    }

    private void setOfflineStatus() {
        mTvTrackingStatus.setText("Offline");
        mTvTrackingStatus.setBackgroundResource(R.color.colorPrimary);
    }

    private void setOnlineStatus() {
        mTvTrackingStatus.setText("Online");
        mTvTrackingStatus.setBackgroundResource(R.color.online);
    }

    private void initViews() {
        mTvTrackingStatus = (TextView) findViewById(R.id.tv_tracking_status);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        toggleTracker();
    }

    private void toggleTracker() {
        if (isOnline){
            stopService(new Intent(EmployeeTrackerActivity.this, LocationTrackerService.class));
        }else {
            startService(new Intent(EmployeeTrackerActivity.this, LocationTrackerService.class));
        }
        setupView();
    }
}
