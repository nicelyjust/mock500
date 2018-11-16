package com.yunzhou.tdinformation.pay;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.bean.oder.RedPacksBean;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.pay.callback.IRedFragmentListener;

import java.util.ArrayList;
import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.pay
 *  @文件名:   PayFragment
 *  @创建者:   lz
 *  @创建时间:  2018/9/30 10:00
 *  @描述：
 */

public class RedFragment extends DialogFragment implements View.OnClickListener, RedAdapter.Callback {


    private IRedFragmentListener mListener;
    private List<RedPacksBean> mRedPacks;
    RecyclerView mRv;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (IRedFragmentListener) context;
        } catch (Exception e) {
            throw new ClassCastException("must implement IRedFragmentListener");
        }
    }

    private void initBundle(Bundle bundle) {
        mRedPacks = bundle.getParcelableArrayList(AppConst.Extra.RED_PACK);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_red);
        dialog.setCanceledOnTouchOutside(true);

        // 设置宽度为屏宽, 靠近屏幕底部。
        final Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.AnimHorizon);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
        window.setAttributes(lp);
        initBundle(getArguments());
        initView(dialog);
        return dialog;
    }

    private void initView(Dialog dialog) {
        dialog.findViewById(R.id.btn_close).setOnClickListener(this);
        mRv = dialog.findViewById(R.id.rv_red_packet);
        mRv.setBackgroundColor(getResources().getColor(R.color.base_F5F5F5));
        mRv.setLayoutManager(new LinearLayoutManager(getContext()));
        RedAdapter adapter = new RedAdapter(getContext() ,this ,RedAdapter.TYPE_ODER);
        mRv.setAdapter(adapter);
        adapter.setData(mRedPacks);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_close:
                dismiss();
                break;

        }
    }


    public static RedFragment newInstance(ArrayList<RedPacksBean> redPacks) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(AppConst.Extra.RED_PACK, redPacks);
        RedFragment fragment = new RedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClickCheckUselessRed() {

    }

    @Override
    public void onItemClick(int pos) {
        // 金额返回给oderFragment
        dismiss();
        RedPacksBean redPacksBean = mRedPacks.get(pos);
        mListener.onSelRedPacketBack(redPacksBean);
    }
}
