package com.sarthakmeh.WhereIsMyStuff;




import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HomeScreen extends Activity {
	
	Button connectDevice,getLocation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homescreen);
   
	connectDevice=(Button) findViewById(R.id.btconnect);
	getLocation=(Button) findViewById(R.id.getlocation);
	
		
	connectDevice.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			startActivity(new Intent(HomeScreen.this,PairedDevices.class));	
		}
	});
	
getLocation.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			startActivity(new Intent(HomeScreen.this,DisplayLocation.class));	
		}
	});
	
	
	}

	
}
