package com.chaseit.fragments;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chaseit.ParseHelper;
import com.chaseit.R;
import com.chaseit.activities.HuntShowImageActivity;
import com.chaseit.models.Hunt;
import com.chaseit.models.Location;
import com.chaseit.models.UserHunt;
import com.chaseit.models.UserHunt.HuntStatus;
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
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.SaveCallback;

public class HuntPlayFragment extends Fragment {

	private static final String tag = "Debug - com.chaseit.fragments.HuntPlayFragment";

	private GoogleMap googleMap;
	private Builder builder;
	private Button focusOnMarkers;

	private UserHuntWrapper uHuntWrapper;
	private UserHunt userHunt;
	private ParseImageView ivHuntImageClue;

	Location targetLocation;
	Location currentLocation;
	private int lastLocationIndex;
	private int currentIndex;

	private List<Location> locationList;
	private String hint;
	private ParseFile image;

	private TextView tvHuntMessageClue;

	private Button btnHuntProgressCheck;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_hunt_play, container,
				false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// setupViews(getView());

		Bundle extras = getArguments();
		uHuntWrapper = (UserHuntWrapper) extras
				.getSerializable(Constants.USER_HUNT_WRAPPER_DATA_NAME);

		userHunt = ParseHelper.getHuntInProgressByIdBlocking(uHuntWrapper
				.getObjectId());
		getHunt();
		// ParseHelper.getHuntInProgressById(uHuntWrapper.getObjectId(),
		// new GetCallback<UserHunt>() {
		//
		// @Override
		// public void done(UserHunt object, ParseException e) {
		// userHunt = object;
		//
		// getHunt();
		// }
		// });
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setupViews(getView());
		drawMarkersAndPolygon(locationList.subList(0, currentIndex + 1));
	}

	private void getHunt() {
		Hunt hunt = ParseHelper.getHuntByObjectIdBlocking(uHuntWrapper
				.getHuntObjectId());
		locationList = ParseHelper.getLocationsforHuntBlocking(hunt);

		if (locationList == null || locationList.size() < 1) {
			lastLocationIndex = uHuntWrapper.getLocationIndex();
			currentIndex = lastLocationIndex;
			currentLocation = locationList.get(lastLocationIndex);
			if (lastLocationIndex == 0) {
				// just started
				currentLocation = targetLocation = locationList
						.get(lastLocationIndex);
				hint = targetLocation.getHint();
				image = targetLocation.getImage();
			}

			else if (lastLocationIndex == locationList.size() - 1) {

				hint = targetLocation.getHint();
				image = targetLocation.getImage();
				Toast.makeText(getActivity().getBaseContext(), "Hunt is over",
						Toast.LENGTH_SHORT).show();
				btnHuntProgressCheck.setClickable(false);
			} else {
				/*
				 * show the next picture and clue
				 */
				targetLocation = locationList.get(lastLocationIndex + 1);
				hint = targetLocation.getHint();
				image = targetLocation.getImage();

				Toast.makeText(getActivity().getBaseContext(),
						"Hunt is NOT over, next location loaded",
						Toast.LENGTH_SHORT).show();
			}

		} else {
			Log.d(tag, "no locations found for hunt");
		}
	}

	private void setupViews(View view) {
		ivHuntImageClue = (ParseImageView) view
				.findViewById(R.id.ivHuntImageClue);
		ivHuntImageClue.setOnClickListener(getHuntImageClueClickListener());

		tvHuntMessageClue = (TextView) view
				.findViewById(R.id.tvHuntMessageClue);

		setupClues();

		btnHuntProgressCheck = (Button) view
				.findViewById(R.id.btnHuntProgressCheck);
		btnHuntProgressCheck
				.setOnClickListener(getBtnHuntProgressCheckClickListener());

		focusOnMarkers = (Button) view.findViewById(R.id.btnFocusOnMarkers);
		focusOnMarkers.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				focusOnMarkers();
			}
		});

		initilizeMap();
	}

	private void focusOnMarkers() {
		googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(
				builder.build(), 50));
		googleMap.setOnCameraChangeListener(null);
	}

	private void setupClues() {
		ivHuntImageClue.setParseFile(image);

		ivHuntImageClue.loadInBackground(new GetDataCallback() {
			@Override
			public void done(byte[] data, ParseException e) {
				// nothing to do
			}
		});

		tvHuntMessageClue.setText(hint);
	}

	private OnClickListener getBtnHuntProgressCheckClickListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Float distanceFromTarget = getDistanceFromTarget();
				if (distanceFromTarget < 200) {
					Toast.makeText(getActivity().getBaseContext(),
							"Perfect !. Congratulations !!", Toast.LENGTH_SHORT)
							.show();

					/*
					 * if current index is the last index, hunt is complete
					 */
					if (currentIndex == (locationList.size() - 1)) {
						Toast.makeText(getActivity().getBaseContext(),
								"Hunt is complete !. Congratulations !!",
								Toast.LENGTH_SHORT).show();
						/*
						 * update userHunt
						 */
						userHunt.setLastLocationLat(targetLocation
								.getLocation().getLatitude());
						userHunt.setLastLocationLong(targetLocation
								.getLocation().getLongitude());
						userHunt.setLocationIndex(currentIndex);
						userHunt.setHuntStatus(HuntStatus.COMPLETED);
						/*
						 * save the user hunt
						 */
						saveUserHunt(userHunt);

					} else {
						/*
						 * show the next picture and clue
						 */

						currentLocation = targetLocation;
						currentIndex = targetLocation.getIndexInHunt();
						userHunt.setLastLocationLat(currentLocation
								.getLocation().getLatitude());
						userHunt.setLastLocationLong(currentLocation
								.getLocation().getLongitude());
						userHunt.setLastLocationObjectId(currentLocation
								.getObjectId());
						userHunt.setLocationIndex(currentIndex);

						if (currentIndex == locationList.size() - 1) {
							userHunt.setHuntStatus(HuntStatus.COMPLETED);
							Toast.makeText(getActivity().getBaseContext(),
									"Hunt is complete", Toast.LENGTH_SHORT)
									.show();
							btnHuntProgressCheck.setClickable(false);
						}

						else {
							// have not reached the end, more clues left
							targetLocation = locationList.get(currentIndex + 1);
							hint = targetLocation.getHint();
							image = targetLocation.getImage();
							setupClues();

							Toast.makeText(getActivity().getBaseContext(),
									"Hunt is NOT over, next location loaded",
									Toast.LENGTH_SHORT).show();
						}
						saveUserHunt(userHunt);
					}

					drawMarkersAndPolygon(locationList.subList(0,
							currentIndex + 1));
				}

			}
		};
	}

	private void saveUserHunt(UserHunt userHunt) {
		userHunt.saveEventually(new SaveCallback() {

			@Override
			public void done(ParseException e) {
				if (e != null)
					Log.d(tag, e.getMessage());
			}
		});
	}

	private OnClickListener getHuntImageClueClickListener() {
		return new OnClickListener() {

			@Override
			public void onClick(View v) {
				Bitmap bitmap = ((BitmapDrawable) ivHuntImageClue.getDrawable())
						.getBitmap();
				Intent in = new Intent(HuntPlayFragment.this.getActivity()
						.getBaseContext(), HuntShowImageActivity.class);
				in.putExtra(Constants.IMAGE_EXTRA, bitmap);
				startActivity(in);
			}
		};
	}

	public Float getDistanceFromTarget() {
		Float result = null;
		LocationManager mLocationManager = (LocationManager) getActivity()
				.getSystemService(Context.LOCATION_SERVICE);
		android.location.Location location = mLocationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (location == null) {
			Toast.makeText(getActivity().getBaseContext(),
					"Unable to find your location.", Toast.LENGTH_SHORT).show();
		} else {
			float[] results = new float[1];
			android.location.Location.distanceBetween(location.getLatitude(),
					location.getLongitude(), targetLocation.getLocation()
							.getLatitude(), targetLocation.getLocation()
							.getLongitude(), results);
			result = Float.valueOf(results[0]);
			Toast.makeText(getActivity().getBaseContext(),
					"Distance: " + result + " meters", Toast.LENGTH_SHORT)
					.show();
		}
		return result;
	}

	/**
	 * function to load map. If map is not created it will create it for you
	 * */
	private void initilizeMap() {
		if (googleMap == null) {
			googleMap = ((SupportMapFragment) getActivity()
					.getSupportFragmentManager().findFragmentById(R.id.huntMap))
					.getMap();

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
		builder = new LatLngBounds.Builder();
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
