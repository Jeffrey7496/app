package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * TODO 哥们，写点东西吧
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/10/22 9:24
 */
public class CommonUtil {
    public static String getCurrentTimeByDayWithNoPattern(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd",Locale.CHINA);
        return sdf.format(new Date());
    }

    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
