package com.yuyh.reflection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.yuyh.reflection.annotation.Inject;
import com.yuyh.reflection.annotation.InjectView;
import com.yuyh.reflection.annotation.OnClick;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.hello)
    TextView tvHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Inject.inject(this);

        tvHello.setText("hahaha");
    }

    @OnClick(R.id.hello)
    public void tvHelloClick(View v){

    }
}
