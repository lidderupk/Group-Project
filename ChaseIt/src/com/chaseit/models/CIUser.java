package com.chaseit.models;

import com.parse.ParseUser;

public class CIUser {	
	
	private CIUser(){
		//empty constructor	
	}
	
	/**
	 * @return the facebookid
	 */
	public static String getFacebookid() {
		return ParseUser.getCurrentUser().getString("facebookid");
	}
	
	/**
	 * @param facebookid the facebookid to set
	 */
	public static void setFacebookid(String facebookid) {
		ParseUser.getCurrentUser().put("facebookid", facebookid);
	}
	
	/**
	 * @return the name
	 */
	public static String getName() {
		return ParseUser.getCurrentUser().getString("name");
	}
	
	/**
	 * @param name the name to set
	 */
	public static void setName(String name) {
		ParseUser.getCurrentUser().put("name", name);
	}
	
	/**
	 * @return the location
	 */
	public static String getLocation() {
		return ParseUser.getCurrentUser().getString("location");
	}
	
	/**
	 * @param location the location to set
	 */
	public static void setLocation(String location) {
		ParseUser.getCurrentUser().put("location", location);
	}
	
	/**
	 * @return the gender
	 */
	public static String getGender() {
		return ParseUser.getCurrentUser().getString("gender");
	}
	
	/**
	 * @param gender the gender to set
	 */
	public static void setGender(String gender) {
		ParseUser.getCurrentUser().put("gender", gender);
	}
	
	/**
	 * @return the birthday
	 */
	public static String getBirthday() {
		return ParseUser.getCurrentUser().getString("birthday");
	}
	
	/**
	 * @param birthday the birthday to set
	 */
	public static void setBirthday(String birthday) {
		ParseUser.getCurrentUser().put("birthday", birthday);
	}
	
//	public static void save(){
//		ParseUser.getCurrentUser().saveInBackground();
//	}
	
}
