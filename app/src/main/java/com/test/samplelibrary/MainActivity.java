package com.test.samplelibrary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.buttonGlide) Button buttonGlide;
    @BindView(R.id.buttonCircleImageView) Button buttonCircleImageView;
    @BindView(R.id.buttonButterknife) Button buttonButterknife;
    @BindView(R.id.buttonOkHttp3AndGSON) Button buttonOkHttp3AndGSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ButterKnife 세팅
        ButterKnife.bind(this);
    }

    @OnClick({R.id.buttonGlide, R.id.buttonCircleImageView, R.id.buttonButterknife, R.id.buttonOkHttp3AndGSON})
    public void onButtonClick(View view) {
        Intent intent = null;

        switch ( view.getId() ) {
            case R.id.buttonGlide:
                intent = new Intent(MainActivity.this, GlideActivity.class);
                break;
            case R.id.buttonCircleImageView:
                intent = new Intent(MainActivity.this, CircleImageActivity.class);
                break;
            case R.id.buttonButterknife:
                intent = new Intent(MainActivity.this, ButterKnifeActivity.class);
                break;
            case R.id.buttonOkHttp3AndGSON:
                intent = new Intent(MainActivity.this, OkHTTP3AndGSONActivity.class);
                break;
            default:
                break;
        }

        startActivity(intent);
    }
}
