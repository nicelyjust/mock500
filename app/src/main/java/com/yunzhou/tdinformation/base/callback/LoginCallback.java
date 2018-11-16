/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yunzhou.tdinformation.base.callback;

import android.content.Context;
import android.content.Intent;

import com.lzy.okgo.model.Response;
import com.yunzhou.common.http.LzException;
import com.yunzhou.common.http.callback.JsonCallback;
import com.yunzhou.common.utils.ToastUtil;
import com.yunzhou.tdinformation.login.LoginActivity;
import com.yunzhou.tdinformation.user.UserManager;


public abstract class LoginCallback<T> extends JsonCallback<T> {
    private LzException mLzException;
    private Context mContext;

    public LoginCallback(Context context) {
        super();
        this.mContext = context;
    }

    @Override
    public void onError(Response<T> response) {
        super.onError(response);
        Throwable exception = response.getException();
        if (exception instanceof LzException) {
            LzException lzException = (LzException) exception;
            if (lzException.getCode() == LzException.NEED_LOGIN) {
                // 跳转去登录
                mLzException = lzException;
            }
        }
    }

    @Override
    public void onFinish() {
        if (mLzException != null) {
            UserManager.getInstance().clear();
            ToastUtil.showShort(mContext , mLzException.getMessage());
            Intent intent = new Intent(mContext, LoginActivity.class);
            mContext.startActivity(intent);
            mLzException = null;
        }
    }
}
