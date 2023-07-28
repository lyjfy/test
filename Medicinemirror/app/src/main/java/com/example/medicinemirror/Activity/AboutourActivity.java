package com.example.medicinemirror.Activity;

import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.medicinemirror.R;

public class AboutourActivity extends AppCompatActivity {
    private TextView aboutourtext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.aboutour);
        StringBuffer about=new StringBuffer();
        about.append("      \"照药镜\"是一款为中药材爱好者提供的免费学习软件，是由成都市食品药品检验研究院基于中药识别共享平台数据图谱库，利用人工智能深度学习信息技术建成的一款中药材人工智能识别软件系统。通过手机上传或直接拍摄药材图片，软件即可给出药材名称、性状、功能主治等内容，同时可浏览、查询药材原植物、正品、非正品及劣质品等图片信息，供用户自行学习了解判定药材名称及真伪。\n      本软件诚挚感谢中国科学院植物研究所、中国食品药品检定研究院、成都智析万变科技有限公司、成都荷花池中药材市场的指导和支持。");
        aboutourtext=findViewById(R.id.aboutourtext);
        aboutourtext.setText(about);
    }
}
