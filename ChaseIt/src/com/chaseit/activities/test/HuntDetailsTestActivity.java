package com.chaseit.activities.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;

import com.chaseit.R;
import com.chaseit.fragments.interfaces.HuntStartInterface;
import com.chaseit.models.wrappers.HuntWrapper;
import com.chaseit.models.wrappers.UserHuntWrapper;
import com.chaseit.util.Constants;
import com.chaseit.util.FragmentFactory;

public class HuntDetailsTestActivity extends FragmentActivity implements
		HuntStartInterface {

	private static final String tag = "Debug - com.chaseit.activities.test.HuntDetailsTestActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hunt_details_test);

		HuntWrapper hWrapper = (HuntWrapper) getIntent().getSerializableExtra(
				Constants.HUNT_WRAPPER_DATA_NAME);

		if (hWrapper == null) {
			Log.e(tag, "huntId is missing !.");
			return;
		}

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.flHuntDetails,
				FragmentFactory.getHuntDetailsFragment(hWrapper));
		ft.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hunt_details, menu);
		return true;
	}

	@Override
	public void startHunt(UserHuntWrapper uHuntWrapper) {
		Log.d(tag, "hunt clicked. Activity notified !");
		Intent in = new Intent(getBaseContext(), HuntPlayTestActivity.class);
		in.putExtra(Constants.HUNT_WRAPPER_DATA_NAME, uHuntWrapper);
		startActivity(in);
	}
}
