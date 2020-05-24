package com.impact.mycando;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.impact.mycando.recyclerview_will.WillActivity;

public class PopupActivity extends Activity {

    TextView txtText;
    Button yesButton, laterButton;

    // 목표를 저장시켜주기 위한 shared 파일 생성
    SharedPreferences userinfo;
    // 회원정보 shared 파일을 수정하기 위한 파일 생성
    SharedPreferences.Editor userinfoEditor;

    // 아이디 마다의 목표를 설정해주기 위한 작업
    SharedPreferences userid;
    // 로그인할 때 가져온 id가 useridString이다.
    String useridString;

    // 목표 저장해줄 shared 파일
    SharedPreferences setgoalFile;
    // 목표 수정할 setgoal editor 파일
    SharedPreferences.Editor setgoalEditor;

    String goalcheck;
    private static final int REQ_FIRST_WILL = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_activity);

        //UI 객체생성
        txtText = (TextView)findViewById(R.id.txtText);

        // 해당 id를 담고 있는 걸 벨류값으로 주는 쉐어드 하나 더 만들어준 걸 가져온다.
        // SharedPreferences userid;
        userid = getSharedPreferences("useridFile", MODE_PRIVATE);
        // 로그인할 때 가져온 id가 useridString이다.
        // String useridString;
        useridString = userid.getString("userid", "");

        // 회원가입한 정보 shared 객체를 가져와 닉네임을 불러와준다.
        userinfo = getSharedPreferences("userinfoFile", MODE_PRIVATE);

        // 날짜 저장해줄 shared 파일
        // SharedPreferences setgoalFile;
        setgoalFile = getSharedPreferences("setgoalFile", MODE_PRIVATE);
        // 날짜 수정할 setgoal editor 파일
        // SharedPreferences.Editor setgoalEditor;
        setgoalEditor = setgoalFile.edit();

        // 목표값 있는지 체크
        goalcheck = setgoalFile.getString(useridString, "");

        // 나중에 버튼 , 그럼요 버튼 구분
        // 지금 글을 쓴다면 will write
        yesButton = (Button) findViewById(R.id.yes_button);
        yesButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        if(userinfo.contains(useridString)){

                            // 아직 목표를 작성하지 않았다면
                            if(goalcheck.equals("")){

                                Toast.makeText(getApplicationContext(), "집중목표 먼저 설정해주세요!!", Toast.LENGTH_SHORT).show();
                                finish();

                            // 목표가 있다면 작성할 수 있게
                            } else {
                                Intent intent = new Intent(getApplicationContext(), WillActivity.class);
                                intent.putExtra("check","checkfirst");
                                startActivity(intent);
//                                startActivityForResult(intent, REQ_FIRST_WILL);

                                finish();

//                                startActivityForResult(intent, REQ_ADD_WILL);

                            }

                        }

                    }
                }
        );

        // 나중에 쓴다면 will 화면
        laterButton = (Button) findViewById(R.id.later_button);
        laterButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        Intent intent = new Intent(getApplicationContext(), WillActivity.class);
                        startActivity(intent);
                        finish();

                    }
                }
        );



    }

//    //확인 버튼 클릭
//    public void mOnClose(View v){
//
//        Intent intent = new Intent(this, Main2Activity.class);
//        startActivity(intent);
//        finish();
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()== MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

//    @Override
//    public void onBackPressed() {
//        //안드로이드 백버튼 막기
//        return;
//    }

}
