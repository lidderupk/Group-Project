package com.chaseit.activities;

import java.io.ByteArrayOutputStream;

import org.apache.commons.lang3.StringUtils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.parse.SaveCallback;

public class CreateChaseActivity extends FragmentActivity implements
		AddPictureListener {
	public static int CREATE_CHASE_ACTIVITY_CODE = 10;

	private EditText etAddHeadline;
	private EditText etAddDetails;
	private Spinner sDifficulty;
	private Button btnCreate;
	private ParseFile photoFile;
	private Bitmap bitmapFile;
	private String bitmapFileName;
	private AlertDialog alert;
//	private ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_chase);
		getLayoutElements();
		setCreateHuntButtonListener();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == CreateChaseLocationsActivity.CREATE_CHASE_LOCATIONS_REQ_CODE){
			//destroy this activity
			setResult(resultCode);
			finish();			
		} else {
			super.onActivityResult(requestCode, resultCode, data);
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

//					pDialog = new ProgressDialog(getBaseContext());
//					pDialog.setMessage("Starting Location Selector...");
//					pDialog.setCancelable(false);
//					pDialog.show();

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
//					pDialog.dismiss();
					if (e == null) {
						Intent in = new Intent(getBaseContext(),
								CreateChaseLocationsActivity.class);
						in.putExtra("chaseId", chase.getObjectId());
						in.putExtra("chaseName", chase.getName());
						startActivityForResult(in, CreateChaseLocationsActivity.CREATE_CHASE_LOCATIONS_REQ_CODE);
					} else {
						e.printStackTrace();
					}
				}
			});
		} else {
			if (chase != null) {
//				pDialog.dismiss();
				Intent in = new Intent(getBaseContext(),
						CreateChaseLocationsActivity.class);
				in.putExtra("chaseId", chase.getObjectId());
				in.putExtra("chaseName", chase.getName());
				startActivityForResult(in, CreateChaseLocationsActivity.CREATE_CHASE_LOCATIONS_REQ_CODE);
			} else {
				Toast.makeText(getBaseContext(), "Unable to create chase",
						Toast.LENGTH_SHORT).show();
				setResult(RESULT_CANCELED);
//				pDialog.dismiss();
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
}
