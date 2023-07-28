package com.example.medicinemirror.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.medicinemirror.R;

import java.util.List;

//首页的页面碎片，最初为首页
public class FragmentActivity extends AppCompatActivity implements View.OnClickListener {
    private int i=0,j=1;
    private  LinearLayout home,histroy,my;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zdyxia);
        //隐藏标题栏
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        //更改图标及字颜色
        histroy(j);
        home(i);
        my(j);
        //获取控件
        home=findViewById(R.id.home);
        histroy=findViewById(R.id.histroy);
        my=findViewById(R.id.my);
        replace(new HomeFragment());
        //设置下方LinearLayout监听事件
        home.setOnClickListener(this);
        histroy.setOnClickListener(this);
        // dynamic.setOnClickListener(this);
        my.setOnClickListener(this);
    }//oncreat
    //fragment替换
    public void replace(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.suipian,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    //自定义监听下方变色
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home:
                //更改首页图标及字颜色
                home(i);
                histroy(j);
//                scancode(j);
                my(j);
                //改变fragment
                replace(new HomeFragment());
                break;
            case R.id.histroy:
                //更改历史图标及字颜色
                home(j);
//                dynamic(j);
                histroy(i);
                my(j);
                replace(new HistroyFragment());
                break;
//            case R.id.dynamic:
//                //更改图标及字颜色
//                search(j);
//                dynamic(i);
//                scancode(j);
//                my(j);
//                replace(new DynamicFragment());
//                break;
            case R.id.my:
                //更改我的图标及字颜色
                home(j);
//                dynamic(j);
                histroy(j);
                my(i);
                replace(new MyFragment());
                break;

        }
    }
    /*-------------------------------------下列导航栏色彩变色-----------------------------------------------*/
    //首页导航栏变色
    public void home(int a){
        ImageView imageView1 = findViewById(R.id.home1);
        TextView textView1 = findViewById(R.id.home2);
        if (a==0){
            textView1.setTextColor(getResources().getColor(R.color.bule,null));
            imageView1.setImageResource(R.drawable.homeafter);
        }else {
            textView1.setTextColor(getResources().getColor(R.color.hui, null));
            imageView1.setImageResource(R.drawable.home);
        }
    }
    //历史导航栏变色
    public void histroy(int a){
        ImageView imageView1 = findViewById(R.id.histroy1);
        TextView textView1 = findViewById(R.id.histroy2);
        if (a==0){
            textView1.setTextColor(getResources().getColor(R.color.bule, null));
            imageView1.setImageResource(R.drawable.historyafter);
        }else {
            textView1.setTextColor(getResources().getColor(R.color.hui, null));
            imageView1.setImageResource(R.drawable.history);
        }
    }
    //我的导航栏变色
    public void my(int a){
        ImageView imageView1 = findViewById(R.id.my1);
        TextView textView1 = findViewById(R.id.my2);
        if (a==0){
            textView1.setTextColor(getResources().getColor(R.color.bule, null));
            imageView1.setImageResource(R.drawable.myafter);
        }else {
            textView1.setTextColor(getResources().getColor(R.color.hui, null));
            imageView1.setImageResource(R.drawable.my);
        }
    }
//    //扫码导航栏变色
//    public void dynamic(int a){
//        ImageView imageView1 = findViewById(R.id.dynamic1);
//        TextView textView1 = findViewById(R.id.dynamic2);
//        if (a==0){
//            textView1.setTextColor(getResources().getColor(R.color.bule, null));
//            imageView1.setImageResource(R.drawable.dynamich);
//        }else {
//            textView1.setTextColor(getResources().getColor(R.color.hui, null));
//            imageView1.setImageResource(R.drawable.dynamic);
//        }
//    }
    /*-------------------------------------申请权限到对应的Fragment-----------------------------------------------*/
    //权限回调
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        //查找activity的fragemnt
//        List<Fragment> fragments=getSupportFragmentManager().getFragments();
//        if (fragments==null){
//            return ;
//        }
//        //依次便利
//        for (Fragment fragement: fragments) {
//            if (fragement!=null){
//                //调用fragement的权限回调
//                fragement.onRequestPermissionsResult(requestCode,permissions,grantResults);
//            }
//        }
//    }
    //activity回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 1.使用getSupportFragmentManager().getFragments()获取到当前Activity中添加的Fragment集合
         * 2.遍历Fragment集合，手动调用在当前Activity中的Fragment中的onActivityResult()方法。
         */
        if (getSupportFragmentManager().getFragments() != null && getSupportFragmentManager().getFragments().size() > 0) {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            for (Fragment mFragment : fragments) {
                mFragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

}
