package com.impact.mycando.recyclerview_will;

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

public class WillclickActivity extends AppCompatActivity {

    // 오늘의 각오 contents 선언
    TextView tv_willexplain;
    // 오늘의 각오 목표 선언
    TextView tv_willgoal;
    // 오늘의 각오 이미지 선언
    ImageView tv_willimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_willclick);

        // 뒤로가기 버튼 누르면 메인액티비티로 이동
        ImageButton btn_back = (ImageButton) findViewById(R.id.back_icon);
        btn_back.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), WillActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );


        // 오늘의 각오 contents 참조
        tv_willexplain = (TextView) findViewById(R.id.textview_willexplain);

        // 오늘의 각오 목표 참조
        tv_willgoal = (TextView) findViewById(R.id.textview_willgoal);

        // 오늘의 각오 이미지 참조
        tv_willimage = (ImageView) findViewById(R.id.will_image);

        // will 어댑로부터 각오 클릭했을 때 받아온 설명값 박아줌
        tv_willexplain.setText(getIntent().getStringExtra("click_willcontents"));
        Log.v("오늘의 각오 아이템 클릭 화면 확인 알림 로그 ","click_willcontents"+ getIntent().getStringExtra("click_willcontents"));

        // 각오 클릭했을 때 받아온 목표값 박아줌
        tv_willgoal.setText(getIntent().getStringExtra("click_willgoal"));
        Log.v("오늘의 각오 아이템 클릭 화면 확인 알림 로그 ","click_willgoal" + getIntent().getStringExtra("click_willgoal"));

//        Bitmap selectedPhoto = null;
//        try {
//            selectedPhoto = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(getIntent().getStringExtra("click_image")) );
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Bitmap resizeBitmap = Bitmap.createScaledBitmap(selectedPhoto, 150, 150, true);
//        tv_willimage.setScaleType(ImageView.ScaleType.FIT_XY);
//        tv_willimage.setImageBitmap(resizeBitmap);

        if(getIntent().getStringExtra("click_image") != null) {
            // 각오 클릭했을 때 받아온 image string 값을 uri로 바꿔줌
            tv_willimage.setImageURI(Uri.parse(getIntent().getStringExtra("click_image")));
            Log.v("오늘의 각오 아이템 클릭 화면 확인 알림 로그 ", "click_image  : " + getIntent().getStringExtra("click_image"));
        }
    }
}

