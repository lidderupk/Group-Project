package com.chaseit.activities.test;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.chaseit.R;
import com.chaseit.util.FragmentFactory;

public class HuntMapWithMarkersTestActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hunt_map_with_markers_test);

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.flMapWithMarkers,
				FragmentFactory.getHuntMapWithMarkersFragment("1"));
		ft.commit();
	}
}
