package flexible.xd.android_base.mvpBase;

public interface IBasePresenter<T> {

    void onAttach(T t);

    void onDetach();
    
}