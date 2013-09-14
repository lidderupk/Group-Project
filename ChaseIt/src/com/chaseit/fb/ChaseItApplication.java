package com.chaseit.fb;

import android.app.Application;

import com.chaseit.fb.R;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;

public class ChaseItApplication extends Application {

	static final String TAG = "MyApp";

	@Override
	public void onCreate() {
		super.onCreate();

		Parse.initialize(this, "uH7SFyWs6YTPjIFrejvVNLXL4Zpq9RrFHjeuna2M",
				"DuRlsH6bLO1RrSuKZBTIeGzkRYJWvwfT6qd1txGB");

		// Set your Facebook App Id in strings.xml
		ParseFacebookUtils.initialize(getString(R.string.app_id));

	}

}
