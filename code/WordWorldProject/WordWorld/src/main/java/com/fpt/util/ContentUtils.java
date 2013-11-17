package com.fpt.util;

import com.fpt.config.Config;

/**
 * Created by Huynh Quang Thao on 11/15/13.
 */
public class ContentUtils {
    public static String addCSSJS(String content) {
        String css = "<style>img {max-width: 100%} "
                + "." + Config.HIGHLIGHT_CLASSNAME
                + "{ background-color: yellow; }"
                + "." + Config.NO_HIGHLIGHT_CLASSNAME
                + "{ background-color: white !important; }"
                + "." + Config.HIGHLIGHTOFF_CLASSNAME
                + "{ background-color: white !important; }"
                + "</style>";

        String js = "<script>\n" +
                "var unique_seq = 0; " +

                "function turn_highlight_off() {" +
                    "var elements = document.getElementsByClassName('"+ Config.HIGHLIGHT_CLASSNAME+"');\n" +
                    "var elements2 = document.getElementsByClassName('"+ Config.NO_HIGHLIGHT_CLASSNAME+"');\n" +
                    "for (var i = 0; i < elements.length; i++) {   \n" +
                    "\n" +
                    " elements[i].className = '"+ Config.HIGHLIGHT_CLASSNAME +" "+Config.HIGHLIGHTOFF_CLASSNAME+"';" +
                    "\n" +
                    "}\n" +
                    "for (var i = 0; i < elements2.length; i++) {   \n" +
                    "\n" +
                    " elements2[i].className = '"+ Config.NO_HIGHLIGHT_CLASSNAME +" "+Config.HIGHLIGHTOFF_CLASSNAME+"';" +
                    "\n" +
                    "}\n" +
                "}" +

                "function turn_highlight_on() {" +
                    "var elements = document.getElementsByClassName('"+ Config.HIGHLIGHT_CLASSNAME+"');\n" +
                    "var elements2 = document.getElementsByClassName('"+ Config.NO_HIGHLIGHT_CLASSNAME+"');\n" +
                    "for (var i = 0; i < elements.length; i++) {   \n" +
                    "\n" +
                    " elements[i].className = '"+Config.HIGHLIGHT_CLASSNAME+"';" +
                    "\n" +
                    "}\n" +
                    "for (var i = 0; i < elements2.length; i++) {   \n" +
                    "\n" +
                    " elements2[i].className = '"+Config.NO_HIGHLIGHT_CLASSNAME+"';" +
                    "\n" +
                    "}\n" +
                "}" +


                "document.querySelector('body').addEventListener('click', function(event) {\n" +
                " unique_seq++;" +
                " event.target.setAttribute('id', 'u_' + unique_seq);" +
                "  if (event.target.className === '"+Config.HIGHLIGHT_CLASSNAME+"') {\n" +
                "   AndroidCallback.addWord(event.target.innerText, unique_seq);      " +
                "  } else if (event.target.className === '"+Config.NO_HIGHLIGHT_CLASSNAME+"') {" +
                "   AndroidCallback.removeWord(event.target.innerText, unique_seq);   " +
                "  }" +
                "});" +

                "function unwrap_word(unique_seq) {" +
                "document.getElementById('u_' + unique_seq).className = '"+Config.NO_HIGHLIGHT_CLASSNAME+"';" +
                "}" +
                "function wrap_word(unique_seq) {" +
                "document.getElementById('u_' + unique_seq).className = '"+Config.HIGHLIGHT_CLASSNAME+"';" +
                "}" +

                "</script>";
        return content + css + js;
    }
}
