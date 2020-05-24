package com.impact.mycando.alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.impact.mycando.R;
import com.impact.mycando.Realmenu;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class SetalarmActivity extends AppCompatActivity {

    SharedPreferences userid;
    String useridString;
    SharedPreferences testUserInfo;
    String thisUserinfo;

    String alaramIDcheck;
    String alaramNicknamecheck;

    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    long millis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setalarm);

//        // 이미지뷰
//        ImageView candoit_image = (ImageView) findViewById(R.id.candoit_image);
//        Glide.with(this).load(R.drawable.icandothisallday).into(candoit_image );

        // timepicker
        final TimePicker picker=(TimePicker)findViewById(R.id.timePicker);
        picker.setIs24HourView(true);


        // 아이디별로 알람 다르게
        // 로그인할 때 저장해준 아이디를 가지고 있는 쉐어드 파일을 불러온다.
        // SharedPreferences userid
        userid = getSharedPreferences("useridFile", MODE_PRIVATE);
        // 로그인할 때 저장해준 벨류값을 가지고 있는 key를 useridString에 넣어줘서 이 key로 언제든지 회원정보에 접근할 수 있게 한다.
        // String useridString
        useridString = userid.getString("userid","");

        // 저장된 회원정보를 불러오기 위해 같은 네임파일을 찾음.
        // SharedPreferences testUserInfo
        testUserInfo = getSharedPreferences("userinfoFile",0);

        // 해당 아이디(KEY)로 해당 유저에 대한 정보를 가져올 수 있다.
        // String thisUserinfo라는 문자열에 testUserInfo라는 회원가입 시 저장했던 쉐어드 객체의 해당 아이디(key)와 value 값을 불러와준다.
        thisUserinfo =  testUserInfo.getString(useridString, "");

        // String alarmNicknamecheck
        alaramNicknamecheck = thisUserinfo.split("!@#")[1];
        Log.v("알람 설정 화면에서 닉네임 값 확인 알림 : "," " + alaramNicknamecheck);

        // 이름 -1 , 닉네임 - 2, 아이디 - 3, 비밀번호 - 4로 이루어진 회원정보에서 세 번째 값인(index 2) 아이디를 가져온다.
        // String alaramIDcheck
        alaramIDcheck = thisUserinfo.split("!@#")[2];
        Log.v("알람 설정 화면에서 id 값 확인 알림 : "," " + alaramIDcheck);


        // 로그인한 아이디가 key가 맞으면 해당 아이디를 키로 알람 불러오기
        if(testUserInfo.contains(alaramIDcheck)) {

            // 앞서 설정한 값으로 보여주기
            // 없으면 디폴트 값은 현재시간
            SharedPreferences sharedPreferences = getSharedPreferences("daily alarm", MODE_PRIVATE);
            // long millis
            millis = sharedPreferences.getLong(alaramIDcheck, Calendar.getInstance().getTimeInMillis());
            Log.v("알림페이지 확인 알림","millis : " + millis);

            //  우리가 쓰는 달력은 GregorianCalendar(국제표준시)로 Calendar를 상속받은 클래스
            Calendar nextNotifyTime = new GregorianCalendar();
            nextNotifyTime.setTimeInMillis(millis);

            // 보여줄 날짜 형식
            Date nextDate = nextNotifyTime.getTime();
            String date_text = new SimpleDateFormat("a hh시 mm분", Locale.getDefault()).format(nextDate);
            Log.v("알림페이지 확인 알림","nextDate : " +  nextDate);
            Log.v("알림페이지 확인 알림","date_text : " +  date_text);

            // String date_text = new SimpleDateFormat("yy년 MM월 dd일 EE요일 a hh시 mm분 ", Locale.getDefault()).format(nextDate);
//            Toast.makeText(getApplicationContext(), alaramNicknamecheck + "님", Toast.LENGTH_SHORT).show();
//            Toast.makeText(getApplicationContext(),"현재 " + date_text + "으로 알람이 설정되어 있습니다!", Toast.LENGTH_SHORT).show();

            // Toast.makeText(getApplicationContext(),"다음 알람은 " + date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();
            Log.v("알람 처음 실행시 메시지 확인 알림","" + "[처음 실행시] 다음 알람은 " + date_text + "으로 알림이 설정되어 있습니다!" + " 아이디 체크 : " + alaramIDcheck + " 닉네임 체크 : " +  alaramNicknamecheck);

            // 이전 설정값으로 TimePicker 초기화
            Date currentTime = nextNotifyTime.getTime();
            Log.v("알림페이지 확인 알림","currentTime : " + currentTime);

            // 시간을 원하는 형태로 성정하기
            SimpleDateFormat HourFormat = new SimpleDateFormat("kk", Locale.getDefault());
            SimpleDateFormat MinuteFormat = new SimpleDateFormat("mm", Locale.getDefault());
            Log.v("알림페이지 확인 알림","HourFormat : " + HourFormat);
            Log.v("알림페이지 확인 알림","MinuteFormat : " + MinuteFormat);

            // 숫자형의 문자열을 인자값으로 받으면 해당 값을 10진수의 Integer 형으로 반환해준다.
            int pre_hour = Integer.parseInt(HourFormat.format(currentTime));
            int pre_minute = Integer.parseInt(MinuteFormat.format(currentTime));
            Log.v("알림페이지 확인 알림","HourFormat.format(currentTime) : " + HourFormat.format(currentTime));
            Log.v("알림페이지 확인 알림","MinuteFormat.format(currentTime) : " + MinuteFormat.format(currentTime));

            // 안드로이드에서는 android.os.Build.VERSION.SDK_INT를 통해서 기기의 SDK Version을 가져올 수 있다.
            if (Build.VERSION.SDK_INT >= 23 ){
                picker.setHour(pre_hour);
                picker.setMinute(pre_minute);
                Log.v("알림페이지 확인 알림","pre_hour : " + pre_hour + "  pre_minute  : " + pre_minute);

            }
            else{
                picker.setCurrentHour(pre_hour);
                picker.setCurrentMinute(pre_minute);
            }
        }


        //back 아이콘 누르면 알람설정화면에서 accountpage로 넘어가기
        ImageButton back = (ImageButton) findViewById(R.id.back_icon);
        back.setOnClickListener(new Button.OnClickListener(){

            public void onClick(View v){

                Intent intent=(new Intent(getApplicationContext(), Realmenu.class));
                startActivity(intent);
                finish();

            }

        });

        // 알람 설정 버튼
        Button setAlarmBtn = (Button) findViewById(R.id.btn_complete);
        setAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                // 로그인한 아이디가 key가 맞으면 해당 아이디를 키로 알람 저장하기
                if(testUserInfo.contains(alaramIDcheck)) {

                    int hour, hour_24, minute;

                    // 시간 가져옴
                    String am_pm;
                    if (Build.VERSION.SDK_INT >= 23 ){
                        hour_24 = picker.getHour();
                        minute = picker.getMinute();
                    }
                    else{
                        hour_24 = picker.getCurrentHour();
                        minute = picker.getCurrentMinute();
                    }

                    if(hour_24 > 12) {
                        am_pm = "PM";
                        hour = hour_24 - 12;
                    }
                    else
                    {
                        hour = hour_24;
                        am_pm="AM";
                    }

                    // 현재 지정된 시간으로 알람 시간 설정
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(Calendar.HOUR_OF_DAY, hour_24);
                    calendar.set(Calendar.MINUTE, minute);
                    calendar.set(Calendar.SECOND, 0);

                    // 이미 지난 시간을 지정했다면 다음날 같은 시간으로 설정
                    if (calendar.before(Calendar.getInstance())) {
                        calendar.add(Calendar.DATE, 1);
                    }

                    Date currentDateTime = calendar.getTime();
                    Log.v("알림페이지 확인 알림","currentDateTime : " + currentDateTime);

                    String date_text = new SimpleDateFormat("a hh시 mm분 ", Locale.getDefault()).format(currentDateTime);
                    Log.v("알림페이지 확인 알림","date_text : " + date_text);

//                String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분 ", Locale.getDefault()).format(currentDateTime);
                    Toast.makeText(getApplicationContext(),""+ alaramNicknamecheck + "님", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(),""+ date_text + "으로 알림이 설정되었습니다.", Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), "내일 봐요!", Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(),date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();
                    Log.v("알람 설정 확인 알림","" + date_text + "으로 알람이 설정되었습니다." + " 아이디 체크 : " + alaramIDcheck + " 닉네임 체크 : " +  alaramNicknamecheck);

                    //  Preference에 설정한 값 저장
                    SharedPreferences.Editor editor = getSharedPreferences("daily alarm", MODE_PRIVATE).edit();

                    // receiver에 시간값 넘겨주기
                    editor.putLong(alaramIDcheck, (long) calendar.getTimeInMillis());
                    Log.v("알림페이지 확인 알림","calendar.getTimeInMillis() : " + calendar.getTimeInMillis());

                    editor.apply();

                    diaryNotification(calendar);

                    Intent intent = new Intent(getApplicationContext(), Realmenu.class);

                    startActivity(intent);
                    finish();
                }
            }
        });

        // https://sh-itstory.tistory.com/64
        // https://webnautes.tistory.com/1365
        // http://blog.naver.com/PostView.nhn?blogId=banhong&logNo=194136656
        // https://migom.tistory.com/10
        // https://all-day-study-room.tistory.com/4
        // https://citynetc.tistory.com/220
        // https://stackoverflow.com/questions/28922521/how-to-cancel-alarm-from-alarmmanager

        // 알람 정지 버튼
        Button alarm_off = findViewById(R.id.btn_cancel);
        alarm_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SetalarmActivity.this,"알림이 취소되었습니다.",Toast.LENGTH_SHORT).show();


                // AlarmManager alarmManager
                alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Log.v("알림페이지 확인 알림","알람 정지 alarmManager : " + alarmManager);

                Intent myIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
                Log.v("알림페이지 확인 알림","알람 정지 myIntent  : " + myIntent );

                // PendingIntent pendingIntent
                pendingIntent = PendingIntent.getBroadcast(
                        getApplicationContext(), 0, myIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                Log.v("알림페이지 확인 알림","알람 정지 myIntent  : " + myIntent  + " pendingIntent : " + pendingIntent);
                alarmManager.cancel(pendingIntent);

                Intent intent = new Intent(getApplicationContext(), Realmenu.class);
                startActivity(intent);
                finish();

            }
        });
    }



    void diaryNotification(Calendar calendar)
    {
//        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
//        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
//        Boolean dailyNotify = sharedPref.getBoolean(SettingsActivity.KEY_PREF_DAILY_NOTIFICATION, true);
        Boolean dailyNotify = true; // 무조건 알람을 사용
        Log.v("알림페이지 확인 알림","알람 dailyNotify : " + dailyNotify );

        PackageManager pm = this.getPackageManager();
        ComponentName receiver = new ComponentName(this, DeviceBootReceiver.class);

        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        // PendingIntent pendingIntent
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);

        // AlarmManager alarmManager
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Log.v("알림페이지 확인 알림","알람 alarmIntent  : " + alarmIntent + " pendingIntent : " + pendingIntent + " alarmManager : " + alarmManager  );

        // 사용자가 매일 알람을 허용했다면
        if (dailyNotify) {
            if (alarmManager != null) {

//                AlarmManager alarmManager
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, pendingIntent);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                }
            }

//            // 부팅 후 실행되는 리시버 사용가능하게 설정
//            pm.setComponentEnabledSetting(receiver,
//                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
//                    PackageManager.DONT_KILL_APP);
        }

//        else { //Disable Daily Notifications
//            if (PendingIntent.getBroadcast(this, 0, alarmIntent, 0) != null && alarmManager != null) {
//                alarmManager.cancel(pendingIntent);
//                //Toast.makeText(this,"Notifications were disabled",Toast.LENGTH_SHORT).show();
//            }
//            pm.setComponentEnabledSetting(receiver,
//                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
//                    PackageManager.DONT_KILL_APP);
//        }


    }

}

