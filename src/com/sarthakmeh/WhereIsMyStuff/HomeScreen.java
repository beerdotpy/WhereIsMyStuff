package com.sarthakmeh.WhereIsMyStuff;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

public class HomeScreen extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homescreen);
   
		mCountDown.start();
	
		
	}

	protected CountDownTimer mCountDown = new CountDownTimer(3000, 1000)
    {

        @Override
        public void onTick(long millisUntilFinished)
        {
        	
        	Log.d("Timer","time for 10sec");
        	       	            
        	        }

        @Override
        public void onFinish()
        {   
        	Log.d("Timer","3mins finish");
        	
        		
        		Intent intent_homescreen=new Intent(HomeScreen.this,PairedDevices.class);
        		
        	    Intent serviceIntent = new Intent(HomeScreen.this, StartupService.class);
        		HomeScreen.this.startService(serviceIntent);
    			
        		startActivity(intent_homescreen);
    			finish();
    		        	
        	}
        };

}
