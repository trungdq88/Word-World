package com.fpt.util;

import android.util.Log;

import com.fpt.config.Config;
import com.fpt.model.Article;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * Created by Thanh Hai on 11/15/13.
 */
public class ParserUtils {

    /**
     * Cast the input stream to String
     *
     * @param stream
     * @return String
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    public static String convertInputStreamToString(InputStream stream) {
//        Reader reader = null;
//        reader = new InputStreamReader(stream, "UTF-8");
//        char[] buffer = new char[len];
//        reader.read(buffer);
//        return new String(buffer);
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * make a request url from article url
     *
     * @param articleUrl
     * @return
     */
    private static String makeUrl(String articleUrl) {
        String url = "https://www.readability.com/api/content/v1/parser?url=" + articleUrl + "&token=" + Config.READABILITY_API_TOKEN;
        return url;
    }

    /**
     * get Json string by a url
     *
     * @param articleUrl
     * @return Json
     * @throws IOException
     */
    public static String getJsonString(String articleUrl) {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        String url = makeUrl(articleUrl);
        try {
            // Starts the query
            HttpURLConnection conn = NetworkUtils.getConnection(url);
            conn.connect();
            int response = conn.getResponseCode();
            //Log.d(DEBUG_TAG, "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = convertInputStreamToString(is);

            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Article getAricle(String articleUrl) {
        try {
            String jsonString = null;
            jsonString = getJsonString(articleUrl);
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
            String url = jsonObject.get("url").getAsString();
            String title = jsonObject.get("title").getAsString();
            String content = jsonObject.get("content").getAsString();
            Article article = new Article(0, url, title, content, new Date().getTime());
            return article;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
