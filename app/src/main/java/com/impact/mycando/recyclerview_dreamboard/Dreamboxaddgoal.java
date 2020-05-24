package com.impact.mycando.recyclerview_dreamboard;

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
import android.widget.Toast;

import com.impact.mycando.R;

public class Dreamboxaddgoal extends AppCompatActivity {

    // 내가 입력한 꿈을 받을 edittext
    EditText et_writedream;
    // 꿈 설명 입력받을 edittext
    EditText et_writedreamexplain;
    // 완료버튼
    Button btn_dreamcomplete;

    ImageButton btn_gogallery;
    // 이미지 받아올 때 필요한 리퀘스트 코드
    private static final int GET_PICTURE_URI = 0;
    // Uri로 받아온 이미지 uri
    Uri selectedImageUri;


    // 회원가입한 정보 shared 객체를 아이디, 비밀번호 입력값과 비교하기 위해 가지고 와야함
    SharedPreferences userinfo;

    // 로그인했을 때 같이 넘겨준 아이디 파일
    SharedPreferences userid;
    // 아이디파일에 담겨져 있는 string
    String useridString;

    // 드림보드 꿈 - 메모 - 이미지 저장해줄 파일
    SharedPreferences UserdreamPreffile;
    SharedPreferences.Editor UserdreamPrefEditor;

