package com.chaseit.activities.test;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.chaseit.R;
import com.chaseit.models.wrappers.UserHuntWrapper;
import com.chaseit.util.Constants;
import com.chaseit.util.FragmentFactory;

public class HuntPlayTestActivity extends FragmentActivity {

	private final static String tag = "Debug - com.chaseit.activities.test.HuntPlayTestActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hunt_play_test);

		UserHuntWrapper uHuntWrapper = (UserHuntWrapper) getIntent()
				.getSerializableExtra(Constants.USER_HUNT_WRAPPER_DATA_NAME);

		if (uHuntWrapper != null) {
			Log.e(tag, "huntId is missing !.");
			return;
		}
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.flHuntPlay,
				FragmentFactory.getHuntPlayFragment(uHuntWrapper));
		ft.commit();
	}
}
