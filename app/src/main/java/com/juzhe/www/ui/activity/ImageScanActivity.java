package com.juzhe.www.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Button;

import com.blankj.utilcode.util.ToastUtils;
import com.juzhe.www.Constant;
import com.juzhe.www.R;
import com.juzhe.www.base.BaseActivity;
import com.juzhe.www.bean.PhotoInfo;
import com.juzhe.www.ui.adapter.ImgScanAdapter;
import com.juzhe.www.utils.DownloadCallBack;
import com.juzhe.www.utils.RetrofitHttp;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @package: com.juzhe.www.ui.activity
 * @user:xhkj
 * @date:2018/11/26
 * @description:
 **/
public class ImageScanActivity extends BaseActivity {
    @BindView(R.id.view_pager_theme)
    ViewPager viewPagerTheme;
    @BindView(R.id.btn_down)
    Button btnDown;
    private int position;
    private ArrayList<PhotoInfo> imageInfo = new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.activity_img_scan;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle != null) {
            position = bundle.getInt("position");
            imageInfo = bundle.getParcelableArrayList("imageview");
        }
        viewPagerTheme.setAdapter(new ImgScanAdapter(imageInfo, this));
        viewPagerTheme.setCurrentItem(position);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        viewPagerTheme.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                ImageScanActivity.this.position = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_down)
    public void onViewClicked() {
        String downloadUrl = imageInfo.get(position).getUrl();
        String mDownloadFileName = downloadUrl.substring(downloadUrl.lastIndexOf('/') + 1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                RetrofitHttp.getInstance().downloadFile(0, downloadUrl, mDownloadFileName, new DownloadCallBack() {
                    @Override
                    public void onProgress(int progress) {


                    }

                    @Override
                    public void onCompleted() {
                        ToastUtils.showShort("成功保存到" + Constant.APP_ROOT_PATH + Constant.DOWNLOAD_DIR + mDownloadFileName);
                        try {
                            MediaStore.Images.Media.insertImage(mContext.getContentResolver(), Constant.APP_ROOT_PATH + Constant.DOWNLOAD_DIR, mDownloadFileName, null);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Constant.APP_ROOT_PATH + Constant.DOWNLOAD_DIR + mDownloadFileName)));
                    }

                    @Override
                    public void onError(String msg) {
                        Log.i("single", msg);
                    }
                });

            }
        }).start();
    }
}
