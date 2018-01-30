package flexible.xd.android_base.utils;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;


import java.io.File;

import static flexible.xd.android_base.utils.ToastUtil.showShort;


/**
 * Created by Flexible on 2017/9/2 0002.
 */

public class AvatarMananger {

    private String AVATAR_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator + "xd";
    public static final int AVATER_PIC = 1001;
    public static final int AVATER_ALBUM = 1002;
    public static final int AVATER_CROPE = 1003;
    private static AvatarMananger instance;
    private static Activity act;
    public static Uri lastUri;
    public static File picFile;
    private String[] choose = {"拍照", "从相册获取"};
    private String appId = "";


    private AvatarMananger() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                PackageManager.PERMISSION_DENIED  == ContextCompat.checkSelfPermission(act, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            showShort( "请手动开启sd卡权限");
            return;
        }
        deleterFile();
        newDirectory();
    }

    private AvatarMananger(String appId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                PackageManager.PERMISSION_DENIED  == ContextCompat.checkSelfPermission(act, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            showShort( "请手动开启sd卡权限");
            return;
        }
        this.appId = appId;
        deleterFile();
        newDirectory();
    }

    private void newDirectory() {
        File file = new File(AVATAR_PATH);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    public static AvatarMananger newInstance(Activity activity) {
        act = activity;
        if (null == instance)
            instance = new AvatarMananger();
        return instance;
    }

    public static AvatarMananger newInstance(Activity activity, String appId) {
        act = activity;
        if (null == instance)
            instance = new AvatarMananger(appId);
        return instance;
    }

    /**
     * 打开弹框
     */
    public void openDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(act);
        dialog.setItems(choose, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    takePic();
                } else {
                    openAlbum();
                }
                dialog.dismiss();
            }
        }).show();

    }

    /**
     * 打开弹框
     */
    public void openDialog(final int photo, final int album) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(act);
        dialog.setItems(choose, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    takePic(photo);
                } else {
                    openAlbum(album);
                }
                dialog.dismiss();
            }
        }).show();

    }

    /**
     * 打开图库
     */
    public void openAlbum() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                PackageManager.PERMISSION_DENIED  == ContextCompat.checkSelfPermission(act, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            showShort( "请手动开启sd卡权限");
            return;
        }
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        act.startActivityForResult(intent, AVATER_ALBUM);
    }

    /**
     * 打开图库
     */
    public void openAlbum(int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                PackageManager.PERMISSION_DENIED  == ContextCompat.checkSelfPermission(act, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            showShort( "请手动开启sd卡权限");
            return;
        }
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        act.startActivityForResult(intent, id);
    }

    /**
     * 拍照
     */
    public void takePic() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                PackageManager.PERMISSION_DENIED  == ContextCompat.checkSelfPermission(act, android.Manifest.permission.CAMERA)) {

            showShort( "请手动开启拍照片权限");
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                PackageManager.PERMISSION_DENIED  == ContextCompat.checkSelfPermission(act, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            showShort( "请手动开启拍照片权限");
            return;
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            lastUri = FileProvider.getUriForFile(act, appId + ".fileprovider", createFile());
        } else {
            lastUri = Uri.fromFile(createFile());
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, lastUri);
        act.startActivityForResult(intent, AVATER_PIC);
    }

    /**
     * 拍照
     */
    public void takePic(int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                PackageManager.PERMISSION_DENIED  == ContextCompat.checkSelfPermission(act, android.Manifest.permission.CAMERA)) {
            showShort( "请手动开启拍照片权限");
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                PackageManager.PERMISSION_DENIED  == ContextCompat.checkSelfPermission(act, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            showShort( "请手动开启sd卡权限");
            return;
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            lastUri = FileProvider.getUriForFile(act, appId + ".fileprovider", createFile());
        } else {
            lastUri = Uri.fromFile(createFile());
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, lastUri);
        act.startActivityForResult(intent, id);
    }

    /**
     * 裁剪
     */
    public void cropePic(Uri uri, int outputX, int outputY) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);//黑边
        intent.putExtra("scaleUpIfNeeded", true);//黑边
        intent.putExtra("return-data", false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            lastUri = FileProvider.getUriForFile(act, appId + ".fileprovider", createFile());
        } else {
            lastUri = Uri.fromFile(createFile());
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, lastUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        act.startActivityForResult(intent, AVATER_CROPE);
    }

    /**
     * 裁剪
     */
    public void cropePic(Uri uri, int outputX, int outputY, int id) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);//黑边
        intent.putExtra("scaleUpIfNeeded", true);//黑边
        intent.putExtra("return-data", false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            lastUri = FileProvider.getUriForFile(act, appId + ".fileprovider", createFile());
        } else {
            lastUri = Uri.fromFile(createFile());
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, lastUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        act.startActivityForResult(intent, id);
    }

    private File createFile() {
        picFile = new File(AVATAR_PATH + "/" + System.currentTimeMillis() + ".jpg");
        return picFile;
    }


    private void deleterFile() {
        FileUtils.deleteDir(AVATAR_PATH);
    }


    public static Uri getLastUri() {
        return lastUri;
    }


    /**
     * 无裁剪后获取相册文件
     *
     * @return
     */
    public File getAlbumFile(Uri uri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = act.getContentResolver().query(uri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        File file = new File(res);

        return file;
    }

    /**
     * 剪裁完成后获取相册文件
     *
     * @return
     */
    public File getAlbumFile() {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = act.getContentResolver().query(lastUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        File file = new File(res);

        return file;
    }

    public File getPicFile() {
        return picFile;
    }
}

