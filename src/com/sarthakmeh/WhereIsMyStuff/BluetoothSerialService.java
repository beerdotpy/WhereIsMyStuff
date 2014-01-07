package com.sarthakmeh.WhereIsMyStuff;

import java.io.IOException;
import java.util.UUID;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;


public class BluetoothSerialService {
    // Debugging
    private static final String TAG = "BluetoothReadService";
    private static final boolean D = true;


	private static final UUID SerialPortServiceClass_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // Member fields
    private final BluetoothAdapter mAdapter;
    private ConnectThread mConnectThread;
    Context ctx;
        
    public BluetoothSerialService(Context context) {
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        ctx=context;
    }
    


public synchronized void connect(BluetoothDevice device) {
    if (D) Log.d(TAG, "connect to: " + device);

    
     // Start the thread to connect with the given device
    mConnectThread = new ConnectThread(device);
    mConnectThread.start();
   
}

public synchronized void connected(BluetoothSocket socket, BluetoothDevice device) {
    if (D) Log.d(TAG, "connected");

    // Cancel the thread that completed the connection
    if (mConnectThread != null) {
    	mConnectThread.cancel(); 
    	mConnectThread = null;
    }

  

    Toast.makeText(ctx, "Connected", Toast.LENGTH_LONG).show();
    
}


private class ConnectThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;

    public ConnectThread(BluetoothDevice device) {
        mmDevice = device;
        BluetoothSocket tmp = null;

        // Get a BluetoothSocket for a connection with the
        // given BluetoothDevice
        try {
            tmp = device.createRfcommSocketToServiceRecord(SerialPortServiceClass_UUID);
        } catch (IOException e) {
            Log.e(TAG, "create() failed", e);
        }
        mmSocket = tmp;
    }
    
    public void run() {
        Log.i(TAG, "BEGIN mConnectThread");
        setName("ConnectThread");

        // Always cancel discovery because it will slow down a connection
        mAdapter.cancelDiscovery();

        // Make a connection to the BluetoothSocket
        try {
            // This is a blocking call and will only return on a
            // successful connection or an exception
            mmSocket.connect();
        } catch (IOException e) {
       // 	Toast.makeText(ctx, "Connection Lost", Toast.LENGTH_LONG).show();
            // Close the socket
            try {
                mmSocket.close();
            } catch (IOException e2) {
                Log.e(TAG, "unable to close() socket during connection failure", e2);
            }
            // Start the service over to restart listening mode
            //BluetoothSerialService.this.start();
            return;
        }

        // Reset the ConnectThread because we're done
        synchronized (BluetoothSerialService.this) {
            mConnectThread = null;
        }

      //  Toast.makeText(ctx, "Connection Made", Toast.LENGTH_LONG).show();
        connected(mmSocket, mmDevice);
      }

    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
            Log.e(TAG, "close() of connect socket failed", e);
        }
    }
}



}