package flexible.xd.android_base.model;

/**
 * Created by Flexible on 2017/2/15 0015.
 */

public enum PermissionType {
    CONTACTS("android.permission.READ_CONTACTS", 0), PHONE("android.permission.READ_PHONE_STATE", 1), CALENDAR("android.permission.READ_CALENDAR", 2),
    CAMERA("android.permission.CAMERA", 3), SENSORS("android.permission.BODY_SENSORS", 4), LOCATION("android.permission.ACCESS_FINE_LOCATION", 5),
    STORAGE("android.permission.READ_EXTERNAL_STORAGE", 6), MICROPHONE("android.permission.RECORD_AUDIO", 7), SMS("android.permission.RECEIVE_SMS", 8);
    private String permisson;
    private int i;

    PermissionType(String permisson, int i) {
        this.permisson = permisson;
        this.i = i;
    }

    public String getPermisson() {
        return permisson;
    }

    public int getCode() {
        return i;
    }
}
