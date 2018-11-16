package com.yunzhou.tdinformation.blog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.ielse.imagewatcher.ImageWatcherHelper;
import com.yunzhou.common.utils.DialogHelper;
import com.yunzhou.common.utils.ToastUtil;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.activity.BaseActivity;
import com.yunzhou.tdinformation.bean.blog.BlogPostBean;
import com.yunzhou.tdinformation.blog.adapter.PostBlogTagAdapter;
import com.yunzhou.tdinformation.blog.presenter.PostBlogPresenter;
import com.yunzhou.tdinformation.blog.view.PostBlogView;
import com.yunzhou.tdinformation.view.WaitDialog;
import com.yunzhou.tdinformation.view.blog.GlideSimpleLoader;
import com.yunzhou.tdinformation.view.blog.TweetPicturesPreviewer;

import java.util.Collections;

import butterknife.BindView;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.blog
 *  @文件名:   PostBlogActivity
 *  @创建者:   lz
 *  @创建时间:  2018/10/19 11:51
 *  @描述：
 */

public class PostBlogActivity extends BaseActivity<PostBlogPresenter> implements PostBlogView, View.OnClickListener, TweetPicturesPreviewer.Callback {

    private static final String TAG = "PostBlogActivity";
    @BindView(R.id.tv_title_name)
    TextView mTvTitle;
    @BindView(R.id.rv_tag)
    RecyclerView mRv;
    @BindView(R.id.tpp_picture)
    TweetPicturesPreviewer mTppPicture;
    @BindView(R.id.et_post_detail)
    EditText mEtPost;
    @BindView(R.id.btn_cancel)
    TextView mBtnCancel;
    @BindView(R.id.btn_confirm_post)
    TextView mBtnConfirmPost;
    private PostBlogPresenter mPresenter;

    private String defaultContent = null;
    private String[] paths = null;
    private PostBlogTagAdapter mAdapter;
    private WaitDialog mWaitDialog;
    private ImageWatcherHelper iwHelper;

    @Override
    protected int getContentView() {
        return R.layout.activity_post_blog;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        mBtnCancel.setOnClickListener(this);
        mBtnConfirmPost.setOnClickListener(this);
        mAdapter = new PostBlogTagAdapter(this);
        mRv.setLayoutManager(new GridLayoutManager(this ,4));
        mRv.setAdapter(mAdapter);
        mRv.setAnimation(null);
        mRv.setItemAnimator(null);
        initImageWatch();
        mTppPicture.setCallback(this);
    }

    @Override
   protected void initData() {
        defaultContent = mPresenter.loadContentFromCache();
        paths = mPresenter.loadImgFromCache();
        if (!TextUtils.isEmpty(defaultContent)) {
            mEtPost.setText(defaultContent);
            mEtPost.setSelection(mEtPost.getText().length());
        }
        if (paths != null && paths.length > 0)
            mTppPicture.set(paths);

        mAdapter.setData(mPresenter.getList(getResources().getStringArray(R.array.tag)));
    }

    @Override
    protected PostBlogPresenter createP(Context context) {
        mPresenter = new PostBlogPresenter();
        return mPresenter;
    }
    private void initImageWatch() {
        iwHelper = ImageWatcherHelper.with(this, new GlideSimpleLoader())
                .setTranslucentStatus(25)
                .setErrorImageRes(R.mipmap.error_picture);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                mPresenter.saveContentFromCache(mEtPost.getText().toString().trim());
                mPresenter.saveImgFromCache(mTppPicture.getPaths());
                finish();
                break;
            case R.id.btn_confirm_post:
                //弹出等待弹窗,上传图片 发帖
                mPresenter.postBlog(this , mEtPost.getText().toString() ,mTppPicture.getPaths() ,mAdapter.getTag());
                break;
            default:

                break;
        }
    }
    @Override
    public void showUploadImgSuccess() {
        Log.d(TAG, "showUploadImgSuccess:");
    }

    @Override
    public void showUploadImgError(int length, int failedCount) {
        DialogHelper.getConfirmDialog(this, "",
                "成功上传" + (length - failedCount) + "张," + "失败 " + failedCount + "张",
                "重试", "取消", false ,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 重新上传图片
                mPresenter.postBlog(PostBlogActivity.this, mEtPost.getText().toString(), mTppPicture.getPaths() ,mAdapter.getTag());
            }
        } , new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 已上传的丢弃,用户重新走流程
                mPresenter.reset();
                dialog.dismiss();
            }
        }).show();
    }

    @Override
    public Context getContext() {
        return PostBlogActivity.this;
    }

    @Override
    public void showRealPostError(final BlogPostBean blogPostBean) {
        DialogHelper.getConfirmDialog(this, "",
                "发帖失败,是否重试?",
                "重新发送", "取消", false ,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 重新上传图片
                        mPresenter.realPost(PostBlogActivity.this, blogPostBean);
                    }
                } , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 用户重新走流程
                        mPresenter.reset();
                        dialog.dismiss();
                    }
                }).show();
    }

    @Override
    public void showRealPostSuccess() {
        ToastUtil.showShort(this ,"发帖成功");
        finish();
    }

    @Override
    public void showWaitDialog(String tips) {
        if (mWaitDialog == null) {
            mWaitDialog = new WaitDialog(this);
            mWaitDialog.setCanceledOnTouchOutside(false);
        }
        mWaitDialog.setRemindTxt(tips);
        mWaitDialog.show();
    }

    @Override
    public void showMessage(String msg) {
        ToastUtil.showShort(this ,msg);
    }

    @Override
    public void showLoading() {
        if (mWaitDialog == null) {
            mWaitDialog = new WaitDialog(this);
        }
        mWaitDialog.setRemindTxt(R.string.request_wait);
        mWaitDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mWaitDialog != null) {
            mWaitDialog.dismiss();
        }
    }


    public static void start(Context context) {
        Intent starter = new Intent(context, PostBlogActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void onPreviewImg(String path) {
        iwHelper.show(Collections.singletonList(Uri.parse("file://".concat(path))));
    }

    @Override
    public void onBackPressed() {
        if (!iwHelper.handleBackPressed()) {
            super.onBackPressed();
        }
    }
}
