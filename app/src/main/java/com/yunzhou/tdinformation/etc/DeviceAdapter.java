package com.yunzhou.tdinformation.etc;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.BaseRvAdapter;


/*
 *  @项目名：  mock500
 *  @包名：    com.yunzhou.tdinformation.etc
 *  @文件名:   SimpleAdapter
 *  @创建者:   lz
 *  @创建时间:  2019/1/29 15:01
 *  @描述：
 */
public class DeviceAdapter extends BaseRvAdapter<BluetoothDevice> {

    public DeviceAdapter(Context context) {
        super(context);
    }

    @Override
    protected RecyclerView.ViewHolder createHolderView(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_article, parent, false);
        return new ArticleHolder(view);
    }

    @Override
    protected void bindHolderView(RecyclerView.ViewHolder holder, BluetoothDevice bluetoothDevice, int position) {
        if (holder instanceof ArticleHolder) {
            ArticleHolder vh = (ArticleHolder) holder;
            vh.mTvArticleDetail.setText("name : " + bluetoothDevice.getName()+ ";" + position);
            vh.mTvAuthorName.setText(bluetoothDevice.getAddress());
            vh.mTvViewCount.setText(String.valueOf(bluetoothDevice.getType()));
            vh.mTvPayTag.setVisibility(View.GONE);
        }
    }

    static class ArticleHolder extends RecyclerView.ViewHolder {
        TextView mTvPayTag;
        TextView mTvArticleDetail;
        TextView mTvTime;
        TextView mTvViewCount;
        ImageView mIvArticleAuthor;
        TextView mTvAuthorName;

        public ArticleHolder(View itemView) {
            super(itemView);
            mTvPayTag = itemView.findViewById(R.id.tv_pay_tag);
            mTvArticleDetail = itemView.findViewById(R.id.tv_article_detail);
            mTvTime = itemView.findViewById(R.id.tv_time);
            mTvViewCount = itemView.findViewById(R.id.tv_view_count);
            mIvArticleAuthor = itemView.findViewById(R.id.iv_article_author);
            mTvAuthorName = itemView.findViewById(R.id.tv_author_name);
        }
    }
}
