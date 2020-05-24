package com.impact.mycando.recyclerview_goalmanagement;

import android.util.Log;

public class DataGoalmanagement {

    // 변수 선언
    private String concentrationGoal;

    boolean goalcheck;


    public String getConcentrationGoal() {

        return concentrationGoal;
    }


    public boolean isGoalcheck() {
        Log.v("isGoalcheck() 확인 알림 로그 ", "" + goalcheck);

        return goalcheck;
    }

    // DataWisesentences 생성자

    public DataGoalmanagement(String ConcentrationGoal, boolean Goalcheck) {

        Log.v("명언 데이터 클래스 확인 로그 알림","DataWisesentences 생성자");
        this.concentrationGoal = ConcentrationGoal;
        this.goalcheck = Goalcheck;
    }
}
//    int i = 0;
//
//    public boolean isIdcheck() {
//
//        if(isIdcheck() == true){
//            return idcheck = true ;
//
//        }else{
//
//            return idcheck = false ;
//
//        }
//
//    }
//
//    boolean idcheck;



