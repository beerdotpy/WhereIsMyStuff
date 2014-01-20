//package com.sarthakmeh.WhereIsMyStuff;
//
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapFragment;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.widget.Toast;
//
//public class Temp extends FragmentActivity {
//
//	//private GoogleMap googleMap;
//	//double[] lat1={1.1,2.2};
////	=28.7034327,lat2=28.7023049,long1=77.1490096,long2=77.1493722;
//	 
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_temp);
// 
//        
//        
//        try {
//            // Loading map
//            initilizeMap();
// 
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        
//    //    MarkerOptions mk=new MarkerOptions().position(new LatLng(lat1,long1)).title("P1");
//        //googleMap.addMarker(mk);
//    //    MarkerOptions mk1=new MarkerOptions().position(new LatLng(lat2,long2)).title("P2");
//      //  googleMap.addMarker(mk1);
// 
//    }
// 
//    /**
//     * function to load map. If map is not created it will create it for you
//     * */
//    private void initilizeMap() {
//        if (googleMap == null) {
//            googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(
//                    R.id.map)).getMap();
// 
//            // check if map is created successfully or not
//            if (googleMap == null) {
//                Toast.makeText(getApplicationContext(),
//                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
//                        .show();
//            }
//        }
//    }
// 
//    @Override
//    protected void onResume() {
//        super.onResume();
//        initilizeMap();
//    }
// 
//}
//
