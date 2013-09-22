package com.chaseit.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.chaseit.R;
import com.chaseit.fragments.HuntFragementDataInterface;

public class HuntDetailsTestActivity extends Activity implements
		HuntFragementDataInterface {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hunt_details_test);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hunt_details, menu);
		return true;
	}

	@Override
	public String getData() {
		return "This is sample data";
	}
}
