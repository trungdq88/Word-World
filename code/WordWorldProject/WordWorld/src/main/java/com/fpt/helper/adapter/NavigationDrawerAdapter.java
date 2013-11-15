package com.fpt.helper.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import static com.fpt.util.LogUtils.makeLogTag;

/**
 * Created by Huynh Quang Thao on 11/15/13.
 */
public class NavigationDrawerAdapter {

    private static String TAG = makeLogTag(NavigationDrawerAdapter.class);

    public static class HeaderAdapter extends BaseAdapter {

        private static String TAG = makeLogTag(HeaderAdapter.class);

        Context mContext;

        public HeaderAdapter(Context context) {
            this.mContext = context;
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }

    public static class ItemAdapter extends BaseAdapter {

        private static String TAG = makeLogTag(ItemAdapter.class);

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }

}
