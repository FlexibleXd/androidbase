package flexible.xd.android_base.swipeBack;

/**
 * Created by flexibleXd on 2016/12/12.
 */

public  interface SwipeBackActivityBase {

    /**
     * @return the SwipeBackLayout associated with this activity.
     */
     abstract SwipeBackLayout getSwipeBackLayout();

     abstract void setSwipeBackEnable(boolean enable);

    /**
     * Scroll out contentView and finish the activity
     */
     abstract void scrollToFinishActivity();
}
