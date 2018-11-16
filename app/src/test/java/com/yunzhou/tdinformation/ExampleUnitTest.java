package com.yunzhou.tdinformation;

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
        List<String> stringList = splitOpenCode("06,07,21,29,30+01,10");
        for (String s : stringList) {
            System.out.println(s);
        }
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
}