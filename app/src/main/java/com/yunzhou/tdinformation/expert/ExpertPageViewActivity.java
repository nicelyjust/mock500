package com.yunzhou.tdinformation.expert;

/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.expert
 *  @文件名:   ExpertPageViewActivity
 *  @创建者:   lz
 *  @创建时间:  2018/10/15 17:03
 *  @描述：
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.flyco.tablayout.SlidingTabLayout;
import com.yunzhou.common.utils.ToastUtil;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.activity.BaseCommonAct;
import com.yunzhou.tdinformation.bean.expert.ExpertIntroEntity;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.expert.view.ArticleFragment;
import com.yunzhou.tdinformation.expert.view.ExpertBlogFragment;
import com.yunzhou.tdinformation.expert.view.ExpertPageView;
import com.yunzhou.tdinformation.expert.view.RecordFragment;
import com.yunzhou.tdinformation.home.adapter.HomeAdapter;
import com.yunzhou.tdinformation.login.LoginActivity;
import com.yunzhou.tdinformation.user.UserManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ExpertPageViewActivity extends BaseCommonAct implements ExpertPageView, View.OnClickListener {
    @BindView(R.id.tb_tool_bar_expert_page)
    Toolbar mToolBar;
    @BindView(R.id.iv_head_spec)
    ImageView mIvHead;
    @BindView(R.id.tv_name_spec)
    TextView mTvNameSpec;
    @BindView(R.id.tv_spec_like_num)
    TextView mTvLikeNum;
    @BindView(R.id.tv_fan_num_spec)
    TextView mTvFanNum;
    @BindView(R.id.tv_oder_count_spec)
    TextView mTvOderCount;
    @BindView(R.id.tv_follow_spec)
    TextView mTvFollowSpec;
    @BindView(R.id.fl_follow_spec)
    FrameLayout mFlFollowSpec;
    @BindView(R.id.ll_good_at_container_spec)
    LinearLayout mLlGoodAtContainer;
    @BindView(R.id.tv_intro_detail_spec)
    TextView mTvIntro;
    @BindView(R.id.tab_expert_page)
    SlidingTabLayout mTab;
    @BindView(R.id.vp_expert_page)
    ViewPager mVp;
    private String[] tabs;
    private ExpertPageController mController;
    private ExpertIntroEntity mIntroEntity;

    @Override
    protected int getContentView() {
        return R.layout.activity_expert_page;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setSupportActionBar(mToolBar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
        tabs = getResources().getStringArray(R.array.expert);
        mController = new ExpertPageController(this);
    }

    @Override
    protected void initData() {
        mController.loadData(UserManager.getInstance().getUid(), getIntent().getIntExtra(AppConst.Extra.EXPERT_ID, -1));
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.menu_share) {
            ToastUtil.showShort(this, R.string.share);
        }
        return super.onOptionsItemSelected(item);
    }

    private void initVpAndFragments(List<Fragment> fragments, ExpertIntroEntity entity) {
        ArticleFragment articleFragment = ArticleFragment.newInstance(entity.getId(), entity.getAvaSmall());
        RecordFragment recordFragment = RecordFragment.newInstance();
        ExpertBlogFragment expertBlogFragment = ExpertBlogFragment.newInstance();
        fragments.add(articleFragment);
        fragments.add(recordFragment);
        fragments.add(expertBlogFragment);

        HomeAdapter homeAdapter = new HomeAdapter<>(getSupportFragmentManager(), tabs, fragments);
        mVp.setAdapter(homeAdapter);
        mVp.setOffscreenPageLimit(2);
    }

    @Override
    public void showExpert(ExpertIntroEntity introEntity) {
        this.mIntroEntity = introEntity;
        List<Fragment> fragments = new ArrayList<>(tabs.length);
        initVpAndFragments(fragments, introEntity);
        mTab.setViewPager(mVp);
        initExpertInfo(introEntity);
    }

    @Override
    public void showFollowSuccess(boolean hasFollow, String tip) {
        mIntroEntity.setWatchExp(hasFollow ? 2 : 1);
        setFollowView(hasFollow ? 2 : 1);
    }

    @Override
    public void showFollowError(boolean hasFollow, String message) {
        setFollowView(hasFollow ? 1 : 2);
        ToastUtil.showShort(this, message);
    }

    private void initExpertInfo(ExpertIntroEntity entity) {
        mTvNameSpec.setText(TextUtils.isEmpty(entity.getNickName()) ? "无名学霸" : entity.getNickName());
        RequestOptions options = new RequestOptions().placeholder(R.mipmap.default_head);
        Glide.with(this).load(entity.getAvaSmall()).apply(options.circleCrop()).into(mIvHead);
        setFollowView(entity.getWatchExp());
        mFlFollowSpec.setOnClickListener(this);

        mTvLikeNum.setText(String.valueOf(entity.getBravo()));

        mTvFanNum.setText(String.valueOf(entity.getAttentionCount()));
        mTvOderCount.setText(String.valueOf(entity.getContentsNum()));

        String goodAt = entity.getGoodAt();
        if (!TextUtils.isEmpty(goodAt)) {
            String[] split = goodAt.split(",");
            for (String s : split) {
                TextView view = new TextView(this);
                view.setText(s);
                view.setTextColor(Color.parseColor("#FFFEFEFE"));
                view.setTextSize(12f);
                view.setPadding(4, 2, 4, 2);
                view.setGravity(Gravity.CENTER);
                view.setBackgroundResource(R.drawable.shape_gray_stroke_mid_radius_bg);
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                param.rightMargin = 10;
                mLlGoodAtContainer.addView(view, param);
            }
        }
        mTvIntro.setText(entity.getIntro());
    }

    private void setFollowView(int watchExp) {
        mTvFollowSpec.setText(watchExp == 2 ? R.string.follow_plus : R.string.has_follow);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_follow_spec:
                if (!UserManager.getInstance().isLogin()) {
                    LoginActivity.start(this);
                    return;
                }
                if (mIntroEntity != null) {
                    mController.requestFollowExpert(this, mIntroEntity.getWatchExp() == 1, mIntroEntity.getId());
                }
                break;
            default:

                break;
        }
    }
    @Override
    public void hideLoading() {

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mController.detachView();
    }

    public static void start(Context context, int expertId) {
        Intent starter = new Intent(context, ExpertPageViewActivity.class);
        starter.putExtra(AppConst.Extra.EXPERT_ID, expertId);
        context.startActivity(starter);
    }
}
