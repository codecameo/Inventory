package wit.bytes.inventory.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import wit.bytes.inventory.R;
import wit.bytes.inventory.models.Location;
import wit.bytes.inventory.obserables.LocationUpdateObservable;
import wit.bytes.inventory.utils.LocationUpdateUtilities;
import wit.bytes.inventory.utils.PermissionHandler;
import wit.bytes.inventory.utils.Utils;

import static wit.bytes.inventory.utils.PermissionHandler.REQUEST_BOTH_LOCATION_PERMISSION;
import static wit.bytes.inventory.utils.PermissionHandler.REQUEST_COARSE_LOCATION;
import static wit.bytes.inventory.utils.PermissionHandler.REQUEST_FINE_LOCATION;

public class EmployeeLocationActivity extends BaseActivity implements OnMapReadyCallback, Observer, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mGoogleMap;
    private BitmapDescriptor mBitmapDescriptor;
    private LocationUpdateObservable mLocationUpdateObservable;
    private ArrayList<Location> mLocations = new ArrayList<>();
    private LatLng mMyLastLocation;
    private CameraPosition mCameraPosition;
    private GoogleApiClient mGoogleApiClient;
    private boolean isPreviouslyLoaded;
    private Handler mHandler;
    private UiSettings mUiSettings;

    private Runnable mLastLocation = new Runnable() {
        @Override
        public void run() {
            if (ActivityCompat.checkSelfPermission(EmployeeLocationActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(EmployeeLocationActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                if (mGoogleApiClient.isConnected()) {
                    android.location.Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                    if (lastLocation != null) {
                        mMyLastLocation = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
                        setCamera();
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_location);
        setupToolBar(R.id.employee_location_toolbar);

        initVariable();

        // Scheduler for now
        LocationUpdateUtilities.scheduleLocationUpdate(this, true);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Scheduler for regular interval
        LocationUpdateUtilities.scheduleLocationUpdate(this, false);
        mLocationUpdateObservable.addObserver(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocationUpdateUtilities.stopLocationUpdateScheduler(this);
        mLocationUpdateObservable.deleteObserver(this);
    }

    private void initVariable() {
        mBitmapDescriptor = getBitmapDescriptor(R.drawable.ic_employee_marker);
        mLocationUpdateObservable = LocationUpdateObservable.getInstance();
        mHandler = new Handler();

        //Initializing googleapi client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;
        mUiSettings = mGoogleMap.getUiSettings();
        mapUiSetting(true);
        setCamera();

        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mGoogleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney").icon(mBitmapDescriptor));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }


    private BitmapDescriptor getBitmapDescriptor(int id) {
        Drawable vectorDrawable = ContextCompat.getDrawable(this, id);
        int h = ((int) Utils.convertDpToPixel(36));
        int w = ((int) Utils.convertDpToPixel(36));
        vectorDrawable.setBounds(0, 0, w, h);
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bm);
    }

    @Override
    public void update(Observable observable, Object data) {
        mLocations = (ArrayList<Location>) data;

        Log.d("LocationUpdateJobServic","Got LoginData "+mLocations.size());

        //Toast.makeText(this,"Size Got "+mLocations.size(),Toast.LENGTH_SHORT).show();

        if (mGoogleMap != null){
            mGoogleMap.clear();
            for (Location location : mLocations) {
                LatLng sydney = new LatLng(location.getLat(), location.getLng());
                mGoogleMap.addMarker(new MarkerOptions().position(sydney).title(location.getEmployeeName()).icon(mBitmapDescriptor));
            }
        }
    }

    private void setCamera() {
        if (mMyLastLocation != null) {
            mCameraPosition = new CameraPosition.Builder()
                    .target(mMyLastLocation)
                    .zoom(13)
                    .build();
            mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(mCameraPosition));
        }
    }



    //UI settings of map
    private void mapUiSetting(boolean flag) {

        enableLocation(flag);

        mGoogleMap.setBuildingsEnabled(flag);
        mUiSettings.setZoomControlsEnabled(flag);
        mUiSettings.setCompassEnabled(flag);
        mUiSettings.setMyLocationButtonEnabled(flag);
        mUiSettings.setScrollGesturesEnabled(flag);
        mUiSettings.setZoomGesturesEnabled(flag);
        mUiSettings.setTiltGesturesEnabled(flag);
        mUiSettings.setRotateGesturesEnabled(flag);

        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    private void enableLocation(boolean flag) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mGoogleMap.setMyLocationEnabled(flag);
        } else if (!PermissionHandler.hasFineLocationPermission(this)) {
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
                    enableLocation(true);
                } else {
                    Toast.makeText(this, getString(R.string.toast_location_permision_denied), Toast.LENGTH_LONG).show();
                }
                break;
        }
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (!isPreviouslyLoaded) {
            isPreviouslyLoaded = true;
            mHandler.post(mLastLocation);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        connectGoogleApiClient();
    }

    @Override
    protected void onStart() {
        connectGoogleApiClient();
        super.onStart();
    }

    @Override
    protected void onStop() {
        disconnectGoogleApiClient();
        super.onStop();
    }

    private void connectGoogleApiClient() {
        if (!mGoogleApiClient.isConnected() && !mGoogleApiClient.isConnecting()) {
            mGoogleApiClient.connect();
        }
    }

    private void disconnectGoogleApiClient() {
        if (mGoogleApiClient.isConnected() || mGoogleApiClient.isConnecting()) {
            mGoogleApiClient.disconnect();
        }
    }
}
