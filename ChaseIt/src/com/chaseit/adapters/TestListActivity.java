package com.chaseit.adapters;

import android.app.ListActivity;
import android.os.Bundle;

import com.chaseit.models.Hunt;
import com.parse.ParseQueryAdapter;

public class TestListActivity extends ListActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ParseQueryAdapter<Hunt> mainAdapter = new ParseQueryAdapter<Hunt>(this,
				Hunt.class);
		mainAdapter.setTextKey("name");

		// Default view is all meals
		setListAdapter(mainAdapter);
	}

}
