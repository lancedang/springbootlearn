package com.lance.pdfdemo.util;

import java.util.HashMap;
import java.util.Map;

public class JChartMain {
    public static void main(String[] args) {
        Map<String, Double> map = new HashMap<String, Double>();
        map.put("冠心病", (double) 1000);
        map.put("脑卒中", (double) 700);
        map.put("肺结核", (double) 600);
        map.put("糖尿病", (double) 400);
        map.put("高血压", (double) 100);
        map.put("精神病", (double) 2000);
        //JFreeChartUtil.createPiePort("慢病统计结果", map,"D:\\pdf\\aa.jpg");

        Map<String, Double> map1 = new HashMap<String, Double>();
        //设置第一期的投票信息
        map1.put("2020-02-03", (double) 700);
        map1.put("2020-02-04", (double) 1000);
        map1.put("2020-02-05", (double) 600);
        map1.put("2020-02-06", (double) 400);
        map1.put("2020-02-07", (double) 4000);
        map1.put("2020-02-08", (double) 1200);
        map1.put("2020-02-09", (double) 800);
        JFreeChartUtil.createLinePort("近7日金额(日报)", map1, "日期", "金额（元）", "D:\\pdf\\bb.jpg");


    }
}
