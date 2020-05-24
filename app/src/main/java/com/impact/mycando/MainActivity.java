package com.impact.mycando;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.impact.mycando.recyclerview_dreamboard.DreamboxActivity;
import com.impact.mycando.recyclerview_goalmanagement.GoalmanagementActivity;
import com.impact.mycando.recyclerview_keepsentences.KeepActivity;
import com.impact.mycando.recyclerview_will.WillActivity;
import com.impact.mycando.recyclerview_wisesentences.MainpagesentencesActivity;
import com.impact.mycando.youtube.YoutubecontentsActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private AdView mAdView;


    // 메뉴 버튼 / 글쓰기 / 명심할 것 이미지 / 오늘의 각오 / 심호흡 / 유튜브 / 드림보드
    ImageButton btn_menu, btn_write, btn_keep, btn_will, btn_breath, btn_youtube, btn_dreamboard, btn_goalmanagement;
    Button btn_setgoal, btn_nickname;

    // 회원가입한 정보 shared 객체를 아이디, 비밀번호 입력값과 비교하기 위해 가지고 와야함
    SharedPreferences userinfo;
    SharedPreferences userid;

    String useridString;

    // shared 파일에서 닉네임을 불러와 string값에 담아준다.
    String user_nickname;
    TextView tv_nickname;

    // 화면 중간에 목표값 넣어주기
    // 목표와 기간을 박아줄 textview
    TextView tv_setgoal;
    // 넘겨받은 날짜와 목표
    String rc_date, rc_goal;
    // 날짜 + 목표
    String DateGoal;

    // 날짜 저장해둔 shared 파일
    SharedPreferences setdateFile;

    // 목표 저장해둔 shared 파일
    SharedPreferences setgoalFile;

    // 목표설정할 때 목표관리 박스에 이전 목표 완료했는지 체크하기 위한 변수들
    SharedPreferences goalManagementPref;
    SharedPreferences.Editor goalManagementEditor;
    String goalslistCheck;

    // 오늘의 각오 쓸 때 오늘의 각오에 값이 없다면 오늘의 각오 쓰라고 메시지 날려줌
    // 드림보드 꿈 - 메모 - 이미지 저장해줄 파일
    SharedPreferences UserWillPreffile;
    SharedPreferences.Editor UserWillPrefEditor;
    String willemptyCheck;

    // 뒤로 가기 두 번 종료
    private BackPressCloseHandler backPressCloseHandler;

    // 텍스트 전환 핸들러
    private int i = 0;
    private TextView myi;
    private TextView myi2;

    int count;
    int ii;

    private boolean condition = true;
//    출처: https://mc10sw.tistory.com/15 [Make it possible]

    // 명언문장들 저장해둔 파일
    SharedPreferences UsermainPreffile;
    SharedPreferences.Editor UsermainPrefEditor;

    // 명언들 담겨있는 string 파일
    String get_wisecontents;
    // 명언들 한 문장씩 뿌려줄 텍스트뷰
    TextView wise_contents;
    String[] splitmainsentences;
    String get_maincontent;


    // 최근에 입력한 값이 완료되었는지 안되었는지 체크해주는 boolean 값
    boolean get_goalcheck;



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

    long checkd;
    String checkA;
    SharedPreferences ddayCheck;
    Calendar calendar;
    Calendar dCalendar;


    //이미지
    // 회원마다의 이미지를 저장해주는 파일
    SharedPreferences profileimageFile;
    String menu_imageStr;

    // 박힐 이미지
    ImageView rc_goalimageview;
    // STRING으로 가져온 거 URI로 바꿔주기
    Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

                // 이미지뷰
        ImageView idea_gif = (ImageView) findViewById(R.id.idea);
        Glide.with(this).load(R.drawable.light).into(idea_gif);




        // 광고 api
        MobileAds.initialize(this, getString(R.string.admob_app_id));

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



//        myi = (TextView) findViewById(R.id.i);
//        myi2 = (TextView) findViewById(R.id.i2);

        // dday
        // text뷰 참조
