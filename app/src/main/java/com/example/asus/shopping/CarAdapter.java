package com.example.asus.shopping;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * cjy
 */
public class CarAdapter extends BaseAdapter {


    private Context context;
    private List<CarInfo> list;

    private CheckInterface checkInterface;
    private ModifyCountInterface modifyCountInterface;

//  private   ImageLoader imageLoader;
//private RequestQueue mQueue;
//    private com.android.volley.toolbox.ImageLoader mImageLoader;


    //商品Id
    private int carid;
    //商品数量
    private String num;


    public CarAdapter(Context context, List<CarInfo> list) {
        this.context = context;
        this.list = list;
//        imageLoader = ImageLoader.getInstance();
//        mQueue = Volley.newRequestQueue(context);
//        mImageLoader = new com.android.volley.toolbox.ImageLoader(mQueue, new BitmapCache());//
    }


    public void setCheckInterface(CheckInterface checkInterface) {
        this.checkInterface = checkInterface;
    }

    public void setModifyCountInterface(ModifyCountInterface modifyCountInterface) {
        this.modifyCountInterface = modifyCountInterface;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.shopcart_item, null);
            holder = new ViewHolder();
            holder.good_name = (TextView) convertView.findViewById(R.id.good_name);
            holder.good_fname = (TextView) convertView.findViewById(R.id.good_fname);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.good_money = (TextView) convertView.findViewById(R.id.good_money);
            holder.nu = (TextView) convertView.findViewById(R.id.nu);
            holder.checkone = (CheckBox) convertView.findViewById(R.id.checkone);
            holder.jia = (Button) convertView.findViewById(R.id.jia);
            holder.jian = (Button) convertView.findViewById(R.id.jian);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        final CarInfo info = list.get(position);
        System.out.println("start-----------");
        if (info != null) {
            holder.checkone.setChecked(info.isChoosed());

            holder.checkone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    info.setChoosed(((CheckBox) v).isChecked());
                    holder.checkone.setChecked(((CheckBox) v).isChecked());
                    checkInterface.checkChild(position, ((CheckBox) v).isChecked());// 暴露子选接口
                }
            });


            holder.good_name.setText(list.get(position).getSpname());
            holder.good_fname.setText("型号: " + info.getSptype());
            holder.good_money.setText("¥ " + list.get(position).getSpprice());
            System.out.println(list.get(position).getSpimg());
            Glide.with(context)
                    .load("http://10.0.2.2:8080/"+list.get(position).getSpimg()).into(holder.img);
            holder.nu.setText(list.get(position).getSpnum());

            holder.jia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    modifyCountInterface.doIncrease(position, holder.nu, holder.checkone.isChecked());// 暴露增加接口
                }
            });


            holder.jian.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    modifyCountInterface.doDecrease(position, holder.nu, holder.checkone.isChecked());// 暴露删减接口
                }
            });
        }

        return convertView;
    }


    class ViewHolder {
        ImageView img;
        Button jia, jian;
        TextView good_name, good_fname, good_money, nu;
        CheckBox checkone;
    }


    /**
     * 澶嶉€夋鎺ュ彛
     */
    public interface CheckInterface {
//          /**
//           * 组选框状态改变触发的事件
//           *
//           * @param groupPosition 组元素位置
//           * @param isChecked     组元素选中与否
//           */
//        public void checkGroup(int groupPosition, boolean isChecked);

        /**
         * 子选框状态改变时触发的事件
         *
         * @param childPosition 子元素位置
         * @param isChecked     子元素选中与否
         */
        public void checkChild(int childPosition, boolean isChecked);
    }

    /**
     * 改变数量的接口
     */
    public interface ModifyCountInterface {
        /**
         * 增加操作
         *
         * @param childPosition 子元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */

        public void doIncrease(int childPosition, View showCountView, boolean isChecked);

        /**
         * 删减操作
         *
         * @param childPosition 子元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */

        public void doDecrease(int childPosition, View showCountView, boolean isChecked);
    }
}
