package com.chaseit.util;

import android.os.Bundle;

import com.chaseit.fragments.HuntDetailsFragment;

public class FragmentFactory {
	public static HuntDetailsFragment getHuntDetailsFragment(String huntId) {
		HuntDetailsFragment huntDetailsFragment = new HuntDetailsFragment();

		Bundle args = new Bundle();
		args.putString(Constants.HUNT_ID, huntId);
		huntDetailsFragment.setArguments(args);
		return huntDetailsFragment;
	}
}
