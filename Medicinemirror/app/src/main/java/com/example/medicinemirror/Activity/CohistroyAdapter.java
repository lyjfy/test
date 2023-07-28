package com.example.medicinemirror.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.bumptech.glide.Glide;
import com.example.medicinemirror.Http.Herbselect;
import com.example.medicinemirror.Http.ReThred;
import com.example.medicinemirror.Model.Cohistroy;
import com.example.medicinemirror.Model.Herbmedicine;
import com.example.medicinemirror.R;
import com.google.gson.Gson;

import java.util.List;

class CohistroyAdapter extends BaseAdapter {
    private List<Cohistroy> list;
    private String buttonvisible;//适配场景，collect delete，invisible三种
    private Context context;
    private LinearLayout herblinearlayout;//列表行
    private TextView herbname;//草药名称
    private ImageView herbpicture;//列表图像
    private ImageButton deleteherb;//列表删除按钮

    public CohistroyAdapter(List<Cohistroy> list, Context context, String buttonvisible) {
        super();
        this.list = list;
        this.context = context;
        this.buttonvisible=buttonvisible;
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
        deleteherb=convertView.findViewById(R.id.deleteherb);
        if (this.buttonvisible.equals("delete")){
            deleteherb.setVisibility(View.VISIBLE);
        }else if (this.buttonvisible.equals("collect")){
            deleteherb.setVisibility(View.VISIBLE);
            deleteherb.setImageResource(R.drawable.collect);
        }
        //赋值
        herbname.setText(list.get(position).getHerbname());
        if(!(list.get(position).getHerbpicture()==null)){
            Glide.with(context).load(list.get(position).getHerbpicture()).into(herbpicture);
        }
        //点击查看详情
        herblinearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                herbdetails(list.get(position).getHerbname());
            }
        });
        /*收藏与删除监听*/
        deleteherb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonvisible.equals("delete")){
                    deletehistory(list.get(position));
                }else {
                    collectdelete(v,list.get(position));
                }
            }
        });
        return convertView;
    }//getview
    /*点击药品查看详情*/
    public  void herbdetails(String cohistroy){
        Herbselect herbselect=new Herbselect(cohistroy);
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
    /*删除识别历史*/
    public void deletehistory(Cohistroy cohistroy){
        AlertDialog.Builder builder=new AlertDialog.Builder(herbpicture.getContext());
        builder.setTitle("温馨提示");
        builder.setMessage("您确认要删除该识别历史记录吗？");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("确认",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String deletepath="http://192.168.43.27:9090/cohistroy/deletehistroy?username="+cohistroy.getUsername()+"&herbname="+cohistroy.getHerbname();
                        System.out.println("Coadapter333"+deletepath);
                        ReThred reThred=new ReThred();
                        reThred.setUrl(deletepath);
                        reThred.setType("get");
                        reThred.start();
                        try {
                            reThred.join();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        if (reThred.getResult().equals("success")){
                            Toast.makeText(context, "删除成功！", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "删除失败！请重试", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        AlertDialog dialog=builder.create();
        dialog.show();
    }
    /*收藏的删除与再次收藏*/
    public void collectdelete(View v,Cohistroy cohistroy){
        ReThred reThred=new ReThred();
        System.out.println("collectdelete3333333333333"+v.getTag());
        if (v.getTag().equals("yes")){
            String url="http://192.168.43.27:9090/cohistroy/deletecollect?username="+cohistroy.getUsername()+"&herbname="+cohistroy.getHerbname();
            reThred.setUrl(url);
            reThred.setType("get");
            reThred.start();
            try {
                reThred.join();
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println("22222222222"+cohistroy.getUsername()+reThred.getResult());
            if (reThred.getResult().equals("success")){
                ((ImageButton) v).setImageResource(R.drawable.collectbefore);
                v.setTag("no");
            }else {
                Toast.makeText(context, "取消失败", Toast.LENGTH_SHORT).show();
            }
        }else {
            Gson gson=new Gson();
            reThred.setUrl("http://192.168.43.27:9090/cohistroy/insertcollect");
            reThred.setType("post");
            reThred.setConnent(gson.toJson(cohistroy));
            reThred.start();
            try {
                reThred.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("33333333333"+cohistroy.getUsername()+reThred.getResult());
            if (reThred.getResult().equals("success")){
                ((ImageButton) v).setImageResource(R.drawable.collect);
                v.setTag("yes");
            }else {
                Toast.makeText(context, "收藏失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
