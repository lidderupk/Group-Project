package com.chaseit.fragments;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chaseit.R;
import com.chaseit.util.Constants;

public class HuntDetailsFragment extends Fragment {

	private static final String tag = "Debug - com.chaseit.fragments.HuntDetailsFragment";
	private String huntId;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_huntdetails, container,
				false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Bundle extras = getArguments();
		huntId = extras.getString(Constants.HUNT_ID);
		Log.d(tag, "huntID: " + huntId);
		setupViews(getView());
	}

	private void setupViews(View view) {

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append("http://maps.google.com/maps/api/staticmap?markers=");
		stringBuilder.append(Constants.latUnionSquare);
		stringBuilder.append(",");
		stringBuilder.append(Constants.lngUnionSquare);
		stringBuilder.append("&zoom=16&size=520x520&sensor=false");
		String[] stringURL = new String[1];
		stringURL[0] = stringBuilder.toString();
		GetGoogleImage task = new GetGoogleImage(view);
		task.execute(stringURL);
	}
}

class GetGoogleImage extends AsyncTask<String, Void, Bitmap> {

	private View view;
	private static final String tag = "Debug com.chaseit.fragments.GetGoogleImage";

	public GetGoogleImage(View view) {
		super();
		this.view = view;
	}

	@Override
	protected Bitmap doInBackground(String... stringURL) {
		Bitmap bmp = null;
		try {
			URL url = new URL(stringURL[0]);
			Log.d(tag, "url: " + url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			BitmapFactory.Options options = new BitmapFactory.Options();

			bmp = BitmapFactory.decodeStream(is, null, options);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bmp;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		ImageView iHuntsDetailsMap = (ImageView) view
				.findViewById(R.id.ivHuntsDetailsMap);
		iHuntsDetailsMap.setImageBitmap(result);
		super.onPostExecute(result);
	}

}
