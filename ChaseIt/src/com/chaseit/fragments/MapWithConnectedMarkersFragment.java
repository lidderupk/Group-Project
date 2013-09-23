package com.chaseit.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.chaseit.R;
import com.chaseit.models.Location;
import com.chaseit.util.Constants;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLngBounds.Builder;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.parse.ParseGeoPoint;

public class MapWithConnectedMarkersFragment extends Fragment {
	private static final String tag = "Debug - com.chaseit.fragments.MapWithConnectedMarkersFragment";
	private String huntId;
	private GoogleMap googleMap;
	private Builder builder;
	private Button focusOnMarkers;
	private PolylineOptions rectOptions;
	private Polyline polyline;

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
		huntId = extras.getString(Constants.HUNT_ID);
		Log.d(tag, "huntID: " + huntId);
		setupViews(getView(), getFakeLocations());
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

			for (Location loc : locationList) {
				ParseGeoPoint geoPt = loc.getLocation();
				LatLng latLngPt = new LatLng(geoPt.getLatitude(),
						geoPt.getLongitude());
				googleMap

						.addMarker(new MarkerOptions()
								.position(latLngPt)
								.title(loc.getHint())
								.icon(BitmapDescriptorFactory
										.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
				builder.include(latLngPt);

				rectOptions.add(latLngPt).width(5).color(Color.BLUE)
						.geodesic(true);
			}

			polyline = googleMap.addPolyline(rectOptions);

			googleMap.setOnCameraChangeListener(new OnCameraChangeListener() {

				public void onCameraChange(CameraPosition arg0) {
					focusOnMarkers();
				}

			});

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
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.mapWithMarkers)).getMap();

			// check if map is created successfully or not
			if (googleMap == null) {
				Log.d(tag, "unable to get a handle on google map");
			}
		}
	}

	private LatLng getLatLongForHunt(String huntId) {
		return new LatLng(Constants.latUnionSquare, Constants.lngUnionSquare);
	}

	private List<Location> getFakeLocations() {
		List<Location> list = new ArrayList<Location>();

		Location loc = new Location();
		loc.setLocation(new ParseGeoPoint(Constants.latJapanTown,
				Constants.lngJapanTown));
		loc.setLocationName("Japan Town");
		loc.setHint("Japan Town Hint");
		list.add(loc);

		loc = new Location();
		loc.setLocation(new ParseGeoPoint(Constants.latRussianHill,
				Constants.lngRussianHill));
		loc.setLocationName("Russian Hill");
		loc.setHint("Russian Hill Hint");
		list.add(loc);

		loc = new Location();
		loc.setLocation(new ParseGeoPoint(Constants.latRichmond,
				Constants.lngRichmond));
		loc.setLocationName("Richmond");
		loc.setHint("Richmond Hint");
		list.add(loc);

		loc = new Location();
		loc.setLocation(new ParseGeoPoint(Constants.latMission,
				Constants.lngMission));
		loc.setLocationName("Mission");
		loc.setHint("Mission Hint");
		list.add(loc);

		return list;
	}

}
