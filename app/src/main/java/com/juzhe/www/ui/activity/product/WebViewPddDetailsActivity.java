package com.juzhe.www.ui.activity.product;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.juzhe.www.R;
import com.juzhe.www.base.BaseActivity;
import com.juzhe.www.ui.fragment.SmartRefreshWebLayout;
import com.kepler.jd.Listener.OpenAppAction;
import com.kepler.jd.login.KeplerApiManager;
import com.kepler.jd.sdk.bean.KelperTask;
import com.kepler.jd.sdk.bean.KeplerAttachParameter;
import com.kepler.jd.sdk.exception.KeplerBufferOverflowException;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 拼多多详情页面
 */
public class WebViewPddDetailsActivity extends BaseActivity {


    @BindView(R.id.ll_container)
    LinearLayout coordinator;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;
    @BindView(R.id.img_close)
    ImageView imgClose;
    @BindView(R.id.img_refresh)
    ImageView imgRefresh;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private String link;
    protected AgentWeb mAgentWeb;
    private SmartRefreshWebLayout mSmartRefreshWebLayout = null;
    Handler mHandler;
    KelperTask mKelperTask;
    public static final int timeOut = 15;
    private String jdPid;
    private String newLink;

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
        mHandler = new Handler();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle != null) {
            link = bundle.getString("link");
            if (bundle.containsKey("jd_pid")) {
                jdPid = bundle.getString("jd_pid");
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
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            Log.i("single", request.getUrl().toString());
            if (request.getUrl().toString().startsWith("https://item.m.jd.com/")) {
                String url = request.getUrl().toString();
                int lastIndex = url.lastIndexOf("?");
                openJd(url.substring(0, lastIndex));
                return true;
            }
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
                if (jdPid != null) {
                    txtTitle.setText("京东");
                } else {
                    txtTitle.setText(title);
                }
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

    OpenAppAction mOpenAppAction = new OpenAppAction() {
        @Override
        public void onStatus(final int status) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (status == OpenAppAction.OpenAppAction_start) {//开始状态未必一定执行，
                        Log.i("single", "status" + status);
                    } else if (status == OpenAppAction.OpenAppAction_result_NoJDAPP) {

                    } else {
                        mKelperTask = null;
                        Log.i("single", "status" + 123456);
                    }
                }
            });
        }
    };
    KeplerAttachParameter mKeplerAttachParameter = new KeplerAttachParameter();//这个是即时性参数  可以设置


    /**
     * 打开京东领取优惠券
     */
    private void openJd(String link) {
        this.newLink = link;
        int kk = Integer.parseInt(jdPid);
//        boolean hasInstalled = IntentUtils.checkHasInstalledApp(mContext, "com.jingdong.app.mall");
//        if (!hasInstalled) {
//            Bundle bundle = new Bundle();
//            bundle.putString("link", newLink);
//            IntentUtils.get().goActivity(mContext, WebViewPddDetailsActivity.class, bundle);
//            return;
//        }
        try {
            mKeplerAttachParameter.setPositionId(kk);
            mKelperTask = KeplerApiManager.getWebViewService().openJDUrlPage(link, mKeplerAttachParameter, this, mOpenAppAction, timeOut);
        } catch (KeplerBufferOverflowException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mAgentWeb.destroy();
        mAgentWeb.getWebLifeCycle().onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.img_back, R.id.img_close, R.id.img_refresh})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                if (mAgentWeb.getWebCreator().getWebView().canGoBack()) {
                    mAgentWeb.back();
                } else {
                    finish();
                }
                break;
            case R.id.img_close:
                finish();
                break;
            case R.id.img_refresh:
                mAgentWeb.getWebCreator().getWebView().reload();
                break;
        }
    }
}
