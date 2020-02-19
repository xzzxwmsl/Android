package com.example.asus.shopping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class orderlistAdapter extends BaseAdapter {

    private Context mcontext;
    private LinkedList<order_item> mdata;

    public orderlistAdapter(Context context,LinkedList<order_item> data)
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
            view= LayoutInflater.from(mcontext).inflate(R.layout.order_item,viewGroup,false);
            holder=new viewHolder();
           holder.order_no=view.findViewById(R.id.order_item_no);
           holder.order_clist=view.findViewById(R.id.order_item_c_list);
           holder.order_ctotalnum=view.findViewById(R.id.order_item_totalnum);
           holder.order_ctotalprice=view.findViewById(R.id.order_item_totalprice);
           holder.order_statu=view.findViewById(R.id.order_item_status);
           holder.order_btn=view.findViewById(R.id.order_item_btn);


            view.setTag(holder);
        }
        else {
            holder=(viewHolder)view.getTag();
        }
        holder.order_no.setText(mdata.get(i).getOrderid()+"");

        switch (mdata.get(i).getStatus())
        {
            case "paying":
                holder.order_statu.setText("未支付");
                holder.order_btn.setText("去支付");
                break;
            case "payed":
                holder.order_statu.setText("已支付");
                holder.order_btn.setText("确认收货");
                break;
            case "finished":
                holder.order_statu.setText("已收货");
                holder.order_btn.setText("评价");
                break;
        }

        int price=0;
        int num=0;


        List<order_item_citem> clist=mdata.get(i).getCdata();

        for(int n=0;n<clist.size();n++)
        {
            order_item_citem c=clist.get(n);
            price=price+c.getPrice();
            num=num+c.getNum();

        }

        mdata.get(i).setTotalnum(num);
        mdata.get(i).setTotalprice(price);

        holder.order_ctotalprice.setText(price+"");
        holder.order_ctotalnum.setText(num+"");

        order_item_citemAdapter cAapter=new order_item_citemAdapter(mcontext,(LinkedList<order_item_citem>)clist);
        holder.order_clist.setAdapter(cAapter);









        return view;
    }

    static class viewHolder
    {
        TextView order_no;
        clistview order_clist;
        TextView order_ctotalnum;
        TextView order_ctotalprice;
        TextView order_statu;
        Button order_btn;



    }
}
