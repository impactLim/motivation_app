package com.impact.mycando;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SetgoalActivity extends AppCompatActivity {

    // textview에 입력받은 날짜
    private TextView textView_date;
    // edittext에 입력받은 목표
    private EditText editText_goal;

    private DatePickerDialog.OnDateSetListener callbackMethod;

    // 완료버튼
    Button btn_complete;

    // String에 year + "년 " + monthofyear + "월 " + dayOfMonth + "일" 들어감. 완료버튼 누를 때 이 date 값을 putextra해주면 메뉴화면에서 get할 수 있도록
    String date;

    // 목표를 저장시켜주기 위한 shared 파일 생성
    SharedPreferences userinfo;
    // 회원정보 shared 파일을 수정하기 위한 파일 생성
    SharedPreferences.Editor userinfoEditor;

    // 아이디 마다의 목표를 설정해주기 위한 작업
    SharedPreferences userid;
    // 로그인할 때 가져온 id가 useridString이다.
    String useridString;

    // 날짜 저장해줄 shared 파일
    SharedPreferences setdateFile;
    // 날짜 수정할 setdate editor 파일
    SharedPreferences.Editor setdateEditor;

    // 목표 저장해줄 shared 파일
    SharedPreferences setgoalFile;
    // 목표 수정할 setgoal editor 파일
    SharedPreferences.Editor setgoalEditor;

    SharedPreferences goalManagementPref;
    SharedPreferences.Editor goalManagementEditor;
    String goal_add;
    String goal; // 기간 + 목표

    boolean successCheck = false;
    String goalcheck;

    SharedPreferences widgetGoalPref;
    SharedPreferences.Editor widgetGoalEditor;




    // ddaty 텍스트뷰
    private TextView ddayText;
    // today 텍스트뷰
    private TextView todayText;
    // resultText 텍스트뷰
    private TextView resultText;
    // dateButton
    private Button dateButton;

    //오늘 연월일 변수
    private int tYear;
    private int tMonth;
    private int tDay;

    //디데이 연월일 변수
    private int dYear=1;
    private int dMonth=1;
    private int dDay=1;


    private long d;
    private long t;
    private long r;

    private int resultNumber=0;

    static final int DATE_DIALOG_ID=0;
    // 출처: https://hyunssssss.tistory.com/118?category=411681 [현's 블로그]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setgoal);

        // text뷰 참조
//        ddayText=(TextView)findViewById(R.id.dday);
//        todayText=(TextView)findViewById(R.id.today);
        resultText=(TextView)findViewById(R.id.result);

        Calendar calendar = Calendar.getInstance();              //현재 날짜 불러옴
        tYear = calendar.get(Calendar.YEAR);
        tMonth = calendar.get(Calendar.MONTH);
        tDay = calendar.get(Calendar.DAY_OF_MONTH);

        Calendar dCalendar = Calendar.getInstance();
        dCalendar.set(dYear,dMonth, dDay);

        t = calendar.getTimeInMillis();               //오늘 날짜를 밀리타임으로 바꿈
        d = dCalendar.getTimeInMillis();              //디데이날짜를 밀리타임으로 바꿈
        r = ( d - t ) / ( 24 * 60 * 60 * 1000 );      //디데이 날짜에서 오늘 날짜를 뺀 값을 '일'단위로 바꿈

        resultNumber = (int) r + 1;

//        // updatedisplay
//        //디데이 날짜가 오늘날짜보다 뒤에오면 '-', 앞에오면 '+'를 붙인다
//        todayText.setText(String.format("%d년 %d월 %d일",tYear, tMonth+1,tDay));
//        ddayText.setText(String.format("%d년 %d월 %d일",dYear, dMonth+1,dDay));
//
//        if(resultNumber>=0){
//            resultText.setText(String.format("D-%d", resultNumber));
//        }
//
//        else{
//            int absR=Math.abs(resultNumber);
//            resultText.setText(String.format("D+%d", absR));
//        }



        // 목표설정한 날짜 참조
        textView_date = (TextView) findViewById(R.id.tv_date);
        // 설정한 목표
        editText_goal = (EditText)findViewById(R.id.et_setgoal);

        // 해당 id를 담고 있는 걸 벨류값으로 주는 쉐어드 하나 더 만들어준 걸 가져온다.
        // SharedPreferences userid;
        userid = getSharedPreferences("useridFile", MODE_PRIVATE);
        // 로그인할 때 가져온 id가 useridString이다.
        // String useridString;
        useridString = userid.getString("userid", "");

        // 회원가입한 정보 shared 객체를 가져와 닉네임을 불러와준다.
        userinfo = getSharedPreferences("userinfoFile", MODE_PRIVATE);

        // 아이디별로 날짜를 다르게 설정할 수 있도록
        // 로그인한 아이디를 키로 주고 그 때마다 date 다르게 해준다.
        // 날짜 저장해줄 shared 파일
        // SharedPreferences setdateFile;
        setdateFile = getSharedPreferences("setdateFile", MODE_PRIVATE);
        // 날짜 수정할 setdate editor 파일
        // SharedPreferences.Editor setdateEditor;
        setdateEditor = setdateFile.edit();

        // 날짜 저장해줄 shared 파일
        // SharedPreferences setgoalFile;
        setgoalFile = getSharedPreferences("setgoalFile", MODE_PRIVATE);
        // 날짜 수정할 setgoal editor 파일
        // SharedPreferences.Editor setgoalEditor;
        setgoalEditor = setgoalFile.edit();
        Log.v("목표설정 화면 - date 확인로그 ", "date : " + date );


        if(setdateFile.getString(useridString,"") != null){
            // 날짜 박아주기
            textView_date.setText(setdateFile.getString(useridString,""));
        }

        if(setgoalFile.getString(useridString,"") != null){
            // 목표 입력받아넣기
            editText_goal.setText(setgoalFile.getString(useridString,""));
        }

        // 달력을 누르고 날짜를 선택했을 때 호출되는 메쏘드
        callbackMethod = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {

                // 월이 1달 적게 나와서 + 1 해줌
                int monthofyear = monthOfYear + 1;

                // String date;
                date = year + "년 " + monthofyear + "월 " + dayOfMonth + "일";

                // 설정한 날짜를 edittext_date에 박아줌
                textView_date.setText(date);
                Log.v("목표설정 화면 - date 확인로그 ", "date : " + date );

                setdateEditor.putString(useridString, date);
                setdateEditor.commit();

                //// Dday를 구하기 위한.
                // 설정한 년
                dYear = year;
                Log.v(" dYear 확인로그 ", "date : " + dYear );
                // 설정한 월
                dMonth = monthOfYear;
                Log.v("  dMonth 확인로그 ", " dMonth : " +  dMonth );
                // 설정한 일
                dDay = dayOfMonth;
                Log.v(" dDay 확인로그 ", " dDay : " +  dDay );

                final Calendar dCalendar = Calendar.getInstance();
                // 캘린더에 년, 월, 일을 넣는다.
                dCalendar.set(dYear, dMonth, dDay);

                d = dCalendar.getTimeInMillis();
                r = (d - t) / (24*60*60*1000);

                Log.v(" dDay 확인로그 ", " d : " +  d );
                Log.v(" dDay 확인로그 ", " r : " +  r );

                resultNumber = (int) r;

//                // updatedisplay
//                todayText.setText(String.format("%d년 %d월 %d일",tYear, tMonth+1,tDay));
//                ddayText.setText(String.format("%d년 %d월 %d일",dYear, dMonth+1,dDay));

                if(resultNumber >= 0){
                    resultText.setText(String.format("D-%d", resultNumber));
                }

                else{
                    int absR=Math.abs(resultNumber);
                    resultText.setText(String.format("D+%d", absR));
                }

                // 한 번이라도 저장을 한 적이 있으면
                String a = "a";

                // Dday 저장시켜주기 위해
                SharedPreferences ddayCheck = getSharedPreferences("ddayCheck", MODE_PRIVATE);
                SharedPreferences.Editor ddayCheckEditor = ddayCheck.edit();
                ddayCheckEditor.putLong(useridString, d);
                Log.v(" dDay 확인로그 ", " d 2 : " +  d );

                ddayCheckEditor.apply();

                SharedPreferences ddayA = getSharedPreferences("ddayA", MODE_PRIVATE);
                SharedPreferences.Editor ddayAeditor = ddayA.edit();
                ddayAeditor.putString(useridString, a);
                ddayAeditor.apply();

            }
        };

        // 뒤로가기 버튼 누르면 메인액티비티로 이동
        ImageButton btn_back = (ImageButton) findViewById(R.id.back_icon);
        btn_back.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);

                    }
                }
        );

        // 완료버튼 참조
        btn_complete = findViewById(R.id.setgoal_completebutton);
        btn_complete.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        // 목표를 입력하지 않았을 때
                        if(editText_goal.getText().toString().equals("")){
                            Toast.makeText(getApplicationContext(), "목표를 입력해주세요.", Toast.LENGTH_SHORT).show();
                        // 목표기간을 입력하지 않았을 때

                        }else if(setdateFile == null){
                            Toast.makeText(getApplicationContext(), "목표기간을 입력해주세요.", Toast.LENGTH_SHORT).show();
                        // 제대로 입력했을 때

                        }else{
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);


                            setgoalEditor.putString(useridString, editText_goal.getText().toString());
                            setgoalEditor.commit();
                            Log.v("목표설정하기 화면에서 날짜랑 목표 확인 로그","date : " + date + "  goal : " + editText_goal.getText().toString());


                            // SharedPreferences goalManagementPref
                            goalManagementPref = getSharedPreferences("goalManagementFile", MODE_PRIVATE);
                            // SharedPreferences.Editor goalManagementEditor
                            goalManagementEditor = goalManagementPref.edit();

                            // 유저별 목표관리 기능
                            if(userinfo.contains(useridString)){

                                // 쉐어드에 값이 있는지 없는지 체크하기 위해 먼저 getstring 해줌
                                String checkGoalEmpty = goalManagementPref.getString(useridString, "");
                                // String goal에는 기간과 목표가 들어감
                                goal = "" + setdateFile.getString(useridString,"") + "까지 " + editText_goal.getText().toString() + "한다!!";

                                // 값이 없으면 입력값인 goal 그대로 저장
                                if(checkGoalEmpty.equals("")){

                                    // String goalcheck
                                    goalcheck = goal + "###"
                                            + successCheck; // boolean successCheck

                                    // 기간과 목표를 담은 goal이 들어감
                                    goalManagementEditor.putString(useridString, goalcheck);
                                    Log.v("배경화면 문구 다이어로그 추가 알림 로그","strkeepSentence "  +  "" + date + "까지 " + editText_goal.getText().toString() + "한다!!");

                                    // 확인
                                    goalManagementEditor.commit();

                                    // 위젯클래스에 보내줄 목표 저장
                                    // SharedPreferences widgetGoalPref
                                    widgetGoalPref = getSharedPreferences("widgetGoalPreffile",MODE_PRIVATE);
                                    widgetGoalEditor = widgetGoalPref.edit();
                                    widgetGoalEditor.putString(useridString, goal);
                                    widgetGoalEditor.commit();

                                    // 위젯에게 값이 변경되었으니 업데이트하라는 메시지를 broadcast를 통해 전달
                                    Intent widgetintent = new Intent(getApplicationContext(), MyAppWidget.class);
                                    // 업데이트하라고 action을 보낸다.
                                    widgetintent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                                    // sendbroadcast 방송 보내기
                                    SetgoalActivity.this.sendBroadcast(widgetintent);


                                    // 초기 값이 있으면 초기 값에 내가 추가해주는 값들 더해줘야 한다.
                                } else {

                                    // goal_firstcontents에 첫 게시물을 담아준다.
                                    String goal_firstcontents =  goalManagementPref.getString(useridString, "");

                                    // 목표콘텐츠
                                    StringBuffer sb_goalcontents = new StringBuffer();

                                    // main_add 라는 string 값에 기본 콘텐츠를 비교해줘야할 구분자인 &&&와 추가로 입력해주는 값을 담아줌.
                                    // String goal_add
                                    goal_add = "&&&" + goal + "###" + successCheck; // boolean successCheck

                                    // 그걸 sb_sb_goalcontents에 계속 추가시켜준다.
                                    sb_goalcontents.append(goal_add);
                                    Log.v("배경화면 문구 다이어로그 추가 알림 로그","goal_add"  +  goal_add);

                                    // 기존 value인  keep_maincontents 에 추가되는 값들 sb_maincontents.toString()을 더해준다.
                                    goalManagementEditor.putString(useridString, goal_firstcontents + sb_goalcontents.toString());
                                    Log.v("명심할 것 다이어로그 추가 알림 로그","goal_firstcontents "  +  goal_firstcontents + "sb_maincontents.toString()" + sb_goalcontents.toString());

                                    // 파일에 최종 반영함
                                    goalManagementEditor.commit();

                                    // 위젯클래스에 보내줄 목표 저장
                                    // SharedPreferences widgetGoalPref
                                    widgetGoalPref = getSharedPreferences("widgetGoalPreffile",MODE_PRIVATE);
                                    widgetGoalEditor = widgetGoalPref.edit();
                                    widgetGoalEditor.putString(useridString,goal);
                                    widgetGoalEditor.commit();

                                    Intent widgetintent = new Intent(getApplicationContext(), MyAppWidget.class);
                                    // 업데이트하라고 action을 보낸다.
                                    widgetintent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                                    // sendbroadcast 방송 보내기
                                    SetgoalActivity.this.sendBroadcast(widgetintent);

                                }
                            }

                            startActivity(intent);
                            finish();

                        }

                    }
                }
        );
    }

