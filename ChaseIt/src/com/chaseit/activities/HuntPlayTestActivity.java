package com.chaseit.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.chaseit.R;
import com.chaseit.util.FragmentFactory;

public class HuntPlayTestActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hunt_play_test);

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.flHuntPlay, FragmentFactory.getHuntPlayFragment("1"));
		ft.commit();
	}
}
