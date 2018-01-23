package com.example.androidproject;


import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.model.Step;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Switch botanicSwitch;
    private Switch btnrSwitch;
    private Switch gbtbSwitch;
    private Switch macritchieSwitch;
    private Switch sbwrSwitch;
    private Button genrouteBtn;
    private TextView routeResult;
    private EditText budget;
    private Button showRoute;

    private boolean botanic;
    private boolean btnr;
    private boolean gbtb;
    private boolean macritchie;
    private boolean sbwr;

    private List<List<LocationEdge>> locationAdjList;
    private Algo generateRoute;
    private List<Location> optimalRoute;


    boolean mShowMap;
    GoogleMap mMap;
    Geocoder geocoder;
    List<Address> addresses = new ArrayList<>();
    private String place = "Singapore";

    public static final String TAG = "algo_activity";
    private String serverKey = "AIzaSyABO1fl647jdMJvlxZmJGtSTbYyGMk6cp8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botanicSwitch = (Switch)findViewById(R.id.botanic_switch);
        btnrSwitch = (Switch)findViewById(R.id.btnr_switch);
        gbtbSwitch = (Switch)findViewById(R.id.gbtb_switch);
        macritchieSwitch = (Switch)findViewById(R.id.macritchie_switch);
        sbwrSwitch = (Switch)findViewById(R.id.sbwr_switch);
        genrouteBtn = (Button) findViewById(R.id.genroute_button);
        budget = (EditText) findViewById(R.id.budget);

        genrouteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                botanic = botanicSwitch.isChecked();
                btnr = btnrSwitch.isChecked();
                gbtb = gbtbSwitch.isChecked();
                macritchie = macritchieSwitch.isChecked();
                sbwr = sbwrSwitch.isChecked();
                Integer travellingBudget = 0;


                if(!(botanic || btnr || gbtb || macritchie || sbwr)){
                    Toast.makeText(MainActivity.this, "Please select a Location", Toast.LENGTH_SHORT).show();
                }else{
                    if(budget.getText().toString().equals("")){
                        Toast.makeText(MainActivity.this, "Please enter a budget", Toast.LENGTH_SHORT).show();
                    }else{
                        travellingBudget = Integer.parseInt(budget.getText().toString());
                        SelectLocations selectLocations = new SelectLocations(botanic, btnr, gbtb, macritchie, sbwr);
                        locationAdjList = selectLocations.getAdjlist();

                        generateRoute = new Algo(locationAdjList, travellingBudget);

                        GenerateRoute startALgo = new GenerateRoute();
                        startALgo.execute(generateRoute);
                    }
                }
            }
        });



        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        geocoder = new Geocoder(this, Locale.getDefault());
//        try {
//            addresses = geocoder.getFromLocationName(place, 1);
//            double latitude = addresses.get(0).getLatitude();
//            double longtitude = addresses.get(0).getLongitude();
        LatLng displayLocation = new LatLng(1.352083, 103.819836);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(displayLocation));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(10));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public class GenerateRoute extends AsyncTask<Algo, Void, List<Location>>{
        @Override
        protected List<Location> doInBackground(Algo... algos) {
            Location firstLocation = new Location("Marina Bay", 0);
            algos[0].startBF(firstLocation);
            List<Location> optimalRoute = algos[0].getOptimalRoute();

            return optimalRoute;
        }

        @Override
        protected void onPostExecute(List<Location> locations) {
            super.onPostExecute(locations);

//            for(Location l : locations){
//                Log.d(TAG, l.getLocation() + " by " + l.getModeoftransport());
//            }
            geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

            optimalRoute = locations;

            mMap.clear();

            String result = "";
            for(int i = 0; i < locations.size(); i++){
                result += (i+1) + ") " + locations.get(i).getLocation() + " by " + locations.get(i).getModeoftransport() + "\n\n";

                try {
                    addresses = geocoder.getFromLocationName(locations.get(i).getLocation(),1);
                    double latitude = addresses.get(0).getLatitude();
                    double longtitude = addresses.get(0).getLongitude();
                    mMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longtitude)).title(locations.get(i).getLocation()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Intent intent = new Intent(MainActivity.this, Popup.class);
            intent.putExtra(TAG, result);
            MainActivity.this.startActivity(intent);
            //routeResult.setText(result);
//            GoogleDirection.withServerKey(serverKey)
//                    .from(new LatLng(37.7681994, -122.444538))
//                    .to(new LatLng(37.7749003,-122.4034934))
//                    .execute(new DirectionCallback() {
//                        @Override
//                        public void onDirectionSuccess(Direction direction, String rawBody) {
//                            if(direction.isOK()) {
//                                Log.d(TAG, "direction OK");
//                                Route route = direction.getRouteList().get(0);
//                                Leg leg = route.getLegList().get(0);
//                                List<Step> stepList = leg.getStepList();
//                                ArrayList<LatLng> pointList = leg.getDirectionPoint();
//                                PolylineOptions polylineOptions = DirectionConverter.createPolyline(getApplicationContext(), pointList, 5, Color.RED);
//                                mMap.addPolyline(polylineOptions);
//                            } else {
//                                Log.d(TAG, "direction not OK");
//                            }
//                        }
//
//                        @Override
//                        public void onDirectionFailure(Throwable t) {
//                            Log.d(TAG, "error getting direction");
//                        }
//                    });

        }
    }
}
