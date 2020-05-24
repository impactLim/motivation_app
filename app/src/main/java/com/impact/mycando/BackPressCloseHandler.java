package com.impact.mycando;

import android.app.Activity;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

public class BackPressCloseHandler {

    // 0.    시간을 저장하는 변수(t) = 0;
    Toast toast;
    private long backKeyPressedTime = 0;
    private Activity activity;

    public BackPressCloseHandler(Activity context) {
        this.activity = context;
    }

    // 메인화면에서 뒤로 가기 눌렀을 때 실행 메쏘드
    public void onBackPressed() {

        // 뒤로가기 버튼 클릭 후 2초가 지나면, 토스트 메시지 다시 띄워줌
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {

            backKeyPressedTime = System.currentTimeMillis();
            showGuide();

            return;

        }

        // 1.    뒤로가기 버튼 (처음)클릭시 시간을 저장하는 변수(t) + 2000(2초)가 현재 시간보다 작다.
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            ActivityCompat.finishAffinity(activity); // 뒤로 버튼 두 번 누르면 어플 종료된다.

//          https://blog.asamaru.net/2015/12/15/android-app-finish/
//          https://dsnight.tistory.com/14

            toast.cancel();

        }
    }

    // 토스트 메시지 띄워줌
    public void showGuide() {

        toast = Toast.makeText(activity, "뒤로 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
        toast.show();

    }
}