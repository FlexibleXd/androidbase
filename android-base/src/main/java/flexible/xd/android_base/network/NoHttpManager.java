package flexible.xd.android_base.network;

import android.content.Context;


import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;

import flexible.xd.android_base.R;
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
        if (response.get() == null) {
            return;
        }
        if (null != mListener) {
            mListener.onSucceed(what, response);
        }
    }


    @Override
    public void onFailed(int what, Response<T> response) {
        LogUtils.LOGE("netException", response.getException().toString());
        ToastUtil.showShort(R.string.request_faile);
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
}
