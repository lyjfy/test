package com.example.medicinemirror.Activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.bumptech.glide.Glide;
import com.example.medicinemirror.Http.Herbselect;
import com.example.medicinemirror.Model.Herbmedicine;
import com.example.medicinemirror.Model.Herbtype;
import com.example.medicinemirror.R;

import java.util.List;

public class HerbtypeAdapter extends BaseAdapter {
    private List<Herbtype> list;
    private Context context;
    private LinearLayout herblinearlayout;//列表行
    private TextView herbname,nodate;//草药名称,没有更多数据
    private ImageView herbpicture;//列表图像

    public HerbtypeAdapter(List<Herbtype> list, Context context) {
        this.list = list;
        this.context = context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(R.layout.histroylist,null);
        //获取控件
        herblinearlayout=convertView.findViewById(R.id.herblinearlayout);
        herbname=convertView.findViewById(R.id.herbname);
        herbpicture=convertView.findViewById(R.id.herbpicture);
        //赋值
        herbname.setText(list.get(position).getTitle());
        if(!(list.get(position).getPicturepath()==null)){
            Glide.with(context).load(list.get(position).getPicturepath()).into(herbpicture);
        }
        /*点击查看详情*/
        herblinearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("position000000000000000000"+position);
                if (list.get(position).getType().equals("laye")){
                    Intent intent=new Intent(context,CeshiActivity.class);
                    intent.putExtra("picturepath",list.get(0).getPicturepath());
                    context.startActivity(intent);
                }else {
                    Herbselect herbselect=new Herbselect(list.get(position).getTitle());
                    herbselect.select();
                    List<Herbmedicine> list1=herbselect.getList();
                    if (!(list1==null||list1.size()==0)){
                        Intent intent=new Intent(context,HerbdetailsActivity.class);
                        intent.putExtra("title",list1.get(0).getTitle());
                        intent.putExtra("content",list1.get(0).getContent());
                        intent.putExtra("herbpicturepath",list1.get(0).getHerbpicturepath());
                        context.startActivity(intent);
                    }else {
                        Toast.makeText(context, "未查询到记录", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        /*显示没有更多数据*//*
        if (position==list.size()){
            System.out.println("position000000000000000000"+position);
            nodate=convertView.findViewById(R.id.nodata);
            nodate.setVisibility(View.VISIBLE);
        }*/
        return convertView;

    }
}
