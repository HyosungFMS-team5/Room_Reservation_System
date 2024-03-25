package datecalc.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateCalc {
	// 체크할 날짜 리스트 return
    public static List<String> getDateRange(Date startDate, int dateDiff) {
        List<String> dateList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (int i = 0; i < dateDiff; i++) {
            String dateString = dateFormat.format(calendar.getTime());
            dateList.add(dateString);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        return dateList;
    }
	
	// 체크인 체크아웃 날짜의 차 계산
    public static int calcDateDiff(Date startDate, Date endDate) {
        long differenceInMilliseconds = endDate.getTime() - startDate.getTime();
        long differenceInDays = differenceInMilliseconds / (1000 * 60 * 60 * 24);
        return (int) differenceInDays; 
    }
}
