package com.impact.mycando;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MenuActivity extends AppCompatActivity {

    // 메뉴화면이 아니라 마이페이지 화면
    private AdView mAdView;



    // 메인피드로 가는 버튼
    ImageButton btn_back;
    // 프로필편집 버튼
    Button btn_editprofile;

    // 알람설정 버튼
    Button btn_setalarm;
    // 공유하기 버튼
    Button btn_share;
    // 설정하기 이미지버튼
    ImageButton btn_gosetting;

    // 메뉴 화면의 닉네임 텍스트뷰
    TextView tv_nickname;

    // 프로필편집화면으로 넘어갈 때
    private int REQUEST_TEST = 1;

    // 목표설정한 화면에서 날짜와 목표를 받아와줌
    String rc_date;
    String rc_goal;
    // rc_date와 rc_goal을 tv_dategoal에 박아줌
    TextView tv_dategoal;
    // dategoal에 날짜와 목표를 담아줌
    String DateGoal; // date + goal

    // 프로필편집화면에서 받아온 Uri 값
    Uri selectedImageUri;
    // Uri값을 파싱할 이미지뷰
    ImageView rc_goalimage;

    // 목표이미지가 없으면 '목표이미지를 등록해주세요.', 목표이미지가 있으면 '내용 사라짐'
    TextView tv_goaltitle;

    // 회원가입한 정보 shared 객체를 아이디, 비밀번호 입력값과 비교하기 위해 가지고 와야함
    SharedPreferences userinfo;
    SharedPreferences.Editor userinfoEditor;

    // 회원정보 shared 파일에서 불러와준 닉네임값
    String menuuser_nickname;
    // 회원정보 shared 파일에서 불러와준 이미지값
    String menu_imageStr;

    // 회원가입 했을 때 함께 등록해준 userid
    SharedPreferences userid;
    String useridString;

    // 날짜 저장해둔 shared 파일
    SharedPreferences setdateFile;
    // 날짜 수정할 setdate editor 파일
    SharedPreferences.Editor setdateEditor;

    // 목표 저장해둔 shared 파일
    SharedPreferences setgoalFile;
    SharedPreferences.Editor setgoalEditor;

    // 회원마다의 이미지를 저장해주는 파일
    SharedPreferences profileimageFile;
    // 회원마다의 이미지 저장, 수정할 때 필요한 에디터
    SharedPreferences.Editor profileimageEditor;

    // 자동로그인할 때 저장해줄 아이디
    SharedPreferences autologinFile;
    // 자동로그인할 때 필요한 editor
    SharedPreferences.Editor autologinEditor;



    // 아이디와 아이디저장 체크박스 유무가 저장된 shared 파일
    SharedPreferences saveidFile ;
    // 아이디와 아이디저장 여부 수정하게 해주는 editor 객체
    SharedPreferences.Editor saveidEditor ;


    SharedPreferences goalManagementPref;
    SharedPreferences.Editor goalManagementEditor;
    String get_goallists;
    String goalslistCheck;
    boolean get_goalcheck;

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                        intent.putExtra("maingoal", DateGoal);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        // 광고 api
        MobileAds.initialize(this, getString(R.string.admob_app_id));

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

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



        // 해당 id를 담고 있는 걸 벨류값으로 주는 쉐어드 하나 더 만들어준 걸 가져온다.
        userid = getSharedPreferences("useridFile", MODE_PRIVATE);

        // 로그인할 때 가져온 id가 String useridString이다.
        useridString = userid.getString("userid", "");

        // 회원가입한 정보 shared 객체를 가져온다.
        userinfo = getSharedPreferences("userinfoFile", MODE_PRIVATE);
        // 로그인한 아이디를 키로 하고 value에 회원정보들이 담겨 있는 thisuserinfo를 String으로 가져와준다
        String thisuserinfo = userinfo.getString(useridString,"");

        // 회원정보에 있는 닉네임값을 split을 이용해 user_nickname값으로 받아와준다.
        menuuser_nickname = thisuserinfo.split("!@#")[1];

        // 메뉴화면의 닉네임 텍스트뷰를 참조
        tv_nickname = findViewById(R.id.menu_nickname);

        // shared 파일에서 닉네임을 불러와 string값에 담아준다.
        tv_nickname.setText(menuuser_nickname);

        // SharedPreferences profileimageFile;
        profileimageFile = getSharedPreferences("userimageFile", MODE_PRIVATE);

        // 이미지가 담겨있는 string값을 불러와 menu_imageStr에 담아준다.
        menu_imageStr = profileimageFile.getString(useridString, "");
        Log.v("메뉴화면 이미지 uri값 확인 로그 알림","1" + menu_imageStr);

        // 메뉴화면 목표와 관련된 이미지 위에 '목표이미지를 등록해주세요'
        tv_goaltitle = findViewById(R.id.goal_title);

        // 이미지가 담겨져 있는 string 값의 menu_imageStr이 null 값이 아니라면
        if(menu_imageStr.equals("") == false){

            // String값인 menu_imageStr를 Uri 값인 selectedImageUri로 바꿔줌.
            selectedImageUri = Uri.parse(menu_imageStr);
            Log.v("메뉴화면 이미지 uri값 확인 로그 2","2" + selectedImageUri);

            // 프로필편집화면에서 저장해준 uri값을 받을 이미지뷰를 참조해주고
            rc_goalimage = findViewById(R.id.goal_image);

            // 이미지뷰에 받아온 uri 값을 뿌려줌
            rc_goalimage.setImageURI(selectedImageUri);
            Log.v("메뉴화면 이미지 uri값 확인 로그 3","3" + selectedImageUri);

            // 목표이미지가 있다면 ""로 처리
            tv_goaltitle.setText("");

        }

        // 아이디별로 날짜를 다르게 설정할 수 있도록
        // 로그인한 아이디를 키로 주고 그 때마다 date 다르게 해준다.
        // 날짜 저장해줄 shared 파일
        // SharedPreferences setdateFile;
        setdateFile = getSharedPreferences("setdateFile", MODE_PRIVATE);
        setdateEditor = setdateFile.edit();

        // 날짜 저장해줄 shared 파일
        // SharedPreferences setgoalFile;
        setgoalFile = getSharedPreferences("setgoalFile", MODE_PRIVATE);
        setgoalEditor = setgoalFile.edit();

        // onPause
        // 아이디저장 체크박스 작업
        // SharedPreferences saveidFile
        saveidFile = getSharedPreferences("saveidFile",0);
        // SharedPreferences.Editor saveidEditor
        saveidEditor = saveidFile.edit();

        // 자동 로그인 체크가 해제되면 자동로그인한 정보 사라짐
        autologinFile = getSharedPreferences("autologinFile", Activity.MODE_PRIVATE);
        autologinEditor = autologinFile.edit();

        // date랑 goal 값을 목표설정화면에서 받아오기
        // String rc_date, rc_goal
        rc_date = setdateFile.getString(useridString,"");
        rc_goal = setgoalFile.getString(useridString,"");

        tv_dategoal = findViewById(R.id.menu_goal);

        // 날짜와 목표가 null값이 아니라면
        if(setdateFile.getString(useridString,"") != null && setgoalFile.getString(useridString,"") != null){

            // rc_date, rc_goal이 null 값 ("") 이 아니라면
            if(rc_date.equals("") == false && rc_goal.equals("") == false ){
                // 목표 참조
                // String DateGoal
                DateGoal = rc_date + "까지 " + rc_goal + "한다!!";
                // 목표에 내가 받아온 날짜와 목표를 set해줌
                tv_dategoal.setText(DateGoal);
                Log.v("메뉴화면에서 목표확인로그 ","" + rc_date + "까지 " + rc_goal + "한다!!");
            }

        }





        // 뒤로가기 버튼을 누르면 메인 화면으로 감
        btn_back = (ImageButton) findViewById(R.id.back_icon);
        btn_back.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                        intent.putExtra("maingoal", DateGoal);
                        startActivity(intent);
                        // finish();
                    }
                }
        );

        // 프로필편집 버튼을 누르면 프로필편집 화면으로 감
        btn_editprofile = (Button) findViewById(R.id.editprofile_button);
        btn_editprofile.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent(getApplicationContext(), EditprofileActivity.class);
                        startActivity(intent);
                    }
                }
         );

        // 로그아웃 클릭하면 제일 첫 화면(로그인, 회원가입 둘 중 하나 고르는 화면)으로 이동하는 코드
        Button btn_logout = (Button) findViewById(R.id.account_logoutbutton);
        btn_logout.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        // 로그아웃하기
                        Go_logout();

                    }
                }

        );

        // 회원탈퇴 클릭하면 제일 첫 화면(로그인, 회원가입 둘 중 하나 고르는 화면)으로 이동하는 코드
        Button btn_deleteAccount = (Button) findViewById(R.id.account_deletebutton);
        btn_deleteAccount.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        // 회원탈퇴하기
                        Go_deleteaccount();

                    }
                }
        );






