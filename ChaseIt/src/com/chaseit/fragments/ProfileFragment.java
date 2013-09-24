package com.chaseit.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chaseit.R;

public class ProfileFragment extends Fragment {
	//
	// private ProfilePictureView userProfilePictureView;
	// private TextView userNameView;
	// private TextView userLocationView;
	// private TextView userGenderView;
	// private TextView userDateOfBirthView;
	// private Button logoutButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_container, container,
				false);
		return view;
	}

	// userProfilePictureView = (ProfilePictureView)
	// getActivity().findViewById(R.id.userProfilePicture);
	// userNameView = (TextView) getActivity().findViewById(R.id.userName);
	// userLocationView = (TextView)
	// getActivity().findViewById(R.id.userLocation);
	// userGenderView = (TextView) getActivity().findViewById(R.id.userGender);
	// userDateOfBirthView = (TextView)
	// getActivity().findViewById(R.id.userDateOfBirth);
	//
	// logoutButton = (Button) getActivity().findViewById(R.id.logoutButton);
	// logoutButton.setOnClickListener(new View.OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// onLogoutButtonClicked();
	// }
	// });

	// Fetch Facebook user info if the session is active
	// Session session = ParseFacebookUtils.getSession();
	// if (session != null && session.isOpened()) {
	// makeMeRequest();
	// }
	// }

	// @Override
	// public void onResume() {
	// super.onResume();
	//
	// ParseUser currentUser = ParseUser.getCurrentUser();
	// if (currentUser != null) {
	// // Check if the user is currently logged
	// // and show any cached content
	// updateViewsWithProfileInfo();
	// } else {
	// // If the user is not logged in, go to the
	// // activity showing the login view.
	// startLoginActivity();
	// }
	// }

	// private void makeMeRequest() {
	// Request request = Request.newMeRequest(ParseFacebookUtils.getSession(),
	// new Request.GraphUserCallback() {
	// @Override
	// public void onCompleted(GraphUser user, Response response) {
	// if (user != null) {
	// // Populate the JSON object
	// CIUser.setFacebookid(user.getId());
	// CIUser.setName(user.getName());
	// if (user.getLocation().getProperty("name") != null) {
	// CIUser.setLocation((String) user.getLocation()
	// .getProperty("name"));
	// }
	// if (user.getProperty("gender") != null) {
	// CIUser.setGender((String) user
	// .getProperty("gender"));
	// }
	// if (user.getBirthday() != null) {
	// CIUser.setBirthday(user.getBirthday());
	// }
	//
	// // Save the user profile info in a user property
	// ParseHelper.saveUser(CIUser.getCurrentUser(), null);
	//
	// // Show the user info
	// updateViewsWithProfileInfo();
	// } else if (response.getError() != null) {
	// if ((response.getError().getCategory() ==
	// FacebookRequestError.Category.AUTHENTICATION_RETRY)
	// || (response.getError().getCategory() ==
	// FacebookRequestError.Category.AUTHENTICATION_REOPEN_SESSION)) {
	// Log.d(ChaseItApplication.TAG,
	// "The facebook session was invalidated.");
	// onLogoutButtonClicked();
	// } else {
	// Log.d(ChaseItApplication.TAG,
	// "Some other error: "
	// + response.getError()
	// .getErrorMessage());
	// }
	// }
	// }
	// });
	// request.executeAsync();
	//
	// }

	// private void updateViewsWithProfileInfo() {
	// if (CIUser.getFacebookid() != null) {
	// userProfilePictureView.setProfileId(CIUser.getFacebookid());
	// } else {
	// // Show the default, blank user profile picture
	// userProfilePictureView.setProfileId(null);
	// }
	// if (CIUser.getName() != null) {
	// userNameView.setText(CIUser.getName());
	// } else {
	// userNameView.setText("");
	// }
	// if (CIUser.getLocation() != null) {
	// userLocationView.setText(CIUser.getLocation());
	// } else {
	// userLocationView.setText("");
	// }
	// if (CIUser.getGender() != null) {
	// userGenderView.setText(CIUser.getGender());
	// } else {
	// userGenderView.setText("");
	// }
	// if (CIUser.getBirthday() != null) {
	// userDateOfBirthView.setText(CIUser.getBirthday());
	// } else {
	// userDateOfBirthView.setText("");
	// }
	// }
	//
	// private void onLogoutButtonClicked() {
	// // Log the user out
	// ParseUser.logOut();
	//
	// // Go to the login view
	// startLoginActivity();
	// }

	// private void startLoginActivity() {
	// Intent intent = new Intent(this, LoginActivity.class);
	// intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	// intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	// startActivity(intent);
	// }

}
