package com.juzhe.www.ui.activity.product;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.juzhe.www.R;
import com.juzhe.www.base.BaseMvpActivity;
import com.juzhe.www.bean.JdSearchModel;
import com.juzhe.www.bean.KeyWordModel;
import com.juzhe.www.bean.UserModel;
import com.juzhe.www.common.mvp_senior.annotaions.CreatePresenterAnnotation;
import com.juzhe.www.common.widget.FlowTagLayout;
import com.juzhe.www.mvp.contract.SearchContract;
import com.juzhe.www.mvp.presenter.SearchPresenter;
import com.juzhe.www.utils.IntentUtils;
import com.juzhe.www.utils.KeyWordDaoOpe;
import com.juzhe.www.utils.UserUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 搜索页面
 */
@CreatePresenterAnnotation(SearchPresenter.class)
public class SearchActivity extends BaseMvpActivity<SearchContract.View, SearchPresenter> implements SearchContract.View {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.edit_search)
    EditText editSearch;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tag_hot_search)
    FlowTagLayout tagHotSearch;
    @BindView(R.id.tag_history)
    FlowTagLayout tagHistory;
    @BindView(R.id.txt_search)
    TextView txtSearch;
    @BindView(R.id.radio)
    RadioGroup radio;
    @BindView(R.id.rb_taobao)
    RadioButton rbTaobao;
    private UserModel userModel;
    private String type = "1";


    @Override
    protected int getLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(R.id.toolbar)
                .statusBarColor(R.color.colorWhite)
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH)
                    if (!TextUtils.isEmpty(editSearch.getText().toString())) {
                        if (TextUtils.equals(type, "1")||TextUtils.equals(type,"3")) {
                            saveLocale(editSearch.getText().toString());
                            Bundle bundle = new Bundle();
                            bundle.putString("type",type);
                            bundle.putString("keyword", editSearch.getText().toString());
                            IntentUtils.get().goActivity(mContext, SearchDetailActivity.class, bundle);
                        }  else {
                            getMvpPresenter().getJdPddSearch(type, editSearch.getText().toString(), "", userModel.getId(), userModel.getUser_channel_id(), userModel.getLevel());
                        }
                    }
                return false;
            }
        });

        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_taobao:
                        type = "1";
                        break;
                    case R.id.rb_jd:
                        type = "2";
                        break;
                    case R.id.rb_pdd:
                        type = "3";
                        break;
                }
            }
        });

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        showSearchHistory(KeyWordDaoOpe.updateList(mContext));
        rbTaobao.setChecked(true);
        userModel = UserUtils.getUser(mContext);
        getMvpPresenter().getHotKeyWord(userModel.getId(), userModel.getUser_channel_id());
    }

    @Override
    public void setHotKeyWords(List<KeyWordModel> models) {
        List<String> dataList = new ArrayList<>();
        if (models != null && models.size() > 0)
            for (KeyWordModel keyWordModel : models) {
                dataList.add(keyWordModel.getKeyword());
            }
        tagHotSearch.addTags(dataList);
        tagHotSearch.setTagClickListener(new FlowTagLayout.OnTagClickListener() {
            @Override
            public void tagClick(int position) {
                saveLocale(models.get(position).getKeyword());
                editSearch.setText(dataList.get(position));
                editSearch.setSelection(editSearch.getText().length());
                if (!TextUtils.isEmpty(editSearch.getText().toString())) {
                    if (TextUtils.equals(type, "1")||TextUtils.equals(type,"3")) {
                        saveLocale(editSearch.getText().toString());
                        Bundle bundle = new Bundle();
                        bundle.putString("type",type);
                        bundle.putString("keyword", editSearch.getText().toString());
                        IntentUtils.get().goActivity(mContext, SearchDetailActivity.class, bundle);
                    }  else {
                        getMvpPresenter().getJdPddSearch(type, editSearch.getText().toString(), "", userModel.getId(), userModel.getUser_channel_id(), userModel.getLevel());
                    }
                }
            }
        });
    }

    @Override
    public void showError(Throwable throwable) {

    }

    @Override
    public void setJdPddResult(JdSearchModel result) {
        Bundle bundle = new Bundle();
        bundle.putString("link", result.getLink());
        bundle.putString("jd_pid", result.getJingdong_pid());
        IntentUtils.get().goActivity(mContext, WebViewPddDetailsActivity.class, bundle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.img_back, R.id.txt_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_search:
                if (!TextUtils.isEmpty(editSearch.getText().toString())) {
                    if (TextUtils.equals(type, "1")||TextUtils.equals(type,"3")) {
                        saveLocale(editSearch.getText().toString());
                        Bundle bundle = new Bundle();
                        bundle.putString("type",type);
                        bundle.putString("keyword", editSearch.getText().toString());
                        IntentUtils.get().goActivity(mContext, SearchDetailActivity.class, bundle);
                    }  else {
                        getMvpPresenter().getJdPddSearch(type, editSearch.getText().toString(), "", userModel.getId(), userModel.getUser_channel_id(), userModel.getLevel());
                    }
                }
                break;
        }
    }


    private void saveLocale(String keyWord) {
        showSearchHistory(KeyWordDaoOpe.insertDB(mContext, keyWord));
    }

    private void showSearchHistory(List<KeyWordModel> models) {
        List<String> dataList = new ArrayList<>();
        if (models != null && models.size() > 0)
            for (KeyWordModel keyWordModel : models) {
                dataList.add(keyWordModel.getKeyword());
            }
        tagHistory.addTags(dataList);
        tagHistory.setTagClickListener(new FlowTagLayout.OnTagClickListener() {
            @Override
            public void tagClick(int position) {
                editSearch.setText(dataList.get(position));
                editSearch.setSelection(editSearch.getText().length());
                if (TextUtils.equals(type, "1")||TextUtils.equals(type,"3")) {
                    saveLocale(editSearch.getText().toString());
                    Bundle bundle = new Bundle();
                    bundle.putString("type",type);
                    bundle.putString("keyword", editSearch.getText().toString());
                    IntentUtils.get().goActivity(mContext, SearchDetailActivity.class, bundle);
                }  else {
                    getMvpPresenter().getJdPddSearch(type, editSearch.getText().toString(), "", userModel.getId(), userModel.getUser_channel_id(), userModel.getLevel());
                }
            }
        });
    }



}
