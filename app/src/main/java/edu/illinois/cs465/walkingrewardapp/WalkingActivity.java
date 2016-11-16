package edu.illinois.cs465.walkingrewardapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import static java.lang.Double.valueOf;

import android.widget.ProgressBar;

import edu.illinois.cs465.walkingrewardapp.Maps.TouchableWrapper;

import edu.illinois.cs465.walkingrewardapp.Data.Challenge;


public class WalkingActivity extends AppCompatActivity implements
        GoogleMap.OnCameraMoveListener,
        GoogleMap.OnMyLocationButtonClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
        LocationListener,
        SensorEventListener,
        TouchableWrapper.UpdateMapAfterUserInterection
{

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;
    private GoogleMap mMap;
    LocationManager locationManager;
    String locationProvider;

    int progress = 0;
    ProgressBar simpleProgressBar;

    protected void openActivity(Class<?> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }

        mSensorManager.registerListener(this, mStepCounterSensor,
                SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(this, mStepDetectorSensor,
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            this.locationManager.removeUpdates(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //mSensorManager.unregisterListener(this, mStepCounterSensor);
        //mSensorManager.unregisterListener(this, mStepDetectorSensor);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walking);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }

        try {
            Challenge goal = (Challenge) getIntent().getExtras().getSerializable("goal_object");
            TextView restaurant = (TextView) findViewById(R.id.restaurant);
            restaurant.setText(goal.getRestaurant());
            TextView description = (TextView) findViewById(R.id.description);
            description.setText(goal.getDescription());
            TextView step = (TextView) findViewById(R.id.step);
            step.setText(Integer.toString(goal.getStepsRequired()) + "steps");
        }
            catch (Exception e) {
        }

        SetupSensor();

        // initiate progress
        simpleProgressBar = (ProgressBar) findViewById(R.id.simpleProgressBar);
        setProgressValue(progress);
    }

    private void setProgressValue(final int progress) {

        // set the progress
        simpleProgressBar.setProgress(progress);
        // thread is used to change the progress value
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setProgressValue(progress + 10);
            }
        });
        thread.start();
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
        mMap.setOnMyLocationButtonClickListener(this);
        enableMyLocation();

        Location location = null;
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            location  = locationManager.getLastKnownLocation(locationProvider);
        }

        //initialize the location
        if(location != null) {
            onLocationChanged(location);
        }
    }


    boolean isFollowing = true;
    @Override
    public void onLocationChanged(Location location) {
        if(isFollowing) {
            CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
            this.mMap.moveCamera(center);
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
            this.mMap.animateCamera(zoom);
        }
    }

    @Override
    public void onCameraMove(){

    }

    @Override
    public boolean onMyLocationButtonClick() {
        isFollowing = true;
        return false;
    }

    public void onUpdateMapAfterUserInterection() {
        isFollowing = false;
    }
    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            //get the location manager
            this.locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

            //define the location manager criteria
            Criteria criteria = new Criteria();
            this.locationProvider = locationManager.getBestProvider(criteria, false);

            mMap.setMyLocationEnabled(true);
            this.locationManager.requestLocationUpdates(this.locationProvider, 400, 1, this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            mPermissionDenied = false;
        }
    }

    @Override
    public void onProviderDisabled(String arg0) {

    }

    @Override
    public void onProviderEnabled(String arg0) {

    }

    @Override
    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {

    }

    //code from http://www.vogella.com/tutorials/AndroidActionBar/article.html
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

//        MenuItem item = menu.findItem(R.id.spinner);
//        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
//
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.spinner_items, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        spinner.setAdapter(adapter);

        return true;
    }

    //code from http://www.vogella.com/tutorials/AndroidActionBar/article.html
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_change_goal:
                openActivity(ChooseGoalActivity.class);
                break;
            case R.id.action_my_rewards:
                //Toast.makeText(getApplicationContext(), "Thanks for clicking the Rewards button!", Toast.LENGTH_SHORT).show();
                openActivity(RewardsActivity.class);
                break;
            case R.id.action_view_statistics:
                openActivity(StatisticsActivity.class);
                break;
            default:
                break;
        }
        return true;
    }

    private SensorManager mSensorManager;

    private Sensor mStepCounterSensor;

    private Sensor mStepDetectorSensor;

    protected void SetupSensor()
    {
        mSensorManager = (SensorManager)
                getSystemService(Context.SENSOR_SERVICE);
        mStepCounterSensor = mSensorManager
                .getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        mStepDetectorSensor = mSensorManager
                .getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        ((TextView)findViewById(R.id.stepCount)).setText("Step Counter Detected : 0");
    }

    public void onSensorChanged (SensorEvent e)
    {
        Sensor sensor = e.sensor;
        float[] values = e.values;
        TextView textView = (TextView)findViewById(R.id.stepCount);
        int value = -1;

        if (values.length > 0) {
            value = (int) values[0];
        }

        //if (sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            textView.setText("Step Counter Detected : " + value);
        //}
    }

    public void onAccuracyChanged(Sensor s, int i)
    {

    }
}
