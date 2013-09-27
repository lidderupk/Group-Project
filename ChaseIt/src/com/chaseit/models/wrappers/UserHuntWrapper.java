package com.chaseit.models.wrappers;

import java.io.Serializable;

import com.chaseit.models.UserHunt;
import com.chaseit.models.UserHunt.HuntStatus;

public class UserHuntWrapper implements Serializable{
	private static final long serialVersionUID = 9048914257529310101L;
	private ParseObjectWrapper wrapper;
	
	public String getObjectId(){
		return wrapper.getString("objectId");
	}

	public UserHuntWrapper(ParseObjectWrapper userHuntWrapper) {
		wrapper = userHuntWrapper;
	}
	
	public UserHuntWrapper(UserHunt userHunt) {
		wrapper = new ParseObjectWrapper(userHunt);
	}

	public UserWrapper getUser() {
		return new UserWrapper(wrapper.getParseUser("user"));
	}

	public HuntWrapper getHunt() {
		return new HuntWrapper(wrapper.getParseObject("hunt"));
	}

	public LocationWrapper getLastLocation() {
		return new LocationWrapper(wrapper.getParseObject("lastlocation"));
	}

	public HuntStatus getHuntStatus() {
		return HuntStatus.valueOf(wrapper.getString("huntstatus"));
	}

}
