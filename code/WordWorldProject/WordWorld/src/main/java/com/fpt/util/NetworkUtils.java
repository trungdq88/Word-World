package com.fpt.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Thanh Hai on 11/16/13.
 */
public class NetworkUtils {

    public static HttpURLConnection getConnection(String myUrl) throws IOException {
        URL url = new URL(myUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        return conn;
    }


}
