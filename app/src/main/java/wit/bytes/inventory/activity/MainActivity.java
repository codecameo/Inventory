package wit.bytes.inventory.activity;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import wit.bytes.inventory.R;
import wit.bytes.inventory.services.LocationTrackerService;
import wit.bytes.inventory.utils.LocationUpdateUtilities;
import wit.bytes.inventory.utils.PermissionHandler;

import static wit.bytes.inventory.utils.PermissionHandler.REQUEST_BOTH_LOCATION_PERMISSION;
import static wit.bytes.inventory.utils.PermissionHandler.REQUEST_COARSE_LOCATION;
import static wit.bytes.inventory.utils.PermissionHandler.REQUEST_FINE_LOCATION;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FloatingActionButton mFab;
    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setupToolBar(R.id.toolbar);
        initNavigationView();
        enableLocation();

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        LocationUpdateUtilities.scheduleLocationUpdate(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Location","Is Service running "+isMyServiceRunning(LocationTrackerService.class));

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

    private void initNavigationView() {
        mToggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    private void initView() {
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_target) {
            // Handle the camera action
            startActivity(new Intent(MainActivity.this,TargetActivity.class));
        } else if (id == R.id.nav_employee_location) {
            startActivity(new Intent(MainActivity.this,EmployeeLocationActivity.class));
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {
            startService(new Intent(MainActivity.this, LocationTrackerService.class));
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void enableLocation() {
        if (!PermissionHandler.hasFineLocationPermission(this)) {
            PermissionHandler.requestPermissions(this, PermissionHandler.REQUEST_FINE_LOCATION, getString(R.string.permission_msg_location), Manifest.permission.ACCESS_FINE_LOCATION);
        } else if (!PermissionHandler.hasCoarseLocationPermission(this)) {
            PermissionHandler.requestPermissions(this, PermissionHandler.REQUEST_FINE_LOCATION, getString(R.string.permission_msg_location), Manifest.permission.ACCESS_COARSE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case REQUEST_BOTH_LOCATION_PERMISSION:
            case REQUEST_COARSE_LOCATION:
            case REQUEST_FINE_LOCATION:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Location enabled", Toast.LENGTH_SHORT).show();
                    enableLocation();
                } else {
                    Toast.makeText(this, getString(R.string.toast_location_permision_denied), Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
