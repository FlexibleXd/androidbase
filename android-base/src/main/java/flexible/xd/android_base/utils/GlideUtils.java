package flexible.xd.android_base.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;


/**
 * Created by flexibleXd on 2016/12/22.
 * <p>
 * desc  : glide封装
 */

public class GlideUtils {
    /**
     * 普通加载 无默认 int
     *
     * @param ctx
     * @param resouceId
     * @param imageView
     */
    public static void resouce2Iv(Context ctx, int resouceId, ImageView imageView) {
        Glide.with(ctx).load(resouceId).centerCrop().into(imageView);
    }

    /**
     * url
     *
     * @param ctx
     * @param imageUrl
     * @param imageView
     */
    public static void url2Iv(Context ctx, String imageUrl,ImageView imageView) {
        Glide.with(ctx).load(imageUrl).centerCrop().into(imageView);
    }


    /**
     * string 带默认图片
     *
     * @param ctx
     * @param imageUrl
     * @param imageView
     */
    public static void default2Iv(Context ctx, String imageUrl, int defaultImg, ImageView imageView) {
        Glide.with(ctx).load(imageUrl).centerCrop().placeholder(defaultImg).into(imageView);
    }
    /**
     * uri
     *
     * @param ctx
     * @param imageUri
     * @param imageView
     */
    public static void uri2Iv(Context ctx, Uri imageUri, ImageView imageView) {
        Glide.with(ctx).load(imageUri).centerCrop().into(imageView);
    }

//
//
    public static void glide2IvBitmap(Context ctx, Bitmap imageUri, ImageView imageView) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageUri.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        Glide.with(ctx).load(bytes).centerCrop().into(imageView);
    }
}
