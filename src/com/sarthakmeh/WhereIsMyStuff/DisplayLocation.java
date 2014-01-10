package com.sarthakmeh.WhereIsMyStuff;


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

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;



public class DisplayLocation extends FragmentActivity implements GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener,LocationListener {
	
	GoogleMap googleMap;
	static final LatLng HAMBURG = new LatLng(53.558, 9.927);
	static final LatLng KIEL = new LatLng(53.551, 9.993);
	LocationClient mLocationClient;
	Location mCurrentLocation;
	LocationRequest mLocationRequest;
	String userLocation;
	String newuserLocation;
	
	// Milliseconds per second
    private static final int MILLISECONDS_PER_SECOND = 1000;
    // Update frequency in seconds
    public static final int UPDATE_INTERVAL_IN_SECONDS = 5;
    // Update frequency in milliseconds
    private static final long UPDATE_INTERVAL =
            MILLISECONDS_PER_SECOND * UPDATE_INTERVAL_IN_SECONDS;
    // The fastest update frequency, in seconds
    private static final int FASTEST_INTERVAL_IN_SECONDS = 1;
    // A fast frequency ceiling in milliseconds
    private static final long FASTEST_INTERVAL =
            MILLISECONDS_PER_SECOND * FASTEST_INTERVAL_IN_SECONDS;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapvie);   
       
               mLocationClient=new LocationClient(this, this, this);
               mLocationRequest=LocationRequest.create();
               
               mLocationRequest.setPriority(
                       LocationRequest.PRIORITY_HIGH_ACCURACY);
               // Set the update interval to 5 seconds
               mLocationRequest.setInterval(UPDATE_INTERVAL);
               // Set the fastest update interval to 1 second
               mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

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
 
    /**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(
                    R.id.map)).getMap();
 
            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
 
    @Override
    protected void onStart() {
        super.onStart();
        // Connect the client.
        mLocationClient.connect();
    }
    
    @Override
    protected void onStop() {
        // Disconnecting the client invalidates it.
        mLocationClient.disconnect();
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
		mCurrentLocation = mLocationClient.getLastLocation();
      userLocation="Latitude"+Double.toString(mCurrentLocation.getLatitude())+"Longitude"+
    		  Double.toString(mCurrentLocation.getLongitude());
      mLocationClient.requestLocationUpdates(mLocationRequest, (com.google.android.gms.location.LocationListener) this);
		Toast.makeText(this, userLocation, Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Disconnected. Please re-connect.",
                Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onLocationChanged(Location location) {
		
		newuserLocation="Latitude"+Double.toString(mCurrentLocation.getLatitude())+"Longitude"+
	    		  Double.toString(mCurrentLocation.getLongitude());
		Toast.makeText(getApplicationContext(), newuserLocation, Toast.LENGTH_LONG).show();
		
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

