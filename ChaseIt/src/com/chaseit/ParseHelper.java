package com.chaseit;

import java.util.List;

import com.chaseit.models.CIUser;
import com.chaseit.models.Hunt;
import com.chaseit.models.Location;
import com.chaseit.models.UserHunt;
import com.chaseit.models.UserHunt.HuntStatus;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class ParseHelper {
	public static final String CREATED_AT = "createdAt";
	public static final String LATITUDE_TAG = "latitude";
	public static final String LONGITUDE_TAG = "longitude";
	public static final String FILENAME_TAG = "fileName";
	public static final String OBJECTID_TAG = "objectId";

	// information retrieval
	public static void getAllHuntsByCreateDate(FindCallback<Hunt> callback) {
		if (callback == null)
			return;
		ParseQuery<Hunt> query = ParseQuery.getQuery(Hunt.class);
		query.orderByDescending(CREATED_AT);
		query.findInBackground(callback);
	}

	public static void getAllHuntsByAvgRating(FindCallback<Hunt> callback) {
		if (callback == null)
			return;
		ParseQuery<Hunt> query = ParseQuery.getQuery(Hunt.class);
		query.orderByDescending(Hunt.HUNT_AVGRATING_TAG);
		query.findInBackground(callback);
	}

	public static void getAllHuntsByNumRatings(FindCallback<Hunt> callback) {
		if (callback == null)
			return;
		ParseQuery<Hunt> query = ParseQuery.getQuery(Hunt.class);
		query.orderByDescending(Hunt.HUNT_NUMRATING_TAG);
		query.findInBackground(callback);
	}

	public static void getHuntByObjectId(String objectId,
			GetCallback<Hunt> callback) {
		if (callback == null)
			return;
		ParseQuery<Hunt> query = ParseQuery.getQuery(Hunt.class);
		query.whereEqualTo(ParseHelper.OBJECTID_TAG, objectId);
		query.getFirstInBackground(callback);
	}

	public static void getAllHuntsByProximity(ParseGeoPoint currentLocation,
			FindCallback<Hunt> callback) {
		if (callback == null)
			return;
		ParseQuery<Hunt> query = ParseQuery.getQuery(Hunt.class);
		query.whereNear(Hunt.HUNT_STARTLOCATION_TAG, currentLocation);
		query.findInBackground(callback);
	}

	public static void getMyHuntsCreated(FindCallback<Hunt> callback) {
		if (callback == null)
			return;
		ParseQuery<Hunt> query = ParseQuery.getQuery(Hunt.class);
		query.whereEqualTo(Hunt.HUNT_CRATOR_TAG, CIUser.getCurrentUser());
		query.findInBackground(callback);
	}

	public static void getMyHuntsCompleted(FindCallback<UserHunt> callback) {
		if (callback == null)
			return;
		ParseQuery<UserHunt> query = ParseQuery.getQuery(UserHunt.class);
		query.whereEqualTo(UserHunt.USERHUNT_USEROBJECTID_TAG, CIUser
				.getCurrentUser().getObjectId());
		query.whereEqualTo(UserHunt.USERHUNT_HUNTSTATUS_TAG,
				HuntStatus.COMPLETED.toString());
		query.findInBackground(callback);
	}

	public static void getMyHuntsInProgress(FindCallback<UserHunt> callback) {
		if (callback == null)
			return;
		ParseQuery<UserHunt> query = ParseQuery.getQuery(UserHunt.class);
		query.whereEqualTo(UserHunt.USERHUNT_USEROBJECTID_TAG, CIUser
				.getCurrentUser().getObjectId());
		// query.whereEqualTo("huntstatus", HuntStatus.IN_PROGRESS.toString());
		String string = HuntStatus.IN_PROGRESS.toString();
		query.whereEqualTo(UserHunt.USERHUNT_HUNTSTATUS_TAG,
				UserHunt.HuntStatus.IN_PROGRESS.toString());
		// query.whereEqualTo("huntobjectId", "9kmJGW0CXr");
		query.findInBackground(callback);
	}

	public static void getHuntsInProgressForUser(ParseUser user,
			FindCallback<UserHunt> callback) {
		if (callback == null)
			return;
		ParseQuery<UserHunt> query = ParseQuery.getQuery(UserHunt.class);
		query.whereEqualTo(UserHunt.USERHUNT_USEROBJECTID_TAG,
				user.getObjectId());
		query.whereEqualTo(UserHunt.USERHUNT_HUNTSTATUS_TAG,
				UserHunt.HuntStatus.IN_PROGRESS.toString());
		query.findInBackground(callback);
	}

	public static void getHuntInProgressGivenHunt(Hunt hunt,
			FindCallback<UserHunt> callback) {
		if (callback == null)
			return;
		ParseQuery<UserHunt> query = ParseQuery.getQuery(UserHunt.class);
		query.whereEqualTo(UserHunt.USERHUNT_HUNTOBJECTID_TAG,
				hunt.getObjectId());
		query.findInBackground(callback);
	}

	public static void getHuntInProgressGivenHuntAndUser(Hunt hunt,
			ParseUser user, FindCallback<UserHunt> callback) {
		if (callback == null || hunt == null)
			return;
		ParseQuery<UserHunt> query = ParseQuery.getQuery(UserHunt.class);
		query.whereEqualTo(UserHunt.USERHUNT_HUNTOBJECTID_TAG,
				hunt.getObjectId());
		query.whereEqualTo(UserHunt.USERHUNT_HUNTSTATUS_TAG,
				HuntStatus.IN_PROGRESS.toString());
		query.whereEqualTo(UserHunt.USERHUNT_USEROBJECTID_TAG,
				user.getObjectId());
		query.findInBackground(callback);
	}

	public static void getLocationByObjectId(String objectId,
			FindCallback<Location> callback) {
		if (callback == null)
			return;
		ParseQuery<Location> query = ParseQuery.getQuery(Location.class);
		query.whereEqualTo(ParseHelper.OBJECTID_TAG, objectId);
		query.findInBackground(callback);
	}

	public static void getLocationsforHunt(Hunt hunt,
			FindCallback<Location> callback) {
		if (callback == null)
			return;
		ParseQuery<Location> query = ParseQuery.getQuery(Location.class);
		query.whereEqualTo(Location.LOCATION_PARENTHUNT_TAG, hunt);
		query.findInBackground(callback);
	}  

	public static void getLocationByHuntAndIndex(Hunt hunt, int index,
			FindCallback<Location> callback) {
		if (callback == null)
			return;
		ParseQuery<Location> query = ParseQuery.getQuery(Location.class);
		query.whereEqualTo(Location.LOCATION_PARENTHUNT_TAG, hunt);
		query.whereEqualTo(Location.LOCATION_INDEX_TAG, index);
		query.findInBackground(callback);
	}

	// information create/update
	public static void saveUser(ParseUser user, SaveCallback callback) {
		if (callback == null) {
			user.saveInBackground();
		} else {
			user.saveInBackground(callback);
		}
	}

	public static void rateHunt(Hunt hunt, int rating, SaveCallback callback) {
		double avgRating = Math.abs(hunt.getAvgRating());
		int numRatings = Math.abs(hunt.getNumRatings());

		double totalRating = avgRating * numRatings;
		++numRatings;
		avgRating = totalRating / numRatings;
		hunt.setAvgRating(avgRating);
		hunt.setNumRatings(numRatings);

		if (callback == null) {
			hunt.saveInBackground();
		} else {
			hunt.saveInBackground(callback);
		}
	}

	public static void deleteHunt(final Hunt hunt, final DeleteCallback callback) {
		getLocationsforHunt(hunt, new FindCallback<Location>() {
			@Override
			public void done(List<Location> huntPoints, ParseException e) {
				if (e == null) {
					// delete all hunt locations
					for (Location location : huntPoints) {
						location.deleteInBackground();
					}
					// delete hunt
					if (callback == null) {
						hunt.deleteInBackground();
					} else {
						hunt.deleteInBackground(callback);
					}

				} else {
					e.printStackTrace();
				}
			}

		});
	}
}
