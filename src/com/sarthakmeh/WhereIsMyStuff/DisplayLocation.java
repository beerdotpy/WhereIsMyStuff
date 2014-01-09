package com.sarthakmeh.WhereIsMyStuff;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;



public class DisplayLocation extends FragmentActivity implements GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener {
	
	GoogleMap googleMap;
	static final LatLng HAMBURG = new LatLng(53.558, 9.927);
	static final LatLng KIEL = new LatLng(53.551, 9.993);
	LocationClient mLocationClient;
	Location mCurrentLocation;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapvie);   
       
               mLocationClient=new LocationClient(this, this, this);                    

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
      Log.d("Location",Double.toString(mCurrentLocation.getLatitude()));
      Log.d("Location",Double.toString(mCurrentLocation.getLongitude()));
		Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Disconnected. Please re-connect.",
                Toast.LENGTH_SHORT).show();
		
	}          
	}

