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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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

public class DisplayLocation extends Activity  {
	
	LocationClient mLocationClient;
	Location mCurrentLocation;
	LocationRequest mLocationRequest;
	String lat1,lat2;
	String long1,long2;
	String TAG="WhereIsMyStuff";
	TextView userLastLocation,userCurrentLocation;
	Button navigate;
	ProgressBar mActivityIndicator;
	SharedPreferences prefs;
	Editor editPrefs;
	String lastUserLatitude;
	String lastUserLongitude;
	boolean locationChanged=false;
		
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showlocation); 
             
        userCurrentLocation=(TextView) findViewById(R.id.currentLocation);
        userLastLocation=(TextView) findViewById(R.id.lastLocation);
        mActivityIndicator=(ProgressBar) findViewById(R.id.address_progress);
        navigate=(Button) findViewById(R.id.navigate);
        
        prefs=getSharedPreferences("UPDATE_LOCAION",Context.MODE_PRIVATE);
    	editPrefs=prefs.edit();
    	
    	
    	lat1=prefs.getString("PairedLatitude",null);
    	long1=prefs.getString("PairedLongitude",null);
    	lat2=prefs.getString("UnPairedLatitude",null);
    	long2=prefs.getString("UnPairedLongitude",null);
    	
    	new GetAddressTask(this).execute(lat1,long1,lat2,long2);
        
       
               navigate.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
				
					 
					
					Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
			        	    Uri.parse("http://maps.google.com/maps?saddr="+lat2+
			        	    		", "+long2+"&daddr="+lat1+", "+long1));
			        	startActivity(intent);
					
				}
			});


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
		
	  if(str[0]!=null && str[1]!=null && str[2]!=null && str[3]!=null){
		  
		try {
			lastAddress = geocoder.getFromLocation(Double.parseDouble(str[0]),
			        Double.parseDouble(str[1]), 1);
			currentAddress = geocoder.getFromLocation(Double.parseDouble(str[2]),
			        Double.parseDouble(str[3]), 1);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(NullPointerException e){
			
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
	  }else{
		  Log.d(TAG,"Null Pointer");
	  }
		return true;
   }
	
	@Override
	protected void onPostExecute(Boolean b) {
       // Set activity indicator visibility to "gone"
		
		if(currentAddressText==null && lastAddressText==null){
          
			Toast.makeText(getApplicationContext(), "Location not saved .Some problem occured.",Toast.LENGTH_LONG).show();
			startActivity(new Intent(DisplayLocation.this,HomeScreen.class));
            finish();		
		}else{
			
		       userCurrentLocation.setText(currentAddressText);
		       userLastLocation.setText(lastAddressText);
		       mActivityIndicator.setVisibility(View.GONE);		}
   }
	 
	 }

     
}

