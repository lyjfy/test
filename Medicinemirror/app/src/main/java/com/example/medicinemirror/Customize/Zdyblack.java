package com.example.medicinemirror.Customize;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import com.example.medicinemirror.R;


public class Zdyblack extends LinearLayout {
    private ImageView imageView;

    public Zdyblack(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //加载布局
        LayoutInflater.from(context).inflate(R.layout.zdyblack,this);
        //加载布局控件
        imageView=findViewById(R.id.back);
        //设置监听事件
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)getContext()).finish();
            }
        });

    }
}
