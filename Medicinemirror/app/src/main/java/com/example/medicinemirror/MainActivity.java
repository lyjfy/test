package com.example.medicinemirror;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.medicinemirror.Activity.FragmentActivity;
import com.example.medicinemirror.Activity.RegisterActivity;
import com.example.medicinemirror.Model.User;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.regex.Pattern;

// 注册登录
public class MainActivity extends AppCompatActivity {
    private EditText taccount,tpasswad;
    private TextView register;
    private Button login;
    private static String account, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //隐藏标题栏
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        //获取布局的控件
        taccount=findViewById(R.id.account);
        tpasswad=findViewById(R.id.password);
        register=findViewById(R.id.zhuce);
        login=findViewById(R.id.login);


        //设置登录按钮监听
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://192.168.43.27:9090/user/login?username="+taccount.getText().toString()+"&userpwd="+tpasswad.getText().toString();
                if (isPasswordValid(tpasswad.getText().toString())&&isUserNameValid(taccount.getText().toString())){
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(url)
                            .build();
                    Call call = okHttpClient.newCall(request);
                    //创建子线程
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Response response = call.execute();
                                if (response.body().string().equals("success")){
                                    SharedPreferences.Editor editor=getSharedPreferences("user",MODE_PRIVATE).edit();
                                    editor.putString("user_id",taccount.getText().toString());
                                    editor.apply();
                                    //跳转页面
                                    Intent intent=new Intent();
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.setClass(MainActivity.this, FragmentActivity.class);
                                    startActivity(intent);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }else {
                    Toast.makeText(MainActivity.this, "输入账号或者密码格式错误！", Toast.LENGTH_SHORT).show();
                }

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
    // 验证用户名
    public boolean isUserNameValid(String userName) {
        String regex = "^[0-9]{3,8}$";
        return Pattern.matches(regex, userName);
    }

    // 验证密码
    public boolean isPasswordValid(String password) {
        String regex = "^[a-zA-Z0-9]{3,15}$";
        return Pattern.matches(regex, password);
    }
}