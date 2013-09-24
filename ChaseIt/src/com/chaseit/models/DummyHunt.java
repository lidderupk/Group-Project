package com.chaseit.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseUser;

@ParseClassName("DummyHunt")
public class DummyHunt extends Hunt {
	public DummyHunt() {
		// empty constructor
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chaseit.models.Hunt#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chaseit.models.Hunt#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chaseit.models.Hunt#getDetails()
	 */
	@Override
	public String getDetails() {
		// TODO Auto-generated method stub
		return this.details;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chaseit.models.Hunt#setDetails(java.lang.String)
	 */
	@Override
	public void setDetails(String details) {
		// TODO Auto-generated method stub
		this.details = details;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chaseit.models.Hunt#getDifficulty()
	 */
	@Override
	public int getDifficulty() {
		// TODO Auto-generated method stub
		return this.difficulty;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chaseit.models.Hunt#setDifficulty(int)
	 */
	@Override
	public void setDifficulty(int difficulty) {
		// TODO Auto-generated method stub
		this.difficulty = difficulty;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chaseit.models.Hunt#getAvgRating()
	 */
	@Override
	public double getAvgRating() {
		// TODO Auto-generated method stub
		return this.avgRating;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chaseit.models.Hunt#setAvgRating(int)
	 */
	@Override
	public void setAvgRating(double avgRating) {
		// TODO Auto-generated method stub
		this.avgRating = avgRating;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chaseit.models.Hunt#getNumRatings()
	 */
	@Override
	public int getNumRatings() {
		// TODO Auto-generated method stub
		return this.numRatings;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chaseit.models.Hunt#setNumRatings(int)
	 */
	@Override
	public void setNumRatings(int numRatings) {
		// TODO Auto-generated method stub
		this.numRatings = numRatings;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chaseit.models.Hunt#getTotalDistance()
	 */
	@Override
	public double getTotalDistance() {
		// TODO Auto-generated method stub
		return this.totalDistance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chaseit.models.Hunt#setTotalDistance(double)
	 */
	@Override
	public void setTotalDistance(double totalDistance) {
		// TODO Auto-generated method stub
		this.totalDistance = totalDistance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chaseit.models.Hunt#getCreator()
	 */
	@Override
	public ParseUser getCreator() {
		// TODO Auto-generated method stub
		return this.creator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chaseit.models.Hunt#setCreator(com.chaseit.models.User)
	 */
	@Override
	public void setCreator(ParseUser creator) {
		// TODO Auto-generated method stub
		this.creator = creator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chaseit.models.Hunt#getStartLocation()
	 */
	@Override
	public ParseGeoPoint getStartLocation() {
		// TODO Auto-generated method stub
		return this.startLocation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chaseit.models.Hunt#setStartLocation(com.chaseit.models.Location)
	 */
	@Override
	public void setStartLocation(ParseGeoPoint startLocation) {
		// TODO Auto-generated method stub
		this.startLocation = startLocation;
	}

	@Override
	public ParseFile getHuntPicture() {
		return this.huntPicture;
	}

	@Override
	public void setHuntPicture(ParseFile huntPicture) {
		this.huntPicture = huntPicture;
	}

}
