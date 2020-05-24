package com.impact.mycando;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    // 로그인 버튼
    Button btn_login;
    // 회원가입 버튼
    Button btn_join;

    // 로그인화면에서 입력받을 아이디와 패스워드 값
    EditText write_id;
    EditText write_pw;

    // 회원가입한 정보 shared 객체를 아이디, 비밀번호 입력값과 비교하기 위해 가지고 와야함
    SharedPreferences userinfo;

    // 아이디 저장 시 필요한 체크박스
    CheckBox saveid_box;
    // 아이디와 아이디저장 체크박스 유무가 저장된 shared 파일
    SharedPreferences saveidFile ;
    // 아이디와 아이디저장 여부 수정하게 해주는 editor 객체
    SharedPreferences.Editor saveidEditor ;
    // 저장된 아이디
    String saveid;
    // 아이디 저장 체크했는지 여부
    Boolean checksaveidbox;


    // 자동로그인 할 때 필요한 체크박스
    CheckBox autologin_box;
    // 자동로그인할 때 저장해줄 아이디
    SharedPreferences autologinFile;
    // 자동로그인할 때 필요한 editor
    SharedPreferences.Editor autologinEditor;
    Boolean checkautologinbox;

    // 로그인할 때 존재하는 아이디라면 thisuserinfo에 회원정보를 받아와서 패스워드와 닉네임을 split할 준비를 한다.
    String thisuserinfo;

    // 권한 체크
    String[] permission_list = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    boolean permissionCheck;

    // 탈퇴한 회원정보
    SharedPreferences deleteIDpref;
    SharedPreferences.Editor deletePrefEditor;

    String usernickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        checkPermission();



        // 입력받을 id 값 - EditText write_id
        write_id = findViewById(R.id.et_id);
        // 입력받을 pw 값 - EditText write_pw
        write_pw = findViewById(R.id.et_pw);

        // 회원가입한 정보 shared 객체를 아이디, 비밀번호 입력값과 비교하기 위해 가지고 와야함
        userinfo = getSharedPreferences("userinfoFile", MODE_PRIVATE);

        // oncreat - 아이디저장 체크박스 작업
        // SharedPreferences saveidFile ;
        saveidFile = getSharedPreferences("saveidFile",0) ;
        // onstop했을 때 저장했던 입력값과 체크박스 상태여부를 불러온다.
        // String saveid
        saveid = saveidFile.getString("id_save","");
        // Boolean checksaveidbox
        checksaveidbox = saveidFile.getBoolean("id_savecheckbox",false);

        // 체크박스 참조
        saveid_box = (CheckBox) findViewById(R.id.idsavebox);

        // 체크박스에 체크가 되어 있었다면, write_id란 EditText에 onstop 될 때 저장되었던 값(입력값)을 oncreat 될 때 꺼내서 set 해준다.
        // 체크박스에 체크가 되어 있었다면, checkSaveId란 체크박스에 onstop 될 때 저장되었던 값(체크 or 공란)을 불러와준다. 디폴트 값은 false = 공란이다.
        if(checksaveidbox == true){
            write_id.setText(saveid);
            saveid_box.setChecked(checksaveidbox);
            Log.v("아이디저장 체크박스 불러오기 확인 알림","String saveid : " + saveid + " checksaveidbox : " +  checksaveidbox);
        }

        // SharedPreferences autologinFile;
        autologinFile = getSharedPreferences("autologinFile",Activity.MODE_PRIVATE);

        // 자동로그인 체크박스 참조
        autologin_box = (CheckBox) findViewById(R.id.autologinbox);
        // Boolean checkautologinbox // 값이 없으면 체크 안되어있음
        checkautologinbox = autologinFile.getBoolean("autologinChecked",false);

        if(checkautologinbox == true){
            autologin_box.setChecked(checkautologinbox);
            Log.v("자동로그인 체크박스 불러오기 확인 알림"," checkautologinbox " +  checkautologinbox);
        }

        // SharedPreferences deleteIDpref
        deleteIDpref = getSharedPreferences("deleteidPreffile", MODE_PRIVATE);
        // SharedPreferences.Editor deletePrefEditor
        deletePrefEditor = deleteIDpref.edit();

        // 로그인 버튼을 누르면 로그인 화면으로 감
        btn_login = (Button) findViewById(R.id.loginbutton);
        btn_login.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