//        ddayText=(TextView)findViewById(R.id.dday);
//        todayText=(TextView)findViewById(R.id.today);
        resultText=(TextView)findViewById(R.id.result);

        // Calendar calendar
        calendar = Calendar.getInstance();              //현재 날짜 불러옴
        tYear = calendar.get(Calendar.YEAR);
        tMonth = calendar.get(Calendar.MONTH);
        tDay = calendar.get(Calendar.DAY_OF_MONTH);

        // Calendar dCalendar
        dCalendar = Calendar.getInstance();
        dCalendar.set(dYear,dMonth, dDay);



        // 해당 id를 담고 있는 걸 벨류값으로 주는 쉐어드 하나 더 만들어준 걸 가져온다.
        // SharedPreferences userid
        userid = getSharedPreferences("useridFile", MODE_PRIVATE);
        // 로그인할 때 가져온 id가 useridString이다.
        // String useridString
        useridString = userid.getString("userid", "");

        // 회원가입한 정보 shared 객체를 가져와 닉네임을 불러와준다.
        userinfo = getSharedPreferences("userinfoFile", MODE_PRIVATE);
        // 로그인한 아이디를 키로 하고 value에 회원정보들이 담겨 있는 thisuserinfo를 String으로 가져와준다
        String thisuserinfo = userinfo.getString(useridString,"");




        // 배경화면 이미지
        // SharedPreferences profileimageFile;
        profileimageFile = getSharedPreferences("userimageFile", MODE_PRIVATE);

        // 이미지가 담겨있는 string값을 불러와 menu_imageStr에 담아준다.
        menu_imageStr = profileimageFile.getString(useridString, "");

        // 이미지가 담겨져 있는 string 값의 menu_imageStr이 null 값이 아니라면
        if(menu_imageStr.equals("") == false){

            // String값인 menu_imageStr를 Uri 값인 selectedImageUri로 바꿔줌.
            selectedImageUri = Uri.parse(menu_imageStr);

            // 프로필편집화면에서 저장해준 uri값을 받을 이미지뷰를 참조해주고
            rc_goalimageview = findViewById(R.id.imageView_goal);

            // 이미지뷰에 받아온 uri 값을 뿌려줌
//            rc_goalimageview.setImageURI(selectedImageUri);

        }




        // 회원정보에 있는 닉네임값을 split을 이용해 user_nickname값으로 받아와준다.
        user_nickname = thisuserinfo.split("!@#")[1];

        btn_nickname = findViewById(R.id.nickname_button);
        // 메뉴 버튼을 누르면 메뉴 화면으로 감
        btn_nickname.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                        startActivity(intent);
                    }
                }
        );
        // 메인화면 우측 상단 텍스트뷰 - 닉네임 참조
//        tv_nickname = findViewById(R.id.main_nickname);
        // tv_nickname에 로그인 시 넘겨받은 닉네임값 고정
        btn_nickname.setText(user_nickname + "! JUST DO IT.");
        // 메인화면 목표값 텍스트뷰 참조하기
        tv_setgoal = findViewById(R.id.tv_goal);

        // 날짜 저장해둔 파일 불러옴
        // String rc_date, rc_goal - date랑 goal 값을 목표설정화면에서 받아오기
        // shared setdatefile;
        setdateFile = getSharedPreferences("setdateFile", MODE_PRIVATE);
        // 아이디를 키로 날짜를 value로 저장해둔 파일
        rc_date = setdateFile.getString(useridString, "");

        // 목표 저장해둔 파일 불러옴
        // shared setgoalfile
        setgoalFile = getSharedPreferences("setgoalFile", MODE_PRIVATE);
        rc_goal = setgoalFile.getString(useridString,"");

        // 받아온 날짜와 목표가 null이 아니라면.
        if(rc_date.equals("") == false && rc_goal.equals("") == false){

            // String DateGoal
            DateGoal = rc_date + "까지 " + rc_goal + "한다!!";
            // 목표에 내가 받아온 날짜와 목표를 set해줌
            tv_setgoal.setText(DateGoal);
            Log.v("메인화면에서 목표확인로그 ","" + rc_date + "까지 " + rc_goal + "한다!!");

        }

        // ddayA 파일에 저장된 값
        SharedPreferences ddayA = getSharedPreferences("ddayA", MODE_PRIVATE);
        ddayA.getString(useridString,"");

        // SharedPreferences dday
        ddayCheck = getSharedPreferences("ddayCheck", MODE_PRIVATE);

        t = calendar.getTimeInMillis();               //오늘 날짜를 밀리타임으로 바꿈
        d = dCalendar.getTimeInMillis();              //디데이날짜를 밀리타임으로 바꿈

