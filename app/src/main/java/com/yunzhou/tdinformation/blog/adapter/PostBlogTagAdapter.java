package com.yunzhou.tdinformation.blog.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.BaseRvAdapter;
import com.yunzhou.tdinformation.bean.blog.TagBean;

import butterknife.BindView;
import butterknife.ButterKnife;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.blog.adapter
 *  @文件名:   PostBlogTagAdapter
 *  @创建者:   lz
 *  @创建时间:  2018/10/22 11:02
 *  @描述：
 */

public class PostBlogTagAdapter extends BaseRvAdapter<TagBean> {
    private int prePos = -1;

    public PostBlogTagAdapter(Context context) {
        super(context);
    }

    @Override
    protected RecyclerView.ViewHolder createHolderView(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_tag, parent, false);
        return new TagVH(view);
    }

    @Override
    protected void bindHolderView(RecyclerView.ViewHolder holder, TagBean tagBean, int position) {
        if (holder instanceof TagVH) {
            final TagVH vh = (TagVH) holder;
            vh.mTvTagName.setText(tagBean.tagName);
            vh.mTvTagName.setEnabled(tagBean.isEnabled);
            vh.itemView.setTag(position);
            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int lastPos = (int) v.getTag();
                    if (prePos != lastPos || prePos == -1) {
                        TagBean bean = mItems.get(lastPos);
                        vh.mTvTagName.setEnabled(false);
                        bean.isEnabled = false;
                        if (prePos != -1) {
                            mItems.get(prePos).isEnabled = true;
                            notifyItemChanged(prePos);
                        }
                        prePos = lastPos;
                    }

                }
            });
        }
    }
    @Nullable
    public String getTag() {
        try {
            return mItems.get(prePos).tagName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static class TagVH extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_tag_name)
        TextView mTvTagName;

        public TagVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
