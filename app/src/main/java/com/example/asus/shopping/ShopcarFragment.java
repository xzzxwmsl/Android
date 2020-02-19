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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ShopcarFragment extends Fragment implements CarAdapter.CheckInterface, CarAdapter.ModifyCountInterface {
    //private Button mGotopay;
    private Context context;

    private ListView MyList;
    private CheckBox allcheck;
    private TextView hejivalue, jiesuanvalue;
    private RelativeLayout jiesuanRea;
    private List<CarInfo> list;

    public static LinearLayout bottemLin;
    private CarAdapter adapter;

    private double totalPrice = 0.00;// 购买的商品总价
    private int totalCount = 0;// 购买的商品总数量

    private View view;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_shop_cart, null);
        context = getActivity();


        initView();
        initList();
//        c();

        return view;
    }

    //定义数据-自定义----须修改为导入数据
    private void initList() {
        list = new ArrayList<CarInfo>();
        ////////////

        GetCart();
        try{
            Thread.sleep(500);
            adapter = new CarAdapter(context, list);
            MyList.setAdapter(adapter);
            c();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //////////
        System.out.println("---------1" + list);
//        for(int i =0;i<10;i++){
//            CarInfo info = new CarInfo();
//            info.setSpnum("1");
//            info.setSpprice("36");
//            info.setSptype("cjy");
//            info.setSpname("Sam");
//            list.add(info);
//        }

    }

    private void GetCart() {

        //Intent intent=getIntent();
        //final String username = intent.getStringExtra("username");
        final String username = "xzz";
        final String url="http://10.0.2.2:8080/GetCart";



        /*
        构造传参
        */
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> paramsMap = new HashMap<>();
                paramsMap.put("username", username);
                MyHttpUtil myHttpUtil = new MyHttpUtil();

                String jsonString = myHttpUtil.httpGet(url,paramsMap);
                System.out.println(jsonString);
                try {
                    JSONObject json = new JSONObject(jsonString);
                    System.out.println("-------------" + json.toString());
                    String flag = json.get("status").toString();

                    if (flag.equals("success")) {
                        //处理json

                        System.out.println(json.toString());

                        JSONArray values = json.getJSONArray("value");


                        for (int i = 0; i < values.length(); i++) { //values 里每一项都是一个jsonobject，一次循环处理一个订单
                            //System.out.println("xxxx"+i);
                            JSONObject jsonObject = values.getJSONObject(i);//得到该用户第 i  个订单

                            CarInfo info = new CarInfo();
                            String cid = jsonObject.getString("cid");
                            String price = jsonObject.getString("price");
                            String num = jsonObject.getString("num");//获取json数组里的信息
                            String src = jsonObject.getString("src");

                            info.setSpnum(num);
                            info.setSpprice(price);
                            info.setSptype(cid);
                            info.setSpname(cid);
                            info.setSpimg(src);
                            list.add(info);
                            System.out.println("---------2" + list);
                        }
                        System.out.println("XXXXXXXXXXXXXXx"+list);


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("xxxasf bhgfhajgkjmkx");
                                adapter = new CarAdapter(context, list);
                                MyList.setAdapter(adapter);
                                c();
                            }
                        });



                    } else {
                        //转到主线程再使用Toast，否则会程序崩溃

                    }
                }catch (JSONException e){

                }

            }
        }).start();



//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//                String jsonString = response.body().string();
//                try {
//                    JSONObject json = new JSONObject(jsonString);
//                    String flag = json.get("status").toString();
//
//                    if(flag.equals("success")){
//                        //处理json
//
//                        System.out.println(json.toString());
//
//                        JSONArray values= json.getJSONArray("value");
//
//
//                        for (int i=0;i<values.length();i++){ //values 里每一项都是一个jsonobject，一次循环处理一个订单
//                            //System.out.println("xxxx"+i);
//                            JSONObject jsonObject=values.getJSONObject(i);//得到该用户第 i  个订单
//
//                            CarInfo info = new CarInfo();
//                            String cid=jsonObject.getString("cid");
//                            String price=jsonObject.getString("price");
//                            String num=jsonObject.getString("num");//获取json数组里的信息
//
//
//                            info.setSpnum(num);
//                            info.setSpprice(price);
//                            info.setSptype(cid);
//                            info.setSpname(cid);
//                            list.add(info);
//                            System.out.println("---------2"+list);
//                        }
//
//
//
//                    }else {
//                        //转到主线程再使用Toast，否则会程序崩溃
//
//                    }
//
//                }catch (JSONException e){
//                    System.out.println(e.toString());
//                }
//
//            }
//        });
//        System.out.println("---------"+list);
    }

    private void runOnUiThread(Runnable runnable) {

    }

    private void initView() {
        jiesuanRea = view.findViewById(R.id.jiesuanRea);
        bottemLin = view.findViewById(R.id.bottemLin);
        MyList = view.findViewById(R.id.MyList);
        allcheck = view.findViewById(R.id.allcheck);
        hejivalue = view.findViewById(R.id.hejivalue);
        jiesuanvalue = view.findViewById(R.id.jiesuanvalue);
        allcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doCheckAll();
            }
        });

        jiesuanRea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Pay.class);
                startActivity(intent);
            }
        });


    }


    private void c() {
        adapter.setCheckInterface(this);// 关键步骤1：设置复选框接口
        adapter.setModifyCountInterface(this);// 关键步骤2,设置数量增减接口
    }

    /**
     * 全选与反选
     */
    private void doCheckAll() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setChoosed(allcheck.isChecked());
        }
        adapter.notifyDataSetChanged();
        calculate();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        jiesuanvalue = null;
        hejivalue = null;
        allcheck = null;
    }


    private boolean isAllCheck() {

        for (CarInfo info : list) {
            if (!info.isChoosed())
                return false;

        }

        return true;
    }

    private void calculate() {
        totalCount = 0;
        totalPrice = 0.00;
        for (int j = 0; j < list.size(); j++) {
            CarInfo product = list.get(j);
            if (product.isChoosed()) {
                totalCount += Integer.parseInt(product.getSpnum());
                totalPrice += Double.parseDouble(product.getSpprice()) * Double.parseDouble(product.getSpnum());
            }
        }
        hejivalue.setText("¥" + totalPrice);
        jiesuanvalue.setText("" + totalCount);
    }

    @Override
    public void checkChild(int childPosition, boolean isChecked) {

        if (isAllCheck())
            allcheck.setChecked(true);
        else
            allcheck.setChecked(false);
        adapter.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void doIncrease(int childPosition, View showCountView, boolean isChecked) {
        CarInfo info = (CarInfo) adapter.getItem(childPosition);
        int currentCount = Integer.parseInt(info.getSpnum());
        currentCount++;
        info.setSpnum(String.valueOf(currentCount));
        ((TextView) showCountView).setText(currentCount + "");

        adapter.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void doDecrease(int childPosition, View showCountView, boolean isChecked) {
        CarInfo info = (CarInfo) adapter.getItem(childPosition);
        int currentCount = Integer.parseInt(info.getSpnum());
        if (currentCount == 1)
            return;
        currentCount--;

        info.setSpnum(String.valueOf(currentCount));
        ((TextView) showCountView).setText(currentCount + "");

        adapter.notifyDataSetChanged();
        calculate();
    }


}

