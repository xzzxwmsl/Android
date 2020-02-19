package com.example.asus.shopping;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;

public class FirstpageFragment  extends Fragment implements View.OnClickListener
{
    private View view;
    private Context context;

    private EditText search_et_input;
    private Button search_btn_back;

    private ImageView fp_book;
    private ImageView fp_computer;
    private ImageView fp_phone;
    private ImageView fp_sports;
    private ImageView fp_mother;
    private ImageView fp_electric;
    private ImageView fp_suitcase;
    private ImageView fp_clothes;

    private ImageView fp_img;
    private ListView fp_list_hot;
    private ListView fp_list_discount;

    private String search_text;
    private String username;

    private LinkedList<fp_list_item> listdata_hot=new LinkedList<>();
    private LinkedList<fp_list_item> listdata_discount=new LinkedList<>();

    private fp_listAdapter fplistAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.firstpage, null);

        initID();
        //getdata_moni();
        getdata();
        userdata();





        return view;
    }

    public void initID()
    {
        context=getActivity();

        search_et_input=view.findViewById(R.id.search_et_input);
        search_btn_back=view.findViewById(R.id.search_btn_back);

        fp_book=view.findViewById(R.id.book);
        fp_clothes=view.findViewById(R.id.clothes);
        fp_computer=view.findViewById(R.id.computer);
        fp_electric=view.findViewById(R.id.electric);
        fp_mother=view.findViewById(R.id.mother);
        fp_phone=view.findViewById(R.id.phone);
        fp_sports=view.findViewById(R.id.sports);
        fp_suitcase=view.findViewById(R.id.suitcase);

        fp_img=view.findViewById(R.id.firstpage_img);
        fp_list_hot=view.findViewById(R.id.fp_list_hot);
        fp_list_discount=view.findViewById(R.id.fp_list_discount);

        search_btn_back.setOnClickListener(this);


    }

    public void getdata()
    {


        listdata_hot=new LinkedList<>();
        listdata_discount=new LinkedList<>();

        //HTTP请求
        String url = "http://10.0.2.2:8080/SearchCommodity";

        final String action="all";

        url=url+"?"+"action="+action;
        //构造get链接

        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e)
            {/*
                //转到主线程再使用Toast，否则会程序崩溃
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context,"网络连接失败，请检查网络设置",Toast.LENGTH_SHORT).show();
                    }
                });*/

                Toast.makeText(context,"网络连接失败，请检查网络设置",Toast.LENGTH_SHORT).show();
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
                        JSONArray jsonArray=a.getJSONArray("value");
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            System.out.println("-----------------------FUCKYOU");
                            JSONObject object=jsonArray.getJSONObject(i);

                            String cid=object.getString("cid");
                            int num=object.getInt("num");
                            int price=object.getInt("price");
                            String category=object.getString("category");
                            String name=object.getString("name");
                            String src=object.getString("src");

                            listdata_hot.add(new fp_list_item(src,name,cid,price,num,category));

                        }



                    }else {
                        /*
                        //转到主线程再使用Toast，否则会程序崩溃
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AddressNew.this,"保存失败",Toast.LENGTH_SHORT).show();
                            }
                        });*/
                        Toast.makeText(context,"请求失败",Toast.LENGTH_SHORT).show();

                    }

                }catch (JSONException e){
                    System.out.println(e.toString());
                }


            }
        });

        listdata_discount=listdata_hot;

    }

    public void getdata_moni()
    {
        listdata_hot.add(new fp_list_item("null","华为手机，超强待机","123",999,120,"phone"));
        listdata_hot.add(new fp_list_item("null","最新华为手机，只需要999，即可入手","126",888,300,"computer"));

        listdata_discount=listdata_hot;

    }

    public void userdata()
    {
        fplistAdapter=new fp_listAdapter(context,listdata_hot);
        fp_list_hot.setAdapter(fplistAdapter);
        fplistAdapter=new fp_listAdapter(context,listdata_discount);
        fp_list_discount.setAdapter(fplistAdapter);
    }


    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.search_btn_back:
                String input = search_et_input.getText().toString();
                Intent intent=new Intent(context,commodity_list.class);
                intent.putExtra("input",input);
                startActivity(intent);
                break;
        }

    }
}