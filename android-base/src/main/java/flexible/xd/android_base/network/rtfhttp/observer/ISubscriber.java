package flexible.xd.android_base.network.rtfhttp.observer;


import io.reactivex.disposables.Disposable;

/**
 * author : flexible
 * email : lgd19940421@163.com
 * github: https://github.com/FlexibleXd
 **/
public interface ISubscriber<T> {

    void doOnSubscribe(Disposable d);

    void onFail(String errorMsg);

    void onSuccess(T t);

}
