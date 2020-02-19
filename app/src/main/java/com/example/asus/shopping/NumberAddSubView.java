package com.example.asus.shopping;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.TintTypedArray;

public class NumberAddSubView extends LinearLayout implements View.OnClickListener{

    private LayoutInflater mInflater;
    private Button mBtnAdd;
    private Button mBtnSub;
    private TextView mTextNum;
    private int value,minValue,maxValue;
    private OnButtonClickListener mOnButtonClickListener;

    public int getValue() {
        String val = mTextNum.getText().toString();

        if(val != null && !"".equals(val))
            this.value = Integer.parseInt(val);

        return value;
    }

    public int getMinValue() {
        return minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setValue(int value) {
        mTextNum.setText(value+"");
        this.value = value;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }



    public NumberAddSubView(Context context) {
        this(context,null);
    }

    public NumberAddSubView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }


    @SuppressLint("RestrictedApi")
    public NumberAddSubView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mInflater = LayoutInflater.from(context);
        initView();

        if(attrs!=null){

            TintTypedArray a = TintTypedArray.obtainStyledAttributes(context,attrs,R.styleable.NumberAddSubView,defStyleAttr,0);

            int val = a.getInt(R.styleable.NumberAddSubView_value,0);
            setValue(val);

            int minVal = a.getInt(R.styleable.NumberAddSubView_minValue,0);
            setMinValue(minVal);

            int maxVal = a.getInt(R.styleable.NumberAddSubView_maxValue,0);
            setMaxValue(maxVal);

            Drawable drawableBtnAdd = a.getDrawable(R.styleable.NumberAddSubView_btnAddBackground);
            Drawable drawableBtnSub = a.getDrawable(R.styleable.NumberAddSubView_btnSubBackground);
            Drawable drawableTextView = a.getDrawable(R.styleable.NumberAddSubView_textViewBackground);

            setButtonAddBackground(drawableBtnAdd);
            setButtonSubBackground(drawableBtnSub);
            setTextViewBackground(drawableTextView);

            a.recycle();
        }
    }


    private void initView(){

        View view = mInflater.inflate(R.layout.weight_number_add_sub,this,true);
        mBtnAdd = view.findViewById(R.id.btn_add);
        mBtnSub = view.findViewById(R.id.btn_sub);
        mTextNum = view.findViewById(R.id.txt_num);

        mBtnAdd.setOnClickListener(this);
        mBtnSub.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.btn_add)
        {
            numAdd();
            if(mOnButtonClickListener!=null){
                mOnButtonClickListener.onButtonAddClick(view,value);
            }

        }
        else if(view.getId() == R.id.btn_sub)
        {
            numSub();
            if(mOnButtonClickListener!=null){
                mOnButtonClickListener.onButtonSubClick(view,value);
            }

        }
    }

    private void numAdd(){

        if(value<maxValue)
            value = value + 1;

        mTextNum.setText(value + "");

    }

    private void numSub(){
        if(value>minValue)
            value = value - 1;

        mTextNum.setText(value+"");
    }

    public void setTextViewBackground(Drawable drawable){

        mTextNum.setBackgroundDrawable(drawable);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setButtonAddBackground(Drawable background){

        this.mBtnAdd.setBackground(background);
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setButtonSubBackground(Drawable background){

        this.mBtnSub.setBackground(background);
    }

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener){
        this.mOnButtonClickListener = onButtonClickListener;
    }

    public interface OnButtonClickListener{

        void  onButtonAddClick(View view, int value);
        void  onButtonSubClick(View view, int value);
    }
}
