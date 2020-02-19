package com.example.asus.shopping;



import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    //UI Object
    private TextView txt_top;
    private TextView txt_firstpage;
    private TextView txt_news;
    private TextView txt_friend;
    private TextView txt_shopcar;
    private TextView txt_mine;
    private FrameLayout ly_content;

    //Fragment Object
    private FirstpageFragment FirstpageF;
    private FriendFragment FriendF;
    private NewsFragment NewsF;
    private ShopcarFragment ShopcarF;
    private MineFragment MineF;
    private FragmentManager fManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        fManager = getSupportFragmentManager();
        bindViews();
       // txt_firstpage.performClick();
        txt_firstpage.performClick();
        //模拟一次点击，既进去后选择第一项

        int id=getIntent().getIntExtra("id",0);
        switch (id)
        {
            case 1:txt_firstpage.performClick();break;
            case 2:txt_news.performClick();break;
            case 3:txt_friend.performClick();break;
            case 4:txt_shopcar.performClick();break;
            case 5:txt_mine.performClick();break;
        }

        String username=getIntent().getStringExtra("username");


    }

    //UI组件初始化与事件绑定
    private void bindViews() {
        txt_top = (TextView) findViewById(R.id.top_tv_01);
        txt_firstpage = (TextView) findViewById(R.id.tab_menu_firstpage);
        txt_news = (TextView) findViewById(R.id.tab_menu_news);
        txt_friend= (TextView) findViewById(R.id.tab_menu_friend);
        txt_shopcar = (TextView) findViewById(R.id.tab_menu_shopcar);
        txt_mine=findViewById(R.id.tab_menu_mine);
        ly_content = (FrameLayout) findViewById(R.id.ly_content);

        txt_firstpage.setOnClickListener(this);
        txt_news.setOnClickListener(this);
        txt_friend.setOnClickListener(this);
        txt_shopcar.setOnClickListener(this);
        txt_mine.setOnClickListener(this);
    }

    //重置所有文本的选中状态
    private void setSelected(){
        txt_firstpage.setSelected(false);
        txt_news.setSelected(false);
        txt_friend.setSelected(false);
        txt_shopcar.setSelected(false);
        txt_mine.setSelected(false);
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(FirstpageF != null)fragmentTransaction.hide(FirstpageF);
        if(NewsF != null)fragmentTransaction.hide(NewsF);
        if(FriendF != null)fragmentTransaction.hide(FriendF);
        if( ShopcarF!= null)fragmentTransaction.hide(ShopcarF);
        if(MineF!=null)fragmentTransaction.hide(MineF);
    }


    @Override
    public void onClick(View v) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);
        switch (v.getId()){
            case R.id.tab_menu_firstpage:
                setSelected();
                //txt_top.setText("FirstPage");

                txt_firstpage.setSelected(true);
                if(FirstpageF == null){
                    FirstpageF = new FirstpageFragment();
                    fTransaction.add(R.id.ly_content,FirstpageF);
                }else{
                    fTransaction.show(FirstpageF);
                }
                break;
            case R.id.tab_menu_news:
                setSelected();

                txt_top.setText("新闻资讯");

                txt_news.setSelected(true);
                if(NewsF == null){
                    NewsF = new NewsFragment();
                    fTransaction.add(R.id.ly_content,NewsF);
                }else{
                    fTransaction.show(NewsF);
                }
                break;
            case R.id.tab_menu_friend:
                setSelected();
                txt_top.setText("friend");
                txt_friend.setSelected(true);
                if(FriendF == null){
                    FriendF = new FriendFragment();
                    fTransaction.add(R.id.ly_content,FriendF);
                }else{
                    fTransaction.show(FriendF);
                }
                break;
            case R.id.tab_menu_shopcar:
                setSelected();

                txt_top.setText("购物车");

                txt_shopcar.setSelected(true);
                if(ShopcarF == null){
                    ShopcarF = new ShopcarFragment();
                    fTransaction.add(R.id.ly_content,ShopcarF);
                }else{
                    fTransaction.show(ShopcarF);
                }
                break;
            case R.id.tab_menu_mine:
                setSelected();


                txt_top.setText("我的信息");

                txt_mine.setSelected(true);
                if(MineF == null){
                    MineF = new MineFragment();
                    fTransaction.add(R.id.ly_content,MineF);

                }else{
                    fTransaction.show(MineF);
                }
                break;
        }
        fTransaction.commit();
    }
}
