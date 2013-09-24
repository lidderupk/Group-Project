package com.chaseit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.chaseit.R;
import com.chaseit.fragments.NewsFeedFragment;

public class NewsFeedTestActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_feed);

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.flNewsFeedFragment, new NewsFeedFragment());
		ft.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.news_feed_test, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Intent in;
		switch (item.getItemId()) {
		case R.id.maps:
			in = new Intent(getBaseContext(), MapsTestActivity.class);
			startActivity(in);
			return true;

		case R.id.huntDetails:
			in = new Intent(getBaseContext(), HuntDetailsTestActivity.class);
			startActivity(in);
			return true;
		
		case R.id.huntPlayMenu:
			in = new Intent(getBaseContext(), HuntPlayTestActivity.class);
			startActivity(in);
			return true;
		
		case R.id.huntMapLineMenu:
			in = new Intent(getBaseContext(),
					HuntMapWithMarkersTestActivity.class);

			startActivity(in);
			return true;
			
		case R.id.create_chase:
			Intent createChase = new Intent(getBaseContext(), CreateChaseActivity.class);
			startActivityForResult(createChase, CreateChaseActivity.CREATE_CHASE_ACTIVITY_CODE);
			
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
