package com.fpt.util;

import com.fpt.config.Config;

/**
 * Created by Huynh Quang Thao on 11/15/13.
 */
public class ContentUtils {
    public static String addCSSJS(String content) {
        String css = "<style>img {max-width: 100%} ." + Config.HIGHLIGHT_CLASSNAME + "{ background-color: yellow; }</style>";
        String js = "<script>\n" +
                "var elements = document.getElementsByClassName('"+ Config.HIGHLIGHT_CLASSNAME+"');\n" +
                "for (var i = 0; i < elements.length; i++) {   \n" +
                "\n" +
                " elements[i].onclick = function() {\n" +
                "    console.log(this.innerText);\n" +
                "\tAndroidCallback.addWord(this.innerText);\n" +
                " }\n" +
                "\n" +
                "}\n" +
                "</script>";
        return content + css + js;
    }
}
