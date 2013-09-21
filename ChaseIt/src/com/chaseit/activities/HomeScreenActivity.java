package com.chaseit.activities;


import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.View;

import com.chaseit.R;
import com.chaseit.fragments.MyHuntsFragment;
import com.chaseit.fragments.NewsFeedFragment;
import com.chaseit.fragments.RecentHuntsFragment;

public class HomeScreenActivity extends FragmentActivity implements TabListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_feed);
		setupNavigationTabs();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void setupNavigationTabs() {
		ActionBar actionBar = getActionBar();

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		Tab tabHome = actionBar.newTab().setText("All Hunts")
				.setTag("HomeTimelineFragment").setTabListener(this);

		Tab tabRecent = actionBar.newTab().setText("Recent")
				.setTag("RecentFragment").setTabListener(this);
		Tab tabMine = actionBar.newTab().setText("Mine")
				.setTag("MyHuntsFragment").setTabListener(this);

		actionBar.addTab(tabHome);
		actionBar.addTab(tabMine);
		actionBar.addTab(tabRecent);
		actionBar.selectTab(tabHome);

		actionBar.setDisplayShowHomeEnabled(false);
		//displaying custom ActionBar w Menu icon to the left
		View mActionBarView = getLayoutInflater().inflate(R.layout.my_action_bar, null);
		actionBar.setCustomView(mActionBarView);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);


	}

	public void onProfileSelected(View v){
		Intent i = new Intent(HomeScreenActivity.this, UserDetailsActivity.class);
		startActivity(i);
	}

	@Override
	public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
		FragmentManager manager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fts = manager
				.beginTransaction();
		if (tab.getTag() == "HomeTimelineFragment") {
			fts.replace(R.id.flNewsFeedFragment, new NewsFeedFragment());

		} else if (tab.getTag() == "RecentHuntsFragment") {
			fts.replace(R.id.flNewsFeedFragment, new RecentHuntsFragment());
		} else {
			fts.replace(R.id.flNewsFeedFragment, new MyHuntsFragment());
		}

		fts.commit();
	}

	@Override
	public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
}
