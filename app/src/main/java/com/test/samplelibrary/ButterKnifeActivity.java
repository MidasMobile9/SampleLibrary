package com.test.samplelibrary;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ButterKnifeActivity extends AppCompatActivity {

    @BindView(R.id.textViewTitle) TextView textViewTitle;
    @BindView(R.id.buttonClick) Button buttonClick;
    @BindViews({R.id.textViewList1, R.id.textViewList2, R.id.textViewList3}) List<TextView> textList;
    @BindView(R.id.textViewProperty) TextView textViewProperty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butter_knife);
        // ButterKnife 세팅
        ButterKnife.bind(this);

        // 1. 버터나이프를 이용해 View 세팅
        textViewTitle.setText("ButterKnife 사용");

        // 2. 클릭이벤트 설정(OnClickListener 세팅하지 않고 메소드로만 세팅 가능)
        // 기존 방식 : OnClickListener 세팅
        // 버터나이프 방식 : @OnClick 어노테이션에 뷰를 세팅하면 자동으로 onButtonClickByButterKnife() 메소드 세팅.

        // 3. 타입이 같은 여러 개의 뷰를 리스트로 세팅
        for ( int i = 0; i < textList.size(); i++ ) {
            textList.get(i).setText("버터나이프 텍스트 리스트 : " + i);
        }

        // 3 - 1. 단체 속성부여
        final ButterKnife.Action<View> DISABLE = new ButterKnife.Action<View>() {
            @Override public void apply(View view, int index) {
                view.setEnabled(false);

                if ( view instanceof TextView ) {
                    ((TextView)view).setTextColor(Color.BLUE);
                } else {
                    ((TextView)view).setTextColor(Color.BLACK);
                }
            }
        };

        final ButterKnife.Setter<View, Boolean> ENABLED = new ButterKnife.Setter<View, Boolean>() {
            @Override public void set(View view, Boolean value, int index) {
                view.setEnabled(value);

                if ( view instanceof TextView ) {
                    ((TextView)view).setTextColor(Color.GREEN);
                } else {
                    ((TextView)view).setTextColor(Color.BLACK);
                }
            }
        };

        ButterKnife.apply(textList, DISABLE);
        ButterKnife.apply(textList, ENABLED, false);

        // 4. 개별 속성 부여
        ButterKnife.apply(textViewProperty, View.ALPHA, 0.5f);
    }

    /*
     * 지금은 매개변수 타입으로 View를 썼지만 해당 뷰에 대응하는 매개변수 타입 대입하거나 비워도 됨.
     * ex) ~~~(), ~~~(Button button), ~~~(Object object) 등
     *
     * @OnClick() 어노테이션에 View ID를 하나만 세팅했는데 여러개도 가능
     * ex) @OnClick({R.id.button1, R.id.button2, R.id.button3, R.id.button4})
     */
    @OnClick(R.id.buttonClick)
    public void onButtonClickByButterKnife(View view) {
        String viewName = null;

        if ( view instanceof Button ) {
            viewName = ((Button)view).getText().toString().trim();
        } else {
            viewName = "View is not instance of Button.";
        }

        Toast.makeText(ButterKnifeActivity.this, "View Text : " + viewName, Toast.LENGTH_SHORT).show();
    }
}
