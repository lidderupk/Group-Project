package com.chaseit.fragments;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.Spinner;

import com.chaseit.ParseHelper;
import com.chaseit.R;
import com.chaseit.activities.HomeScreenActivity;
import com.chaseit.adapters.HuntAdapter;
import com.chaseit.models.Hunt;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;

public class NewsFeedFragment extends Fragment {

	private static final String tag = "Debug - com.chaseit.fragments.NewsFeedFragment";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_newsfeed, container,
				false);
		queryParse();
		return view;
	}

	private void queryParse() {
		ParseHelper.getAllHuntsByCreateDate(new FindCallback<Hunt>() {

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
						listView.setOnItemClickListener(getHuntClickedListener());
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

	@Override
	public void onStart() {
		super.onStart();
		Spinner spinner = (Spinner) getView().findViewById(R.id.spFilter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {
				if (position == 0) {
					ParseHelper
							.getAllHuntsByNumRatings(new FindCallback<Hunt>() {

								@Override
								public void done(List<Hunt> objects,
										ParseException e) {
									if (e == null && objects != null) {
										for (Hunt hunts : objects) {
											HuntAdapter adapter = new HuntAdapter(
													getActivity(), objects);
											// Attach the adapter to a ListView
											ListView listView = (ListView) getView()
													.findViewById(
															R.id.lvHuntList);
											listView.setAdapter(adapter);
											listView.setOnItemClickListener(getHuntClickedListener());
										}

									} else {
										e.printStackTrace();
									}
								}

							});
				} else if (position == 1){
					ParseHelper
					.getAllHuntsByCreateDate(new FindCallback<Hunt>() {

						@Override
						public void done(List<Hunt> objects,
								ParseException e) {
							if (e == null && objects != null) {
								for (Hunt hunts : objects) {
									HuntAdapter adapter = new HuntAdapter(
											getActivity(), objects);
									// Attach the adapter to a ListView
									ListView listView = (ListView) getView()
											.findViewById(
													R.id.lvHuntList);
									listView.setAdapter(adapter);
									listView.setOnItemClickListener(getHuntClickedListener());
								}

							} else {
								e.printStackTrace();
							}
						}

					});
				} else if (position == 2){
					ParseHelper
					.getAllHuntsByAvgRating(new FindCallback<Hunt>() {

						@Override
						public void done(List<Hunt> objects,
								ParseException e) {
							if (e == null && objects != null) {
								for (Hunt hunts : objects) {
									HuntAdapter adapter = new HuntAdapter(
											getActivity(), objects);
									// Attach the adapter to a ListView
									ListView listView = (ListView) getView()
											.findViewById(
													R.id.lvHuntList);
									listView.setAdapter(adapter);
									listView.setOnItemClickListener(getHuntClickedListener());
								}

							} else {
								e.printStackTrace();
							}
						}

					});
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				// your code here
			}

		});
	}

	private OnItemClickListener getHuntClickedListener() {
		return new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d(tag, "hunt clicked");
				Hunt hunt = (Hunt) parent.getItemAtPosition(position);
				Log.d(tag, "hunt id: " + hunt.getObjectId());
				Log.d(tag, "hunt name: " + hunt.getName());
				/*
				 * notify the parent interface to start the next activity
				 */
				HomeScreenActivity homeScreenActivity;
				try {
					homeScreenActivity = (HomeScreenActivity) getActivity();
					homeScreenActivity.huntClicked(hunt);
				} catch (ClassCastException castException) {
					Log.e(tag, castException.getMessage());
				}
			}
		};
	}
}
