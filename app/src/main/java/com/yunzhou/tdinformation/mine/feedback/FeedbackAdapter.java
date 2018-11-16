package com.yunzhou.tdinformation.mine.feedback;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.yunzhou.tdinformation.base.BaseRvAdapter;
import com.yunzhou.tdinformation.bean.HelpEntity;


/*
 *  @项目名：  project 
 *  @包名：    com.yunzhou.tdinformation.mine.feedback
 *  @文件名:   FeedbackAdapter
 *  @创建者:   lz
 *  @创建时间:  2018/11/5 19:04
 *  @描述：    TODO
 */

public class FeedbackAdapter extends BaseRvAdapter<HelpEntity> {

    public FeedbackAdapter(Context context) {
        super(context);
    }

    @Override
    protected RecyclerView.ViewHolder createHolderView(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected void bindHolderView(RecyclerView.ViewHolder holder, HelpEntity helpEntity, int position) {

    }

}
