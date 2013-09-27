package com.chaseit.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("HuntImage")
public class HuntImage extends ParseObject {

	public static final String HUNTIMAGE_HUNT_TAG = "hunt";
	public static final String HUNTIMAGE_IMAGE_TAG = "image";

	public HuntImage() {
		// empty constructor
	}

	public void setHunt(Hunt hunt) {
		put("hunt", hunt);
	}

	public Hunt getHunt(Hunt hunt) {
		return (Hunt) getParseObject("hunt");
	}

	public void setImage(ParseFile image) {
		put("image", image);
	}

	public ParseFile getImage() {
		return getParseFile("image");
	}
}
