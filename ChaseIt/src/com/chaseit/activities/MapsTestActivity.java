//package com.chaseit.activities;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.widget.Toast;
//
//import com.chaseit.R;
//import com.google.android.gms.common.GooglePlayServicesUtil;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapFragment;
//
//public class MapsTestActivity extends Activity {
//
//	// Google Map
//	private GoogleMap googleMap;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_maps_test);
//		GooglePlayServicesUtil
//				.isGooglePlayServicesAvailable(getApplicationContext());
//		try {
//			// Loading map
//			initilizeMap();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	/**
//	 * function to load map. If map is not created it will create it for you
//	 * */
//	private void initilizeMap() {
//		if (googleMap == null) {
//			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
//					R.id.map)).getMap();
//
//			// check if map is created successfully or not
//			if (googleMap == null) {
//				Toast.makeText(getApplicationContext(),
//						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
//						.show();
//			}
//		}
//	}
//
//	@Override
//	protected void onResume() {
//		super.onResume();
//		initilizeMap();
//	}
//}
