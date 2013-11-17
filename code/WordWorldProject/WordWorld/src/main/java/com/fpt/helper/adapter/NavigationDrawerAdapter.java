package com.fpt.helper.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fpt.view.R;

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
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolderHeader holder = null;
            View row = convertView;

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (row == null) {
                row = inflater.inflate(R.layout.list_item_header, null);
                holder = new ViewHolderHeader();
                holder.txtName = (TextView) row.findViewById(R.id.name);
                holder.txtMail = (TextView) row.findViewById(R.id.mail);
                holder.imgAvatar = (ImageView) row.findViewById(R.id.imageViewInfo);
                row.setTag(holder);
            }
            else {
                holder = (ViewHolderHeader) row.getTag();
            }

            // assign value to view
            holder.txtName.setText("Your vocabs");
            holder.txtMail.setText("HACK@THON 2013");
            holder.imgAvatar.setImageResource(R.drawable.abc_ic_search);
            return row;
        }
    }

    public static class ItemAdapter extends BaseAdapter {

        private static String TAG = makeLogTag(ItemAdapter.class);

        public enum TYPE {
            WELCOME,
            HEAD_VOCABULARY,
            ALL_VOCABULARY,
            SAVE_ARTICLE,
        }

        Context mContext;
        String[] categories;
        IItemDelegate delegate;

        public ItemAdapter(Context context) {
            this.mContext = context;
            categories = context.getResources().getStringArray(R.array.navigation_drawer_item);
        }

        @Override
        public int getCount() {
            return categories.length;
        }

        @Override
        public Object getItem(int position) {
            return categories[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolderItem holder = null;
            View row = convertView;

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (row == null) {
                row = inflater.inflate(R.layout.list_item_navigation_drawer, null);
                holder = new ViewHolderItem();
                holder.txtView = (TextView) row.findViewById(R.id.text);
                holder.imageView = (ImageView) row.findViewById(R.id.icon);

                row.setTag(holder);
            }
            else {
                holder = (ViewHolderItem) row.getTag();
            }

            // assign value to holder
            TYPE type = null;
            try {
                holder.txtView.setText(categories[position]);
            }
            catch (Exception e) {
                if (holder == null) Log.e("Huynh Quang Thao", "Silly Error");
            }

            switch(position) {
                case 0:
                    holder.imageView.setImageResource(R.drawable.wwicon_welcome);
                    type = TYPE.WELCOME;
                    break;
                case 1:
                    holder.imageView.setImageResource(R.drawable.wwicon_yourwords);
                    type = TYPE.HEAD_VOCABULARY;
                    break;
                case 2:
                    holder.imageView.setImageResource(R.drawable.wwicon_allwords);
                    type = TYPE.ALL_VOCABULARY;
                    break;
                case 3:
                    holder.imageView.setImageResource(R.drawable.wwicon_savedarticles);
                    type = TYPE.SAVE_ARTICLE;
                    break;
            }

            final TYPE finalType = type;
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delegate.gotoCategoryPage(finalType);
                }
            });
            return row;
        }

        public void setDelegate(IItemDelegate delegate) {
            this.delegate = delegate;
        }


    }


    private static class ViewHolderHeader {
        TextView txtName;
        TextView txtMail;
        ImageView imgAvatar;
    }

    private static class ViewHolderItem {
        ImageView imageView;
        TextView txtView;
    }


    public static interface IItemDelegate {
        void gotoCategoryPage(ItemAdapter.TYPE type);
    }

}
