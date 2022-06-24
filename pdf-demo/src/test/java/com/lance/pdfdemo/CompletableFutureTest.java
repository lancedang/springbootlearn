package com.lance.pdfdemo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CompletableFutureTest {
    @Test
    public void test() {
        ForkJoinPool pool = new ForkJoinPool();

        Map<String, CompletableFuture<String>> maps = new HashMap<>();
        Map<String, CompletableFuture> maps2 = new HashMap<>();
        Map<String, String> urlMap = new HashMap<>();

        maps.put("field1", null);
        maps.put("field2", null);


        maps.forEach((key, value) ->
                {
                    CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
                        try {
                            log.info("threadName in supplyAsync=" + Thread.currentThread().getName());
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                        }
                        return key + "-url";
                    });
                    maps.put(key, cf);

                }
        );

        for (Map.Entry<String, CompletableFuture<String>> entry : maps.entrySet()) {
            String key = entry.getKey();
            CompletableFuture<String> value = entry.getValue();

            CompletableFuture<Void> acceptFuture = value.thenAccept((url) -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("threadName in accept=" + Thread.currentThread().getName());

                urlMap.put(key, url);
            });
            
            maps2.put(key, acceptFuture);
        }

        log.info("main-start get " + System.currentTimeMillis());
        for (Map.Entry<String, CompletableFuture> entry : maps2.entrySet()) {
            try {
                entry.getValue().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        log.info("main-end get " + System.currentTimeMillis());

        log.info(urlMap+"");

    }
}
