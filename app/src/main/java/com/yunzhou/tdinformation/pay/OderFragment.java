package com.yunzhou.tdinformation.pay;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.yunzhou.common.utils.ToastUtil;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.bean.oder.PayTypeBean;
import com.yunzhou.tdinformation.bean.oder.RedPacksBean;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.pay.callback.IOderFragmentListener;

import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.pay
 *  @文件名:   PayFragment
 *  @创建者:   lz
 *  @创建时间:  2018/9/30 10:00
 *  @描述：
 */

public class OderFragment extends DialogFragment implements View.OnClickListener {
    TextView mTvMoney;
    //选择支付方式
    TextView mSelPay;
    TextView mSelRedPocket;
    private String mMoney;
    private IOderFragmentListener mListener;
    private boolean mCanUsePacket;
    private String mOderNo;
    private PayTypeBean mPayTypeBean;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (IOderFragmentListener) context;
        } catch (Exception e) {
            throw new ClassCastException("must implement IOderFragmentListener");
        }
    }

    private void initBundle(Bundle bundle) {
        mMoney = bundle.getString(AppConst.JS.MONEY);
        mOderNo = bundle.getString(AppConst.Extra.ODER_NO);
        mCanUsePacket = bundle.getBoolean(AppConst.Extra.CAN_USE_PACKET);
        mPayTypeBean = (PayTypeBean) bundle.getSerializable(AppConst.Extra.PAY_TYPE_BEAN);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_oder);
        dialog.setCanceledOnTouchOutside(true);

        // 设置宽度为屏宽, 靠近屏幕底部。
        final Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.AnimBottom);
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
        mTvMoney = dialog.findViewById(R.id.tv_money);
        dialog.findViewById(R.id.btn_close).setOnClickListener(this);
        dialog.findViewById(R.id.btn_pay_right_now).setOnClickListener(this);
        mSelPay = dialog.findViewById(R.id.tv_sel_pay);
        mSelPay.setOnClickListener(this);
        if (mCanUsePacket) {
            mSelRedPocket = dialog.findViewById(R.id.tv_sel_red_pocket);
            mSelRedPocket.setOnClickListener(this);
            mSelRedPocket.setText("可选");
        } else {
            mSelRedPocket.setText("暂无可用红包");
        }
        dialog.findViewById(R.id.btn_pay_right_now).setOnClickListener(this);

        mTvMoney.setText("¥".concat(mMoney));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_close:
                dismiss();
                break;
            case R.id.tv_sel_pay:
                ToastUtil.showShort(getContext() ,"选择支付方式");
                break;
            case R.id.tv_sel_red_pocket:
                // 跳转去
                List<RedPacksBean> redPacksBeans = mListener.onSelectRed();
                break;
            case R.id.btn_pay_right_now:
                // 支付
                mListener.onPayBtnClick();
                break;

        }
    }

    public static OderFragment newInstance(String money, String oderNo, PayTypeBean payType, boolean canUsePacket) {

        Bundle bundle = new Bundle();
        bundle.putString(AppConst.JS.MONEY, money);
        bundle.putString(AppConst.Extra.ODER_NO, oderNo);
        bundle.putBoolean(AppConst.Extra.CAN_USE_PACKET, canUsePacket);
        bundle.putSerializable(AppConst.Extra.PAY_TYPE_BEAN, payType);
        OderFragment fragment = new OderFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void onSelRedPacketBack(RedPacksBean redPacksBean) {
        mSelRedPocket.setText("-" + redPacksBean.getAmount() + " 彩币");
    }
}
