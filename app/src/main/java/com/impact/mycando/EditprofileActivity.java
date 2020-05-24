package com.impact.mycando;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class EditprofileActivity extends AppCompatActivity {

    // 프로필 편집 화면의 닉네임 edit text 객체선언
    EditText et_nickname;

    // 뒤로가기 누르면 메뉴 화면 나옴
    ImageButton btn_back;

    // 완료 버튼 누르면 메뉴 화면으로 감
    Button btn_complete;

    // 갤러리 여는 startstartactivityforresult - request code
    private static final int GET_PICTURE_URI = 0;

    // 갤러리에서 Uri 값으로 가져온 이미지 경로
    Uri selectedImageUri;

    // 회원가입한 정보 shared 객체
    SharedPreferences userinfo;

    // 회원정보 shared 파일에서 불러와준 닉네임값
    String receive_name;
    String receive_nickname;
    String receive_email;
    String receive_password;
    String recieve_image;
    ImageView setgoal_imageview;

    // 회원가입 했을 때 함께 등록해준 userid
    SharedPreferences userid;
    String useridString;

    // 회원마다의 이미지를 저장해주는 파일
    SharedPreferences profileimageFile;

    // 회원마다의 이미지 저장, 수정할 때 필요한 에디터
    SharedPreferences.Editor profileimageEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        // 해당 id를 담고 있는 걸 벨류값으로 주는 쉐어드 하나 더 만들어준 걸 가져온다.
        userid = getSharedPreferences("useridFile", MODE_PRIVATE);
        // 로그인할 때 가져온 id가 String useridString이다.
        useridString = userid.getString("userid", "");

        // 회원가입한 정보 shared 객체를 가져온다.
        userinfo = getSharedPreferences("userinfoFile", MODE_PRIVATE);
        // 로그인한 아이디를 키로 하고 value에 회원정보들이 담겨 있는 thisuserinfo를 String으로 가져와준다
        String thisuserinfo = userinfo.getString(useridString,"");

        // 프로필 편집 화면의 닉네임 edittext 참조
        et_nickname = findViewById(R.id.rc_nickname);

        // 회원정보에 있는 닉네임값을 split을 이용해 receive_nickname값으로 받아와준다.
        receive_nickname = thisuserinfo.split("!@#")[1];
        et_nickname.setText(receive_nickname);

        // 프로필편집할 때 같이 편집한 닉네임을 기존의 이름과 이메일, 패스워드를 같이 넣어주기 위해
        // split으로 [0]이란 배열에 들어가 있는 이름 index 0번째의 값, 이름을 가져오겠다.
        // String receive_name
        receive_name = thisuserinfo.split("!@#")[0];

        // split으로 [2]이란 배열에 들어가 있는 index 2번째의 값, 이메일을 가져오겠다.
        // String receive_email
        receive_email = thisuserinfo.split("!@#")[2];

        // split으로 [3]이란 배열에 들어가 있는 index 2번째의 값을 가져오겠다.
        // String receive_password
        receive_password = thisuserinfo.split("!@#")[3];

        // SharedPreferences profileimageFile;
        profileimageFile = getSharedPreferences("userimageFile", MODE_PRIVATE);

        // 이미지가 담겨있는 string값을 불러와 recieve_image에 담아준다.
        recieve_image = profileimageFile.getString(useridString, "");
        Log.v("프로필 편집 화면 이미지 uri값 확인 로그 알림","" + recieve_image);

        // 이미지를 박아줄 이미지뷰 참조
        setgoal_imageview = (ImageView) findViewById(R.id.setgoal_image);

        // string에 담겨져있는 string 주소가 null 값이 아니라면
        if(recieve_image != null){
            // 이미지뷰에 이미지를 박아준다.
            setgoal_imageview.setImageURI(Uri.parse(recieve_image)); // setgoal_imageview 참조 안해줘서 이게 자꾸 null 값뜬거였음
            Log.v("프로필 편집 화면 null 값 체크 확인 로그", "setgoal_imageview" + setgoal_imageview + " recieve_image " + recieve_image);
        }


        // 뒤로가기 버튼을 누르면 메뉴 화면으로 감
        btn_back = (ImageButton) findViewById(R.id.back_icon);
        btn_back.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );

        // 갤러리 열어서 이미지 등록하는 단계
        findViewById(R.id.gallery_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EditprofileActivity.this, "갤러리 열기", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_PICTURE_URI);
            }
        });


        // 프로필편집화면에서 완료 버튼을 누르면 메뉴 화면으로 감
        btn_complete = (Button) findViewById(R.id.profileedit_completebutton);
        btn_complete.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        // 메뉴 화면으로 간다.
                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);

                        // 회원가입한 정보 shared 객체를 가져와서
                        SharedPreferences userinfo = getSharedPreferences("userinfoFile", MODE_PRIVATE);
                        // editor 호출
                        SharedPreferences.Editor userinfoEditor = userinfo.edit();

                        // 닉네임값에 입력받고 있는 text를 edit_nickname에 넣어줌
                        String edit_nickname = et_nickname.getText().toString();

                        // 새로 넣어줄 회원정보에 이름 + 닉네임 + 이메일 + 패스워드가 들어간다.
                        String userinfostring2 = receive_name + "!@#"
                                + edit_nickname + "!@#"
                                + receive_email + "!@#"
                                + receive_password + "!@#" ;

                        // 회원가입 시 저장했던 아이디를 그대로 key 값으로 가져왔고 기존 회원정보에서 닉네임 값만 변경시켜주고 다시 저장하겠다.
                        userinfoEditor.putString(receive_email, userinfostring2);
                        Log.v("프로필편집화면에서 수정된 닉네임 값 확인 로그 알림","" + edit_nickname);

                        // 최종확인
                        userinfoEditor.commit();

                        startActivity(intent);
                        finish();

                    }
                }
        );

    }

    // 갤러리에서 불러온 이미지 붙여넣는 단계
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GET_PICTURE_URI && resultCode == RESULT_OK && data != null && data.getData() != null) {

            selectedImageUri = data.getData();
            setgoal_imageview.setImageURI(selectedImageUri);
            Log.v("프로필편집화면 - 갤러리에서 불러온 이미지 uri 값 확인 로그",""+ selectedImageUri);

            // editor 호출
            // 회원마다의 이미지 저장, 수정할 때 필요한 에디터
            // SharedPreferences.Editor profileimageEditor
            profileimageEditor = profileimageFile.edit();

            // useridString 란 key값에 이미지를 담아준다
            profileimageEditor.putString(useridString, selectedImageUri.toString());
            Log.v("프로필편집 화면에서 사진 불러온 값 저장 확인 로그 알림", " selectedimageUri 값을 string값으로 돌린 값 확인 알림 : " + selectedImageUri.toString());

            // 최종확인
            profileimageEditor.commit();
            // 저장공간과 지속적인 동기를 통해 preference 를 작성하던 commit 과 달리
            // apply는 비동기적으로 반영이 된다는 것이다.

        }
    }
}
