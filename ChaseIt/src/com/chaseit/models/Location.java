package com.chaseit.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

@ParseClassName("Location")
public class Location extends ParseObject {

	public Location() {
		// empty constructor
	}

	/**
	 * @return the locationName
	 */
	public String getLocationName() {
		return getString("locationname");
	}

	/**
	 * @param locationName
	 *            the locationName to set
	 */
	public void setLocationName(String locationName) {
		put("locationname", locationName);
	}

	/**
	 * @return the location
	 */
	public ParseGeoPoint getLocation() {
		return getParseGeoPoint("location");
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(ParseGeoPoint location) {
		put("location", location);
	}

	/**
	 * @return the hint
	 */
	public String getHint() {
		return getString("hint");
	}

	/**
	 * @param hint
	 *            the hint to set
	 */
	public void setHint(String hint) {
		put("hint", hint);
	}

	/**
	 * @return the image
	 */
	public ParseFile getImage() {
		return getParseFile("image");
	}

	/**
	 * @param image
	 *            the image to set
	 */
	public void setImage(ParseFile image) {
		put("image", image);
	}

	/**
	 * @returns the parent hunt for this location
	 */
	public Hunt getParentHunt() {
		return (Hunt) getParseObject("parentHunt");
	}

	/**
	 * @param the
	 *            parent hunt this location belongs to
	 */
	public void setParentHunt(Hunt parent) {
		put("parenthunt", parent);
	}

	/**
	 * @return get the index in the hunt
	 */
	public int getIndexInHunt() {
		return getInt("index");
	}

	/**
	 * @param index
	 *            in the hunt
	 */
	public void setIndexInHunt(int index) {
		put("index", index);
	}

}
