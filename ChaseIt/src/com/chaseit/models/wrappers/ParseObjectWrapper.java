package com.chaseit.models.wrappers;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;

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
		for(String key : object.keySet()) {
			@SuppressWarnings("rawtypes")
			Class classType = object.get(key).getClass();
			if(classType == byte[].class || classType == String.class || 
					classType == Integer.class || classType == int.class || classType == Boolean.class || classType == double.class) {
				values.put(key, object.get(key));
			} else if(classType == ParseUser.class) {
				ParseObjectWrapper parseUserObject = new ParseObjectWrapper((ParseObject)object.get(key));
				values.put(key, parseUserObject);
			} else if(classType == ParseFile.class) {
				ParseFile pf = (ParseFile)object.get(key);
				File f = null;
				if(pf.isDataAvailable()){
					String fileName = pf.getName();
					f = new File(fileName);
					try {
						FileUtils.writeByteArrayToFile(f, pf.getData());
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				values.put(key, f);
			} else if(classType == ParseGeoPoint.class) {
				ParseGeoPoint point = (ParseGeoPoint)object.get(key);
				LatLng l = new LatLng(point.getLatitude(), point.getLongitude()); 
				values.put(key, l);
			}
		}
	}

	public String getString(String key) {
		if(has(key)) {
			return (String) values.get(key);
		} else {
			return "";
		}
	}

	public int getInt(String key) {
		if(has(key)) {
			return (Integer)values.get(key);
		} else {
			return 0;
		}
	}

	public double getDouble(String key) {
		if(has(key)) {
			return (Double)values.get(key);
		} else {
			return 0;
		}
	}

	public Boolean getBoolean(String key) {
		if(has(key)) {
			return (Boolean)values.get(key);
		} else {
			return false;
		}
	}

	public byte[] getBytes(String key) {
		if(has(key)) {
			return (byte[])values.get(key);
		} else {
			return new byte[0];
		}
	}	

	public File getFile(String key) {
		if(has(key)) {
			return (File)values.get(key);
		} else {
			return null;
		}
	}

	public LatLng getLocation(String key) {
		if(has(key)) {
			return (LatLng)values.get(key);
		} else {
			return null;
		}
	}

	public ParseObjectWrapper getParseUser(String key) {
		if(has(key)) {
			return (ParseObjectWrapper) values.get(key);
		} else {
			return null;
		}
	}

	public Boolean has(String key) {
		return values.containsKey(key);
	}
}
