package com.fpt.helper.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fpt.model.Article;
import com.fpt.view.R;

import java.util.Date;
import java.util.List;

import static com.fpt.util.LogUtils.makeLogTag;

/**
 * Created by Dell on 11/16/13.
 */
public class AllArticleAdapter extends BaseAdapter{

    public static String TAG = makeLogTag(AllVocabularyAdapter.class);


    Context mContext;

    public List<Article> articles;

    public AllArticleAdapter(Context context, List<Article> articles) {
        this.mContext = context;
        this.articles = articles;
    }

    @Override
    public int getCount() {
        return articles.size();
    }

    @Override
    public Object getItem(int position) {
        return articles.get(position);
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
            holder.articleTextView = (TextView) row.findViewById(R.id.articleTxt);
            holder.urlTextView = (TextView) row.findViewById(R.id.urlTxt);
            holder.dateTextView = (TextView) row.findViewById(R.id.dateTxt);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        Article article = articles.get(position);
        holder.articleTextView.setText(article.title);
        holder.urlTextView.setText(article.url);
        Date date = new Date(article.created * 1000);
        holder.dateTextView.setText(date.toString());

        return row;
    }

    public static class ViewHolder {
       public TextView articleTextView;
       public TextView urlTextView;
       public TextView dateTextView;

    }

}
