package flexible.xd.android_base.base;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.StringUtils;

import flexible.xd.android_base.R;
import flexible.xd.android_base.model.listener.NoDataOnClickListener;
import flexible.xd.android_base.model.listener.RefreshOnClickListener;
import flexible.xd.android_base.swipeBack.SwipeBackActivityBase;
import flexible.xd.android_base.swipeBack.SwipeBackActivityHelper;
import flexible.xd.android_base.swipeBack.SwipeBackLayout;
import flexible.xd.android_base.swipeBack.Utils;



/**
 * Created by flexibleXd on 2016/12/23.
 */

public abstract class ToolBarActivity extends BaseActivity implements SwipeBackActivityBase {
    private Toolbar toolbar;
    private ViewGroup container;
    private ViewGroup frame;

    public static final String _TITLE = "TB_TITLE";


    private Boolean isCheckNet = false;
    private SwipeBackActivityHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        super.setContentView(_iView);
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
