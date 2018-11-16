package com.yunzhou.common.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;


/**
 * 通用的对话框
 * Created by haibin
 * on 2016/11/2.
 */
@SuppressWarnings("all")
public final class DialogHelper {
    public static AlertDialog.Builder getDialog(Context context) {
        // TODO: 2018/10/19 待设置主题 R.style.App_Theme_Dialog_Alert
        return new AlertDialog.Builder(context);
    }

    /**
     * 获取一个普通的消息对话框，没有取消按钮
     */
    public static AlertDialog.Builder getMessageDialog(
            Context context,
            String title,
            String message,
            boolean cancelable) {
        return getDialog(context)
                .setCancelable(cancelable)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("确定", null);
    }

    /**
     * 获取一个普通的消息对话框，没有取消按钮
     */
    public static AlertDialog.Builder getMessageDialog(
            Context context,
            String title,
            String message) {
        return getMessageDialog(context, title, message, true);
    }

    /**
     * 获取一个普通的消息对话框，没有取消按钮
     */
    public static AlertDialog.Builder getMessageDialog(Context context, String message) {
        return getMessageDialog(context, "", message, true);
    }

    /**
     * 获取一个普通的消息对话框，没有取消按钮
     */
    public static AlertDialog.Builder getMessageDialog(
            Context context,
            String title,
            String message,
            String positiveText) {
        return getDialog(context)
                .setCancelable(true)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveText, null);
    }

    public static AlertDialog.Builder getConfirmDialog(Context context,
                                                       String title,
                                                       View view,
                                                       DialogInterface.OnClickListener positiveListener) {
        return getDialog(context)
                .setTitle(title)
                .setView(view)
                .setPositiveButton("确定", positiveListener)
                .setNegativeButton("取消", null);
    }

    public static AlertDialog.Builder getConfirmDialog(Context context,
                                                       String title,
                                                       View view,
                                                       String positiveText,
                                                       String negativeText,
                                                       DialogInterface.OnClickListener positiveListener) {
        return getDialog(context)
                .setTitle(title)
                .setView(view)
                .setCancelable(true)
                .setPositiveButton(positiveText, positiveListener)
                .setNegativeButton(negativeText, null);
    }

    /**
     * 获取一个验证对话框
     */
    public static AlertDialog.Builder getConfirmDialog(
            Context context,
            String title,
            String message,
            String positiveText,
            String negativeText,
            boolean cancelable,
            DialogInterface.OnClickListener positiveListener,
            DialogInterface.OnClickListener negativeListener) {
        return getDialog(context)
                .setCancelable(cancelable)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveText, positiveListener)
                .setNegativeButton(negativeText, negativeListener);
    }

    /**
     * 获取一个验证对话框
     */
    public static AlertDialog.Builder getConfirmDialog(
            Context context, String message,
            DialogInterface.OnClickListener positiveListener,
            DialogInterface.OnClickListener negativeListener) {
        return getDialog(context)
                .setMessage(message)
                .setPositiveButton("确定", positiveListener)
                .setNegativeButton("取消", negativeListener);
    }

    public static AlertDialog.Builder getSingleChoiceDialog(
            Context context,
            String title,
            String[] arrays,
            int selectIndex,
            DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setSingleChoiceItems(arrays, selectIndex, onClickListener);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        builder.setNegativeButton("取消", null);
        return builder;
    }


    /**
     * 获取一个验证对话框，没有点击事件
     */
    public static AlertDialog.Builder getConfirmDialog(
            Context context,
            String title,
            String message,
            String positiveText,
            String negativeText,
            boolean cancelable,
            DialogInterface.OnClickListener positiveListener) {
        return getConfirmDialog(
                context, title, message, positiveText,
                negativeText, cancelable, positiveListener, null);
    }

    /**
     * 获取一个验证对话框，没有点击事件
     */
    public static AlertDialog.Builder getConfirmDialog(
            Context context,
            String title,
            String message,
            String positiveText,
            String negativeText,
            DialogInterface.OnClickListener positiveListener) {
        return getConfirmDialog(
                context, title, message, positiveText, negativeText, true, positiveListener, null);
    }


    /**
     * 获取一个验证对话框，没有点击事件
     */
    public static AlertDialog.Builder getConfirmDialog(
            Context context,
            String title,
            String message,
            String positiveText,
            String negativeText,
            boolean cancelable) {
        return getConfirmDialog(
                context, title, message, positiveText, negativeText, cancelable, null, null);
    }

    /**
     * 获取一个验证对话框，没有点击事件
     */
    public static AlertDialog.Builder getConfirmDialog(
            Context context,
            String message,
            String positiveText,
            String negativeText,
            boolean cancelable) {
        return getConfirmDialog(context, "", message, positiveText, negativeText
                , cancelable, null, null);
    }

    /**
     * 获取一个验证对话框，没有点击事件，取消、确定
     */
    public static AlertDialog.Builder getConfirmDialog(
            Context context,
            String title,
            String message,
            boolean cancelable) {
        return getConfirmDialog(context, title, message, "确定", "取消", cancelable, null, null);
    }

    /**
     * 获取一个验证对话框，没有点击事件，取消、确定
     */
    public static AlertDialog.Builder getConfirmDialog(
            Context context,
            String message,
            boolean cancelable,
            DialogInterface.OnClickListener positiveListener) {
        return getConfirmDialog(context, "", message, "确定", "取消", cancelable, positiveListener, null);
    }

    /**
     * 获取一个验证对话框，没有点击事件，取消、确定
     */
    public static AlertDialog.Builder getConfirmDialog(
            Context context,
            String message,
            DialogInterface.OnClickListener positiveListener) {
        return getConfirmDialog(context, "", message, "确定", "取消", positiveListener);
    }

    /**
     * 获取一个验证对话框，没有点击事件，取消、确定
     */
    public static AlertDialog.Builder getConfirmDialog(
            Context context,
            String title,
            String message) {
        return getConfirmDialog(context, title, message, "确定", "取消", false, null, null);
    }

    /**
     * 获取一个输入对话框
     */
    public static AlertDialog.Builder getInputDialog(
            Context context,
            String title,
            AppCompatEditText editText,
            String positiveText,
            String negativeText,
            boolean cancelable,
            DialogInterface.OnClickListener positiveListener,
            DialogInterface.OnClickListener negativeListener) {
        return getDialog(context)
                .setCancelable(cancelable)
                .setTitle(title)
                .setView(editText)
                .setPositiveButton(positiveText, positiveListener)
                .setNegativeButton(negativeText, negativeListener);
    }

    /**
     * 获取一个输入对话框
     */
    public static AlertDialog.Builder getInputDialog(
            Context context, String title,
            AppCompatEditText editText,
            String positiveText,
            String negativeText,
            boolean cancelable,
            DialogInterface.OnClickListener positiveListener) {
        return getInputDialog(
                context,
                title,
                editText,
                positiveText,
                negativeText,
                cancelable,
                positiveListener,
                null);
    }

    /**
     * 获取一个输入对话框
     */
    public static AlertDialog.Builder getInputDialog(
            Context context,
            String title,
            AppCompatEditText editText,
            boolean cancelable,
            DialogInterface.OnClickListener positiveListener) {
        return getInputDialog(context, title, editText, "确定", "取消"
                , cancelable, positiveListener, null);
    }

    /**
     * 获取一个输入对话框
     */
    public static AlertDialog.Builder getInputDialog(
            Context context, String title, AppCompatEditText editText, String positiveText,
            boolean cancelable,
            DialogInterface.OnClickListener positiveListener,
            DialogInterface.OnClickListener negativeListener) {
        return getInputDialog(
                context, title, editText, positiveText, "取消", cancelable
                , positiveListener, negativeListener);
    }

    /**
     * 获取一个输入对话框
     */
    public static AlertDialog.Builder getInputDialog(
            Context context, String title, AppCompatEditText editText,
            boolean cancelable,
            DialogInterface.OnClickListener positiveListener,
            DialogInterface.OnClickListener negativeListener) {
        return getInputDialog(
                context, title, editText, "确定", "取消", cancelable
                , positiveListener, negativeListener);
    }


    /**
     * 获取一个等待对话框
     */
    public static ProgressDialog getProgressDialog(Context context) {
        return new ProgressDialog(context);
    }

    /**
     * 获取一个等待对话框
     */
    public static ProgressDialog getProgressDialog(Context context, boolean cancelable) {
        ProgressDialog dialog = getProgressDialog(context);
        dialog.setCancelable(cancelable);
        return dialog;
    }

    /**
     * 获取一个等待对话框
     */
    public static ProgressDialog getProgressDialog(Context context, String message) {
        ProgressDialog dialog = getProgressDialog(context);
        dialog.setMessage(message);
        return dialog;
    }

    /**
     * 获取一个等待对话框
     */
    public static ProgressDialog getProgressDialog(
            Context context, String title, String message, boolean cancelable) {
        ProgressDialog dialog = getProgressDialog(context);
        dialog.setCancelable(cancelable);
        dialog.setTitle(title);
        dialog.setMessage(message);
        return dialog;
    }

    /**
     * 获取一个等待对话框
     */
    public static ProgressDialog getProgressDialog(
            Context context, String message, boolean cancelable) {
        ProgressDialog dialog = getProgressDialog(context);
        dialog.setCancelable(cancelable);
        dialog.setMessage(message);
        return dialog;
    }

    public static AlertDialog.Builder getSelectDialog(
            Context context, String title, String[] items,
            String positiveText,
            DialogInterface.OnClickListener itemListener) {
        return getDialog(context)
                .setTitle(title)
                .setItems(items, itemListener)
                .setPositiveButton(positiveText, null);

    }

    public static AlertDialog.Builder getSelectDialog(
            Context context, String[] items,
            String positiveText,
            DialogInterface.OnClickListener itemListener) {
        return getDialog(context)
                .setItems(items, itemListener)
                .setPositiveButton(positiveText, null);

    }

    public static AlertDialog.Builder getSelectDialog(Context context, View view, String positiveText,
                                                      DialogInterface.OnClickListener itemListener) {
        return getDialog(context)
                .setView(view)
                .setPositiveButton(positiveText, null);
    }
}
