package com.fpt.model;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Huynh Quang Thao on 11/16/13.
 */
public class JavascriptCallback {
    Context mContext;

    public JavascriptCallback(Context context) {
        this.mContext = context;
    }

    public void showToast(String webMessage){
        // processing here
        Toast.makeText(mContext, webMessage, Toast.LENGTH_LONG).show();
    }

    public void addWord(String word) {

    }

}
