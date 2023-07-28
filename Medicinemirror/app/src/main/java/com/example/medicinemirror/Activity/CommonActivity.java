package com.example.medicinemirror.Activity;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.alibaba.fastjson.JSONObject;
import com.example.medicinemirror.Http.ReThred;
import com.example.medicinemirror.Model.Cohistroy;
import com.example.medicinemirror.Model.Herbtype;
import com.example.medicinemirror.R;

import java.util.ArrayList;
import java.util.List;


public class CommonActivity extends AppCompatActivity {
    private TextView title;
    private ListView herbtypelist;
    private List<Herbtype> list=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.collect);
        /*获取控件*/
        title=findViewById(R.id.title);
        herbtypelist=findViewById(R.id.collectlist);
        title.setText(getIntent().getStringExtra("content"));
        /*赋值*/
        if (getherbtype(getIntent().getStringExtra("type")).equals("success")){
            HerbtypeAdapter herbtypeAdapter=new HerbtypeAdapter(list,CommonActivity.this);
            herbtypelist.setAdapter(herbtypeAdapter);
        }
    }//oncreat
    /*获取药材类型list集合*/
    public String getherbtype(String type){
        String herbtypepath="http://192.168.43.27:9090/cohistroy/herbtype?type="+type;
        ReThred reThred=new ReThred();
        reThred.setUrl(herbtypepath);
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
            list= JSONObject.parseArray(reThred.getResult(), Herbtype.class);
        }
        return "success";
    }
}