//        if(ddayA.getString(useridString,"").equals("a")){
//
//            // long check
//            checkd = ddayCheck.getLong(useridString,0);
//            Log.v("check 확인로그 "," check " + checkd);
//
//            // checkA = dday.getString(useridString,"");
//            r = ( checkd - t ) / ( 24 * 60 * 60 * 1000 );      //디데이 날짜에서 오늘 날짜를 뺀 값을 '일'단위로 바꿈
//            resultNumber = (int) r + 1; //+1
//            Log.v("checkd 확인로그 1"," checkd " + checkd  + " t" +  t + " r " + r);
//
//
//            if(resultNumber >= 0){
//
//                    resultText.setText(String.format("D-%d", resultNumber));
//                    Log.v("resultNumber 확인로그 1"," resultNumber 1 " + resultNumber);
//
//                }
//
//                else{
//
//                    int absR = Math.abs(resultNumber);
//                    resultText.setText(String.format("D+%d", absR));
//                    Log.v("check 확인로그 2"," check 2 " + resultNumber);
//
//                }
//        }

//        check = (int) r + 1;
//        // updatedisplay
//        //디데이 날짜가 오늘날짜보다 뒤에오면 '-', 앞에오면 '+'를 붙인다
//        todayText.setText(String.format("%d년 %d월 %d일",tYear, tMonth+1,tDay));
//        ddayText.setText(String.format("%d년 %d월 %d일",dYear, dMonth+1,dDay));


        // 메뉴 버튼을 누르면 메뉴 화면으로 감
        btn_menu = (ImageButton) findViewById(R.id.menu_icon);
        btn_menu.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent(getApplicationContext(), Realmenu.class);
                        startActivity(intent);
                    }
                }
        );

        // 목표설정 옆에 환경설정 아이콘 누르면 목표관리 화면으로 이동함
        btn_goalmanagement = (ImageButton) findViewById(R.id.goGoalManagement);
        btn_goalmanagement.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        Intent intent = new Intent(getApplicationContext(), GoalmanagementActivity.class);
                        startActivity(intent);

                    }
                }
        );


        // 기억하고 싶은 문장들 글쓰기 버튼을 누르면 기억하고 싶은 문장들 글쓰기 화면으로 감
        btn_write = (ImageButton) findViewById(R.id.write_icon);
        Glide.with(this).load(R.drawable.pencil2).into(btn_write);
        btn_write.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent(getApplicationContext(), MainpagesentencesActivity.class);
                        startActivity(intent);
                    }
                }
        );


        // 명심할 것들 이미지 버튼을 누르면 명심할 것들 화면으로 감
        btn_keep = (ImageButton) findViewById(R.id.checkbox_imagebutton);
        btn_keep.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent(getApplicationContext(), KeepActivity.class);
                        startActivity(intent);
                    }
                }
        );


        // 추가 버튼을 눌렀을 때 저장되어서 화면에 있는 리사이클러뷰에 불러와져야함.
        // 그러기 위해 shared 파일을 하나 더 만들어줌
        // SharedPreferences  UserWillPreffile
        UserWillPreffile = getSharedPreferences("UserwillPrefFile", 0);

        // editor라는게 필요해서 만들어줬다.
        // SharedPreferences.Editor UserdreamPrefEditor
        UserWillPrefEditor = UserWillPreffile.edit();


        // 오늘의 각오 버튼 누르면 오늘의 각오 화면으로 이동함
        btn_will = (ImageButton) findViewById(R.id.will_button);





        // 할수있다 유튜브 버튼을 누르면 유튜브 화면으로 감
        btn_youtube = (ImageButton) findViewById(R.id.youtube_imagebutton);
        btn_youtube.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent(getApplicationContext(), YoutubecontentsActivity.class);
                        startActivity(intent);
                    }
                }
        );

        // 추가 버튼을 눌렀을 때 저장되어서 화면에 있는 리사이클러뷰에 불러와져야함.
        // 그러기 위해 shared 파일을 하나 더 만들어줌
        // SharedPreferences goalManagementPref;
        goalManagementPref = getSharedPreferences("goalManagementFile", 0);

        // editor라는게 필요해서 만들어줬다.
        // SharedPreferences.Editor goalManagementEditor;
        goalManagementEditor =  goalManagementPref.edit();

        Log.v("oncreate 확인 알림 로그", " on create ");




        // 목표모음상자 버튼을 누르면 목표모음상자 화면으로 감
        btn_dreamboard = (ImageButton) findViewById(R.id.dreamboard_imagebutton);
        btn_dreamboard.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent(getApplicationContext(), DreamboxActivity.class);
                        startActivity(intent);
                    }
                }
        );


        // 명언들 불러와주는 shared파일
        // SharedPreferences UsermainPref
        UsermainPreffile = getSharedPreferences("UsermainPrefFile", 0);
        // 명언들 한 문장씩 바꿔서 보여줄 텍스트뷰
        wise_contents = (TextView) findViewById(R.id.image_contents);



        // 광고가 제대로 로드 되는지 테스트 하기 위한 코드입니다.
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                // 광고가 문제 없이 로드시 출력됩니다.
                Log.d("@@@", "onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                // 광고 로드에 문제가 있을시 출력됩니다.
                Log.d("@@@", "onAdFailedToLoad " + errorCode);
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });



        // 뒤로 가기 누르면 "뒤로 버튼을 한 번 더 누르면 종료되도록."
        backPressCloseHandler = new BackPressCloseHandler(this);
        //출처: https://dsnight.tistory.com/14 [Development Assemble]
        Log.v(" 뒤로 버튼을 한 번 더 누르면 종료되도록 ", " " );

    }

    // 뒤로 가기 누르면 "뒤로 버튼을 한 번 더 누르면 종료되도록."
    @Override
    public void onBackPressed() {

        backPressCloseHandler.onBackPressed();
        Log.v(" 뒤로 버튼을 한 번 더 누르면 종료되도록 ", " " );

    }

    // sendMessage 함수에 의해 전달되는 msg를 받아서 처리하는 하는것
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            // obj로 받은 문장들 set해줌
            wise_contents.setText(msg.obj + "");

        }
    };


    // onstart 될 때마다 "할 수 있다!" toast 메시지
    @Override
    protected void onStart() {
        super.onStart();
        Log.v("onstart 확인 알림 로그", " onstart ");

        // 메인 쓰레드만 ui 변경 가능
        // start 될 때마다 메시지 삭제
        handler.removeMessages(0);

        // boolean condition
        // stop될 때 condition이 false로 바뀌어서 false 시에는 thread가 활동하지 않음
        condition = true;

        // 회원정보가 useridString 값을 가지고 있으면
        if(userinfo.contains(useridString)){

            // 그 아이디를 key값으로 한
            // String get_wisecontents
            get_wisecontents = UsermainPreffile.getString(useridString, "");
            Log.v("메인 명심 확인 알림 로그","get_wisecontents "  +  get_wisecontents);

            // 이 조건문 안 걸어주면 공백인 아이템이 출력된다.
            if(get_wisecontents.equals("") == false){

                // splitmainsentences 배열에 get_maincontents를 &&&로 잘라준다.
                // String[] splitmainsentences
                splitmainsentences = get_wisecontents.split("&&&");

                // 그 잘라준 값들을 배열의 길이만큼 반복해주고 그때마다 어레이리스트에 담긴다.
                for(int i = 0; i< splitmainsentences.length; i ++){

                    // get_maincontent에 콘텐츠 개수만큼 받아주고
                    // String get_maincontent
                    get_maincontent = splitmainsentences[i];
                    Log.v("메인 명심 확인 알림 로그","get_maincontent "  +  get_maincontent);

//                                wise_contents.setText(splitmainsentences[i]);

                }

            }

        }

        // https://wowon.tistory.com/95
        //  run 함수에서 발생시킨 이벤트를 Handler 객체의
        //  handleMessage 함수에서 메시지를 받아 처리 합니다.
        Thread myThread = new Thread(new Runnable() {
            public void run() {

//                count = 0;
                // ii라는 변수를 만들어주고
                ii = 0;

                while (condition) {

                    // msg라는 핸들러가 가질 메시지 변수 선언
                    Message msg = handler.obtainMessage();
                    // ii는 계속 증가되자만 배열길이와 같을 시 0으로 만들어줘서 다시 시작
                    ii++;

                    // 값이 존재할 때만
                    if(get_wisecontents.equals("") == false){

                        // 이 ii가 배열 길이가 될 때까지만 메시지를 쏴준다.
                        if(ii <= splitmainsentences.length){

                            // 보낼 메시지는 인덱스값 0부터여야 하기 때문에 괄호 안에 있는 걸 0으로 만들어줌
                            msg.obj = splitmainsentences[ii-1];
                            // 문장을 담은 msg를 보내준다.
                            handler.sendMessage(msg);

//                    count ++ ;
//                    //Message객체에있는 arg1클래스에 count값을 넣고 메세지 큐로 데이터를 보낸다.
//                    msg.arg1 = count;

                            try {
                                // thread가 핸들러 정보를 얻는다. > obatainmessage / sendmessage함수를 이용해 메시지 큐에 데이터를 보낸다.
                                // sendMessage 함수에 의해 전달되는 msg를 받아서 처리하는 것
                                // handler.sendMessage(handler.obtainMessage());
                                Thread.sleep(5000);
                                Log.v("thread 로그 확인 알림","thread 로그 확인 알림");

                                // ii가 배열 길이와 같아질 때 ii를 0으로 만들어줘서 반복될 수 있도록 설정
                                if(ii == splitmainsentences.length){
                                    ii = 0;
                                }

                            } catch (Throwable t) {

                            }

                        }
                    }

                }
            }
        });

//        myThread.setDaemon(true);//데몬쓰레드로 설정

        // 처음 한 번 만 찍힘 - 그 다음부턴 mythread 객체 안에 있는 로그만 찍히는데 핸들러 객체의 updatethread 때문.
        myThread.start();
        Log.v("로그 체크 start()", "start()");

        // String goalslistCheck;
        goalslistCheck = goalManagementPref.getString(useridString,"");
        Log.v("goalslistCheck 확인 알림 로그","goalslistCheck " +  goalslistCheck);

        // splitgoallists 배열에 get_goallists를 &&&로 잘라준다.
        String[] splitgoallists = goalslistCheck.split("&&&");
        Log.v("splitgoallists 확인 알림 로그", " splitgoallists " + splitgoallists );
        Log.v("splitgoallists[splitgoallists.length - 1]확인 알림 로그", "splitgoallists[splitgoallists.length - 1] " + splitgoallists[splitgoallists.length - 1] );


        if(splitgoallists.length >= 2){
            // boolean get_goalcheck
            get_goalcheck = Boolean.parseBoolean(splitgoallists[splitgoallists.length - 1].split("###")[1]);
            Log.v("get_goalcheck 확인 알림 로그", " get_goalcheck " + get_goalcheck );
        }



        // qqqq1111 qqqq1111! / ggggg gggg1111! /
        // 대표목표설정하기 버튼을 누르면 목표설정하는 화면으로 감
        btn_setgoal = (Button) findViewById(R.id.setgoal_button);
        btn_setgoal.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        // 유저별 정보 분리
                        if(userinfo.contains(useridString)){

                            // 집중목표 관리 파일에 아무것도 없을 때 들어갈 수 있음
                            if(goalslistCheck.equals("")){

                                Intent intent = new Intent(getApplicationContext(), SetgoalActivity.class);
                                startActivity(intent);

                                // 한 개 이상의 목표가 있을 때
                            }else{

                                // 최근에 설정한 목표를 완료 했으면
                                if(get_goalcheck == true){

                                    Intent intent = new Intent(getApplicationContext(), SetgoalActivity.class);
                                    startActivity(intent);

                                // 목표 완료 못했으면
                                } else{

                                    Toast.makeText(getApplicationContext(), "기존 집중목표를 먼저 완료해주세요!!", Toast.LENGTH_SHORT).show();

                                }

                            }

                        }

                    }
                }
        );

        Log.v("get_goalcheck 확인 알림 로그 2 골체크", " get_goalcheck " + get_goalcheck );

        if(get_goalcheck == true){
            tv_setgoal.setText("집중목표를 다시 설정해주세요!");
            Log.v("get_goalcheck 확인 알림 로그 2 골체크 2", " get_goalcheck " + get_goalcheck );
        }

        willemptyCheck = UserWillPreffile.getString(useridString,"");
        // 오늘의 각오 작성 - 값이 없다면 팝업창으로, 값이 있다면 기본 will 창으로
        btn_will.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        if(userinfo.contains(useridString)){

                            // 각오에 값이 없다면
                            if(willemptyCheck.equals("")){

                                Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                                startActivity(intent);
                             //   startActivityForResult(intent, REQ_ADD_WILL);

                            // 각오에 값이 있다면
                            } else{

                                Intent intent = new Intent(getApplicationContext(), WillActivity.class);
                                startActivity(intent);

                            }

                        }

                    }
                }
        );


        // 대표목표설정하기 버튼을 누르면 목표설정하는 화면으로 감
        btn_setgoal = (Button) findViewById(R.id.setgoal_button);
        btn_setgoal.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        // String goalslistCheck;
                        goalslistCheck = goalManagementPref.getString(useridString,"");
                        Log.v("goalslistCheck 확인 알림 로그","goalslistCheck " +  goalslistCheck);

                        // 유저별 정보 분리
                        if(userinfo.contains(useridString)){

                            // 집중목표 관리 파일에 아무것도 없을 때 들어갈 수 있음
                            if(goalslistCheck.equals("")){

                                Intent intent = new Intent(getApplicationContext(), SetgoalActivity.class);
                                startActivity(intent);

                                // 한 개 이상의 목표가 있을 때
                            }else{

                                // splitgoallists 배열에 get_goallists를 &&&로 잘라준다.
                                String[] splitgoallists = goalslistCheck.split("&&&");
                                Log.v("splitgoallists 확인 알림 로그", " splitgoallists " + splitgoallists );
                                Log.v("splitgoallists[splitgoallists.length - 1]확인 알림 로그", "splitgoallists[splitgoallists.length - 1] " + splitgoallists[splitgoallists.length - 1] );

                                // 맨 마지막에 썼던 목표의 마지막 값이 true(완료했는지)인지 false(완료하지 않았는지)인지 체크해서
                                boolean get_goalcheck = Boolean.parseBoolean(splitgoallists[splitgoallists.length - 1].split("###")[1]);
                                Log.v("get_goalcheck 확인 알림 로그", " get_goalcheck " + get_goalcheck );

                                // 최근에 설정한 목표를 완료 했으면
                                if(get_goalcheck == true){

                                    // 목표설정 할 수 있게
                                    Intent intent = new Intent(getApplicationContext(), SetgoalActivity.class);
                                    startActivity(intent);

                                    // 목표 완료 못했으면
                                } else{

                                    Toast.makeText(getApplicationContext(), "기존 집중목표를 먼저 완료해주세요!!", Toast.LENGTH_SHORT).show();

                                }

                            }

                        }

                    }
                }
        );

    }

    // onResume 될 때마다 "할 수 있다!" toast 메시지
    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "할 수 있다!!", Toast.LENGTH_SHORT).show();
    }

    @Override

    public void onPause() {
        super.onPause( );

        condition = false;

    }

}


