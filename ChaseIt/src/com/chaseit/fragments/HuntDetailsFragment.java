package com.chaseit.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chaseit.R;
import com.chaseit.models.Hunt;
import com.parse.ParseFacebookUtils.Permissions.User;

public class HuntDetailsFragment extends Fragment {

	private static final String tag = "Debug - com.chaseit.fragments.HuntDetailsFragment";
	private Hunt hunt;
	private User user;

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
		setupViews();
	}

	private void setupViews() {

	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		HuntFragementDataInterface hostActivity;
		try {
			hostActivity = (HuntFragementDataInterface) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement HuntFragementDataInterface");
		}
		String data = hostActivity.getData();
		Log.d(tag, "data: " + data);
	}
}
