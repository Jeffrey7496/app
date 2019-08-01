package date;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 * LocalDate工具
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/7/15 11:20
 */
public class LocalDateUtils {

    public static void main(String[] args) {
        // 获取星期
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate localDate = LocalDate.parse("20190715",fmt);
        System.out.println(localDate.getDayOfWeek().name());

        LocalDate now = LocalDate.now();
        LocalDate date = LocalDate.of(2019,6,30);
        Period period = Period.between(now,date);
        System.out.println(period.getYears());
        System.out.println(period.getMonths());
        System.out.println(period.getDays());
    }
}
