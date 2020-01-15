package com.deyanm.shopy.util;

import android.app.ProgressDialog;
import android.content.Context;

public class ActivityUtils {

    public static ProgressDialog displayProgressDialog(Context context, String message) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(true);
        progressDialog.setMessage(message);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        return progressDialog;
    }
}
