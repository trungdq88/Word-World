package com.fpt.helper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;

import com.fpt.model.Article;

/**
 * Created by Quang Trung on 11/16/13.
 */
public abstract class NetworkBackground extends AsyncTask<String, Void, Article> {

    /** application context */
    public INetworkCallback callback;

    /** progress dialog to show user that the backup is processing. */
    public ProgressDialog dialog;

    /** status of excute */
    public boolean status = true;

    public NetworkBackground(INetworkCallback callback) {
        this.callback = callback;
//        if(INetworkCallback.class.isAssignableFrom(Fragment.class)) {
            Fragment f = (Fragment) callback;
            dialog = new ProgressDialog(f.getActivity());
//        } else {
//            dialog = new ProgressDialog((Activity)callback);
//        }
    }

    @Override
    protected void onPreExecute() {
        this.dialog.setMessage("Progress is loading");
        this.dialog.show();
    }

    @Override
    protected void onPostExecute(final Article article) {

        if (status == false) {
            dialog.setMessage("Error when retrieve data from server");
        }
        else if (dialog.isShowing()) {
            dialog.dismiss();
        }
        callback.process(article);
    }

    /** must implement this method */
    @Override
    protected abstract Article doInBackground(String... params);

    public static interface INetworkCallback {
         void process(Article article);
    }
}
