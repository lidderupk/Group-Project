package com.chaseit.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.chaseit.R;
import com.chaseit.models.Location;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;

public class LocationImageAdapter extends ArrayAdapter<Location>{
	
	public LocationImageAdapter(Context context, List<Location> objects) {
		super(context, R.layout.item_hunt_image, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		Location location = getItem(position);
		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			view = inflater.inflate(R.layout.item_hunt_image, null);
		}

		ParseImageView huntImageView = (ParseImageView) view.findViewById(R.id.hunt_image);
		ParseFile photoFile = location.getParseFile(Location.LOCATION_IMAGE_TAG);
		if (photoFile != null) {
			huntImageView.setParseFile(photoFile);
			huntImageView.loadInBackground(new GetDataCallback() {
				@Override
				public void done(byte[] data, ParseException e) {
					if(e != null){
						e.printStackTrace();
					}
				}
			});
		}

		
		return view;

	}

}
