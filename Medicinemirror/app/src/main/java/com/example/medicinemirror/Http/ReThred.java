package com.example.medicinemirror.Http;

import com.example.medicinemirror.Utils.Base64Util;
import com.example.medicinemirror.Utils.FileUtil;
import com.example.medicinemirror.Utils.HttpUtil;
import okhttp3.*;

import java.io.IOException;
import java.net.URLEncoder;

public class ReThred extends Thread{
    private String result,connent,url,type;

    public ReThred() {
    }

    public ReThred(String result, String connent, String url, String type) {
        this.result = result;
        this.connent = connent;
        this.url = url;
        this.type = type;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getConnent() {
        return connent;
    }

    public void setConnent(String connent) {
        this.connent = connent;
    }

    @Override
    public void run() {
        if (type.equals("post")){
            post();
        }else if (type.equals("get")){
            get();
        }else if (type.equals("baidupost")){
            postpicture();
        }
    }
    public void postpicture(){
        try {
            byte[] imgData = FileUtil.readFileByBytes(connent);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "image=" + imgParam;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
//            String accessToken = "24.ba5b687002200e24c9181aa13ecdeb3f.2592000.1681181396.282335-31013867";
            String accessToken = "24.063274404b80b8ad9f42e53fc02d24d4.2592000.1684311458.282335-31013867";

            this.result = HttpUtil.post(url, accessToken, param);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void get(){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            this.result=response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void post(){
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaType,connent);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            this.result=response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
