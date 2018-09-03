package flexible.xd.android_base.utils;

import com.alibaba.fastjson.JSON;
import java.util.ArrayList;

/**
 * Created by Flexible on 2017/1/9 0009.
 */

public class FastJsonUtils {

    public static String encode(Object body) {
        return JSON.toJSONString(body);
    }

    public static<T> T decode(String jsonMsg, Class<T> c) {
        return JSON.parseObject(jsonMsg, c);
    }

    public static Object decodeArray(String jsonMsg, Class<?> c) {

        ArrayList<Object> arrayList = (ArrayList<Object>) FastJsonUtils.decode(jsonMsg, ArrayList.class);
        return arrayList;
    }
}
