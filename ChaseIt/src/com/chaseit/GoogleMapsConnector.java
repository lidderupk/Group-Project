package com.chaseit;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class GoogleMapsConnector {
	public static final String STATIC_MAP_BASE_URL = "http://maps.google.com/maps/api/staticmap";

	private AsyncHttpClient client;
	private static GoogleMapsConnector instance;
	
	private GoogleMapsConnector(){
		client = new AsyncHttpClient();
	}
	
	public static synchronized GoogleMapsConnector getGoogleMapsConnector(){
		if(instance == null){
			instance = new GoogleMapsConnector();
		}
		return instance;
	}
	
	public void getStaticMapAsync(RequestParams rparams, BinaryHttpResponseHandler handler){
		client.get(STATIC_MAP_BASE_URL, rparams, handler);
	}
}
