package com.chaseit.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.chaseit.R;
import com.chaseit.R.menu;
import com.chaseit.activities.test.HuntDetailsTestActivity;
import com.chaseit.fragments.FriendsHuntsFragment;
import com.chaseit.fragments.InProgressHuntsFragment;
import com.chaseit.fragments.NewsFeedFragment;
import com.chaseit.fragments.interfaces.HuntDetailsInterface;
import com.chaseit.models.Hunt;
import com.chaseit.models.wrappers.HuntWrapper;
import com.chaseit.util.Constants;
import com.parse.ParseUser;
import com.viewpagerindicator.TabPageIndicator;

public class HomeScreenActivity extends ActionBarActivity implements
		HuntDetailsInterface {
	/**
	 * The number of pages.
	 */
	private static final int NUM_PAGES = 3;

	/**
	 * The pager widget, which handles animation and allows swiping horizontally
	 * to access previous and next wizard steps.
	 */
	private ViewPager mPager;

	private TabPageIndicator mTabPageIndicator;

	/**
	 * The pager adapter, which provides the pages to the view pager widget.
	 */
	private PagerAdapter mPagerAdapter;

	private static final String tag = "Debug - com.chaseit.activities.HomeScreenActivity";

	// Views related to Navigation Drawer
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;

	/* Activity Lifecycle */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_feed);
		setupNavigationDrawer();

		// setupNavigationTabs();

		// Instantiate a ViewPager and a PagerAdapter.
		mPager = (ViewPager) findViewById(R.id.pager);
		mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
		mPager.setAdapter(mPagerAdapter);

		// Set-up TabPageIndicator
		mTabPageIndicator = (TabPageIndicator) findViewById(R.id.tabpageindicator);
		mTabPageIndicator.setViewPager(mPager);
		// mTabPageIndicator.setOnPageChangeListener(mPageChangeListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onBackPressed() {
		if (mPager.getCurrentItem() == 0) {
			// If the user is currently looking at the first step, allow the
			// system to handle the
			// Back button. This calls finish() on this activity and pops the
			// back stack.
			super.onBackPressed();
		} else {
			// Otherwise, select the previous step.
			mPager.setCurrentItem(mPager.getCurrentItem() - 1);
		}
	}

	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
		public ScreenSlidePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				return new NewsFeedFragment();
			case 1:
				return new FriendsHuntsFragment();
			case 2:
				return new InProgressHuntsFragment();
			default:
				return null;
			}
		}

		@Override
		public int getCount() {
			return NUM_PAGES;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return "PUBLIC";
			case 1:
				return "FRIENDS";
			case 2:
			default:
				return "IN PROGRESS";
			}
		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	/* ActionBar Options Menu */

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		// view
//		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
//		menu.findItem(R.id.create_chase).recreate();
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		} else {
			// Handle your other action bar items...
			Intent createChase = new Intent(getBaseContext(),
					CreateChaseActivity.class);
			startActivityForResult(createChase,
					CreateChaseActivity.CREATE_CHASE_ACTIVITY_CODE);
			return true;
		}
	}

	/* Private Methods */

	private void setupNavigationDrawer() {

		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer icon to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description */
		R.string.drawer_close /* "close drawer" description */
		) {

			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {
				getSupportActionBar().setTitle(mTitle);
				
				// invalidateOptionsMenu();
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle(mDrawerTitle);
				 invalidateOptionsMenu();
			}
		};

		String[] menuArray = getResources().getStringArray(
				R.array.menu_drawer_array);
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, menuArray));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// Set the drawer toggle as the DrawerListener
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	private void selectItem(int position) {

		if (position == 0) {
			Intent i = new Intent(HomeScreenActivity.this,
					UserDetailsActivity.class);
			startActivity(i);
		}

		if (position == 4) {
			// Log the user out
			ParseUser.logOut();

			// Go to the login view
			startLoginActivity();
		}
		mDrawerList.setItemChecked(position, true);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	private void startLoginActivity() {
		Intent intent = new Intent(this, LoginActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	@Override
	public void huntClicked(Hunt hunt) {
		Log.d(tag, "hunt clicked. Activity notified !");
		HuntWrapper hWrapper = new HuntWrapper(hunt);
		Intent in = new Intent(getBaseContext(), HuntDetailsTestActivity.class);
		in.putExtra(Constants.HUNT_WRAPPER_DATA_NAME, hWrapper);
		startActivity(in);
	}
}
