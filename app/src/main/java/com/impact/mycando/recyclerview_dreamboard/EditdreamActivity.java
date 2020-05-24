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

public class EditdreamActivity extends AppCompatActivity {

    // 수정화면에서 입력값을 받고 있는 꿈 이름 edittext
    EditText edit_dreamname;

    // 수정화면에서 입력값을 받고 있는 꿈설명 edittext
    EditText edit_dreamexplain;

    // 수정화면으로 받아온 position 값
    int edit_position;

    // 수정화면 이미지버튼에 담겨있을 이미지 참조
    ImageButton editbtn_dreamimage;
    // 수정화면으로 받아온 imageuri 값
    Uri edit_dreamimageUri;

    // 수정 완료버튼
    Button editcomplete_button;

    // 갤러리 불러오기 위한 리퀘스트 코드
    private static final int GET_PICTURE_URI2 = 1;


    // 회원가입한 정보 shared 객체를 아이디, 비밀번호 입력값과 비교하기 위해 가지고 와야함
    SharedPreferences userinfo;

    // 로그인했을 때 같이 넘겨준 아이디 파일
    SharedPreferences userid;
    // 아이디파일에 담겨져 있는 string
    String useridString;

    // 드림보드 꿈 - 메모 - 이미지 저장해줄 파일
    SharedPreferences UserdreamPreffile;
    SharedPreferences.Editor UserdreamPrefEditor;

    String get_dreamcontents;

    // 드림보드 파일에 있는 값을 getstring해줌
    String receive_dreamcontentsInfo;

