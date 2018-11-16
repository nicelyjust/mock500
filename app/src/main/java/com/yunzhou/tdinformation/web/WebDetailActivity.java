package com.yunzhou.tdinformation.web;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yunzhou.common.utils.L;
import com.yunzhou.common.utils.ToastUtil;
import com.yunzhou.tdinformation.R;
import com.yunzhou.tdinformation.base.AppManager;
import com.yunzhou.tdinformation.base.activity.BaseCommonAct;
import com.yunzhou.tdinformation.bean.oder.OderEntity;
import com.yunzhou.tdinformation.bean.oder.PayTypeBean;
import com.yunzhou.tdinformation.bean.oder.RedPacksBean;
import com.yunzhou.tdinformation.constant.AppConst;
import com.yunzhou.tdinformation.constant.NetConstant;
import com.yunzhou.tdinformation.login.LoginActivity;
import com.yunzhou.tdinformation.pay.OderFragment;
import com.yunzhou.tdinformation.pay.PayFragment;
import com.yunzhou.tdinformation.pay.PayResultFragment;
import com.yunzhou.tdinformation.pay.RedFragment;
import com.yunzhou.tdinformation.pay.callback.IOderFragmentListener;
import com.yunzhou.tdinformation.pay.callback.IRedFragmentListener;
import com.yunzhou.tdinformation.user.UserManager;
import com.yunzhou.tdinformation.utils.Utils;
import com.yunzhou.tdinformation.view.pay.PayPwdView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import butterknife.BindView;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.web
 *  @文件名:   WebDetailActivity
 *  @创建者:   lz
 *  @创建时间:  2018/9/27 9:53
 *  @描述：
 */

public class WebDetailActivity extends BaseCommonAct implements PayPwdView.InputCallBack, IOderFragmentListener, WebDetailView, IRedFragmentListener {
    private static final String TAG = "WebDetailActivity";
    private static final String TAG_ODER = "oder";
    private static final String TAG_RED = "red";
    private static final String TAG_PASSWORD = "password";
    private static final String TAG_PAY_RESULT = "payResult";
    @BindView(R.id.ll_root_view)
    LinearLayout mLlRootView;
    @BindView(R.id.tb_tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.pb_web)
    ProgressBar mPb;
    private WebView mWebView;
    private HashMap<String, String> mParams;
    private WebDetailController mController;
    private String mPayJWT;
    private int mWhere;

