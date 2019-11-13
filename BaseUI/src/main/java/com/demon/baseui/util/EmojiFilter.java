package com.demon.baseui.util;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 表情过滤器
 * et_text.setFilters(new InputFilter[]{new EmojiFilter()});
 */
public class EmojiFilter implements InputFilter {

    private Pattern mEmojiPattern = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        Matcher emojiMatcher = mEmojiPattern.matcher(source);

        if (emojiMatcher.find()) {
            return "";
        }
        return source;
    }
}
