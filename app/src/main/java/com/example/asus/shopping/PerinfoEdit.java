package com.example.asus.shopping;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class PerinfoEdit extends AppCompatActivity implements View.OnClickListener
{
    private TextView perinfo_return;
    private TextView perinfo_save;//保存于返回

    private CircleImageView perinfo_img;
    private TextView perinfo_img_edit;//头像

    private TextView perinfo_name;
    private TextView perinfo_phone;
    private TextView perinfo_sex;//名字、号码、性别文本框

    private TextView perinfo_phone_edit;
    private TextView perinfo_sex_edit;//修改点击

    private Context context;
    private String username;

    @Override
    protected void onCreate(Bundle ins)
    {
        super.onCreate(ins);
        setContentView(R.layout.personalinfo);
        initview();//找id

        Intent intent=getIntent();
        username = intent.getStringExtra("username");
       // username="xzz";
        //username="gaoyi";
        get_perinfo(username);
    }

    private void initview()
    {
        perinfo_return=findViewById(R.id.perinfo_return);
        perinfo_save=findViewById(R.id.perinfo_save);

        perinfo_img=findViewById(R.id.perinfo_img);
        perinfo_img_edit=findViewById(R.id.perinfo_img_edit);

        perinfo_name=findViewById(R.id.perinfo_name);
        perinfo_phone=findViewById(R.id.perinfo_phone);
        perinfo_sex=findViewById(R.id.perinfo_sex);

        perinfo_phone_edit=findViewById(R.id.perinfo_phone_edit);
        perinfo_sex_edit=findViewById(R.id.perinfo_sex_edit);

        context=PerinfoEdit.this;

        perinfo_return.setOnClickListener(this);
        perinfo_save.setOnClickListener(this);
        perinfo_img_edit.setOnClickListener(this);
        perinfo_phone_edit.setOnClickListener(this);
        perinfo_sex_edit.setOnClickListener(this);

    }

    private void get_perinfo(final String username)
    {



            /*
            使用POST提交
             */

        OkHttpClient okHttpClient = new OkHttpClient();
        //构造提交表单



        HashMap<String,String> paramsMap=new HashMap<>();
        paramsMap.put("username",username);

        FormBody.Builder builder = new FormBody.Builder();
        for (String key : paramsMap.keySet()) {
            //追加表单信息
            builder.add(key, paramsMap.get(key));
        }

        RequestBody requestBody=builder.build();
        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/GetUserInfo")
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context,"网络连接失败，请检查网络设置",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String jsonString = response.body().string();
                try {
                    JSONObject json = new JSONObject(jsonString);
                    String flag = json.get("status").toString();

                    if(flag.equals("success")){
                        //处理json


                        JSONObject values= json;
                        //System.out.println(values.toString());
                        for (int i=0;i<1;i++){
                            //System.out.println("xxxx"+i);
                            //JSONObject jsonObject=values.getJSONObject(i);
                            JSONObject jsonObject=values;
                            //System.out.println(jsonObject.toString());
                            String phone=jsonObject.getString("tel");
                            String sex=jsonObject.getString("sex");

                            perinfo_name.setText(username);
                            perinfo_phone.setText(phone);
                            perinfo_sex.setText(sex);
                            //获取json数组里的信息

                            //indexview.append(name + cid + category + num + price );

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


    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.perinfo_return:
                Intent it=new Intent(context,MainActivity.class);
                it.putExtra("id",5);
                startActivity(it);
                break;
            case R.id.perinfo_save:
                save_perinfo();
                break;
            case R.id.perinfo_img_edit:
                break;
            case R.id.perinfo_phone_edit:
                phonePopwindow(view);
                break;
            case R.id.perinfo_sex_edit:
                sexPopwindow(view);
                break;

        }
    }

    private void save_perinfo()
    {
        //HTTP请求

        String url = "http://10.0.2.2:8080/UpdateUser";
        final String username = perinfo_name.getText().toString();
        final String tel =perinfo_phone.getText().toString();
        final String sex=perinfo_sex.getText().toString();
        //final String action="add";

        url=url+"?username="+username+"&tel="+tel+"&sex="+sex;
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
                        Toast.makeText(PerinfoEdit.this,"网络连接失败，请检查网络设置",Toast.LENGTH_SHORT).show();
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



                        Intent intent = new Intent();
                        intent.putExtra("id",5);
                        intent.putExtra("username",username);
                        intent.setClass(PerinfoEdit.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        //转到主线程再使用Toast，否则会程序崩溃
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(PerinfoEdit.this,"保存失败",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                }catch (JSONException e){
                    System.out.println(e.toString());
                }


            }
        });

    }

    private void phonePopwindow(View view)
    {
        final Context mContext=PerinfoEdit.this;
        view = LayoutInflater.from(mContext).inflate(R.layout.pop_phoneedit, null, false);
        Button btn_cancel = (Button) view.findViewById(R.id.newphone_cancel);
        Button btn_confirm = (Button) view.findViewById(R.id.newphone_confirm);
        final EditText et_newphone=view.findViewById(R.id.newphone_phone);
        et_newphone.setText(null);
        //1.构造一个PopupWindow，参数依次是加载的View，宽高
        final PopupWindow popWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        //popWindow.setAnimationStyle(R.anim.anim_pop);  //设置加载动画

        //这些为了点击非PopupWindow区域，PopupWindow会消失的，如果没有下面的
        //代码的话，你会发现，当你把PopupWindow显示出来了，无论你按多少次后退键
        //PopupWindow并不会关闭，而且退不出程序，加上下述代码可以解决这个问题
        popWindow.setTouchable(true);
        popWindow.setTouchInterceptor(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        popWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));    //要为popWindow设置一个背景才有效

        //设置popupWindow显示的位置，参数依次是参照View，x轴的偏移量，y轴的偏移量
        popWindow.showAsDropDown(view, 50, 0);

        //设置popupWindow里的按钮的事件
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "你点击了嘻嘻~", Toast.LENGTH_SHORT).show();

                popWindow.dismiss();
            }
        });
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "你点击了呵呵~", Toast.LENGTH_SHORT).show();
                if(et_newphone.getText().toString()!=null)
                    perinfo_phone.setText(et_newphone.getText().toString());
                popWindow.dismiss();
            }
        });




    }

    private void sexPopwindow(View view)
    {
        final Context mContext=PerinfoEdit.this;
        view = LayoutInflater.from(mContext).inflate(R.layout.pop_sexedit, null, false);
        TextView sexedit_male=view.findViewById(R.id.sexedit_male);
        TextView sexedit_female=view.findViewById(R.id.sexedit_female);
        //1.构造一个PopupWindow，参数依次是加载的View，宽高
        final PopupWindow popWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        //popWindow.setAnimationStyle(R.anim.anim_pop);  //设置加载动画

        //这些为了点击非PopupWindow区域，PopupWindow会消失的，如果没有下面的
        //代码的话，你会发现，当你把PopupWindow显示出来了，无论你按多少次后退键
        //PopupWindow并不会关闭，而且退不出程序，加上下述代码可以解决这个问题
        popWindow.setTouchable(true);
        popWindow.setTouchInterceptor(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        popWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));    //要为popWindow设置一个背景才有效

        //设置popupWindow显示的位置，参数依次是参照View，x轴的偏移量，y轴的偏移量
        popWindow.showAsDropDown(view, 50, 0);

        //设置popupWindow里的按钮的事件
        sexedit_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "你点击了嘻嘻~", Toast.LENGTH_SHORT).show();
                perinfo_sex.setText("男");
                popWindow.dismiss();
            }
        });
        sexedit_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "你点击了呵呵~", Toast.LENGTH_SHORT).show();
                perinfo_sex.setText("女");
                popWindow.dismiss();
            }
        });

    }


}
