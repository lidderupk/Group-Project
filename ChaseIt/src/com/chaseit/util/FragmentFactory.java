package com.chaseit.util;

import android.os.Bundle;

import com.chaseit.fragments.HuntDetailsFragment;
import com.chaseit.fragments.HuntPlayFragment;
import com.chaseit.fragments.MapWithConnectedMarkersFragment;
import com.chaseit.models.wrappers.HuntWrapper;
import com.chaseit.models.wrappers.UserHuntWrapper;

public class FragmentFactory {
	public static HuntDetailsFragment getHuntDetailsFragment(
			HuntWrapper huntWrapper) {
		HuntDetailsFragment huntDetailsFragment = new HuntDetailsFragment();

		Bundle args = new Bundle();
		args.putSerializable(Constants.HUNT_WRAPPER_DATA_NAME, huntWrapper);
		huntDetailsFragment.setArguments(args);
		return huntDetailsFragment;
	}

	public static HuntPlayFragment getHuntPlayFragment(
			UserHuntWrapper uHuntWrapper) {
		HuntPlayFragment huntPlayFragment = new HuntPlayFragment();

		Bundle args = new Bundle();
		args.putSerializable(Constants.USER_HUNT_WRAPPER_DATA_NAME,
				uHuntWrapper);
		huntPlayFragment.setArguments(args);
		return huntPlayFragment;
	}

	public static MapWithConnectedMarkersFragment getHuntMapWithMarkersFragment(
			UserHuntWrapper uhWrapper) {
		MapWithConnectedMarkersFragment huntMapWithMarkersFragment = new MapWithConnectedMarkersFragment();

		Bundle args = new Bundle();
		args.putSerializable(Constants.USER_HUNT_WRAPPER_DATA_NAME, uhWrapper);
		huntMapWithMarkersFragment.setArguments(args);
		return huntMapWithMarkersFragment;
	}
}
