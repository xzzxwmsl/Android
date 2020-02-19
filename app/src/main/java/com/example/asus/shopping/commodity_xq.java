package com.example.asus.shopping;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class commodity_xq extends AppCompatActivity implements View.OnClickListener
{
    private ImageView cxq_img;
    private TextView cxq_price;
    private TextView cxq_name;
    private TextView cxq_num;
    private TextView cxq_love;
    private Button cxq_btn_add;
    private Context context;


    private String username;
    private String cid;

    @Override
    protected void onCreate(Bundle saveInce) {
        super.onCreate(saveInce);
        setContentView(R.layout.commodity_xq);

        Init();//绑定id
        aboutIntent();//处理传过来的Intent；
        GetC();//获取图片啥的


    }

    private void Init()
    {
        cxq_img=findViewById(R.id.c_xq_img);
        cxq_price=findViewById(R.id.c_xq_price);
        cxq_name=findViewById(R.id.c_xq_name);
        cxq_num=findViewById(R.id.c_xq_num);//商品图像，价格，描述，库存

        cxq_love=findViewById(R.id.c_xq_love);
        cxq_btn_add=findViewById(R.id.c_xq_add);//添加收藏，添加购物车

        context=commodity_xq.this;

        cxq_love.setOnClickListener(this);
        cxq_btn_add.setOnClickListener(this);
    }

    private void aboutIntent()
    {
        username=getIntent().getStringExtra("username");
        username="xzz";
        cid=getIntent().getStringExtra("cid");

    }

    private void GetC()
    {
        //HTTP请求

        String url = "http://10.0.2.2:8080/SearchCommodity";

        final String action="ambiguous";

        url=url+"?name="+cid+"&action="+action;
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
                            System.out.println(jsonObject.toString());
                            Integer price =jsonObject.getInt("price");
                            Integer num=jsonObject.getInt("num");
                            String category=jsonObject.getString("category");
                            String name=jsonObject.getString("name");
                            //cid=jsonObject.getString("cid");//获取json数组里的信息
                            String src=jsonObject.getString("src");

                            updateImage(src);
                            cxq_price.setText(price+"");
                            cxq_name.setText(name);
                            cxq_num.setText(num+"");

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


    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.c_xq_love:
                addlove();//向服务器中收藏列表添加
                break;

            case R.id.c_xq_add:
                addshopcar();//添加到购物车中，本地Intent处理；
                break;
        }

    }

    private void addlove()
    {

        //HTTP请求

        String url = "http://10.0.2.2:8080/MakeFavorite";

        //final String action="add";

        url=url+"?username="+username+"&cid="+cid;
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
                    JSONObject a = new JSONObject(jsonString);
                    String flag = a.get("status").toString();

                    if(flag.equals("success"))
                    {
                        cxq_love.setSelected(true);




                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context,"保存成功",Toast.LENGTH_SHORT).show();
                            }
                        });


                    }else {
                        //转到主线程再使用Toast，否则会程序崩溃
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context,"保存失败",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                }catch (JSONException e){
                    System.out.println(e.toString());
                }


            }
        });


    }

    private void addshopcar()
    {
        //HTTP请求

        String url = "http://10.0.2.2:8080/UpdateCart";

        final String action="add";
        //final String action="add";
        username="xzz";
        url=url+"?username="+username+"&cid="+cid+"&action="+action;
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
                    JSONObject a = new JSONObject(jsonString);
                    String flag = a.get("status").toString();

                    if(flag.equals("success"))
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context,"添加成功",Toast.LENGTH_SHORT).show();
                            }
                        });





                    }else {
                        //转到主线程再使用Toast，否则会程序崩溃
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context,"添加失败",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                }catch (JSONException e){
                    System.out.println(e.toString());
                }


            }
        });
    }

    public void updateImage(final String src)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide.with(context).load("http://10.0.2.2:8080/"+src).into(cxq_img);
            }
        });
    }
}
