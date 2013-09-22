package com.chaseit.fragments;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.chaseit.ParseHelper;
import com.chaseit.R;
import com.chaseit.adapters.HuntAdapter;
import com.chaseit.models.Hunt;
import com.parse.FindCallback;
import com.parse.ParseException;

public class MyHuntsFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_newsfeed, container,
				false);
		queryParse();
		return view;
	}

	private void queryParse() {
		ParseHelper.getMyHuntsCreated(new FindCallback<Hunt>() {

			@Override
			public void done(List<Hunt> objects, ParseException e) {
				if (e == null && objects != null) {
					for (Hunt hunts : objects) {
						HuntAdapter adapter = new HuntAdapter(getActivity(),
								objects);
						// Attach the adapter to a ListView
						ListView listView = (ListView) getView().findViewById(
								R.id.lvHuntList);
						listView.setAdapter(adapter);
					}

				} else {
					e.printStackTrace();
				}

			}
		});

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
}