package com.chaseit.models.wrappers;

import java.io.Serializable;

import com.parse.ParseUser;

public class UserWrapper implements Serializable{
	private static final long serialVersionUID = -5443336070462246001L;
	private ParseObjectWrapper wrapper;
	
	public UserWrapper(ParseObjectWrapper userWrapper) {
		wrapper = userWrapper;
	}
	
	public UserWrapper(ParseUser user) {
		wrapper = new ParseObjectWrapper(user);
	}

	public String getObjectId(){
		return wrapper.getString("objectId");
	}

	public String getUserName(){
		return wrapper.getString("username");
	}

	public String getBirthday(){
		return wrapper.getString("birthday");
	}

	public String getEmail(){
		return wrapper.getString("email");
	}

	public String getFacebookId(){
		return wrapper.getString("facebookId");
	}

	public String getLocation(){
		return wrapper.getString("location");
	}

	
	public String getName(){
		return wrapper.getString("name");
	}


}
