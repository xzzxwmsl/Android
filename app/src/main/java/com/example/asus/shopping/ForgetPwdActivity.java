package com.example.asus.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class ForgetPwdActivity extends AppCompatActivity implements View.OnClickListener,TextWatcher {

    private EditText et_retrieve_tel_input,et_retrieve_username_input;
    private Button bt_retrieve_submit;
    private ImageView iv_retrieve_username_del,iv_retrieve_tel_del;
    private TextView tv_navigation_label;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_retrieve_pwd);

        tv_navigation_label = (TextView) findViewById(R.id.tv_navigation_label);
        tv_navigation_label.setText("找回");

        et_retrieve_tel_input = (EditText) findViewById(R.id.et_retrieve_tel_input);
        et_retrieve_username_input = (EditText) findViewById(R.id.et_retrieve_username_input);

        bt_retrieve_submit = (Button) findViewById(R.id.bt_retrieve_submit);
        bt_retrieve_submit.setEnabled(false);

        iv_retrieve_username_del = (ImageView) findViewById(R.id.iv_retrieve_username_del);
        iv_retrieve_tel_del = (ImageView) findViewById(R.id.iv_retrieve_tel_del);
        //onclick
        iv_retrieve_tel_del.setOnClickListener(this);
        iv_retrieve_username_del.setOnClickListener(this);
        bt_retrieve_submit.setOnClickListener(this);
        findViewById(R.id.ib_navigation_back).setOnClickListener(this);

        //inputchange
        et_retrieve_tel_input.addTextChangedListener(this);
        et_retrieve_username_input.addTextChangedListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_navigation_back:
                finish();
                break;
            case R.id.bt_retrieve_submit:
                isRight();
                break;
            case R.id.iv_retrieve_username_del:
                et_retrieve_username_input.setText(null);
                break;
            case R.id.iv_retrieve_tel_del:
                et_retrieve_tel_input.setText(null);
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    public void afterTextChanged(Editable s) {
        String username = et_retrieve_username_input.getText().toString();
        String tel = et_retrieve_tel_input.getText().toString();

        //是否显示清除按钮
        if (username.length() > 0) {
            iv_retrieve_username_del.setVisibility(View.VISIBLE);
        } else {
            iv_retrieve_username_del.setVisibility(View.INVISIBLE);
        }

        if(tel.length() > 0){
            iv_retrieve_tel_del.setVisibility((View.VISIBLE));
        }else{
            iv_retrieve_tel_del.setVisibility(View.INVISIBLE);
        }

        //登录按钮是否可用
        if (!TextUtils.isEmpty(tel) && !TextUtils.isEmpty(username)) {
            bt_retrieve_submit.setEnabled(true);
            bt_retrieve_submit.setBackgroundResource(R.drawable.bg_login_submit);
            bt_retrieve_submit.setTextColor(getResources().getColor(R.color.white));
        } else {
            bt_retrieve_submit.setEnabled(false);
            bt_retrieve_submit.setBackgroundResource(R.drawable.bg_login_submit_lock);
            bt_retrieve_submit.setTextColor(getResources().getColor(R.color.account_lock_font_color));
        }
    }

    private void isRight() {

        final String username = et_retrieve_username_input.getText().toString();
        String tel = et_retrieve_tel_input.getText().toString();

        OkHttpClient okHttpClient = new OkHttpClient();
        /*
            构造提交表单
         */
        HashMap<String,String> paramsMap=new HashMap<>();

        paramsMap.put("username",username);
        paramsMap.put("tel",tel);

        FormBody.Builder builder = new FormBody.Builder();
        for (String key : paramsMap.keySet()) {
            //追加表单信息
            builder.add(key, paramsMap.get(key));
        }

        RequestBody requestBody=builder.build();
        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/Retrieve")
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ForgetPwdActivity.this,"网络连接失败，请检查网络设置",Toast.LENGTH_SHORT).show();
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
                        intent.putExtra("msg","true");
                        intent.setClass(ForgetPwdActivity.this,ResetPwdActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        //转到主线程再使用Toast，否则会程序崩溃
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ForgetPwdActivity.this,"账号或手机号不符",Toast.LENGTH_SHORT).show();
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
