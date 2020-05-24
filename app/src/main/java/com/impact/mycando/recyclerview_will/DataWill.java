package com.impact.mycando.recyclerview_will;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataWill {
    // 들어가는 데이터들 선언

    // 대표 목표
    public String willGoalStr;
    // 오늘의 각오
    public String willContentsStr;
    // 각오 이미지
    public String willimageStr;

    // 현재시간을 msec 으로 구한다.
    private long now = System.currentTimeMillis();
    // 현재시간을 date 변수에 저장한다.
    private Date date = new Date(now);
    // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
    // private SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd");
    private SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd E요일 HH:mm");
    // 시연을 위해 ss까지 표현
    // dateNow 변수에 값을 저장한다.
    private String dateStr = sdfNow.format(date);


    // 생성자 - 한 묶음으로 묶어줌
    public DataWill(String willGoal, String willContents, String willimage, String willdate){
        willContentsStr = willContents;
        willGoalStr = willGoal;
        willimageStr = willimage;
        dateStr = willdate;
        Log.v("오늘의 각오 데이터 클래스 확인 알림 로그","DataWill 생성자 willContents :" + willContents + " willGoal " + willGoal + " willimage " + willimage);
    }

    public String getWillGoalStr() {
        Log.v("오늘의 각오 데이터 클래스  getWillGoalStr 확인 알림 로그"," getWillGoalStr()" + willGoalStr);
        return willGoalStr;
    }


    public String getWillContentsStr() {
        Log.v("오늘의 각오 데이터 클래스  getWillContentsStr() 확인 알림 로그"," getWillContentsStr()" + willContentsStr);
        return willContentsStr;
    }

    public String getWillimageStr() {
        Log.v("오늘의 각오 데이터 클래스  getWillimageStr() 확인 알림 로그"," getWillimageStr()" + willimageStr);
        return willimageStr;
    }

    public String getDateStr() {
        Log.v("오늘의 각오 데이터 클래스  getDateStr() 확인 알림 로그","  getDateStr()" + dateStr);
        return this.dateStr;
    }
}

