package com.chaseit.fb;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.chaseit.models.Hunt;

public class NewsFeedFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_newsfeed, container,
				false);
		return view;
	}

	private void setupViews() {
		FragmentActivity activity = getActivity();
		List<Hunt> huntsArray = Hunt.createSampleHunts(activity
				.getBaseContext());
		// Create the adapter to convert the array to views
		HuntAdapter adapter = new HuntAdapter(activity, huntsArray);
		// Attach the adapter to a ListView
		ListView listView = (ListView) getView().findViewById(R.id.lvHuntList);
		listView.setAdapter(adapter);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setupViews();
	}
}
