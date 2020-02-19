package com.example.asus.shopping;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import okhttp3.Call;

import java.util.LinkedList;

public class fp_listAdapter extends BaseAdapter
{
    private Context context;
    private LinkedList<fp_list_item> mdata;
    String url="http://10.0.2.2:8080/";
    //private String cid;

    public fp_listAdapter(Context context,LinkedList<fp_list_item> mdata)
    {
        this.context=context;
        this.mdata=mdata;
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
            view= LayoutInflater.from(context).inflate(R.layout.fp_list_item,viewGroup,false);
            holder=new viewHolder();
            holder.img=view.findViewById(R.id.fp_list_item_img);
            holder.cname=view.findViewById(R.id.fp_list_item_name);
            holder.cprice=view.findViewById(R.id.fp_list_item_price);
            holder.cnum=view.findViewById(R.id.fp_list_item_num);


            view.setTag(holder);
        }
        else {
            holder=(viewHolder) view.getTag();
        }

        holder.cname.setText(mdata.get(i).getCname());
        holder.cprice.setText(mdata.get(i).getCprice()+"");
        holder.cnum.setText(mdata.get(i).getCnum()+"");
        holder.cid=mdata.get(i).getCid();
       // holder.img.setImageDrawable();
        //holder.img.setBackground();



        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,commodity_xq.class);
                //intent.putExtra("username",username);
                intent.putExtra("cid",mdata.get(i).getCid());
                context.startActivity(intent);

            }
        });



        //holder.img.setBackgroundResource();



        Glide
                .with(context)
                .load(url+mdata.get(i).getImgsrc())
                .into((ImageView) holder.img);













        return view;
    }

    static class viewHolder
    {
        ImageView img;
        TextView cname;
        TextView cprice;
        TextView cnum;
        String cid;




    }


}
