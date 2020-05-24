package com.impact.mycando;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.impact.mycando.alarm.SetalarmActivity;

public class Realmenu extends AppCompatActivity {

    // 메뉴화면이 아니라 마이페이지 화면
    private AdView mAdView;

    // 알람설정 버튼
    Button btn_setalarm;
    // 공유하기 버튼
    Button btn_share;

    // 문의하기 버튼
    Button btn_ask;
    // 뒤로가기하기 버튼
    ImageButton btn_back;

    Button btn_breath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realmenu);

        // 광고 api
        MobileAds.initialize(this, getString(R.string.admob_app_id));

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        // 광고가 제대로 로드 되는지 테스트 하기 위한 코드입니다.
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                // 광고가 문제 없이 로드시 출력됩니다.
                Log.d("@@@", "onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                // 광고 로드에 문제가 있을시 출력됩니다.
                Log.d("@@@", "onAdFailedToLoad " + errorCode);
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });

        // 알람설정 버튼을 누르면 알람설정 화면으로 감
        btn_setalarm = (Button) findViewById(R.id.setalarm_button);
        btn_setalarm.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent(getApplicationContext(), SetalarmActivity.class);
                        startActivity(intent);
                    }
                }
        );

//        // 공유하기 버튼을 누르면 공유하는 다이어로그 띄어짐
//        btn_share = (Button) findViewById(R.id.share_button);
//        btn_share.setOnClickListener(
//                new Button.OnClickListener(){
//                    public void onClick(View v){
//                        // 다이어로그로 바꾸기
//                        Intent intent = new Intent(getApplicationContext(), Dialogappsharedialog.class);
//                        startActivity(intent);
//                    }
//                }
//        );

        // 조급할 때 심호흡 버튼 누르면 심호흡할 수 있는 화면으로 이동함
        btn_breath = (Button) findViewById(R.id.breath_button);
        btn_breath.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent(getApplicationContext(), DeepbreathActivity.class);
                        startActivity(intent);
                    }
                }
        );

        // 문의하기 버튼을 누르면 문의사항 화면으로 감
        btn_ask = (Button) findViewById(R.id.ask_button);
        btn_ask.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent(getApplicationContext(), AskActivity.class);
                        startActivity(intent);
                    }
                }
        );

        // 뒤로가기 버튼을 누르면 프로필편집 화면으로 감
        btn_back = (ImageButton) findViewById(R.id.back_icon);
        btn_back.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                }
        );

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                        intent.putExtra("maingoal", DateGoal);
        startActivity(intent);
    }

}
