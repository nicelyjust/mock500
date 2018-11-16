package com.yunzhou.tdinformation.blog.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yunzhou.common.http.dialog.ToastUtils;
import com.yunzhou.common.utils.FastClickUtil;
import com.yunzhou.common.utils.TDevice;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.bean.blog.CommentChildBean;
import com.yunzhou.tdinformation.bean.blog.PostCommentEntity;
import com.yunzhou.tdinformation.bean.community.Blog;
import com.yunzhou.tdinformation.view.blog.MessagePicturesLayout;
import com.yunzhou.tdinformation.view.blog.VerticalCommentWidget;

import java.util.ArrayList;
import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.blog.adapter
 *  @文件名:   BlogDetailAdapter
 *  @创建者:   lz
 *  @创建时间:  2018/10/12 10:30
 *  @描述：
 */

public class BlogDetailAdapter extends RecyclerView.Adapter {
    private static final int ITEM_HEADER = 0x01;
    private static final int ITEM_COMMENT = 0x02;
    private static final int ITEM_EMPTY = 0x03;


    private List<PostCommentEntity> mCommentEntities;
    private Blog mBlog;
    private Context mContext;
    private Callback mCallback;

    public BlogDetailAdapter(Context context, Callback callback) {
        this.mContext = context;
        this.mCallback = callback;
        this.mCommentEntities = new ArrayList<>();
    }

    public void setData(List<PostCommentEntity> commentEntities, Blog blog) {
        if (blog != null) {
            mBlog = blog;
            if (commentEntities != null && !commentEntities.isEmpty()) {
                int previousSize = mCommentEntities.size();
                mCommentEntities.clear();
                notifyItemRangeRemoved(0, previousSize + 1);
                mCommentEntities.addAll(commentEntities);
                notifyItemRangeInserted(0, mCommentEntities.size() + 1);
            } else {
                notifyDataSetChanged();
            }
        }

    }

