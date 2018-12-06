package flexible.xd.android_base.mvpBase;

public interface IBaseView {

    void showLoading();

    void hideLoading();

    void onError(String message);
}
