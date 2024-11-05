package flexible.xd.android_base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import flexible.xd.android_base.swipeBack.SwipeBackActivityBase;
import flexible.xd.android_base.swipeBack.SwipeBackActivityHelper;
import flexible.xd.android_base.swipeBack.SwipeBackLayout;
import flexible.xd.android_base.swipeBack.Utils;

public class Main2Activity extends AppCompatActivity  implements SwipeBackActivityBase{
    private SwipeBackActivityHelper mHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
        getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
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
