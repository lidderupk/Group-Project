package com.chaseit.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaseit.GoogleMapsConnector;
import com.chaseit.ParseHelper;
import com.chaseit.R;
import com.chaseit.models.Hunt;
import com.chaseit.util.Constants;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.parse.GetCallback;
import com.parse.ParseException;

public class CreateChaseLocationsActivity extends FragmentActivity {
	private ImageView ivMap;
	private TextView tvChaseName;
	private Hunt chase;
	private int locationIndex = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_create_chase_locations);
		ivMap = (ImageView)findViewById(R.id.ivMap);
		tvChaseName = (TextView)findViewById(R.id.tvChaseName);
		tvChaseName.bringToFront();
		Intent i = getIntent();
		String chaseId = i.getStringExtra("chaseId");
		String chaseName = i.getStringExtra("chaseName");
		ParseHelper.getHuntByObjectId(chaseId, new GetCallback<Hunt>() {
			@Override
			public void done(Hunt object, ParseException e) {
				if(e == null){
					chase = object;
				} else {
					e.printStackTrace();
				}
			}
		});
		
		tvChaseName.setText(chaseName);
		RequestParams rparams = new RequestParams();
		rparams.put("markers", Constants.latUnionSquare + "," + Constants.lngUnionSquare);
		rparams.put("zoom", "12");
		rparams.put("sensor", "false");
		rparams.put("size", "1024x786");
		
		GoogleMapsConnector.getGoogleMapsConnector().getStaticMapAsync(rparams, new BinaryHttpResponseHandler(){
			@Override
            public void onSuccess(byte[] fileData) {
                Log.d("DEBUG", "Got the image data...");
                Bitmap map = BitmapFactory.decodeByteArray(fileData, 0, fileData.length);
                ivMap.setImageBitmap(map);
            }

            @Override
            public void onFailure(Throwable e, byte[] binaryData) {
                Log.e("Album Adapter","Can't fetch the image");
            }
		});
		
		ivMap.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				Intent addLocation = new Intent(getBaseContext(), AddChaseLocation.class);
				addLocation.putExtra("chaseId", chase.getObjectId());
				addLocation.putExtra("latitude", Constants.latUnionSquare);
				addLocation.putExtra("longitude", Constants.lngUnionSquare);
				addLocation.putExtra("friendlyName", "Union Square, SFO");
				addLocation.putExtra("locationIndex", locationIndex);
				startActivity(addLocation);
				locationIndex++;
				return true;
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_chase_locations, menu);
		return true;
	}

}