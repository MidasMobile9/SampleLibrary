package com.test.samplelibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CircleImageActivity extends AppCompatActivity {

    @BindView(R.id.circleImageView) CircleImageView circleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_image);
        // ButterKnife 세팅
        ButterKnife.bind(this);
    }
}
