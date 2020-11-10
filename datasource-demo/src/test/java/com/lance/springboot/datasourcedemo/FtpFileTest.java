// Copyright (C) 2020 Meituan
// All rights reserved
package com.lance.springboot.datasourcedemo;

import org.junit.Test;

/**
 * @author qiankai07
 * @version 1.0
 * Created on 3/30/20 5:52 PM
 **/
public class FtpFileTest {

    @Test
    public void test() {
        try {
            String s = TestHelper.readSourceFile("mapping.csv");

            System.out.println(s);
            String[] split = s.split("\n");

            System.out.println(split[0]);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}