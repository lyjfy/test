package com.example.medicinemirror.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.bumptech.glide.Glide;
import com.example.medicinemirror.Model.Herbmedicine;
import com.example.medicinemirror.R;

import java.util.List;

class HistroyAdapter extends BaseAdapter {
    private List<Herbmedicine> list;
    private String buttonvisible;//适配场景，collect delete，invisible三种
    private Context context;
    private LinearLayout herblinearlayout;//列表行
    private TextView herbname;//草药名称
    private ImageView herbpicture;//列表图像
    private ImageButton deleteherb;//列表删除按钮

    public HistroyAdapter(List<Herbmedicine> list, Context context) {
        super();
        this.list = list;
        this.context = context;
//        this.buttonvisible=buttonvisible;
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
        if(!(list.get(position).getHerbpicturepath()==null)){
            Glide.with(context).load(list.get(position).getHerbpicturepath()).into(herbpicture);
        }
        /*设置删除监听
        deleteherb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
//                                String deletepath="http://192.168.43.45:9988/deletedynamic?dyid="+list.get(position).getDyid();
//                                String deresult=Customthread.Thread(deletepath,null,"GET");
                                String deresult="success";
                                if (deresult.equals("success")){
                                    Toast.makeText(context, "删除成功！", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(context, "删除失败！请重试", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });*/
        return convertView;
    }
}