    @Override
    protected int getContentView() {
        return R.layout.activity_web_detail;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setSupportActionBar(mToolBar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
        mController = new WebDetailController(this);
        initWebView();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mWhere == 1) {
            getMenuInflater().inflate(R.menu.menu_share, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.menu_share) {
            ToastUtil.showShort(this, R.string.share);
        }
        return super.onOptionsItemSelected(item);
    }

    private void initWebView() {
        mWebView = new WebView(AppManager.getsAppContext());
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        // 允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                return super.shouldInterceptRequest(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (mPb != null) {
                    mPb.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (mPb != null) {
                    mPb.setVisibility(View.GONE);
                }
            }

            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Log.d(TAG, "onReceivedError: " + error.getDescription().toString());
            }
        });
        mWebView.setWebChromeClient(mWebChromeClient);
        // mWebView.addJavascriptInterface(new TestApi(), "test");
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mLlRootView.addView(mWebView, params);
    }

    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            L.d("lz", "title == " + title);
            mTvTitle.setText(TextUtils.isEmpty(title) ? "文章详情" : title);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (mPb != null) {
                mPb.setProgress(newProgress);
            }
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {

            //假定传入进来的 url = "js://payMoney?arg1=111&arg2=222"

            Uri uri;
            try {
                uri = Uri.parse(message);
            } catch (Exception e) {
                e.printStackTrace();
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }
            // 如果url的协议 = 预先约定的 js 协议 就解析往下解析参数
            if (uri.getScheme().equals("js")) {
                String authority = uri.getAuthority();
                if (authority.equals(AppConst.JS.PAY_MONEY)) {
                    HashMap<String, String> params = new HashMap<>();
                    Set<String> set = uri.getQueryParameterNames();
                    for (String name : set) {
                        params.put(name, uri.getQueryParameter(name));
                    }
                    result.confirm("WTF");
                    String money = params.get(AppConst.JS.MONEY);
                    String essayId = params.get(AppConst.JS.ESSAY_ID);
                    mController.addOder(WebDetailActivity.this, essayId, money);
                } else if (authority.equals(AppConst.JS.SEND_KEY)) {
                    result.confirm(UserManager.getInstance().getJwt());
                } else if (authority.equals(AppConst.JS.LOGIN)) {
                    LoginActivity.startForResult(WebDetailActivity.this);
                    result.confirm(UserManager.getInstance().getJwt());
                }
                return true;
            }
            return super.onJsPrompt(view, url, message, defaultValue, result);
        }

    };

    private void showPayBottomDialog(String money, String oderNo, PayTypeBean payType, boolean canUsePacket) {
        OderFragment oderFragment = OderFragment.newInstance(money, oderNo, payType, canUsePacket);
        oderFragment.show(getSupportFragmentManager(), TAG_ODER);
    }


    @Override
    protected void initData() {
        mWhere = getIntent().getIntExtra(AppConst.Extra.FROM_WHERE ,0);
        if (mWhere == 1) {
            String contentID = getIntent().getStringExtra(AppConst.Extra.CONTENT_ID);
            String userID = getIntent().getStringExtra(AppConst.Extra.USER_ID);
            mParams = new HashMap<>(2);
            mParams.put(AppConst.Extra.CONTENT_ID, contentID);
            mParams.put(AppConst.Extra.USER_ID, userID);
            mWebView.loadUrl(Utils.addBasicParams(NetConstant.BASE_ARTICLE_URL, mParams));
        } else if (mWhere == 2){
            String url = getIntent().getStringExtra(AppConst.Extra.CHART_URL);
            mWebView.loadUrl(url);
        }

    }

    @Override
    public void showOderDialog(OderEntity content) {
        List<RedPacksBean> redPacks = content.getRedPacks();
        showPayBottomDialog(content.getOrderMoney(), content.getOrderNum(), content.getPayType(), redPacks == null || redPacks.isEmpty() ? false : true);
    }

    @Override
    public void showGetJwtOk(String payJWT) {
        // 弹出输入密码框
        mPayJWT = payJWT;
        dismissFragment(TAG_ODER);
        showPassWordFragment();
    }

    @Override
    public void showPaySuccess() {
        PayResultFragment fragment = PayResultFragment.newInstance();
        fragment.show(getSupportFragmentManager() ,TAG_PAY_RESULT);
        // 刷新当前网页
        mWebView.loadUrl(Utils.addBasicParams(NetConstant.BASE_ARTICLE_URL, mParams));
    }

    public void showPassWordFragment() {
        Bundle bundle = new Bundle();
        PayFragment fragment = new PayFragment();
        fragment.setArguments(bundle);
        fragment.setPaySuccessCallBack(WebDetailActivity.this);
        fragment.show(getSupportFragmentManager(), TAG_PASSWORD);
    }

    @Override
    public void onInputFinish(String result) {
        // 去支付 , 订单流水号 ; 密码
        dismissFragment(TAG_PASSWORD);
        mController.pay(this, mController.getContent().getOrderNum(), result,mPayJWT ,mController.getContent().getOrderMoney());
    }

    @Override
    public void onPayBtnClick() {
        mController.getPayJwt(this);
    }

    @Override
    public List<RedPacksBean> onSelectRed() {
        ArrayList<RedPacksBean> redPacks = (ArrayList<RedPacksBean>) mController.getContent().getRedPacks();
        RedFragment redFragment = RedFragment.newInstance(redPacks);
        redFragment.show(getSupportFragmentManager(), TAG_RED);
        return redPacks;
    }

    @Override
    public void onSelRedPacketBack(RedPacksBean redPacksBean) {
        OderFragment oderFragment = (OderFragment) getSupportFragmentManager().findFragmentByTag(TAG_ODER);
        oderFragment.onSelRedPacketBack(redPacksBean);
    }

    private void dismissFragment(String tag) {
        switch (tag) {
            case TAG_PASSWORD:
                PayFragment payFragment = (PayFragment) getSupportFragmentManager().findFragmentByTag(TAG_PASSWORD);
                payFragment.dismiss();
                break;
            case TAG_RED:
                RedFragment redFragment = (RedFragment) getSupportFragmentManager().findFragmentByTag(TAG_RED);
                redFragment.dismiss();
                break;
            case TAG_ODER:
                OderFragment oderFragment = (OderFragment) getSupportFragmentManager().findFragmentByTag(TAG_ODER);
                oderFragment.dismiss();
                break;
            default:

                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConst.Request.LOGIN && resultCode == RESULT_OK) {
            // 刷新当前网页
            mWebView.loadUrl(Utils.addBasicParams(NetConstant.BASE_ARTICLE_URL, mParams));
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showBtnLoading() {

    }

    @Override
    public void hideBtnLoading() {

    }

    @Override
    public void showToast(String msg) {
        ToastUtil.showShort(this, msg);
    }

    @Override
    public void showToast(int msg) {
        ToastUtil.showShort(this, msg);
    }

    public static void start(Context context, int where, String contentId, String uID) {
        Intent starter = new Intent(context, WebDetailActivity.class);
        starter.putExtra(AppConst.Extra.CONTENT_ID, contentId);
        starter.putExtra(AppConst.Extra.FROM_WHERE, where);
        starter.putExtra(AppConst.Extra.USER_ID, TextUtils.isEmpty(uID) ? "-1" : uID);
        context.startActivity(starter);
    }
    public static void start(Context context, int where ,String url) {
        Intent starter = new Intent(context, WebDetailActivity.class);
        starter.putExtra(AppConst.Extra.CHART_URL, url);
        starter.putExtra(AppConst.Extra.FROM_WHERE, where);
        context.startActivity(starter);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mController.detachView();
        ViewGroup parent = (ViewGroup) mWebView.getParent();
        if (parent != null) {
            parent.removeView(mWebView);
            mWebView.destroy();
        }
    }
}
