package com.chaseit.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.chaseit.R;
import com.chaseit.models.Hunt;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;

public class HuntAdapter extends ArrayAdapter<Hunt> {

	public HuntAdapter(Context context, List<Hunt> objects) {
		super(context, R.layout.hunt_newsfeed_item, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		Hunt hunt = getItem(position);
		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			view = inflater.inflate(R.layout.hunt_newsfeed_item, null);
		}

		ParseImageView imHuntImage = (ParseImageView) view.findViewById(R.id.ivHuntImage);
		TextView tvHuntName = (TextView) view.findViewById(R.id.tvHuntName);
		TextView tvHuntDetails = (TextView) view
				.findViewById(R.id.tvHuntDetails);
		TextView tvHuntRating = (TextView) view.findViewById(R.id.tvHuntRating);
		TextView tvHuntCreator = (TextView) view.findViewById(R.id.tvHuntCreator);

		tvHuntName.setText(hunt.getName());
		tvHuntDetails.setText(hunt.getDetails());
		ParseFile huntPic = hunt.getHuntPicture();
		tvHuntRating.setText(Double.toString(hunt.getAvgRating()));
		tvHuntCreator.setText(hunt.getCreator().toString());
		
		if (huntPic != null) {
			imHuntImage.setParseFile(huntPic);
			imHuntImage.loadInBackground(new GetDataCallback() {
				@Override
				public void done(byte[] data, ParseException e) {
					// nothing to do
				}
			});
		}

		return view;
	}

}
