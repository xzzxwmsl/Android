package com.example.asus.shopping;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private EditText et_register_tel,et_register_username,et_register_pwd;
    private ImageView iv_register_tel_del,iv_register_username_del,iv_register_pwd_del;
    private Button bt_register_submit;
    private CheckBox cb_protocol;
    private TextView tv_register_male,tv_register_female,tv_protocol;
    private String sex="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_register);
        initView();
    }

    private void initView() {

        //////////////////////////
        //findview
        /////////////////////////
        bt_register_submit = (Button) findViewById(R.id.bt_register_submit);
        bt_register_submit.setEnabled(false);

        et_register_pwd = (EditText) findViewById(R.id.et_register_pwd);
        et_register_username = (EditText) findViewById(R.id.et_register_username);
        et_register_tel = (EditText) findViewById(R.id.et_register_tel);

        iv_register_pwd_del = (ImageView) findViewById(R.id.iv_register_pwd_del);
        iv_register_tel_del = (ImageView) findViewById(R.id.iv_register_tel_del);
        iv_register_username_del = (ImageView) findViewById((R.id.iv_register_username_del));

        cb_protocol = (CheckBox) findViewById(R.id.cb_protocol);

        tv_register_male = (TextView) findViewById(R.id.tv_register_male);
        tv_register_female = (TextView) findViewById(R.id.tv_register_female);
        tv_protocol = (TextView) findViewById(R.id.tv_protocol);


        //////////////////////////
        //onclick
        /////////////////////////
        et_register_tel.setOnClickListener(this);
        et_register_username.setOnClickListener(this);
        et_register_pwd.setOnClickListener(this);
        iv_register_username_del.setOnClickListener(this);
        iv_register_tel_del.setOnClickListener(this);
        iv_register_pwd_del.setOnClickListener(this);
        cb_protocol.setOnClickListener(this);
        tv_register_female.setOnClickListener(this);
        tv_register_male.setOnClickListener(this);
        tv_protocol.setOnClickListener(this);
        bt_register_submit.setOnClickListener(this);

        //////////////////////////
        //textchanged
        /////////////////////////
        et_register_pwd.addTextChangedListener(this);
        et_register_username.addTextChangedListener(this);
        et_register_tel.addTextChangedListener(this);

        findViewById(R.id.ib_navigation_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_navigation_back:
                finish();
                break;
            case R.id.iv_register_pwd_del:
                et_register_pwd.setText(null);
                break;
            case R.id.iv_register_tel_del:
                et_register_tel.setText(null);
                break;
            case R.id.iv_register_username_del:
                et_register_username.setText(null);
                break;
            case R.id.tv_register_male:
                male();
                break;
            case R.id.tv_register_female:
                female();
                break;
            case R.id.bt_register_submit:
                submit();
                break;
            case R.id.tv_protocol:
                protocol();
                break;
        }
    }

    private void protocol() {
        Uri uri = Uri.parse("http://10.0.2.2:8080/DOC.html");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void submit() {
        if(!ischecked()){
            Toast.makeText(RegisterActivity.this,"请同意用户协议",Toast.LENGTH_SHORT).show();
        }else if(sex.equals("")){
            Toast.makeText(RegisterActivity.this,"请选择性别",Toast.LENGTH_SHORT).show();
        }else {
            String tel = et_register_tel.getText().toString();
            final String username = et_register_username.getText().toString();
            String password = et_register_pwd.getText().toString();

            /*
            使用POST提交
             */

            OkHttpClient okHttpClient = new OkHttpClient();
            //构造提交表单
            HashMap<String,String> paramsMap=new HashMap<>();
            paramsMap.put("username",username);
            paramsMap.put("password",password);
            paramsMap.put("tel",tel);
            paramsMap.put("type","USER");
            paramsMap.put("sex",sex);

            FormBody.Builder builder = new FormBody.Builder();
            for (String key : paramsMap.keySet()) {
                //追加表单信息
                builder.add(key, paramsMap.get(key));
            }

            RequestBody requestBody=builder.build();
            Request request = new Request.Builder()
                    .url("http://10.0.2.2:8080/Register")
                    .post(requestBody)
                    .build();

            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegisterActivity.this,"网络连接失败，请检查网络设置",Toast.LENGTH_SHORT).show();
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
                            intent.putExtra("msg","注册成功");
                            intent.setClass(RegisterActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            //转到主线程再使用Toast，否则会程序崩溃
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RegisterActivity.this,"账号或手机号已存在",Toast.LENGTH_SHORT).show();
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

    private void female() {
        sex="female";
        Drawable drawableLeftMale = getResources().getDrawable(
                R.mipmap.btn_gender_male_normal);
        Drawable drawableLeftFemale = getResources().getDrawable(
                R.mipmap.form_checkbox_checked);

        tv_register_male.setCompoundDrawablesWithIntrinsicBounds(drawableLeftMale,
                null, null, null);
        tv_register_male.setCompoundDrawablePadding(7);

        tv_register_female.setCompoundDrawablesWithIntrinsicBounds(drawableLeftFemale,null,null,null);
        tv_register_female.setCompoundDrawablePadding(7);


    }

    private void male() {
        sex="male";
        Drawable drawableLeftMale = getResources().getDrawable(
                R.mipmap.form_checkbox_checked);
        Drawable drawableLeftFemale = getResources().getDrawable(
                R.mipmap.btn_gender_female_normal);

        tv_register_male.setCompoundDrawablesWithIntrinsicBounds(drawableLeftMale,
                null, null, null);
        tv_register_male.setCompoundDrawablePadding(7);

        tv_register_female.setCompoundDrawablesWithIntrinsicBounds(drawableLeftFemale,null,null,null);
        tv_register_female.setCompoundDrawablePadding(7);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    public void afterTextChanged(Editable s) {
        String pwd = et_register_pwd.getText().toString();
        String tel = et_register_tel.getText().toString();
        String username = et_register_username.toString();

        //是否显示清除按钮
        if (username.length() > 0) {
            iv_register_username_del.setVisibility(View.VISIBLE);
        } else {
            iv_register_username_del.setVisibility(View.INVISIBLE);
        }

        if (tel.length() > 0) {
            iv_register_tel_del.setVisibility(View.VISIBLE);
        } else {
            iv_register_tel_del.setVisibility(View.INVISIBLE);
        }

        if (pwd.length() > 0) {
            iv_register_pwd_del.setVisibility(View.VISIBLE);
        } else {
            iv_register_pwd_del.setVisibility(View.INVISIBLE);
        }



        //登录按钮是否可用
        if ( pwd.length()>=6 && !TextUtils.isEmpty(username) && !TextUtils.isEmpty(tel) ) {
            bt_register_submit.setEnabled(true);
            bt_register_submit.setBackgroundResource(R.drawable.bg_login_submit);
            bt_register_submit.setTextColor(getResources().getColor(R.color.white));
        } else {
            bt_register_submit.setEnabled(false);
            bt_register_submit.setBackgroundResource(R.drawable.bg_login_submit_lock);
            bt_register_submit.setTextColor(getResources().getColor(R.color.account_lock_font_color));
        }
    }

    private boolean ischecked(){
        return cb_protocol.isChecked();
    }

}