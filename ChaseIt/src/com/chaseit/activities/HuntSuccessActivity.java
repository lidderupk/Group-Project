package com.chaseit.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.chaseit.R;
import com.chaseit.fragments.HuntSuccessFragment;

public class HuntSuccessActivity extends FragmentActivity {

	private final static String tag = "Debug - com.chaseit.activities.test.HuntPlayTestActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hunt_success);

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.flHuntSuccess, new HuntSuccessFragment());
		ft.commit();
	}

}
