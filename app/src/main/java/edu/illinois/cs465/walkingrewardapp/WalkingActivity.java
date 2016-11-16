package edu.illinois.cs465.walkingrewardapp;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
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
import android.support.v7.app.AlertDialog;
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

import static edu.illinois.cs465.walkingrewardapp.R.id.goal;
import static java.lang.Double.valueOf;

import android.widget.ProgressBar;

import edu.illinois.cs465.walkingrewardapp.Maps.TouchableWrapper;

import edu.illinois.cs465.walkingrewardapp.Data.Challenge;
import edu.illinois.cs465.walkingrewardapp.Library;

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
    Challenge goal;

    int progress = 0;
    ProgressBar simpleProgressBar;

    int value = 0;
    int maxSteps;

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

        try {
            goal = Library.getCurrentGoal();
            TextView current_goal = (TextView) findViewById(R.id.goal);
            current_goal.setText("Current Goal: " + goal.getRestaurant());
            TextView description = (TextView) findViewById(R.id.description);
            description.setText(goal.getDescription());
            maxSteps = goal.getStepsRequired();
            TextView progress = (TextView) findViewById(R.id.progress);
            progress.setText(Integer.toString(Library.getCurrentSteps()) + "/" + Integer.toString(maxSteps) + " steps");
        }
        catch (Exception e) {
        }
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
            Library.setCurrentGoal((Challenge) getIntent().getExtras().getSerializable("goal_object"));
            goal = Library.getCurrentGoal();
            TextView current_goal = (TextView) findViewById(R.id.goal);
            current_goal.setText("Current Goal: " + goal.getRestaurant());
            TextView description = (TextView) findViewById(R.id.description);
            description.setText(goal.getDescription());
            maxSteps = goal.getStepsRequired();
            TextView progress = (TextView) findViewById(R.id.progress);
            progress.setText(Integer.toString(Library.getCurrentSteps()) + "/" + Integer.toString(maxSteps) + " steps");
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
        thread.start();//*/
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
    }

    public void onSensorChanged (SensorEvent e)
    {
        Sensor sensor = e.sensor;
        if (sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            float[] values = e.values;
            TextView textView = (TextView) findViewById(R.id.stepCount);

            if (values.length > 0) {
                value = (int) values[0];
                Library.setTotalSteps(value);
                Library.setCurrentSteps(Library.getCurrentSteps()+1);
                goal = Library.getCurrentGoal();
                if(goal != null && Library.getCurrentSteps() >= goal.getStepsRequired())
                {
                    Library.setCurrentSteps(0);
                    Library.addReward(goal);
                    Library.setnRewardsEarned(Library.getnRewardsEarned()+1);
                    //TODO: notify and remove goal
                    Library.setCurrentGoal(null);
                    goal = Library.getCurrentGoal();
                    TextView current_goal = (TextView) findViewById(R.id.goal);
                    current_goal.setText("Current Goal: None");
                    TextView description = (TextView) findViewById(R.id.description);
                    description.setText("");

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("You have earned a Reward!")
                            .setTitle("Congratulations!");
                    builder.setPositiveButton("View My Rewards", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            openActivity(RewardsActivity.class);
                        }
                    });
                    builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }

            textView.setText("Step Counter Detected : " + value);

            TextView progress = (TextView) findViewById(R.id.progress);
            progress.setText(Integer.toString(Library.getCurrentSteps()) + "/" + Integer.toString(maxSteps) + " steps");
        }
    }

    public void onAccuracyChanged(Sensor s, int i)
    {

    }
}
