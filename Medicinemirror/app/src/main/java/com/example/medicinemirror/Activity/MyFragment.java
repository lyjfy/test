package com.example.medicinemirror.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.example.medicinemirror.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MyFragment extends Fragment {
    private TableRow peoplenews,collect,aboutour,compact,secret,version;
    private ImageView touxiang;
    private TextView name;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //加载布局
        View view=inflater.inflate(R.layout.my,container,false);
        //获取控件
        peoplenews=view.findViewById(R.id.peoplenews);
        collect=view.findViewById(R.id.collect);
        aboutour=view.findViewById(R.id.aboutour);
        compact=view.findViewById(R.id.compact);
        secret=view.findViewById(R.id.secret);
        version=view.findViewById(R.id.version);
        touxiang=view.findViewById(R.id.touxiang);
        name=view.findViewById(R.id.name);
        //赋值
        Glide.with(touxiang.getContext()).load("https://cn.bing.com/sa/simg/hpb/LaDigue_EN-CA1115245085_1920x1080.jpg").into(touxiang);
        name.setText("123");
        //设置监听
        peoplenews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),PeoplenewsActivity.class);
                startActivity(intent);
            }
        });
        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),CollectActivity.class);
                startActivity(intent);
            }
        });
        aboutour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),AboutourActivity.class);
                startActivity(intent);
            }
        });
        compact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        secret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(version.getContext(), "已经是最新版本了！", Toast.LENGTH_SHORT).show();
            }
        });
        touxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取手机权限
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
                }
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,1);
            }
        });
        return view;
    }//oncreatview

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1){
//            touxiang.setImageURI(data.getData());
            if (data==null)
            System.out.println("data.getData()111111111111111111");
           /* SharedPreferences user=getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
            String headphotopath=getActivity().getApplicationContext().getExternalCacheDir().getPath()+"/"+user.getString("user_id","12")+".jpg";
            File file=new File(headphotopath);
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(),data.getData());
                if (file.exists()){
                    file.delete();
                }else {
                    file.createNewFile();
                }
                FileOutputStream out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG,30,out);
                out.flush();
                out.close();
                String result1= Customthread.Thread("http://192.168.43.45:9988/api/postuserpic",headphotopath,"POSTPHOTO");
                if (result1.equals("success")){
                    imageView.setImageURI(data.getData());
                }else {
                    Toast.makeText(getContext(), "头像更换失败，请重试", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
    }
}
