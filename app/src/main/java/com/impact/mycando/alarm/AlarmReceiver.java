package com.impact.mycando.alarm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.impact.mycando.IntroActivity;
import com.impact.mycando.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(context, IntroActivity.class); // Mainactivity에서 introactivity로 바꿈

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingI = PendingIntent.getActivity(context, 0,
                notificationIntent, 0);


        // 알림을 만들려면 NotificationCompat.Builder 개체를 사용하여 콘텐츠와 채널을 설정해야 한다.
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");


        //OREO API 26 이상에서는 채널 필요
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            // setSmallIcon() 설정한 작은 아이콘
            builder.setSmallIcon(R.drawable.ic_launcher_foreground); //mipmap 사용시 Oreo 이상에서 시스템 UI 에러남


            String channelName ="매일 알람 채널";
            String description = "매일 정해진 시간에 알람합니다.";
            int importance = NotificationManager.IMPORTANCE_HIGH; //소리와 알림메시지를 같이 보여줌

            NotificationChannel channel = new NotificationChannel("default", channelName, importance);
            channel.setDescription(description);

            if (notificationManager != null) {
                // 노티피케이션 채널을 시스템에 등록
                notificationManager.createNotificationChannel(channel);
            }
        }else builder.setSmallIcon(R.mipmap.ic_launcher); // Oreo 이하에서 mipmap 사용하지 않으면 Couldn't create icon: StatusBarIcon 에러남


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

        builder.setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())

                //setContentTitle() 설정한 제목
                //setContentText() 설정한 세부 텍스트
                .setTicker("{Time to watch some cool stuff!}")
                .setContentTitle(" < 할 수 있다 > 알림")
                .setContentText(alaramNicknamecheck + "님, 힘을 빼봐요. 할 수 있습니다!!")
                .setContentInfo("INFO")
                .setContentIntent(pendingI);

        // 로그인한 아이디가 key가 맞으면 해당 아이디를 키로 알람 불러오기
        if(testUserInfo.contains(alaramIDcheck)) {

            if (notificationManager != null) {

                // 알림을 표시하려면  NotificationManager.notify()을 호출하고 고유 id와 결과를 전해야.
                // 노티피케이션 동작시킴
                notificationManager.notify(1234, builder.build());

                Calendar nextNotifyTime = Calendar.getInstance();

                // 내일 같은 시간으로 알람시간 결정
                nextNotifyTime.add(Calendar.DATE, 1);

                //  Preference에 설정한 값 저장
                SharedPreferences.Editor editor = context.getSharedPreferences("daily alarm", MODE_PRIVATE).edit();
                editor.putLong(alaramIDcheck, nextNotifyTime.getTimeInMillis());
                editor.apply();

                Date currentDateTime = nextNotifyTime.getTime();
                String date_text = new SimpleDateFormat("EE요일 a hh시 mm분 ", Locale.getDefault()).format(currentDateTime);
//            String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분 ", Locale.getDefault()).format(currentDateTime);
                Toast.makeText(context.getApplicationContext(),alaramNicknamecheck + "님!!", Toast.LENGTH_SHORT).show();
                Toast.makeText(context.getApplicationContext(),"할 수 있다! 오늘의 다짐을 쓸 시간입니다!", Toast.LENGTH_SHORT).show();
//                Toast.makeText(context.getApplicationContext(),"다음 알람은 " + date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();
//            Toast.makeText(context.getApplicationContext(),"다음 알람은 " + date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();
            }

        }

    }
}
