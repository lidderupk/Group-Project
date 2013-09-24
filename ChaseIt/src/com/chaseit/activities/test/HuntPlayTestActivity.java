package com.chaseit.activities.test;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.chaseit.R;
import com.chaseit.util.Constants;
import com.chaseit.util.FragmentFactory;
import com.chaseit.util.Helper;

public class HuntPlayTestActivity extends FragmentActivity {

	private final static String tag = "Debug - com.chaseit.activities.test.HuntPlayTestActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hunt_play_test);

		String huntId = getIntent().getStringExtra(Constants.HUNT_ID);

		if (!Helper.isNotEmpty(huntId)) {
			Log.e(tag, "huntId is missing !.");
			return;
		}
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.flHuntPlay, FragmentFactory.getHuntPlayFragment(huntId));
		ft.commit();
	}
}
