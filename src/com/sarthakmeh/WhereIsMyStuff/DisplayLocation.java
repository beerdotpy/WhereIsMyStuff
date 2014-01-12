package com.sarthakmeh.WhereIsMyStuff;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;



public class DisplayLocation extends FragmentActivity implements GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener,LocationListener, com.google.android.gms.location.LocationListener {
	
	
	LocationClient mLocationClient;
	Location mCurrentLocation;
	LocationRequest mLocationRequest;
	String currentUserLatitude;
	String currentUserLongitude;
	String TAG="WhereIsMyStuff";
	TextView userLastLocation,userCurrentLocation;
	Button navigate;
	ProgressBar mActivityIndicator;
	SharedPreferences prefs;
	Editor editPrefs;
	String lastUserLatitude;
	String lastUserLongitude;
	boolean locationChanged=false;
	
	// Milliseconds per second
    private static final int MILLISECONDS_PER_SECOND = 1000;
    // Update frequency in seconds
    public static final int UPDATE_INTERVAL_IN_SECONDS = 300;
    // Update frequency in milliseconds
    private static final long UPDATE_INTERVAL =
            MILLISECONDS_PER_SECOND * UPDATE_INTERVAL_IN_SECONDS;
    // The fastest update frequency, in seconds
    private static final int FASTEST_INTERVAL_IN_SECONDS = 300;
    // A fast frequency ceiling in milliseconds
    private static final long FASTEST_INTERVAL =
            MILLISECONDS_PER_SECOND * FASTEST_INTERVAL_IN_SECONDS;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showlocation); 
        
        
        userCurrentLocation=(TextView) findViewById(R.id.currentLocation);
        userLastLocation=(TextView) findViewById(R.id.lastLocation);
        mActivityIndicator=(ProgressBar) findViewById(R.id.address_progress);
        navigate=(Button) findViewById(R.id.navigate);
        
        if(checkNetworkConn()){
        	Log.d("Connection","Connected");
        	
        }else{
        	Toast.makeText(getApplicationContext(), "Please connect to internet and try again", Toast.LENGTH_LONG).show();
        	startActivity(new Intent(DisplayLocation.this,HomeScreen.class));
        	finish();
        }
        
        mLocationClient=new LocationClient(this, this, this);
        mLocationRequest=LocationRequest.create();
        
        mLocationRequest.setPriority(
                LocationRequest.PRIORITY_HIGH_ACCURACY);
        // Set the update interval to 5 seconds
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        // Set the fastest update interval to 1 second
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        
        prefs=getSharedPreferences("UPDATE_LOCAION",Context.MODE_PRIVATE);
        
   	 lastUserLatitude=prefs.getString("Latitude",null);
   	 lastUserLongitude=prefs.getString("Longitude",null);
     
   
          
          if(lastUserLatitude==null && lastUserLongitude==null){
				
				
				navigate.setEnabled(false);
				Toast.makeText(getApplicationContext(), "Navigation not available as last location and " +
						"current location same.Please try after some time",Toast.LENGTH_LONG).show();
			}else{
				navigate.setEnabled(true);
			}
      
    	
        
               navigate.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
				
					 
					
					Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
			        	    Uri.parse("http://maps.google.com/maps?saddr="+currentUserLatitude+
			        	    		", "+currentUserLongitude+"&daddr="+lastUserLatitude+", "+lastUserLongitude));
			        	startActivity(intent);
					
				}
			});

//        try {
//            // Loading map
//      //      initilizeMap();
// 
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        
//        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
//        	    Uri.parse("http://maps.google.com/maps?saddr=53.558, 9.927&daddr=53.551, 9.993"));
//        	startActivity(intent);
//               
//        Marker hamburg = googleMap.addMarker(new MarkerOptions().position(HAMBURG)
//                .title("Hamburg"));
//            Marker kiel = googleMap.addMarker(new MarkerOptions()
//                .position(KIEL)
//                .title("Kiel")
//                .snippet("Kiel is cool")
//                .icon(BitmapDescriptorFactory
//                    .fromResource(R.drawable.ic_launcher)));
// 
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
	
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	         = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null;
	}
 
    /**
     * function to load map. If map is not created it will create it for you
     * */
