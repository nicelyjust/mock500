package com.yunzhou.tdinformation.lottery.lottery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.flyco.tablayout.SlidingTabLayout;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.home.adapter.HomeAdapter;
import com.yunzhou.tdinformation.lottery.lottery.view.CustomCountryFragment;
import com.yunzhou.tdinformation.lottery.lottery.view.CustomHighFragment;
import com.yunzhou.tdinformation.lottery.lottery.view.CustomLocalFragment;
import com.yunzhou.tdinformation.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.lottery
 *  @文件名:   CustomLotteryDialogFragment
 *  @创建者:   lz
 *  @创建时间:  2018/10/24 17:44
 *  @描述：
 */

public class CustomLotteryDialogFragment extends DialogFragment implements View.OnClickListener {
    private static final String TAG = "CustomLotteryDialog";
    @BindView(R.id.tab_custom_lottery)
    SlidingTabLayout mTab;
    @BindView(R.id.vp_custom_lottery)
    NoScrollViewPager mVp;
    @BindView(R.id.btn_cancel)
    Button mBtnCancel;
    private List<AbsCustomFragment> mFragmentList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BottomDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setCanceledOnTouchOutside(true);
        View view = inflater.inflate(R.layout.fragment_custom_lottery, container, false);
        final Window window = getDialog().getWindow();
        window.setBackgroundDrawableResource(R.color.transparent);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);
        mTab = view.findViewById(R.id.tab_custom_lottery);
        mVp = view.findViewById(R.id.vp_custom_lottery);
        mBtnCancel = view.findViewById(R.id.btn_cancel);
        mBtnCancel.setOnClickListener(this);
        String[] tab = getResources().getStringArray(R.array.custom_lottery);
        CustomCountryFragment customCountryFragment = CustomCountryFragment.newInstance();
        CustomLocalFragment customLocalFragment = CustomLocalFragment.newInstance();
        CustomHighFragment customHighFragment = CustomHighFragment.newInstance();
        mFragmentList = new ArrayList<>(3);
        mFragmentList.add(customCountryFragment);
        mFragmentList.add(customLocalFragment);
        mFragmentList.add(customHighFragment);
        HomeAdapter adapter = new HomeAdapter<>(getChildFragmentManager(), tab, mFragmentList);
        mVp.setOffscreenPageLimit(2);
        mVp.setAdapter(adapter);
        mTab.setViewPager(mVp);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                dismiss();
                if (mFragmentList != null && !mFragmentList.isEmpty()) {
                    for (AbsCustomFragment absCustomFragment : mFragmentList) {
                        if (absCustomFragment.hasCustom) {
                            notifyRefresh(getContext());
                            break;
                        }
                    }

                }
                break;
        }

    }

    private void notifyRefresh(Context context) {
        Intent intent = new Intent("com.yunzhou.action.refresh.custom");
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
