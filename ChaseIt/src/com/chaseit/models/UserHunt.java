package com.chaseit.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("UserHunt")
public class UserHunt extends ParseObject {

	public static final String USERHUNT_USEROBJECTID_TAG = "userobjectId";
	public static final String USERHUNT_HUNTOBJECTID_TAG = "huntobjectId";
	public static final String USERHUNT_LASTLOCATIONOBJECTID_TAG = "locationobjectId";
	public static final String USERHUNT_LASTLOCATIONLATITUDE_TAG = "lastlocationLatitude";
	public static final String USERHUNT_LASTLOCATIONLONGITUDE_TAG = "lastlocationLongitude";
	public static final String USERHUNT_LOCATIONINDEX_TAG = "locationindex";
	public static final String USERHUNT_HUNTSTATUS_TAG = "huntstatus";

	public enum HuntStatus {
		IN_PROGRESS, COMPLETED
	};

	public UserHunt() {
		// empty constructor
	}

	public void setUserObjectId(String userObjectId) {
		put(USERHUNT_USEROBJECTID_TAG, userObjectId);
	}

	public String getUserObjectId() {
		return getString(USERHUNT_USEROBJECTID_TAG);
	}

	public void setHuntObjectId(String huntObjectId) {
		put(USERHUNT_HUNTOBJECTID_TAG, huntObjectId);
	}

	public String getHuntObjectId() {
		return getString(USERHUNT_HUNTOBJECTID_TAG);
	}

	public void setLastLocationObjectId(String locationObjectId) {
		put(USERHUNT_LASTLOCATIONOBJECTID_TAG, locationObjectId);
	}

	public String getLastLocationObjectId() {
		return getString(USERHUNT_LASTLOCATIONOBJECTID_TAG);
	}

	public void setLastLocationLat(double latitude) {
		put(USERHUNT_LASTLOCATIONLATITUDE_TAG, latitude);
	}

	public double getLastLocationLat() {
		return getDouble(USERHUNT_LASTLOCATIONLATITUDE_TAG);
	}

	public void setLastLocationLong(double longitude) {
		put(USERHUNT_LASTLOCATIONLONGITUDE_TAG, longitude);
	}

	public double getLastLocationLong() {
		return getDouble(USERHUNT_LASTLOCATIONLONGITUDE_TAG);
	}

	public void setLocationIndex(int index) {
		put(USERHUNT_LOCATIONINDEX_TAG, index);
	}

	public int getLocationIndex() {
		return getInt(USERHUNT_LOCATIONINDEX_TAG);
	}

	public void setHuntStatus(HuntStatus status) {
		put(USERHUNT_HUNTSTATUS_TAG, status.toString());
	}

	public HuntStatus getHuntStatus() {
		return HuntStatus.valueOf(getString(USERHUNT_HUNTSTATUS_TAG));
	}

}
