package com.chaseit.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("UserHunts")
public class UserHunt extends ParseObject{
	
	public UserHunt(){
		//empty constructor
	}
	
	public void setUser(ParseUser user){
		put("inprogressuser", user);
	}

	public ParseUser getUser(){
		return getParseUser("inprogressuser");
	}

	public void setHunt(Hunt hunt){
		put("inprogresshunt", hunt);
	}

	public Hunt getHunt(){
		return (Hunt)getParseObject("inprogresshunt");
	}

	public void setLastLocation(Location location){
		put("lastlocation", location);
	}

	public Location getLastLocation(){
		return (Location)getParseObject("lastlocation");
	}

}
