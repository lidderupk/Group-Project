package com.chaseit.models.wrappers;

import java.io.Serializable;

import com.chaseit.ParseHelper;
import com.chaseit.models.Hunt;
import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseFile;

public class HuntWrapper implements Serializable {
	private static final long serialVersionUID = 6043642531310587835L;
	private ParseObjectWrapper wrapper;

	public HuntWrapper(Hunt h) {
		wrapper = new ParseObjectWrapper(h);
	}

	public HuntWrapper(ParseObjectWrapper h) {
		wrapper = h;
	}

	public String getObjectId() {
		return wrapper.getString(ParseHelper.OBJECTID_TAG);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return wrapper.getString(Hunt.HUNT_NAME_TAG);
	}

	/**
	 * @return the details
	 */
	public String getDetails() {
		return wrapper.getString(Hunt.HUNT_DETAILS_TAG);
	}

	/**
	 * @return the difficulty
	 */
	public int getDifficulty() {
		return wrapper.getInt(Hunt.HUNT_DIFFICULTY_TAG);
	}

	/**
	 * @return the avgRating
	 */
	public double getAvgRating() {
		return wrapper.getDouble(Hunt.HUNT_AVGRATING_TAG);
	}

	/**
	 * @return the numRatings
	 */
	public int getNumRatings() {
		return wrapper.getInt(Hunt.HUNT_NUMRATING_TAG);
	}

	/**
	 * @return the totalDistance
	 */
	public double getTotalDistance() {
		return wrapper.getDouble(Hunt.HUNT_TOTALDISTANCE_TAG);
	}

	/**
	 * @return the creator
	 */
	public UserWrapper getCreator() {
		return new UserWrapper(wrapper.getParseUser(Hunt.HUNT_CRATOR_TAG));
	}

	/**
	 * @return the creator
	 */
	public String getCreatorName() {
		return wrapper.getString(Hunt.HUNT_CRATOR_NAME_TAG);
	}

	
	/**
	 * @return the startLocation
	 */
	public LatLng getStartLocation() {
		return (LatLng) wrapper.getLocation(Hunt.HUNT_STARTLOCATION_TAG);
	}

	/**
	 * @return the huntPicture
	 */
	public ParseFile getHuntPicture() {
		return wrapper.getParseFile(Hunt.HUNT_PICTURE_TAG);
	}

	/**
	 * @return the locality
	 */
	public String getLocality() {
		return wrapper.getString(Hunt.HUNT_LOCALITY_TAG);
	}

}
