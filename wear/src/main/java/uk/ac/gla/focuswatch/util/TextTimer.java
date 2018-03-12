package uk.ac.gla.focuswatch.util;

import android.widget.TextView;

import java.util.TimerTask;

public class TextTimer {
    private String timerValue;

    public TextTimer(String timerValue) {
        this.timerValue = timerValue;
    }

    private void updateTimerValue(String timerValue) {
        this.timerValue = timerValue;
    }

    public String increment() {
        /**
         * Increments a timer text (MM:SS) to the next second. Overflows to minutes.
         * If minutes overflow, add an hour in front (H:MM:SS)
         */
        int prevHours, prevMinutes, prevSeconds;
        String[] timerValues = timerValue.split(":");
        // check if we have an hour
        if (timerValues.length > 2) {
            // yes
            prevHours = Integer.parseInt(timerValues[0]);
            prevMinutes = Integer.parseInt(timerValues[1]);
            prevSeconds = Integer.parseInt(timerValues[2]);
        } else {
            // no
            prevHours = 0;
            prevMinutes = Integer.parseInt(timerValues[0]);
            prevSeconds = Integer.parseInt(timerValues[1]);
        }
        int nextSeconds = prevSeconds + 1;
        int nextMinutes = prevMinutes;
        int nextHours = prevHours;
        // are we overflowing?
        if (nextSeconds > 59) {
            nextSeconds = 0;
            nextMinutes = prevMinutes + 1;
            if (nextMinutes > 59) {
                nextMinutes = 0;
                nextHours = prevHours + 1;
            }
        }
        String timerNextSeconds = nextSeconds < 10 ? "0" + nextSeconds : nextSeconds + "";
        String timerNextMinutes = nextMinutes < 10 ? "0" + nextMinutes + ":" : nextMinutes + ":";
        String timerNextHours = nextHours == 0 ? "" : nextHours + ":";
        String newValue = timerNextHours + timerNextMinutes + timerNextSeconds;
        updateTimerValue(newValue);
        return newValue;
    }

    public int toSeconds() {
        int hours, minutes, seconds;
        String[] timerValues = timerValue.split(":");
        if (timerValues.length > 2) {
            // yes
            hours = Integer.parseInt(timerValues[0]);
            minutes = Integer.parseInt(timerValues[1]);
            seconds = Integer.parseInt(timerValues[2]);
        } else {
            // no
            hours = 0;
            minutes = Integer.parseInt(timerValues[0]);
            seconds = Integer.parseInt(timerValues[1]);
        }
        return hours*3600 + minutes*60 + seconds;
    }

}