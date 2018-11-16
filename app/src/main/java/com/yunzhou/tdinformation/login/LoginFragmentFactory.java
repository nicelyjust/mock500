package com.yunzhou.tdinformation.login;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.yunzhou.tdinformation.login.fragment.AbstractLoginFlyWeightFragment;
import com.yunzhou.tdinformation.login.fragment.ForgetFragment;
import com.yunzhou.tdinformation.login.fragment.LoginFragment;
import com.yunzhou.tdinformation.login.fragment.RegisterFragment;

/**
 * Created by LuoTao on 2018/6/16.
 * 简单工厂方式生产登陆页fragment
 */
public class LoginFragmentFactory {

    public LoginFragmentFactory(){
    }

    //登陆页
    public final static int LoginTag = 0;
    //忘记密码页
    public final static int ForgetTag = 1;
    //注册页R
    public final static int RegisterTag = 2;
    //必须是MainActivity生成
    @Nullable
    public AbstractLoginFlyWeightFragment createMainFragment(@NonNull LoginActivity context, int tag){
        AbstractLoginFlyWeightFragment abstractMainFragment = null;
        switch (tag){
            case LoginTag:
                abstractMainFragment = new LoginFragment();
                break;
            case ForgetTag:
                abstractMainFragment = new ForgetFragment();
                break;
            case RegisterTag:
                abstractMainFragment = new RegisterFragment();
                break;
        }
        return abstractMainFragment;
    }

    protected void onResume() {
        /*if(null != loginPresenter)
            loginPresenter.onStart();*/
    }

    public void onDestroy(){
        /*if(null != loginPresenter)
            loginPresenter.onDestroy();*/
    }
}
