package com.chaseit.fragments;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chaseit.ParseHelper;
import com.chaseit.R;
import com.chaseit.fragments.interfaces.HuntStartInterface;
import com.chaseit.models.CIUser;
import com.chaseit.models.Hunt;
import com.chaseit.models.UserHunt;
import com.chaseit.models.UserHunt.HuntStatus;
import com.chaseit.models.wrappers.HuntWrapper;
import com.chaseit.models.wrappers.ParseObjectWrapper;
import com.chaseit.models.wrappers.UserHuntWrapper;
import com.chaseit.util.Constants;
import com.chaseit.util.Helper;
import com.google.android.gms.maps.model.LatLng;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;

public class HuntDetailsFragment extends Fragment {

	private static final String tag = "Debug - com.chaseit.fragments.HuntDetailsFragment";
	private HuntWrapper hWrapper;
	private Hunt hunt;
	private TextView tvHuntDetailsTitle;
	private TextView tvHuntDetailsCreatorHandle;
	private RatingBar rbHuntDetailsRating;
	private TextView tvHuntDetailsLocationName;
	private TextView tvHuntDetailsDescription;
	private ImageView ivHuntsDetailsMap;
	private Button btnHuntDetailsLaunch;

	private boolean isHuntInProgress = false;
	protected UserHuntWrapper uHuntWrapper;

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
		hWrapper = (HuntWrapper) extras
				.getSerializable(Constants.HUNT_WRAPPER_DATA_NAME);

		ParseHelper.getHuntByObjectId(hWrapper.getObjectId(),
				new GetCallback<Hunt>() {

					@Override
					public void done(Hunt object, ParseException e) {
						if (e == null) {

							hunt = object;
							setupViews(getView(), hunt);
						} else {
							Log.d(tag, e.getMessage());
						}
					}
				});

		ParseHelper.getHuntByObjectId(hWrapper.getObjectId(),
				new GetCallback<Hunt>() {

					@Override
					public void done(Hunt hunt, ParseException e) {
						ParseHelper.getHuntInProgressGivenHuntAndUser(hunt,
								CIUser.getCurrentUser(),
								new FindCallback<UserHunt>() {

									@Override
									public void done(List<UserHunt> hunts,
											ParseException e) {
										if (e == null) {
											if (hunts != null
													&& hunts.size() > 0) {
												Log.d(tag,
														"This hunt is already in progress !");
												isHuntInProgress = true;
												btnHuntDetailsLaunch
														.setText("Continue");
											}
										} else
											Log.d(tag, e.getMessage());
									}
								});
					}
				});

	}

	private void setupViews(View view, Hunt hunt) {

		tvHuntDetailsTitle = (TextView) view
				.findViewById(R.id.tvHuntDetailsTitle);
		tvHuntDetailsCreatorHandle = (TextView) view
				.findViewById(R.id.tvHuntDetailsCreatorHandle);
		rbHuntDetailsRating = (RatingBar) view
				.findViewById(R.id.rbHuntDetailsRating);
		tvHuntDetailsLocationName = (TextView) view
				.findViewById(R.id.tvHuntDetailsLocationName);
		tvHuntDetailsDescription = (TextView) view
				.findViewById(R.id.tvHuntDetailsDescription);
		ivHuntsDetailsMap = (ImageView) view
				.findViewById(R.id.ivHuntsDetailsMap);
		btnHuntDetailsLaunch = (Button) view
				.findViewById(R.id.btnHuntDetailsLaunch);

		if (Helper.isNotEmpty(hunt.getName()))
			tvHuntDetailsTitle.setText(hunt.getName());

		// if (Helper.nonEmpty(hunt.getCreator());
		// tvHuntDetailsCreatorHandle.setText(hunt.getCreator().getUsername());

		rbHuntDetailsRating.setRating(Math.round(hunt.getAvgRating()));

		// get hunt location
		// if(Helper.nonEmpty(hunt.get)))
		// tvHuntDetailsCreatorHandle.setText(hunt.getCreator().getUsername());
		if (Helper.isNotEmpty(hunt.getDetails()))
			tvHuntDetailsDescription.setText(hunt.getDetails());

		ParseGeoPoint startLocation = hunt.getStartLocation();
		if (startLocation != null) {
			getMap(new LatLng(startLocation.getLatitude(),
					startLocation.getLongitude()), view);
		}

		btnHuntDetailsLaunch.setOnClickListener(getHuntStartClickListener());
	}

	private OnClickListener getHuntStartClickListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(tag, "start or contiue clicked");

				/*
				 * hunt started, create a new user hunt and push to parse
				 * 
				 * first get the hunt from parse
				 */

				if (!isHuntInProgress) {

					try {
						UserHunt userHunt = new UserHunt();
						userHunt.setHunt(hunt);
						userHunt.setUser(CIUser.getCurrentUser());
						userHunt.setHuntStatus(HuntStatus.IN_PROGRESS);
						userHunt.save();

						uHuntWrapper = new UserHuntWrapper(
								new ParseObjectWrapper(userHunt));

					} catch (ParseException e1) {
						Toast.makeText(getActivity().getBaseContext(),
								"Hunt could not be started. Try again",
								Toast.LENGTH_SHORT).show();
						e1.printStackTrace();
					}
				}

				FragmentActivity activity = getActivity();
				if (activity instanceof HuntStartInterface)
					((HuntStartInterface) activity).startHunt(uHuntWrapper);
			}
		};
	}

	private void getMap(LatLng point, View view) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append("http://maps.google.com/maps/api/staticmap?markers=");
		stringBuilder.append(point.latitude);
		stringBuilder.append(",");
		stringBuilder.append(point.longitude);
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
