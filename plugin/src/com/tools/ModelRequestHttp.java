package com.tools;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

import org.apache.http.ParseException;

public class ModelRequestHttp {
    public static String sendGut(String url, Map<String, Object> map, String encoding) throws ParseException, IOException {
        String body = "";
        CloseableHttpResponse response = null;
        //client 客户端   ---------------------------- -----   // 创建默认的httpClient实例.
        CloseableHttpClient client = HttpClients.createDefault();
        //GET     - 向指定位置获取其内容---------------// 创建httpGet
        System.out.println("请求地址：" + url);
        HttpGet httpGet = new HttpGet(url);
        //StringEntity是httpGet对象的一个实现类
        httpGet.setHeader("Content-type", "application/json");
//	            httpPut.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        //客户端执行。获取数据
        response = client.execute(httpGet);
        //通过response里的getEntity()方法获取客户端的返回值，然后return进行返回；
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            body = EntityUtils.toString(entity, encoding);
        }
        //
        EntityUtils.consume(entity);
        response.close();
        return body;
    }

    public static void main(String[] args) {
        String data = null;
        try {
            data = sendGut("http://127.0.0.1/tbcnn", null, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(data);
    }
}