//        // 설정하기 버튼을 누르면 설정 화면으로 이동
//        btn_gosetting = (ImageButton) findViewById(R.id.goSettingIcon);
//        btn_gosetting.setOnClickListener(
//                new Button.OnClickListener(){
//                    public void onClick(View v){
//                        // 다이어로그로 바꾸기
//                        Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
//                        startActivity(intent);
//                    }
//                }
//        );



    }

    // 스택이 사라져야 하는데 안 사라진다.
    // 로그아웃 메쏘드
    private void Go_logout() {
        new AlertDialog.Builder(this)
                .setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?")
                .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent i = new Intent(MenuActivity.this, LoginActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(i);


                        // 자동로그인 정보 사라지게 함
                        autologinEditor.clear();
                        autologinEditor.commit();


                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {



                    }
                })
                .show();
    }

    // 스택이 사라져야 하는데 안 사라진다.
    // 회원탈퇴 메쏘드
    private void Go_deleteaccount() {
        new AlertDialog.Builder(this)
                .setTitle("회원탈퇴").setMessage("회원탈퇴 하시겠습니까?")
                .setPositiveButton("회원탈퇴", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

//                        reallySoundpool = new SoundPool(1, (Integer) AudioManager,1);
//                        reallybgm = reallySoundpool.load(getApplicationContext(),R.raw.really,1);
//                        reallySoundpool.play(reallybgm,1,1,0,1,1);


                        // 해당 id를 담고 있는 걸 벨류값으로 주는 쉐어드 하나 더 만들어준 걸 가져온다.
                        userid = getSharedPreferences("useridFile", MODE_PRIVATE);
                        // 로그인할 때 가져온 id가 String useridString이다.
                        useridString = userid.getString("userid", "");

                        // 회원가입한 정보 shared 객체를 가져와서
                        // SharedPreferences userinfo;
                        userinfo = getSharedPreferences("userinfoFile", MODE_PRIVATE);
                        // SharedPreferences.Editor userinfoEditor ; editor 호출
                        userinfoEditor = userinfo.edit();

                        // 해당 아이디를 key로 가지고 있는 회원정보 (이름, 닉네임, 아이디, 패스워드 등)를 deleteUserinfo에 담아준다.
                        String deleteUserinfo = userinfo.getString(useridString, "");
                        Log.v("회원탈퇴 deleteUserinfo 벨류 값 삭제 확인 알림 로그"," deleteUserinfo : " + deleteUserinfo);

                        // 회원탈퇴한 유저이름 모음집들
                        SharedPreferences deleteIDpref = getSharedPreferences("deleteidPreffile", MODE_PRIVATE);
                        SharedPreferences.Editor deletePrefEditor = deleteIDpref.edit();
                        deletePrefEditor.putString(useridString, useridString);
                        deletePrefEditor.commit();

                        // remove 안에 키값을 넣어줘야 하는데 계속 string 값을 넣어줬음 ;;
                        userinfoEditor.remove(useridString).commit();
                        Log.v("회원탈퇴 deleteUserinfo 벨류 값 삭제 확인 알림 로그"," useridString : " + useridString);

                        // setdateFile, setgoalFile,  userimageFile, saveidFile 삭제하기
                        // setdateEditor / setgoalEditor
                        // 해당 유저아이디의 목표기간 삭제해줌
                        setdateEditor.remove(useridString);
                        setdateEditor.commit();

                        // 해당 유저아이디의 목표설정 삭제해줌
                        setgoalEditor.remove(useridString);
                        setgoalEditor.commit();

//                        // 해당 유저아이디의 목표와 관련된 이미지 삭제해줌
//                        profileimageEditor.remove(useridString);
//                        profileimageEditor.commit();

                        // 해당 유저가 아이디 저장과 관련된 정보를 가지고 있으면 삭제해줌.
                        saveidEditor.clear();
                        saveidEditor.commit();

                        // 자동로그인 정보 사라지게 함
                        autologinEditor.clear();
                        autologinEditor.commit();

                        // 로그인할 때 저장해줬던 아이디 key value도 삭제해준다.
                        userinfoEditor.remove("userid").commit();

                        // 화면 setFlags , https://stickyny.tistory.com/109
                        Intent i = new Intent(MenuActivity.this, LoginActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(i);

                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                })
                .show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // SharedPreferences goalManagementPref
        goalManagementPref = getSharedPreferences("goalManagementFile", MODE_PRIVATE);
        // SharedPreferences.Editor goalManagementEditor
        goalManagementEditor = goalManagementPref.edit();


        // String goalslistCheck;
        goalslistCheck = goalManagementPref.getString(useridString,"");
        Log.v("goalslistCheck 확인 알림 로그","goalslistCheck " +  goalslistCheck);

        // splitgoallists 배열에 get_goallists를 &&&로 잘라준다.
        String[] splitgoallists = goalslistCheck.split("&&&");
        Log.v("splitgoallists 확인 알림 로그", " splitgoallists " + splitgoallists );
        Log.v("splitgoallists[splitgoallists.length - 1]확인 알림 로그", "splitgoallists[splitgoallists.length - 1] " + splitgoallists[splitgoallists.length - 1] );

        // 길이가 1이면(값이 없는 것.) split 자체가 안됨
        if(splitgoallists.length >= 2){

            // boolean get_goalcheck
            get_goalcheck = Boolean.parseBoolean(splitgoallists[splitgoallists.length - 1].split("###")[1]);
            Log.v("get_goalcheck 확인 알림 로그", " get_goalcheck " + get_goalcheck );

            if(get_goalcheck == true){
                tv_dategoal.setText("집중목표를 다시 설정해주세요!");
                Log.v("get_goalcheck 확인 알림 로그 2 골체크 2", " get_goalcheck " + get_goalcheck );
            }

        }

    }
}

