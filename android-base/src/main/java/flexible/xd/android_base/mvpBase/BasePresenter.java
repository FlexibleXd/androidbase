package flexible.xd.android_base.mvpBase;



import io.reactivex.disposables.CompositeDisposable;

public abstract class BasePresenter<T extends IBaseView,M> implements IPresenter<T> {


    protected T mView;
    protected M mModel;

    protected CompositeDisposable mDisposable;

    public BasePresenter() {
        this.mModel = bindModel();
        mDisposable = new CompositeDisposable();
    }

    public boolean isViewAttached() {
        return mView != null;
    }


    public void checkViewAttached() {
        if (!isViewAttached()) throw new RuntimeException("未注册View");
    }

    @Override
    public void onAttach(T t) {
        mView = t;
        if (mModel == null) {
            throw new NullPointerException("model没有绑定 不能使用");
        }
    }

    /**
     * return Model
     */
    public abstract M bindModel();

    @Override
    public void onDetach() {
        if (!mDisposable.isDisposed())
            mDisposable.dispose();
        mView = null;
    }


}
