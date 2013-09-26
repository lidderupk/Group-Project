package com.chaseit.models;

import com.parse.ParseUser;

public class CIUser {

	public static final String CIUSER_FACEBOOKID_TAG = "facebookid";
	public static final String CIUSER_NAME_TAG = "name";
	public static final String CIUSER_LOCATION_TAG = "location";
	public static final String CIUSER_GENDER_TAG = "gender";
	public static final String CIUSER_BIRTHDAY_TAG = "birthday";

	private CIUser() {
		// empty constructor
	}

	/**
	 * @return the facebookid
	 */
	public static String getFacebookid() {
		return ParseUser.getCurrentUser().getString(CIUSER_FACEBOOKID_TAG);
	}

	/**
	 * @param facebookid
	 *            the facebookid to set
	 */
	public static void setFacebookid(String facebookid) {
		ParseUser.getCurrentUser().put(CIUSER_FACEBOOKID_TAG, facebookid);
	}

	/**
	 * @return the name
	 */
	public static String getName() {
		return ParseUser.getCurrentUser().getString(CIUSER_NAME_TAG);
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public static void setName(String name) {
		ParseUser.getCurrentUser().put(CIUSER_NAME_TAG, name);
	}

	/**
	 * @return the location
	 */
	public static String getLocation() {
		return ParseUser.getCurrentUser().getString(CIUSER_LOCATION_TAG);
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public static void setLocation(String location) {
		ParseUser.getCurrentUser().put(CIUSER_LOCATION_TAG, location);
	}

	/**
	 * @return the gender
	 */
	public static String getGender() {
		return ParseUser.getCurrentUser().getString(CIUSER_GENDER_TAG);
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public static void setGender(String gender) {
		ParseUser.getCurrentUser().put(CIUSER_GENDER_TAG, gender);
	}

	/**
	 * @return the birthday
	 */
	public static String getBirthday() {
		return ParseUser.getCurrentUser().getString(CIUSER_BIRTHDAY_TAG);
	}

	/**
	 * @param birthday
	 *            the birthday to set
	 */
	public static void setBirthday(String birthday) {
		ParseUser.getCurrentUser().put(CIUSER_BIRTHDAY_TAG, birthday);
	}

	public static ParseUser getCurrentUser() {
		return ParseUser.getCurrentUser();
	}

}
