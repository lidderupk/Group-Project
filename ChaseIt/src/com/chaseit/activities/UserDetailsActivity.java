package com.chaseit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.chaseit.ChaseItApplication;
import com.chaseit.ParseHelper;
import com.chaseit.R;
import com.chaseit.models.CIUser;
import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

public class UserDetailsActivity extends ActionBarActivity {

	private ProfilePictureView userProfilePictureView;
	private TextView userNameView;
	private TextView userLocationView;
	private TextView userGenderView;
	private TextView userDateOfBirthView;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_profile);

		setupNavigationDrawer();

		userProfilePictureView = (ProfilePictureView) findViewById(R.id.userProfilePicture);
		userNameView = (TextView) findViewById(R.id.userName);
		userLocationView = (TextView) findViewById(R.id.userLocation);
		userGenderView = (TextView) findViewById(R.id.userGender);
		userDateOfBirthView = (TextView) findViewById(R.id.userDateOfBirth);

		// Fetch Facebook user info if the session is active
		Session session = ParseFacebookUtils.getSession();
		if (session != null && session.isOpened()) {
			makeMeRequest();
		}
	}

	@Override
	public void onResume() {
		super.onResume();

		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
			// Check if the user is currently logged
			// and show any cached content
			updateViewsWithProfileInfo();
		} else {
			// If the user is not logged in, go to the
			// activity showing the login view.
			startLoginActivity();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		// view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.create_chase).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle your other action bar items...
		Intent in;
		switch (item.getItemId()) {

		case R.id.create_chase:
			Intent createChase = new Intent(getBaseContext(),
					CreateChaseActivity.class);
			startActivityForResult(createChase,
					CreateChaseActivity.CREATE_CHASE_ACTIVITY_CODE);

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void makeMeRequest() {
		Request request = Request.newMeRequest(ParseFacebookUtils.getSession(),
				new Request.GraphUserCallback() {
					@Override
					public void onCompleted(GraphUser user, Response response) {
						if (user != null) {
							// Populate the JSON object
							CIUser.setFacebookid(user.getId());
							CIUser.setName(user.getName());
							if (user.getLocation().getProperty("name") != null) {
								CIUser.setLocation((String) user.getLocation()
										.getProperty("name"));
							}
							if (user.getProperty("gender") != null) {
								CIUser.setGender((String) user
										.getProperty("gender"));
							}
							if (user.getBirthday() != null) {
								CIUser.setBirthday(user.getBirthday());
							}

							// Save the user profile info in a user property
							ParseHelper.saveUser(CIUser.getCurrentUser(), null);

							// Show the user info
							updateViewsWithProfileInfo();
						} else if (response.getError() != null) {
							if ((response.getError().getCategory() == FacebookRequestError.Category.AUTHENTICATION_RETRY)
									|| (response.getError().getCategory() == FacebookRequestError.Category.AUTHENTICATION_REOPEN_SESSION)) {
								Log.d(ChaseItApplication.TAG,
										"The facebook session was invalidated.");
								onLogoutButtonClicked();
							} else {
								Log.d(ChaseItApplication.TAG,
										"Some other error: "
												+ response.getError()
														.getErrorMessage());
							}
						}
					}
				});
		request.executeAsync();

	}

	private void updateViewsWithProfileInfo() {
		if (CIUser.getFacebookid() != null) {
			userProfilePictureView.setProfileId(CIUser.getFacebookid());
		} else {
			// Show the default, blank user profile picture
			userProfilePictureView.setProfileId(null);
		}
		if (CIUser.getName() != null) {
			userNameView.setText(CIUser.getName());
		} else {
			userNameView.setText("");
		}
		if (CIUser.getLocation() != null) {
			userLocationView.setText(CIUser.getLocation());
		} else {
			userLocationView.setText("");
		}
		if (CIUser.getGender() != null) {
			userGenderView.setText(CIUser.getGender());
		} else {
			userGenderView.setText("");
		}
		if (CIUser.getBirthday() != null) {
			userDateOfBirthView.setText("11");
		} else {
			userDateOfBirthView.setText("");
		}
	}

	private void onLogoutButtonClicked() {
		ParseUser.logOut();

		startLoginActivity();
	}

	private void startLoginActivity() {
		Intent intent = new Intent(this, LoginActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	private void setupNavigationDrawer() {

		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_profile);
		mDrawerList = (ListView) findViewById(R.id.left_drawer_profile);
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer icon to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description */
		R.string.drawer_close /* "close drawer" description */
		) {
			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {
				getSupportActionBar().setTitle(mTitle);
				invalidateOptionsMenu();
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu();
			}
		};

		String[] menuArray = getResources().getStringArray(
				R.array.menu_drawer_array_profile);
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
			Intent i = new Intent(UserDetailsActivity.this,
					HomeScreenActivity.class);
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

}
