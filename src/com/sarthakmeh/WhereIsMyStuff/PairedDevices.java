package com.sarthakmeh.WhereIsMyStuff;

import java.util.ArrayList;
import java.util.Set;

import android.os.Bundle;
import android.os.Message;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PairedDevices extends Activity {

	ArrayList<BlueTooth> pairedDeviceList = new ArrayList<BlueTooth>();
	ArrayList<BlueTooth> foundDeviceList = new ArrayList<BlueTooth>();
	BluetoothDeviceAdapter btAdapter;
	BluetoothAdapter mBtAdapter;
	int REQUEST_ENABLE_BT = 1;
	String TAG="WhereIsMyStuff";
	int pid=1;
	int fid=1;
	ListView paired_bluetoothList;
	ListView found_bluetoothList;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_paireddevices);
	
	   paired_bluetoothList = (ListView) findViewById(R.id.pairedlistview);
	   found_bluetoothList = (ListView) findViewById(R.id.foundlistview);
	   
	   paired_bluetoothList.setOnItemClickListener(openBluetoothSettings);
	   found_bluetoothList.setOnItemClickListener(openBluetoothSettings);
	   
	   mBtAdapter=BluetoothAdapter.getDefaultAdapter();
		
       CheckBluetoothState();
       
       
       Intent serviceIntent = new Intent(PairedDevices.this, StartupService.class);
	   PairedDevices.this.startService(serviceIntent);
       
	   mBtAdapter.startDiscovery();
	   Toast.makeText(getApplicationContext(), "Searching for devices", Toast.LENGTH_LONG).show();
	   
	   IntentFilter newDeviceFound = new IntentFilter(BluetoothDevice.ACTION_FOUND);
       this.registerReceiver(mReceiver, newDeviceFound);
       
	}
	
	private OnItemClickListener openBluetoothSettings = new OnItemClickListener() {
		
		@Override
		public void onItemClick(AdapterView<?> Aview, View view, int arg2,
				long arg3) {
			
			// TODO Auto-generated method stub
			
			mBtAdapter.cancelDiscovery();
		
			final Intent intent = new Intent(Intent.ACTION_MAIN, null);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            final ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.bluetoothSettings");
            intent.setComponent(cn);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity( intent);

			
		}
       };
       
	
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // If it's already paired, skip it, because it's been listed already
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    BlueTooth foundDevice=new BlueTooth();
                    String deviceName=device.getName();
                    foundDevice.set_device_name(deviceName);
                    String deviceAdd=device.getAddress();
                    foundDevice.set_device_address(deviceAdd);
                    foundDevice.set_id(fid);
                    fid++;
            
                    foundDeviceList.add(foundDevice);
                    
                }
            }
            btAdapter=new BluetoothDeviceAdapter(PairedDevices.this,foundDeviceList);
            found_bluetoothList.setAdapter(btAdapter);
        }
	};	
	
	void dispalyPairedDevices(){
		
		   Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();
		   
		   if (pairedDevices.size() > 0) {
		       for (BluetoothDevice device : pairedDevices) { 
		    	BlueTooth pairedDevice=new BlueTooth();
		        String deviceBTName = device.getName();
		        pairedDevice.set_device_name(deviceBTName);
		        String deviceBTaddress=device.getAddress();
		        pairedDevice.set_device_address(deviceBTaddress);
		        Log.d("Device Name Device Address",deviceBTName+deviceBTaddress);
		        pairedDevice.set_id(pid);
		        pid++;
		        
		        pairedDeviceList.add(pairedDevice);
		        btAdapter=new BluetoothDeviceAdapter(PairedDevices.this,pairedDeviceList);
		        paired_bluetoothList.setAdapter(btAdapter);
		       }
		   }else{
			   
			   Toast.makeText(this, "No Paired Devices Found", Toast.LENGTH_LONG).show();
			   Log.d(TAG,"No device found");
		   }
			
		   
		   
		   
	}
	
	
	
	
	void CheckBluetoothState(){
		
		if(mBtAdapter==null){
			Toast.makeText(getApplicationContext(), "Bluetooth Not supported on this device", Toast.LENGTH_LONG).show();
		}else{
			if(mBtAdapter.isEnabled()){
				if(mBtAdapter.isDiscovering()){
					Toast.makeText(getApplicationContext(), "Bluetooth is in discovering mode", Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(getApplicationContext(), "Bluetooh is On", Toast.LENGTH_LONG).show();
					dispalyPairedDevices();
				}
				
			}else{
			 Intent bluetoothOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			 startActivityForResult(bluetoothOn,REQUEST_ENABLE_BT);
		}
		
		
	}
	}

	@Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  // TODO Auto-generated method stub
	  if(requestCode == REQUEST_ENABLE_BT){
	   CheckBluetoothState();
	  }
  }   
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home_screen, menu);
		return true;
	}

}
