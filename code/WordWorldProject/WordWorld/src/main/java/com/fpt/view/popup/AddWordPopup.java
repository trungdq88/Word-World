package com.fpt.view.popup;

import android.app.Activity;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fpt.view.R;

public class AddWordPopup extends Activity {

    /**
     * Add Word Button
     */
    Button addBtn;

    /**
     * Cancel Button
     */
    Button cancelBtn;

    /**
     * Name TextView
     */
    EditText mNameTextView;

    /**
     * Description TextView
     */
    EditText mDescriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_add_word);

        // inflate widget
        addBtn = (Button) findViewById(R.id.btnSave);
        cancelBtn = (Button) findViewById(R.id.btnCancel);
        mNameTextView = (EditText) findViewById(R.id.txtWord);
        mDescriptionTextView = (EditText) findViewById(R.id.txtDescription);

    }
}
