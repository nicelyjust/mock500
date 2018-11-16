package com.yunzhou.tdinformation.mine.help;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.BaseRvAdapter;
import com.yunzhou.tdinformation.bean.HelpEntity;
import com.yunzhou.tdinformation.web.WebDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.mine.help
 *  @文件名:   HelpAdapter
 *  @创建者:   lz
 *  @创建时间:  2018/11/5 16:13
 *  @描述：
 */

public class HelpAdapter extends BaseRvAdapter<HelpEntity> {

    private final OnItemClickListener mListener;
    // 1:意见反馈 2:帮助中心
    private int mFromWhere;

    public HelpAdapter(Context context, OnItemClickListener listener, int fromWhere) {
        super(context);
        mListener = listener;
        mFromWhere = fromWhere;
    }

    @Override
    protected RecyclerView.ViewHolder createHolderView(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_simple_text, parent, false);
        return new HelpViewHolder(view);
    }

    @Override
    protected void bindHolderView(RecyclerView.ViewHolder holder, HelpEntity helpEntity, int position) {
        if (holder instanceof HelpViewHolder) {
            HelpViewHolder vh = (HelpViewHolder) holder;
            vh.mVBlockGray.setVisibility(position == 0 && mFromWhere == 1 ? View.VISIBLE : View.GONE);
            vh.mTvName.setText(helpEntity.subject);
            vh.mTvDescPlus.setVisibility(TextUtils.isEmpty(helpEntity.desc) ? View.GONE : View.VISIBLE);
            vh.mTvDescPlus.setText(helpEntity.desc);
            vh.mIbArrow.setVisibility(View.VISIBLE);
            vh.itemView.setTag(position);
            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag();
                    HelpEntity entity = mItems.get(pos);
                    if (!TextUtils.isEmpty(entity.url)) {
                        WebDetailActivity.start(mContext, 2, entity.url);
                    } else {
                        mListener.onItemClick(pos);
                    }
                }
            });
        }
    }

    static class HelpViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.v_block_gray)
        View mVBlockGray;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.ib_arrow)
        ImageButton mIbArrow;
        @BindView(R.id.tv_desc_plus)
        TextView mTvDescPlus;

        public HelpViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }
}
