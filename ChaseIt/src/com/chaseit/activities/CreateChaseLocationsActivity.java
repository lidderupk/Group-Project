package com.chaseit.activities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.widget.TextView;

import com.chaseit.ParseHelper;
import com.chaseit.R;
import com.chaseit.models.Hunt;
import com.chaseit.util.Constants;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLngBounds.Builder;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.loopj.android.http.RequestParams;
import com.parse.GetCallback;
import com.parse.ParseException;

public class CreateChaseLocationsActivity extends FragmentActivity {
	//private ImageView ivMap;
	private GoogleMap fCreateChaseMap;
	private TextView tvChaseName;
	private Hunt chase;
	private List<LatLng> mapPoints;
	private LatLng currentPoint;
	private String friendlyName;
	private Geocoder geocode;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_create_chase_locations);
		initilizeMap();
		geocode = new Geocoder(getBaseContext(), Locale.getDefault());
		mapPoints = new ArrayList<LatLng>();
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
		
//		GoogleMapsConnector.getGoogleMapsConnector().getStaticMapAsync(rparams, new BinaryHttpResponseHandler(){
//			@Override
//            public void onSuccess(byte[] fileData) {
//                Log.d("DEBUG", "Got the image data...");
//                Bitmap map = BitmapFactory.decodeByteArray(fileData, 0, fileData.length);
//                ivMap.setImageBitmap(map);
//            }
//
//            @Override
//            public void onFailure(Throwable e, byte[] binaryData) {
//                Log.e("Album Adapter","Can't fetch the image");
//            }
//		});
		
		fCreateChaseMap.setOnMapLongClickListener(new OnMapLongClickListener() {

			@Override
			public void onMapLongClick(LatLng point) {
				//mapPoints.add(point);
				currentPoint = new LatLng(point.latitude, point.longitude);
				friendlyName = "Unknown";
				try {
					List<Address> address = geocode.getFromLocation(point.latitude, point.longitude, 1);
					if(address != null && address.size() == 1){
						friendlyName = address.get(0).getSubLocality() + ", " + address.get(0).getLocality();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				Intent addLocation = new Intent(getBaseContext(), AddChaseLocation.class);
				addLocation.putExtra("chaseId", chase.getObjectId());
				addLocation.putExtra("latitude", point.latitude);
				addLocation.putExtra("longitude", point.longitude);
				addLocation.putExtra("friendlyName", friendlyName);
				addLocation.putExtra("locationIndex", mapPoints.size());
				startActivityForResult(addLocation, AddChaseLocation.ADD_CHASE_LOCATION_ACTIVITY_CODE);
			}
			
		});
		
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			//add current point to the list of locations for this chase
			mapPoints.add(currentPoint);
			fCreateChaseMap.addMarker(new MarkerOptions()
								.position(currentPoint)
								.title((mapPoints.size() + 1) + ". " + friendlyName)
								.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
			drawPolygon();
		} else {
			//center on the last position
			if(mapPoints.size() > 0){
				fCreateChaseMap.animateCamera(CameraUpdateFactory.newLatLng(mapPoints.get(mapPoints.size() - 1)));				
			} else {
				fCreateChaseMap.animateCamera(CameraUpdateFactory.newCameraPosition(fCreateChaseMap.getCameraPosition()));
				//
			}

		}
	}

	private void drawPolygon() {
		PolylineOptions rectOptions = new PolylineOptions();
		Builder builder  = new LatLngBounds.Builder();
		for(LatLng point : mapPoints){
			builder.include(point);			
			rectOptions.add(point).width(5).color(Color.BLUE).geodesic(true);
			fCreateChaseMap.addPolyline(rectOptions);
		}
		//move the camera to show the whole chase
		fCreateChaseMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 50));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_chase_locations, menu);
		return true;
	}

	
	/**
	 * function to load map. If map is not created it will create it for you
	 * */
	private void initilizeMap() {
		if (fCreateChaseMap == null) {
			fCreateChaseMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fCreateChaseMap)).getMap();
			fCreateChaseMap.setMyLocationEnabled(true);
			// check if map is created successfully or not
			if (fCreateChaseMap == null) {
				Log.d("DEBUG", "Sorry! unable to create maps");
			}
		}
	}

}