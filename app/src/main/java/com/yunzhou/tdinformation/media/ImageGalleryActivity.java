package com.yunzhou.tdinformation.media;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.github.ielse.imagewatcher.ImageWatcher;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.activity.BaseCommonAct;
import com.yunzhou.tdinformation.utils.Utils;
import com.yunzhou.tdinformation.view.blog.GlideSimpleLoader;

import java.util.ArrayList;

import butterknife.BindView;


/**
 * 图片预览Activity
 */
public class ImageGalleryActivity extends BaseCommonAct {
    private static final String TAG = "ImageGalleryActivity";
    public static final String KEY_IMG_LIST = "key_img_list";
    public static final String KEY_IMG_BUNDLE = "key_img_bundle";
    @BindView(R.id.iw_watch)
    ImageWatcher mIwWatch;
    private ArrayList<Uri> mImgList;

    @Override
    protected int getContentView() {
        return R.layout.activity_image_gallery;
    }

    @Override
    protected void initWindow() {
        super.initWindow();
    }

    @Override
    protected void initWidget(Bundle bundle) {
        super.initWidget(bundle);
        initBundle(getIntent());
        getWindow().setBackgroundDrawable(null);
        setTitle("");
        Utils.setStatusBarColor(this , getResources().getColor(android.R.color.black));
        mIwWatch.setErrorImageRes(R.mipmap.error_picture);
        mIwWatch.setLoader(new GlideSimpleLoader());
        mIwWatch.setOnStateChangedListener(new ImageWatcher.OnStateChangedListener() {
            @Override
            public void onStateChangeUpdate(ImageWatcher imageWatcher, ImageView imageView, int i, Uri uri, float v, int i1) {
            }
            @Override
            public void onStateChanged(ImageWatcher imageWatcher, int position, Uri uri, int actionTag) {
                if (actionTag == ImageWatcher.STATE_EXIT_HIDING) {
                    finish();
                }
            }
        });
        mIwWatch.show(mImgList);
    }

    private void initBundle(Intent intent) {
        Bundle bundle = intent.getBundleExtra(KEY_IMG_BUNDLE);
        mImgList = bundle.getParcelableArrayList(KEY_IMG_LIST);
        if (mImgList == null)
            finish();
    }

    @Override
    protected void initData() {
        super.initData();
    }

    public static void start(Context context, ArrayList<Uri> imgList) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(KEY_IMG_LIST, imgList);
        Intent starter = new Intent(context, ImageGalleryActivity.class);
        starter.putExtra(KEY_IMG_BUNDLE, bundle);
        context.startActivity(starter);
    }

}
