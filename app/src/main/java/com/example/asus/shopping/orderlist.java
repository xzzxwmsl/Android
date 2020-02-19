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
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class orderlist extends AppCompatActivity implements View.OnClickListener
{
    private TextView orderlist_tv_1;
    private TextView orderlist_tv_2;
    private TextView orderlist_tv_3;
    private TextView orderlist_tv_4;
    private TextView orderlist_tv_return;
    private ListView orderlist;
    private orderlistAdapter orderAdapter;
    private LinkedList<order_item> orderdata=new LinkedList<order_item>();
    private LinkedList<order_item_citem> order_cdata=new LinkedList<order_item_citem>();
    private Context context;
    private String username;
    private String src="";
    private Integer price=2000;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderlist);

        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }

        orderlist_tv_1=findViewById(R.id.orderlist_tv_1);
        orderlist_tv_2=findViewById(R.id.orderlist_tv_2);
        orderlist_tv_3=findViewById(R.id.orderlist_tv_3);
        orderlist_tv_4=findViewById(R.id.orderlist_tv_4);
        orderlist_tv_return=findViewById(R.id.orderlist_return);
        orderlist=findViewById(R.id.orderlist);
        context=orderlist.this;
        username=getIntent().getStringExtra("username");

        orderlist_tv_1.performClick();

        orderlist_tv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent();
                it.setClass(orderlist.this,MainActivity.class);
                it.putExtra("id",5);
                startActivity(it);
            }
        });

        orderlist_tv_1.setOnClickListener(this);
        orderlist_tv_2.setOnClickListener(this);
        orderlist_tv_3.setOnClickListener(this);
        orderlist_tv_4.setOnClickListener(this);



    }

    private void setSelected(){
        orderlist_tv_1.setSelected(false);
        orderlist_tv_2.setSelected(false);
        orderlist_tv_3.setSelected(false);
        orderlist_tv_4.setSelected(false);

    }

    @Override
    public void onClick(View view)
    {

        switch (view.getId())
        {
            case R.id.orderlist_tv_1:
                setSelected();
                orderlist_tv_1.setSelected(true);
                getorderlist("all");
                Toast.makeText(orderlist.this,"全部",Toast.LENGTH_LONG).show();
                break;
            case R.id.orderlist_tv_2:
                setSelected();
                orderlist_tv_2.setSelected(true);
                getorderlist("paying");
                //Toast.makeText(orderlist.this,"待付款",Toast.LENGTH_LONG).show();
                break;
            case R.id.orderlist_tv_3:
                setSelected();
                orderlist_tv_3.setSelected(true);
                getorderlist("payed");
                //Toast.makeText(orderlist.this,"待收货",Toast.LENGTH_LONG).show();
                break;
            case R.id.orderlist_tv_4:
                setSelected();
                orderlist_tv_4.setSelected(true);
                getorderlist("finished");
               // Toast.makeText(orderlist.this,"待评价",Toast.LENGTH_LONG).show();
                break;

        }
    }


    private void getdata() {

        //HTTP请求

        String url = "http://10.0.2.2:8080/GetOrder";

        username="xzz";

        url = url + "?username=" + username;
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
                        Toast.makeText(context, "网络连接失败，请检查网络设置", Toast.LENGTH_SHORT).show();
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

                        System.out.println(json.toString());

                        JSONArray values= json.getJSONArray("value");

                        System.out.println(values.toString());
                        for (int i=0;i<values.length();i++){ //values 里每一项都是一个jsonobject，一次循环处理一个订单
                            //System.out.println("xxxx"+i);
                            JSONObject jsonObject=values.getJSONObject(i);//得到该用户第 i  个订单

                            String orderid=jsonObject.getString("orderid");
                            String username=jsonObject.getString("username");
                            String tel=jsonObject.getString("tel");//获取json数组里的信息
                            String address=jsonObject.getString("address");
                            String status = jsonObject.getString("status");

                            JSONObject commodityinfo = jsonObject.getJSONObject("commodityinfo");// 商品信息是一个jsonobject

                            //遍历

                            Iterator<String> keys = commodityinfo.keys();

                            String [] CID =new  String[10];//商品ID
                            String [] NUM =new String[10];//商品数量  ， 与ID一一对应

                            int index=0;
                            while(keys.hasNext()){
                                CID[index]=(String) keys.next();
                                NUM[index]=commodityinfo.getString(CID[index]);
                                index++;
                            }

                            LinkedList<order_item_citem> ordercdata=new LinkedList<order_item_citem>();

                            for(int j=0;j<index;j++){
                                getCom(CID[j]);

                                System.out.println("++++"+price);
                                ordercdata.add(new order_item_citem(src,CID[j],price,Integer.parseInt(NUM[j])));
                            }

//                            order_cdata.add(new order_item_citem(null,"orange",300,99));
//                            order_cdata.add(new order_item_citem(null,"apple",200,5));
                            Addlist_item add;
                            add=new Addlist_item("cxk","123456789","china");

                            orderdata.add(new order_item(123,9999,3,status,ordercdata,null));

                            //获得到了，输出到文字框里，做示范

                            //updateText(orderid,status,address,CID,NUM,index); //必须重写一个函数来切换到主线程再更新UI，否则会使app崩溃

                            //一个订单处理完毕

                        }



                    }else {
                        //转到主线程再使用Toast，否则会程序崩溃
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context,"error",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                }catch (JSONException e){
                    System.out.println(e.toString());
                }

            }
        });


    }

    private HashMap<String, String> getCom(String cid) {

        LinkedList<order_item_citem> ordercdata=new LinkedList<order_item_citem>();

            //HTTP请求

            String url = "http://10.0.2.2:8080/SearchCommodity";

            final HashMap<String, String> hashMap =new HashMap<>();

            final String action="ambiguous";

            url=url+"?name="+cid+"&action="+action;
            //构造get链接

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
                            for (int i=0;i<values.length();i++){
                                //System.out.println("xxxx"+i);
                                JSONObject jsonObject=values.getJSONObject(i);
                                System.out.println(jsonObject.toString());
                                hashMap.put("src",jsonObject.getString("src"));
                                hashMap.put("price",jsonObject.getString("price"));
                                src=jsonObject.getString("src");
                                price=jsonObject.getInt("price");
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


            System.out.println(src+"___"+price);


        return hashMap;
    }


    private void getorderdata()
    {
        orderdata=new LinkedList<order_item>();
        order_cdata=new LinkedList<order_item_citem>();

        order_cdata.add(new order_item_citem(null,"orange",300,99));
        order_cdata.add(new order_item_citem(null,"apple",200,5));
        Addlist_item add;
        add=new Addlist_item("cxk","123456789","china");

        orderdata.add(new order_item(1230,9999,3,"paying",order_cdata,null));
        orderdata.add(new order_item(1230,9999,3,"payed",order_cdata,null));
        orderdata.add(new order_item(1230,9999,3,"finished",order_cdata,null));
        orderdata.add(new order_item(1230,9999,3,"finished",order_cdata,null));


    }

    private void getorderlist(String select)
    {
        //getorderdata();
        getdata();
        LinkedList<order_item> orderdata_fanal=null;
        if(select=="all")
        {
            orderdata_fanal=orderdata;
        }
        else
        {
            orderdata_fanal = new LinkedList<>();
            //orderdata_fanal=null;
            //orderdata_fanal=orderdata;
            for(int i=0;i<orderdata.size();i++)
            {


                order_item order=orderdata.get(i);

                if(order.getStatus().equals(select))
                {



                    orderdata_fanal.add(order);

                }
            }
        }

        if(orderdata_fanal==null)
        {




            Toast.makeText(orderlist.this,"1111111评价",Toast.LENGTH_LONG).show();
        }
        else {

            orderAdapter = new orderlistAdapter(context, orderdata_fanal);
            orderlist.setAdapter(orderAdapter);
        }


    }


}
