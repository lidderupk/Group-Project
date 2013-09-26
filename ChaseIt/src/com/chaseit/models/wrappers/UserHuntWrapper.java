package com.chaseit.models.wrappers;

import java.io.Serializable;

import com.chaseit.models.UserHunt;
import com.chaseit.models.UserHunt.HuntStatus;

public class UserHuntWrapper implements Serializable {
	private static final long serialVersionUID = 9048914257529310101L;
	private ParseObjectWrapper wrapper;

	public String getObjectId() {
		return wrapper.getString(ParseObjectWrapper.OBJECTID_TAG);
	}

	public UserHuntWrapper(ParseObjectWrapper userHuntWrapper) {
		wrapper = userHuntWrapper;
	}

	public UserHuntWrapper(UserHunt userHunt) {
		wrapper = new ParseObjectWrapper(userHunt);
	}

	public String getUserObjectId() {
		return wrapper.getString(UserHunt.USERHUNT_USEROBJECTID_TAG);
	}

	public String getLocationObjectId() {
		return wrapper.getString(UserHunt.USERHUNT_LOCATIONOBJECTID_TAG);
	}

	public HuntStatus getHuntStatus() {
		return HuntStatus.valueOf(wrapper
				.getString(UserHunt.USERHUNT_HUNTSTATUS_TAG));
	}

	public String getHuntObjectId() {
		return wrapper.getString(UserHunt.USERHUNT_HUNTOBJECTID_TAG);
	}

	public double getLastLocationLong() {
		return wrapper.getDouble(UserHunt.USERHUNT_LASTLOCATIONLONGITUDE_TAG);
	}

	public double getLastLocationLat() {
		return wrapper.getDouble(UserHunt.USERHUNT_LASTLOCATIONLATITUDE_TAG);
	}

	public int getLocationIndex() {
		return wrapper.getInt(UserHunt.USERHUNT_LOCATIONINDEX_TAG);
	}
}