//    매번 발생할 때 마다 updateThread() 함수가 실행되게 되는데 이 함수 안에는 4개의 이미지를 번갈아 가면서 보여줍니다.
////    이 이미지들은 동그라미들인데 방향이 다 틀려서 4가지 이미지를 차례로 로딩하면 한바뀌 도는 것 같이 보일 겁니다.
////    이게 중요한 것이 아니라 스레드를 이용해서 이미지들을 바꿔서 애니메이션 효과를 얻었다는 것이죠.
//
//    private void updateThread() {
//
//        // % 는 나눗셈.
//        int mod = i % 4;
//
//        switch (mod) {
//            case 0:
//                i++;
////                imageView.setImageResource(R.drawable.ic_launcher_background);
//                Log.v("로그 체크 0", "i  " + i);
//
//                break;
//
//            case 1:
//                i++;
////                imageView.setImageResource(R.drawable.ic_launcher_foreground);
//                Log.v("로그 체크 1", "i  " + i);
//
//                break;
//
//            case 2:
//                i++;
////                imageView.setImageResource(R.drawable.ic_launcher_background);
//                Log.v("로그 체크 2", "i " + i);
//
//                break;
//
//            case 3:
//                i = 0;
////                imageView.setImageResource(R.drawable.ic_launcher_foreground);
//                Log.v("로그 체크 3", " i " + i);
//
//                break;
//        }
//
//        myi.setText(String.valueOf(i));
//        Log.v("로그 체크 settext", "" + i);
//
//
//
//
//        // 회원정보가 useridString 값을 가지고 있으면
//        if(userinfo.contains(useridString)){
//
//            // 그 아이디를 key값으로 한
//            // String get_wisecontents
//            get_wisecontents = UsermainPreffile.getString(useridString, "");
//            Log.v("메인 명심 확인 알림 로그","get_wisecontents "  +  get_wisecontents);
//
//            // 이 조건문 안 걸어주면 공백인 아이템이 출력된다.
//            if(get_wisecontents.equals("") == false){
//
//                // splitmainsentences 배열에 get_maincontents를 &&&로 잘라준다.
//                String[] splitmainsentences = get_wisecontents.split("&&&");
//
//                // 그 잘라준 값들을 배열의 길이만큼 반복해주고 그때마다 어레이리스트에 담긴다.
//                for(int i = 0; i< splitmainsentences.length; i ++){
//
//                    // get_maincontent에 콘텐츠 개수만큼 받아주고
//                    String get_maincontent = splitmainsentences[i];
//                    Log.v("메인 명심 확인 알림 로그","get_maincontent "  +  get_maincontent);
//
//                    wise_contents.setText(splitmainsentences[i]);
//                }
//
//            }
//
//        }
//
//
//
//
////        // 테스트
////        int ii = 0;
////        while(ii < 10 ){
////            ii++;
////
////            break;
////        }
////
////        myi2.setText(String.valueOf(ii));
//
//        // 테스트 2
////        for(int ii = 0; ii<100; ii++){
////            Log.v("로그 체크 settext", "" + ii);
////
////            myi2.setText(String.valueOf(ii));
////
////        }
//
////        int arr[] = {1, 2, 3, 4, 5};
////        for(int num : arr) {
////            System.out.println(num);
////        }
//
//    }