package com.chaseit.fragments;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.io.FilenameUtils;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;

import com.chaseit.ParseHelper;
import com.chaseit.R;
import com.chaseit.activities.CreateChaseLocationsActivity;
import com.chaseit.activities.HomeScreenActivity;
import com.chaseit.models.Hunt;
import com.chaseit.models.HuntImage;
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
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.SaveCallback;

public class HuntSuccessFragment extends Fragment {

	private static final String tag = "Debug - com.chaseit.fragments.HuntSuccessFragment";
	// wrappers that get passed in from details/summary page
	
	private static final int TAKE_PHOTO_CODE = 1;
	private static final int CROP_PHOTO_CODE = 3;

	private String photoUri;
	private Bitmap photoBitmap;

	
	private UserHuntWrapper uHuntWrapper;
	private HuntWrapper wHunt;
	private ArrayList<LocationWrapper> wLocations;
	private GoogleMap googleMap;
	private Builder builder;
	private RatingBar rbHuntSuccessRate;
	private Hunt chase;
	private ParseFile photoFile;
	private String photoFileName;
	private double huntRating = 3.1;
	private ImageButton ibHuntSuccessImages;

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
		ParseHelper.getHuntByObjectId(uHuntWrapper.getHuntObjectId(), new GetCallback<Hunt>() {

			@Override
			public void done(Hunt object, ParseException e) {
				if(e == null) {
					chase = object;
				} else {
					e.printStackTrace();
				}
			}
		});

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
		ibHuntSuccessImages = (ImageButton)view.findViewById(R.id.ibHuntSuccessImages);
		ibHuntSuccessImages.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				File photoFile = getOutputMediaFile(); // create a file to save the
														// image
				i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile)); // set the
																				// image
																				// file
																				// name
				photoUri = photoFile.getAbsolutePath();
				startActivityForResult(i, TAKE_PHOTO_CODE);
			}
		});
		
		rbHuntSuccessRate = (RatingBar) view.findViewById(R.id.rbHuntSuccessRate);
		rbHuntSuccessRate.setRating((float)huntRating);
		rbHuntSuccessRate.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				huntRating = rating + 0.1; 
				ParseHelper.rateHunt(chase, huntRating, null);
			}
		});
		
		initilizeMap();
	}

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
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == TAKE_PHOTO_CODE) {
				cropPhoto();
			} else if (requestCode == CROP_PHOTO_CODE) {
				photoBitmap = data.getParcelableExtra("data");
				photoFileName = FilenameUtils.getName(photoUri);
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				photoBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
				byte[] scaledData = bos.toByteArray();
				photoFile = new ParseFile(photoFileName, scaledData);
				HuntImage chaseImage = new HuntImage();
				chaseImage.setHunt(chase);
				chaseImage.setImage(photoFile);
				chaseImage.saveInBackground(new SaveCallback() {
					@Override
					public void done(ParseException e) {
						if (e == null) {
							//nothing to do here
						} else {
							e.printStackTrace();
						}
					}
				});
			}
		}
	}

	private static File getOutputMediaFile() {
		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"chaseit");
		if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
			return null;
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)
				.format(new Date());
		File mediaFile = new File(mediaStorageDir.getPath() + File.separator
				+ "IMG_" + timeStamp + ".jpg");

		return mediaFile;
	}

	private void cropPhoto() {
		// call the standard crop action intent (the user device may not support
		// it)
		Intent cropIntent = new Intent("com.android.camera.action.CROP");
		// indicate image type and Uri
		cropIntent.setDataAndType(Uri.fromFile(new File(photoUri)), "image/*");
		// set crop properties
		cropIntent.putExtra("crop", "true");
		// indicate aspect of desired crop
		cropIntent.putExtra("aspectX", 1);
		cropIntent.putExtra("aspectY", 1);
		// indicate output X and Y
		cropIntent.putExtra("outputX", 300);
		cropIntent.putExtra("outputY", 300);
		// retrieve data on return
		cropIntent.putExtra("return-data", true);
		// start the activity - we handle returning in onActivityResult
		startActivityForResult(cropIntent, CROP_PHOTO_CODE);
	}

}
