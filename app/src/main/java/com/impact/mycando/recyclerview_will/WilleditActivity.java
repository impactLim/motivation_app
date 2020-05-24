package com.impact.mycando.recyclerview_will;

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
import android.widget.TextView;
import android.widget.Toast;

import com.impact.mycando.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WilleditActivity extends AppCompatActivity {

    // 입력받고 있어야할 오늘의 각오
    EditText et_willcontents;
    // 입력받고 있어야할 목표
    EditText et_willgoal;
    // 입력받고 있어야할 이미지
    ImageView et_willimage;
    // 사진 수정 버튼
    Button et_willimagebtn;
    // 사진 수정하기 버튼 눌렀을 때 호출하는 request 코드
    private static final int GET_PICTURE_URI2 = 1;
    Uri edit_willimageUri;

    // position 값도 보내줘야함
    // 어댑터에서 수정화면으로 받아온 position 값
    int edit_position;

    // 수정 완료 버튼
    Button et_willcompletebtn;


    // 회원가입한 정보 shared 객체를 아이디, 비밀번호 입력값과 비교하기 위해 가지고 와야함
    SharedPreferences userinfo;

    // 로그인했을 때 같이 넘겨준 아이디 파일
    SharedPreferences userid;
    // 아이디파일에 담겨져 있는 string
    String useridString;

    // 드림보드 꿈 - 메모 - 이미지 저장해줄 파일
    SharedPreferences UserWillPreffile;
    SharedPreferences.Editor UserWillPrefEditor;

    String receive_willcontentsInfo;

    String editwillContentsInfo;



    // 날짜 저장해둔 shared 파일
    SharedPreferences setdateFile;

    // 목표 저장해둔 shared 파일
    SharedPreferences setgoalFile;

    // 목표설정한 화면에서 날짜와 목표를 받아와줌
    String rc_date;
    String rc_goal;
    // rc_date와 rc_goal을 tv_dategoal에 박아줌
    TextView tv_dategoal;
    String DateGoal;


    // 현재시간을 msec 으로 구한다.
    private long now = System.currentTimeMillis();

    // 현재시간을 date 변수에 저장한다.
    private Date date = new Date(now);

    // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
//    private SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd");
    private SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd E요일 HH:mm");
    // 시연을 위해 ss까지 표현

    // dateNow 변수에 값을 저장한다.
    private String dateStr = sdfNow.format(date);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_willedit);

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
        // SharedPreferences  UserWillPreffile
        UserWillPreffile = getSharedPreferences("UserwillPrefFile", 0);

        // editor라는게 필요해서 만들어줬다.
        // SharedPreferences.Editor UserdreamPrefEditor
        UserWillPrefEditor = UserWillPreffile.edit();

        // String receive_willcontentsInfo
        receive_willcontentsInfo = UserWillPreffile.getString(useridString, "");



        // 아이디별로 날짜를 다르게 설정할 수 있도록
        // 로그인한 아이디를 키로 주고 그 때마다 date 다르게 해준다.
        // 날짜 저장해줄 shared 파일
        // SharedPreferences setdateFile;
        setdateFile = getSharedPreferences("setdateFile", MODE_PRIVATE);

        // 날짜 저장해줄 shared 파일
        // SharedPreferences setgoalFile;
        setgoalFile = getSharedPreferences("setgoalFile", MODE_PRIVATE);

        // date랑 goal 값을 목표설정화면에서 받아오기
        // String rc_date, rc_goal
        rc_date = setdateFile.getString(useridString,"");
        rc_goal = setgoalFile.getString(useridString,"");

        // 날짜와 목표가 null값이 아니라면
        if(setdateFile.getString(useridString,"") != null && setgoalFile.getString(useridString,"") != null){

            // rc_date, rc_goal이 null 값 ("") 이 아니라면
            if(rc_date.equals("") == false && rc_goal.equals("") == false ){
                // 목표 참조
                tv_dategoal = findViewById(R.id.now_goal);
                // String DateGoal
                DateGoal = rc_date + "까지 " + rc_goal + "한다!!";
                // 목표에 내가 받아온 날짜와 목표를 set해줌
                tv_dategoal.setText(DateGoal);
                Log.v("오늘의 각오 목표 확인 알림 로그 ","" + rc_date + "까지 " + rc_goal + "한다!!");
            }

        }

        // 뒤로가기 버튼 누르면 메인액티비티로 이동
        ImageButton btn_back = (ImageButton) findViewById(R.id.back_icon);
        btn_back.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        Intent intent = new Intent(getApplicationContext(), WillActivity.class);
                        startActivity(intent);
                        finish();

                    }
                }
        );

        // 오늘의 각오 edittext 참조해줌
        et_willcontents = (EditText) findViewById(R.id.edit_willcontents);
        // edittext로 "editdreamname" 키가 가지고 있는 값을 박아줌
        et_willcontents.setText(getIntent().getStringExtra("Edit_willcontents"));

        // 목표 edittext 참조해줌
        et_willgoal = (EditText) findViewById(R.id.edit_willgoal);
        et_willgoal.setText(getIntent().getStringExtra("Edit_willgoal"));

        // 오늘의 각오 이미지 참조해줌
        et_willimage = (ImageView) findViewById(R.id.ediltwill_image);
        if(getIntent().getStringExtra("Edit_willimage") != null){
            et_willimage.setImageURI(Uri.parse(getIntent().getStringExtra("Edit_willimage")));
        }

        // position 값도 받아오자
        edit_position = getIntent().getIntExtra("Edit_willposition", 0);

        // 오늘의 각오 사진 수정하기 버튼
        et_willimagebtn = (Button) findViewById(R.id.editwillimage_button);
        // 갤러리 열어서 이미지 등록하는 단계
        et_willimagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "갤러리 열기", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

                startActivityForResult(intent, GET_PICTURE_URI2);
            }
        });

        et_willcompletebtn = (Button) findViewById(R.id.willeditcomplete_button);
        et_willcompletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();

                if(et_willcontents.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "오늘의 다짐을 입력해주세요!", Toast.LENGTH_SHORT).show();

                } else if (et_willgoal.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "집중목표를 입력해주세요!", Toast.LENGTH_SHORT).show();

                } else{

                    // "Edit_willcontents2"라는 키로 오늘의 각오을 수정한 값을 오늘의 각오 화면으로 보내준다
                    intent.putExtra("Edit_willcontents2", et_willcontents.getText().toString());
                    Log.v("오늘의 각오 리사이클러뷰 화면 수정 확인 알림 로그", "edit_dreamname.getText().toString(): " + et_willcontents.getText().toString());

                    // "Edit_willgoal2"라는 키로 꿈 메모을 수정한 값을 드림보드 화면으로 보내준다
                    intent.putExtra("Edit_willgoal2", et_willgoal.getText().toString());
                    Log.v("오늘의 각오 리사이클러뷰 화면 수정 확인 알림 로그", " edit_dreamexplain.getText().toString()) : " +  et_willgoal.getText().toString());

                    // 이미지를 바꿔줬다면 바뀐 값을 보내주고 - (즉, 수정화면에서 갤러리를 호출해서 불러온 uri가 null값이 아니라면)
                    if(edit_willimageUri != null){
                        intent.putExtra("Edit_willimage2",  edit_willimageUri.toString());
                        Log.v("오늘의 각오 리사이클러뷰 화면 수정 확인 알림 로그", " 바뀐 값 edit_willimageUri.toString() : " +  edit_willimageUri.toString());

                    }else{
                        // 이미지를 바꾸지 않았다면 기존에 있던 값을 보내줘야 한다.
                        intent.putExtra("Edit_willimage2", getIntent().getStringExtra("Edit_willimage"));
                        Log.v("오늘의 각오 리사이클러뷰 화면 수정 확인 알림 로그", " 기존에 있던 값 edit_willimageUri.toString() : " +  getIntent().getStringExtra("Edit_willimage"));
                    }

                    // "editposition2"라는 키로 위치값을 보내준다.
                    intent.putExtra("Edit_willposition", edit_position);
                    Log.v("오늘의 각오 리사이클러뷰 화면 수정 확인 알림 로그", " edit_position : " + edit_position);

                    setResult(RESULT_OK, intent);


                    // shared 파일이 가지고 있는 값들을 receive_willcontentsinfo로 가져와서 &&&&&&로 잘라준 값이 splitReceivewillinfo다.
                    String[] splitReceivewillinfo = receive_willcontentsInfo.split("&&&&&&");

                    // 회원정보가 해당 아이디를 가지고 있다면,
                    if(userinfo.contains(useridString)) {

                        // 이미지 수정하지 않을 가능성이 커서 editimaguri에 null 값을 할당
                        // edit_dreamimageUri - 새로 받아온 값인데, 이게 없다는 건 이미지 수정하지 않았다는 것.
                        if(edit_willimageUri == null) {
                            // 삭제하려는 값을 삭제는 못하고 대신 해당 인덱스에 있는 값을 공백으로 바꿔준다
                            // String editwillContentsInfo;
                            editwillContentsInfo = receive_willcontentsInfo.replace(splitReceivewillinfo[splitReceivewillinfo.length - 1 - edit_position],
                                    et_willgoal.getText().toString() + "@@@"
                                            + et_willcontents.getText().toString() + "@@@"
                                            + getIntent().getStringExtra("Edit_willimage") + "@@@"
                                            + dateStr); // EditImageUri.toString() 대신에 기존에 있던 이미지 string값

                            // 이미지 수정했을 경우.
                        }else{

                            // 삭제하려는 값을 삭제는 못하고 대신 해당 인덱스에 있는 값을 공백으로 바꿔준다
                            // String editwillContentsInfo;
                            editwillContentsInfo = receive_willcontentsInfo.replace(splitReceivewillinfo[splitReceivewillinfo.length - 1 - edit_position],
                                    et_willgoal.getText().toString() + "@@@"
                                            + et_willcontents.getText().toString() + "@@@"
                                            + edit_willimageUri.toString() + "@@@"
                                            + dateStr); // getIntent().getStringExtra("EditDreamimage") 대신에 기존에 있던 이미지 string값
                        }

                        // 해당 아이디를 키값으로 위의 자료를 담아준다.
                        UserWillPrefEditor.putString(useridString, editwillContentsInfo);

                        // 파일에 최종 반영함
                        UserWillPrefEditor.commit();

                    }

                    finish();

                }

            }
        });

    }

    // 갤러리에서 불러온 사진 가져다 붙인다.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        Toast.makeText(getBaseContext(), "resultCode : " + resultCode, Toast.LENGTH_SHORT).show();

        if (requestCode == GET_PICTURE_URI2 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            edit_willimageUri = data.getData();
            et_willimage.setImageURI(edit_willimageUri);

        }
    }
}
