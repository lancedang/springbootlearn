package com.lance.boot.actuator.actuatordemo.util;

import org.kairosdb.client.Client;
import org.kairosdb.client.HttpClient;

import java.net.MalformedURLException;

public class SelfKairosdbUtil {
    public static Client getKairosdbClient() {
        try {
            HttpClient client = new HttpClient("http://fat-kairosdb.ppdaicorp.com");
            return client;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
