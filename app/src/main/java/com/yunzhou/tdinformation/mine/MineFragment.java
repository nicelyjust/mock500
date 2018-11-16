package com.yunzhou.tdinformation.mine;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yunzhou.common.http.bean.DataBean;
import com.yunzhou.common.http.bean.JsonResult;
import com.yunzhou.common.utils.FastClickUtil;
import com.yunzhou.common.utils.L;
import com.yunzhou.common.utils.TDevice;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.callback.LoginCallback;
import com.yunzhou.tdinformation.base.fragment.LazyBaseFragment;
import com.yunzhou.tdinformation.bean.PostCountBean;
import com.yunzhou.tdinformation.bean.user.UserEntity;
import com.yunzhou.tdinformation.blog.MyPostBlogActivity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.constant.NetConstant;
import com.yunzhou.tdinformation.login.LoginActivity;
import com.yunzhou.tdinformation.mine.campaign.CampaignActivity;
import com.yunzhou.tdinformation.mine.collect.CollectActivity;
import com.yunzhou.tdinformation.mine.fans.MineFanActivity;
import com.yunzhou.tdinformation.mine.feedback.FeedbackActivity;
import com.yunzhou.tdinformation.mine.follow.MineFollowActivity;
import com.yunzhou.tdinformation.mine.help.HelpActivity;
import com.yunzhou.tdinformation.mine.payarticle.PayArticleActivity;
import com.yunzhou.tdinformation.mine.red.MyRedPacketActivity;
import com.yunzhou.tdinformation.mine.widget.FuncItem;
import com.yunzhou.tdinformation.setting.SettingActivity;
import com.yunzhou.tdinformation.user.UserActivity;
import com.yunzhou.tdinformation.user.UserManager;
import com.yunzhou.tdinformation.view.badgeview.BadgeFactory;
import com.yunzhou.tdinformation.view.badgeview.BadgeView;

import java.lang.ref.WeakReference;

import butterknife.BindView;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.main
 *  @文件名:   MineFragment
 *  @创建者:   lz
 *  @创建时间:  2018/9/20 15:58
 *  @描述：
 */

