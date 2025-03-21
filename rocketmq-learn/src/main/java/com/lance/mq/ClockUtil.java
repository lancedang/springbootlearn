package com.lance.mq;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ClockUtil {
    public static long now() {
        return System.currentTimeMillis();
    }

    public static void sleep(int time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        List<String> list = Lists.newArrayList();
        list.get(0);
    }
}
