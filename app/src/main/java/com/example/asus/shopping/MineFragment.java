package com.example.asus.shopping;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MineFragment extends Fragment
{
    private ImageView mine_iv_per;
    private TextView mine_tv_order;
    private TextView mine_tv_love;
    private TextView mine_tv_addlist;
    private Button mine_btn_login;
    private Context context=null;

    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        View view = inflater.inflate(R.layout.mine,null);
        mine_iv_per=view.findViewById(R.id.mine_person_image);
        mine_tv_order=view.findViewById(R.id.mine_order);
        mine_tv_love=view.findViewById(R.id.mine_love);
        mine_tv_addlist=(TextView) view.findViewById(R.id.mine_address);
        mine_btn_login=view.findViewById(R.id.mine_btn_login);

        mine_iv_per.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(context,PerinfoEdit.class);
                startActivity(it);
            }
        });

        mine_tv_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,orderlist.class);
                startActivity(intent);
            }
        });

        mine_tv_love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,lovelist.class);
                startActivity(intent);
            }
        });

        mine_tv_addlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,Addresslist.class);

                startActivity(intent);
            }
        });
        mine_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(context,LoginActivity.class);
                startActivity(intent);
                Toast.makeText(context,"1",Toast.LENGTH_LONG).show();
            }
        });


        return view;
    }









}