public class MineFragment extends LazyBaseFragment implements View.OnClickListener {
    @BindView(R.id.ib_setting)
    ImageView mIbSetting;
    @BindView(R.id.ib_ring)
    ImageView mIbRing;
    @BindView(R.id.iv_mine_head)
    ImageView mIvMineHead;
    @BindView(R.id.iv_arrow_mine)
    ImageView mIvArrow;
    @BindView(R.id.tv_nickname)
    TextView mTvNickname;
    @BindView(R.id.iv_grade)
    ImageView mIvGrade;
    @BindView(R.id.tv_coin_count)
    TextView mTvCoinCount;
    @BindView(R.id.tv_grade)
    TextView mTvGrade;
    @BindView(R.id.tv_blog_count)
    TextView mTvBlogCount;
    @BindView(R.id.tv_focus_count)
    TextView mTvFocusCount;
    @BindView(R.id.tv_follow_count)
    TextView mTvFollowCount;
    @BindView(R.id.ll_func_container)
    LinearLayout mLlFuncContainer;
    @BindView(R.id.srl_mine)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.ll_blog)
    LinearLayout mLlBlog;
    @BindView(R.id.ll_follow)
    LinearLayout mLlFollow;
    @BindView(R.id.ll_fans)
    LinearLayout mLlFans;
    private BadgeView badgeView;
    private IntentFilter mFilter;
    private RefreshBroadcastReceiver mReceiver;

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        badgeView = BadgeFactory.createCircle(mContext);
        mIbRing.setOnClickListener(this);
        mIbSetting.setOnClickListener(this);
        initFunc();
        initUserInfo(UserManager.getInstance().getUid() != UserManager.UN_LOGIN);
        initRefreshLayout();
        mIvArrow.setOnClickListener(this);
        mIvMineHead.setOnClickListener(this);
        mTvNickname.setOnClickListener(this);
        mTvGrade.setOnClickListener(this);
        mIvGrade.setOnClickListener(this);
        mLlBlog.setOnClickListener(this);
        mLlFollow.setOnClickListener(this);
        mLlFans.setOnClickListener(this);
        if (mReceiver == null) {
            mFilter = new IntentFilter(AppConst.Action.REFRESH_ACCOUNT);
            mReceiver = new RefreshBroadcastReceiver(this);
        }
        LocalBroadcastManager.getInstance(mContext).registerReceiver(mReceiver, mFilter);
    }

    private void initUserInfo(boolean isLogin) {
        if (isLogin) {
            UserEntity userEntity = UserManager.getInstance().getUserEntity();
            Glide.with(mContext).load(userEntity.getAvatar()).apply(new RequestOptions().placeholder(R.mipmap.default_head).error(R.mipmap.default_head).circleCrop()).into(mIvMineHead);
            mTvNickname.setText(userEntity.getNickName());
            if (userEntity.getGid() > 4)
                mTvBlogCount.setText(String.valueOf( userEntity.getContentsNum()));
            mTvGrade.setText(userEntity.getGrade());
            mTvFocusCount.setText(String.valueOf(userEntity.getAttentionCount()));
            mTvFollowCount.setText(String.valueOf(userEntity.getFansCount()));
        } else {
            mIvMineHead.setBackgroundResource(R.mipmap.default_head);
            mTvNickname.setText(R.string.please_login);
            mTvGrade.setText("--");
            mTvBlogCount.setText(String.valueOf(0));
            mTvFocusCount.setText(String.valueOf(0));
            mTvFollowCount.setText(String.valueOf(0));
        }
    }

    private void initRefreshLayout() {
        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setEnableOverScrollDrag(true);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                if (UserManager.getInstance().isLogin())
                    loadPostCount();
                loadUserInfo(mContext);
            }
        });
    }

    private void initFunc() {
        FuncItem funcItem1 = new FuncItem(mContext);
        funcItem1.setImageResource(R.mipmap.mine_collect);
        funcItem1.setText("我的收藏");
        funcItem1.setLineVisibility(View.VISIBLE);
        funcItem1.setBlockVisibility(View.GONE);
        funcItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserManager.getInstance().isLogin()) {
                    CollectActivity.start(mContext);
                } else {
                    LoginActivity.start(mContext);
                }
            }
        });
        mLlFuncContainer.addView(funcItem1);

        FuncItem funcItem2 = new FuncItem(mContext);
        funcItem2.setImageResource(R.mipmap.mine_pay_article);
        funcItem2.setText("付费文章");
        funcItem2.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.mipmap.mine_need_pay);
        funcItem2.setCompoundDrawablePadding(TDevice.dip2px(7));
        funcItem2.setLineVisibility(View.VISIBLE);
        funcItem2.setBlockVisibility(View.GONE);
        funcItem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserManager.getInstance().isLogin()) {
                    PayArticleActivity.start(mContext);
                } else {
                    LoginActivity.start(mContext);
                }
            }
        });
        mLlFuncContainer.addView(funcItem2);

        FuncItem funcItem3 = new FuncItem(mContext);
        funcItem3.setImageResource(R.mipmap.mine_coin);
        funcItem3.setText("我的彩币");
        funcItem3.setLineVisibility(View.GONE);
        funcItem3.setBlockVisibility(View.VISIBLE);
        funcItem3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mLlFuncContainer.addView(funcItem3);

        FuncItem funcItem4 = new FuncItem(mContext);
        funcItem4.setImageResource(R.mipmap.mine_red_packet);
        funcItem4.setText("我的红包");
        funcItem4.setLineVisibility(View.VISIBLE);
        funcItem4.setBlockVisibility(View.GONE);
        funcItem4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserManager.getInstance().isLogin()) {
                    MyRedPacketActivity.start(mContext);
                } else {
                    LoginActivity.start(mContext);
                }
            }
        });
        mLlFuncContainer.addView(funcItem4);

        FuncItem funcItem5 = new FuncItem(mContext);
        funcItem5.setImageResource(R.mipmap.mine_activity);
        funcItem5.setText("活动中心");
        funcItem5.setLineVisibility(View.GONE);
        funcItem5.setBlockVisibility(View.VISIBLE);
        funcItem5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserManager.getInstance().isLogin()) {
                    CampaignActivity.start(mContext);
                } else {
                    LoginActivity.start(mContext);
                }
            }
        });
        mLlFuncContainer.addView(funcItem5);

        FuncItem funcItem6 = new FuncItem(mContext);
        funcItem6.setImageResource(R.mipmap.mine_feedback);
        funcItem6.setText("意见反馈");
        funcItem6.setLineVisibility(View.VISIBLE);
        funcItem6.setBlockVisibility(View.GONE);
        funcItem6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserManager.getInstance().isLogin()) {
                    FeedbackActivity.start(mContext);
                } else {
                    LoginActivity.start(mContext);
                }
            }
        });
        mLlFuncContainer.addView(funcItem6);

        FuncItem funcItem7 = new FuncItem(mContext);
        funcItem7.setImageResource(R.mipmap.mine_help);
        funcItem7.setText("帮助中心");
        funcItem7.setLineVisibility(View.GONE);
        funcItem7.setBlockVisibility(View.GONE);
        funcItem7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserManager.getInstance().isLogin()) {
                    HelpActivity.start(mContext);
                } else {
                    LoginActivity.start(mContext);
                }
            }
        });
        mLlFuncContainer.addView(funcItem7);
    }

    @Override
    protected void fetchData() {
        if (UserManager.getInstance().isLogin())
            loadPostCount();
        badgeView.setBadgeCount(12).setSpace(2, 2).bind(mIbRing);
    }

    private void loadPostCount() {
        OkGo.<JsonResult<PostCountBean>>get(NetConstant.GET_MY_POST_COUNT + UserManager.getInstance().getUid())
                .headers(AppConst.Param.JWT, UserManager.getInstance().getJwt())
                .execute(new LoginCallback<JsonResult<PostCountBean>>(mContext) {
                    @Override
                    public void onSuccess(Response<JsonResult<PostCountBean>> response) {
                        UserEntity userEntity = UserManager.getInstance().getUserEntity();
                        if (getContext() != null && userEntity != null && userEntity.getGid() <= 4) {
                            mTvBlogCount.setText(String.valueOf(response.body().content.getData()));
                        }
                    }

                    @Override
                    public void onError(Response<JsonResult<PostCountBean>> response) {
                        super.onError(response);
                        L.e("lz", response.getException().getMessage());
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_setting:
                if (!FastClickUtil.isFastClick())
                    SettingActivity.start(mContext);
                break;
            case R.id.ib_ring:

                break;
            case R.id.iv_arrow_mine:
            case R.id.iv_mine_head:
            case R.id.tv_nickname:
            case R.id.iv_grade:
            case R.id.tv_grade:
                if (UserManager.getInstance().isLogin()) {
                    UserActivity.start(mContext);
                } else {
                    LoginActivity.startForResult(mContext);
                }

                break;
            case R.id.ll_blog:
                if (UserManager.getInstance().isLogin()) {
                    MyPostBlogActivity.start(mContext);
                } else {
                    LoginActivity.startForResult(mContext);
                }

                break;
            case R.id.ll_follow:
                if (UserManager.getInstance().isLogin()) {
                    MineFollowActivity.start(mContext,UserManager.getInstance().getUid());
                } else {
                    LoginActivity.startForResult(mContext);
                }

                break;
           case R.id.ll_fans:
               if (UserManager.getInstance().isLogin()) {
                   MineFanActivity. start(mContext,UserManager.getInstance().getUid());
               } else {
                   LoginActivity.startForResult(mContext);
               }
                break;
            default:

                break;
        }
    }
    public void loadUserInfo(Context context) {
        OkGo.<JsonResult<DataBean<UserEntity>>>get(NetConstant.GET_USER_INFO + "/" + UserManager.getInstance().getUid())
                .headers(AppConst.Param.JWT, UserManager.getInstance().getJwt())
                .execute(new LoginCallback<JsonResult<DataBean<UserEntity>>>(context) {
                    @Override
                    public void onSuccess(Response<JsonResult<DataBean<UserEntity>>> response) {
                        if (mContext != null) {
                            UserEntity entity = response.body().content.dataMap;
                            mRefreshLayout.finishRefresh();
                            if (entity != null) {
                                UserManager.getInstance().save(entity);
                                initUserInfo(true);
                            }
                        }
                    }

                    @Override
                    public void onError(Response<JsonResult<DataBean<UserEntity>>> response) {
                        super.onError(response);
                        if (mContext !=null) {
                            mRefreshLayout.finishRefresh(false);
                            String message = response.getException().getMessage();
                            L.e("刷新账户失败,onError: " + message);
                        }
                    }
                });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConst.Request.LOGIN && resultCode == Activity.RESULT_OK) {
            initUserInfo(true);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mReceiver != null) {
            LocalBroadcastManager.getInstance(mContext).unregisterReceiver(mReceiver);
            mReceiver = null;
        }
    }

    static class RefreshBroadcastReceiver extends BroadcastReceiver {

        private final WeakReference<MineFragment> mWeakReference;

        public RefreshBroadcastReceiver(MineFragment fragment) {
            mWeakReference = new WeakReference<>(fragment);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            MineFragment customMadeFragment = mWeakReference.get();
            if (customMadeFragment != null) {
                customMadeFragment.initUserInfo(UserManager.getInstance().getUid() != UserManager.UN_LOGIN);
            }
        }
    }
}
