package com.lance.datasourcedemo;


import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by qiankai02 on 2017/11/9.
 */
public class TestHelper {



    //return JSON string from json file
    public static String readSourceFile(String rs) throws Exception {
        Class<TestHelper> testHelperClass = TestHelper.class;
        ClassLoader classLoader = testHelperClass.getClassLoader();
        URI uri = classLoader.getResource(rs).toURI();
        byte[] bytes = Files.readAllBytes(Paths.get(uri));
        return new String(bytes, "UTF-8");
    }

}
