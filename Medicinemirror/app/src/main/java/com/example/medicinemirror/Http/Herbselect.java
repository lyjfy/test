package com.example.medicinemirror.Http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.medicinemirror.Model.Herbmedicine;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
//查询草药的封装，出入URL与药名content，先进行服务器查询未有再进行中草药api接口查询
public class Herbselect {
    private List<Herbmedicine> list=new ArrayList<>();
    private String url="https://apis.tianapi.com/zhongyao/index?key=20349c2f89c123922b60c70ab76eea95&word=";
    private String url1="http://192.168.43.27:9090/herb/selectherb?title=",content;
    private String photourl="http://192.168.43.27:9090/cohistroy/text/?herbname=";

    public Herbselect() {

    }

    public Herbselect( String content) {
        this.content = content;
    }

    public List<Herbmedicine> getList() {
        return this.list;
    }

    public  void select(){
   /*     if (type.equals("jiekou")){
            this.list=jiekou(url,content,type);
        }else if (type.equals("fuwuqi")){
            this.list=jiekou(url1,content,type);
        }
        对搜索到的草药信息进行分解，显示出来
        String result=null;
        if (this.type.equals("jiekou")){
            result=jiekou(url,this.content);
            JSONObject jobject = JSON.parseObject(result);
            if (jobject.getJSONObject("result").getString("code").equals("200")){
                String s= jobject.getJSONObject("result").getString("list");
                JSONArray jobject2= JSON.parseArray(s);
                String b= jobject2.getString(0);
                Herbmedicine herbmedicine=JSONObject.parseObject(b,Herbmedicine.class);
//                JSONObject jobject3 = JSON.parseObject(b);
                String d= herbmedicine.getContent();
                d=d.replaceAll("<p>","\n");
                d=d.replaceAll("</p>","\n");
                d=d.replaceAll("<br>","\n");
                d=d.replaceAll("】","】\n");
                herbmedicine.setContent(d);
                this.list.add(herbmedicine);
            }else {
               this.list=null;
            }
        }else if (type.equals("fuwuqi")){
            result=jiekou(url1,this.content);
            if (result.equals("fail")){
                this.list=null;
            }else {
                this.list=JSONObject.parseArray(result,Herbmedicine.class);
            }*/
            String result=jiekou(url1+content);
            //先进行服务器请求，没有再请求中药接口
            if (!(result.equals("[]")||result.equals("fail"))){
                if (result.equals("输入为空")){
                    this.list=null;
                    System.out.println("全部请求无"+result);
                }else {
                    List<Herbmedicine> list1;
                    list1=JSONObject.parseArray(jiekou(url1+content),Herbmedicine.class);
//                消除<p><br>
                    for (int i=0;i<list1.size();i++){
                        String content=list1.get(i).getContent();
                        content=content.replaceAll("<p>","\n");
                        content=content.replaceAll("</p>","\n");
                        content=content.replaceAll("<br>","\n");
                        content=content.replaceAll("】","】\n");
                        list1.get(i).setContent(content);
                    }
                    this.list=list1;
                    System.out.println("服务器请求"+list1.get(0).getTitle());
                }
            }else{
                result=jiekou(url+this.content);
                System.out.println("5555555555api"+result);
                JSONObject jobject = JSON.parseObject(result);
                if (jobject.getString("code").equals("200")){
                    String s= jobject.getJSONObject("result").getString("list");
                    JSONArray jobject2= JSON.parseArray(s);
                    String b= jobject2.getString(0);
                    Herbmedicine herbmedicine=JSONObject.parseObject(b,Herbmedicine.class);
                    /*获取草药图片路径*/
                    String photopath=jiekou(photourl+content+"图片");
                    System.out.println(photourl+content);
                    System.out.println("获取到的图片路径"+photopath);
                    if (!((photopath==null||photopath.equals("fail"))||photopath.contains("error"))){
                        System.out.println("photopath.contains(\"error\")3333333333"+photopath.contains("error"));
                        herbmedicine.setHerbpicturepath(photopath);
                        /*将查询到的药品信息上传插入到数据库*/
                        Gson gson=new Gson();
                        String connent1=gson.toJson(herbmedicine);
                        System.out.println("++++++++++++++++"+connent1);
                        ReThred reThred=new ReThred();
                        reThred.setUrl("http://192.168.43.27:9090/herb/insertherb");
                        reThred.setConnent(connent1);
                        reThred.setType("post");
                        reThred.start();
                        try {
                            reThred.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("11111111111111111111"+reThred.getResult());
                    }
                    /*对药品内容进行分析*/
                    String d= herbmedicine.getContent();
                    d=d.replaceAll("<p>","\n");
                    d=d.replaceAll("</p>","\n");
                    d=d.replaceAll("<br>","\n");
                    d=d.replaceAll("】","】\n");
                    herbmedicine.setContent(d);
                    this.list.add(herbmedicine);
                    System.out.println("服务器请求"+this.list.get(0).getTitle());
                }else {
                    this.list=null;
                    System.out.println("全部请求无");
                }
            }
    }
    public  String jiekou(String url){
//        List<Herbmedicine> list1=new ArrayList<>();
        ReThred reThred=new ReThred();
        reThred.setType("get");
        reThred.setUrl(url);
        reThred.start();
        try {
            reThred.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       return reThred.getResult();
    }
}
