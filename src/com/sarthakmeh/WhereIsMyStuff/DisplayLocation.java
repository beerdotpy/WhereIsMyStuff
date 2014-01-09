package com.sarthakmeh.WhereIsMyStuff;


import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;



public class DisplayLocation extends FragmentActivity {
	
	GoogleMap googleMap;
	static final LatLng HAMBURG = new LatLng(53.558, 9.927);
	static final LatLng KIEL = new LatLng(53.551, 9.993);
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapvie);   
        try {
            // Loading map
      //      initilizeMap();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
        	    Uri.parse("http://maps.google.com/maps?saddr=53.558, 9.927&daddr=53.551, 9.993"));
        	startActivity(intent);
               
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
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }          
	}

