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
import java.util.List;

public class Addresslist extends AppCompatActivity
{
    private TextView addlist_tv_return;
    private TextView addlist_tv_new;
    private ListView addlist_list;
    private Context context;
    private addresslistAdapter addAdapter=null;
    private List<Addlist_item> mdata=null;

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_list);

        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }

        initID();
        //getaddlist_moni();
        getaddlist();
        userdata();

    }

    private void initID()
    {
        addlist_tv_return=findViewById(R.id.addlist_return);
        addlist_tv_new=findViewById(R.id.addlist_add);
        context=Addresslist.this;
        addlist_list=findViewById(R.id.addlist);

        mdata=new LinkedList<Addlist_item>();

        username=getIntent().getStringExtra("username");







        addlist_tv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(Addresslist.this,MainActivity.class);
                intent.putExtra("id",5);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });

        addlist_tv_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(Addresslist.this,AddressNew.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });
    }

    private void getaddlist_moni()
    {

        mdata.add(new Addlist_item("gaoyi","101","bugaosan"));
        mdata.add(new Addlist_item("yigao","102","changqiao"));

    }

    private void getaddlist()
    {
            //HTTP请求

            String url = "http://10.0.2.2:8080/GetAddress";

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
                                String name=jsonObject.getString("username");
                                String Address=jsonObject.getString("address");
                                String tel=jsonObject.getString("tel");

                                mdata.add(new Addlist_item(name,tel,Address));



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



    private void userdata()
    {
        addAdapter=new addresslistAdapter((LinkedList<Addlist_item>)mdata,context,false);
        addlist_list.setAdapter(addAdapter);
    }



}
