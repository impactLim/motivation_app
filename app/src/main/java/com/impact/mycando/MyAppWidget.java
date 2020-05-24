package com.impact.mycando;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import static android.content.Context.MODE_PRIVATE;

/**
 * Implementation of App Widget functionality.
 */
public class MyAppWidget extends AppWidgetProvider {

//    https://comostudio.tistory.com/56
//    https://comostudio.tistory.com/95

    //https://tapito.tistory.com/452 데이터 전달

    // 회원가입한 정보 shared 객체를 아이디, 비밀번호 입력값과 비교하기 위해 가지고 와야함
    SharedPreferences userinfo;
    SharedPreferences userid;

    String useridString;


    // 브로드캐스트 내부와 핸들러 외부에서 이 메소드 호출. AppWidget 제공자를 소유한 uid에서 호출된 경우에만 작동.
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId) {

//        CharSequence widgetText = context.getString(R.string.appwidget_text);
//        // Construct the RemoteViews object
//        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_app_widget);
//
//        Intent intent = new Intent(context, IntroActivity.class);
//        PendingIntent pe = PendingIntent.getActivity(context, 0, intent, 0);
//
//        views.setTextViewText(R.id.appwidget_text, "목ddd");
//        views.setOnClickPendingIntent(R.id.appwidget_text, pe);
//
//        // Instruct the widget manager to update the widget
//        appWidgetManager.updateAppWidget(appWidgetId, views);

    }


    // http://blog.naver.com/huewu/110089286698
    // onUpdate 메소드는 위젯의 remoteview가 요청될 때 위젯 프로바이더가 action_appwidget_update broadcast에 응답하기 위해서 호출된다.
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);

            // AppWidget 은 특정 건물에 세들어 가는 세입자라고 할 수 있습니다.
            // 전세로 들어간 아파트에는 벽에 시계를 걸기 위하여 못 하나 새로 치는 것도 주인 눈치가 보이는 만큼,
            // AppWidget 이 화면을 구성하는데도 여러가지 제약 조건이 따르게 됩니다.
            // 무엇보다도 AppWidget 은 실재 어플리케이션과 서로 다른 프로세스에서 동작하는 만큼, 직접적으로 화면상에 그림을 그릴 수 없습니다.
            // 자신이 원하는 형태로 화면을 그리도록 AppWidgetService 를 통해 AppWidget Host 에게 부탁해야합니다.
            // 그리고 이 때 사용되는 클래스가 바로 RemoteViews 입니다.
            // <RemoteViews 는 실제 View 를 생성하기 위한 설계도 입니다.>
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.my_app_widget);

            // 일단 인트로 화면으로 가는데, 켜져 있으면 해당 액티비티로 갈 수 있도록. > 근데 이 목표 버튼을 누르는 경우를 생각해보면
            // 어플이 꺼져 있는 상태일 가능성이 크고 켜져 있다 해도 리소스 때문에 다시 켜질 가능성이 있음
            Intent intent = new Intent(context, IntroActivity.class);

            // Activity를 시작하는 인텐트를 생성함
            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);

            // 클릭이벤트
            remoteViews.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);

            // refresh
            this.refresh(context, remoteViews);

            // manager.updateAppWidget(위젯ID배열, 리모트뷰);
            // 위젯 배열에 담긴 위젯을 모두 업데이트 할 때
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);



//                RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.my_app_widget);
//
//            Intent i = new Intent(Intent.ACTION_MAIN);
//            i.addCategory(Intent.CATEGORY_LAUNCHER);
//            i.setComponent(new ComponentName(context, MainActivity.class));
//            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, 0);
//
//
//
//                // 클릭이벤트
//                remoteViews.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);
//
//                // refresh
//                this.refresh(context, remoteViews);
//
//                // manager.updateAppWidget(위젯ID배열, 리모트뷰);
//                // 위젯 배열에 담긴 위젯을 모두 업데이트 할 때
//                appWidgetManager.updateAppWidget(appWidgetId, remoteViews);







        }
    }

    private void refresh(Context context, RemoteViews remoteViews) {

        // 해당 id를 담고 있는 걸 벨류값으로 주는 쉐어드 하나 더 만들어준 걸 가져온다.
        // SharedPreferences userid
        userid = context.getSharedPreferences("useridFile", MODE_PRIVATE);
        // 로그인할 때 가져온 id가 useridString이다.
        // String useridString
        useridString = userid.getString("userid", "");

        // 회원가입한 정보 shared 객체를 가져와 닉네임을 불러와준다.
        userinfo = context.getSharedPreferences("userinfoFile", MODE_PRIVATE);

        SharedPreferences widgetGoalPref = context.getSharedPreferences("widgetGoalPreffile", MODE_PRIVATE);

        // 텍스트 set해줌
        remoteViews.setTextViewText(R.id.appwidget_text,widgetGoalPref.getString(useridString,"집중목표를 설정해주세요!"));

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}

