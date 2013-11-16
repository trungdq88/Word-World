package com.fpt.model;

import com.fpt.helper.NetworkBackground;
import com.fpt.util.ParserUtils;

/**
 * Created by Quang Trung on 11/16/13.
 */
public class ContentGetter extends NetworkBackground {

    public ContentGetter(INetworkCallback activity) {
        super(activity);
    }

    @Override
    protected void onPostExecute(Article success) {
        super.onPostExecute(success);
    }

    @Override
    protected Article doInBackground(String... urls) {
        Article article = ParserUtils.getAricle(urls[0]);

        return article;
    }

}
