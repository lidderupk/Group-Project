//package com.chaseit.fragments;
//
//import android.app.Fragment;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.chaseit.R;
//import com.chaseit.util.Constants;
//
//public class HuntPlayFragment extends Fragment {
//
//	private static final String tag = "Debug - com.chaseit.fragments.HuntPlayFragment";
//	private String huntId;
////	private GoogleMap gMap;
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		View view = inflater.inflate(R.layout.fragment_hunt_play, container,
//				false);
//		return view;
//	}
//
//	@Override
//	public void onActivityCreated(Bundle savedInstanceState) {
//		super.onActivityCreated(savedInstanceState);
//		Bundle extras = getArguments();
//		huntId = extras.getString(Constants.HUNT_ID);
//		Log.d(tag, "huntID: " + huntId);
////		LatLng latLongForHunt = getLatLongForHunt(huntId);
//
//		setupViews(getView(), latLongForHunt);
//	}
//
//	private void setupViews(View view, LatLng latLongForHunt) {
//		try {
//			// Loading map
//			initilizeMap();
//
//			Log.d(tag, "adding markers");
//
//			gMap.addMarker(new MarkerOptions()
//					.position(latLongForHunt)
//					.title("San Francisco")
//					.icon(BitmapDescriptorFactory
//							.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//
//			gMap.moveCamera(CameraUpdateFactory.newLatLng(latLongForHunt));
//
//			// Zoom in the Google Map
//			gMap.animateCamera(CameraUpdateFactory.zoomTo(15));
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/*
//	 * returns the lat and long given the huntid
//	 */
//	private LatLng getLatLongForHunt(String huntId) {
//		return new LatLng(Constants.latUnionSquare, Constants.lngUnionSquare);
//	}
//
//	private void addMarkerToMap(LatLng latLng) {
//
//	}
//
//	/**
//	 * function to load map. If map is not created it will create it for you
//	 * */
//	private void initilizeMap() {
//		if (gMap == null) {
//			gMap = ((MapFragment) getFragmentManager().findFragmentById(
//					R.id.huntMap)).getMap();
//
//			// check if map is created successfully or not
//			if (gMap == null) {
//				Log.d(tag, "Sorry! unable to create maps");
//			}
//		}
//	}
//}
