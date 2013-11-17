package com.fpt.util;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.fpt.model.Word;
import com.fpt.model.dal.WordDAL;
import com.fpt.view.R;

import java.util.Date;

/**
 * Created by Quang Trung on 11/17/13.
 */
public class PopupUtils {

    public static void openAddWordPopup(final Activity activity, String word) {
        // Create custom dialog object
        final Dialog dialog = new Dialog(activity);

        // Include dialog.xml file
        dialog.setContentView(R.layout.popup_add_word);
        // Set dialog title
        dialog.setTitle(activity.getString(R.string.title_activity_add_word_popup));

        Button saveBtn = (Button) dialog.findViewById(R.id.btnSave);
        Button cancelBtn = (Button) dialog.findViewById(R.id.btnCancel);
        final EditText txtWord = (EditText) dialog.findViewById(R.id.txtWord);
        final EditText txtDescription = (EditText) dialog.findViewById(R.id.txtAddDescription);

        // assign text to word TextView
        txtWord.setText(word);


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

    public static void openRemoveWordPopup(final Activity activity, String word) {
        // Create custom dialog object
        final Dialog dialog = new Dialog(activity);

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
}
