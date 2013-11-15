package com.fpt.util;

/**
 * Created by Huynh Quang Thao on 11/15/13.
 */
public class HQTUtils {

    public static String generateHTML() {
        String res = "<html>"
                + "<head>"
                + "<title>JSP Page</title>"
                + "</head>"
                + "<body>"
                + "<h1>Huynh <span class='ww_highlight'>Quang</span> Thao</h1>"
                + "<h2>Tran Kim Du</h2>"
                + "</body>"
                + "</html>";
        return res;
    }
}
