package flexible.xd.android_base.mvpBase;

public interface IPresenter<T> {

    void onAttach(T t);

    void onDetach();
    
}