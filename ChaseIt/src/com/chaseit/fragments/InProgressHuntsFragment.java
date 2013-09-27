package com.chaseit.fragments;

import java.util.ArrayList;
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
import com.chaseit.models.UserHunt;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;

public class InProgressHuntsFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_newsfeed, container,
				false);
		return view;
	}

	private void queryParse() {
		final ArrayList<Hunt> huntsList = new ArrayList<Hunt>();
		ParseHelper.getMyHuntsInProgress(new FindCallback<UserHunt>() {
			@Override
			public void done(List<UserHunt> objects, ParseException e) {

				if (e == null && objects != null) {
					for (UserHunt hunts : objects) {
						ParseHelper.getHuntByObjectId(hunts.getHuntObjectId(),
								new GetCallback<Hunt>() {
									public void done(Hunt hunt, ParseException e) {
										huntsList.add(hunt);
									}
								});
						HuntAdapter adapter = new HuntAdapter(getActivity(),
								huntsList);
						// Attach the adapter to a ListView
						ListView listView = (ListView) getView().findViewById(
								R.id.lvHuntList);
						listView.setAdapter(adapter);
						adapter.notifyDataSetChanged();
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
		queryParse();
	}
}