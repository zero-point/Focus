package uk.ac.gla.focuswatch.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class Date {
    /**
     * Keeping sane dates in UTC and single format
     */
    public static final SimpleDateFormat isoDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    public static final TimeZone utcTimeZone = TimeZone.getTimeZone("UTC");

    public static String utcnow() {
        /**
         * Returns string representation of the time now in UTC format.
         */
        return isoDateFormat.format(Calendar.getInstance().getTime());
    }
}
