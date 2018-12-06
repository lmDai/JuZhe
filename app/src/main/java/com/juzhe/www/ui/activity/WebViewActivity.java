package com.juzhe.www.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.H5PayCallback;
import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.util.H5PayResultModel;
import com.blankj.utilcode.util.LogUtils;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.juzhe.www.R;
import com.juzhe.www.base.BaseActivity;
import com.juzhe.www.ui.fragment.SmartRefreshWebLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_container)
    LinearLayout coordinator;
    protected AgentWeb mAgentWeb;
    private SmartRefreshWebLayout mSmartRefreshWebLayout = null;
    private String link;
    private int type = 0;
    private String realm = "http://test.bestsoft.channel.cqjjsms.com";

    @Override
    protected int getLayout() {
        return R.layout.activity_web_agent;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(R.id.toolbar)
                .statusBarColor(R.color.colorWhite)
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initView(Bundle savedInstanceState) {
        txtTitle.setVisibility(View.GONE);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle != null) {
            link = bundle.getString("link");
            if (bundle.containsKey("type")) {
                type = bundle.getInt("type");
            }
        }
        mSmartRefreshWebLayout = new SmartRefreshWebLayout(mContext);
        settingWebView();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        final SmartRefreshLayout mSmartRefreshLayout = (SmartRefreshLayout) this.mSmartRefreshWebLayout.getLayout();
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mAgentWeb.getUrlLoader().reload();

                mSmartRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSmartRefreshLayout.finishRefresh();
                    }
                }, 2000);
            }
        });
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void settingWebView() {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(coordinator, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setWebLayout(mSmartRefreshWebLayout)
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DERECT)//打开其他应用时，弹窗咨询用户是否前往其他应用
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                .createAgentWeb()
                .ready()
                .go(link);
    }

    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (type == 1) {
                //微信H5支付核心代码
                Log.i("single", url + "dfsfsd");
                if (url.startsWith("weixin://wap/pay?")) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
                if (!(url.startsWith("http") || url.startsWith("https"))) {
                    return true;
                }
                final PayTask task = new PayTask(mContext);
                boolean isInstercepter = task.payInterceptorWithUrl(url, true, new H5PayCallback() {
                    @Override
                    public void onPayResult(H5PayResultModel h5PayResultModel) {
                        final String url = h5PayResultModel.getReturnUrl();
                        WebViewActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!TextUtils.isEmpty(url)) {
                                    Map<String, String> extraHeaders = new HashMap<>();
                                    extraHeaders.put("Referer", realm);
                                    view.loadUrl(url, extraHeaders);
                                }
                            }
                        });
                    }
                });
                if (!isInstercepter) {
                    Map<String, String> extraHeaders = new HashMap<>();
                    extraHeaders.put("Referer", realm);
                    view.loadUrl(url, extraHeaders);
                }
            }
            return true;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            Log.i("single", request.getMethod() + request.getUrl() + "dfsfsd");
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //do you  work
            Log.i("Info", "BaseWebActivity onPageStarted");
        }
    };

    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //do you work
//            Log.i("Info","onProgress:"+newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (txtTitle != null) {
                txtTitle.setText(title);
                txtTitle.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("Info", "onResult:" + requestCode + " onResult:" + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mAgentWeb.destroy();
        mAgentWeb.getWebLifeCycle().onDestroy();
    }
}
