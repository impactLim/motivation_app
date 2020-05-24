package com.impact.mycando.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class DeviceBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.equals(intent.getAction(), "android.intent.action.BOOT_COMPLETED")) {

            // on device boot complete, reset the alarm
            Intent alarmIntent = new Intent(context, AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);

            AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            SharedPreferences userid;
            String useridString;
            SharedPreferences testUserInfo;
            String thisUserinfo;

            String alaramIDcheck;
            String alaramNicknamecheck;

            // 아이디별로 알람 다르게
            // 로그인할 때 저장해준 아이디를 가지고 있는 쉐어드 파일을 불러온다.
            // SharedPreferences userid
            userid = context.getSharedPreferences("useridFile", MODE_PRIVATE);

            // 로그인할 때 저장해준 벨류값을 가지고 있는 key를 useridString에 넣어줘서 이 key로 언제든지 회원정보에 접근할 수 있게 한다.
            // String useridString
            useridString = userid.getString("userid","");

            // 저장된 회원정보를 불러오기 위해 같은 네임파일을 찾음.
            // SharedPreferences testUserInfo
            testUserInfo = context.getSharedPreferences("userinfoFile",0);

            // 해당 아이디(KEY)로 해당 유저에 대한 정보를 가져올 수 있다.
            // String thisUserinfo라는 문자열에 testUserInfo라는 회원가입 시 저장했던 쉐어드 객체의 해당 아이디(key)와 value 값을 불러와준다.
            thisUserinfo =  testUserInfo.getString(useridString, "");

            // String alarmNicknamecheck
            alaramNicknamecheck = thisUserinfo.split("!@#")[1];
            Log.v("알람리시버에서 닉네임 값 확인 알림 : "," " + alaramNicknamecheck);

            // 이름 -1 , 닉네임 - 2, 아이디 - 3, 비밀번호 - 4로 이루어진 회원정보에서 세 번째 값인(index 2) 아이디를 가져온다.
            // String alaramIDcheck
            alaramIDcheck = thisUserinfo.split("!@#")[2];
            Log.v("알람리시버에서 id 값 확인 알림 : "," " + alaramIDcheck);

            SharedPreferences sharedPreferences = context.getSharedPreferences("daily alarm", MODE_PRIVATE);
            long millis = sharedPreferences.getLong("nextNotifyTime", Calendar.getInstance().getTimeInMillis());

            if(testUserInfo.contains(alaramIDcheck)) {

                Calendar current_calendar = Calendar.getInstance();
                Calendar nextNotifyTime = new GregorianCalendar();
                nextNotifyTime.setTimeInMillis(sharedPreferences.getLong(alaramIDcheck, millis));

                if (current_calendar.after(nextNotifyTime)) {
                    nextNotifyTime.add(Calendar.DATE, 1);
                }

                Date currentDateTime = nextNotifyTime.getTime();
                String date_text = new SimpleDateFormat("a hh시 mm분 ", Locale.getDefault()).format(currentDateTime);
                Toast.makeText(context.getApplicationContext(),"다음 알림은 " + date_text + "입니다!", Toast.LENGTH_SHORT).show();

                if (manager != null) {
                    manager.setRepeating(AlarmManager.RTC_WAKEUP, nextNotifyTime.getTimeInMillis(),
                            AlarmManager.INTERVAL_DAY, pendingIntent);
                }

            }


        }
    }
}
