package com.phunware.phunwaredemo.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.TextView;

import com.phunware.phunwaredemo.R;

public class Utils {

	 private static final String TAG = "Utils";
	
	 public static class ViewHolderItem {

			public TextView txtVenueTitle;
			public TextView txtVenueAddress;
	 }
	 
	public static ProgressDialog getProgressDialog(Context context, String title, String msj){
		
    	ProgressDialog dialog = new ProgressDialog(context);

    	dialog.setIndeterminate(true);
    	dialog.setCancelable(true);
    	dialog.setInverseBackgroundForced(false);
    	dialog.setCanceledOnTouchOutside(true);
    	dialog.setTitle(title);
    	dialog.setMessage(msj);

    	return dialog;
	}
	
	 /*
	  * Display alert dialog asking confirmation to close the application
	  */
	 public static void closeApplicationMessage(final android.app.Activity activity){
			new AlertDialog.Builder(activity)
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setTitle(R.string.exit_title)
			.setMessage(R.string.exit_msj)
			.setPositiveButton(R.string.exit_ok, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					activity.finish(); 
				}

			})
			.setNegativeButton(R.string.exit_cancel, null)
			.show();
	}
}
