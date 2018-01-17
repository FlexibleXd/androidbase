package flexible.xd.android_base.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by flexible on 2016/7/29.
 */
public class RegexUtil {

    /**
     * 验证email
     *
     * @param str
     * @return  
     *
     * by flexible 2016/7/29
     */
    public static boolean isEmail(String str) {
        Pattern pattern = Pattern.compile("\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}");
        Matcher m = pattern.matcher(str);
        return m.matches();
    }

    /**
     * 验证手机号码
     *
     * @param mobileNumber
     * @return  
     *
     * by flexible 2016/7/29
     */
    public static boolean isPhone(String mobileNumber) {
        boolean flag = false;
        try {
            Pattern regex = Pattern.compile("(13\\d|14[57]|15[^4,\\D]|17[678]|18\\d)\\d{8}|170[059]\\d{7}");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }


}
