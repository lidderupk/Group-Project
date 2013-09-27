package com.chaseit.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.chaseit.ParseHelper;
import com.chaseit.R;
import com.chaseit.models.Hunt;
import com.chaseit.models.Location;
import com.chaseit.models.wrappers.UserHuntWrapper;
import com.chaseit.util.Constants;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLngBounds.Builder;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;

public class MapWithConnectedMarkersFragment extends Fragment {
	private static final String tag = "Debug - com.chaseit.fragments.MapWithConnectedMarkersFragment";
	private UserHuntWrapper uhWrapper;
	private GoogleMap googleMap;
	private Builder builder;
	private Button focusOnMarkers;
	private PolylineOptions rectOptions;
	protected List<Location> locationList;

	
	//
	//
	// public static interface OnCompleteListener {
	// public abstract void onComplete();
	// }
	//
	// private OnCompleteListener mListener;
	//
	// public void onAttach(Fragment fragement) {
	// try {
	// this.mListener = (OnCompleteListener)fragement;
	// }
	// catch (final ClassCastException e) {
	// throw new ClassCastException(fragement.toString() +
	// " must implement OnCompleteListener");
	// }
	// }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_maps_with_connected_markers,
						container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Bundle extras = getArguments();
		uhWrapper = (UserHuntWrapper) extras
				.getSerializable(Constants.USER_HUNT_WRAPPER_DATA_NAME);
		Log.d(tag, "huntID: " + uhWrapper.getHuntObjectId());

		ParseHelper.getHuntByObjectId(uhWrapper.getHuntObjectId(),
				new GetCallback<Hunt>() {

					@Override
					public void done(Hunt hunt, ParseException e) {
						if (e == null) {

							ParseHelper.getLocationsforHunt(hunt,
									new FindCallback<Location>() {
										@Override
										public void done(
												List<Location> locationList,
												ParseException e) {
											MapWithConnectedMarkersFragment.this.locationList = locationList;
											if (e == null) {
												List<Location> markerLocationList = new ArrayList<Location>();
												int lastLocationIndex = uhWrapper
														.getLocationIndex();
												for (int i = 0; i <= lastLocationIndex; i++) {
													// draw markers for all
													// locations before and
													// including lastLocation
													markerLocationList
															.add(locationList
																	.get(i));
												}
												setupViews(getView(),
														markerLocationList);
												focusOnMarkers();
											} else {
												Log.d(tag,
														"no locations found for hunt: "
																+ e.getMessage());
											}
										}
									});
						} else {
							Log.d(tag, "hunt not found: " + e.getMessage());
						}

					}
				});
	}

	private void setupViews(View view, List<Location> locationList) {

		focusOnMarkers = (Button) view.findViewById(R.id.btnFocusOnMarkers);
		focusOnMarkers.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				focusOnMarkers();
			}
		});

		try {
			// Loading map
			initilizeMap();

			builder = new LatLngBounds.Builder();
			rectOptions = new PolylineOptions();

			// for (Location loc : locationList) {
			// ParseGeoPoint geoPt = loc.getLocation();
			// LatLng latLngPt = new LatLng(geoPt.getLatitude(),
			// geoPt.getLongitude());
			// googleMap
			//
			// .addMarker(new MarkerOptions()
			// .position(latLngPt)
			// .title(loc.getHint())
			// .icon(BitmapDescriptorFactory
			// .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
			// builder.include(latLngPt);
			//
			// rectOptions.add(latLngPt).width(5).color(Color.BLUE)
			// .geodesic(true);
			// }
			//
			// googleMap.addPolyline(rectOptions);
			//
			// googleMap.setOnCameraChangeListener(new OnCameraChangeListener()
			// {
			//
			// public void onCameraChange(CameraPosition arg0) {
			// focusOnMarkers();
			// }
			//
			// });

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void focusOnMarkers() {
		googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(
				builder.build(), 50));
		googleMap.setOnCameraChangeListener(null);
	}

	/**
	 * function to load map. If map is not created it will create it for you
	 * */
	private void initilizeMap() {
		if (googleMap == null) {
			googleMap = ((SupportMapFragment) getActivity()
					.getSupportFragmentManager().findFragmentById(
							R.id.mapWithMarkers)).getMap();

			// check if map is created successfully or not
			if (googleMap == null) {
				Log.d(tag, "unable to get a handle on google map");
			} else
				googleMap.setMyLocationEnabled(true);
		}
	}

	public void drawMarkersAndPolygon(List<Location> mapPoints) {
		PolylineOptions rectOptions = new PolylineOptions();
		// Collections.sort(mapPoints);
		Builder builder = new LatLngBounds.Builder();
		for (Location point : mapPoints) {
			LatLng ll = new LatLng(point.getLocation().getLatitude(), point
					.getLocation().getLongitude());

			// drop marker first
			googleMap.addMarker(new MarkerOptions()
					.position(ll)
					.title((point.getIndexInHunt() + 1) + ". "
							+ point.getLocationName())
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

			builder.include(ll);
			rectOptions.add(ll).width(5).color(Color.BLUE).geodesic(true);
			googleMap.addPolyline(rectOptions);

			// move the camera to show the whole chase
			googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(
					builder.build(), 10));

		}
	}
}
