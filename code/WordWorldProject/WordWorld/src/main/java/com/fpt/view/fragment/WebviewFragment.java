package com.fpt.view.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fpt.config.Config;
import com.fpt.helper.NetworkBackground;
import com.fpt.model.Article;
import com.fpt.model.ContentGetter;
import com.fpt.model.JavascriptCallback;
import com.fpt.model.Word;
import com.fpt.model.dal.WordDAL;
import com.fpt.util.ContentUtils;
import com.fpt.util.DisplayUtils;
import com.fpt.util.HQTUtils;
import com.fpt.util.StringHelper;
import com.fpt.view.R;
import com.fpt.view.SharedActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WebviewFragment extends Fragment implements NetworkBackground.INetworkCallback {

    /**
     * parent activity
     */
    SharedActivity activity;

    /**
     * using Handler for manipulated UI Thread
     */
    final Handler myHandler = new Handler();

    WebView webView;

    PopupWindow pw;

    View rootView;

    public WebviewFragment() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (SharedActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_webview, container, false);

        webView = (WebView) rootView.findViewById(R.id.webview);

        webView.getSettings().setBuiltInZoomControls(true);

        /** enable javascript for callback */
        webView.getSettings().setJavaScriptEnabled(true);
        /** register a javascript interface */
        JavascriptCallback callback = new JavascriptCallback(this, this.getActivity().getApplicationContext());
        webView.addJavascriptInterface(callback, "AndroidCallback");


        /** Load HTML here */
        if (activity.linkWebPage != null && !activity.linkWebPage.isEmpty()) {
            (new ContentGetter(this)).execute(activity.linkWebPage);
        } else {
            webView.loadDataWithBaseURL("", HQTUtils.generateHTML(), "text/html", "UTF-8", "");
        }
        return rootView;
    }


    private PopupWindow dimBackground() {

        LayoutInflater inflater = (LayoutInflater) getActivity().getBaseContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.fadepopup, null, false);
        PopupWindow fadePopup = new PopupWindow(layout, rootView.getWidth(), rootView.getHeight(), false);
        fadePopup.showAtLocation(layout, Gravity.NO_GRAVITY, 0, 0);
        return fadePopup;
    }


    public void openAddWordPopup(String word) {
        // Create custom dialog object
        final Dialog dialog = new Dialog(getActivity());

        // Include dialog.xml file
        dialog.setContentView(R.layout.popup_add_word);
        // Set dialog title
        dialog.setTitle("Add Word Dialog");

        Button saveBtn = (Button) dialog.findViewById(R.id.btnSave);
        Button cancelBtn = (Button) dialog.findViewById(R.id.btnCancel);
        EditText txtWord = (EditText) dialog.findViewById(R.id.txtWord);
        EditText txtDescription = (EditText) dialog.findViewById(R.id.txtDescription);

        // assign text to word TextView
        txtWord.setText(word);
        if (activity.linkWebPage != null) {
            txtWord.setText(activity.linkWebPage);
        }

        // add action code for button
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void openRemoveWordPopup(String word) {
        // Create custom dialog object
        final Dialog dialog = new Dialog(getActivity());

        // Include dialog.xml file
        dialog.setContentView(R.layout.popup_remove_word);
        // Set dialog title
        dialog.setTitle("Remove Word Dialog");

        /** inflat widget here */
        Button editBtn = (Button) dialog.findViewById(R.id.btnEdit);
        Button removeBtn = (Button) dialog.findViewById(R.id.btnRemove);
        EditText txtWord = (EditText) dialog.findViewById(R.id.txtWord);

        // assign text into TextView
        txtWord.setText(word);

        // add action here
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    @Override
    public void process(Article article) {
        // Get all word and insert to database
        List<String> allWords = StringHelper.getListWord(article.content);

        for (String word : allWords) {
            Word w = WordDAL.getWordByText(activity.getApplicationContext(), word);
            // If the word is not exists in database, then insert it.
            if (w == null) {
                WordDAL.insertWord(activity.getApplicationContext(), new Word(word, "", 0, 0, (new Date()).getTime()));
            } else {
                // If the word is already in database, then increase the counter
                WordDAL.updateSeenCount(activity.getApplicationContext(), w.id);
            }
        }

        // TODO: insert article


        // Get all word in database with status 1 ("remembered")
        List<Word> rememberedWords = WordDAL.getAllWordsWithStatus(activity.getApplicationContext(), 1);


        // Get the color - tagged string
        String html = StringHelper.colorWord(article.content,
                DisplayUtils.listWordToListString(rememberedWords),
                Config.OPEN_HIGHLIGHT_TAG, Config.CLOSE_HIGHLIGHT_TAG);


        // Add CSS and Javascript for call back activity
        html = ContentUtils.addCSSJS(html);

        // Display things
        webView.loadDataWithBaseURL("", html, "text/html", "UTF-8", "");
    }
}
