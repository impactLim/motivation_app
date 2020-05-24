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

public class WillwriteActivity extends AppCompatActivity {

    // 오늘의 각오
    EditText et_willcontents;
    // 목표
    EditText et_willgoal;

    // 완료버튼
    Button upload_contents;

    // 사진등록하기 버튼
    Button upload_photo;
    // 호출한 request 번호
    private static final int GET_PICTURE_URI = 0;
    // 받아올 uri 값
    Uri willImageUri;


    // 회원가입한 정보 shared 객체를 아이디, 비밀번호 입력값과 비교하기 위해 가지고 와야함
    SharedPreferences userinfo;

    // 로그인했을 때 같이 넘겨준 아이디 파일
    SharedPreferences userid;
    // 아이디파일에 담겨져 있는 string
    String useridString;

    // 드림보드 꿈 - 메모 - 이미지 저장해줄 파일
    SharedPreferences UserWillPreffile;
    SharedPreferences.Editor UserWillPrefEditor;

    String write_willcontents;


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
        setContentView(R.layout.activity_willwrite);


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

        et_willcontents = findViewById(R.id.write_willcontents);
        et_willgoal = (EditText) findViewById(R.id.write_willgoal);

        //글쓰기 화면의 완료 버튼
        upload_contents = findViewById(R.id.willaddcomplete_button);
        upload_contents.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // 오늘의 각오가 공백이라면
                if(et_willcontents.getText().toString().equals("")) {

                    Toast.makeText(getApplicationContext(), "오늘의 다짐을 입력해주세요!", Toast.LENGTH_SHORT).show();

                    // 이미지 등록하지 않았을 시
                }else if(willImageUri == null) {
                    Toast.makeText(getApplicationContext(), "오늘의 다짐 이미지를 등록해주세요!", Toast.LENGTH_SHORT).show();

                } else if(et_willgoal.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "집중목표를 입력해주세요!", Toast.LENGTH_SHORT).show();

                    // 값이 있다면 추가됨
                }else{

                    Intent intent = new Intent();

                    intent.putExtra("willwrite_contents", et_willcontents.getText().toString());
                    Log.v("willwrite_contents 확인 알림 로그","값 확인 : " + et_willcontents.getText().toString());

                    intent.putExtra("willwrite_goal", et_willgoal.getText().toString());
                    Log.v("willwrite_goal 확인 알림 로그","값 확인 : " +  et_willgoal.getText().toString());

                    if(willImageUri != null){
                        intent.putExtra("willwrite_image", willImageUri.toString());
                        Log.v("willwrite_image 확인 알림 로그","값 확인 : " +  willImageUri.toString());
                    }

                    setResult(RESULT_OK, intent);

                    // 집중목표, 오늘의 각오, 이미지, 날짜 등이 담겨있는 파일에 값이 있는지 확인
                    String checkShareEmpty = UserWillPreffile.getString(useridString, "");

                    if(userinfo.contains(useridString)){

                        // 파일에 값이 없다면
                        if(checkShareEmpty.equals("")){

                            // 꿈, 꿈과 관련된 메모, 이미지를 string 에 다 넣어줌
                            // String write_willcontents;
                            // 처음 들어가는 값에 대한 구조를 만들어서
                            write_willcontents = et_willgoal.getText().toString() + "@@@"
                                    + et_willcontents.getText().toString() + "@@@"
                                    + willImageUri.toString() + "@@@"
                                    + dateStr;

                            // 저장해주고
                            UserWillPrefEditor.putString(useridString, write_willcontents);

                            // commit
                            UserWillPrefEditor.commit();


                            // 파일에 값이 있다면
                        } else {

                            // 첫번째 값을 받아와주고
                            String first_willcontents = UserWillPreffile.getString(useridString, "");

                            // StringBuffer 자료형과 append 라는 메소드를 이용하여 계속해서 문자열을 추가.
                            StringBuffer sb_willcontentsinfo = new StringBuffer();

                            // String write_willcontents;
                            // write_willcontents에 &&&&&& 구분자를 포함해 나중에 split할 수 있도록.
                            write_willcontents = "&&&&&&" + et_willgoal.getText().toString() + "@@@"
                                    + et_willcontents.getText().toString() + "@@@"
                                    + willImageUri.toString() + "@@@"
                                    + dateStr;

                            // 위의 구조를 append시켜줌
                            sb_willcontentsinfo.append(write_willcontents);

                            // append 할수록 first_willcontents에 값이 계속 쌓이며 새로 추가된 값은 sb_willcontentsinfo.tostring에 담기게 됨.
                            UserWillPrefEditor.putString(useridString, first_willcontents + sb_willcontentsinfo.toString());

                            // commit
                            UserWillPrefEditor.commit();

                        }

                    }

                    finish(); // 액티비티를 끝낸다.

                }

            }
        });

        upload_photo = findViewById(R.id.addwillimage_button);
        upload_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

        if (requestCode == GET_PICTURE_URI && resultCode == RESULT_OK && data != null && data.getData() != null) {

            ImageView will_imageview = (ImageView) findViewById(R.id.will_image);
            willImageUri = data.getData();
            will_imageview.setImageURI(willImageUri);
            Log.v("willImageUri 확인 알림 로그","값 확인 : " +  willImageUri);

        }
    }
}
