package com.example.medicinemirror.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.example.medicinemirror.Http.Herbselect;
import com.example.medicinemirror.Http.ReThred;
import com.example.medicinemirror.Model.Cohistroy;
import com.example.medicinemirror.Model.Herbmedicine;
import com.example.medicinemirror.R;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/*介绍：展示识别后的数据，草药名称以及识别度和图片*/
public class BaidushibieActivity extends AppCompatActivity {
    private LinearLayout herbbaidulinearlayout;//整体行
    private ImageView herbbaidupicture;//草药图片
    private List<Herbmedicine> list=new ArrayList<>();
    private TextView herbbaiduname,shibiedu,jieguo;//识别草药的名字，识别度，以及下方标识
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baidu);
        //隐藏标题栏
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        /*初始化*/
        init();
        /*点击查看详情监听*/
        herbbaidulinearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                history();
                /*传递草药名，内容，图片路径*/
                Intent intent=new Intent(BaidushibieActivity.this,HerbdetailsActivity.class);
                intent.putExtra("title",list.get(0).getTitle());
                intent.putExtra("content",list.get(0).getContent());
                intent.putExtra("herbpicturepath",list.get(0).getHerbpicturepath());
                startActivity(intent);
            }
        });
    }//oncreat
    /*初始化获取控件获取数据并赋值*/
    public void init(){
        herbbaidulinearlayout=findViewById(R.id.herbbaidulinearlayout);
        herbbaidupicture=findViewById(R.id.herbbaidupicture);
        herbbaiduname=findViewById(R.id.herbbaiduname);
        shibiedu=findViewById(R.id.shibiedu);
        jieguo=findViewById(R.id.jieguo);
        /*获取页面跳转的数据*/
        Intent intent=getIntent();
        String result=intent.getStringExtra("result");
//        String result="{\"score\":0.77410173,\"name\":\"忍冬\"}";
        if (result.equals("非植物")||result.equals("fail")){
            /*未识别或者识别非植物，将表示数据的linerlayout隐藏
             * 并下方标识设置为未识别到*/
            herbbaidulinearlayout.setVisibility(View.INVISIBLE);
            jieguo.setText("小镜未识别出，请重试!");
            Toast.makeText(this, "小镜未识别出，请重试", Toast.LENGTH_SHORT).show();
        }else {
            /*获取到的数据赋值*/
            JSONObject json= JSON.parseObject(result);
            String name=json.getString("name");
            System.out.println(name);
            Herbselect herbselect=new Herbselect(name);
            System.out.println(json.getString("name")+"333333333333333");
            herbselect.select();
            list=herbselect.getList();
            if (!(list==null||list.size()==0)){
                System.out.println(list+"44444444444");
                herbbaiduname.setText(list.get(0).getTitle());
                Double score=Double.parseDouble(json.getString("score"));
                DecimalFormat decimalFormat=new DecimalFormat("0.00%");
                String score1=decimalFormat.format(score);
                shibiedu.setText("识别度："+score1);
                if (!(list.get(0).getHerbpicturepath()==null)){
                    Glide.with(BaidushibieActivity.this).load(list.get(0).getHerbpicturepath()).into(herbbaidupicture);
                }
            }else {
                herbbaidulinearlayout.setVisibility(View.INVISIBLE);
                jieguo.setText("小镜未识别出，请重试!");
                Toast.makeText(this, "小镜未识别出，请重试", Toast.LENGTH_SHORT).show();
            }

        }

    }
    /*点击详情将识别传到上传识别记录*/
    public void history(){
        SharedPreferences user=getSharedPreferences("user", Context.MODE_PRIVATE);
        Cohistroy cohistroy=new Cohistroy(user.getString("user_id","123"),list.get(0).getTitle(),list.get(0).getHerbpicturepath());
        Gson gson=new Gson();
        String connent=gson.toJson(cohistroy);
        ReThred reThred=new ReThred();
        reThred.setUrl("http://192.168.43.27:9090/cohistroy/inserthistroy");
        reThred.setConnent(connent);
        reThred.setType("post");
        reThred.start();
        try {
            reThred.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("BaidushibieActivity11111"+reThred.getResult());
    }
}
