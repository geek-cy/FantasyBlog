package cn.fantasyblog.utils;


/**
 * @Description
 * @Author Cy
 * @Date 2021-04-14 20:07
 */
public class DateUtil {
    public static String formatDate(Integer year,Integer month,Integer day){
        if(day != null){
            return String.format("%4d-%02d-%02d",year,month,day);
        } else {
            return String.format("%4d-%02d",year,month);
        }
    }
}
