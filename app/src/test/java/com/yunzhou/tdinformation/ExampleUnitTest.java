package com.yunzhou.tdinformation;

import android.annotation.SuppressLint;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        String s = "Thursday Aug 1,16:28";
        String today = s.replaceFirst("\\w+.|\\s", "Today.");
        System.out.println(s);
        System.out.println(today);
    }

    private List<String> splitOpenCode(String openCode) {
        int redPos = openCode.indexOf("+");
        List<String> stringList = new ArrayList<>(2);
        String redCode;
        String blueCode = null;
        if (redPos != -1) {
            // 说明有蓝球
            redCode = openCode.substring(0, redPos);
            blueCode = openCode.substring(redPos + 1, openCode.length());
        } else {
            // 没有蓝球
            System.out.println(openCode);
            redCode = openCode;
        }
        if (redCode != null && redCode.length() != 0) {
            stringList.add(redCode);
        }
        if (blueCode != null && blueCode.length() != 0) {
            stringList.add(blueCode);
        }
        return stringList;
    }

    @SuppressLint("DefaultLocale")
    public static String formatDuration(int value) {
        int hours = value / 3600;
        int minutes = value % 3600 / 60;
        int seconds = value % 3600 % 60;
        String fHours = String.format("%02d", hours);
        String fMinutes = String.format("%02d", minutes);
        String fSeconds = String.format("%02d", seconds);

        if (fHours.equals("00"))
            fHours = "0";
        else if (fHours.startsWith("0")) {
            fHours = fHours.substring(1);
        }
        return fHours + ":" + fMinutes + ":" + fSeconds;
    }
}