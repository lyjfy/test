package com.example.medicinemirror.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.medicinemirror.Http.ReThred;
import com.example.medicinemirror.Model.User;
import com.example.medicinemirror.R;
import com.google.gson.Gson;

public class PeoplenewsActivity extends AppCompatActivity {
    private EditText peoaccount,peosex,peotel,peoadress;
    private Button alter,edit;
    private User user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.peoplenews);
        //隐藏标题栏
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        //初始化获取控件
       init();
       //修改信息
       alter();

    }//oncreat
    public void init(){
        peoaccount=findViewById(R.id.peoaccount);
        peosex=findViewById(R.id.peosex);
        peotel=findViewById(R.id.peotel);
        peoadress=findViewById(R.id.peoadress);
        alter=findViewById(R.id.newsalter);
        edit=findViewById(R.id.editbuttton);
        edit.setBackground(null);
        peoaccount.setBackground(null);
        peosex.setBackground(null);
        peotel.setBackground(null);
        peoadress.setBackground(null);
        //赋值
        SharedPreferences user1=getSharedPreferences("user",MODE_PRIVATE);
        String path="http://192.168.43.27:9090/user/select?username="+user1.getString("user_id","123");
        ReThred thred=new ReThred();
        thred.setType("get");
        thred.setUrl(path);
        thred.start();
        try {
            thred.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String result=thred.getResult();
        if(result.equals("fail")){
            Toast.makeText(this, "服务器出错！请稍后重试", Toast.LENGTH_SHORT).show();
        }else {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>"+result);
            JSONObject jsonObject= JSON.parseObject(result);
            user=JSONObject.toJavaObject(jsonObject,User.class);
            peoaccount.setText(user.getUsername());
            peosex.setText(user.getUsersex());
            peotel.setText(user.getUsertel());
            peoadress.setText(user.getUseradress());
        }
    }
    public void alter(){
        //设置编辑监听
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                peosex.setEnabled(true);
                peotel.setEnabled(true);
                peoadress.setEnabled(true);
                alter.setVisibility(View.VISIBLE);
            }
        });
        alter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setUseradress(peoadress.getText().toString());
                user.setUsersex(peosex.getText().toString());
                user.setUsertel(peotel.getText().toString());
                Gson gson=new Gson();
                String content=gson.toJson(user);
                String path="http://192.168.43.27:9090/user/updateuser";
                ReThred thred=new ReThred();
                thred.setType("post");
                thred.setUrl(path);
                thred.setConnent(content);
                thred.start();
                try {
                    thred.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String result=thred.getResult();
                if(result.equals("fail")){
                    Toast.makeText(PeoplenewsActivity.this, "服务器出错！请稍后重试", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(PeoplenewsActivity.this, "个人信息修改成功！", Toast.LENGTH_SHORT).show();
                    alter.setVisibility(View.GONE);
                    peosex.setEnabled(false);
                    peotel.setEnabled(false);
                    peoadress.setEnabled(false);
                }
            }
        });
    }
}