    public void addAll(List<PostCommentEntity> commentList) {
        if (commentList != null) {
            int size = mCommentEntities.size() + 1;
            this.mCommentEntities.addAll(commentList);
            notifyItemRangeInserted(size, commentList.size());
            notifyItemRangeChanged(size, commentList.size());
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case ITEM_HEADER:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_header_blog_detail, parent, false);
                return new BlogDetailHeaderVH(view);
            case ITEM_EMPTY:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_blog_comment_empty, parent, false);
                return new CommentEmptyVH(view);
            default:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_comment_blog_detail, parent, false);
                return new CommentVH(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List payloads) {
        if (holder instanceof BlogDetailHeaderVH) {
            BlogDetailHeaderVH headerVH = (BlogDetailHeaderVH) holder;
            bindHeaderViewHolder(headerVH, mBlog ,payloads);
        } else if (holder instanceof CommentEmptyVH) {
            CommentEmptyVH emptyVH = (CommentEmptyVH) holder;
        } else {
            CommentVH commentVH = (CommentVH) holder;
            bindCommentViewHolder(position - 1, commentVH,payloads);
        }
    }

    private void bindCommentViewHolder(final int position, CommentVH holder, List payloads) {
        final PostCommentEntity item = mCommentEntities.get(position);
        if (payloads.size() > 0){
            int o = (int) payloads.get(0);
            if (o == 2) {
                setLike(holder, position, item);
            } else if (o == 3){
                setCommentView(position, holder, item);
            }
            return;
        }
        RequestOptions requestOptions = new RequestOptions().placeholder(R.mipmap.default_head).error(R.mipmap.default_head);
        Glide.with(mContext).load(item.getAvatar()).apply(requestOptions.circleCrop()).into(holder.mIvProfile);
        holder.mIvProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onHeadClick(item.getUid());
            }
        });
        holder.mTvNickname.setText(TextUtils.isEmpty(item.getNickName()) ? "无名学霸":item.getNickName());
        holder.mTvNickname.setTag(item.getUid());
        holder.mTvNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onHeadClick((int) v.getTag());
            }
        });
        holder.mTvCommentPos.setText(item.getFloor() + "楼");
        holder.mTvPostTime.setText(item.getCreateTime());
        holder.mTvDetail.setText(item.getContent());
        setLike(holder, position, item);
        setCommentView(position, holder, item);
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallback != null) {
                    mCallback.onItemClick((int) v.getTag());
                }
            }
        });
    }

    private void setCommentView(final int position, CommentVH holder, final PostCommentEntity item) {
        final List<CommentChildBean> commentBeans = item.getCommentBeans();
        holder.mVcwCommentArea.addComments(commentBeans);
        holder.mVcwCommentArea.setCallback(new VerticalCommentWidget.Callback() {
            @Override
            public void onCommentClick(int index) {
                if (mCallback != null) {
                    CommentChildBean commentChildBean = commentBeans.get(index);
                    commentChildBean.rootUid = item.getCommentsId();
                    mCallback.onCommentClick(position , commentChildBean);
                }
            }
        });
    }

    private void bindHeaderViewHolder(final BlogDetailHeaderVH holder, final Blog blog, List payloads) {
        if (payloads.size()>0){
            setFollowTxt(holder, blog);
        } else {
            holder.mImgGridView.setVisibility(blog.urlThumbList.isEmpty() ? View.GONE : View.VISIBLE);
            holder.mImgGridView.set(blog.urlThumbList, blog.urlList);
            setFollowTxt(holder, blog);
            holder.mTvNickname.setText(blog.getAuthorNickName());
            holder.mTvNickname.setTag(blog.getUid());
            holder.mTvNickname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onHeadClick((int) v.getTag());
                }
            });
            holder.mTvPostTime.setText(blog.getCreateTime());
            holder.mTvDetail.setText(blog.getContent());

            RequestOptions requestOptions = new RequestOptions().placeholder(R.mipmap.default_head).error(R.mipmap.default_head);
            Glide.with(mContext).load(blog.getAuthorAvatar()).apply(requestOptions.circleCrop()).into(holder.mIvProfile);
            holder.mIvProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onHeadClick(blog.getUid());
                }
            });
        }

    }

    private void setFollowTxt(final BlogDetailHeaderVH holder, final Blog blog) {
        holder.mTvFollow.setBackgroundResource(blog.getIsCheck() == 1 ? R.drawable.shape_gray_has_follow_bg : R.drawable.shape_red_stroke_bg);
        holder.mTvFollow.setText(blog.getIsCheck() == 1 ? R.string.has_follow : R.string.follow_plus);
        holder.mTvFollow.setTextColor(mContext.getResources().getColor(blog.getIsCheck() == 1 ? R.color.colorText9 : R.color.common_red));
        holder.mTvFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TDevice.hasInternet()) {
                    ToastUtils.showToastWithBorder(mContext, R.string.no_network, Toast.LENGTH_SHORT, Gravity.BOTTOM);
                    return;
                }
                mCallback.onFollowClick(blog);

            }
        });
    }

    private void setLike(final CommentVH holder, final int position, PostCommentEntity blog) {
        holder.mTvLikeComment.setText(String.valueOf(blog.getLikeNum()));
        holder.mTvLikeComment.setTextColor(mContext.getResources().getColor(blog.getIsLiked() == 0 ?
                R.color.colorText9 : R.color.common_orange));
        holder.mTvLikeComment.setCompoundDrawablesRelativeWithIntrinsicBounds(blog.getIsLiked() == 0 ?
                R.mipmap.un_like : R.mipmap.liked, 0, 0, 0);
        holder.mTvLikeComment.setTag(position);
        holder.mTvLikeComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TDevice.hasInternet()) {
                    ToastUtils.showToastWithBorder(mContext, R.string.no_network, Toast.LENGTH_SHORT, Gravity.BOTTOM);
                }
                if (FastClickUtil.isFastClick()) {
                    return;
                }
                int pos = (int) v.getTag();
                PostCommentEntity commentEntity = mCommentEntities.get(pos);
                int preIsLike = commentEntity.getIsLiked();
                if (mCallback != null) {
                    mCallback.onCommentLikeClick(pos ,preIsLike, commentEntity.getCommentsId());
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        if (mBlog == null) {
            return 0;
        } else if (mCommentEntities.isEmpty()) {
            // blog 和 空视图
            return 2;
        }
        return 1 + mCommentEntities.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_HEADER;
        } else if (mCommentEntities.isEmpty()) {
            return ITEM_EMPTY;
        } else {
            return ITEM_COMMENT;
        }
    }
    @Nullable
    public PostCommentEntity getCommentItem(int position) {
        try {
            return mCommentEntities.get(position);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public final void addItemFirst(PostCommentEntity item) {
        if (item != null) {

            if (this.mCommentEntities.size() == 0) {
                item.setFloor(1);
                this.mCommentEntities.add(item);
                /*notifyItemInserted(1);
                notifyItemRangeChanged(1,mCommentEntities.size());*/
                notifyDataSetChanged();
            } else {
                PostCommentEntity entity = mCommentEntities.get(0);
                item.setFloor(entity.getFloor());
                this.mCommentEntities.add(0, item);
                notifyItemInserted(1);
                notifyItemRangeChanged(1,mCommentEntities.size());
            }
        }

    }

    static class BlogDetailHeaderVH extends RecyclerView.ViewHolder {
        ImageView mIvProfile;
        TextView mTvNickname;
        TextView mTvPostTime;
        TextView mTvFollow;
        TextView mTvDetail;
        MessagePicturesLayout mImgGridView;

        public BlogDetailHeaderVH(View itemView) {
            super(itemView);
            mIvProfile = itemView.findViewById(R.id.iv_profile);
            mTvNickname = itemView.findViewById(R.id.tv_nickname);
            mTvPostTime = itemView.findViewById(R.id.tv_post_time);
            mTvFollow = itemView.findViewById(R.id.tv_follow);
            mTvDetail = itemView.findViewById(R.id.tv_detail);
            mImgGridView = itemView.findViewById(R.id.img_grid_view);
        }
    }

    static class CommentVH extends RecyclerView.ViewHolder {
        ImageView mIvProfile;
        TextView mTvNickname;
        TextView mTvCommentPos;
        TextView mTvPostTime;
        TextView mTvLikeComment;
        TextView mTvDetail;
        VerticalCommentWidget mVcwCommentArea;

        public CommentVH(View itemView) {
            super(itemView);
            mIvProfile = itemView.findViewById(R.id.iv_profile_comment);
            mTvNickname = itemView.findViewById(R.id.tv_nickname);
            mTvCommentPos = itemView.findViewById(R.id.tv_comment_pos);
            mTvPostTime = itemView.findViewById(R.id.tv_post_time);
            mTvLikeComment = itemView.findViewById(R.id.tv_like_comment);
            mTvDetail = itemView.findViewById(R.id.tv_detail);
            mVcwCommentArea = itemView.findViewById(R.id.vcw_comment_area);
        }
    }

    static class CommentEmptyVH extends RecyclerView.ViewHolder {

        public CommentEmptyVH(View itemView) {
            super(itemView);
        }
    }

    public interface Callback {
        void onFollowClick(Blog blog);
        void onCommentLikeClick(int position, int preIsLike, int commentsId);

        void onCommentClick(int position, CommentChildBean commentChildBean);

        void onItemClick(int position);

        void onHeadClick(int uid);
    }
}
