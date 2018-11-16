package com.yunzhou.tdinformation.view.blog;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.utils.Utils;
import com.yunzhou.tdinformation.view.RoundCornerImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * QQ 517309507
 * 至尊流畅;daLao专用;/斜眼笑
 */
public class MessagePicturesLayout extends FrameLayout implements View.OnClickListener {

    public static final int MAX_DISPLAY_COUNT = 9;
    private final LayoutParams lpChildImage = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    private final int pxOneMaxWandH;
    private final int mSpace;
    private final List<ImageView> iPictureList = new ArrayList<>();
    private final SparseArray<ImageView> mVisiblePictureList = new SparseArray<>();

    private Callback mCallback;
    private boolean isInit;
    private List<Uri> mDataList;
    private List<Uri> mThumbDataList;

    public MessagePicturesLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        DisplayMetrics mDisplayMetrics = context.getResources().getDisplayMetrics();
        pxOneMaxWandH = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 212, mDisplayMetrics) + 0.5f);
        mSpace = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, mDisplayMetrics) + 0.5f);

        for (int i = 0; i < MAX_DISPLAY_COUNT; i++) {
            RoundCornerImageView squareImageView = new RoundCornerImageView(context);
            squareImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            squareImageView.setVisibility(View.GONE);
            squareImageView.setOnClickListener(this);
            addView(squareImageView);
            iPictureList.add(squareImageView);
        }

    }

    public void set(List<Uri> urlThumbList, List<Uri> urlList) {
        mThumbDataList = urlThumbList;
        mDataList = urlList;
        if (true) {
            notifyDataChanged();
        }
    }

    private void notifyDataChanged() {
        final List<Uri> thumbList = mThumbDataList;
        final int urlListSize = thumbList != null ? mThumbDataList.size() : 0;

        if (thumbList == null || thumbList.size() < 1) {
            setVisibility(View.GONE);
            return;
        } else {
            setVisibility(View.VISIBLE);
        }

        if (thumbList.size() > mDataList.size()) {
            throw new IllegalArgumentException("dataList.size(" + mDataList.size() + ") > thumbDataList.size(" + thumbList.size() + ")");
        }

        int column = 3;
        if (urlListSize == 1) {
            column = 1;
        } else if (urlListSize == 4) {
            column = 2;
        }
        int row = 0;
        if (urlListSize > 6) {
            row = 3;
        } else if (urlListSize > 3) {
            row = 2;
        } else if (urlListSize > 0) {
            row = 1;
        }
        final int imageSize = urlListSize == 1 ? (int) ((getWidth() * 1f - mSpace * 2) / 3) :(int) ((getWidth() * 1f - mSpace * (column - 1)) / column);
        mVisiblePictureList.clear();
        for (int i = 0; i < iPictureList.size(); i++) {
            final ImageView iPicture = iPictureList.get(i);
            if (i < urlListSize) {
                iPicture.setVisibility(View.VISIBLE);
                mVisiblePictureList.put(i, iPicture);

                if(urlListSize != 1){
                    lpChildImage.width = imageSize;
                    lpChildImage.height = lpChildImage.width;
                    iPicture.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    iPicture.setLayoutParams(lpChildImage);
                }else {
                    int expectW = 0;
                    int expectH = 0;
                    try {
                        String[] dimen = Utils.getDimenFromUri(thumbList.get(i));
                        expectW = Integer.parseInt(dimen[0]);
                        expectH = Integer.parseInt(dimen[1]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    iPicture.setAdjustViewBounds(true);

                    if(expectW == 0 || expectH == 0){
                        lpChildImage.width = imageSize;
                        lpChildImage.height = lpChildImage.width;
                        iPicture.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        iPicture.setLayoutParams(lpChildImage);
                    }else{
                        int actualW = 0;
                        int actualH = 0;
                        float scale = ((float) expectH)/((float) expectW);
                        if(expectW >= pxOneMaxWandH){
                            actualW = pxOneMaxWandH;
                            actualH = (int)(actualW * scale);
                        } else if(expectW < pxOneMaxWandH){
                            actualW = expectW;
                            actualH = (int)(actualW * scale);
                        }
                        lpChildImage.width = actualW;
                        lpChildImage.height = actualH;
                        iPicture.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        iPicture.setLayoutParams(lpChildImage);
                    }
                }
                iPicture.setBackgroundResource(R.drawable.default_picture);
                Glide.with(getContext()).load(thumbList.get(i)).into(iPicture);
                iPicture.setTranslationX((i % column) * (lpChildImage.width + mSpace));
                iPicture.setTranslationY((i / column) * (lpChildImage.height + mSpace));
            } else {
                iPicture.setVisibility(View.GONE);
            }
        }
        getLayoutParams().height = lpChildImage.height * row + mSpace * (row - 1);
    }

    @Override
    public void onClick(View v) {
        if (mCallback != null) {
            mCallback.onThumbPictureClick((ImageView) v, mVisiblePictureList, mDataList);
        }
    }

    public interface Callback {
        void onThumbPictureClick(ImageView i, SparseArray<ImageView> imageGroupList, List<Uri> urlList);
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        isInit = true;
        notifyDataChanged();
    }
}
