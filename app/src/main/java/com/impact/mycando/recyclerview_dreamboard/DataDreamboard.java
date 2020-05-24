package com.impact.mycando.recyclerview_dreamboard;

import android.util.Log;

public class DataDreamboard {
    // 꿈 설정
    public String dreamnameStr;
    // 꿈 메모
    public String dreamexplainStr;
    // 꿈 이미지 uri
    public String dreamimageUriStr;

    // 꿈 입력했을 시 입력값이 출력되도록
    public DataDreamboard(String dreamname, String dreamexplain, String dreamimageuri){
        dreamnameStr = dreamname;
        dreamexplainStr = dreamexplain;
        dreamimageUriStr = dreamimageuri;
    }

    public String getDreamnameStr() {
        Log.v("데이터 클래스 getDreamnameStr 확인 알림 로그","dreamnameStr " + dreamnameStr);
        return this.dreamnameStr;
    }

    public String getDreamexplainStr() {
        Log.v("데이터 클래스 getDreamexplainStr 확인 알림 로그","getDreamexplainStr " + dreamexplainStr);
        return this.dreamexplainStr;
    }

    public String getDreamimageUriStr() {
        Log.v("데이터 클래스 dreamimageUriStr 확인 알림 로그","dreamimageUriStr " + dreamimageUriStr);
        return this.dreamimageUriStr;
    }
}

