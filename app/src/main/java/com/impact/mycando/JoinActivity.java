package com.impact.mycando;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JoinActivity extends AppCompatActivity {

    // 회원가입하기 버튼
    Button btn_join;

    // 뒤로가기하기 버튼
    ImageButton btn_back;

    // 회원가입 정보를 입력받는 edittext 객체 선언 / 이름 닉네임 아이디 패스워드 패스워드 체크
    EditText put_name, put_nickname, put_id, put_password, put_passwordcheck;

    // 입력한 값들을 받아주는 string 값
    String name, nickname, id, password, passwordcheck;

    // 회원정보 저장할 shared 객체
    SharedPreferences userinfo;
    // 회원정보 수정할 editor 객체
    SharedPreferences.Editor userinfoEditor;

    // 쉐어드에 있는 값 null 값인지 체크
    String userinfoEmptyCheck;

    // 아이디 중복체크할 때 필요한 변수
    boolean idcheck;

    // 탈퇴한 회원정보
    SharedPreferences deleteIDpref;
    SharedPreferences.Editor deletePrefEditor;

    // 아이디 정규식 체크
    Pattern idpattern;
    Matcher idMatch;
    String idRegex;

    // 비밀번호 정규식 체크
    Boolean pwRegexCheck;
    String pwRegex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);




