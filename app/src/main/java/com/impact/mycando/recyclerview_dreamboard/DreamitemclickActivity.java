package com.impact.mycando.recyclerview_dreamboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.impact.mycando.R;

public class DreamitemclickActivity extends AppCompatActivity {

    // 꿈 아이템 - 꿈이름
    TextView tv_dream;
    // 꿈 아이템 - 꿈 메모
    TextView tv_dreamexplain;

    // 꿈 아이템 - 포지션
    int item_position;

    // 꿈 아이템 - 꿈 이미지
    ImageView iv_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dreamitemclick);

        // 뒤로가기 버튼 누르면 메인액티비티로 이동
        ImageButton btn_back = (ImageButton) findViewById(R.id.back_icon);
        btn_back.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        Intent intent = new Intent(getApplicationContext(), DreamboxActivity.class);
                        startActivity(intent);
                        finish();

                    }
                }
        );

        // 꿈 textview 참조
        tv_dream = (TextView) findViewById(R.id.textview_dream);
        tv_dreamexplain = (TextView) findViewById(R.id.textview_dreamexplain);

        // 꿈 이미지 imageview 참조
        iv_image = (ImageView) findViewById(R.id.dream_image);

        // 꿈이름에 클릭했을 때 받아온 값 박아줌
        tv_dream.setText(getIntent().getStringExtra("click_dreamname"));
        Log.v("꿈 아이템 클릭 화면 확인 알림 로그 ","click_dreamname : "+ getIntent().getStringExtra("click_dreamname"));

        // 꿈 클릭했을 때 받아온 설명값 박아줌
        tv_dreamexplain.setText(getIntent().getStringExtra("click_dreamexplain"));
        Log.v("꿈 아이템 클릭 화면 확인 알림 로그 ","click_dreamnameexplain : "+ getIntent().getStringExtra("click_dreamexplain"));

        // 꿈 클릭했을 때 받아온 string 값을 uri로 바꿔서 보내줌
        iv_image.setImageURI(Uri.parse(getIntent().getStringExtra("click_dreamimage")));
        Log.v("꿈 아이템 클릭 화면 uri 확인 알림 로그 ","Uri.parse(getIntent().getStringExtra(\"click_dreamimage\")) : " + Uri.parse(getIntent().getStringExtra("click_dreamimage")) + " getIntent().getStringExtra(\"click_dreamimage\")) :  " + getIntent().getStringExtra("click_dreamimage"));

        // 포지션값도 받아와줌
        item_position = getIntent().getIntExtra("click_position",0); // 일단 0으로 설정 > 다시 확인해줘야함
        Log.v("꿈 아이템 클릭 화면 확인 알림 로그", " item_position : " + item_position);

    }
}
