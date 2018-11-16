package com.yunzhou.tdinformation.setting.view;

import com.yunzhou.tdinformation.base.mvp.BaseView;

/**
 * Created by Administrator on 2018/11/7.
 */

public interface IBindingView extends BaseView {
    void onIdentifyingCodeSuccess();

    void showBindingSuccess();

    void showBindingError(String message);
}
