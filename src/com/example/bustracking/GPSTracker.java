package com.example.bustracking;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

public class GPSTracker extends Service implements LocationListener {
	
	private final Context mContext;
	
	boolean isGPSEnabled = false;
	boolean isNetwordEnabled = false;
	boolean canGetLocation = false;
	
	Location location;
	double latitude;
	double longitude;
	
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
	private static final long MIN_TIME_BW_UPDATES = 1000 * 60  * 1;//1 mnute
	protected LocationManager locationManager;
	
	public GPSTracker(Context context){
		this.mContext = context;
		getLocation();
	}
	
	public Location getLocation(){
		locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
		isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		isNetwordEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		Toast.makeText(mContext, "isGPSEnabled: " + String.valueOf(isGPSEnabled), Toast.LENGTH_LONG);
		Toast.makeText(mContext, "isNetwordEnabled: " + String.valueOf(isNetwordEnabled), Toast.LENGTH_LONG);
		if (!isGPSEnabled && !isNetwordEnabled){
			// no network provider is enabled
		}else{
			this.canGetLocation = true;
			
			if (isNetwordEnabled){
				locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
				Log.d("Network", "Network");
				if (locationManager != null){
					location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
					if (location != null){
						latitude = location.getLatitude();
						longitude = location.getLongitude();
					}
				}
			}
			
			if (isGPSEnabled){
				if (location == null){
					locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
					if (location != null){
						latitude = location.getLatitude();
						longitude = location.getLongitude();
					}
				}
			}
		}
		return location;
	}
	

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public double getLatitude(){
		if (location != null){
			latitude = location.getLatitude();
		}
		return latitude;
	}
	
	public double getLongitude(){
		if (location != null){
			longitude = location.getLongitude();
		}
		return longitude;
	}
	
	public boolean canGetLocation(){
		return this.canGetLocation;
	}
	
	public void showSettingsAlert(){
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
		
		alertDialog.setTitle("GPS is settings");
		alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
		alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				mContext.startActivity(intent);
			}
		});
		
		alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		});
		alertDialog.show();
	}
	
	public void stopUsingGPS(){
		if (locationManager != null){
			locationManager.removeUpdates(GPSTracker.this);
		}
	}

}
