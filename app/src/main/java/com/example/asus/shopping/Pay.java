package com.example.asus.shopping;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class Pay extends AppCompatActivity {

    private Button mWeixinpay,mQQpay;
    private ImageButton Pay_return;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        mWeixinpay = findViewById(R.id.pay_weixinpay);
        mQQpay = findViewById(R.id.pay_QQpay);
        Pay_return=findViewById(R.id.pay_back);
        context=Pay.this;
        mWeixinpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Pay.this,Pay_success.class);
                startActivity(intent);
            }
        });
        mQQpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Pay.this,Pay_success.class);
                startActivity(intent);
            }
        });

        Pay_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,MainActivity.class);
                intent.putExtra("id",4);
                startActivity(intent);
            }
        });
    }
}
