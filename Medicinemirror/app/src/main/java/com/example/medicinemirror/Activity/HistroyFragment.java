package com.example.medicinemirror.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.example.medicinemirror.Http.ReThred;
import com.example.medicinemirror.Model.Cohistroy;
import com.example.medicinemirror.R;



import java.util.ArrayList;
import java.util.List;

public class HistroyFragment extends Fragment {
    private ImageButton deleteall;
    private boolean isGetData = false;
    private ListView histroylist;
    private List<Cohistroy> list=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.histroy,container,false);
        //获取控件
        deleteall=v.findViewById(R.id.deleteall);
        histroylist=v.findViewById(R.id.historylist);
        /*获取识别历史list*/
        String result= gethistorylist();
        if (result.equals("fail")){
        }else {
            CohistroyAdapter cohistroyAdapter=new CohistroyAdapter(list,getContext(),"delete");
            histroylist.setAdapter(cohistroyAdapter);
        }

        //删除监听
        deleteall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(deleteall.getContext());
                builder.setTitle("温馨提示");
                builder.setMessage("您确认要删除所有历史记录吗？");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setPositiveButton("确认",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                                String deletepath="http://192.168.43.45:9988/deletedynamic?dyid="+list.get(position).getDyid();
//                                String deresult=Customthread.Thread(deletepath,null,"GET");
                                String deresult="success";
                                if (deresult.equals("success")){
                                    Toast.makeText(deleteall.getContext(),  "删除成功！", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(deleteall.getContext(), "删除失败！请重试", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });
        return v;
    }//oncreatview
    /*获取识别历史*/
    public String gethistorylist(){
        SharedPreferences user=getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        String historypath="http://192.168.43.27:9090/cohistroy/selecthistroylist?username="+user.getString("user_id","123");
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
            list=JSONObject.parseArray(reThred.getResult(),Cohistroy.class);
        }
        return "success";
    }
}

