package com.example.asus.shopping;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;

public class lovelist extends AppCompatActivity
{
    private TextView lovelist_return;

    private Context context;
    private String username;
    private String cid;

    private ListView lovelist;
    private LinkedList<fp_list_item> listdata=new LinkedList<>();


    private lovelistAdapter lovelistAdapter;
    @Override
    protected void onCreate(Bundle ins) {

        super.onCreate(ins);
        setContentView(R.layout.lovelist);

        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }

        lovelist_return=findViewById(R.id.lovelist_return);
        lovelist_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent();
                it.setClass(lovelist.this,MainActivity.class);
                it.putExtra("id",5);
                startActivity(it);

            }
        });

        lovelist=findViewById(R.id.lovelist);
        context=lovelist.this;

        username=getIntent().getStringExtra("username");

        //getdata_moni();
        getdata();
        userdata();


    }

    private void getdata_moni()
    {
        listdata.add(new fp_list_item("null","华为手机，超强待机","123",999,120,"phone"));
        listdata.add(new fp_list_item("null","最新华为手机，只需要999，即可入手","126",888,300,"computer"));

    }

    private void getdata()
    {
        //HTTP请求

        String url = "http://10.0.2.2:8080/GetFavorite";
        username="xzz";


        url=url+"?username="+username;
        //构造get链接

        System.out.println(url);
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //转到主线程再使用Toast，否则会程序崩溃
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context,"网络连接失败，请检查网络设置",Toast.LENGTH_SHORT).show();
                    }
                });
            }//连接网络失败




            /*

            此处为请求成功，解析返回的json数据

             */
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonString = response.body().string();
                try {
                    JSONObject json = new JSONObject(jsonString);
                    String flag = json.get("status").toString();

                    if(flag.equals("success")){
                        //处理json

                        //System.out.println("success------------------------");
                        //System.out.println(json.toString());
                        JSONArray values= json.getJSONArray("value");
                        System.out.println(values.toString());
                        for (int i=0;i<values.length();i++){
                            //System.out.println("xxxx"+i);
                            JSONObject jsonObject=values.getJSONObject(i);
                            // System.out.println(jsonObject.toString());


                            cid=jsonObject.getString("cid");
                            GetC();



                            //indexview.append(name + cid + category + num + price );



                        }
                    }else {
                        //转到主线程再使用Toast，否则会程序崩溃
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context,"请求数据失败",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                }catch (JSONException e){
                    System.out.println(e.toString());
                }


            }
        });




    }

    private void GetC()
    {
        //HTTP请求

        String url = "http://10.0.2.2:8080/SearchCommodity";

        final String action="ambiguous";

        url=url+"?name="+cid+"&action="+action;
        //构造get链接

        System.out.println("-----"+url);
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //转到主线程再使用Toast，否则会程序崩溃
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context,"网络连接失败，请检查网络设置",Toast.LENGTH_SHORT).show();
                    }
                });
            }//连接网络失败




            /*

            此处为请求成功，解析返回的json数据

             */
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonString = response.body().string();
                try {
                    JSONObject json = new JSONObject(jsonString);
                    String flag = json.get("status").toString();

                    if(flag.equals("success")){
                        //处理json

                        //System.out.println("success------------------------");
                        //System.out.println(json.toString());
                        JSONArray values= json.getJSONArray("value");
                        System.out.println(values.toString());
                        for (int i=0;i<values.length();i++){
                            //System.out.println("xxxx"+i);
                            JSONObject jsonObject=values.getJSONObject(i);
                            System.out.println(jsonObject.toString());
                            Integer price =jsonObject.getInt("price");
                            Integer num=jsonObject.getInt("num");
                            String category=jsonObject.getString("category");
                            String name=jsonObject.getString("name");
                            //cid=jsonObject.getString("cid");//获取json数组里的信息
                            String src=jsonObject.getString("src");

                            listdata.add(new fp_list_item(src,name,cid,price,num,category));

                            //indexview.append(name + cid + category + num + price );



                        }
                    }else {
                        //转到主线程再使用Toast，否则会程序崩溃
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context,"请求数据失败",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                }catch (JSONException e){
                    System.out.println(e.toString());
                }


            }
        });




    }

    public void userdata()
    {

        lovelistAdapter=new lovelistAdapter(context,listdata);
        lovelist.setAdapter(lovelistAdapter);
    }

}
