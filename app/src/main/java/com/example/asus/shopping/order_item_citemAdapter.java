package com.example.asus.shopping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

public class order_item_citemAdapter extends BaseAdapter
{
    private Context mcontext;
    private LinkedList<order_item_citem> mdata;

    public order_item_citemAdapter(Context context,LinkedList<order_item_citem> data)
    {
        this.mcontext=context;
        this.mdata=data;
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
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        viewHolder holder=null;
        if(view==null)
        {
            view= LayoutInflater.from(mcontext).inflate(R.layout.order_item_c_item,viewGroup,false);
            holder=new viewHolder();

            holder.c_cid=view.findViewById(R.id.order_item_c_name);
            holder.c_img=view.findViewById(R.id.order_item_c_image);
            holder.c_num=view.findViewById(R.id.order_item_c_num);
            holder.c_price=view.findViewById(R.id.order_item_c_price);

            view.setTag(holder);
        }
        else
        {
            holder=(viewHolder)view.getTag();
        }

        holder.c_cid.setText(mdata.get(i).getCid());
        holder.c_num.setText(mdata.get(i).getNum()+"");
        holder.c_price.setText(mdata.get(i).getPrice()+"");


        return view;
    }

    static class viewHolder
    {
        ImageView c_img;
        TextView c_cid;
        TextView c_price;
        TextView c_num;
    }
}
