package com.yunzhou.tdinformation.lottery.lottery.view;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.yunzhou.common.utils.L;
import com.yunzhou.common.utils.ToastUtil;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.bean.lottery.RecommendLotteryEntity;
import com.yunzhou.tdinformation.lottery.lottery.AbsCustomFragment;
import com.yunzhou.tdinformation.lottery.lottery.adapter.CustomCountryAdapter;
import com.yunzhou.tdinformation.lottery.lottery.adapter.LotteryCategoryAdapter;
import com.yunzhou.tdinformation.lottery.lottery.viewholder.ProvinceLotteryItem;
import com.yunzhou.tdinformation.lottery.lottery.presenter.CustomLocalAndHighPresenter;

import java.util.ArrayList;
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

public class CustomHighFragment extends AbsCustomFragment implements CustomLocalAndHighView, CustomCountryAdapter.Callback, LotteryCategoryAdapter.Callback {
    private static final String TAG = "CustomLocalFragment";
    @BindView(R.id.rv_has_custom)
    RecyclerView mRvHasCustom;
    @BindView(R.id.rv_custom)
    RecyclerView mRvCustom;
    private CustomLocalAndHighPresenter mPresenter;
    private CustomCountryAdapter mHasCustomAdapter;
    private LotteryCategoryAdapter mAdapter;
    private ProvinceLotteryItem mLotteryItem;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_custom_country;
    }

    public static CustomHighFragment newInstance() {
        return new CustomHighFragment();
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
        mPresenter = new CustomLocalAndHighPresenter(1);
    }

    private void initRecycleView() {
        mHasCustomAdapter = new CustomCountryAdapter(true, mContext, this);
        mRvHasCustom.setLayoutManager(new GridLayoutManager(mContext, 3));
        mRvHasCustom.setAdapter(mHasCustomAdapter);
    }

    @Override
    protected void fetchData() {
        mPresenter.loadData();
    }

    @Override
    public void onItemClick(boolean top, RecommendLotteryEntity item) {
        mPresenter.unSubscribe(mContext , item);
    }

    @Override
    public void onItemClick(int adapterPosition, MultiItemEntity item, RecommendLotteryEntity entity) {
        if (mRecordSubscribeList.size() > 10) {
            ToastUtil.showShort(mContext ,"最多定制10种哦");
        } else {
            mPresenter.subscribe(mContext ,entity);
        }

    }

    @Override
    public void showSubscribeList(List<RecommendLotteryEntity> subscribeList) {
        addAllSubscribe(subscribeList);
        mHasCustomAdapter.setData(subscribeList);
    }

    @Override
    public void showNotSubscribeList(ArrayList<MultiItemEntity> realEntity) {
        if (mAdapter == null) {
            mAdapter = new LotteryCategoryAdapter(realEntity, this);
            final GridLayoutManager layoutManager = new GridLayoutManager(mContext, 3);
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int itemViewType = mAdapter.getItemViewType(position);
                    int spanCount = layoutManager.getSpanCount();
                    L.d(TAG, "getSpanSize: itemViewType == " + itemViewType + ";spanCount == " + spanCount);
                    return itemViewType == LotteryCategoryAdapter.TYPE_DETAIL ? 3 : 1;
                }
            });
            mRvCustom.setAdapter(mAdapter);
            mRvCustom.setLayoutManager(layoutManager);
        } else {
            mAdapter.setNewData(realEntity);
        }
    }

    @Override
    public void subscribeLotteryError(String message) {
        ToastUtil.showShort(mContext ,"定制失败");
    }

    @Override
    public void subscribeLotterySuccess(RecommendLotteryEntity item) {
        mHasCustomAdapter.addItem(item);
        addSubscribe(item);
    }

    @Override
    public void unSubscribeLotteryError(String message) {
        ToastUtil.showShort(mContext ,"取消定制失败");
    }

    @Override
    public void unSubscribeLotterySuccess(RecommendLotteryEntity item) {
        removeSubscribe(item);
        mHasCustomAdapter.removeItem(item);

        int provinceId = item.getProvinceId();
        if (mLotteryItem == null) {
            mLotteryItem = new ProvinceLotteryItem();
        }
        mLotteryItem.id = provinceId;
        int pos = mAdapter.getData().indexOf(mLotteryItem);
        L.d("wtf", "pos === " + pos);
        if (pos != -1){
            mAdapter.addSubItem(item, pos);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
