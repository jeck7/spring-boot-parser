package ef.parser.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ParserUtil {

    public static final String INPUT_DATE = "yyyy-MM-dd.HH:mm:ss";
    public static final String LOG_DATE = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final SimpleDateFormat INPUT_DATE_FORMAT = new SimpleDateFormat(INPUT_DATE);
    public static final SimpleDateFormat LOG_DATE_FORMAT = new SimpleDateFormat(LOG_DATE);

    public static Date addHours(Date oldDate, int hours) {
        return new Date(oldDate.getTime() + TimeUnit.HOURS.toMillis(hours));
    }

}
