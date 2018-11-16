package com.yunzhou.tdinformation.setting.presenter;

/**
 * Created by wsj on 2018/11/7.
 */

public interface IBindingPresent {
    void onSendIdentifyingCodeSuccess(String msg);

    void onSendIdentifyingCodeError(int StringResId);
    void onSendIdentifyingCodeError(String msg);

}
