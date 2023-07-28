package com.example.medicinemirror.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.example.medicinemirror.Http.ReThred;
import com.example.medicinemirror.Model.User;
import com.example.medicinemirror.R;
import com.example.medicinemirror.Utils.Base64Util;
import com.example.medicinemirror.Utils.FileUtil;
import com.example.medicinemirror.Utils.HttpUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomeFragment extends Fragment {
    private ViewPager viewPager;
    private LinearLayout photograph,collect1,laye,poison,root,fruits,grass,flower,leaves,other;
//    private looperpagerAdapter mLooperPagerAdapter;
    private static List<String> sColors=new ArrayList<>();
    private EditText search;
    private Button search_button;
    public static final int TAKE_PHOTO = 1,Alum=2;
    private ImageView picture;
    private Uri imageUri;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.home1,container,false);
        //调用初始化轮播图方法
        initview(v);
        //调用搜索和拍照事件监听方法
        photographclicklistener(v);
        //调用收藏，药材分类等跳转页面监听方法
        otherclicklistener(v);
        return v;
    }//oncreateview
    //初始化轮播图
    private void initview(View v){
        /*viewPager=v.findViewById(R.id.viewpages);
        //准备数据
        mLooperPagerAdapter=new looperpagerAdapter();
        //在适配器类中添加一个方法来设置数据
        String picturepath="http://192.168.43.27:9090/user/lbpicture";
//        String picturepath="http://192.168.31.1:9090/user/lbpicture";
        ReThred reThred=new ReThred();
        reThred.setUrl(picturepath);
        reThred.setType("get");
        reThred.start();
        try {
            reThred.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 构造正则表达式，匹配<>中的字符
        System.out.println("HomeFragmentHomeFragment123123"+reThred.getResult());
        Pattern pattern = Pattern.compile("<([^>]*)>");
        Matcher matcher = pattern.matcher(reThred.getResult());
        // 遍历匹配结果，将每个<>中的字符加入集合
        while (matcher.find()) {
            sColors.add(matcher.group(1));
        }
        mLooperPagerAdapter.setData(sColors);
        viewPager.setAdapter(mLooperPagerAdapter);
        mLooperPagerAdapter.notifyDataSetChanged();
        //轮播图的初始位置
        viewPager.setCurrentItem(mLooperPagerAdapter.getDataResultSizse(),false);*/
    }
    //搜索和拍照事件监听
    private void photographclicklistener(View v){
        //获取控件
        photograph=v.findViewById(R.id.photograph);
        search=v.findViewById(R.id.search);
        search_button=v.findViewById(R.id.search_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),SearchActivity.class);
                intent.putExtra("content",search.getText().toString());
                startActivity(intent);
            }
        });
        photograph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setTitle("请选择方式");
                builder.setNegativeButton("拍照", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
/*
                第一步
                创建File对象，用于存储拍照后的照片，并将它存放在手机SD卡的应用关联缓存目录下。
                应用关联缓存目录：指SD卡中专门用于存放当前应用缓存数据的位置，调用getExternalCacheDir()方法可以得到该目录。
                具体的路径是/sdcard/Android/data/<package name>/cache .
                */
                        File outputImage = new File(getContext().getExternalCacheDir(),"output_image.jpg");
                        try {
                            if (outputImage.exists()){
                                outputImage.delete();
                            }
                            outputImage.createNewFile();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                /*
                第二步
                对当前运行设备的系统版本进行判断，低于Android7.0，就调用Uri.fromFile(outputImage);
                否则，就调用FileProvider的getUriForFile()方法
                  */
                        if(Build.VERSION.SDK_INT >= 24 ){
                            imageUri = FileProvider.getUriForFile(getContext(),
                                    "com.example.medicinemirror",outputImage);
                        }else{
                            imageUri = Uri.fromFile(outputImage);
                        }
                        //启动相机程序
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                        startActivityForResult(intent,TAKE_PHOTO);
                    }
                });
                builder.setPositiveButton("相册",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //获取手机权限
                                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
                                }
                                Intent intent=new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(intent,Alum);
                            }
                        });
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });
    }
    //收藏，药材分类等跳转页面监听
    private void otherclicklistener(View v){
        //获取控件
//        collect1=v.findViewById(R.id.collect1);
        laye=v.findViewById(R.id.laye);
        poison=v.findViewById(R.id.poison);
        root=v.findViewById(R.id.root);
        fruits=v.findViewById(R.id.fruits);
        grass=v.findViewById(R.id.grass);
        flower=v.findViewById(R.id.flower);
        leaves=v.findViewById(R.id.leaves);
        other=v.findViewById(R.id.other);
        /*collect1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),CollectActivity.class);
                startActivity(intent);
            }
        });*/
        laye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),CommonActivity.class);
                intent.putExtra("content","腊叶标本");
                intent.putExtra("type","laye");
                startActivity(intent);
            }
        });
        poison.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),CommonActivity.class);
                intent.putExtra("content","毒性药材");
                intent.putExtra("type","poison");
                startActivity(intent);
            }
        });
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),CommonActivity.class);
                intent.putExtra("content","根及根茎类");
                intent.putExtra("type","root");
                startActivity(intent);
            }
        });
        fruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),CommonActivity.class);
                intent.putExtra("content","果实种子类");
                intent.putExtra("type","fruits");
                startActivity(intent);
            }
        });
        grass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),CommonActivity.class);
                intent.putExtra("content","全草类");
                intent.putExtra("type","grass");
                startActivity(intent);
            }
        });
        flower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),CommonActivity.class);
                intent.putExtra("content","花类");
                intent.putExtra("type","flower");
                startActivity(intent);
            }
        });
        leaves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),CommonActivity.class);
                intent.putExtra("content","叶类");
                intent.putExtra("type","leaves");
                startActivity(intent);
            }
        });
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),CommonActivity.class);
                intent.putExtra("content","其他类");
                intent.putExtra("type","other");
                startActivity(intent);
            }
        });
    }

    //回调，注意是requestcode，不是resultcode，弄得调试不对
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case Alum:
                //上传图片
                String picturepath=getActivity().getApplicationContext().getExternalCacheDir().getPath()+"/output_image2.jpg";
                try {
                    /*判断是否点击返回按钮*/
                    if (!(data==null)){
                        Bitmap bitmap=MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(),data.getData());
                        String result=baidushibie(picturepath,bitmap);
                        System.out.println("222222222"+result);
                        Intent intent=new Intent(getContext(),BaidushibieActivity.class);
                        intent.putExtra("result",result);
                        startActivity(intent);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case TAKE_PHOTO:
                /*获取结果跳转页面*/
                String headphotopath=getActivity().getApplicationContext().getExternalCacheDir().getPath()+"/output_image1.jpg";
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(getContext().getContentResolver().openInputStream(imageUri));
                    /*判断是否点击返回按钮*/
                    if (!(bitmap ==null)){
                        String result=baidushibie(headphotopath,bitmap);
                        System.out.println("222222222"+result);
                        Intent intent=new Intent(getContext(),BaidushibieActivity.class);
                        intent.putExtra("result",result);
                        startActivity(intent);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }//onactivity
    /*此方法调用识别图片获取到相应结果并返回草药名称识别度等*/
    public String baidushibie(String headphotopath,Bitmap bitmap){
        File file=new File(headphotopath);
        try {
//            Bitmap bitmap = BitmapFactory.decodeStream(getContext().getContentResolver().openInputStream(imageUri));
            if (file.exists()) {
                file.delete();
            } else {
                file.createNewFile();
            }
            /*将盒子提供器的照片复制到新路径，因为提取不到盒子照片路径无效*/
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        /*百度图像识别URL*/
        String url = "https://aip.baidubce.com/rest/2.0/image-classify/v1/plant";
        // 本地文件路径，传入照片新路径开始识别
        String filePath = headphotopath;
        System.out.println("图片路径home111111111111111"+filePath);
        ReThred thred=new ReThred();
        thred.setUrl(url);
        thred.setConnent(filePath);
        thred.setType("baidupost");
        thred.start();
        try {
            thred.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /*返回识别结果*/
        String result=thred.getResult();
        System.out.println("HomeFragment result11111111111111111111"+result);
        JSONObject json= JSON.parseObject(result);
        /*判断是否返回了结果*/
        if (!(json.getString("result") ==null)){
            JSONArray jsonArray=json.getJSONArray("result");
            JSONObject jsonObject=jsonArray.getJSONObject(0);
            if (jsonObject.getString("name").equals("非植物")){
                return "非植物";
            }
            return jsonObject.toString();
        }
        return "fail";
    }
    //
}

