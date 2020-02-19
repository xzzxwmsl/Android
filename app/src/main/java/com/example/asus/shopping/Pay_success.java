package com.example.asus.shopping;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class Pay_success extends AppCompatActivity  implements View.OnClickListener {

    private Button pay_success_return;
    private  Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_success);

        InitID();

    }

    private void InitID()
    {

        context=Pay_success.this;

        pay_success_return=findViewById(R.id.pay_success_return);


        pay_success_return.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.pay_success_return:
                Intent intent=new Intent(context,MainActivity.class);
                intent.putExtra("id",4);
                startActivity(intent);

                break;
        }

    }
}
