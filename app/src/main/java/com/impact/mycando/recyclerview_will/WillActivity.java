package com.impact.mycando.recyclerview_will;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.impact.mycando.MainActivity;
import com.impact.mycando.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WillActivity extends AppCompatActivity {



    RecyclerView willRecyclerview;
    AdapterWill willAdapter;
    ArrayList<DataWill> DataWillArrayList;
    LinearLayoutManager willLayoutManager;

    Button addwill_btn;

    // private static final을 선언한 변수를 사용하면 재할당하지 못하며, 메모리에 한 번 올라가면 같은 값을 클래스 내부의 전체 필드, 메서드에서 공유한다.
    private static final int REQ_ADD_WILL = 1;
    private static final int REQ_EDIT_WILL = 2;
    private static final int REQ_FIRST_WILL= 3;

    // 회원가입한 정보 shared 객체를 아이디, 비밀번호 입력값과 비교하기 위해 가지고 와야함
    SharedPreferences userinfo;

    // 로그인했을 때 같이 넘겨준 아이디 파일
    SharedPreferences userid;
    // 아이디파일에 담겨져 있는 string
    String useridString;

    // 드림보드 꿈 - 메모 - 이미지 저장해줄 파일
    SharedPreferences UserWillPreffile;
    SharedPreferences.Editor UserWillPrefEditor;

    String get_willcontents;
    String check_firstcontents;
    String check_getintent;

    // 목표 저장해줄 shared 파일
    SharedPreferences setgoalFile;
    // 목표 수정할 setgoal editor 파일
    SharedPreferences.Editor setgoalEditor;
    String goalcheck;

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
        setContentView(R.layout.activity_will);


        //        // 이미지뷰
        ImageView search = (ImageView) findViewById(R.id.search_imageview);
        Glide.with(this).load(R.drawable.search2).into(search);



        // 뒤로가기 버튼 누르면 메인액티비티로 이동
        ImageButton btn_back = (ImageButton) findViewById(R.id.back_icon);
        btn_back.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }
        );


        willRecyclerview = findViewById(R.id.recyclerview_will);

        willLayoutManager = new LinearLayoutManager(this);
        willRecyclerview.setLayoutManager(willLayoutManager);

        // 실제 데이터가 들어갈 리스트 생성
        DataWillArrayList = new ArrayList<>();
        // 데이터가 담긴 리스트를 넣어주며 어댑터 생성
        willAdapter = new AdapterWill(DataWillArrayList,this);

        willRecyclerview.setAdapter(willAdapter);

        // 날짜 저장해줄 shared 파일
        // SharedPreferences setgoalFile;
        setgoalFile = getSharedPreferences("setgoalFile", MODE_PRIVATE);
        // 날짜 수정할 setgoal editor 파일
        // SharedPreferences.Editor setgoalEditor;
        setgoalEditor = setgoalFile.edit();


        addwill_btn = (Button) findViewById(R.id.addwill_button);


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

        // String check_firstcontents
        check_firstcontents = UserWillPreffile.getString(useridString, "");

        // 나중에요 or 그럼요 팝업창에서 그럼요를 눌렀을 때만 값을 넘겨준다.
        check_getintent = getIntent().getStringExtra("check");

        // 그리고 그 값이 null 값이 아닐 때만 == 그럼요를 눌렀을 때만
        if(check_getintent != null){

            // 글쓰는 창으로 이동할 수 있도록 설정해둠.
            // 빈 값이 없으면 바로 write 화면으로 갈 수 있도록, 근데 팝업창에서 그럼요를 눌렀을 때만 이동해야함
            if(check_firstcontents.equals("") && check_getintent.equals("checkfirst")){

                Intent i = new Intent(getApplicationContext(), WillwriteActivity.class);
                startActivityForResult(i, REQ_ADD_WILL);

            }

        }




        // 회원정보가 useridString 값을 가지고 있으면
        if(userinfo.contains(useridString)){

            // 그 아이디를 key값으로 한
            // String get_willcontents
            get_willcontents = UserWillPreffile.getString(useridString, "");

            // 이 조건문 안 걸어주면 공백인 아이템이 출력된다.
            if(get_willcontents.equals("") == false){

                // splitkeepsentences 배열에 get_keepcontents를 &&&로 잘라준다.
                String[] splitwillcontents = get_willcontents.split("&&&&&&");

                // 그 잘라준 값들을 배열의 길이만큼 반복해주고 그때마다 어레이리스트에 담긴다.
                for(int i = 0; i< splitwillcontents.length; i ++){

                    // get_willcontent에 길이만큼 받아주고
                    // 목표
                    String get_willgoal = splitwillcontents[i].split("@@@")[0];

                    // 목표 콘텐츠
                    String get_willcontent = splitwillcontents[i].split("@@@")[1];

                    // 목표 이미지
                    String get_willimage = splitwillcontents[i].split("@@@")[2];

                    // 목표 날짜
                    String get_willidate = splitwillcontents[i].split("@@@")[3];

                    // dataWill 객체 생성할 때 박아주고
                    DataWill dataWill = new DataWill(get_willgoal, get_willcontent, get_willimage, get_willidate);

                    // 위에 하나씩 쌓아줌
                    DataWillArrayList.add(0, dataWill); //첫 줄에 삽입

                    willAdapter.notifyDataSetChanged(); //변경된 데이터를 화면에 반영 -- notifyitemchanged로 특정 position을 제대로 짚어주는 게 좋긴 함 - 이건 다음에 해보자.

                }

            }

        }



        // 검색할 값 받아오는 edittext 참조함
        EditText editText = findViewById(R.id.edittext);
        // addTextChangeListener는 EditText에 추가적인 글자 변화가 있는지 항상 듣고 있는 리스너
        editText.addTextChangedListener(new TextWatcher() {
            // TextWatcher는 인터페이스로써 3단계(글자변화 전, 중, 후)로 구성된 글자 변화의 시점의 메서드
            // 글자 변화되기 전 → beforeTextChanged
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 무언가 바뀐 시점 전에
            }

            // 글자 변화되는 중 → onTextChanged
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 무언가 바뀐 시점
                // charsequence s > 사용자가 새로 입력한 문자열을 포함한 에디트 텍스트의 문자열
                // int start - 새로 추가된 문자열의 시작 위치 값
                // int before - 새 문자열 대신 삭제된 기존 문자열의 수
                // int count 새로 추가된 문자열의 수

            }

            // 글자 변화된 후 → afterTextChanged
            // 거의 이 메쏘드만 사용됨
            // 편집가능한 문자열로 EditText 의 기본 Type
            @Override
            public void afterTextChanged(Editable s) {
                // 무언가 비뀐 이후
                // 필터 메쏘드
                filter(s.toString());
            }
        });

    }

    // https://www.youtube.com/watch?v=OWwOSLfWboY
    // edittext에 입력받은 값을 string으로 바꿔준 값을 생성자로 받는 filter 메쏘드
    private void filter(String text) {
        // 어레이리스트 객체 하나 생성해줌
        ArrayList<DataWill> filteredList = new ArrayList<>();

        // 배열에 이용되는 for 문.
        // for(변수 : 배열)
        // datawillarraylist에 들어있는 값들을 하나씩 item 변수에 대입시킨다.
        // 반복문이 돌 때마다 item에 들어 있는 값이 변하게 된다.
        // 예를 들어     int datawillarraylist = {1, 2, 3, 4, 5}; 라면
        // 처음에는 1, 다음에는 2 3 4 5 순서대로 하나씩 item에 반영된다.
        for (DataWill item : DataWillArrayList) {
            // 입력값이 오늘의 각오콘텐츠와 목표 중 일치하는 값이 있다면 datawillarraylist가 가지고 있는 item 길이만큼 filteredList에 item을 더해준다.
            if (item.getWillContentsStr().toLowerCase().contains(text.toLowerCase()) || item.getWillGoalStr().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
                Log.v("DataWillArrayList.size() 확인 알림 로그 ㅈㅂ", "DataWillArrayList.size() : "+ DataWillArrayList.size());
                Log.v("filteredList 확인 알림 로그 ㅈㅂ ", "filteredList.size() : "+ filteredList.size());

                // 작성된 글 전체 다 출력
                for(int i = 0 ; i < DataWillArrayList.size() ; i++){
                    Log.v(" DataWillArrayList 값 확인하기 확인 로그 알림 ㅈㅂ"," DataWillArrayList.get(i) " + "list[" + i + "] = " + DataWillArrayList.get(i).getWillGoalStr());
                    Log.v("  DataWillArrayList 값 확인하기 확인 로그 알림 ㅈㅂ"," DataWillArrayList.get(i) " + "list[" + i + "] = " + DataWillArrayList.get(i).getWillContentsStr());
                }

                // 입력값에 해당하는 콘텐츠와 목표를 가지고 있는 아이템만 출력
                for(int i = 0 ; i < filteredList.size() ; i++){
                    Log.v("filteredList 확인하기 확인 로그 알림 ㅈㅂ"," filteredList.get(i) " + "list[" + i + "] = " + filteredList.get(i).getWillGoalStr());
                    Log.v("filteredList 확인하기 확인 로그 알림 ㅈㅂ"," filteredList.get(i) " + "list[" + i + "] = " + filteredList.get(i).getWillContentsStr());
                }

            }
        }

        willAdapter.filterList(filteredList);
        // 변경된 데이터 화면에 반영
        willAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onStart() {
        super.onStart();

        // 각오 추가할 때
        addwill_btn.setOnClickListener(new Button.OnClickListener(){

            public void onClick(View v){

                if(userinfo.contains(useridString)){

                    // 목표값 있는지 체크
                    goalcheck = setgoalFile.getString(useridString, "");

                    if(goalcheck.equals("")){

                        Toast.makeText(getApplicationContext(), "집중목표 먼저 설정해주세요!!", Toast.LENGTH_SHORT).show();

                    }else{
                        // 오늘의 각오 추가하는 화면으로 고고
                        Intent i = new Intent(getApplicationContext(), WillwriteActivity.class);
                        startActivityForResult(i, REQ_ADD_WILL);
                        // 1. startActivityForResult()로 Activity 호출하기
                        // 기존에 startActivity()로 호출하던 것을 startActivityForResult()로 호출을 하면서 인수를 하나 추가해 줍니다.
                        // 이 인수는 0보다 크거나 같은 integer 값으로 추후 onActivityResult() 메소드에도 동일한 값이 전달되며
                        // 이를 통해 하나의 onActivityResult() 메소드에서 (만약 있다면) 여러 개의 startActivityForResult()를 구분할 수 있습니다.
                    }
                }

            }

        });
    }

    // 입력한 꿈에 대한 결과값을 리사이클러뷰에 하나씩 쌓아줌
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 요청코드가 1번 또는 3번이고 결과코드가 OK일 때
        if (requestCode == REQ_ADD_WILL ) { // || requestCode == REQ_FIRST_WILL
            if (resultCode == Activity.RESULT_OK) {

                // willwrite 오늘의 각오 추가하는 화면으로부터 "willwrite_contents" 키값에 담긴 오늘의 각오를 string 타입의 str_willcontents에 넣어줌
                String str_willcontents = data.getStringExtra("willwrite_contents");
                Log.v("메인 오늘의 각오 추가  확인 알림 로그", "str_willcontents : " + str_willcontents);

                String str_willgoal = data.getStringExtra("willwrite_goal");
                Log.v("메인 오늘의 각오 추가  확인 알림 로그", "willwrite_goal : " + str_willgoal);

                String str_willimage = data.getStringExtra("willwrite_image");
                Log.v("메인 오늘의 각오 추가  확인 알림 로그", "willwrite_imgae : " + str_willimage);

                // string 타입에 담긴 str_dreamname 값을 데이터클래스의 객체가 생성될 때 생성자로 넣어줌
                DataWill datawill = new DataWill(str_willgoal, str_willcontents, str_willimage, dateStr);
                Log.v("메인 오늘의 각오 데이터 클래스 객체 생성 확인 알림 로그", " 데이터클래스 객체 생성 ");

                DataWillArrayList.add(0, datawill); //첫 줄에 삽입
                Log.v("메인 오늘의 각오 데이터 클래스 객체를 arraylist에 넣어줌 확인 알림 로그", " 데이터클래스 객체를 arraylist에 넣어줌 ");

                willAdapter.notifyDataSetChanged(); //변경된 데이터를 화면에 반영 -- notifyitemchanged로 특정 position을 제대로 짚어주는 게 좋긴 함 - 이건 다음에 해보자.

            } else {

//                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();

            }
        }

        else{
            if (requestCode == REQ_EDIT_WILL && resultCode == Activity.RESULT_OK) {

                // edit 화면으로부터 수정된 오늘의 각오를 받아와줌
                String str_editwillContents = data.getStringExtra("Edit_willcontents2") ;
                Log.v("메인 오늘의 각오 수정  확인 알림 로그", "str_editwillContents : " + str_editwillContents);

                // edit 화면으로부터 수정된 목표값을 받아와줌
                String str_editwillGoal = data.getStringExtra("Edit_willgoal2") ;
                Log.v("메인 오늘의 각오 수정  확인 알림 로그", "str_editwillGoal : " + str_editwillGoal);

                // edit 화면으로부터 수정된 오늘의 각오 이미지를 받아와줌
                String str_editwillimage = data.getStringExtra("Edit_willimage2") ;
                Log.v("메인 오늘의 각오 수정  확인 알림 로그", " str_editwillimage : " + str_editwillimage );

                // edit 화면으로부터 "editposition2" 키에 담긴 position 값을 받아준다.
                int edit_position = data.getIntExtra("Edit_willposition", 0);
                Log.v("메인 오늘의 각오 수정  확인 알림 로그", " edit_position  : " + edit_position );

                DataWill editdataWill = new DataWill(str_editwillGoal, str_editwillContents, str_editwillimage, dateStr);

                // 수정할 때 해당 position 값을 넣어주고, ri를 입력해준다.
                DataWillArrayList.set(edit_position, editdataWill);
                Toast.makeText(getApplicationContext(), "수정되었습니다.", Toast.LENGTH_SHORT).show();

                // 어댑터에서 RecyclerView에 데이터를 반영하도록 합니다.
                willAdapter.notifyDataSetChanged();

            }else{

//                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();

            }

        }

    }
}
