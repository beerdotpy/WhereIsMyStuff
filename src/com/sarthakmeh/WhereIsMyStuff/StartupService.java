package com.sarthakmeh.WhereIsMyStuff;

import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;
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
	   
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		Log.i(TAG, "Service is Running");
		IntentFilter btConnected = new IntentFilter();
		   btConnected.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
		   registerReceiver(btReceiver, btConnected);
		   
		   IntentFilter btDisConnected = new IntentFilter(BluetoothDevice.ACTION_ACL_DISCONNECTED);
		   registerReceiver(btReceiver, btDisConnected);
		   
		   return super.onStartCommand(intent, flags, startId);
	}
	
	private final BroadcastReceiver btReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
		    String action = intent.getAction();
		    Vibrator vib=(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		   
		    // When discovery finds a device
		    if (BluetoothDevice.ACTION_ACL_CONNECTED.equalsIgnoreCase(action)) {
		        // Get the BluetoothDevice object from the Intent
		        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
		        Toast.makeText(context, device.getName()+" Connected", Toast.LENGTH_LONG).show();
		        vib.vibrate(500);
		        Log.d(TAG,"Device Connected");
		        
		    }if(BluetoothDevice.ACTION_ACL_DISCONNECTED.equalsIgnoreCase(action)) {
		
		    	vib.vibrate(500);
		    	Toast.makeText(context, "Device Disconnected", Toast.LENGTH_LONG).show();
		    	Log.d(TAG,"Device Disconnected");
		    }
	}
	};


}
