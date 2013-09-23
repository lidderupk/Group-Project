package com.chaseit.activities;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.chaseit.R;
import com.chaseit.util.FragmentFactory;

public class HuntPlayTestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hunt_play_test);

		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.flHuntPlay, FragmentFactory.getHuntPlayFragment("1"));
		ft.commit();
	}
}
