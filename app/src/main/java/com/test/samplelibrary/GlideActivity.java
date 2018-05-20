package com.test.samplelibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GlideActivity extends AppCompatActivity {

    public static final String URL_IMAGE = "http://35.187.156.145/steadyhard/data/profile_image/steadyhard@steadyhard.com_profileImage.png";

    @BindView(R.id.imageView) ImageView imageView;
    @BindView(R.id.imageView2) ImageView imageView2;
    @BindView(R.id.imageView3) ImageView imageView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        // ButterKnife 세팅
        ButterKnife.bind(this);

        // 1. Glide 사용하여 소스 이미지 가져오기
        Glide.with(GlideActivity.this) // Activity 또는 Fragment의 context
                .load(R.drawable.image_source) // drawable에 저장된 이미지
                .into(imageView); // 이미지를 보여줄 view

        // 2. Glide 사용하여 URL 이미지 가져오기
        Glide.with(GlideActivity.this)
                .load(URL_IMAGE) // 이미지 URL 주소
                .into(imageView2);

        // 3. Glide 사용하여 URL 이미지 + 로딩 중 보여줄 이미지 + 에러 이미지 가져오기
        Glide.with(GlideActivity.this)
                .load(URL_IMAGE + "123")
                .placeholder(R.drawable.image_loading) // 로딩 중 보여줄 이미지
                .error(R.drawable.image_error) // 로딩 실패시 보여줄 에러 이미지
                .into(imageView3);
    }
}
