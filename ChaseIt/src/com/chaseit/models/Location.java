package com.chaseit.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

@ParseClassName("Location")
public class Location extends ParseObject {

	public static final String LOCATION_NAME_TAG = "locationname";
	public static final String LOCATION_LOCATION_TAG = "location";
	public static final String LOCATION_HINT_TAG = "hint";
	public static final String LOCATION_IMAGE_TAG = "image";
	public static final String LOCATION_PARENTHUNT_TAG = "parentHunt";
	public static final String LOCATION_INDEX_TAG = "index";

	public Location() {
		// empty constructor
	}

	/**
	 * @return the locationName
	 */
	public String getLocationName() {
		return getString(LOCATION_NAME_TAG);
	}

	/**
	 * @param locationName
	 *            the locationName to set
	 */
	public void setLocationName(String locationName) {
		put(LOCATION_NAME_TAG, locationName);
	}

	/**
	 * @return the location
	 */
	public ParseGeoPoint getLocation() {
		return getParseGeoPoint(LOCATION_LOCATION_TAG);
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(ParseGeoPoint location) {
		put(LOCATION_LOCATION_TAG, location);
	}

	/**
	 * @return the hint
	 */
	public String getHint() {
		return getString(LOCATION_HINT_TAG);
	}

	/**
	 * @param hint
	 *            the hint to set
	 */
	public void setHint(String hint) {
		put(LOCATION_HINT_TAG, hint);
	}

	/**
	 * @return the image
	 */
	public ParseFile getImage() {
		return getParseFile(LOCATION_IMAGE_TAG);
	}

	/**
	 * @param image
	 *            the image to set
	 */
	public void setImage(ParseFile image) {
		put(LOCATION_IMAGE_TAG, image);
	}

	/**
	 * @returns the parent hunt for this location
	 */
	public Hunt getParentHunt() {
		return (Hunt) getParseObject(LOCATION_PARENTHUNT_TAG);
	}

	/**
	 * @param the
	 *            parent hunt this location belongs to
	 */
	public void setParentHunt(Hunt parent) {
		put(LOCATION_PARENTHUNT_TAG, parent);
	}

	/**
	 * @return get the index in the hunt
	 */
	public int getIndexInHunt() {
		return getInt(LOCATION_INDEX_TAG);
	}

	/**
	 * @param index
	 *            in the hunt
	 */
	public void setIndexInHunt(int index) {
		put(LOCATION_INDEX_TAG, index);
	}

}
