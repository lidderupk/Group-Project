package com.chaseit.fragments;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.io.FilenameUtils;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chaseit.R;
import com.chaseit.fragments.AddPictureDialogFragment.AddPictureDialogListener;

public class AddPictureFragment extends Fragment implements AddPictureDialogListener{
	
	public interface AddPictureListener {
        public void onPictureAdded(Fragment fragment);
    }
	
	private static final int TAKE_PHOTO_CODE = 1;
	private static final int PICK_PHOTO_CODE = 2;
	private static final int CROP_PHOTO_CODE = 3;

	private String photoUri;
	private Bitmap photoBitmap;
	private ImageView ivAddPicture;
	private AddPictureDialogFragment dialog;
	private AddPictureListener addPicListener;
	

	/**
	 * @return the photoUri
	 */
	public String getPhotoUri() {
		return photoUri;
	}

	/**
	 * @return the photoBitmap
	 */
	public Bitmap getPhotoBitmap() {
		return photoBitmap;
	}

	/**
	 * @return the photoName
	 */
	public String getPhotoName() {
		return FilenameUtils.getName(photoUri);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_add_picture, container, false);
		ivAddPicture = (ImageView)v.findViewById(R.id.ivAddPhoto);
		ivAddPicture.setClickable(true);
		ivAddPicture.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog = AddPictureDialogFragment.newInstance(R.string.dialog_title_text);
				dialog.show(getActivity().getSupportFragmentManager(), "AddPictureDialog");
			}
		});

		return v;
	}
	
    // Override the Fragment.onAttach() method to instantiate the AddPictureListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the AddPictureListener so we can send events to the host
            addPicListener = (AddPictureListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement AddPictureListener");
        }
    }

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == TAKE_PHOTO_CODE) {
				cropPhoto();
			} else if (requestCode == PICK_PHOTO_CODE) {
	            photoUri = getFileUri(data.getData());
	            cropPhoto();
			} else if (requestCode == CROP_PHOTO_CODE) {
				photoBitmap = data.getParcelableExtra("data");
				ivAddPicture.setImageBitmap(photoBitmap);
				addPicListener.onPictureAdded(AddPictureFragment.this);
			} 
		}
	}
	
	@Override
	public void onCameraClick(DialogFragment dialog) {
		dialog.dismiss();
		Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File photoFile = getOutputMediaFile(); // create a file to save the image
	    i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile)); // set the image file name
	    photoUri = photoFile.getAbsolutePath();
	    startActivityForResult(i, TAKE_PHOTO_CODE);
		
	}

	@Override
	public void onGalleryClick(DialogFragment dialog) {
		dialog.dismiss();
		Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(i, PICK_PHOTO_CODE);
	}

	
	private static File getOutputMediaFile() {
	    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
	              Environment.DIRECTORY_PICTURES), "chaseit");
	    if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
	        return null;
	    }

	    // Create a media file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
	    File mediaFile = new File(mediaStorageDir.getPath() + File.separator +
		        "IMG_"+ timeStamp + ".jpg");

	    return mediaFile;
	}
	
	private void cropPhoto() {
		//call the standard crop action intent (the user device may not support it)
		Intent cropIntent = new Intent("com.android.camera.action.CROP");
		//indicate image type and Uri
		cropIntent.setDataAndType(Uri.fromFile(new File(photoUri)), "image/*");
		//set crop properties
		cropIntent.putExtra("crop", "true");
		//indicate aspect of desired crop
		cropIntent.putExtra("aspectX", 1);
		cropIntent.putExtra("aspectY", 1);
		//indicate output X and Y
		cropIntent.putExtra("outputX", 300);
		cropIntent.putExtra("outputY", 300);
		//retrieve data on return
		cropIntent.putExtra("return-data", true);
		//start the activity - we handle returning in onActivityResult
		startActivityForResult(cropIntent, CROP_PHOTO_CODE);
	}

	private String getFileUri(Uri mediaStoreUri) {
		String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().getContentResolver().query(mediaStoreUri,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String fileUri = cursor.getString(columnIndex);
        cursor.close();
        
        return fileUri;
	}


}
