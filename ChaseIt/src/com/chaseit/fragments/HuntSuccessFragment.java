package com.chaseit.fragments;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.chaseit.R;
import com.chaseit.activities.HomeScreenActivity;
import com.chaseit.models.wrappers.HuntWrapper;
import com.chaseit.models.wrappers.LocationWrapper;
import com.chaseit.models.wrappers.UserHuntWrapper;
import com.chaseit.util.Constants;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLngBounds.Builder;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class HuntSuccessFragment extends Fragment {

	private static final String tag = "Debug - com.chaseit.fragments.HuntSuccessFragment";
	// wrappers that get passed in from details/summary page
	private UserHuntWrapper uHuntWrapper;
	private HuntWrapper wHunt;
	private ArrayList<LocationWrapper> wLocations;
	private GoogleMap googleMap;
	private Builder builder;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_hunt_success, container,
				false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		loadViewElements(getView());
		setupExtras();
		drawMarkersAndPolygon(wLocations, true);
	}

	private void setupExtras() {
		// get the user-hunt wrapper hunt wrapper and all the wrapped locations
		// for this hunt
		Bundle extras = getActivity().getIntent().getExtras();
		uHuntWrapper = (UserHuntWrapper) extras
				.getSerializable(Constants.USER_HUNT_WRAPPER_DATA_NAME);
		wHunt = (HuntWrapper) extras
				.getSerializable(Constants.HUNT_WRAPPER_DATA_NAME);
		Bundle locationBundle = extras
				.getBundle(Constants.LOCATIONS_BUNDLE_DATA_NAME);
		wLocations = locationBundle
				.getParcelableArrayList(Constants.LOCATIONS_WRAPPER_DATA_NAME);
	}

	private void loadViewElements(View view) {
		ImageButton ibHuntSuccessFinish = (ImageButton) view
				.findViewById(R.id.ibHuntSuccessFinish);
		ibHuntSuccessFinish.setOnClickListener(getHuntSuccessFinishListener());
		// Button btnFocusOnMarkers = (Button) view
		// .findViewById(R.id.btnFocusOnMarkers);
		// btnFocusOnMarkers.setOnClickListener(getFocusButtonClickListener());
		initilizeMap();
	}

	// private OnClickListener getFocusButtonClickListener() {
	// return new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// if (googleMap == null || wLocations == null)
	// return;
	//
	// drawMarkersAndPolygon(wLocations, true);
	// }
	// };
	// }

	private OnClickListener getHuntSuccessFinishListener() {
		return new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent in = new Intent(getActivity().getBaseContext(),
						HomeScreenActivity.class);
				startActivity(in);
			}
		};
	}

	/**
	 * function to load map. If map is not created it will create it for you
	 * */
	private void initilizeMap() {
		if (googleMap == null) {
			googleMap = ((SupportMapFragment) getActivity()
					.getSupportFragmentManager().findFragmentById(
							R.id.successHuntMap)).getMap();

			// check if map is created successfully or not
			if (googleMap == null) {
				Log.d(tag, "unable to get a handle on google map");
			} else {
				googleMap.setMyLocationEnabled(true);

				googleMap.setMyLocationEnabled(false);
				UiSettings mapUiSettings = googleMap.getUiSettings();
				// make this a non interactive map
				mapUiSettings.setAllGesturesEnabled(false);

				googleMap
						.setOnCameraChangeListener(new OnCameraChangeListener() {

							@Override
							public void onCameraChange(CameraPosition arg0) {
								// Move camera.
								googleMap.animateCamera(CameraUpdateFactory
										.newLatLngBounds(builder.build(), 50));
								// Remove listener to prevent position reset on
								// camera move.
								googleMap.setOnCameraChangeListener(null);
							}
						});

			}
		}
	}

	public void drawMarkersAndPolygon(List<LocationWrapper> mapPoints,
			boolean animate) {
		if (mapPoints == null || mapPoints.size() == 0) {
			Log.d("DEBUG", "Nothing to draw");
			return;
		}

		PolylineOptions rectOptions = new PolylineOptions();
		builder = new LatLngBounds.Builder();
		for (LocationWrapper point : mapPoints) {
			LatLng ll = point.getLocation();

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
		}
	}
}
