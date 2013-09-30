package com.chaseit.models.wrappers;

import java.io.Serializable;
import java.util.HashMap;

import com.chaseit.ParseHelper;
import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class ParseObjectWrapper implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5977604336086318801L;
	private HashMap<String, Object> values = new HashMap<String, Object>();

	public HashMap<String, Object> getValues() {
		return values;
	}

	public void setValues(HashMap<String, Object> values) {
		this.values = values;
	}

	public ParseObjectWrapper(ParseObject object) {
		// insert the objectId since this is not part of the key set
		values.put(ParseHelper.OBJECTID_TAG, object.getObjectId());

		for (String key : object.keySet()) {
			@SuppressWarnings("rawtypes")
			Class classType = object.get(key).getClass();
			if (classType == byte[].class || classType == String.class
					|| classType == Integer.class || classType == int.class || classType == Double.class
					|| classType == Boolean.class || classType == double.class) {
				values.put(key, object.get(key));
			} else if (classType == ParseUser.class) {
				ParseObjectWrapper parseUserObject = new ParseObjectWrapper(
						(ParseObject) object.get(key));
				values.put(key, parseUserObject);
			} else if (classType == ParseObject.class) {
				ParseObjectWrapper parseUserObject = new ParseObjectWrapper(
						(ParseObject) object.get(key));
				values.put(key, parseUserObject);
			} else if (classType == ParseFile.class) {
				ParseFile pf = (ParseFile) object.get(key);
				if (pf.isDataAvailable()) {
					String fileName = pf.getName();
					try {
						values.put(key, pf.getData());
						values.put(key + ParseHelper.FILENAME_TAG, fileName);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			} else if (classType == ParseGeoPoint.class) {
				ParseGeoPoint point = (ParseGeoPoint) object.get(key);
				// LatLng l = new LatLng(point.getLatitude(),
				// point.getLongitude());
				values.put(key + "." + ParseHelper.LATITUDE_TAG,
						point.getLatitude());
				values.put(key + "." + ParseHelper.LONGITUDE_TAG,
						point.getLongitude());
			}
		}
	}

	public String getString(String key) {
		if (has(key)) {
			return (String) values.get(key);
		} else {
			return "";
		}
	}

	public int getInt(String key) {
		if (has(key)) {
			return (Integer) values.get(key);
		} else {
			return 0;
		}
	}

	public double getDouble(String key) {
		if (has(key)) {
			return (Double) values.get(key);
		} else {
			return 0;
		}
	}

	public Boolean getBoolean(String key) {
		if (has(key)) {
			return (Boolean) values.get(key);
		} else {
			return false;
		}
	}

	public byte[] getBytes(String key) {
		if (has(key)) {
			return (byte[]) values.get(key);
		} else {
			return new byte[0];
		}
	}

	public ParseFile getParseFile(String key) {
		if (has(key)) {
			byte[] data = (byte[]) values.get(key);
			String fileName = (String) values.get(key
					+ ParseHelper.FILENAME_TAG);
			ParseFile pF = new ParseFile(fileName, data);
			return pF;

		} else {
			return null;
		}
	}

	public LatLng getLocation(String key) {
		if (has(key + "." + ParseHelper.LATITUDE_TAG)) {
			double lat = (Double) values.get(key + "."
					+ ParseHelper.LATITUDE_TAG);
			double lon = (Double) values.get(key + "."
					+ ParseHelper.LONGITUDE_TAG);
			return new LatLng(lat, lon);
		} else {
			return null;
		}
	}

	public ParseObjectWrapper getParseUser(String key) {
		if (has(key)) {
			return (ParseObjectWrapper) values.get(key);
		} else {
			return null;
		}
	}

	public ParseObjectWrapper getParseObject(String key) {
		if (has(key)) {
			return (ParseObjectWrapper) values.get(key);
		} else {
			return null;
		}
	}

	public Boolean has(String key) {
		return values.containsKey(key);
	}
}
