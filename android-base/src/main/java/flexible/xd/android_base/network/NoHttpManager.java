package flexible.xd.android_base.network;

import android.content.Context;

import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;

import flexible.xd.android_base.R;
import flexible.xd.android_base.base.XdApp;
import flexible.xd.android_base.model.event.LoginEvent;
import flexible.xd.android_base.utils.LogUtils;
import flexible.xd.android_base.utils.ToastUtil;
import flexible.xd.android_base.widget.LoadingDialog;

/**
 * Created by Flexible on 2017/2/6 0006.
 */

public class NoHttpManager<T> implements OnResponseListener<T> {


    private NoHttpListener<T> mListener;
    private Request<T> mRequest;
    private LoadingDialog loadingDialog;


    private boolean isLoad;
    private Context ctx;

    public NoHttpManager(Request<T> request, NoHttpListener<T>
            httpListener) {
        mListener = httpListener;
        this.mRequest = request;
    }

    public NoHttpManager(Request<T> request, NoHttpListener<T>
            httpListener, boolean isLoad) {
        mListener = httpListener;
        this.mRequest = request;
        this.isLoad = isLoad;
    }

    public NoHttpManager(Request<T> request, NoHttpListener<T>
            httpListener, boolean isLoad, Context ctx) {
        mListener = httpListener;
        this.mRequest = request;
        this.isLoad = isLoad;
        this.ctx = ctx;
    }

    @Override
    public void onStart(int what) {
        if (null != mListener) {
            mListener.onStart(what);
            if (isLoad) {
                if (null == loadingDialog)
                    loadingDialog = new LoadingDialog(ctx, -1, -1);
                loadingDialog.show();
            }
        }
    }

    @Override
    public void onSucceed(int what, Response<T> response) {
        if (response.responseCode() == 401) {
            EventBus.getDefault().post(new LoginEvent());
            return;
        }
        if (null != mListener) {
            mListener.onSucceed(what, response);
        }
    }


    @Override
    public void onFailed(int what, Response<T> response) {
        LogUtils.LOGE("Exception", response.getException().toString());
        ToastUtil.showToastBottom(XdApp.getAppContext(), XdApp.getAppContext().getString(R.string.request_faile));
        if (null != mListener) {
            mListener.onFailed(what, response);
        }
    }

    @Override
    public void onFinish(int what) {
        if (null != mListener) {
            mListener.onFinish(what);
            if (isLoad && null != loadingDialog)
                loadingDialog.dismiss();
        }
    }

//    public static Activity getActivity() {
//        try {
//            Class activityThreadClass = Class.forName("android.app.ActivityThread");
//            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
//            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
//            activitiesField.setAccessible(true);
//            Map activities = null;
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) { // 4.4 以下使用的是 HashMap
//                activities = (HashMap) activitiesField.get(activityThread);
//            } else { // 4.4 以上使用的是 ArrayMap
//                activities = (ArrayMap) activitiesField.get(activityThread);
//            }
//            for (Object activityRecord : activities.values()) {
//                Class activityRecordClass = activityRecord.getClass();
//                Field pausedField = activityRecordClass.getDeclaredField("paused"); // 找到 paused 为 false 的activity
//                pausedField.setAccessible(true);
//                if (!pausedField.getBoolean(activityRecord)) {
//                    Field activityField = activityRecordClass.getDeclaredField("activity");
//                    activityField.setAccessible(true);
//                    Activity activity = (Activity) activityField.get(activityRecord);
//                    return activity;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

}
