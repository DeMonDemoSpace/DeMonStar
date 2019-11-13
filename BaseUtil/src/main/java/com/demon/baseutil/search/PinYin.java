package com.demon.baseutil.search;

import com.demon.baseutil.coding.HanziToPinyin;
import com.demon.baseutil.coding.HanziToPinyin.Token;

import java.util.ArrayList;

/**
 * @author DeMon
 * @date 2018/1/9
 * @description
 */

public class PinYin {
    public static String getPinYin(String input) {
        ArrayList<Token> tokens = HanziToPinyin.getInstance().get(input);
        StringBuilder sb = new StringBuilder();
        if (tokens != null && tokens.size() > 0) {
            for (Token token : tokens) {
                if (Token.PINYIN == token.type) {
                    sb.append(token.target);
                } else {
                    sb.append(token.source);
                }
            }
        }
        return sb.toString().toLowerCase();
    }

    /**
     * 如果字符串首位是英文就返回首字母，否则返回'#'
     *
     * @param s
     * @return
     */
    public static String getInitials(String s) {
        String initials = PinYin.getPinYin(s).substring(0, 1);
        if (initials.matches("^[a-zA-Z]*")) {
            return initials;
        } else {
            return "#";
        }


    }
}
