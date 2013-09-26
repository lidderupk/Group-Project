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
	protected String locality;

	public static final String HUNT_NAME_TAG = "name";
	public static final String HUNT_DETAILS_TAG = "details";
	public static final String HUNT_DIFFICULTY_TAG = "difficulty";
	public static final String HUNT_AVGRATING_TAG = "avgrating";
	public static final String HUNT_NUMRATING_TAG = "numratings";
	public static final String HUNT_TOTALDISTANCE_TAG = "totaldistance";
	public static final String HUNT_CRATOR_TAG = "creator";
	public static final String HUNT_CRATOR_NAME_TAG = "creatorName";
	public static final String HUNT_STARTLOCATION_TAG = "startlocation";
	public static final String HUNT_PICTURE_TAG = "huntpicture";
	public static final String HUNT_LOCALITY_TAG = "locality";

	public Hunt() {
		// empty constructor
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return getString(HUNT_NAME_TAG);
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		put(HUNT_NAME_TAG, name);
	}

	/**
	 * @return the details
	 */
	public String getDetails() {
		return getString(HUNT_DETAILS_TAG);
	}

	/**
	 * @param details
	 *            the details to set
	 */
	public void setDetails(String details) {
		put(HUNT_DETAILS_TAG, details);
	}

	/**
	 * @return the difficulty
	 */
	public int getDifficulty() {
		return getInt(HUNT_DIFFICULTY_TAG);
	}

	/**
	 * @param difficulty
	 *            the difficulty to set
	 */
	public void setDifficulty(int difficulty) {
		put(HUNT_DIFFICULTY_TAG, difficulty);
	}

	/**
	 * @return the avgRating
	 */
	public double getAvgRating() {
		return getDouble(HUNT_AVGRATING_TAG);
	}

	/**
	 * @param avgRating
	 *            the avgRating to set
	 */
	public void setAvgRating(double avgRating) {
		put(HUNT_AVGRATING_TAG, avgRating);
	}

	/**
	 * @return the numRatings
	 */
	public int getNumRatings() {
		return getInt(HUNT_NUMRATING_TAG);
	}

	/**
	 * @param numRatings
	 *            the numRatings to set
	 */
	public void setNumRatings(int numRatings) {
		put(HUNT_NUMRATING_TAG, numRatings);
	}

	/**
	 * @return the totalDistance
	 */
	public double getTotalDistance() {
		return getDouble(HUNT_TOTALDISTANCE_TAG);
	}

	/**
	 * @param totalDistance
	 *            the totalDistance to set
	 */
	public void setTotalDistance(double totalDistance) {
		put(HUNT_TOTALDISTANCE_TAG, totalDistance);
	}

	/**
	 * @return the creator
	 */
	public ParseUser getCreator() {
		return (ParseUser) getParseObject(HUNT_CRATOR_TAG);
	}

	/**
	 * @return the creator name
	 */
	public ParseUser getCreatorName() {
		return (ParseUser) getParseObject(HUNT_CRATOR_NAME_TAG);
	}

	/**
	 * @param creator
	 *            the creator to set
	 */
	public void setCreator(ParseUser creator) {
		put(HUNT_CRATOR_TAG, creator);
		put(HUNT_CRATOR_NAME_TAG, creator.getString("name"));
	}

	/**
	 * @return the startLocation
	 */
	public ParseGeoPoint getStartLocation() {
		return (ParseGeoPoint) getParseGeoPoint(HUNT_STARTLOCATION_TAG);
	}

	/**
	 * @param startLocation
	 *            the startLocation to set
	 */
	public void setStartLocation(ParseGeoPoint startLocation) {
		put(HUNT_STARTLOCATION_TAG, startLocation);
	}

	/**
	 * @return the huntPicture
	 */
	public ParseFile getHuntPicture() {
		return getParseFile(HUNT_PICTURE_TAG);
	}

	/**
	 * @param huntPicture
	 *            the huntPicture to set
	 */
	public void setHuntPicture(ParseFile huntPicture) {
		put(HUNT_PICTURE_TAG, huntPicture);
	}

	/**
	 * @return the locality
	 */
	public String getLocality() {
		return getString(HUNT_LOCALITY_TAG);
	}

	/**
	 * @param locality
	 *            the locality to set
	 */
	public void setLocality(String locality) {
		put(HUNT_LOCALITY_TAG, locality);
	}

	public static List<Hunt> createSampleHunts(Context c) {
		List<Hunt> huntList = new ArrayList<Hunt>();
		Hunt hunt = new DummyHunt();
		hunt.setName(c.getResources().getString(R.string.hunt_name_example));
		hunt.setDetails(c.getResources().getString(
				R.string.hunt_details_example));
		huntList.add(hunt);

		hunt = new DummyHunt();
		hunt.setName(c.getResources().getString(R.string.hunt_name_example));
		hunt.setDetails(c.getResources().getString(
				R.string.hunt_details_example));
		huntList.add(hunt);

		hunt = new DummyHunt();
		hunt.setName(c.getResources().getString(R.string.hunt_name_example));
		hunt.setDetails(c.getResources().getString(
				R.string.hunt_details_example));
		huntList.add(hunt);

		hunt = new DummyHunt();
		hunt.setName(c.getResources().getString(R.string.hunt_name_example));
		hunt.setDetails(c.getResources().getString(
				R.string.hunt_details_example));
		huntList.add(hunt);

		hunt = new DummyHunt();
		hunt.setName(c.getResources().getString(R.string.hunt_name_example));
		hunt.setDetails(c.getResources().getString(
				R.string.hunt_details_example));
		huntList.add(hunt);

		return huntList;
	}
}