    // 수정한 값으로 replace해서 editdreamcontentsinfo로 값을 담아줌
    String editdreamContentsInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editdream);


        // 뒤로가기 버튼 누르면 메인액티비티로 이동
        ImageButton btn_back = (ImageButton) findViewById(R.id.back_icon);
        btn_back.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        Intent intent = new Intent(getApplicationContext(), DreamboxActivity.class);
                        startActivity(intent);
                        finish();

                    }
                }
        );

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

        // String receive_dreamcontentsInfo
        receive_dreamcontentsInfo = UserdreamPreffile.getString(useridString, "");

        // 꿈이름 edittext 참조해줌
        edit_dreamname = (EditText) findViewById(R.id.edit_dream);
        // edittext로 "editdreamname" 키가 가지고 있는 값을 박아줌
        edit_dreamname.setText(getIntent().getStringExtra("EditDreamname"));

        // 꿈설명 edittext 참조해줌
        edit_dreamexplain = (EditText) findViewById(R.id.edit_explain);
        // edittext로 "editdreamname" 키가 가지고 있는 값을 박아줌
        edit_dreamexplain.setText(getIntent().getStringExtra("EditDreamexplain"));

        // edit_position 값으로 position값 받아줌.
        edit_position = getIntent().getIntExtra("EditDream_position", 0);

        // 꿈 설명 이미지뷰 참조해줌
        editbtn_dreamimage = (ImageButton) findViewById(R.id.dream_editimage);
        editbtn_dreamimage.setImageURI(Uri.parse(getIntent().getStringExtra("EditDreamimage")));
        Log.v("꿈 수정하는 화면 image uri 확인로그 알림","Uri.parse(getIntent().getStringExtra(\"EditDreamimage\")) : " + Uri.parse(getIntent().getStringExtra("EditDreamimage")) + "getIntent().getStringExtra(\"EditDreamimage\")) " + getIntent().getStringExtra("EditDreamimage"));

        // 갤러리 열어서 이미지 등록하는 단계
        editbtn_dreamimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "갤러리 열기", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

                startActivityForResult(intent, GET_PICTURE_URI2);
            }
        });

        // 꿈 수정 화면의 수정 버튼
        editcomplete_button = (Button) findViewById(R.id.dreamedit_button);
        editcomplete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edit_dreamname.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "꿈을 입력해주세요", Toast.LENGTH_SHORT).show();

                } else if (edit_dreamexplain.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "꿈과 관련된 메모를 입력해주세요", Toast.LENGTH_SHORT).show();

                } else{

                    Intent intent = new Intent();

                    // "editdreamname2"라는 키로 꿈 이름을 수정한 값을 드림보드 화면으로 보내준다
                    intent.putExtra("editdreamname2", edit_dreamname.getText().toString());
                    Log.v("드림보드 리사이클러뷰 화면 수정 확인 알림 로그", "edit_dreamname.getText().toString(): " + edit_dreamname.getText().toString());

                    // "editdreamexplain2"라는 키로 꿈 메모을 수정한 값을 드림보드 화면으로 보내준다
                    intent.putExtra("editdreamexplain2", edit_dreamexplain.getText().toString());
                    Log.v("드림보드 리사이클러뷰 화면 수정 확인 알림 로그", " edit_dreamexplain.getText().toString()) : " + edit_dreamexplain.getText().toString());

//                intent.putExtra("editdreameimageuri2", edit_dreamimageUri.toString());
// intent.putExtra("editdreameimageuri2", edit_dreamimageUri.toString()); 빼고 아래 문단 넣어주기

                    // 이미지를 바꿔줬다면 바뀐 값을 보내주고 - (즉, 수정화면에서 갤러리를 호출해서 불러온 uri가 null값이 아니라면)
                    if(edit_dreamimageUri != null){
                        // "editdreameimageuri2"라는 키로 이미지 uri를 드림보드 화면으로 보내준다.
                        intent.putExtra("editdreameimageuri2", edit_dreamimageUri.toString());
                    }else{
                        // 이미지를 바꾸지 않았다면 기존에 있던 값을 보내줘야 한다.
                        intent.putExtra("editdreameimageuri2", getIntent().getStringExtra("EditDreamimage"));
                    }

                    // "editposition2"라는 키로 위치값을 보내준다.
                    intent.putExtra("editposition2", edit_position);
                    Log.v("드림보드 리사이클러뷰 화면 수정 확인 알림 로그", " edit_position : " + edit_position);

                    setResult(RESULT_OK, intent);

                    String[] splitReceivedreaminfo = receive_dreamcontentsInfo.split("&&&&&&");

                    // 회원정보가 해당 아이디를 가지고 있다면,
                    if(userinfo.contains(useridString)) {

                        // 이미지 수정하지 않을 가능성이 커서 editimaguri에 null 값을 할당
                        // edit_dreamimageUri - 새로 받아온 값인데, 이게 없다는 건 이미지 수정하지 않았다는 것.
                        if(edit_dreamimageUri == null) {
                            // 삭제하려는 값을 삭제는 못하고 대신 & + 해당 인덱스에 있는 값을 공백으로 바꿔준다
                            // String editdreamContentsInfo;
                            editdreamContentsInfo = receive_dreamcontentsInfo.replace(splitReceivedreaminfo[splitReceivedreaminfo.length - 1 - edit_position],
                                    edit_dreamname.getText().toString() + "@@@"
                                            + edit_dreamexplain.getText().toString() + "@@@"
                                            + getIntent().getStringExtra("EditDreamimage")); // EditImageUri.toString() 대신에 기존에 있던 이미지 string값

                            // 이미지 수정했을 경우.
                        }else{

                            // 삭제하려는 값을 삭제는 못하고 대신 & + 해당 인덱스에 있는 값을 공백으로 바꿔준다
                            // String editdreamContentsInfo;
                            editdreamContentsInfo = receive_dreamcontentsInfo.replace(splitReceivedreaminfo[splitReceivedreaminfo.length - 1 - edit_position],
                                    edit_dreamname.getText().toString() + "@@@"
                                            + edit_dreamexplain.getText().toString() + "@@@"
                                            + edit_dreamimageUri.toString()); // getIntent().getStringExtra("EditDreamimage") 대신에 기존에 있던 이미지 string값
                        }

                        // 해당 아이디를 키값으로 위의 자료를 담아준다.
                        UserdreamPrefEditor.putString(useridString, editdreamContentsInfo);

                        // 파일에 최종 반영함
                        UserdreamPrefEditor.commit();

                    }

                    finish();

                }

            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        Toast.makeText(getBaseContext(), "resultCode : " + resultCode, Toast.LENGTH_SHORT).show();

        if (requestCode == GET_PICTURE_URI2 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            edit_dreamimageUri = data.getData();
            editbtn_dreamimage.setImageURI(edit_dreamimageUri);

        }
    }
}
