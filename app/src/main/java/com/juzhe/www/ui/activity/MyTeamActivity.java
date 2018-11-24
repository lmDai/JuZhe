package com.juzhe.www.ui.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.juzhe.www.R;
import com.juzhe.www.base.BaseMvpActivity;
import com.juzhe.www.bean.ChartModel;
import com.juzhe.www.bean.UserModel;
import com.juzhe.www.common.mvp_senior.annotaions.CreatePresenterAnnotation;
import com.juzhe.www.mvp.contract.ChartDataContract;
import com.juzhe.www.mvp.presenter.ChartDataPresenter;
import com.juzhe.www.utils.ChartUtils;
import com.juzhe.www.utils.UserUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的团队
 */
@CreatePresenterAnnotation(ChartDataPresenter.class)
public class MyTeamActivity extends BaseMvpActivity<ChartDataContract.View, ChartDataPresenter> implements ChartDataContract.View {

    @BindView(R.id.line_chart)
    LineChart lineChart;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txt_teams_count)
    TextView txtTeamsCount;
    @BindView(R.id.txt_commission)
    TextView txtCommission;
    @BindView(R.id.txt_potential_count)
    TextView txtPotentialCount;
    @BindView(R.id.txt_direct_count)
    TextView txtDirectCount;
    @BindView(R.id.txt_indirect_count)
    TextView txtIndirectCount;
    private UserModel userModel;


    @Override
    protected int getLayout() {
        return R.layout.activity_my_team;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(R.id.toolbar)
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        txtTitle.setText(mContext.getString(R.string.title_my_team));
        toolbar.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_toolbar));

        ChartUtils.initChart(lineChart);
        userModel=UserUtils.getUser(mContext);
        getMvpPresenter().getUserChart(userModel.getId(), userModel.getUser_channel_id());
    }


//    private List<Entry> getData(List<>) {
//        List<Entry> values = new ArrayList<>();
//        values.add(new Entry(0, 15, "08-1"));
//        values.add(new Entry(1, 15, "08-1"));
//        values.add(new Entry(2, 15, "08-1"));
//        values.add(new Entry(3, 20, "08-1"));
//        values.add(new Entry(4, 25, "08-1"));
//        values.add(new Entry(5, 20, "08-1"));
//        values.add(new Entry(6, 20, "08-1"));
//        return values;
//    }


    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }


    @Override
    public void setChartData(ChartModel models) {
        txtTeamsCount.setText(models.getTeams_count());
        txtCommission.setText(models.getCommission());
        txtPotentialCount.setText(models.getPotential_count());
        txtDirectCount.setText(models.getDirect_count());
        txtIndirectCount.setText(models.getIndirect_count());
        if (models.getChart() != null && models.getChart().size() > 0) {
            ChartUtils.notifyDataSetChanged(lineChart, getData(models.getChart()));
        }

    }

    private List<Entry> getData(List<ChartModel.ChartBean> chart) {
        List<Entry> values = new ArrayList<>();
        int i = 0;
        for (ChartModel.ChartBean bean : chart) {
            values.add(new Entry(i++, bean.getCommission(), bean.getDate()));
        }
        return values;
    }

    @Override
    public void showError(Throwable throwable) {

    }
}
