//package com.chaseit.activities;
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

//
//import android.app.Activity;
//import android.os.Bundle;
//import android.widget.Toast;
//
//import com.chaseit.R;
//import com.chaseit.util.Constants;
//import com.google.android.gms.common.GooglePlayServicesUtil;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapFragment;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
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
//			LatLng latLongForHunt = new LatLng(Constants.latUnionSquare,
//					Constants.lngUnionSquare);
//			googleMap.addMarker(new MarkerOptions()
//					.position(latLongForHunt)
//					.title("San Francisco")
//					.icon(BitmapDescriptorFactory
//							.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//
//			// LatLngBounds bounds = new LatLngBounds.Builder().include(
//			// latLongForHunt).build();
//			// googleMap.moveCamera(CameraUpdateFactory
//			// .newLatLngBounds(bounds, 50));
//
//			googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLongForHunt));
//
//			// Zoom in the Google Map
//			googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
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

