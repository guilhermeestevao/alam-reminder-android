package br.com.delxmobile.alarmreminder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Guilherme on 27/07/2017.
 */

public class CalendarUtil {


    public static List<Calendar> getAllAlarms(Calendar start, Calendar end, int hours, int minutes){
        List<Calendar> times = new ArrayList<Calendar>();

        Calendar now = Calendar.getInstance();

        if(start.before(end) && now.before(end)){

            times = getAlarms(start, end, hours, minutes);
        }else{
            if(now.after(end)) {
                end.add(Calendar.DAY_OF_MONTH, 1);
                times = getAlarms(start, end, hours, minutes);
            } else if (now.equals(end)){
                end.add(Calendar.DAY_OF_MONTH, 1);
                times = getAlarms(start, end, hours, minutes);
            } else{
                start.add(Calendar.DAY_OF_MONTH, -1);
                times = getAlarms(start, end, hours, minutes);
            }
        }

        return times;
    }

    private static List<Calendar> getAlarms(Calendar start, Calendar end, int hours, int minutes){

        List<Calendar> times = new ArrayList<Calendar>();
        while(true){

            start.add(Calendar.HOUR_OF_DAY, hours);
            start.add(Calendar.MINUTE, minutes);
            Calendar current = (Calendar) start.clone();

            Calendar.getInstance();

            if(current.after(end)){
                break;
            }else{
                current.set(Calendar.SECOND, 0);
                times.add(current);
            }

        }

        return times;
    }

    public static int getNextAlarm(List<Calendar> times){

        Calendar now = Calendar.getInstance();

        for(int i = 0; i<times.size(); i++){
            Calendar current = times.get(i);
            if(current.after(now))
                return i;

        }

        return -1;
    }


}
