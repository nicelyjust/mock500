package com.yunzhou.tdinformation.community.adapter;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.community
 *  @文件名:   BlogAdapter
 *  @创建者:   lz
 *  @创建时间:  2018/10/8 17:21
 *  @描述：    有两种Item,带图片以及不带图片的
 */

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yunzhou.common.http.dialog.ToastUtils;
import com.yunzhou.common.utils.TDevice;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.bean.community.Blog;
import com.yunzhou.tdinformation.bean.community.ImgUrlBean;
import com.yunzhou.tdinformation.login.LoginActivity;
import com.yunzhou.tdinformation.user.UserManager;
import com.yunzhou.tdinformation.view.blog.MessagePicturesLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.BaseBlogViewHolder> {

    private static final String TAG = "BlogAdapter";
    public static final int TYPE_GROUND = 175;
    public static final int TYPE_OTHER = 176;
    public static final int TYPE_MY_POST = 177;
    private Context mContext;
    private int mTypeFrom = TYPE_GROUND;
    private static final int ITEM_WORD_IMG = 0x01;
    private static final int ITEM_WORD = 0x02;

    @IntDef({TYPE_GROUND, TYPE_OTHER, TYPE_MY_POST})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FromWhere {
    }

    private List<Blog> mBlog;
    private OnBlogClickListener mOnBlogClickListener;
    private MessagePicturesLayout.Callback mCallback;
    private Blog mFirstBlog;

    public BlogAdapter(Context context, @FromWhere int type) {
        this.mContext = context;
        this.mTypeFrom = type;
        this.mBlog = new ArrayList<>();
    }

    public BlogAdapter setPictureClickCallback(MessagePicturesLayout.Callback callback) {
        mCallback = callback;
        return this;
    }

    @NonNull
    @Override
    public BaseBlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case ITEM_WORD_IMG:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_blog_has_pictures, parent, false);
                return new WordAndImgViewHolder(view);
            default:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_blog_common, parent, false);
                return new WordViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        try {
            Blog blog = mBlog.get(position);
            List<ImgUrlBean> imgList = blog.getImgList();
            if (imgList == null || imgList.isEmpty()) {
                return ITEM_WORD;
            } else {
                return ITEM_WORD_IMG;
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
            return ITEM_WORD;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseBlogViewHolder holder, int position) {

        setBaseHolder(holder, position);
        if (holder instanceof WordAndImgViewHolder) {
            WordAndImgViewHolder wordAndImgVH = (WordAndImgViewHolder) holder;
            wordAndImgVH.mPicturesLayout.setCallback(mCallback);
            Blog blog = mBlog.get(position);
            wordAndImgVH.mPicturesLayout.set(blog.urlThumbList, blog.urlList);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseBlogViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        // TODO: 2018/10/10 点赞未成功, 收藏未成功 局部刷新
    }

    private void setBaseHolder(final BaseBlogViewHolder holder, int position) {
        final Blog blog = mBlog.get(position);
        if (mTypeFrom == TYPE_OTHER || mTypeFrom == TYPE_MY_POST) {
            holder.mBlockView.setVisibility(View.VISIBLE);
            holder.mLlTypeArea.setVisibility(View.GONE);
        }
        holder.mIbDelete.setVisibility(mTypeFrom == TYPE_MY_POST ? View.VISIBLE : View.GONE);
        holder.mIbDelete.setTag(position);
        holder.mIbDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnBlogClickListener != null) {
                    mOnBlogClickListener.onDeleteClick((int) v.getTag());
                }
            }
        });
        setType(holder, position, blog);
        RequestOptions options = new RequestOptions().placeholderOf(R.mipmap.default_head).error(R.mipmap.default_head);
        Glide.with(mContext).load(blog.getAuthorAvatar())
                .apply(options.circleCrop()).into(holder.mIvProfile);
        holder.mIvProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!UserManager.getInstance().isLogin()) {
                    LoginActivity.start(mContext);
                } else {
                    if (mOnBlogClickListener != null) {
                        mOnBlogClickListener.onJumpToProfile(blog.getUid());
                    }
                }
            }
        });
        holder.mTvNickname.setText(blog.getAuthorNickName());
        holder.mTvNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!UserManager.getInstance().isLogin()) {
                    LoginActivity.start(mContext);
                } else {
                    if (mOnBlogClickListener != null) {
                        mOnBlogClickListener.onJumpToProfile(blog.getUid());
                    }
                }
            }
        });
        holder.mTvPostTime.setText(blog.getCreateTime());
        holder.mTvDetail.setText(blog.getContent());
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TDevice.hasInternet()) {
                    ToastUtils.showToastWithBorder(mContext, R.string.no_network, Toast.LENGTH_SHORT, Gravity.BOTTOM);
                }
                if (mOnBlogClickListener != null) {
                    mOnBlogClickListener.onCommentClick((int) v.getTag());
                }
            }
        });
        setComment(holder, position, blog);

        setLike(holder, position, blog);

        setCollect(holder, position, blog);
    }

    private void setCollect(final BaseBlogViewHolder holder, int position, Blog blog) {
        holder.mTvCollect.setText(String.valueOf(blog.getCollectNum()));
        holder.mTvCollect.setCompoundDrawablesRelativeWithIntrinsicBounds(blog.getIsCollect() == 0 ?
                R.mipmap.un_collect : R.mipmap.collected, 0, 0, 0);
        holder.mTvCollect.setTag(position);
        holder.mTvCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TDevice.hasInternet()) {
                    ToastUtils.showToastWithBorder(mContext, R.string.no_network, Toast.LENGTH_SHORT, Gravity.BOTTOM);
                }
                int pos = (int) v.getTag();
                Blog blog1 = mBlog.get(pos);
                int preIsCollect = blog1.getIsCollect();
                if (mOnBlogClickListener != null) {
                    if (!mOnBlogClickListener.onCollectClick(preIsCollect, pos)) {
                        return;
                    }
                }
                blog1.setIsCollect(preIsCollect == 1 ? 0 : 1);
                blog1.setCollectNum(preIsCollect == 0 ? blog1.getCollectNum() + 1 : blog1.getCollectNum() - 1);
                holder.mTvCollect.setText(String.valueOf(blog1.getCollectNum()));
                holder.mTvCollect.setCompoundDrawablesRelativeWithIntrinsicBounds(blog1.getIsCollect() == 0 ?
                        R.mipmap.un_collect : R.mipmap.collected, 0, 0, 0);
            }
        });
    }

    private void setLike(final BaseBlogViewHolder holder, final int position, Blog blog) {
        holder.mTvLike.setText(String.valueOf(blog.getLikeNum()));
        holder.mTvLike.setTextColor(mContext.getResources().getColor(blog.getIsLike() == 0 ?
                R.color.colorText9 : R.color.common_orange));
        holder.mTvLike.setCompoundDrawablesRelativeWithIntrinsicBounds(blog.getIsLike() == 0 ?
                R.mipmap.un_like : R.mipmap.liked, 0, 0, 0);
        holder.mTvLike.setTag(position);
        holder.mTvLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TDevice.hasInternet()) {
                    ToastUtils.showToastWithBorder(mContext, R.string.no_network, Toast.LENGTH_SHORT, Gravity.BOTTOM);
                }
                int pos = (int) v.getTag();
                Blog blog1 = mBlog.get(pos);
                int preIsLike = blog1.getIsLike();
                if (mOnBlogClickListener != null) {
                    if (!mOnBlogClickListener.onLikeClick(preIsLike, pos)) {
                        return;
                    }
                }
                blog1.setIsLike(preIsLike == 1 ? 0 : 1);
                blog1.setLikeNum(preIsLike == 0 ? blog1.getLikeNum() + 1 : blog1.getLikeNum() - 1);
                holder.mTvLike.setText(String.valueOf(blog1.getLikeNum()));
                holder.mTvLike.setCompoundDrawablesRelativeWithIntrinsicBounds(blog1.getIsLike() == 0 ?
                        R.mipmap.un_like : R.mipmap.liked, 0, 0, 0);
            }
        });
    }

    private void setComment(BaseBlogViewHolder holder, int position, Blog blog) {
        holder.mTvComment.setText(blog.getContent());
        holder.mTvComment.setText(String.valueOf(blog.getCommitNum()));
        holder.mTvComment.setTag(position);
        holder.mTvComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TDevice.hasInternet()) {
                    ToastUtils.showToastWithBorder(mContext, R.string.no_network, Toast.LENGTH_SHORT, Gravity.BOTTOM);
                }
                if (mOnBlogClickListener != null) {
                    mOnBlogClickListener.onCommentClick((int) v.getTag());
                }
            }
        });
    }

    private void setType(BaseBlogViewHolder holder, int position, Blog blog) {
        holder.mLlTypeArea.setVisibility(View.GONE);
        int type = blog.type;
        if (position == 0) {
            holder.mLlTypeArea.setVisibility(mTypeFrom == TYPE_OTHER || mTypeFrom == TYPE_MY_POST ? View.GONE : View.VISIBLE);
            holder.mTvType.setText(type == 0 ? R.string.recommend : R.string.announcement);
        } else {
            Blog previous = mBlog.get(position - 1);
            if (previous.type != type) {
                holder.mLlTypeArea.setVisibility(mTypeFrom == TYPE_OTHER || mTypeFrom == TYPE_MY_POST ? View.GONE : View.VISIBLE);
                holder.mTvType.setText(type == 0 ? R.string.recommend : R.string.announcement);
            }

        }
    }

    @Override
    public int getItemCount() {
        return mBlog.size();
    }

    public void addAll(List<Blog> blog) {
        if (blog != null) {
            int size = mBlog.size();
            this.mBlog.addAll(blog);
            notifyItemRangeInserted(size, blog.size());
            notifyItemRangeChanged(size, blog.size());
        }
    }

    public void setData(List<Blog> blog) {
        if (blog != null) {
            int previousSize = mBlog.size();
            mBlog.clear();
            notifyItemRangeRemoved(mFirstBlog == null ? 0 : 1, previousSize);
            if (mFirstBlog != null) {
                mBlog.add(mFirstBlog);
            }
            mBlog.addAll(blog);
            notifyItemRangeInserted(mFirstBlog == null ? 0 : 1,
                    mFirstBlog == null ? mBlog.size() : mBlog.size() + 1);
        }
    }

    /**
     * @param blog 管理员发布的公告
     */
    public void insertFirst(Blog blog) {
        this.mFirstBlog = blog;
        if (blog != null) {
            mBlog.add(0, blog);
            notifyItemInserted(0);
            notifyItemRangeChanged(0, mBlog.size());
        }
    }

    @Nullable
    public Blog getItem(int pos) {
        Blog blog = null;
        try {
            blog = this.mBlog.get(pos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return blog;
    }

    public void remove(Blog blog) {
        int position = this.mBlog.indexOf(blog);
        mBlog.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, this.mBlog.size() - position);
        if (mBlog.size() == 0 && mOnBlogClickListener != null){
            mOnBlogClickListener.showEmptyData();
        }
    }

    static class WordAndImgViewHolder extends BaseBlogViewHolder {

        MessagePicturesLayout mPicturesLayout;

        public WordAndImgViewHolder(View itemView) {
            super(itemView);
            mPicturesLayout = itemView.findViewById(R.id.img_grid_view);
        }
    }

    static class WordViewHolder extends BaseBlogViewHolder {
        public WordViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class BaseBlogViewHolder extends RecyclerView.ViewHolder {
        TextView mTvType;
        LinearLayout mLlTypeArea;
        View mBlockView;
        ImageView mIvProfile;
        TextView mTvNickname;
        TextView mTvPostTime;
        TextView mTvTags;
        TextView mTvDetail;
        TextView mTvLike;
        TextView mTvComment;
        TextView mTvCollect;
        ImageButton mIbDelete;

        public BaseBlogViewHolder(View itemView) {
            super(itemView);
            mTvType = itemView.findViewById(R.id.tv_type);
            mLlTypeArea = itemView.findViewById(R.id.ll_type_area);
            mBlockView = itemView.findViewById(R.id.v_block_blog);
            mIvProfile = itemView.findViewById(R.id.iv_profile);
            mTvNickname = itemView.findViewById(R.id.tv_nickname);
            mTvPostTime = itemView.findViewById(R.id.tv_post_time);
            mTvTags = itemView.findViewById(R.id.tv_tags);
            mIbDelete = itemView.findViewById(R.id.ib_delete);
            mTvDetail = itemView.findViewById(R.id.tv_detail);
            mTvLike = itemView.findViewById(R.id.tv_like);
            mTvComment = itemView.findViewById(R.id.tv_comment);
            mTvCollect = itemView.findViewById(R.id.tv_collect);
        }
    }

    public void setOnBlogClickListener(@NonNull OnBlogClickListener onBlogClickListener) {

        this.mOnBlogClickListener = onBlogClickListener;
    }

    public interface OnBlogClickListener {

        boolean onCollectClick(int preIsCollect, int pos);

        boolean onLikeClick(int preIsLike, int pos);

        void onCommentClick(int pos);

        void onDeleteClick(int pos);

        /**
         * 數據全部移除
         */
        void showEmptyData();

        void onJumpToProfile(int uid);
    }
}
