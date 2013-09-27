package com.chaseit.fragments.interfaces;

import java.util.ArrayList;

import com.chaseit.models.wrappers.HuntWrapper;
import com.chaseit.models.wrappers.LocationWrapper;
import com.chaseit.models.wrappers.UserHuntWrapper;

public interface HuntStartInterface {
	public void startHunt(UserHuntWrapper uHuntWrapper, HuntWrapper wHunt, ArrayList<LocationWrapper> wLocations );
}