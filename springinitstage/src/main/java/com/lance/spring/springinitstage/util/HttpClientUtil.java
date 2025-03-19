package com.lance.spring.springinitstage.util;

import java.util.ArrayList;
import java.util.Iterator;  
import java.util.List;  
import java.util.Map;  
import java.util.Map.Entry;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;  
import org.apache.http.NameValuePair;  
import org.apache.http.client.HttpClient;  
import org.apache.http.client.entity.UrlEncodedFormEntity;  
import org.apache.http.client.methods.HttpPost;  
import org.apache.http.message.BasicNameValuePair;  
import org.apache.http.util.EntityUtils;  
/* 
 * 利用HttpClient进行post请求的工具类 
 */
@Slf4j
public class HttpClientUtil {  
    public static String doPost(String url,Map<String,String> map,String charset){
        HttpClient httpClient = null;  
        HttpPost httpPost = null;  
        String result = null;  
        try{  
            httpClient = new SSLClient();


            httpPost = new HttpPost(url);  
            //设置参数  
            List<NameValuePair> list = new ArrayList<NameValuePair>();  
            Iterator iterator = map.entrySet().iterator();  
            while(iterator.hasNext()){  
                Entry<String,String> elem = (Entry<String, String>) iterator.next();  
                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));  
            }  
            if(list.size() > 0){  
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);  
                httpPost.setEntity(entity);  
            }

            //加入2行header
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            httpPost.setHeader("x-requested-with", "XMLHttpRequest");
            httpPost.setHeader("cookie", "Hm_lvt_721634f5f67c2558a5dca6daaefb2f2b=1740035453,1740384493; Hm_lpvt_721634f5f67c2558a5dca6daaefb2f2b=1740384493; HMACCOUNT=C06D43BD305D3CA8; __gads=ID=50911347d8907b33:T=1735025974:RT=1740384495:S=ALNI_MZd-o7PY8bHkE_QVQe10GsA9Iwa8Q; __gpi=UID=00000fb54e4632d3:T=1735025974:RT=1740384495:S=ALNI_MYO9M5jBjJfH9RSUz_0mI5O2GU1Ew; __eoi=ID=0d5abdc232ec63d7:T=1735025974:RT=1740384495:S=AA-AfjajcBG2RFQGGpCzvBERql1J; FCNEC=%5B%5B%22AKsRol-jM4AODbfYEYYCLzTmbGD64wk56NLly8VM3niucWqR_JUtzky7bLHAeiEp_8rx1z-ghE96QUUT4PxLAq5bVqeZFVDGGw_udTtbpOgSDazsFFMbMZfXMKie2PhJNOhMie2n_9nXVh9GK5nIZRdfEXOPgLPosQ%3D%3D%22%5D%5D");
            httpPost.setHeader("accept", "application/json, text/javascript, */*; q=0.01");
            httpPost.setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36");

            HttpResponse response = httpClient.execute(httpPost);
            if(response != null){  
                HttpEntity resEntity = response.getEntity();  
                if(resEntity != null){  
                    result = EntityUtils.toString(resEntity,charset);  
                }  
            }  
        }catch(Exception ex){  
              log.error("查找分页信息失败：", ex);
        }  
        return result;  
    }  
}  