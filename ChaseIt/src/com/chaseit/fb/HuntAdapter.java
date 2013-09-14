package com.chaseit.fb;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaseit.models.Hunt;

public class HuntAdapter extends ArrayAdapter<Hunt> {

	public HuntAdapter(Context context, List<Hunt> objects) {
		super(context, R.layout.item_hangout, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		Hunt hunt = getItem(position);
		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			view = inflater.inflate(R.layout.item_hangout, null);
		}

		ImageView imHuntImage = (ImageView) view.findViewById(R.id.ivHuntImage);
		TextView tvHuntName = (TextView) view.findViewById(R.id.tvHuntName);
		TextView tvHuntDetails = (TextView) view
				.findViewById(R.id.tvHuntDetails);

		tvHuntName.setText(hunt.getName());
		tvHuntDetails.setText(hunt.getDesc());

		return view;
	}

}
