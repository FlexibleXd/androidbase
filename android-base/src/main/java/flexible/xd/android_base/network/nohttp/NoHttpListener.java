package flexible.xd.android_base.network.nohttp;


import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by Flexible on 2017/2/7 0007.
 */

public interface NoHttpListener<T> {
    void onStart(int what);

    void onSucceed(int what, Response<T> response);

    void onFinish(int what);

    void onFailed(int what, Response<T> response);

}
