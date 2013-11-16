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
                "<h2>Huynh <span class='hkt_highlight'>Quang</span> Thao</h2>\n" +
                "<h2>Tran <span class='hkt_highlight'>Kim</span> Du</h2>\n" +
                "</body>\n" +
                "</html>\n" +
                "<script>\n" +
                "var elements = document.getElementsByClassName('hkt_highlight');\n" +
                "for (var i = 0; i < elements.length; i++) {   \n" +
                "\n" +
                " elements[i].onclick = function() {\n" +
                "    console.log(this.innerText);\n" +
                "\tAndroidCallback.addWord(this.innerText);\n" +
                " }\n" +
                "\n" +
                "}\n" +
                "</script>";
        return res;
    }
}
