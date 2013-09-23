package com.chaseit.activities;

import android.app.Activity;
import android.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;

import com.chaseit.R;
import com.chaseit.util.FragmentFactory;

public class HuntDetailsTestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hunt_details_test);

		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.flHuntDetails,
				FragmentFactory.getHuntDetailsFragment("1"));
		ft.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hunt_details, menu);
		return true;
	}
}
