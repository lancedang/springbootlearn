package com.lance.spring.springinitstage.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileDownloader {

    public static void downloadFile(String fileUrl, String savePath) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(fileUrl);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    try (InputStream inputStream = entity.getContent();
                         FileOutputStream fos = new FileOutputStream(savePath)) {
                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            fos.write(buffer, 0, bytesRead);
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        String fileUrl = "http://music.163.com/song/media/outer/url?id=2611342129.mp3"; // 替换为实际的文件URL
        String savePath = "C:\\Users\\weicong\\Music\\wuyinyangsheng\\1.mp3" ; // 替换为实际的保存路径

        try {
            downloadFile(fileUrl, savePath);
            System.out.println("File downloaded successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}