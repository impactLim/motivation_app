package com.impact.mycando;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

public class IntroActivity extends AppCompatActivity {

    // 자동로그인 했을 때 저장해준 값들을 불러와주기 위한 파일
    SharedPreferences autologinFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        // https://abc0123.tistory.com/121 bar 없애기
        ActionBar bar = getSupportActionBar();
        bar.hide();

        ImageView iv = (ImageView) findViewById(R.id.imageView);
//        iv.setOnClickListener(new ImageView.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//
//
//
////                // 자동로그인 저장정보를 불러온다.
////                // SharedPreferences autologinFile;
////                autologinFile = getSharedPreferences("autologinFile", Activity.MODE_PRIVATE);
////
////                //  자동로그인 정보에 자동로그인이 체크되어 있는지 찾아보고 boolean값에 true or false를 넣어준다.
////                Boolean autologincheck = autologinFile.getBoolean("autologinChecked", false);
////
////                // 자동로그인에 체크가 되어 있으면 메인으로 바로 넘어가고
////                if(autologincheck == true){
////
////                    CheckTypesTask task = new CheckTypesTask();
////
////                    //  execute( ) 명령어를 통해 AsyncTask을 실행합니다
////                    task.execute();
////
////                    // 자동로그인에 체크가 되어 있지 않으면 로그인 화면으로 넘어간다.
////                }else{
////
////                    Intent intent = (new Intent(IntroActivity.this,LoginActivity.class));
////                    startActivity(intent);
////
////                }
//
//                // 생각 정리 - 일기, 오늘 진행한 작업, 오늘 공부한 내용, ximd - 에러 및 문제해결 프로세스
//
//            }
//        });

    }

    Handler handler = new Handler();

    Runnable r = new Runnable() {
        @Override
        public void run() {
            // 자동로그인 저장정보를 불러온다.
            // SharedPreferences autologinFile;
            autologinFile = getSharedPreferences("autologinFile", Activity.MODE_PRIVATE);

            //  자동로그인 정보에 자동로그인이 체크되어 있는지 찾아보고 boolean값에 true or false를 넣어준다.
            Boolean autologincheck = autologinFile.getBoolean("autologinChecked", false);

            // 자동로그인에 체크가 되어 있으면 메인으로 바로 넘어가고
            if(autologincheck == true){

                CheckTypesTask task = new CheckTypesTask();

                //  execute( ) 명령어를 통해 AsyncTask을 실행합니다
                task.execute();

                // 자동로그인에 체크가 되어 있지 않으면 로그인 화면으로 넘어간다.
            }else{

                Intent intent = (new Intent(IntroActivity.this,LoginActivity.class));
                startActivity(intent);

            }

        }
    };

    // AsyncTask 를 상속받아 만든 클래스는 3가지 함수를 구현
    private class CheckTypesTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog asyncDialog = new ProgressDialog(
                IntroActivity.this);

        // AsyncTask로 백그라운드 작업을 실행하기 전에 onPreExcuted( )실행됩니다. 이 부분에는 이미지 로딩 작업이라면 로딩 중 이미지를 띄워 놓기 등, 스레드 작업 이전에 수행할 동작을 구현합니다.
        // onPreExecute() : 작업시작, ProgressDialog 객체를 생성하고 시작
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("자동로그인 중입니다..");

            // show dialog
            asyncDialog.show();

        }

        //  doInBackground() : 진행중, ProgressDialog 의 진행 정도를 표현
        // 백그라운드 스레드에서 작업 종료 후, 결과를 메인 스레드에서 통보해 줄 수 있고(onPostExecute)
        // doInBackground( ) 에서 중간 중간 진행 상태를 UI에 업데이트 하도록 하려면 publishProgress( ) 메소드를 호출 합니다.
        // doInBackground( ) 메소드에서 작업이 끝나면 onPostExcuted( ) 로 결과 파라미터를 리턴하면서 그 리턴값을 통해 스레드 작업이 끝났을 때의 동작을 구현.
        @Override
        protected Void doInBackground(Void... arg0) {

            try {
                for (int i = 0; i < 2; i++) {
                    //asyncDialog.setProgress(i * 30);
                    Thread.sleep(500);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;

        }

        //  doPostExecute() : 종료, ProgressDialog 종료 기능을 구현
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            asyncDialog.dismiss();

            // 다이어로그 사라지고 나서 화면전환될 수 있도록.
            Intent intent = new Intent(IntroActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 다시 화면에 들어왔을 때 예약 걸어주기
        handler.postDelayed(r, 3000); // 2.5초 뒤에 Runnable 객체 수행
        Log.v("intro handler 확인 알림 ", " ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 화면을 벗어나면, handler 에 예약해놓은 작업을 취소하자
        // 실행 취소하는 법
        handler.removeCallbacks(r); // 예약 취소
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
////        CheckTypesTask task = new CheckTypesTask();
////        task.execute();
////        finish();
//        // Toast.makeText(this, "intro onPause 호출 됨", Toast.LENGTH_LONG).show();
//        Log.v("intro onPause 호출 확인 로그","intro onPause 호출");
//
//    }
}
//    @Override
//    protected void onResume() {
//        super.onResume();
//        finish();
//    }


//    @Override
//    protected void onStop() {
//        super.onStop();
//        finish();
//        Toast.makeText(this, "Main onStop 호출 됨", Toast.LENGTH_LONG).show();
//        Log.v("Main onStop 호출","Main onStop 호출");
//
//    }


//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        finish();
//        Toast.makeText(this, "Intro onDestroy 호출됨 ", Toast.LENGTH_LONG).show();
//        Log.v("Intro onDestroy 호출 확인 로그","Intro onDestroy 호출");
//
//    }

//        // 이메일 형식 체크
//        String emailregStr = "^[a-zA-Z0-9]+[@][a-zA-Z0-9]+[\\.][a-zA-Z0-9]+$";
//        String emailStr = "babolsk@bbb.com";
//        Pattern pattern = Pattern.compile(emailregStr);
//        Matcher match = pattern.matcher(emailStr);
//
//        // 매칭에 맞을 경우 true
//        Log.v("이메일 확인 알림 로그","[" + emailStr + "] : " + match.find());
//
//        // 아이디 형식 체크 // 영문, 숫자로만 이루어진 5 ~ 12자 이하
//        //출처: https://darkhorizon.tistory.com/259 [너머]
//        String idregStr2 = "^[a-zA-Z0-9_]{5,12}$";  // "^[a-zA-Z]{1}[a-zA-Z0-9_]{4,11}$"; // 시작은 영문으로만, '_'를 제외한 특수문자 안되며 영문, 숫자, '_'으로만 이루어진 5 ~ 12자 이하
//        String idStr = "123123";
//
//
//        Pattern pattern2 = Pattern.compile(idregStr2);
//        Matcher match2 = pattern2.matcher(idStr);
//
//        // https://perpetual.tistory.com/15
//        Log.v("아이디 확인 알림 로그","[" + idStr + "] : " + match2.find());
//
//
//
//        // 비밀번호 체크
//        String userId = "아이디";
//        String password = "비밀번호";
//
//        String pwPattern = "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z])(?=.*[A-Z]).{9,12}$";
//        Matcher matcher = Pattern.compile(pwPattern).matcher(password);
//
//        pwPattern = "(.)\\1\\1\\1";
//        Matcher matcher2 = Pattern.compile(pwPattern).matcher(password);
//
//
////        // 비밀번호 체크
////        EditText t1 = (EditText) findViewById(R.id.et_name);
////
////        String a = t1.getText().toString();
////
////        //1. 숫자,문자,특수문자 모두 포함 (8~15자)
////        String pwPattern3 = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&]).{8,15}.$";
////
////        Boolean tt = Pattern.matches(pwPattern3,a);
////
////
////        if( tt == true){
////            Toast.makeText(this, "정규식이 맞음", Toast.LENGTH_SHORT).show();
////        }
////        else{
////            Toast.makeText(this, "틀림", Toast.LENGTH_SHORT).show();
////        }