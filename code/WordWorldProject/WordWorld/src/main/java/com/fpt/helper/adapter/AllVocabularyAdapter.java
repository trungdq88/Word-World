package com.fpt.helper.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fpt.model.Word;
import com.fpt.view.R;

import java.util.List;

/**
 * Created by Dell on 11/16/13.
 */

public class AllVocabularyAdapter extends BaseAdapter {

    Context mContext;

    public List<Word> words;

    public AllVocabularyAdapter(Context context, List<Word> words) {
        this.mContext = context;
        this.words = words;
    }

    @Override
    public int getCount() {
        return words.size();
    }

    @Override
    public Object getItem(int position) {
        return words.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        View row = convertView;

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (row == null) {
            row = inflater.inflate(R.layout.list_item_allvocabulary, null);
            holder = new ViewHolder();
            holder.wordTextView = (TextView) row.findViewById(R.id.txtWord);
            holder.descriptionTextView = (TextView) row.findViewById(R.id.txtDescription);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        holder.wordTextView.setText(words.get(position).the_word);
        holder.descriptionTextView.setText(words.get(position).description);

        return row;
    }

    public static class ViewHolder {
        public TextView wordTextView;
        public TextView descriptionTextView;
    }

}
