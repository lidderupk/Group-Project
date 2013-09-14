package com.chaseit.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.chaseit.fb.R;

public class Hunt implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7325191126592989417L;

	private Long uid;
	private String name;
	private String desc;
	private String details;
	private int score;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static List<Hunt> createSampleHunts(Context c) {
		List<Hunt> huntList = new ArrayList<Hunt>();
		Hunt hunt = new Hunt();
		hunt.setName(c.getResources().getString(R.string.hunt_name_example));
		hunt.setDesc(c.getResources().getString(R.string.hunt_details_example));
		huntList.add(hunt);

		hunt = new Hunt();
		hunt.setName(c.getResources().getString(R.string.hunt_name_example));
		hunt.setDesc(c.getResources().getString(R.string.hunt_details_example));
		huntList.add(hunt);

		hunt = new Hunt();
		hunt.setName(c.getResources().getString(R.string.hunt_name_example));
		hunt.setDesc(c.getResources().getString(R.string.hunt_details_example));
		huntList.add(hunt);

		hunt = new Hunt();
		hunt.setName(c.getResources().getString(R.string.hunt_name_example));
		hunt.setDesc(c.getResources().getString(R.string.hunt_details_example));
		huntList.add(hunt);

		hunt = new Hunt();
		hunt.setName(c.getResources().getString(R.string.hunt_name_example));
		hunt.setDesc(c.getResources().getString(R.string.hunt_details_example));
		huntList.add(hunt);

		return huntList;
	}
}
