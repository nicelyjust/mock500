package com.yunzhou.tdinformation.view.Comment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yunzhou.common.utils.TDevice;
import com.yunzhou.tdinformation.R;

/*
 *  @项目名：  TDInformation
 *  @包名：    com.yunzhou.tdinformation.view.Comment
 *  @创建者:   lz
 *  @创建时间:  2018/10/14 12:38
 *  @修改时间:  nicely 2018/10/14 12:38
 *  @描述：
 */
@SuppressWarnings("unused")
public class BottomSheetBar {

    private View mRootView;
    private EditText mEditText;
    private Context mContext;
    private Button mBtnCommit;
    private BottomDialog mDialog;


    private BottomSheetBar(Context context) {
        this.mContext = context;
    }

    @SuppressLint("InflateParams")
    public static BottomSheetBar delegation(Context context) {
        BottomSheetBar bar = new BottomSheetBar(context);
        bar.mRootView = LayoutInflater.from(context).inflate(R.layout.layout_bottom_sheet_comment_bar, null, false);
        bar.initView();
        return bar;
    }

    private void initView() {
        mEditText =  mRootView.findViewById(R.id.et_comment);
        mBtnCommit =  mRootView.findViewById(R.id.btn_comment);
        mBtnCommit.setEnabled(false);

        mDialog = new BottomDialog(mContext, false);
        mDialog.setContentView(mRootView);

        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                TDevice.closeKeyboard(mEditText);
            }
        });

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mBtnCommit.setEnabled(s.length() > 0);
            }
        });
    }

    public void show(String hint) {
        mDialog.show();
        mEditText.setHint(hint + " ");
        mRootView.postDelayed(new Runnable() {
            @Override
            public void run() {
                TDevice.showSoftKeyboard(mEditText);
            }
        }, 50);
    }

    public void dismiss() {
        TDevice.closeKeyboard(mEditText);
        mDialog.dismiss();
    }


    public void setCommitListener(View.OnClickListener listener) {
        mBtnCommit.setOnClickListener(listener);
    }

    public EditText getEditText() {
        return mEditText;
    }

    public String getCommentText() {
        String str = mEditText.getText().toString();
        return TextUtils.isEmpty(str) ? "" : str.trim();
    }

    public Button getBtnCommit() {
        return mBtnCommit;
    }


}
