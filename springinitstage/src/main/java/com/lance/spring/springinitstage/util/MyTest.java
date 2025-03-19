package com.lance.spring.springinitstage.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.time.Clock;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class MyTest {

    @Test
    public void ttt() throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 1000, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100));
        executor.submit(new Runnable() {

            public void run() {
                System.out.println("任务1");
            }
        });

        TimeUnit.MINUTES.sleep(5);

        "".substring(0, 1);

        int activeCount = executor.getActiveCount();
        System.out.println("activeCount=" + activeCount);

        executor.execute(() -> System.out.println("任务2"));

        TimeUnit.HOURS.sleep(1);


    }

    @Test
    public void test() throws Exception {


        String url = "https://music.wandhi.com/";

        String ddd = "qingyiny";

        Map<String, String> map = new HashMap<>();
        map.put("input", "轻音乐");
        map.put("filter", "name");
        map.put("type", "netease");

        ExecutorService executorService = new ThreadPoolExecutor(50, 50, 1000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(100));
        //不能设置i<3，这个3不能太多页数，否则报错
        for (int i = 1; i < 100; i++) {

            int finalI = i;
//            executorService.submit(new Runnable() {
//                @Override
//                public void run() {
            try {
                map.put("page", String.valueOf(i));

                String s = HttpClientUtil.doPost(url, map, "UTF-8");
                System.out.println("result is " + s);

                MusicData musicData = JSonUtils.jsonToObject(s, MusicData.class);

                if (musicData.data.isEmpty()) {
                    System.out.println("已经为空，跳出循环");
                    return;
                }

                for (MusicItem musicItem : musicData.data) {
                    String title = musicItem.title;
                    String downloadUrl = musicItem.url;
                    String songid = musicItem.songid;


                    String filePath = "C:\\Users\\weicong\\Music\\" + ddd + "\\" + title + ".mp3";
                    String filePath2 = "C:\\Users\\weicong\\Music\\" + ddd + "\\" + songid + ".mp3";

                    try {
                        FileDownloader.downloadFile(downloadUrl, filePath);
                    } catch (IOException e) {
                        FileDownloader.downloadFile(downloadUrl, filePath2);
                    }

                }
            } catch (Exception e) {
                System.out.println("跳过异常:" + e.getMessage());
            }

            System.out.println("处理完 第 " + i+  " 页");

//                }

//            });
//        }

            //TimeUnit.HOURS.sleep(1);

        }



    }
}
