package com.chaseit;

import com.parse.ParseUser;
import com.parse.SaveCallback;

public class ParseHelper {
	
	public static void saveUser(ParseUser user, SaveCallback callback){
		if(callback == null){
			user.saveInBackground();
		} else {
			user.saveInBackground(callback);
		}
	}

	
	
}
