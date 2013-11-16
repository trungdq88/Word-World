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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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
import com.fpt.model.dal.ArticleDAL;
import com.fpt.model.dal.WordDAL;
import com.fpt.util.ContentUtils;
import com.fpt.util.DisplayUtils;
import com.fpt.util.HQTUtils;
import com.fpt.util.StringBuilderHelper;
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

    // For timing test
    long begin2;

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


        // Load from "share to" option
        if (activity.linkWebPage != null && !activity.linkWebPage.isEmpty()) {
            // Step 1
            begin2 = (new Date()).getTime();
            (new ContentGetter(this)).execute(activity.linkWebPage);
        } else {
            // Load from other activities
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

    /**
     *  unique_seq: a unique sequence number to identify which word was touched...
     * @param word
     * @param unique_seq
     */
    public void openAddWordPopup(String word, final String unique_seq) {
        // Create custom dialog object
        final Dialog dialog = new Dialog(getActivity());

        // Include dialog.xml file
        dialog.setContentView(R.layout.popup_add_word);
        // Set dialog title
        dialog.setTitle(getString(R.string.title_activity_add_word_popup));

        Button saveBtn = (Button) dialog.findViewById(R.id.btnSave);
        Button cancelBtn = (Button) dialog.findViewById(R.id.btnCancel);
        final EditText txtWord = (EditText) dialog.findViewById(R.id.txtWord);
        final EditText txtDescription = (EditText) dialog.findViewById(R.id.txtAddDescription);

        // assign text to word TextView
        txtWord.setText(word);
        if (activity.linkWebPage != null) {
            txtWord.setText(word);
        }

        // add action code for button
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wordText = txtWord.getText().toString();
                String wordDescription = txtDescription.getText().toString();
                // Check if the word is exists
                Word checkWord = WordDAL.getWordByText(activity.getApplicationContext(), wordText);
                if (checkWord != null) {
                    // the word is exists
                    WordDAL.updateWordStatus(activity.getApplicationContext(), checkWord.id, 1);
                    WordDAL.updateWordDescription(activity.getApplicationContext(), checkWord.id, wordDescription);
                } else {
                    // the word is not exists (should not happen)
                    Word w = new Word(wordText, wordDescription, 1, 1, (new Date()).getTime());
                    WordDAL.insertWord(activity.getApplicationContext(), w);
                }

                // Highlight the word
                callJsUnwrap(unique_seq);

                // Close dialog
                dialog.dismiss();
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


    /**
     *  unique_seq: a unique sequence number to identify which word was touched...
     * @param word
     * @param unique_seq
     */
    public void openRemoveWordPopup(String word, final String unique_seq) {
        // Create custom dialog object
        final Dialog dialog = new Dialog(getActivity());

        // Include dialog.xml file
        dialog.setContentView(R.layout.popup_remove_word);
        // Set dialog title
        dialog.setTitle("Edit word");

        /** inflat widget here */
        final Button editBtn = (Button) dialog.findViewById(R.id.btnEdit);
        final Button cancelBtn = (Button) dialog.findViewById(R.id.btnCalcel);
        final EditText txtWord = (EditText) dialog.findViewById(R.id.txtWord);
        final EditText txtDescription = (EditText) dialog.findViewById(R.id.txtDescription);
        final CheckBox chkDelete = (CheckBox) dialog.findViewById(R.id.chkDelete);
        final LinearLayout wordLayout = (LinearLayout) dialog.findViewById(R.id.linear_layout_word);
        final LinearLayout descriptionLayout = (LinearLayout) dialog.findViewById(R.id.linear_layout_description);


        final Word w = WordDAL.getWordByText(activity.getApplicationContext(), word);

        // assign text into TextView
        txtWord.setText(w.the_word);
        txtDescription.setText(w.description);
        Log.i("RemoveWordDebug", w.toString());

        // Checkbox action
        chkDelete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.i("CheckboxDebug", String.valueOf(chkDelete.isChecked()));
                if (chkDelete.isChecked()) {
                    wordLayout.setVisibility(View.INVISIBLE);
                    descriptionLayout.setVisibility(View.INVISIBLE);
                } else {
                    wordLayout.setVisibility(View.VISIBLE);
                    descriptionLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        // save action
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkDelete.isChecked()) {
                    // Delete
                    WordDAL.deleteWordById(activity.getApplicationContext(), w.id);
                    callJsWrap(unique_seq);
                } else {
                    // Update the description
                    WordDAL.updateWordDescription(activity.getApplicationContext(), w.id, txtDescription.getText().toString());
                }
                dialog.dismiss();
            }
        });

        // cancel
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void process(Article article) {
        if (article == null) {
            refreshWebView("<h1>Could not connect to internet! Please check your connection and try again</h1>");
            return;
        }

        // Step 2:
        // Step 1 - Step 2: Internet
        long end2 = (new Date()).getTime();
        Log.i("TimingDebug", "Internet: " + (end2-begin2)+"");
        // Step 3

        // Add article to database
        ArticleDAL.insertArticle(activity.getApplicationContext(), article);


        // Get all word

        long begin3 = (new Date()).getTime();
        List<String> allWords = StringBuilderHelper.getListWord(article.content);
        long end3 = (new Date()).getTime();
        Log.i("TimingDebug", "Alorithm 1: " + (end3-begin3)+"");

        // Insert database

        long begin4 = (new Date()).getTime();
        for (String word : allWords) {
            Word w = WordDAL.getWordByText(activity.getApplicationContext(), word);
            // If the word is not exists in database, then insert it.
            if (w == null) {
                WordDAL.insertWord(activity.getApplicationContext(), new Word(word, "", 0, 1, (new Date()).getTime()));
            } else {
                // If the word is already in database, then increase the counter
                WordDAL.updateSeenCount(activity.getApplicationContext(), w.id);
            }
        }

        // Get all word in database with status 0 ("remembered")
        List<Word> rememberedWords = WordDAL.getAllWordsWithStatus(activity.getApplicationContext(), 0);

        // Step 4:
        // Step 3 - 4: Database

        long end4 = (new Date()).getTime();
        Log.i("TimingDebug", "Database: " + (end4-begin4)+"");

        // Step 5
        // Get the color - tagged string

        long begin5 = (new Date()).getTime();
        String html = StringBuilderHelper.colorAllWord(article.content,
                DisplayUtils.listWordToListString(rememberedWords), allWords,
                Config.OPEN_HIGHLIGHT_TAG, Config.CLOSE_HIGHLIGHT_TAG,
                Config.OPEN_NO_HIGHLIGHT_TAG, Config.CLOSE_NO_HIGHLIGHT_TAG);
        long end5 = (new Date()).getTime();
        // Step 6
        // Step 5 - 6: algorithm
        Log.i("TimingDebug", "Alorigthm 2: " + (end5-begin5)+"");
        // Add CSS and Javascript for call back activity
        html = ContentUtils.addCSSJS(html);

        // Display things
        //webView.loadDataWithBaseURL("", html, "text/html", "UTF-8", "");
        refreshWebView(html);
    }

    private void refreshWebView(String html) {
        webView.loadDataWithBaseURL("", html, "text/html", "UTF-8", "");
    }

    private void testCallJs() {
        Log.i("CallJSDebug", "called!");
        webView.loadUrl("javascript:document.write('asd')");
    }

    private void callJsUnwrap(String unique_seq) {
        webView.loadUrl("javascript:unwrap_word('"+unique_seq+"')");
    }

    private void callJsWrap(String unique_seq) {
        webView.loadUrl("javascript:wrap_word('"+unique_seq+"')");
    }
}
