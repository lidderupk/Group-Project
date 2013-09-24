package com.chaseit.activities.test;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;

import com.chaseit.R;
import com.chaseit.util.Constants;
import com.chaseit.util.FragmentFactory;

public class HuntDetailsTestActivity extends FragmentActivity {

	private static final String tag = "Debug - com.chaseit.activities.test.HuntDetailsTestActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hunt_details_test);

		String huntId = getIntent().getStringExtra(Constants.HUNT_ID);

		if (huntId == null || huntId.length() < 1) {
			Log.e(tag, "huntId is missing !.");
			return;
		}

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.flHuntDetails,
				FragmentFactory.getHuntDetailsFragment(huntId));
		ft.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hunt_details, menu);
		return true;
	}
}
