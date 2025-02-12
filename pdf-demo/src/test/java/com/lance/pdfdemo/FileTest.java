package com.lance.pdfdemo;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

@Slf4j
public class FileTest {
    public static void main(String[] args) throws IOException {
        File file = new File("D:\\11.log");
        if (!file.exists()) {
            log.info("not exist");
            file.createNewFile();
        }

        String name = "22.log";
        File file2 = new File("D:\\" + name);
        if (!file2.exists()) {
            log.info("not exits2");
            file2.createNewFile();
        }
    }
}
