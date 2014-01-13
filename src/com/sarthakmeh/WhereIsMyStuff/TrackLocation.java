package com.sarthakmeh.WhereIsMyStuff;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.sarthakmeh.WhereIsMyStuff.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;


public class TrackLocation extends Activity implements LocationListener{

	Location mCurrentLocation;
	LocationManager mLocationManager;
	String currentUserLatitude;
	String currentUserLongitude;
	String TAG="WhereIsMyStuff";
	SharedPreferences prefs;
	Editor editPrefs;
	String Latitude,Longitude;
	String provider;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
      if(checkNetworkConn()){
    	Log.d("Connection","Connected");    	
    }else{
    	Toast.makeText(getApplicationContext(), "Please connect to internet and try again", Toast.LENGTH_LONG).show();
    	finish();
    }
        
        prefs=getSharedPreferences("UPDATE_LOCAION",Context.MODE_PRIVATE);
    	editPrefs=prefs.edit(); 
        
        Latitude = getIntent().getExtras().getString("Lat");
        Longitude = getIntent().getExtras().getString("Long");
        
        mLocationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        
        Criteria criteria = new Criteria();
        criteria.setBearingAccuracy(Criteria.ACCURACY_FINE);
        provider = mLocationManager.getBestProvider(criteria, false);
        Toast.makeText(getApplicationContext(), provider, Toast.LENGTH_SHORT).show();
        mCurrentLocation = mLocationManager.getLastKnownLocation(provider);
        
           currentUserLatitude=Double.toString(mCurrentLocation.getLatitude());
		   currentUserLongitude=Double.toString(mCurrentLocation.getLongitude());
		   
		   editPrefs.putString(Latitude, currentUserLatitude);
		   editPrefs.putString(Longitude, currentUserLongitude);
		   editPrefs.commit();
		   Toast.makeText(getApplicationContext(), "Location Saved", Toast.LENGTH_SHORT).show();
		   
             
        
	}
	
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	         = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null;
	}
	
     boolean checkNetworkConn(){
		
		boolean value=false;
		if (isNetworkAvailable()) {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500); 
                urlc.connect();
                value=urlc.getResponseCode() == 200;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
        	value=false;
        }
		
		return value;
	}
	
	 @Override
	 protected void onStart() {
	        super.onStart();
	        mLocationManager.requestLocationUpdates(provider, 400, 1, this);
	        finish();
	        Log.d(TAG,"LocationClient connected");
	    }
	
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		String msg="Latitude "+location.getLatitude()+" Longitude"+location.getLongitude();
		Toast.makeText(getApplicationContext(),msg ,Toast.LENGTH_LONG).show();
		
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
}
