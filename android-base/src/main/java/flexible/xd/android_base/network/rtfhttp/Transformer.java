package flexible.xd.android_base.network.rtfhttp;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * author : flexible
 * email : lgd19940421@163.com
 **/
public class Transformer {

    public static <T> ObservableTransformer<T, T> schedule() {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}