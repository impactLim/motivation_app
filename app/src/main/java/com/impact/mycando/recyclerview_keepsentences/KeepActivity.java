package com.impact.mycando.recyclerview_keepsentences;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.impact.mycando.MainActivity;
import com.impact.mycando.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class KeepActivity extends AppCompatActivity {

    // 뒤로 가기
    ImageButton btn_back;

    private ArrayList<DataKeepsentences> mdatakeepArrayList;
    private AdapterKeepsentences mkeepAdapter;

    // 회원가입한 정보 shared 객체를 아이디, 비밀번호 입력값과 비교하기 위해 가지고 와야함
    SharedPreferences userinfo;

    // 로그인했을 때 같이 넘겨준 아이디 파일
    SharedPreferences userid;
    // 아이디파일에 담겨져 있는 string
    String useridString;

    // 문장들 저장해줄 파일
    SharedPreferences UserkeepPreffile;
    SharedPreferences.Editor UserkeepPrefEditor;

    // "&&&&&&" + strkeepSentence
    String put_keepsentence;

    // 사용자가 입력한 값
    String strkeepSentence;

    // "&&&&&&" + strkeepSentence;
    String keep_add;

    // 내가 입력한 값들이 담겨있는 string값
    String get_keepcontents;

    // keep + ##! + date
    String KeepAndDate;

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
        setContentView(R.layout.activity_keep);

        // 로그인했을 때 같이 넘겨준 ID
        // SharedPreferences userid
        userid = getSharedPreferences("useridFile", MODE_PRIVATE);
        // 로그인할 때 가져온 id가 useridString이다.
        // String useridString
        useridString = userid.getString("userid", "");

        // 회원가입한 정보 shared 객체를 가져와 닉네임을 불러와준다.
        userinfo = getSharedPreferences("userinfoFile", MODE_PRIVATE);
        // 로그인한 아이디를 키로 하고 value에 회원정보들이 담겨 있는 thisuserinfo를 String으로 가져와준다
        String thisuserinfo = userinfo.getString(useridString,"");

        // 뒤로가기 버튼을 누르면 메인 화면으로 감
        btn_back = (ImageButton) findViewById(R.id.back_icon);
        btn_back.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_keep);
        Log.v("명심할 것 추가 화면 알림","recyclerview 생성 확인 알림 로그");

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        Log.v("명심할 것 추가 화면 알림","recyclerview layoutmanager 껴주기 확인 알림 로그");

        mdatakeepArrayList = new ArrayList<>();
        Log.v("명심할 것 추가 화면 알림","arraylist 생성 확인 알림 로그");

        //mAdapter = new CustomAdapter( mArrayList);
        mkeepAdapter = new AdapterKeepsentences(this, mdatakeepArrayList);
        Log.v("명심할 것 추가 화면 알림","adapter 생성 확인 알림 로그, 이 어댑터에 데이터 연결시켜줌 로그");

        mRecyclerView.setAdapter(mkeepAdapter);
        Log.v("명심할 것 추가 화면 알림","recyclerview에 데이터를 담은 어댑터를 끼워줌 로그");

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        Log.v("명심할 것 추가 화면 알림","recyclerview layoutmanager support - 구분선 주기 확인 알림 로그");

        // 추가 버튼을 눌렀을 때 저장되어서 화면에 있는 리사이클러뷰에 불러와져야함.
        // 그러기 위해 shared 파일을 하나 더 만들어줌
        // SharedPreferences UserkeepPref
        UserkeepPreffile = getSharedPreferences("UserkeepPrefFile", 0);

        // editor라는게 필요해서 만들어줬다.
        // SharedPreferences.Editor UserkeepPrefEditor
        UserkeepPrefEditor = UserkeepPreffile.edit();


        // 화면 우측 하단에 있는 더하기 버튼 클릭하면
        FloatingActionButton keep_fab = findViewById(R.id.add_keepsentence);
        keep_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("명심할 것 추가 화면 알림","데이터 추가 클릭 확인 알림 로그");
                // 2. 레이아웃 파일 edit_box.xml을 불러와서 화면에 다이얼로그를 보여준다.
                // 다이어로그를 이 액티비티 위에 만들어준다.
                AlertDialog.Builder builder = new AlertDialog.Builder(KeepActivity.this);

                // 레이아웃인플레이터는 xml을 만들고 어댑터의 코드를 짤 때 필요함 - xml에 정의된 자원들을 view의 형태로 반환시켜줌
                // 뷰 객체 생성
                View view1 = LayoutInflater.from(KeepActivity.this)
                        .inflate(R.layout.activity_addkeepsentences_dialog, null, false);

                // 위에서 inflater가 만든 view1객체를 세팅
                builder.setView(view1);
                Log.v("명심할 것 추가 화면 알림","view1 확인 알림 로그"); //  + view1 이거 찍어보면 레이아웃 나옴 androidx.constraintlayout.widget.ConstraintLayout{883af8a V.E...... ......I. 0,0-0,0}
                builder.setTitle("'명심할 것' 추가");
                Log.v("명심할 것 추가 화면 알림","화면에 다이얼로그 띄어줌 알림 로그");

                ///Dialog의 listener에서 사용하기 위해 final로 참조변수 선언
                final Button ButtonSubmit = (Button) view1.findViewById(R.id.button_dialog_submit);
                final Button ButtonCancel = (Button) view1.findViewById(R.id.button_dialog_cancel);
                final EditText editTextkeepSentence = (EditText) view1.findViewById(R.id.edittext_dialog_keepsentence);

                ButtonSubmit.setText("추가");
                final AlertDialog dialog = builder.create();
                Log.v("명심할 것 다이어로그 추가 알림","dialog 생성 확인 알림 로그");


                // 3. 다이얼로그에 있는 추가 버튼을 클릭하면
                ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        Log.v("명심할 것 다이어로그 추가 알림","추가버튼 클릭 확인 알림 로그");

                        // 4. 사용자가 입력한 내용을 가져와서
                        // String strkeepSentence
                        strkeepSentence = editTextkeepSentence.getText().toString();
                        Log.v("명심할 것 다이어로그 추가 알림", "edittext null 값 아님 확인 로그 알림" + editTextkeepSentence.getText().toString());
