package com.fpt.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.webkit.WebView;
import android.widget.Toast;

import com.fpt.model.JavascriptCallback;
import com.fpt.util.HQTUtils;
import com.fpt.view.R;
import com.fpt.view.SharedActivity;

public class WebviewFragment extends Fragment  {

    /** parent activity */
    SharedActivity activity;

    /** using Handler for manipulated UI Thread */
    final Handler myHandler = new Handler();

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

        View rootView = inflater.inflate(R.layout.fragment_webview, container, false);

        WebView webView = (WebView) rootView.findViewById(R.id.webview);
        /** enable javascript for callback */
        webView.getSettings().setJavaScriptEnabled(true);
        /** register a javascript interface */
        webView.addJavascriptInterface(new JavascriptCallback(getActivity().getApplicationContext()), "AndroidCallback");

        webView.loadDataWithBaseURL("", HQTUtils.generateHTML(), "text/html", "UTF-8", "");

        return rootView;
    }



}