//    private void initilizeMap() {
//        if (googleMap == null) {
//            googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(
//                    R.id.map)).getMap();
// 
//            // check if map is created successfully or not
//            if (googleMap == null) {
//                Toast.makeText(getApplicationContext(),
//                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
//                        .show();
//            }
//        }
//    }
// 
    @Override
    protected void onStart() {
        super.onStart();
        // Connect the client.
        
        mLocationClient.connect();
        Log.d(TAG,"LocationClient connected");
    }
    
    @Override
    protected void onStop() {
               
        super.onStop();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
       
     //   initilizeMap();
    }

    @Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnected(Bundle bundle) {
		// TODO Auto-generated method stub
		Log.d(TAG,"onConnected");
		mCurrentLocation = mLocationClient.getLastLocation();	
		
   currentUserLatitude=Double.toString(mCurrentLocation.getLatitude());
   currentUserLongitude=Double.toString(mCurrentLocation.getLongitude());
   
     if(lastUserLatitude==null && lastUserLongitude==null){
    	 lastUserLatitude=currentUserLatitude;
    	 lastUserLongitude=currentUserLongitude;
     }
     
     new GetAddressTask(this).execute(currentUserLatitude,currentUserLongitude,lastUserLatitude,lastUserLongitude);
     
     mLocationClient.requestLocationUpdates(mLocationRequest, this);
		
		
	}
	
	
	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Disconnected. Please re-connect.",
                Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onLocationChanged(Location location) {
		
		Toast.makeText(getApplicationContext(), "Location Changed", Toast.LENGTH_LONG).show();
		SharedPreferences backgroundPrefs;
		backgroundPrefs=getSharedPreferences("UPDATE_LOCAION",Context.MODE_PRIVATE);
		Editor edit;
		edit=backgroundPrefs.edit();
		
	String userLatitude=Double.toString(location.getLatitude());
	String userLongitude=Double.toString(location.getLongitude());
	    
	    edit.putBoolean("Location Changed",true);
	    edit.putString("Latitude",userLatitude);
	    edit.putString("Longitude",userLongitude);
	    edit.commit();
	}
	
	
	 class GetAddressTask extends AsyncTask<String, Void, Boolean> {
         
   	  Context mContext;
   	  List<Address> currentAddress=null;
   	  List<Address> lastAddress=null;
   	  String currentAddressText;
   	  String lastAddressText;
    
   	  public GetAddressTask(Context context) {
             super();
             mContext = context;
      }
   	  
   	@Override
	protected void onPreExecute(){
   		
   		mActivityIndicator.setVisibility(View.VISIBLE);
   	}  
   	  
	@Override
	protected Boolean doInBackground(String... str){
		
	  Geocoder geocoder =
                new Geocoder(mContext, Locale.getDefault());
		
		try {
			currentAddress = geocoder.getFromLocation(Double.parseDouble(str[0]),
			        Double.parseDouble(str[1]), 1);
			lastAddress = geocoder.getFromLocation(Double.parseDouble(str[2]),
			        Double.parseDouble(str[3]), 1);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (currentAddress != null && lastAddress!=null && lastAddress.size()>0 && currentAddress.size() > 0) {
            // Get the first address
            Address currentAdd = currentAddress.get(0);
            Address lastAdd = lastAddress.get(0);
            /*
             * Format the first line of address (if available),
             * city, and country name.
             */
            currentAddressText = "Current Location :"+String.format(
                    "%s, %s, %s",
                    // If there's a street address, add it
                    currentAdd.getMaxAddressLineIndex() > 0 ?
                            currentAdd.getAddressLine(0) : "",
                    // Locality is usually a city
                    currentAdd.getLocality(),
                    // The country of the address
                    currentAdd.getCountryName());
            
            lastAddressText = "Last Location :"+String.format(
                    "%s, %s, %s",
                    // If there's a street address, add it
                    lastAdd.getMaxAddressLineIndex() > 0 ?
                            currentAdd.getAddressLine(0) : "",
                    // Locality is usually a city
                    lastAdd.getLocality(),
                    // The country of the address
                    lastAdd.getCountryName());
            
            Log.d(TAG,currentAddressText+" "+lastAddressText);
		
            
		
	}
		return true;
    }
	
	@Override
	protected void onPostExecute(Boolean b) {
        // Set activity indicator visibility to "gone"
        mActivityIndicator.setVisibility(View.GONE);
        userCurrentLocation.setText(currentAddressText);
        userLastLocation.setText(lastAddressText);   
        
    }
	 
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

