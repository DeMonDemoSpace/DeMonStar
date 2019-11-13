package com.demon.baseutil.search;

import android.text.TextUtils;

import com.demon.baseutil.coding.UnicodeGBK2Alpha;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author DeMon
 * @date 2018/1/9
 * @description
 */

public class SearchUtil {
    /**
     * 根据拼音搜索
     *
     * @return
     */
    public static boolean searchPinyin(String text, String query) {
        if (TextUtils.isEmpty(text) || TextUtils.isEmpty(query)) {
            return false;
        }
        boolean flag = false;
        // 简拼匹配,如果输入在字符串长度大于6就不按首字母匹配了
        if (query.length() < 6) {
            // 获得首字母字符串
            String firstLetters = UnicodeGBK2Alpha.getSimpleCharsOfString(text);
            // 不区分大小写
            Pattern firstLetterMatcher = Pattern.compile("^" + query,
                    Pattern.CASE_INSENSITIVE);
            flag = firstLetterMatcher.matcher(firstLetters).find();
        }
        if (!flag) { // 如果简拼已经找到了，就不使用全拼了
            // 全拼匹配,不区分大小写
            Pattern pattern2 = Pattern.compile(query, Pattern.CASE_INSENSITIVE);
            Matcher matcher2 = pattern2.matcher(PinYin.getPinYin(text));
            flag = matcher2.find();
        }
        return flag;
    }


    public static boolean searchText(String text, String query) {
        if (TextUtils.isEmpty(text) || TextUtils.isEmpty(query)) {
            return false;
        }
        Pattern pattern = Pattern.compile(query, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }


    public static boolean searchNumber(String text, String query) {
        if (TextUtils.isEmpty(text) || TextUtils.isEmpty(query)) {
            return false;
        }
        return text.contains(query);
    }

}
