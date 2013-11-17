package com.fpt.helper.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.fpt.model.Word;
import com.fpt.view.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Dell on 11/17/13.
 */
public class SelectMultiWordAdapter extends BaseAdapter {
    Context mContext;

    public List<String> words;

    public boolean[] checked;

    public SelectMultiWordAdapter(Context context, List<String> words) {
        this.mContext = context;
        this.words = words;
        checked = new boolean[words.size()];
        Arrays.fill(checked, true);
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
            row = inflater.inflate(R.layout.list_item_select_multi_word, null);
            holder = new ViewHolder();
            holder.wordTextView = (TextView) row.findViewById(R.id.wordTextView);
            holder.checkBox = (CheckBox) row.findViewById(R.id.checkbox);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        holder.wordTextView.setText(words.get(position));
        holder.checkBox.setChecked(checked[position]);

        // add listener for checked box
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checked[position] = b;
            }
        });

        return row;
    }

    public static class ViewHolder {
        public TextView wordTextView;
        public CheckBox checkBox;
    }

}
