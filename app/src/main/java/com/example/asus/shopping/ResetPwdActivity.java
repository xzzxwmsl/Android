package com.example.asus.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class ResetPwdActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private String newPwd;
    private String username;
    private EditText et_reset_pwd;
    private Button bt_reset_submit;
    private ImageView iv_reset_pwd_del;
    private TextView tv_navigation_label;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_reset_pwd);

        Intent intent = new Intent();
        intent=getIntent();
        username=intent.getStringExtra("username");

        tv_navigation_label = (TextView) findViewById(R.id.tv_navigation_label);
        tv_navigation_label.setText("重置");

        et_reset_pwd = (EditText) findViewById(R.id.et_reset_pwd);
        bt_reset_submit = (Button) findViewById(R.id.bt_reset_submit);
        iv_reset_pwd_del = (ImageView) findViewById(R.id.iv_reset_pwd_del);

        bt_reset_submit.setEnabled(false);

        iv_reset_pwd_del.setOnClickListener(this);
        bt_reset_submit.setOnClickListener(this);
        findViewById(R.id.ib_navigation_back).setOnClickListener(this);

        et_reset_pwd.addTextChangedListener(this);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.ib_navigation_back:
                finish();
                break;
            case R.id.bt_reset_submit:
                Reset();
                break;
            case R.id.iv_reset_pwd_del:
                et_reset_pwd.setText(null);
                break;
        }
    }

    private void Reset() {
        Intent intent=getIntent();
        final String username = intent.getStringExtra("username");
        String password = et_reset_pwd.getText().toString();


            /*
            使用POST提交
             */

        OkHttpClient okHttpClient = new OkHttpClient();
        //构造提交表单
        HashMap<String,String> paramsMap=new HashMap<>();
        paramsMap.put("username",username);
        paramsMap.put("password",password);

        FormBody.Builder builder = new FormBody.Builder();
        for (String key : paramsMap.keySet()) {
            //追加表单信息
            builder.add(key, paramsMap.get(key));
        }

        RequestBody requestBody=builder.build();
        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/Reset")
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ResetPwdActivity.this,"网络连接失败，请检查网络设置",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String jsonString = response.body().string();
                try {
                    JSONObject a = new JSONObject(jsonString);
                    String flag = a.get("status").toString();

                    if(flag.equals("success")){
                        Intent intent = new Intent();
                        intent.putExtra("Status","Login");
                        intent.putExtra("username",username);
                        intent.putExtra("msg","欢迎登录");
                        intent.setClass(ResetPwdActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        //转到主线程再使用Toast，否则会程序崩溃
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ResetPwdActivity.this,"账号或手机号已存在",Toast.LENGTH_SHORT).show();
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
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    public void afterTextChanged(Editable s) {
        String pwd = et_reset_pwd.getText().toString();

        if (username.length() > 0) {
            iv_reset_pwd_del.setVisibility(View.VISIBLE);
        } else {
            iv_reset_pwd_del.setVisibility(View.INVISIBLE);
        }

        //是否显示清除按钮
//        if (username.length() > 0) {
//            iv_retrieve_username_del.setVisibility(View.VISIBLE);
//        } else {
//            iv_retrieve_username_del.setVisibility(View.INVISIBLE);
//        }
//        if (pwd.length() > 0) {
//            mIvLoginPwdDel.setVisibility(View.VISIBLE);
//        } else {
//            mIvLoginPwdDel.setVisibility(View.INVISIBLE);

//        }
        //登录按钮是否可用
        if ( pwd.length()>=6 ) {
            bt_reset_submit.setEnabled(true);
            bt_reset_submit.setBackgroundResource(R.drawable.bg_login_submit);
            bt_reset_submit.setTextColor(getResources().getColor(R.color.white));
        } else {
            bt_reset_submit.setEnabled(false);
            bt_reset_submit.setBackgroundResource(R.drawable.bg_login_submit_lock);
            bt_reset_submit.setTextColor(getResources().getColor(R.color.account_lock_font_color));
        }
    }

}
