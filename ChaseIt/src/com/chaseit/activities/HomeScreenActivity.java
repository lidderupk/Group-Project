package com.chaseit.activities;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.widget.Toast;

import com.chaseit.R;
import com.chaseit.fragments.ContainerFragment;
import com.chaseit.fragments.NewsFeedFragment;

public class HomeScreenActivity extends FragmentActivity implements TabListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		setupNavigationTabs();
		setContentView(R.layout.activity_news_feed);
//
//		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//		ft.replace(R.id.flNewsFeedFragment, new NewsFeedFragment());
//		ft.commit();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.news_feed, menu);
		return true;
	}
	
    private void setupNavigationTabs() {
    	ActionBar actionBar = getActionBar();
    	actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    	actionBar.setDisplayShowTitleEnabled(true);
    	Tab tabHome = actionBar.newTab().setText("Newsfeed")
    			.setTag("HomeTimelineFragment")
    			.setIcon(R.drawable.ic_test)
    			.setTabListener(this);
    	
    	Tab tabProfile = actionBar.newTab().setText("Profile")
    			.setTag("ContainerFragment")
    			.setIcon(R.drawable.ic_test)
    			.setTabListener(this);
    	
    	actionBar.addTab(tabHome);
    	actionBar.addTab(tabProfile);
    	actionBar.selectTab(tabHome);
	}

	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
		FragmentManager manager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fts = manager.beginTransaction();
		if(tab.getTag() == "HomeTimelineFragment"){
			fts.replace(R.id.flNewsFeedFragment, new NewsFeedFragment());
			
		} else {
			fts.replace(R.id.flNewsFeedFragment, new ContainerFragment());
			Toast.makeText(getApplicationContext(), "working", Toast.LENGTH_SHORT).show();
		}
		
		fts.commit();
	}

	@Override
	public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

}
