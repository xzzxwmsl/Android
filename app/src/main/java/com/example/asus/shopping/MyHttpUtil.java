package com.example.asus.shopping;



import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by lsk on 2017/3/9.
 */

public class MyHttpUtil {
    String str="";
    public String httpGet(String url, HashMap<String,String> paramsMap){
        OkHttpClient okHttpClient=new OkHttpClient();

        FormBody.Builder builder = new FormBody.Builder();
        for (String key : paramsMap.keySet()) {
            //追加表单信息
            builder.add(key, paramsMap.get(key));
        }

        RequestBody requestBody=builder.build();

        Request request = new Request.Builder()
                .url(url) //url
                .post(requestBody)
                .build();

        Call call=okHttpClient.newCall(request);
        //同步GET请求
        try {
            Response response=call.execute();
            if(!response.isSuccessful()) {
                System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
                Headers responseHeaders = response.headers();
                for (int i = 0; i < responseHeaders.size(); i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }
            }
            str= response.body().string();
            System.out.println("___________-------"+str);
        }catch (Exception e){
            e.printStackTrace();
        }

        //异步GET请求
        /*call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                str=response.body().toString();
            }
        });*/
        return str;
    }
}