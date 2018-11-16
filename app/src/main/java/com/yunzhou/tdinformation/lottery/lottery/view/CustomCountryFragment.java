package com.yunzhou.tdinformation.lottery.lottery.view;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yunzhou.common.utils.L;
import com.yunzhou.common.utils.ToastUtil;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.bean.lottery.RecommendLotteryEntity;
import com.yunzhou.tdinformation.lottery.lottery.AbsCustomFragment;
import com.yunzhou.tdinformation.lottery.lottery.adapter.CustomCountryAdapter;
import com.yunzhou.tdinformation.lottery.lottery.presenter.CustomCountryPresenter;

import java.util.List;

import butterknife.BindView;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.lottery
 *  @文件名:   CustomCountryFragment
 *  @创建者:   lz
 *  @创建时间:  2018/10/25 17:47
 *  @描述：
 */

public class CustomCountryFragment extends AbsCustomFragment implements CustomCountryView, CustomCountryAdapter.Callback {

    private static final String TAG = "CustomCountryFragment";
    @BindView(R.id.rv_has_custom)
    RecyclerView mRvHasCustom;
    @BindView(R.id.rv_custom)
    RecyclerView mRvCustom;


    private CustomCountryPresenter mPresenter;
    private CustomCountryAdapter mHasCustomAdapter;
    private CustomCountryAdapter mNoCustomAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_custom_country;
    }

    public static CustomCountryFragment newInstance() {

        Bundle args = new Bundle();

        CustomCountryFragment fragment = new CustomCountryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initWidget(View root) {
        createP();
        initRecycleView();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    private void createP() {
        mPresenter = new CustomCountryPresenter();
    }

    private void initRecycleView() {
        //已定制
        mHasCustomAdapter = new CustomCountryAdapter(true, mContext, this);
        mRvHasCustom.setLayoutManager(new GridLayoutManager(mContext, 3));
        mRvHasCustom.setAdapter(mHasCustomAdapter);
        //未定制
        mNoCustomAdapter = new CustomCountryAdapter(false, mContext, this);
        mRvCustom.setLayoutManager(new GridLayoutManager(mContext, 3));
        mRvCustom.setAdapter(mNoCustomAdapter);
    }

    @Override
    protected void fetchData() {
        mPresenter.loadCountyData();
    }

    @Override
    public void onItemClick(boolean top, RecommendLotteryEntity item) {
        if (top) {
            L.d(TAG ,item.getLotteryName());
            mPresenter.unSubscribe(mContext ,item);
        } else {
            L.d(TAG ,item.getLotteryName());
            mPresenter.subscribe(mContext ,item);

        }
    }

    @Override
    public void showSubscribeList(List<RecommendLotteryEntity> subscribeList) {
        addAllSubscribe(subscribeList);
        mHasCustomAdapter.setData(subscribeList);
    }

    @Override
    public void showNotSubscribeList(List<RecommendLotteryEntity> notSubscribeList) {
        mNoCustomAdapter.setData(notSubscribeList);
    }
    @Override
    public void showErrorView(int visibility) {

    }

    @Override
    public void subscribeLotterySuccess(RecommendLotteryEntity item) {
        // 定制
        mHasCustomAdapter.addItem(item);
        addSubscribe(item);
        mNoCustomAdapter.removeItem(item);
    }

    @Override
    public void subscribeLotteryError(String message) {
        ToastUtil.showShort(mContext ,"定制失败");
    }

    @Override
    public void unSubscribeLotterySuccess(RecommendLotteryEntity item) {
        // 取消定制
        mHasCustomAdapter.removeItem(item);
        removeSubscribe(item);
        mNoCustomAdapter.addItem(item);
    }

    @Override
    public void unSubscribeLotteryError(String message) {
        ToastUtil.showShort(mContext ,"取消定制失败");
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
