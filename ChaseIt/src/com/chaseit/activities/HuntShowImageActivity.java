package com.chaseit.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;

import com.chaseit.R;

public class HuntShowImageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hunt_show_image);
		setupViews();
	}

	private void setupViews() {
		ImageView ivImage = (ImageView) findViewById(R.id.ivImage);
		Bitmap mBitmap = (Bitmap) getIntent().getParcelableExtra("bmp_img");
		ivImage.setImageBitmap(mBitmap);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.empty, menu);
		return true;
	}

}
