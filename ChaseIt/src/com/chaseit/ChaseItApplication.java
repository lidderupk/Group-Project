package com.chaseit;

import android.app.Application;

import com.chaseit.models.DummyHunt;
import com.chaseit.models.Hunt;
import com.chaseit.models.Location;
import com.chaseit.models.UserHunt;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class ChaseItApplication extends Application {

	public static final String TAG = "CHASE_IT";

	@Override
	public void onCreate() {
		super.onCreate();

		// register all models
		ParseObject.registerSubclass(Location.class);
		ParseObject.registerSubclass(Hunt.class);
		ParseObject.registerSubclass(DummyHunt.class);
		// ParseObject.registerSubclass(HuntImage.class);
		ParseObject.registerSubclass(UserHunt.class);
		// init app
		Parse.initialize(this, "uH7SFyWs6YTPjIFrejvVNLXL4Zpq9RrFHjeuna2M",
				"DuRlsH6bLO1RrSuKZBTIeGzkRYJWvwfT6qd1txGB");

		// Set your Facebook App Id in strings.xml
		ParseFacebookUtils.initialize(getString(R.string.app_id));

		// security related attributes
		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();
		defaultACL.setPublicReadAccess(true);
		ParseACL.setDefaultACL(defaultACL, true);

	}

}
