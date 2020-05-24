package com.impact.mycando.recyclerview_keepsentences;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataKeepsentences {

    // 변수 선언
    private String keepsentence;

    // 현재시간을 msec 으로 구한다.
    private long now = System.currentTimeMillis();

    // 현재시간을 date 변수에 저장한다.
    private Date date = new Date(now);

    // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
//    private SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd");
    private SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd E요일 HH:mm");
    // 시연을 위해 ss까지 표현

    // dateNow 변수에 값을 저장한다.
    private String dateStr = sdfNow.format(date);



    // 입력값을 그대로 return 해주는
    public String getKeepsentence() {
        Log.v("명심할 것 데이터 클래스 확인 로그 알림","명심할 것 getKeepsentence() : " + keepsentence);
        return keepsentence;
    }

    public String getDateStr() {
        Log.v("명심할 것 데이터 클래스 확인 로그 알림","날짜 getDateStr" + dateStr );

        return this.dateStr;
    }


    // DataKeepsentences 생성자
    public DataKeepsentences(String Keepsentence, String Date) {
        Log.v("명심 데이터 클래스 확인 로그 알림","DataKeepsentences 생성자");
        this.keepsentence = Keepsentence;
        this.dateStr = Date;
        Log.v("명심할 것 데이터 클래스 확인 로그 알림","keepsentence : " + keepsentence + " Keepsentence : " + Keepsentence );

    }


}