//        //1. 숫자,문자,특수문자 모두 포함 (8~15자)
//        String pwRegex= "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&]).{8,15}.$";
//
//        // String password
//        Boolean pwRegexCheck = Pattern.matches(pwRegex,password);
//
//
//        if( pwRegexCheck == true){
//            Toast.makeText(this, "정규식이 맞음", Toast.LENGTH_SHORT).show();
//        }
//        else{
//            Toast.makeText(this, "틀림", Toast.LENGTH_SHORT).show();
//        }





        // main.xml 레이아웃에서 설정한 뷰들은 초기 설정만 정의를 해놔서 이벤트를 받거나 할 수 없습니다
        // xml 레이웃에 설정된 뷰들을 가져오는 메소드가 findViewById
        // View의 id를 R 클래스에서 받아옴
        // 이름 받아와서
        put_name = findViewById(R.id.et_name);
        // 닉네임
        put_nickname = findViewById(R.id.et_nickname);
        // 패스워드
        put_password = findViewById(R.id.et_pw);
        // 패스워드 체크
        put_passwordcheck =findViewById(R.id.et_pwcheck);

        // 아이디
        put_id = findViewById(R.id.et_id);

        // 이름에 입력된 값을 string name 값에 넣어줌
        // String name
        name = put_name.getText().toString();
        // 닉네임
        // String nickname
        nickname = put_nickname.getText().toString();
        // String id
        id = put_id.getText().toString();
        // 패스워드
        // String password
        password = put_password.getText().toString();
        // 패스워드 체크
        // String passwrodcheck
        passwordcheck = put_passwordcheck.getText().toString();

        // 회원가입한 유저정보 파일
        // "userinfofile"이라는 shared 파일을 쓰겠다 - shared 객체 얻기
        // SharedPreferences userinfo
        userinfo = getSharedPreferences("userinfoFile", MODE_PRIVATE);

        // shared 파일을 수정하는 객체를 얻는다.
        // SharedPreferences.Editor userinfoEditor
        userinfoEditor = userinfo.edit();

        // String userinfoEmptyCheck null 값 체크
        userinfoEmptyCheck = userinfo.getString(put_id.getText().toString(), "");

        // SharedPreferences deleteIDpref
        deleteIDpref = getSharedPreferences("deleteidPreffile", MODE_PRIVATE);
        // SharedPreferences.Editor deletePrefEditor
        deletePrefEditor = deleteIDpref.edit();








        // 아이디 중복체크
        Button btn_idcheck = findViewById(R.id.id_check);
        btn_idcheck.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        // userinfo가 null이 아니라면
                        if(userinfo != null) {
                            Log.v("회원가입 중복체크 확인 알림 로그 1 ", " userinfo :  " + userinfo);

                            // 아이디 형식 체크 // 영문, 숫자로만 이루어진 5 ~ 12자 이하
                            //출처: https://darkhorizon.tistory.com/259 [너머]
                            // String idRegex
                            idRegex = "^[a-zA-Z0-9_]{5,12}$";  // "^[a-zA-Z]{1}[a-zA-Z0-9_]{4,11}$"; // 시작은 영문으로만, '_'를 제외한 특수문자 안되며 영문, 숫자, '_'으로만 이루어진 5 ~ 12자 이하
//                            초창기에 ID를 '영어+숫자'조합으로만 작성가능했기에 '닉네임'이라는 부분이 추가로 생겨나게 되었고..
//                            그게 쭈욱 이어져내려온거라고 보이는데..
//                            지금처럼 누구나 커뮤니티를 만들 수 있게 되기 전에는 인터넷 접속 서비스를 제공하는 일부 기업에서만 회원 가입을 받았죠. 그런데 초창기 인터넷 서비스들은 메일이나 홈페이지 공간 등을 제공하는 것이 주력상품이었습니다. 아이디를 메일 주소, FTP 계정 등에도 똑같이 사용하려니 영문과 숫자, 극소수의 특수

                            // Pattern idpattern
                            idpattern = Pattern.compile(idRegex);
                            // Matcher idMatch
                            idMatch = idpattern.matcher(put_id.getText().toString());

                            // https://perpetual.tistory.com/15
                            //  Log.v("아이디 확인 알림 로그","[" + put_id.getText().toString() + "] : " + idMatch.find());

                            // userinfo가 내가 아이디에 입력한 값을 가지고 있다면 이미 존재하는 아이디라고.
                            if (userinfo.contains(put_id.getText().toString())) {

                                Toast.makeText(JoinActivity.this, "이미 존재하는 아이디입니다.", Toast.LENGTH_SHORT).show();
                                Log.v("회원가입 중복체크 확인 알림 로그 2 ", " 중복체크 boolean :  " + userinfo.contains(put_id.getText().toString()) + " 입력값 확인 " + put_id.getText().toString());

                                // 한 번 탈퇴한 아이디로는 재가입 불가능하게
                            } else if (deleteIDpref.contains(put_id.getText().toString())) {

                                Toast.makeText(JoinActivity.this, "한 번 탈퇴한 아이디로 재가입은 불가능합니다.", Toast.LENGTH_SHORT).show();


                                // 아이디 형식과 일치하지 않으면
                            } else if (idMatch.find() == false){
                                Toast.makeText(JoinActivity.this, "아이디 형식에 맞게 입력해주세요", Toast.LENGTH_SHORT).show();
                                Log.v("아이디 확인 알림 로그","[" + put_id.getText().toString() + "] : " + idMatch.find());

                            } else if(put_id.getText().toString().equals("")){
                                Toast.makeText(JoinActivity.this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();

                                // Userinfo가 내가 아이디에 입력한 값을 가지고 있지 않다면 등록 가능하게.
                            }else{

                                Toast.makeText(JoinActivity.this, "등록 가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
                                Log.v("회원가입 중복체크 확인 알림 로그 3 ", " 중복체크 boolean 2:  " + userinfo.contains(put_id.getText().toString()) + " 입력값 확인 " + put_id.getText().toString());

                                ischecking();
                                Log.v("회원가입 아이디 중복체크 버튼 확인 알림 로그 4",  " ischecking() : " +ischecking()  + "idcheck" + idcheck);

                            }

                            // userinfo가 null이라면 등록가능한 아이디라고 체크
                        }else if(put_id.getText().toString().equals("")){
                            Toast.makeText(JoinActivity.this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(JoinActivity.this, "등록 가능한 아이디입니다.", Toast.LENGTH_SHORT).show();

                            ischecking();
                            Log.v("회원가입 아이디 중복체크 버튼 확인 알림 로그 5",  " ischecking() : " +ischecking()  + "idcheck" + idcheck);

                        }

                    }
                }
        );



        // 가입하기 버튼을 누르면 로그인 화면으로 감
        btn_join = (Button) findViewById(R.id.join_button);
        btn_join.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v) {

                            // 아이디 중복체크 안했으면 중복체크 먼저 하고 오라고 함
                            if(idcheck != true){
                                Toast.makeText(JoinActivity.this, "아이디 중복체크를 해주세요!", Toast.LENGTH_SHORT).show();
                                Log.v("회원가입 버튼 아이디 중복체크 확인 알림 로그",  "idcheck: " + idcheck);
                            } else {

                                // Userinfo가 내가 아이디에 입력한 값을 가지고 있다면 이미 존재하는 아이디라고.
                                if (userinfo.contains(put_id.getText().toString())) {

                                    Toast.makeText(JoinActivity.this, "이미 존재하는 아이디입니다.", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(JoinActivity.this, "아이디 중복체크를 해주세요", Toast.LENGTH_SHORT).show();

                                } else if(deleteIDpref.contains(put_id.getText().toString())){
                                    Toast.makeText(JoinActivity.this, "한 번 탈퇴한 아이디로 재가입은 불가능합니다.", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(JoinActivity.this, "아이디 중복체크를 해주세요", Toast.LENGTH_SHORT).show();

                                    // 공란이 없도록 설정
                                } else if(put_name.getText().toString().equals("") || put_nickname.getText().toString().equals("")
                                        || put_id.getText().toString().equals("") || put_password.getText().toString().equals("") || put_passwordcheck.getText().toString().equals("")){

                                    Log.v("회원가입 공란 확인 및 저장 알림 로그",  " put_name.getText().toString(): " +  put_name.getText().toString() + " put_nickname.getText().toString() : " + put_nickname.getText().toString());
                                    Log.v("회원가입 공란 확인 및 저장 알림 로그",  " put_id.getText().toString() : " +  put_id.getText().toString() + " put_password.getText().toString() : " +  put_password.getText().toString());
                                    Toast.makeText(JoinActivity.this, "공란이 없는지 확인해주세요.", Toast.LENGTH_SHORT).show();

                                    // 회원가입할 때 패스워드 체크
                                } else {

                                    //1. 숫자,문자,특수문자 모두 포함 (8~15자)
                                    // String pwRegex
                                    pwRegex= "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&]).{8,15}.$";

                                    // put_password.getText().toString()
                                    // Boolean pwRegexCheck
                                    pwRegexCheck = Pattern.matches(pwRegex, put_password.getText().toString());

                                    // 비밀번호 형식과 일치하지 않으면
                                    if(pwRegexCheck != true){
                                        Toast.makeText(getApplicationContext(), "비밀번호 형식에 맞게 입력해주세요!", Toast.LENGTH_SHORT).show();

                                    // 비밀번호 형식과 일치하면
                                    } else {

                                        // 내가 입력한 패스워드와 패드워드 체크한 것 비교.
                                        if (put_password.getText().toString().equals(put_passwordcheck.getText().toString())){

                                            // 내가 등록할 유저의 정보
                                            String UserInfoString = put_name.getText().toString() + "!@#"
                                                    + put_nickname.getText().toString() + "!@#"
                                                    + put_id.getText().toString() + "!@#"
                                                    + put_password.getText().toString();

                                            // "입력값"이란 key 값으로 UserInfoString에 들어간 = 내가 입력한 id, pw, 이름, 닉네임을 저장하겠다.
                                            userinfoEditor.putString(put_id.getText().toString(), UserInfoString);

                                            Log.v("회원가입 UserInfoString 입력 및 저장 알림 로그",  "  UserInfoString에 입력한 값들 >> " +  UserInfoString);
                                            Log.v("회원가입 UserInfoString 입력 및 저장 알림 2 로그",  " 이름에 입력한 값 : " +  put_name.getText().toString() + " 닉네임에 입력한 값 : " + put_nickname.getText().toString());
                                            Log.v("회원가입 UserInfoString 입력 및 저장 알림 3 로그 ",   " 아이디에 입력한 값 " + put_id.getText().toString() + " 비밀번호에 입력한 값 :  " + put_password.getText().toString()+ " 비밀번호 체크에 입력한 값 :  " + put_passwordcheck.getText().toString());

                                            userinfoEditor.commit();

                                            Toast.makeText(JoinActivity.this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();

                                            //  로그인 창으로 넘어간다.
                                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                            startActivity(intent);
                                            finish();

                                        } else {

                                            Toast.makeText(getApplicationContext(), "패스워드가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();

                                        }

                                    }


                                }

                            }


                    }
                }
        );

        // 뒤로가기 버튼을 누르면 로그인 화면으로 감
        btn_back = (ImageButton) findViewById(R.id.back_icon);
        btn_back.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );


    }

    // 아이디체크를 true로 만들어주는 메쏘드 / 중복체크가 되었는지 안되었는지 확인용
    boolean ischecking(){

        return idcheck = true;
    }



}

