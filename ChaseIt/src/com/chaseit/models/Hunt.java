package com.chaseit.models;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.chaseit.R;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Hunt")
public class Hunt extends ParseObject {
	protected String name;
	protected String details;
	protected int difficulty;
	protected double avgRating;
	protected int numRatings;
	protected double totalDistance;
	protected ParseUser creator;
	protected ParseGeoPoint startLocation;
	protected ParseFile huntPicture;
	
	public Hunt(){
		//empty constructor
	}
		
	/**
	 * @return the name
	 */
	public String getName() {
		return getString("name");
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		put("name", name);
	}

	/**
	 * @return the details
	 */
	public String getDetails() {
		return getString("details");
	}

	/**
	 * @param details the details to set
	 */
	public void setDetails(String details) {
		put("details", details);
	}

	/**
	 * @return the difficulty
	 */
	public int getDifficulty() {
		return getInt("difficulty");
	}

	/**
	 * @param difficulty the difficulty to set
	 */
	public void setDifficulty(int difficulty) {
		put("difficulty", difficulty);
	}

	/**
	 * @return the avgRating
	 */
	public double getAvgRating() {
		return getDouble("avgrating");
	}

	/**
	 * @param avgRating the avgRating to set
	 */
	public void setAvgRating(double avgRating) {
		put("avgrating", avgRating);
	}

	/**
	 * @return the numRatings
	 */
	public int getNumRatings() {
		return getInt("numratings");
	}

	/**
	 * @param numRatings the numRatings to set
	 */
	public void setNumRatings(int numRatings) {
		put("numratings", numRatings);
	}

	/**
	 * @return the totalDistance
	 */
	public double getTotalDistance() {
		return getDouble("totaldistance");
	}

	/**
	 * @param totalDistance the totalDistance to set
	 */
	public void setTotalDistance(double totalDistance) {
		put("totaldistance", totalDistance);
	}

	/**
	 * @return the creator
	 */
	public ParseUser getCreator() {
		return (ParseUser)getParseObject("creator");
	}

	/**
	 * @param creator the creator to set
	 */
	public void setCreator(ParseUser creator) {
		put("creator", creator);
	}

	/**
	 * @return the startLocation
	 */
	public ParseGeoPoint getStartLocation() {
		return (ParseGeoPoint)getParseGeoPoint("startlocation");
	}

	/**
	 * @param startLocation the startLocation to set
	 */
	public void setStartLocation(ParseGeoPoint startLocation) {
		put("startlocation", startLocation);
	}

	/**
	 * @return the huntPicture
	 */
	public ParseFile getHuntPicture() {
		return getParseFile("huntpicture");
	}

	/**
	 * @param huntPicture the huntPicture to set
	 */
	public void setHuntPicture(ParseFile huntPicture) {
		put("huntpicture", huntPicture);
	}

	public static List<Hunt> createSampleHunts(Context c) {
		List<Hunt> huntList = new ArrayList<Hunt>();
		Hunt hunt = new DummyHunt();
		hunt.setName(c.getResources().getString(R.string.hunt_name_example));
		hunt.setDetails(c.getResources().getString(R.string.hunt_details_example));
		huntList.add(hunt);

		hunt = new DummyHunt();
		hunt.setName(c.getResources().getString(R.string.hunt_name_example));
		hunt.setDetails(c.getResources().getString(R.string.hunt_details_example));
		huntList.add(hunt);

		hunt = new DummyHunt();
		hunt.setName(c.getResources().getString(R.string.hunt_name_example));
		hunt.setDetails(c.getResources().getString(R.string.hunt_details_example));
		huntList.add(hunt);

		hunt = new DummyHunt();
		hunt.setName(c.getResources().getString(R.string.hunt_name_example));
		hunt.setDetails(c.getResources().getString(R.string.hunt_details_example));
		huntList.add(hunt);

		hunt = new DummyHunt();
		hunt.setName(c.getResources().getString(R.string.hunt_name_example));
		hunt.setDetails(c.getResources().getString(R.string.hunt_details_example));
		huntList.add(hunt);

		return huntList;
	}
}
