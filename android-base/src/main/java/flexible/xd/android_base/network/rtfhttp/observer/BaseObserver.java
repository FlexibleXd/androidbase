package flexible.xd.android_base.network.rtfhttp.observer;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * author : flexible
 * email : lgd19940421@163.com
 * github: https://github.com/FlexibleXd
 **/
public abstract class BaseObserver<T> implements Observer<T>, ISubscriber<T> {
    @Override
    public void onSubscribe(Disposable d) {
        doOnSubscribe(d);
    }

    @Override
    public void onNext(T t) {
        //封装不同网络请求成功、失败规则
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            if (httpException.code() == 401) {
                onFail("请登录");
            } else {
                onFail("呀，服务器出错了");
            }
        } else {
            onFail("呀，网络出了问题");
        }
    }

}
