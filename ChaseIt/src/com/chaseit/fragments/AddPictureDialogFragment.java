package com.chaseit.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaseit.R;

public class AddPictureDialogFragment extends DialogFragment {
	public interface AddPictureDialogListener {
		public void onCameraClick(DialogFragment dialog);

		public void onGalleryClick(DialogFragment dialog);
	}

	protected AddPictureDialogListener mListener;

	public AddPictureDialogFragment() {
		super();
	}

	public static AddPictureDialogFragment newInstance(int title) {
		AddPictureDialogFragment frag = new AddPictureDialogFragment();
		Bundle args = new Bundle();
		args.putInt("title", title);
		frag.setArguments(args);
		return frag;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.dialog_image_capture,
				container, false);
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

		int title = getArguments().getInt("title");
		((TextView) view.findViewById(R.id.tvTitle)).setText(title);
		((ImageView) view.findViewById(R.id.ivTakePicture))
				.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						// When button is clicked, call up to owning activity.
						mListener.onCameraClick(AddPictureDialogFragment.this);
					}
				});

		((ImageView) view.findViewById(R.id.ivSelectPicture))
				.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						// When button is clicked, call up to owning activity.
						mListener.onGalleryClick(AddPictureDialogFragment.this);
					}
				});

		return view;
	}

	// Override the Fragment.onAttach() method to instantiate the
	// AddPictureDialogListener
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// Verify that the host activity implements the callback interface
		try {
			// Instantiate the AddPictureDialogListener so we can send events to
			// the host
			// mListener = (AddPictureDialogListener) activity;
			FragmentManager manager = ((FragmentActivity) activity)
					.getSupportFragmentManager();
			// mListener = (AddPictureDialogListener)
			// manager.findFragmentById(R.id.fAddPicture);
			mListener = (AddPictureDialogListener) manager
					.findFragmentByTag("PictureFragment");

		} catch (ClassCastException e) {
			// The activity doesn't implement the interface, throw exception
			throw new ClassCastException(activity.toString()
					+ " must implement AddPictureDialogListener");
		}
	}

}