    // et_writedream.getText().toString() + "@@@"
    //                                    + et_writedreamexplain.getText().toString() + "@@@"
    //                                    + selectedImageUri.toString();
    String write_dreamcontents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dreamboxaddgoal);

        // 로그인했을 때 같이 넘겨준 ID
        // SharedPreferences userid
        userid = getSharedPreferences("useridFile", MODE_PRIVATE);
        // 로그인할 때 가져온 id가 useridString이다.
        // String useridString
        useridString = userid.getString("userid", "");

        // 회원가입한 정보 shared 객체를 가져와 닉네임을 불러와준다.
        userinfo = getSharedPreferences("userinfoFile", MODE_PRIVATE);

        // 추가 버튼을 눌렀을 때 저장되어서 화면에 있는 리사이클러뷰에 불러와져야함.
        // 그러기 위해 shared 파일을 하나 더 만들어줌
        // SharedPreferences UserdreamPreffile
        UserdreamPreffile = getSharedPreferences("UserdreamPrefFile", 0);

        // editor라는게 필요해서 만들어줬다.
        // SharedPreferences.Editor UserdreamPrefEditor
        UserdreamPrefEditor = UserdreamPreffile.edit();

        // 꿈 입력받을 edittext 참조
        et_writedream = findViewById(R.id.write_dream);
        // 꿈 메모 입력받을 edittext 참조
        et_writedreamexplain = findViewById(R.id.write_dreamexplain);

        //글쓰기 화면의 완료 버튼 참조
        btn_dreamcomplete = findViewById(R.id.dreamcomplete_button);
        btn_dreamcomplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 꿈 입력받은게 null 값이라면 꿈을 입력해주세요 메시지
                if(et_writedream.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "꿈을 입력해주세요", Toast.LENGTH_SHORT).show();
                    Log.v("꿈추가 화면 확인 알림 로그","키 : dreamname 값 확인 : " + et_writedream.getText().toString());

                    // 이미지 등록하지 않았을 시
                }else if(selectedImageUri == null) {
                    Toast.makeText(getApplicationContext(), "이미지를 등록해주세요!", Toast.LENGTH_SHORT).show();

                } else if(et_writedreamexplain.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "꿈과 관련된 메모를 입력해주세요", Toast.LENGTH_SHORT).show();

                    // 값이 있다면 추가됨
                }else{

                    // 꿈이름, 메모, 이미지 등이 담겨있는 파일에 값이 있는지 확인
                    String checkShareEmpty = UserdreamPreffile.getString(useridString, "");

                    if(userinfo.contains(useridString)){

                        // 파일에 값이 없다면
                        if(checkShareEmpty.equals("")){

                            // 꿈, 꿈과 관련된 메모, 이미지를 string 에 다 넣어줌
                            // String write_dreamcontents;
                            // 처음 들어가는 값에 대한 구조를 만들어서
                            write_dreamcontents = et_writedream.getText().toString() + "@@@"
                                    + et_writedreamexplain.getText().toString() + "@@@"
                                    + selectedImageUri.toString();

                            // 저장해주고
                            UserdreamPrefEditor.putString(useridString, write_dreamcontents);

                            // commit
                            UserdreamPrefEditor.commit();


                        // 파일에 값이 있다면
                        } else {

                            // 첫번째 값을 받아와주고
                            String first_dreamcontents = UserdreamPreffile.getString(useridString, "");

                            // StringBuffer 자료형과 append 라는 메소드를 이용하여 계속해서 문자열을 추가.
                            StringBuffer sb_dreamcontentsinfo = new StringBuffer();

                            // String write_dreamcontents;
                            // write_dreamcontents에 &&&&&& 구분자를 포함해 나중에 split할 수 있도록.
                            write_dreamcontents = "&&&&&&" + et_writedream.getText().toString() + "@@@"
                                    + et_writedreamexplain.getText().toString() + "@@@"
                                    + selectedImageUri.toString();

                            // 위의 구조를 appen시켜줌
                            sb_dreamcontentsinfo.append(write_dreamcontents);

                            // append 할수록 first_dreamcontents에 값이 계속 쌓이며 새로 추가된 값은 sb_dreamcontentsinfo.tostring에 담기게 됨.
                            UserdreamPrefEditor.putString(useridString, first_dreamcontents + sb_dreamcontentsinfo.toString());

                            // commit
                            UserdreamPrefEditor.commit();

                        }


                    }


                    // intent 굳이 없애지 않아도 가능
                    Intent intent = new Intent();

                    intent.putExtra("dreamname", et_writedream.getText().toString());
                    Log.v("꿈추가 화면 확인 알림 로그","키 : dreamname 값 확인 : " + et_writedream.getText().toString());

                    intent.putExtra("dreamexplain", et_writedreamexplain.getText().toString());
                    Log.v("꿈추가 화면 확인 알림 로그","키 : dreamexplain 값 확인 : " + et_writedreamexplain.getText().toString());

                    // 받아온 이미지 uri를 string값으로 바꿔서 보내줌
                    if(selectedImageUri != null){
                        intent.putExtra("dreamuri", selectedImageUri.toString()); // 사진 올리지 않았을 때 null 값 예외처리
                        Log.v("꿈추가 화면 확인 알림 로그","값 확인 : selectedImageUri.toString()  " + selectedImageUri.toString()  + "  selectedImageUri  " + selectedImageUri);
                    }

                    setResult(RESULT_OK, intent);

                    finish(); // 액티비티를 끝낸다.
                    // 2. 호출된 Activity에서 setResult()로 결과 돌려주기
                    // 호출된 Activity에서 setResult() 메소드로 결과를 저장할 수 있습니다.
                    // 이 때 성공인 경우는 RESULT_OK로 실패라면 RESULT_CANCEL을 설정할 수 있습니다. (두 값 모두 Activity의 멤버 변수입니다.)
                    // 이후 finish() 메소드로 Activity를 종료합니다.

                }
            }
        });

        btn_gogallery = findViewById(R.id.dream_image);
        // 갤러리 열어서 이미지 등록하는 단계
        btn_gogallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "갤러리 열기", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

                startActivityForResult(intent, GET_PICTURE_URI);
            }
        });
    }


    // 갤러리에서 불러온 이미지 붙여넣는 단계
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 리퀘스트코드와 뤼절트 코드 동일할 때 그리고 데이터들이 null값이 아닐 떄
        if (requestCode == GET_PICTURE_URI && resultCode == RESULT_OK && data != null && data.getData() != null) {

            // uri로 데이터 받아오고
            selectedImageUri = data.getData();
            // 이미지버튼에 이미지 uri 박아줌
            btn_gogallery.setImageURI(selectedImageUri);
            Log.v("꿈추가하는 화면에서 추가된 사진의 Uri 가져옴 - 확인알림로그"," selectedImageUri : " + selectedImageUri);

//            // uri로 데이터 받아오고
//            selectedImageUri = data.getData();
//            Cursor cursor = getContentResolver().query(selectedImageUri, null, null, null, null);
//
//            if (cursor == null) { // Source is Dropbox or other similar local file path
//                result = selectedImageUri.getPath();
//                Log.v("이미지 절대경로 result 확인 알림 로그 1 ","result : " + result);
//
//            } else {
//
//                cursor.moveToFirst();
//                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
//                result = cursor.getString(idx);
//                cursor.close();
//                Log.v("이미지 절대경로 result 확인 알림 로그 2 ","result : " + result);
//
//            }
//
//            // 이미지버튼에 이미지 uri 박아줌
//            btn_gogallery.setImageURI(selectedImageUri);
//            Log.v("꿈추가하는 화면에서 추가된 사진의 Uri 가져옴 - 확인알림로그"," selectedImageUri : " + selectedImageUri);

        }
    }

}
