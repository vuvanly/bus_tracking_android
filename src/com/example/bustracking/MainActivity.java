package com.example.bustracking;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;
import android.support.v4.app.Fragment;

public class MainActivity extends Activity implements OnMapClickListener {
	// Google Map
    private GoogleMap googleMap;
    GPSTracker gps;
    double currentLatitude;
    double currentLongitude;
    int zoomLevel = 14;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		try {
            // Loading map
            initilizeMap();
            gps = new GPSTracker(MainActivity.this);
            if (gps.canGetLocation()){
            	currentLatitude = gps.getLatitude();
            	currentLongitude = gps.getLongitude();
            	// create marker
            	MarkerOptions marker = new MarkerOptions().position(new LatLng(currentLatitude, currentLongitude)).title("Hello Maps ");
            	 
            	// adding marker
            	googleMap.addMarker(marker);
            	CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(currentLatitude, currentLongitude)).zoom(zoomLevel).build();
            	googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            	
            	Toast.makeText(getApplicationContext(), "Your location is - \nLat: " + currentLatitude + "\nLong: "+ currentLongitude, Toast.LENGTH_LONG).show();
            }else{
            	gps.showSettingsAlert();
            }
 
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	/**
     * function to load map. If map is not created it will create it for you
     * */

	private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment)this.getFragmentManager().findFragmentById(
                    R.id.map)).getMap();
            googleMap.setOnMapClickListener(this);
 
            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
 
    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }

	@Override
	public void onMapClick(LatLng point) {
		// TODO Auto-generated method stub
		MarkerOptions marker = new MarkerOptions().position(new LatLng(point.latitude, point.longitude)).title("Hello Maps ");
		googleMap.addMarker(marker);
//		AlertDialog.Builder alertDialog = new AlertDialog.Builder(getApplicationContext());
//		alertDialog.setTitle("Tap on map");
		Toast.makeText(getApplicationContext(), "Lat: "+ point.latitude + "\nLong: " + point.longitude, Toast.LENGTH_LONG);
		
	}
}
