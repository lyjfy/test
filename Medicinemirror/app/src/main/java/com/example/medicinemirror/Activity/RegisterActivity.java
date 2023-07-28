package com.example.medicinemirror.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.medicinemirror.Http.ReThred;
import com.example.medicinemirror.MainActivity;
import com.example.medicinemirror.Model.User;
import com.example.medicinemirror.R;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
import java.util.regex.Pattern;

//用户注册页面
public class RegisterActivity extends AppCompatActivity {
    private EditText eusername,etel,eadress,epasswad,epasswadagain;
    private String username,tel,adress,passwad,passwadagain,sex;
    private Button register;
    private RadioGroup rsex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        //隐藏标题栏
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        //获取布局控件
        eusername=findViewById(R.id.account);
        etel=findViewById(R.id.tel);
        eadress=findViewById(R.id.adress);
        epasswad=findViewById(R.id.passwad);
        epasswadagain=findViewById(R.id.passwadagain);
        rsex=findViewById(R.id.sex);
        register=findViewById(R.id.register);

        //注册监听
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取控件的值
                username=eusername.getText().toString();
                for (int i=0;i<rsex.getChildCount();i++){
                    RadioButton r=(RadioButton) rsex.getChildAt(i);
                    if (r.isChecked()){//获取性别
                        sex=r.getText().toString();
                        break;
                    }
                }
                tel=etel.getText().toString();
                adress=eadress.getText().toString();
                passwad=epasswad.getText().toString();
                passwadagain=epasswadagain.getText().toString();
                //进行判断
                if ((passwad.equals(passwadagain))&&isPasswordValid(passwad)){//判断密码格式是否正确以及是否一致
                    if (isUserNameValid(username)){//判断账号格式是否正确
                        if (isMobileValid(tel)){//判断电话格式是否正确
                            if (isAdressValid(adress)){
                                User user=new User(username,passwad,sex,tel,adress);
                                Gson gson=new Gson();
                                String connent=gson.toJson(user);
                                String result;
                                ReThred reThred=new ReThred();
                                reThred.setUrl("http://192.168.43.27:9090/user/register");
                                reThred.setConnent(connent);
                                reThred.setType("post");
                                reThred.start();
                                try {
                                    reThred.join();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                result=reThred.getResult();
                                System.out.println("-----------------------------result"+result);

                                if(result.equals("success")){//判断是否注册过
                                    Toast.makeText(RegisterActivity.this,"注册成功！",Toast.LENGTH_LONG).show();
                                    finish();//退回上个activity
                                }else if(result.equals("fail")) {
                                    Toast.makeText(RegisterActivity.this,"此账号已经注册，请登录！",Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(RegisterActivity.this,"地址填写错误！！",Toast.LENGTH_SHORT).show();
                            }

                        }else {
                            Toast.makeText(RegisterActivity.this,"联系电话格式错误！",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(RegisterActivity.this,"账号格式错误！",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(RegisterActivity.this,"两次密码不一致请重新输入或格式错误！",Toast.LENGTH_SHORT).show();
                    epasswad.setText("");
                    epasswadagain.setText("");
                }


            }
        });
    }//oncreat
    // 验证用户名
    public boolean isUserNameValid(String userName) {
        String regex = "^[0-9]{3,8}$";
        return Pattern.matches(regex, userName);
    }

    // 验证密码
    public boolean isPasswordValid(String password) {
        String regex = "^[a-zA-Z0-9_-]{3,15}$";
        return Pattern.matches(regex, password);
    }

    // 验证邮箱
    public boolean isAdressValid(String adress) {
        String regex = "^[\u4e00-\u9fa5]+$";
        return Pattern.matches(regex, adress);
    }

    // 验证手机号码
    public boolean isMobileValid(String mobile) {
        String regex = "^1[3|4|5|7|8][0-9]\\d{8}$";
        return Pattern.matches(regex, mobile);
    }

/*    调用重写子线程传入路径，传输内容，以及发送方法GET或POST
    public String Thread(String path,String connect,String manner){
        String result = null;
////     System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ result);
//        ReThread reThread=new ReThread();
//        reThread.setPath(path);
//        reThread.setContent(connect);
//        reThread.setManner(manner);
//        reThread.start();
////     System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+path);
//        try {
//            reThread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        result=reThread.getResult();

        return result;
    }
                                String url = "http://192.168.43.27:9090/user/register";
                            OkHttpClient okHttpClient = new OkHttpClient();
                            MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
                            RequestBody requestBody = RequestBody.create(mediaType,connent);
                            Request request = new Request.Builder()
                                    .url(url)
                                    .post(requestBody)
                                    .build();
                            Call call = okHttpClient.newCall(request);
创建子线程
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
//                                        Response response = call.execute();
//   response.body().string()只能读一次，多次报错   System.out.println("-----------------------------"+response.body().string());
                                        if (response.body().string().equals("success")){
                                            System.out.println("------------------------success");
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();*/
}
