package com.example.asus.shopping;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;

public class commodity_list extends Activity
{
    private Context context;
    private String input;
    private EditText search_et_input;
    private Button search_btn_back;

    private ListView clist;
    private LinkedList<fp_list_item> listdata=new LinkedList<>();


    private c_listAdapter clistAdapter;

    @Override
    protected void onCreate(Bundle saveInce)
    {

        super.onCreate(saveInce);
        setContentView(R.layout.commodity_list);

        Intent intent=getIntent();
        input=intent.getStringExtra("input");

        initID();
        //getdata_moni();
        getdata();
        userdata();


    }

    private void initID()
    {
        context=commodity_list.this;

        search_et_input=findViewById(R.id.search_et_input);
        search_btn_back=findViewById(R.id.search_btn_back);

        clist=findViewById(R.id.c_list);
    }

    public void getdata()
    {
        //HTTP请求
        String url = "http://10.0.2.2:8080/SearchCommodity";
        final String cname = "quanyao";
        String action="ambiguous";

        if(input.isEmpty()){
            action="all";
            url=url+"?"+"action="+action;
        }else{
            action="ambiguous";
            url=url+"?name="+input+"&action="+action;
        }




        //构造get链接

        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e)
            {/*
                //转到主线程再使用Toast，否则会程序崩溃
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context,"网络连接失败，请检查网络设置",Toast.LENGTH_SHORT).show();
                    }
                });*/

                Toast.makeText(context,"网络连接失败，请检查网络设置",Toast.LENGTH_SHORT).show();
            }//连接网络失败




            /*

            此处为请求成功，解析返回的json数据

             */
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonString = response.body().string();
                try {
                    JSONObject a = new JSONObject(jsonString);
                    String flag = a.get("status").toString();

                    if(flag.equals("success"))
                    {
                        JSONArray jsonArray=a.getJSONArray("value");
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject object=jsonArray.getJSONObject(i);

                            String cid=object.getString("cid");
                            int num=object.getInt("num");
                            int price=object.getInt("price");
                            String category=object.getString("category");
                            String name=object.getString("name");
                            String src=object.getString("src");

                            listdata.add(new fp_list_item(src,name,cid,price,num,category));

                        }



                    }else {
                        /*
                        //转到主线程再使用Toast，否则会程序崩溃
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AddressNew.this,"保存失败",Toast.LENGTH_SHORT).show();
                            }
                        });*/
                        Toast.makeText(context,"请求失败",Toast.LENGTH_SHORT).show();

                    }

                }catch (JSONException e){
                    System.out.println(e.toString());
                }


            }
        });


    }


    private void getdata_moni()
    {
        listdata.add(new fp_list_item("null","华为手机，超强待机","123",999,120,"phone"));
        listdata.add(new fp_list_item("null","最新华为手机，只需要999，即可入手","126",888,300,"computer"));

    }

    public void userdata()
    {

        clistAdapter=new c_listAdapter(context,listdata);
        clist.setAdapter(clistAdapter);
    }



}
