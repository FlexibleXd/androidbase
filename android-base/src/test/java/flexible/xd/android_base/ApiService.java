package flexible.xd.android_base;

import flexible.xd.android_base.mvpBase.IBaseModel;
import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * author : flexible
 * email : lgd19940421@163.com
 * github: https://github.com/FlexibleXd
 **/
public interface ApiService  {

    @POST("/login")
    Observable<IBaseModel> login(@Query("name") String name,
                                 @Query("pwd") String pwd);
}
