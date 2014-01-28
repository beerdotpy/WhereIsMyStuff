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
              
                    
	}
	
	private OnItemClickListener openBluetoothSettings = new OnItemClickListener() {
		
		@Override
		public void onItemClick(AdapterView<?> Aview, View view, int arg2,
				long arg3) {
			
			// TODO Auto-generated method stub
			
			mBtAdapter.cancelDiscovery();
			Intent intentBluetooth = new Intent();
		    intentBluetooth.setAction(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS);
		    startActivity(intentBluetooth);
		    finish();

			
		}
       };
       
	
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG,"reciever registered");
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
								
					Toast.makeText(getApplicationContext(), "Bluetooth is On", Toast.LENGTH_SHORT).show();
					mBtAdapter.startDiscovery();
					Toast.makeText(getApplicationContext(), "Bluetooth is in discovering mode", Toast.LENGTH_LONG).show();
                    dispalyPairedDevices();
				       
					   IntentFilter newDeviceFound = new IntentFilter(BluetoothDevice.ACTION_FOUND);
				       this.registerReceiver(mReceiver, newDeviceFound);
		            
			}else{
			 Intent bluetoothOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			 startActivityForResult(bluetoothOn,REQUEST_ENABLE_BT);
		}
				
	}
		
		
	}

	@Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  // TODO Auto-generated method stub
		
		if(resultCode==RESULT_OK){
	  if(requestCode == REQUEST_ENABLE_BT){
	   CheckBluetoothState();
	  }
	  }else if(resultCode==RESULT_CANCELED){
		  startActivity(new Intent(PairedDevices.this,HomeScreen.class));
		  finish();
	  }
  }   
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_screen, menu);
		return true;
	}

//	
//	@Override
//    public void onBackPressed() {
//       Log.d("CDA", "onBackPressed Called");
//       //unregisterReceiver(mReceiver);
//    }
	
}
