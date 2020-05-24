package com.impact.mycando;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.media.SoundPool;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

public class SettingActivity extends AppCompatActivity {


    // 문의하기 버튼
    Button btn_ask;
    // 뒤로가기하기 버튼
    ImageButton btn_back;




    // 회원가입 했을 때 함께 등록해준 userid
    SharedPreferences userid;
    String useridString;

    // 아이디를 키로 회원정보를 가져온다.
    SharedPreferences userinfo;
    SharedPreferences.Editor userinfoEditor;



    SoundPool reallySoundpool;
    int reallybgm;
    private Object AudioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


//        // 아이디별로 날짜를 다르게 설정할 수 있도록
//        // 로그인한 아이디를 키로 주고 그 때마다 date 다르게 해준다.
//        // 날짜 저장해줄 shared 파일
//        // SharedPreferences setdateFile;
//        setdateFile = getSharedPreferences("setdateFile", MODE_PRIVATE);
//
//        // 날짜 수정할 setdate editor 파일
//        // SharedPreferences.Editor setdateEditor;
//        setdateEditor = setdateFile.edit();
//
//        // 날짜 저장해줄 shared 파일
//        // SharedPreferences setgoalFile;
//        setgoalFile = getSharedPreferences("setgoalFile", MODE_PRIVATE);
//        // 날짜 수정할 setgoal editor 파일
//        // SharedPreferences.Editor setgoalEditor;
//        setgoalEditor = setgoalFile.edit();
//
//        // SharedPreferences profileimageFile;
//        profileimageFile = getSharedPreferences("userimageFile", MODE_PRIVATE);
//        // editor 호출
//        // 회원마다의 이미지 저장, 수정할 때 필요한 에디터
//        // SharedPreferences.Editor profileimageEditor
//        profileimageEditor = profileimageFile.edit();
//
//        // onPause
//        // 아이디저장 체크박스 작업
//        // SharedPreferences saveidFile
//        saveidFile = getSharedPreferences("saveidFile",0);
//        // SharedPreferences.Editor saveidEditor
//        saveidEditor = saveidFile.edit();

//        // 문의하기 버튼을 누르면 문의사항 화면으로 감
//        btn_ask = (Button) findViewById(R.id.ask_button);
//        btn_ask.setOnClickListener(
//                new Button.OnClickListener(){
//                    public void onClick(View v){
//                        Intent intent = new Intent(getApplicationContext(), AskActivity.class);
//                        startActivity(intent);
//                    }
//                }
//        );
//
//        // 뒤로가기 버튼을 누르면 프로필편집 화면으로 감
//        btn_back = (ImageButton) findViewById(R.id.back_icon);
//        btn_back.setOnClickListener(
//                new Button.OnClickListener(){
//                    public void onClick(View v){
//
//                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
//                        startActivity(intent);
//                        finish();
//
//                    }
//                }
//        );



    }



}
