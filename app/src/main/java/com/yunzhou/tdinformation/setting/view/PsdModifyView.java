package com.yunzhou.tdinformation.setting.view;

import com.yunzhou.tdinformation.base.mvp.BaseView;

/**
 * Created by Administrator on 2018/11/8.
 */

public interface PsdModifyView extends BaseView {
    void onModifySuccess();

    void onModifyError(String message);
}
