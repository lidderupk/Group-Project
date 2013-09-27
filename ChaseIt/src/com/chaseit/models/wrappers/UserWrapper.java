package com.chaseit.models.wrappers;

import java.io.Serializable;

import com.chaseit.ParseHelper;
import com.chaseit.models.CIUser;
import com.parse.ParseUser;

public class UserWrapper implements Serializable{
	private static final long serialVersionUID = -5443336070462246001L;
	private ParseObjectWrapper wrapper;
	
	public static final String USERWRAPPER_EMAIL_TAG = "email";
	public static final String USERWRAPPER_USERNAME_TAG = "username";
	
	public UserWrapper(ParseObjectWrapper userWrapper) {
		wrapper = userWrapper;
	}
	
	public UserWrapper(ParseUser user) {
		wrapper = new ParseObjectWrapper(user);
	}

	public String getObjectId(){
		return wrapper.getString(ParseHelper.OBJECTID_TAG);
	}

	public String getUserName(){
		return wrapper.getString(USERWRAPPER_USERNAME_TAG);
	}

	public String getBirthday(){
		return wrapper.getString(CIUser.CIUSER_BIRTHDAY_TAG);
	}

	public String getEmail(){
		return wrapper.getString(USERWRAPPER_EMAIL_TAG);
	}

	public String getFacebookId(){
		return wrapper.getString(CIUser.CIUSER_FACEBOOKID_TAG);
	}

	public String getLocation(){
		return wrapper.getString(CIUser.CIUSER_LOCATION_TAG);
	}

	
	public String getName(){
		return wrapper.getString(CIUser.CIUSER_NAME_TAG);
	}


}
