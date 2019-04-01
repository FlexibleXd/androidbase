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
            if (httpException.code() == 401) {//一般用于判断登录状态
                network401();
            } else {
                onFail("服务器异常，请稍后再试");
            }
        } else {
            onFail("获取失败，请检查网络状态");
        }
    }

    @Override
    public void onComplete() {

    }

    public abstract void network401();
}
