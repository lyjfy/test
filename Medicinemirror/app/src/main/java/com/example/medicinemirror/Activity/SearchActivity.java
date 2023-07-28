package com.example.medicinemirror.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.medicinemirror.Http.Herbselect;
import com.example.medicinemirror.Http.ReThred;
import com.example.medicinemirror.Model.Herbmedicine;
import com.example.medicinemirror.R;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private TextView content,title;
    private Button button;
    private ListView searchlist;
    private List<Herbmedicine> list=new ArrayList<>();
    private String intrduce;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        //隐藏标题栏
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        //获取控件
        content=findViewById(R.id.search1);
//        title=findViewById(R.id.ces);
        button=findViewById(R.id.search_button1);
        searchlist=findViewById(R.id.searchlist);
        //赋值
        content.setText(getIntent().getStringExtra("content"));
        herbresult();
        //搜索监听
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //类与JSON相互转化
//                Herbmedicine herbmedicine=new Herbmedicine("123","1234","12345");
//                String s=new Gson().toJson(herbmedicine);
//                String a="{\"content\":\"12345\",\"title\":\"1234\"}";
//                System.out.println("++++++++++++"+s);
//                Herbmedicine herbmedicine1=JSONObject.parseObject(a,Herbmedicine.class);
//                System.out.println("-------------------"+herbmedicine1.getTitle());
                herbresult();

            }
        });
        searchlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(SearchActivity.this,HerbdetailsActivity.class);
                intent.putExtra("title",list.get(position).getTitle());
                intent.putExtra("content",list.get(position).getContent());
                intent.putExtra("herbpicturepath",list.get(0).getHerbpicturepath());
                startActivity(intent);
            }
        });

    }//oncreat
    public void herbresult(){
        Herbselect herbselect=new Herbselect(content.getText().toString());
        herbselect.select();
        list=herbselect.getList();
        if (!(list==null||list.size()==0)){
            HistroyAdapter histroyAdapter=new HistroyAdapter(list,SearchActivity.this);
            searchlist.setAdapter(histroyAdapter);
        }else {
            searchlist.setAdapter(null);
            Toast.makeText(SearchActivity.this, "未查询到", Toast.LENGTH_SHORT).show();
        }
    }
}
