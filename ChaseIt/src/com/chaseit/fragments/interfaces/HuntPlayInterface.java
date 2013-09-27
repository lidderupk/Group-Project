package com.chaseit.fragments.interfaces;

import java.util.List;

import android.location.Location;

public interface HuntPlayInterface {
	public void redrawMapWithMarkers(List<Location> locations);
}