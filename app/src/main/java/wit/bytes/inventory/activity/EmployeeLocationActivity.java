package wit.bytes.inventory.activity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import wit.bytes.inventory.R;
import wit.bytes.inventory.models.Location;
import wit.bytes.inventory.obserables.LocationUpdateObservable;
import wit.bytes.inventory.utils.LocationUpdateUtilities;
import wit.bytes.inventory.utils.Utils;

public class EmployeeLocationActivity extends BaseActivity implements OnMapReadyCallback, Observer {

    private GoogleMap mMap;
    private BitmapDescriptor mBitmapDescriptor;
    private LocationUpdateObservable mLocationUpdateObservable;
    private ArrayList<Location> mLocations = new ArrayList<>();

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

        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney").icon(mBitmapDescriptor));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
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

        if (mMap != null){
            mMap.clear();
            for (Location location : mLocations) {
                LatLng sydney = new LatLng(location.getLat(), location.getLng());
                mMap.addMarker(new MarkerOptions().position(sydney).title(location.getEmployeeName()).icon(mBitmapDescriptor));
            }
        }
    }
}