//                        String strEnglish = editTextEnglish.getText().toString();
//                        String strKorean = editTextKorean.getText().toString();

                        // 받아온 값이 널값이라면 명심할 것을 입력해주세요.
                        if(editTextkeepSentence.getText().toString().equals("")){

                            Toast.makeText(KeepActivity.this, "명심할 것을 입력해주세요.", Toast.LENGTH_SHORT).show();
                            // 널값이 아니라면 추가됨

                        } else {

                            // 로그인했을 대의 key값을 회원정보가 포함하고 있다면
                            if(userinfo.contains(useridString)){

                                // 쉐어드에 값이 있는지 없는지 체크하기 위해 먼저 getstring 해줌
                                String checkKeepEmpty = UserkeepPreffile.getString(useridString, "");

                                // 값이 없으면 입력값인 strkeepSentence 그대로 저장
                                if(checkKeepEmpty.equals("")){

                                    // String KeepAndDate;
                                    KeepAndDate = strkeepSentence + "##!"
                                            + dateStr;

                                    UserkeepPrefEditor.putString(useridString, KeepAndDate);
                                    Log.v("명심할 것 다이어로그 추가 알림 로그","strkeepSentence "  +  strkeepSentence);

                                    UserkeepPrefEditor.commit();

                                // 초기 값이 있으면 초기 값에 내가 추가해주는 값들 더해줘야 한다.
                                } else {

                                    // keep_firstcontents에 첫 게시물을 담아준다.
                                    String keep_firstcontents =  UserkeepPreffile.getString(useridString, "");

                                    StringBuffer sb_keepcontents = new StringBuffer();

                                    // keep_add 라는 string 값에 기본 콘텐츠를 비교해줘야할 구분자인 &&&와 추가로 입력해주는 값을 담아줌.
                                    // String keep_add
                                    keep_add = "&&&&&&" + strkeepSentence + "##!"
                                            + dateStr;

                                    // 그걸 sb_keepcontents에 계속 추가시켜준다.
                                    sb_keepcontents.append(keep_add);
                                    Log.v("명심할 것 다이어로그 추가 알림 로그","keep_add"  +  keep_add);

                                    // 기존 value인  keep_firstcontents 에 추가되는 값들 sb_keepcontents.toString()을 더해준다.
                                    UserkeepPrefEditor.putString(useridString, keep_firstcontents + sb_keepcontents.toString());
                                    Log.v("명심할 것 다이어로그 추가 알림 로그","keep_firstcontents"  +  keep_firstcontents + "sb_keepcontents.toString()" + sb_keepcontents.toString());

                                    // 파일에 최종 반영함
                                    UserkeepPrefEditor.commit();

                                }

                                // 5. ArrayList에 추가하고
                                // 데이터 클래스 객체 생성 - 생성자는 입력값
                                // strSentence에 입력받은 값이 string 타입으로 들어가게 되는데 이게 데이터 클래스인 datawisesentences로 넘어간다. // 데이터 클래스 생성자엔 입력한 값을 return해 결과를 출력해준다.
                                DataKeepsentences dataKeepsentences = new DataKeepsentences(strkeepSentence, dateStr);
                                Log.v("명심할 것 다이어로그 추가 알림 로그","dataKeepsentences : 이건 객체"  +  "strSentence  : " + strkeepSentence);
                                // 위의 입력한 걸 그대로 출력해주는데 그 출력되는 값들, 즉 명언들이 arraylist에 담기게 된다

                                // 입력된 값이 추가됨
                                mdatakeepArrayList.add(0, dataKeepsentences); //첫 줄에 삽입
                                // mdatakeepArrayList.add(dataKeepsentences); //마지막 줄에 삽입
                                Log.v("명심할 것 다이어로그 추가 알림","arraylist 추가 확인 알림 로그");

                                // 6. 어댑터에서 RecyclerView에 반영하도록 합니다.
                                mkeepAdapter.notifyDataSetChanged(); //변경된 데이터를 화면에 반영
                                Log.v("명심할 것 다이어로그 추가 알림","adapter에서 recyclerview 반영 확인 알림 로그");

                                dialog.dismiss();

                                Toast.makeText(KeepActivity.this, "명심할 것이 추가되었습니다.", Toast.LENGTH_SHORT).show();
                                Log.v("keep arraylist 데이터 확인 로그 알림","" + mdatakeepArrayList.size());

                            }

                        }
                    }
                });




                // 데이터 추가 눌렀을 때 나오는 취소, 삽입 옵션 중 취소를 눌렀을 때 다이어로그 사라지게 하는 코드
                ButtonCancel.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
                Log.v("명심할 것 다이어로그 추가 알림","dialog.show(); 확인 알림 로그");

            }
        });

        // 명심할 것들 전체삭제하는 버튼
        ImageButton btn_logout = (ImageButton) findViewById(R.id.trash_button);
        btn_logout.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        // 전체삭제하기
                        Go_alldelete();

                    }
                }

        );

    }

    private void Go_alldelete() {
        new AlertDialog.Builder(this)
                .setTitle("명심할 것들").setMessage("전체삭제 하시겠습니까?")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        // 현재 아이디가 작성한 명심할 것들을
                        if(userinfo.contains(useridString)){

                            // 삭제한다.
                            UserkeepPrefEditor.remove(useridString);
                            // 확인
                            UserkeepPrefEditor.commit();
                            // arraylist에 담겨있는 것도 삭제해줘야함
                            mdatakeepArrayList.clear();

                            // 변경된 데이터 반영
                            mkeepAdapter.notifyDataSetChanged();

                            dialog.dismiss();
                            Toast.makeText(KeepActivity.this, "전체삭제되었습니다", Toast.LENGTH_SHORT).show();

                        }

                    }

                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                })
                .show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v("onStart 호출 확인 로그","keepactivity onStart 호출");

        // 회원정보가 useridString 값을 가지고 있으면
        if(userinfo.contains(useridString)){

            // 그 아이디를 key값으로 한
            // String get_keepcontents
            get_keepcontents = UserkeepPreffile.getString(useridString, "");
            Log.v("명심할 것 다이어로그 추가 알림 로그","get_keepcontents "  +  get_keepcontents);

            // 이 조건문 안 걸어주면 공백인 아이템이 출력된다.
            if(get_keepcontents.equals("") == false){
                // splitkeepsentences 배열에 get_keepcontents를 &&&로 잘라준다.
                String[] splitkeepsentences = get_keepcontents.split("&&&&&&");

                // 그 잘라준 값들을 배열의 길이만큼 반복해주고 그때마다 어레이리스트에 담긴다.
                for(int i = 0; i< splitkeepsentences.length; i ++){

                    // get_keepcontent에 길이만큼 받아주고
                    String get_keepcontent = splitkeepsentences[i].split("##!")[0];
                    Log.v("명심할 것 다이어로그 추가 알림 로그","get_keepcontent "  +  get_keepcontent);

                    String get_datecontent = splitkeepsentences[i].split("##!")[1];

                    // 5. ArrayList에 추가하고
                    // 데이터 클래스 객체 생성 - 생성자는 입력값
                    // get_keepcontent에 입력받은 값이 string 타입으로 들어가게 되는데 이게 데이터 클래스인 dataKeepsentences로 넘어간다.
                    // 데이터 클래스 생성자엔 입력한 값을 return해 결과를 출력해준다.
                    DataKeepsentences dataKeepsentences = new DataKeepsentences(get_keepcontent, get_datecontent);

                    // 입력된 값이 추가됨
                    mdatakeepArrayList.add(0, dataKeepsentences); //첫 줄에 삽입
                    // mdatakeepArrayList.add(dataKeepsentences); //마지막 줄에 삽입

                    // 6. 어댑터에서 RecyclerView에 반영하도록 합니다.
                    mkeepAdapter.notifyDataSetChanged(); //변경된 데이터를 화면에 반영

                }

            }

        }

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