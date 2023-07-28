package com.example.medicinemirror.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.medicinemirror.Http.ReThred;
import com.example.medicinemirror.Model.Cohistroy;
import com.example.medicinemirror.Model.Herbmedicine;
import com.example.medicinemirror.R;
import com.google.gson.Gson;

public class HerbdetailsActivity extends AppCompatActivity {
    private TextView herbdetailsname,herbdetailscontent;
    private ImageButton herbdetailscollect;
    private ImageView herbdetailspicture;
    private int collect=0;
    private Cohistroy cohistroy=new Cohistroy();
//    private ReThred reThred=new ReThred();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.herbdetails);
        //隐藏标题栏
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        //获取控件
        herbdetailsname=findViewById(R.id.herbdetailsname);
        herbdetailscontent=findViewById(R.id.herbdetailscontent);
        herbdetailscollect=findViewById(R.id.herbdetailscollect);
        herbdetailspicture=findViewById(R.id.herbdetailspicture);
        //获取值
        Intent intent=getIntent();
        SharedPreferences user=getSharedPreferences("user", Context.MODE_PRIVATE);
        //赋值
        herbdetailsname.setText(intent.getStringExtra("title"));
        herbdetailscontent.setText(intent.getStringExtra("content").trim());
        cohistroy.setUsername(user.getString("user_id","123"));
        cohistroy.setHerbname(intent.getStringExtra("title"));
        if(!(intent.getStringExtra("herbpicturepath")==null)){
            Glide.with(HerbdetailsActivity.this).load(intent.getStringExtra("herbpicturepath")).into(herbdetailspicture);
            cohistroy.setHerbpicture(intent.getStringExtra("herbpicturepath"));
        }else {
            cohistroy.setHerbpicture(null);
        }
        //判断是否已收藏
        ReThred reThred1=new ReThred();
        reThred1.setUrl("http://192.168.43.27:9090/cohistroy/selectcollect?username="+user.getString("user_id","123")+"&herbname="+intent.getStringExtra("title"));
        reThred1.setType("get");
        reThred1.start();
        try {
            reThred1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("11111111111111111"+reThred1.getResult());
        String result=reThred1.getResult();
        if (!(result.equals("null"))){
            herbdetailscollect.setImageResource(R.drawable.collect);
            collect=1;
        }
        //收藏监听
        herbdetailscollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson=new Gson();
                ReThred reThred=new ReThred();
                String connent=gson.toJson(cohistroy);
                if (collect==0){
                    reThred.setUrl("http://192.168.43.27:9090/cohistroy/insertcollect");
                    reThred.setConnent(connent);
                    reThred.setType("post");
                    reThred.start();
                    try {
                        reThred.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("==================="+reThred.getResult()+collect);
                    if (reThred.getResult().equals("success")){
                        herbdetailscollect.setImageResource(R.drawable.collect);
                        collect=1;
                    }else {
                        Toast.makeText(HerbdetailsActivity.this, "收藏失败", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    reThred.setUrl("http://192.168.43.27:9090/cohistroy/deletecollect?username="+cohistroy.getUsername()+"&herbname="+cohistroy.getHerbname());
                    reThred.setType("get");
                    reThred.start();
                    try {
                        reThred.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (reThred.getResult().equals("success")){
                        herbdetailscollect.setImageResource(R.drawable.collectbefore);
                        collect=0;
                    }else {
                        Toast.makeText(HerbdetailsActivity.this, "取消收藏失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }//oncreat
}
