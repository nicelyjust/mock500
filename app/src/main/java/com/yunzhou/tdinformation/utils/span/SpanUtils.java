package com.yunzhou.tdinformation.utils.span;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.utils
 *  @文件名:   SpanUtils
 *  @创建者:   lz
 *  @创建时间:  2018/10/12 9:49
 *  @描述：
 */

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;

public class SpanUtils {
    public static SpannableStringBuilder makeSingleCommentSpan(Context context, String childUserName, String commentContent) {
        String richText = String.format("%s: %s", childUserName, commentContent);
        SpannableStringBuilder builder = new SpannableStringBuilder(richText);
        if (!TextUtils.isEmpty(childUserName)) {
            builder.setSpan(new TextClickSpan(context), 0, childUserName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return builder;
    }

    public static SpannableStringBuilder makeReplyCommentSpan(Context context, String parentUserName, String childUserName, String commentContent) {
        String richText = String.format("%s回复%s: %s", childUserName, parentUserName, commentContent);
        SpannableStringBuilder builder = new SpannableStringBuilder(richText);
        int parentEnd = 0;
        if (!TextUtils.isEmpty(childUserName)) {
            parentEnd = childUserName.length();
            builder.setSpan(new TextClickSpan(context), 0, parentEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        if (!TextUtils.isEmpty(parentUserName)) {
            int childStart = parentEnd + 2;
            int childEnd = childStart + parentUserName.length();
            builder.setSpan(new TextClickSpan(context), childStart, childEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return builder;
    }
}
