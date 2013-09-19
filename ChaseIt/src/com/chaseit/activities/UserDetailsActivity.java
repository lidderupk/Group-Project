//package com.chaseit.activities;
//
//import android.app.ActionBar;
//import android.app.ActionBar.Tab;
//import android.app.ActionBar.TabListener;
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.FragmentTransaction;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import com.chaseit.ChaseItApplication;
//import com.chaseit.ParseHelper;
//import com.chaseit.R;
//import com.chaseit.models.CIUser;
//import com.facebook.FacebookRequestError;
//import com.facebook.Request;
//import com.facebook.Response;
//import com.facebook.Session;
//import com.facebook.model.GraphUser;
//import com.facebook.widget.ProfilePictureView;
//import com.parse.ParseFacebookUtils;
//import com.parse.ParseUser;
//
//public class UserDetailsActivity extends Activity implements TabListener {
//
//	private ProfilePictureView userProfilePictureView;
//	private TextView userNameView;
//	private TextView userLocationView;
//	private TextView userGenderView;
//	private TextView userDateOfBirthView;
//	private Button logoutButton;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//
//		setContentView(R.layout.activity_profile);
//		setupNavigationTabs();
//
//		userProfilePictureView = (ProfilePictureView) findViewById(R.id.userProfilePicture);
//		userNameView = (TextView) findViewById(R.id.userName);
//		userLocationView = (TextView) findViewById(R.id.userLocation);
//		userGenderView = (TextView) findViewById(R.id.userGender);
//		userDateOfBirthView = (TextView) findViewById(R.id.userDateOfBirth);
//
//		logoutButton = (Button) findViewById(R.id.logoutButton);
//		logoutButton.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				onLogoutButtonClicked();
//			}
//		});
//
//		// Fetch Facebook user info if the session is active
//		Session session = ParseFacebookUtils.getSession();
//		if (session != null && session.isOpened()) {
//			makeMeRequest();
//		}
//	}
//	
//	private void setupNavigationTabs() {
//    	ActionBar actionBar = getActionBar();
//    	actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//    	actionBar.setDisplayShowTitleEnabled(true);
//    	Tab tabHome = actionBar.newTab().setText("Newsfeed")
//    			.setTag("HomeTimelineFragment")
//    			.setIcon(R.drawable.ic_test)
//    			.setTabListener(this);
//    	
//    	Tab tabProfile = actionBar.newTab().setText("Profile")
//    			.setTag("ContainerFragment")
//    			.setIcon(R.drawable.ic_test)
//    			.setTabListener(this);
//    	
//    	actionBar.addTab(tabHome);
//    	actionBar.addTab(tabProfile);
//    	actionBar.selectTab(tabProfile);
//	}
//
//	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
//		if(tab.getTag() == "HomeTimelineFragment"){
//			Intent i = new Intent( this, HomeScreenActivity.class );
//			startActivity(i);	
//		}
//	}
//
//	@Override
//	public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void onResume() {
//		super.onResume();
//
//		ParseUser currentUser = ParseUser.getCurrentUser();
//		if (currentUser != null) {
//			// Check if the user is currently logged
//			// and show any cached content
//			updateViewsWithProfileInfo();
//		} else {
//			// If the user is not logged in, go to the
//			// activity showing the login view.
//			startLoginActivity();
//		}
//	}
//
//	private void makeMeRequest() {
//		Request request = Request.newMeRequest(ParseFacebookUtils.getSession(),
//				new Request.GraphUserCallback() {
//					@Override
//					public void onCompleted(GraphUser user, Response response) {
//						if (user != null) {
//							// Populate the JSON object
//							CIUser.setFacebookid(user.getId());
//							CIUser.setName(user.getName());
//							if (user.getLocation().getProperty("name") != null) {
//								CIUser.setLocation((String) user.getLocation().getProperty("name"));
//							}
//							if (user.getProperty("gender") != null) {
//								CIUser.setGender((String) user.getProperty("gender"));
//							}
//							if (user.getBirthday() != null) {
//								CIUser.setBirthday(user.getBirthday());
//							}
//
//							// Save the user profile info in a user property
//							ParseHelper.saveUser(CIUser.getCurrentUser(), null);
//
//							// Show the user info
//							updateViewsWithProfileInfo();
//						} else if (response.getError() != null) {
//							if ((response.getError().getCategory() == FacebookRequestError.Category.AUTHENTICATION_RETRY)
//									|| (response.getError().getCategory() == FacebookRequestError.Category.AUTHENTICATION_REOPEN_SESSION)) {
//								Log.d(ChaseItApplication.TAG,
//										"The facebook session was invalidated.");
//								onLogoutButtonClicked();
//							} else {
//								Log.d(ChaseItApplication.TAG,
//										"Some other error: "
//												+ response.getError()
//														.getErrorMessage());
//							}
//						}
//					}
//				});
//		request.executeAsync();
//
//	}
//
//	private void updateViewsWithProfileInfo() {
//		if (CIUser.getFacebookid() != null) {
//				userProfilePictureView.setProfileId(CIUser.getFacebookid());
//			} else {
//				// Show the default, blank user profile picture
//				userProfilePictureView.setProfileId(null);
//			}
//			if (CIUser.getName() != null) {
//				userNameView.setText(CIUser.getName());
//			} else {
//				userNameView.setText("");
//			}
//			if (CIUser.getLocation() != null) {
//				userLocationView.setText(CIUser.getLocation());
//			} else {
//				userLocationView.setText("");
//			}
//			if (CIUser.getGender() != null) {
//				userGenderView.setText(CIUser.getGender());
//			} else {
//				userGenderView.setText("");
//			}
//			if (CIUser.getBirthday() != null) {
//				userDateOfBirthView.setText(CIUser.getBirthday());
//			} else {
//				userDateOfBirthView.setText("");
//			}
//	}
//
//	private void onLogoutButtonClicked() {
//		// Log the user out
//		ParseUser.logOut();
//
//		// Go to the login view
//		startLoginActivity();
//	}
//
//	private void startLoginActivity() {
//		Intent intent = new Intent(this, LoginActivity.class);
//		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		startActivity(intent);
//	}
//}
