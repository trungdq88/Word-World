package com.fpt.model;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.fpt.view.R;
import com.fpt.view.fragment.WebviewFragment;
import com.fpt.view.popup.AddWordPopup;

public class JavascriptCallback {
    Context mContext;
    WebviewFragment fragment;


    public JavascriptCallback(WebviewFragment fragment, Context context) {
        this.mContext = context;
        this.fragment = fragment;
    }

    public void showToast(String webMessage){
        // processing here
        Toast.makeText(mContext, webMessage, Toast.LENGTH_LONG).show();
    }

    /**
     * unique_seq: a unique sequence number to identify which word was touched...
     * @param word
     * @param unique_seq
     */
    public void addWord(String word, String unique_seq) {
        fragment.openAddWordPopup(word, unique_seq);
    }

    /**
     * calling remove word popup
     */
    public void removeWord(String word, String unique_seq) {
        fragment.openRemoveWordPopup(word, unique_seq);
    }

}
