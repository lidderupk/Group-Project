package com.chaseit.models.wrappers;

import java.io.File;
import java.io.Serializable;

import com.chaseit.models.Hunt;
import com.google.android.gms.maps.model.LatLng;

public class HuntWrapper implements Serializable{
	private static final long serialVersionUID = 6043642531310587835L;
	private ParseObjectWrapper wrapper;
	
	public HuntWrapper(Hunt h){
		wrapper = new ParseObjectWrapper(h);
	}
	
	public String getObjectId(){
		return wrapper.getString("objectId");
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return wrapper.getString("name");
	}

	/**
	 * @return the details
	 */
	public String getDetails() {
		return wrapper.getString("details");
	}

	/**
	 * @return the difficulty
	 */
	public int getDifficulty() {
		return wrapper.getInt("difficulty");
	}

	/**
	 * @return the avgRating
	 */
	public double getAvgRating() {
		return wrapper.getDouble("avgrating");
	}

	/**
	 * @return the numRatings
	 */
	public int getNumRatings() {
		return wrapper.getInt("numratings");
	}

	/**
	 * @return the totalDistance
	 */
	public double getTotalDistance() {
		return wrapper.getDouble("totaldistance");
	}

	/**
	 * @return the creator
	 */
	public ParseObjectWrapper getCreator() {
		return (ParseObjectWrapper)wrapper.getParseUser("creator");
	}

	/**
	 * @return the startLocation
	 */
	public LatLng getStartLocation() {
		return (LatLng) wrapper.getLocation("startlocation");
	}

	/**
	 * @return the huntPicture
	 */
	public File getHuntPicture() {
		return wrapper.getFile("huntpicture");
	}

	/**
	 * @return the locality
	 */
	public String getLocality() {
		return wrapper.getString("locality");
	}

}