//                        // 앱 권한에서 저장소 권한 허용 안했을 때
//                        if(permissionCheck != true){
//                            Toast.makeText(LoginActivity.this, "앱 권한에서 저장소 권한을 허용해주세요!", Toast.LENGTH_SHORT).show();
//
//                        // 앱 권한에서 허용해줬을 때
//                        }else{
                            // 아이디와 비밀번호 일치해 로그인 성공했을 때 자동 로그인이 체크되어 있는 상황이라면 해당 정보를 저장한다.
                            if (userinfo.contains(write_id.getText().toString())) {

                                // 일치하는 아이디가 있다면 해당 아이디(KEY)로 해당 유저에 대한 정보를 가져올 수 있다.
                                // String thisUserinfo라는 문자열에 UserInfo라는 회원가입 시 저장했던 쉐어드 객체의 해당 아이디(key)와 value 값을 불러와준다.
                                // 로그인할 때 존재하는 아이디라면 thisuserinfo에 회원정보를 받아와서 패스워드와 닉네임을 split할 준비를 한다.
                                thisuserinfo = userinfo.getString(write_id.getText().toString(),"");

                                // 유저 비밀번호와 닉네임을 string 값에 담아준다.
                                String userpw = thisuserinfo.split("!@#")[3];
                                // String usernickname
                                usernickname = thisuserinfo.split("!@#")[1];

                                // 비밀번호 일치하면 로그인 성공
                                if(userpw.equals(write_pw.getText().toString())){
                                    Log.v("login할 때 비밀번호 확인 알림 로그"," write_pw.getText().toString() : " + write_pw.getText().toString() + " userp " + userpw);

                                    // 자동로그인 체크되어 있을 때
                                    // 자동 로그인이 체크되어 있고, 로그인에 성공했으면 자동로그인 정보 저장
                                    if (autologin_box.isChecked()) {

                                        // SharedPreferences.Editor autologinEditor
                                        autologinEditor = autologinFile.edit();

                                        // 로그인
                                        autologinEditor.putString("autologinID", write_id.getText().toString());
                                        autologinEditor.putString("autologinPW", write_pw.getText().toString());
                                        // 자동로그인 체크된 걸 저장시켜준다.
                                        autologinEditor.putBoolean("autologinChecked", true);

                                        autologinEditor.commit();

                                        Log.v("login할 때 자동로그인 확인 알림 로그"," write_id.getText().toString() : " + write_id.getText().toString() + " write_pw.getText().toString() " + write_pw.getText().toString());


                                    } else {

                                        // 자동 로그인 체크가 해제되면 자동로그인  삭제
                                        autologinFile = getSharedPreferences("autologinFile",Activity.MODE_PRIVATE);
                                        autologinEditor = autologinFile.edit();
                                        autologinEditor.clear(); // 모든 정보 해제
                                        autologinEditor.commit();

                                    }

                                    // 해당 id를 담고 있는 걸 벨류값으로 주는 쉐어드 하나 더 만들어준다.
                                    SharedPreferences userid = getSharedPreferences("useridFile", MODE_PRIVATE);
                                    SharedPreferences.Editor userIDeditor = userid.edit();
                                    userIDeditor.putString("userid", write_id.getText().toString()); // userid 라는 key값으로 로그인 후 가지고 놀 진짜 ID 데이터를 저장한다.
                                    userIDeditor.commit(); //완료한다.

                                    Log.v("login할 때 아이디를 키값으로하는 쉐어드 파일 확인 알림 로그"," write_id.getText().toString() : " +  write_id.getText().toString());

//                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                                    startActivity(intent);
//                                    finish();

                                    // 로그인 중.. 프로그레스 다이어로그 바 뜨도록.
                                    CheckTypesTask task = new CheckTypesTask();
                                    task.execute();

                                    // 비밀번호가 일치하지 않습니다.
                                }else {
                                    Toast.makeText(LoginActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();

                                }

                                // 아이디와 비밀번호 둘 다 null값일 때
                            } else if (deleteIDpref.contains(write_id.getText().toString())) {
                                // 회원탈퇴한 유저이름 모음집들
                                Toast.makeText(LoginActivity.this, "탈퇴한 회원입니다.", Toast.LENGTH_SHORT).show();


                                // 회원탈퇴한
                            } else if(write_id.getText().toString().equals("") || write_pw.getText().toString().equals("")){
                                Toast.makeText(LoginActivity.this, "로그인 정보를 다시 확인해주세요.", Toast.LENGTH_SHORT).show();

                                // 이도저도 아닐 때
                            } else {
                                Toast.makeText(LoginActivity.this, "일치하는 아이디가 없습니다.", Toast.LENGTH_SHORT).show();
                            }
//                        }

                    }
                }
        );

        // 회원가입 버튼을 누르면 회원가입 화면으로 감
        btn_join = (Button) findViewById(R.id.joinbutton);
        btn_join.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                        startActivity(intent);
                    }
                }
        );

    }

    // AsyncTask 를 상속받아 만든 클래스는 3가지 함수를 구현
    private class CheckTypesTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog asyncDialog = new ProgressDialog(
                LoginActivity.this);

        // onPreExecute() : 작업시작, ProgressDialog 객체를 생성하고 시작
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("로그인 중입니다..");

            // show dialog
            asyncDialog.show();

        }

        //  doInBackground() : 진행중, ProgressDialog 의 진행 정도를 표현
        // 백그라운드 스레드에서 작업 종료 후, 결과를 메인 스레드에서 통보해 줄 수 있고(onPostExecute)
        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                for (int i = 0; i < 4; i++) {
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
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

            Toast.makeText(LoginActivity.this, usernickname + "님!! 환영합니다. ", Toast.LENGTH_SHORT).show();

        }
    }

    private void checkPermission() {
        //현재 안드로이드 버전이 6.0미만이면 메서드를 종료한다.
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return;

        for(String permission : permission_list){
            //권한 허용 여부를 확인한다.
            int chk = checkCallingOrSelfPermission(permission);

            if(chk == PackageManager.PERMISSION_DENIED){
                //권한 허용을여부를 확인하는 창을 띄운다
                requestPermissions(permission_list,0);
            }
        }
    }

    boolean permissionChecking(){

        return permissionCheck == true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==0)
        {
            for(int i=0; i<grantResults.length; i++)
            {
                //허용됬다면
                if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                    permissionChecking();
                }
                else {
                    Toast.makeText(getApplicationContext(),"앱 권한을 설정해주세요!", Toast.LENGTH_LONG).show();
//                    finish();
                }

            }
        }

    }

    // onpause될 때 아이디저장하는 체크박스가 체크되어 있는지 확인한다.
    protected void onPause() {
        super.onPause();

        Log.v("onPause 호출 확인 로그","Menu onPause 호출");

        // 내가 아이디에 입력하는 입력값이 shared의 key값으로 준 아이디가 있는지 일치여부 확인 후 있을 시 - 로그인되었을 때만 아이디저장 되도록.
        // 오른쪽과 같이 넣지 말기 saveid_box.isChecked() && getid.equals(write_id.getText().toString()) && getpassword.equals(write_pw.getText().toString())
        if(userinfo.contains(write_id.getText().toString())){

            thisuserinfo = userinfo.getString(write_id.getText().toString(),"");

                    // onPause
                    // 아이디저장 체크박스 작업
                    // SharedPreferences saveidFile
                    saveidFile = getSharedPreferences("saveidFile",0);
                    // SharedPreferences.Editor saveidEditor
                    saveidEditor = saveidFile.edit();

                    // write_id란 edittext에 입력받은 값을 onstop할 때 저장한다, checkSaveId라는 체크박스에 체크되어 있는지 여부를 저장한다.
                    saveidEditor.putString("id_save", write_id.getText().toString());
                    saveidEditor.putBoolean("id_savecheckbox", saveid_box.isChecked()); // 'boolean android.widget.CheckBox.isChecked()' on a null object reference

                    Log.v("login 아이디저장 체크박스 확인 알림 로그"," 저장된 아이디 : " + write_id.getText().toString() + " 아이디저장 체크 여부 " + saveid_box.isChecked());

                    saveidEditor.commit();

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finishAffinity();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v("onStart 호출 확인 로그","Menu onStart 호출");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("onResume 호출 확인 로그","Menu onResume 호출");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v("onStop 호출 확인 로그","Menu onStop 호출");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("onDestroy 호출 확인 로그","Menu onDestroy 호출");

    }
}



//
//            if(thisuserinfo.split("!@#")[3] != null){
//                    String userpw = thisuserinfo.split("!@#")[3];
//                    Log.v("login 아이디저장 userpw 알림 로그"," userpw : " + userpw);
//
//                    // 비밀번호 일치하면 로그인 성공
//                    if(userpw.equals(write_pw.getText().toString())){


