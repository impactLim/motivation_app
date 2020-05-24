package com.impact.mycando.recyclerview_wisesentences;

import android.util.Log;

public class DataWisesentences {

    // 변수 선언
    private String wisesentence;

    // 입력값을 그대로 return 해주는 생성자
    public String getWisesentence() {
        Log.v("명언 데이터 클래스 확인 로그 알림"," getWisesentence() / wisesentence : " + wisesentence);
        return wisesentence;
    }

    // DataWisesentences 생성자
    public DataWisesentences(String Wisesentence) {
        Log.v("명언 데이터 클래스 확인 로그 알림","DataWisesentences 생성자");
        this.wisesentence = Wisesentence;
    }


}
