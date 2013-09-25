package com.chaseit.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("UserHunt")
public class UserHunt extends ParseObject {

	public enum HuntStatus {
		IN_PROGRESS, COMPLETED
	};

	public UserHunt() {
		// empty constructor
	}

	public void setUserObjectId(String userObjectId) {
		put("userobjectId", userObjectId);
	}

	public String getUserObjectId() {
		return getString("userobjectId");
	}

	public void setHuntObjectId(String huntObjectId) {
		put("huntobjectId", huntObjectId);
	}

	public String getHuntObjectId() {
		return getString("huntobjectId");
	}

	public void setLocationObjectId(String locationObjectId) {
		put("locationobjectId", locationObjectId);
	}

	public String getLocationObjectId() {
		return getString("locationobjectId");
	}

	public void setLastLocationLat(double latitude) {
		put("lastlocationLatitude", latitude);
	}

	public double getLastLocationLat() {
		return getDouble("lastlocationLatitude");
	}
	
	public void setLastLocationLong(double longitude) {
		put("lastlocationLongitude", longitude);
	}

	public double getLastLocationLong() {
		return getDouble("lastlocationLongitude");
	}

	public void setLocationIndex(int index) {
		put("locationindex", index);
	}

	public int getLocationIndex() {
		return getInt("locationindex");
	}
	

	public void setHuntStatus(HuntStatus status) {
		put("huntstatus", status.toString());
	}

	public HuntStatus getHuntStatus() {
		return HuntStatus.valueOf(getString("huntstatus"));
	}

}
