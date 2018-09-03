package flexible.xd.android_base.base;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import flexible.xd.android_base.GlideApp;
import flexible.xd.android_base.R;
import flexible.xd.android_base.model.listener.NoDataOnClickListener;
import flexible.xd.android_base.model.listener.RefreshOnClickListener;
import flexible.xd.android_base.swipeBack.SwipeBackActivityBase;
import flexible.xd.android_base.swipeBack.SwipeBackActivityHelper;
import flexible.xd.android_base.swipeBack.SwipeBackLayout;
import flexible.xd.android_base.swipeBack.Utils;
import flexible.xd.android_base.utils.NetworkUtils;
import flexible.xd.android_base.utils.StringUtils;


/**
 * Created by flexibleXd on 2016/12/23.
 */

public class ToolBarActivity extends BaseActivity implements SwipeBackActivityBase {
    private Toolbar toolbar;
    private ViewGroup container;
    private ViewGroup frame;
    private View noNet;
    private TextView tvRefresh;
    private View noData;
    private TextView tvNoData;
    public static final String _TITLE = "TB_TITLE";
    private ImageView ivNoData;
    private TextView tvNoClick;
    private Boolean isCheckNet = false;
    private SwipeBackActivityHelper mHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
        getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
    }

    @Override
    public void setContentView(int layoutResID) {
        View _iView = getLayoutInflater().inflate(layoutFrameResId(),
                null);
        toolbar = (Toolbar) _iView.findViewById(R.id.flexible__toolbar);
        container = (ViewGroup) _iView.findViewById(R.id.flexible__container);
        frame = (ViewGroup) _iView.findViewById(R.id.flexible__frame);
        initToolbar();
        if (layoutResID > 0) {
            View append = getLayoutInflater().inflate(layoutResID, null);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT);
            getContainer().addView(append, layoutParams);
        }
        noNet();
        super.setContentView(_iView);
    }

    public void setCheckNet(Boolean checkNet) {
        isCheckNet = checkNet;
    }


    /**
     * 无网络回调
     */

    private RefreshOnClickListener refreshOnClickListener;


    public void setRefreshOnClickListener(RefreshOnClickListener refreshOnClickListener) {
        this.refreshOnClickListener = refreshOnClickListener;
    }

    /**
     * 无数据回调
     */
    private NoDataOnClickListener noDataOnClickListener;


    public void setNoDataOnClickListener(NoDataOnClickListener noDataOnClickListener) {
        this.noDataOnClickListener = noDataOnClickListener;
    }


    /**
     * 无数据页面初始化 无按钮
     *
     * @param imgId 图片资源id
     * @param text  提示语言
     */

    public void noDataShow(int imgId, String text) {
        if (noData == null) {
            noData = LayoutInflater.from(this).inflate(R.layout.view_no_data, null);
            tvNoData = ButterKnife.findById(noData, R.id.tv_no_data);
            ivNoData = ButterKnife.findById(noData, R.id.iv_no);
            tvNoClick = ButterKnife.findById(noData, R.id.tv_click);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.gravity = Gravity.CENTER;
            getContainer().addView(noData, params);
        }
        GlideApp.with(BaseApp.getAppContext()).load(imgId).into(ivNoData);
        tvNoData.setText(text);
        tvNoClick.setVisibility(View.GONE);
        noData.setVisibility(View.VISIBLE);
    }

    /**
     * 无数据页面初始化 有点击按钮
     *
     * @param imgId     图片资源id
     * @param text      提示语言
     * @param clickText 按钮文字
     */
    public void noDataShow(int imgId, String text, String clickText, NoDataOnClickListener listener) {
        if (noData == null) {
            noData = LayoutInflater.from(this).inflate(R.layout.view_no_data, null);
            tvNoData = ButterKnife.findById(noData, R.id.tv_no_data);
            ivNoData = ButterKnife.findById(noData, R.id.iv_no);
            tvNoClick = ButterKnife.findById(noData, R.id.tv_click);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.gravity = Gravity.CENTER;
            getContainer().addView(noData, params);
        }
        GlideApp.with(BaseApp.getAppContext()).load(imgId).into(ivNoData);
        tvNoData.setText(text);
        tvNoClick.setText(clickText);
        tvNoClick.setVisibility(View.VISIBLE);
        setNoDataOnClickListener(listener);
        tvNoClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noDataOnClickListener != null)
                    noDataOnClickListener.onClick();
            }
        });
        noData.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏无数据页面
     */
    public void noDataHide() {
        if (noData != null) {
            noData.setVisibility(View.GONE);
        }
    }

    /**
     * 无网络 初始化
     */
    public void noNet() {
        if (!isCheckNet) {
            return;
        }
        if (noNet == null) {
            noNet = LayoutInflater.from(this).inflate(R.layout.view_no_net, null);
            tvRefresh = ButterKnife.findById(noNet, R.id.tv_refresh);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.gravity = Gravity.CENTER;
            getContainer().addView(noNet, params);
        }
        if (NetworkUtils.isConnected()) {
            noNet.setVisibility(View.GONE);
            getContainer().removeView(noNet);
        } else {
            noNet.setVisibility(View.VISIBLE);
        }
        tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (refreshOnClickListener != null) {
                    refreshOnClickListener.onClick();
                    noNet.setVisibility(View.GONE);
                    getContainer().removeView(noNet);
                }
            }
        });
    }


    protected int layoutFrameResId() {
        return R.layout.flexible__content;
    }

    public ViewGroup getContainer() {
        return container;
    }

    public ViewGroup getFrame() {
        return frame;
    }

    protected void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String title = getIntent().getStringExtra(_TITLE);
        if (!StringUtils.isEmpty(title)) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }
}
