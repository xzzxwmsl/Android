package com.example.asus.shopping;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class AddressNew extends AppCompatActivity implements View.OnClickListener
{
    private TextView addnew_tv_return;
    private TextView addnew_tv_save;
    private EditText addnew_et_name;
    private EditText addnew_et_phone;
    private EditText addnew_et_add;

    public String name=null;
    public String phone=null;
    public String address=null;


    @Override
    protected void onCreate(Bundle saveInce)
    {
        super.onCreate(saveInce);
        setContentView(R.layout.address_new);

        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }

        addnew_tv_return=findViewById(R.id.addnew_return);
        addnew_tv_save=findViewById(R.id.addnew_save);
        addnew_et_name=findViewById(R.id.addnew_name);
        addnew_et_phone=findViewById(R.id.addnew_phone);
        addnew_et_add=findViewById(R.id.addnew_add);


        addnew_tv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent();
                intent.setClass(AddressNew.this,Addresslist.class);
                Toast.makeText(AddressNew.this, "请填充完信息", Toast.LENGTH_LONG);

                startActivity(intent);

            }
        });

        name=addnew_et_name.getText().toString();
        phone=addnew_et_phone.getText().toString();
        address=addnew_et_add.getText().toString();

        addnew_tv_save.setOnClickListener(this);
        addnew_et_name.setOnClickListener(this);
        addnew_et_phone.setOnClickListener(this);
        addnew_et_add.setOnClickListener(this);






    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.addnew_save:
                save_newaddress();//保存上传地址
                //Intent it=new Intent(AddressNew.this,Addresslist.class);
                //startActivity(it);
                break;
        }
    }

    private void save_newaddress()
    {

        //HTTP请求

        String url = "http://10.0.2.2:8080/Address";
        final String name = addnew_et_name.getText().toString();
        final String phone =addnew_et_phone.getText().toString();
        final String add=addnew_et_add.getText().toString();
        final String action="add";

        url=url+"?username="+name+"&tel="+phone+"&address="+add+"&action="+action;
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
                        Toast.makeText(AddressNew.this,"网络连接失败，请检查网络设置",Toast.LENGTH_SHORT).show();
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

                    if(flag.equals("success")){

                        //长久化保存，无需再次登录

                        Intent intent = new Intent();

                        intent.putExtra("name",name);
                        intent.setClass(AddressNew.this,Addresslist.class);
                        startActivity(intent);
                        finish();
                    }else {
                        //转到主线程再使用Toast，否则会程序崩溃
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AddressNew.this,"保存失败",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                }catch (JSONException e){
                    System.out.println(e.toString());
                }


            }
        });

    }



}
