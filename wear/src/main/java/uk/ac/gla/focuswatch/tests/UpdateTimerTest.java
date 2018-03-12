package uk.ac.gla.focuswatch.tests;

import junit.framework.TestCase;

import uk.ac.gla.focuswatch.util.TextTimer;

public class UpdateTimerTest extends TestCase {

    public void testIncrementTimerText() {
        String nextTimer = new TextTimer("00:00").increment();
        assertEquals("00:01", nextTimer);
        String nextTimer2 = new TextTimer("00:05").increment();
        assertEquals("00:06", nextTimer2);
        String nextTimer3 = new TextTimer("00:09").increment();
        assertEquals("00:10", nextTimer3);
        String nextTimer4 = new TextTimer("00:26").increment();
        assertEquals("00:27", nextTimer4);
        String nextTimer5 = new TextTimer("00:59").increment();
        assertEquals("01:00", nextTimer5);
        String nextTimer6 = new TextTimer("04:59").increment();
        assertEquals("05:00", nextTimer6);
        String nextTimer7 = new TextTimer("09:59").increment();
        assertEquals("10:00", nextTimer7);
        String nextTimer8 = new TextTimer("25:59").increment();
        assertEquals("26:00", nextTimer8);
        String nextTimer9 = new TextTimer("59:59").increment();
        assertEquals("1:00:00", nextTimer9);
        String nextTimer10 = new TextTimer("1:00:59").increment();
        assertEquals("1:01:00", nextTimer10);
    }

    public void testToSeconds() {
        int secondsTimer = new TextTimer("00:00").toSeconds();
        assertEquals(0, secondsTimer);
        int secondsTimer2 = new TextTimer("00:25").toSeconds();
        assertEquals(25, secondsTimer2);
        int secondsTimer3 = new TextTimer("01:00").toSeconds();
        assertEquals(60, secondsTimer3);
        int secondsTimer4 = new TextTimer("2:01:00").toSeconds();
        assertEquals(7260, secondsTimer4);
    }

}
