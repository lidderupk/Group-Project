package com.chaseit.activities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.chaseit.R;
import com.chaseit.fragments.AddPictureFragment;
import com.chaseit.fragments.AddPictureFragment.AddPictureListener;
import com.chaseit.models.CIUser;
import com.chaseit.models.Hunt;
import com.chaseit.models.HuntImage;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.SaveCallback;

public class CreateChaseActivity extends FragmentActivity implements
		AddPictureListener, LocationListener {
	public static int CREATE_CHASE_ACTIVITY_CODE = 10;

	private LocationManager mLocationManager;
	private Geocoder geocode;
	private EditText etAddHeadline;
	private EditText etAddDetails;
	private Spinner sDifficulty;
	private Button btnCreate;
	private ParseFile photoFile;
	private Bitmap bitmapFile;
	private String bitmapFileName;
	private AlertDialog alert;

	// dummy fill in for now
	private ParseGeoPoint startPoint;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_chase);
		getLayoutElements();
		setCreateHuntButtonListener();
		setupLocationInformation();
	}

	private void setupLocationInformation() {
		mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		geocode = new Geocoder(getBaseContext());

		android.location.Location location = mLocationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (location != null
				&& location.getTime() > Calendar.getInstance()
						.getTimeInMillis() - 2 * 60 * 1000) {
			startPoint = new ParseGeoPoint(location.getLatitude(),
					location.getLongitude());
			try {
				List<Address> addresses = geocode.getFromLocation(
						location.getLatitude(), location.getLongitude(), 1);
				if (addresses != null && !addresses.isEmpty()) {
					Toast.makeText(getBaseContext(),
							addresses.get(0).getLocality(), Toast.LENGTH_SHORT)
							.show();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			mLocationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 0, 0, this);
		}

	}

	private void getLayoutElements() {
		etAddHeadline = (EditText) findViewById(R.id.etAddHeadline);
		etAddDetails = (EditText) findViewById(R.id.etAddDetails);
		sDifficulty = (Spinner) findViewById(R.id.sDifficulty);
		btnCreate = (Button) findViewById(R.id.btnCreate);
	}

	private void setCreateHuntButtonListener() {
		btnCreate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final Hunt chase = new Hunt();
				String headline = etAddHeadline.getText().toString();
				String details = etAddDetails.getText().toString();
				int difficulty = sDifficulty.getSelectedItemPosition();
				if (StringUtils.isBlank(headline)
						|| StringUtils.isBlank(details)) {
					showAlertDialog();
				} else {
					chase.setAvgRating(0);
					chase.setCreator(CIUser.getCurrentUser());
					chase.setDetails(details);
					chase.setDifficulty(difficulty);
					chase.setName(headline);
					chase.setNumRatings(0);
					// dummy start location
					if (startPoint != null) {
						chase.setStartLocation(startPoint);
					}

					if (photoFile != null) {
						photoFile.saveInBackground(new SaveCallback() {
							@Override
							public void done(ParseException e) {
								if (e == null) {
									chase.setHuntPicture(photoFile);
									chase.saveInBackground();
									afterChaseCreated(chase, photoFile);
								} else {
									e.printStackTrace();
									afterChaseCreated(null, null);
								}

							}
						});
					} else {
						chase.saveInBackground();
						afterChaseCreated(chase, null);
					}
				}
			}
		});
	}

	private void showAlertDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				CreateChaseActivity.this);
		builder.setMessage(
				"Please add a headline and detail information for your Chase!")
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						alert.dismiss();
					}
				});
		alert = builder.create();
		alert.show();
	}

	private void afterChaseCreated(final Hunt chase, final ParseFile photo) {
		if (chase != null && photo != null) {
			HuntImage chaseImage = new HuntImage();
			chaseImage.setHunt(chase);
			chaseImage.setImage(photo);
			chaseImage.saveInBackground(new SaveCallback() {

				@Override
				public void done(ParseException e) {
					if (e == null) {
						Intent in = new Intent(getBaseContext(),
								CreateChaseLocationsActivity.class);
						in.putExtra("chaseId", chase.getObjectId());
						in.putExtra("chaseName", chase.getName());
						startActivity(in);
					} else {
						e.printStackTrace();
					}
				}
			});
		} else {
			if (chase != null) {
				Intent in = new Intent(getBaseContext(),
						CreateChaseLocationsActivity.class);
				in.putExtra("chaseId", chase.getObjectId());
				in.putExtra("chaseName", chase.getName());
				startActivity(in);
			} else {
				Toast.makeText(getBaseContext(), "Unable to create chase",
						Toast.LENGTH_SHORT).show();
				finish();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_chase, menu);
		return true;
	}

	@Override
	public void onPictureAdded(Fragment fragment) {
		bitmapFile = ((AddPictureFragment) fragment).getPhotoBitmap();
		bitmapFileName = ((AddPictureFragment) fragment).getPhotoName();
		if (bitmapFile != null && bitmapFileName != null) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			bitmapFile.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			byte[] scaledData = bos.toByteArray();
			photoFile = new ParseFile(bitmapFileName, scaledData);
		} else {
			photoFile = null;
			bitmapFileName = null;
			bitmapFile = null;
		}
	}

	@Override
	public void onLocationChanged(android.location.Location location) {
		if (location != null) {
			startPoint = new ParseGeoPoint(location.getLatitude(),
					location.getLongitude());
			mLocationManager.removeUpdates(this);
			try {
				List<Address> addresses = geocode.getFromLocation(
						location.getLatitude(), location.getLongitude(), 1);
				if (addresses != null && !addresses.isEmpty()) {
					Toast.makeText(getBaseContext(),
							addresses.get(0).getLocality(), Toast.LENGTH_SHORT)
							.show();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

}