//    private void updateDisplay(){
//
//        //디데이 날짜가 오늘날짜보다 뒤에오면 '-', 앞에오면 '+'를 붙인다
//        todayText.setText(String.format("%d년 %d월 %d일",tYear, tMonth+1,tDay));
//        ddayText.setText(String.format("%d년 %d월 %d일",dYear, dMonth+1,dDay));
//
//        if(resultNumber>=0){
//            resultText.setText(String.format("D-%d", resultNumber));
//        }
//
//        else{
//            int absR=Math.abs(resultNumber);
//            resultText.setText(String.format("D+%d", absR));
//        }
//
//    }

//    public void InitializeListener()
//    {
//
//    }

    public void OnClickHandler(View view)
    {
        // 캘린더에서 선택한 값에서
        // 현재시간을 currenttime으로 받아온다.
        Date currentTime = Calendar.getInstance().getTime();

        // 시간을 원하는 형태로 출력하기 위한 코드
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault()); // 년
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault()); // 월
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault()); // 일

        // 현지시간을 각 포맷별로 구분한 걸 string값에 넣어준다.
        String year = yearFormat.format(currentTime); // 년
        String month = monthFormat.format(currentTime); // 월
        String day = dayFormat.format(currentTime); //일

        //  string값을 int로 바꿔준다.
        int num_year = Integer.parseInt(year);
        int num_month = Integer.parseInt(month)-1; // 월이 한 달식 빠르게 나와 줄여준다.
        int num_day = Integer.parseInt(day);

        Log.v("목표설정 화면 - 날짜 확인로그 1", year + "년 " + month + "월 " + day + "일 " );
        Log.v("목표설정 화면 - 날짜 확인로그 2", year + "년 " + month + "월 " + day + "일 " );

        DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod, num_year, num_month, num_day);

        dialog.show();
    }
}
