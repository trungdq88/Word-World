package com.fpt.util;

/**
 * Created by Huynh Quang Thao on 11/15/13.
 */
public class HQTUtils {

    public static String generateHTML() {
        String res = "<html>\n" +
                "<head>\n" +
                "<title>Do it</title>\n" +
                "\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "<h2>HuynXXh <span class='hkt_highlight'>XXQuang</span> Thao</h2>\n" +
                "<h2>TranXX <span class='hkt_highlight'>KiXm</span> Du</h2>\n" +
                "</body>\n" +
                "</html>\n" +
                "<script>\n" +
                "var elements = document.getElementsByClassName('hkt_highlight');\n" +
                "for (var i = 0; i < elements.length; i++) {   \n" +
                "\n" +
                " elements[i].onclick = function() {\n" +
                "\tAndroidCallback.removeWord(this.innerText);\n" +
                " }\n" +
                "\n" +
                "}\n" +
                "</script>";
        return res;
    }
}
