package com.chaseit.activities.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.chaseit.R;
import com.chaseit.activities.CreateChaseActivity;

public class NewsFeedTestActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_feed);

		// FragmentTransaction ft =
		// getSupportFragmentManager().beginTransaction();
		// ft.replace(R.id.flNewsFeedFragment, new NewsFeedFragment());
		// ft.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.empty, menu);
		return true;
	}

//	@Override
//	public boolean onMenuItemSelected(int featureId, MenuItem item) {
//		Intent in;
//		switch (item.getItemId()) {
//
//		case R.id.create_chase:
//			Intent createChase = new Intent(getBaseContext(),
//					CreateChaseActivity.class);
//			startActivityForResult(createChase,
//					CreateChaseActivity.CREATE_CHASE_ACTIVITY_CODE);
//
//		default:
//			return super.onOptionsItemSelected(item);
//		}
//	}
}
