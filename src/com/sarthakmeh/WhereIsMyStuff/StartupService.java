package com.sarthakmeh.WhereIsMyStuff;

import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;
import android.view.ViewDebug.FlagToString;
import android.widget.Toast;

public class StartupService extends Service {
	
	String TAG="Where Is My Stuff";

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();

		Log.i(TAG, "Service Started");
		
	   IntentFilter btConnected = new IntentFilter();
	   btConnected.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
	   registerReceiver(btReceiver, btConnected);
	   
	   IntentFilter btDisConnected = new IntentFilter(BluetoothDevice.ACTION_ACL_DISCONNECTED);
	   registerReceiver(btReceiver, btDisConnected);
	   
	   IntentFilter connectInternet = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
	   registerReceiver(internetReceiver, connectInternet);
	   
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		Log.i(TAG, "Service is Running");
		IntentFilter btConnected = new IntentFilter();
		   btConnected.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
		   registerReceiver(btReceiver, btConnected);
		   
		   IntentFilter btDisConnected = new IntentFilter(BluetoothDevice.ACTION_ACL_DISCONNECTED);
		   registerReceiver(btReceiver, btDisConnected);
		   
		   IntentFilter connectInternet = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
		   registerReceiver(internetReceiver, connectInternet);
		   
		   return super.onStartCommand(intent, flags, startId);
	}
	
	private final BroadcastReceiver internetReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			
			ConnectivityManager connectivityManager = (ConnectivityManager) 
                    context.getSystemService(Context.CONNECTIVITY_SERVICE );
           NetworkInfo activeNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
          boolean isConnected = activeNetInfo != null && activeNetInfo.isConnectedOrConnecting();   
          
          WifiManager wifi = (WifiManager)getSystemService(Context.WIFI_SERVICE);
          

           if (isConnected || wifi.isWifiEnabled()){
        	   
             Log.i("NET", "connected" );
           }
            else{ 
            	Log.d("NET","disconnected");
            	 //check if bluetooth is paired .
            	//if paired then request to enable internet.
            
            }
		}
	};
	
	private final BroadcastReceiver btReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
		    String action = intent.getAction();
		    
		    Vibrator vib=(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		   
		    // When discovery finds a device
		    if (BluetoothDevice.ACTION_ACL_CONNECTED.equalsIgnoreCase(action)) {
		        // Get the BluetoothDevice object from the Intent
		        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
		        Toast.makeText(context, device.getName()+" Connected", Toast.LENGTH_LONG).show();
		        vib.vibrate(1000);
		        Intent gps=new Intent(StartupService.this,TrackLocation.class);
			    gps.putExtra("Lat","PairedLatitude");
			    gps.putExtra("Long","PairedLongitude");
			    gps.putExtra("Device","Connected");
			    gps.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		        context.startActivity(gps);
		        Log.d(TAG,"Device Connected");
		        
		    }if(BluetoothDevice.ACTION_ACL_DISCONNECTED.equalsIgnoreCase(action)) {
		
		    	vib.vibrate(1500);
		    	Toast.makeText(context, "Device Disconnected", Toast.LENGTH_LONG).show();
		    	Intent gps=new Intent(StartupService.this,TrackLocation.class);
			    gps.putExtra("Lat","UnPairedLatitude");
			    gps.putExtra("Long","UnPairedLongitude");
			    gps.putExtra("Device","DisConnected");
			    gps.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		        context.startActivity(gps);
		        
		    }
	}
	};


}