//        put_id.setOnFocusChangeListener(new View.OnFocusChangeListener(){
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if(hasFocus) {
//                    Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$");
//                    Matcher m = p.matcher((put_id).getText().toString());
//
//                    if ( !m.matches()){
//                        Toast.makeText(JoinActivity.this, "Email형식으로 입력하세요", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });


//        //이메일형식체크
//        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(id).matches())
//        {
//            Toast.makeText(JoinActivity.this,"이메일 형식이 아닙니다",Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        //비밀번호 유효성
//        if(!Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,20}$", password))
//        {
//            Toast.makeText(JoinActivity.this,"비밀번호 형식을 지켜주세요.",Toast.LENGTH_SHORT).show();
//
//            return;
//        }

//        TextView join_title = (TextView) findViewById(R.id.jointitle);
//        join_title.setTextColor(Color.parseColor("#ff0000"));

//                        else{
//
//                                // 이름, 닉네임, 아이디, 패스워드, 패스워드체크가 널값이 아니고 입력한 비밀번호와 입력한 비밀번호 확인이 같으면 회원가입
//                                if(name != null && nickname != null && id != null && password != null && passwordcheck != null && put_password.getText().toString().equals(put_passwordcheck.getText().toString())) {
//
//                                // 내가 등록할 유저의 정보
//                                String UserInfoString = put_name.getText().toString() + "!@#"
//                                + put_nickname.getText().toString() + "!@#"
//                                + put_id.getText().toString() + "!@#"
//                                + put_password.getText().toString();
//
//                                // "입력값"이란 key 값으로 UserInfoString에 들어간 = 내가 입력한 id, pw, 이름, 닉네임을 저장하겠다.
//                                userinfoEditor.putString(put_id.getText().toString(), UserInfoString );
//
//                                Log.v("회원가입 UserInfoString 입력 및 저장 알림",  "  UserInfoString에 입력한 값들 >> " +  UserInfoString);
//                                Log.v("회원가입 UserInfoString 입력 및 저장 알림 2 ",  " 이름에 입력한 값 : " +  put_name.getText().toString() + " 닉네임에 입력한 값 : " + put_nickname.getText().toString());
//                                Log.v("회원가입 UserInfoString 입력 및 저장 알림 3 ",   " 아이디에 입력한 값 " + put_id.getText().toString() + " 비밀번호에 입력한 값 :  " + put_password.getText().toString());
//
//                                userinfoEditor.commit();
//
//                                Toast.makeText(JoinActivity.this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
//
//                                //  로그인 창으로 넘어간다.
//                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//        startActivity(intent);
//        finish();
//
//        } else if(userinfo.contains(put_id.getText().toString())) {
//        Toast.makeText(JoinActivity.this, "이미 등록된 아이디입니다.", Toast.LENGTH_SHORT).show();
//
//        // 비밀번호와 비밀번호 체크가 같지 않다면
//        } else if (put_password.getText().toString().equals(put_passwordcheck.getText().toString()) == false){
//        Toast.makeText(JoinActivity.this, "패스워드가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
//
//        } else{
//        Toast.makeText(JoinActivity.this, "공란이 없는지 확인해주세요.", Toast.LENGTH_SHORT).show();
//
//        }
//
//        }