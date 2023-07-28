package com.example.medicinemirror.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.alibaba.fastjson.JSONObject;
import com.example.medicinemirror.Http.ReThred;
import com.example.medicinemirror.Model.Cohistroy;
import com.example.medicinemirror.R;

import java.util.ArrayList;
import java.util.List;

public class CollectActivity extends AppCompatActivity {
    private ImageButton collect;
    private ListView collectlist;
    private List<Cohistroy> list=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.collect);
        /*获取控件*/
        collectlist=findViewById(R.id.collectlist);
        String result= getcollectlist();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>收藏result"+result);
        if (result.equals("fail")){
            System.out.println(">>>>>>>>>>>>>>>收藏获取失败！");
        }else {
            CohistroyAdapter cohistroyAdapter=new CohistroyAdapter(list,CollectActivity.this,"collect");
            collectlist.setAdapter(cohistroyAdapter);
        }
    }//oncreat
    /**/
    /*获取收藏*/
    public String getcollectlist(){
        SharedPreferences user=getSharedPreferences("user", Context.MODE_PRIVATE);
        String historypath="http://192.168.43.27:9090/cohistroy/selectcollectlist?username="+user.getString("user_id","123");
        ReThred reThred=new ReThred();
        reThred.setUrl(historypath);
        reThred.setType("get");
        reThred.start();
        try {
            reThred.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (reThred.getResult().equals("fail")){
            return "fail";
        }else {
            list= JSONObject.parseArray(reThred.getResult(),Cohistroy.class);
        }
        return "success";
    }
}
