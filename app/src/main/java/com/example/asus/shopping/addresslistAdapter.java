package com.example.asus.shopping;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.app.PendingIntent.getActivity;

public class addresslistAdapter extends BaseAdapter

{
    private  int pos;
    private LinkedList<Addlist_item> mdata;
    private Context context;
    boolean flag;

    public addresslistAdapter(LinkedList<Addlist_item> mdata,Context context,boolean flag)
    {
        this.mdata=mdata;
        this.context=context;
        this.flag=flag;

    }

    @Override
    public int getCount() {
        return mdata.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        viewHolder holder=null;
        if(view==null)
        {
            view= LayoutInflater.from(context).inflate(R.layout.address_item,viewGroup,false);
            holder=new viewHolder();
            holder.tv_name=view.findViewById(R.id.additem_name);
            holder.tv_phone=view.findViewById(R.id.additem_phone);
            holder.tv_add=view.findViewById(R.id.additem_add);
            holder.tv_delete=view.findViewById(R.id.additem_delete);


            view.setTag(holder);
        }
        else {
        holder=(viewHolder)view.getTag();
        }
        holder.tv_name.setText(mdata.get(i).getName());
        holder.tv_phone.setText(mdata.get(i).getPhone());
        holder.tv_add.setText(mdata.get(i).getAddress());



        holder.tv_delete.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteadd(view,i,mdata);

                notifyDataSetChanged();

            }
        });




        return view;
    }

    static class viewHolder
    {
        TextView tv_name;
        TextView tv_phone;
        TextView tv_add;
        TextView tv_delete;
        TextView tv_edit;


    }

     boolean deleteadd(View view, int i, final LinkedList<Addlist_item> mdata)
    {
        final Context mContext=context;
        view = LayoutInflater.from(mContext).inflate(R.layout.pop_deleteadd, null, false);
        Button btn_cancel = (Button) view.findViewById(R.id.deleteadd_cancel);
        Button btn_confirm = (Button) view.findViewById(R.id.deleteadd_confirm);
        flag=false;
        pos=i;


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
                Toast.makeText(mContext, "你点击了取消~", Toast.LENGTH_SHORT).show();

                popWindow.dismiss();
            }
        });
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //HTTP请求

                String url = "http://10.0.2.2:8080/Address";
                final String name = mdata.get(pos).getName();
                final String phone =mdata.get(pos).getPhone();
                final String add=mdata.get(pos).getAddress();
                final String action="delete";

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
                            }else {



                            }

                        }catch (JSONException e){
                            System.out.println(e.toString());
                        }


                    }
                });


                flag=true;
                mdata.remove(pos);
                notifyDataSetChanged();

                popWindow.dismiss();


            }
        });
        return flag;




    }

}
