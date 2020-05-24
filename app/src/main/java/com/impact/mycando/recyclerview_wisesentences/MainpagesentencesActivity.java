package com.impact.mycando.recyclerview_wisesentences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;

public class MainpagesentencesActivity extends AppCompatActivity {

    // 뒤로가기 버튼
    ImageButton btn_back, btn_help;

    private ArrayList<DataWisesentences> mdatawiseArrayList;
    private AdapterWisesentences mwiseAdapter;
    private RecyclerView mRecyclerView;

    // 회원가입한 정보 shared 객체를 아이디, 비밀번호 입력값과 비교하기 위해 가지고 와야함
    SharedPreferences userinfo;

    // 로그인했을 때 같이 넘겨준 아이디 파일
    SharedPreferences userid;
    // 아이디파일에 담겨져 있는 string
    String useridString;

    // 문장들 저장해둔 파일
    SharedPreferences UsermainPreffile;
    SharedPreferences.Editor UsermainPrefEditor;

    // 사용자가 입력한 내용을 가져와주는 string
    String strmainSentence;

    // "&&&" + strmainSentence // 내가 입력한 내용을 &&&와 저장시켜주기 위해서 만든 변수
    String main_add;

    // usermainpreffile이란 shared 파일에 있는 값을 get_maincontents로 getstring 해준 값.
    String get_maincontents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpagesentences);

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

        // 추가 버튼을 눌렀을 때 저장되어서 화면에 있는 리사이클러뷰에 불러와져야함.
        // 그러기 위해 shared 파일을 하나 더 만들어줌
        // SharedPreferences UsermainPref
        UsermainPreffile = getSharedPreferences("UsermainPrefFile", 0);

        // editor라는게 필요해서 만들어줬다.
        // SharedPreferences.Editor UsermainPrefEditor
        UsermainPrefEditor = UsermainPreffile.edit();

        // 뒤로가기 버튼을 누르면 로그인 화면으로 감
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


        // 물음표 누르면 명언 추천하는 곳으로 이동
        btn_help = (ImageButton) findViewById(R.id.help_button);
        btn_help.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent(getApplicationContext(), Helpwisesentence.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );

        // RecyclerView mRecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_wise);
        Log.v("배경화면 문구 추가 화면 알림","recyclerview 생성 확인 알림 로그");

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        Log.v("배경화면 문구 추가 화면 알림","recyclerview layoutmanager 껴주기 확인 알림 로그");


        mdatawiseArrayList = new ArrayList<>();
        Log.v("배경화면 문구 추가화면 알림","arraylist 생성 확인 알림 로그");

        //mAdapter = new CustomAdapter( mArrayList);
        mwiseAdapter = new AdapterWisesentences(this, mdatawiseArrayList);
        Log.v("배경화면 문구 추가화면 알림","adapter 생성 확인 알림 로그, 이 어댑터에 데이터 연결시켜줌 로그");

        mRecyclerView.setAdapter(mwiseAdapter);
        Log.v("배경화면 문구 추가화면 알림","recyclerview에 데이터를 담은 어댑터를 끼워줌 로그");

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        Log.v("배경화면 문구 추가화면 알림","recyclerview layoutmanager support - 구분선 주기 확인 알림 로그");

        // https://5stralia.tistory.com/13

        // 화면 우측 하단에 있는 더하기 버튼 클릭하면
        FloatingActionButton fab = findViewById(R.id.add_wisesentence);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.v("배경화면 문구 다이어로그 추가 알림","데이터 추가 클릭 확인 알림 로그");
                // 2. 레이아웃 파일 edit_box.xml을 불러와서 화면에 다이얼로그를 보여준다.
                // 다이어로그를 이 액티비티 위에 만들어준다.
                AlertDialog.Builder builder = new AlertDialog.Builder(MainpagesentencesActivity.this);

                // 레이아웃인플레이터는 xml을 만들고 어댑터의 코드를 짤 때 필요함 - xml에 정의된 자원들을 view의 형태로 반환시켜줌
                // 뷰 객체 생성
                View view1 = LayoutInflater.from(MainpagesentencesActivity.this)
                        .inflate(R.layout.activity_addsentences_dialog, null, false);

                // 위에서 inflater가 만든 view1객체를 세팅
                builder.setView(view1);
                Log.v("배경화면 문구 다이어로그 추가 알림","view1 확인 알림 로그"); //  + view1 이거 찍어보면 레이아웃 나옴 androidx.constraintlayout.widget.ConstraintLayout{883af8a V.E...... ......I. 0,0-0,0}
                builder.setTitle("'할 수 있다' 문구 추가");
                Log.v("배경화면 문구 다이어로그 추가 알림","화면에 다이얼로그 띄어줌 알림 로그");

                ///Dialog의 listener에서 사용하기 위해 final로 참조변수 선언
                final Button ButtonSubmit = (Button) view1.findViewById(R.id.button_dialog_submit);
                final Button ButtonCancel = (Button) view1.findViewById(R.id.button_dialog_cancel);
                final EditText editTextSentence = (EditText) view1.findViewById(R.id.edittext_dialog_sentence);

                ButtonSubmit.setText("추가");
                final AlertDialog dialog = builder.create();
                Log.v("배경화면 문구 다이어로그 추가 알림","dialog 생성 확인 알림 로그");

                // 3. 다이얼로그에 있는 추가 버튼을 클릭하면
                ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Log.v("배경화면 문구 다이어로그 추가 알림 로그","추가버튼 클릭 확인 알림 로그");

                        // 4. 사용자가 입력한 내용을 가져와서
                        // String strmainSentence
                        strmainSentence =  editTextSentence.getText().toString();
                        Log.v("배경화면 문구 다이어로그 추가 알림 로그", "edittext null 값 아님 확인 로그 알림" + editTextSentence.getText().toString());

                        // 받아온 값이 널값이라면 명언을 입력해주세요.
                        if(editTextSentence.getText().toString().equals("")){

                            Toast.makeText(getApplicationContext(), "문구를 입력해주세요.", Toast.LENGTH_SHORT).show();
                            // 널값이 아니라면 추가됨

                        } else {

                            // 로그인했을 대의 key값을 회원정보가 포함하고 있다면
                            if(userinfo.contains(useridString)){

                                // 쉐어드에 값이 있는지 없는지 체크하기 위해 먼저 getstring 해줌
                                String checkKeepEmpty = UsermainPreffile.getString(useridString, "");

                                // 값이 없으면 입력값인 strkeepSentence 그대로 저장
                                if(checkKeepEmpty.equals("")){

                                    UsermainPrefEditor.putString(useridString, strmainSentence);
                                    Log.v("배경화면 문구 다이어로그 추가 알림 로그","strkeepSentence "  +  strmainSentence);

                                    UsermainPrefEditor.commit();

                                    // 초기 값이 있으면 초기 값에 내가 추가해주는 값들 더해줘야 한다.
                                } else {

                                    // main_firstcontents에 첫 게시물을 담아준다.
                                    String main_firstcontents =  UsermainPreffile.getString(useridString, "");

                                    StringBuffer sb_maincontents = new StringBuffer();

                                    // main_add 라는 string 값에 기본 콘텐츠를 비교해줘야할 구분자인 &&&와 추가로 입력해주는 값을 담아줌.
                                    // String main_add
                                    main_add = "&&&" + strmainSentence;

                                    // 그걸 sb_maincontents에 계속 추가시켜준다.
                                    sb_maincontents.append(main_add);
                                    Log.v("배경화면 문구 다이어로그 추가 알림 로그","main_add"  +  main_add);

                                    // 기존 value인  keep_maincontents 에 추가되는 값들 sb_maincontents.toString()을 더해준다.
                                    UsermainPrefEditor.putString(useridString, main_firstcontents + sb_maincontents.toString());
                                    Log.v("명심할 것 다이어로그 추가 알림 로그","main_firstcontents"  +  main_firstcontents + "sb_maincontents.toString()" + sb_maincontents.toString());

                                    // 파일에 최종 반영함
                                    UsermainPrefEditor.commit();

                                }

                            }


                            DataWisesentences dataWisesentences = new DataWisesentences(strmainSentence);
                            Log.v("배경화면 문구 다이어로그 추가 알림","dataWisesentences : 이건 객체"  +  "strSentence  : " + strmainSentence);
                            // 위의 입력한 걸 그대로 출력해주는데 그 출력되는 값들, 즉 명언들이 arraylist에 담기게 된다

                            // 입력된 값이 추가됨
                            // mdatawiseArrayList.add(0, dataWisesentences); //첫 줄에 삽입
                            mdatawiseArrayList.add(dataWisesentences); //마지막 줄에 삽입
                            Log.v("배경화면 문구 다이어로그 추가 알림","arraylist 추가 확인 알림 로그");

                            // 6. 어댑터에서 RecyclerView에 반영하도록 합니다.
                            mwiseAdapter.notifyDataSetChanged(); //변경된 데이터를 화면에 반영
                            Log.v("배경화면 문구 다이어로그 추가 알림","adapter에서 recyclerview 반영 확인 알림 로그");

                            dialog.dismiss();

                            Toast.makeText(MainpagesentencesActivity.this, "'할 수 있다' 문구가 추가되었습니다.", Toast.LENGTH_SHORT).show();


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
                Log.v("배경화면 문구 다이어로그 알림","dialog.show(); 확인 알림 로그");

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v("onStart 호출 확인 로그","mainpageadapter onStart 호출");

        // 오른쪽으로 밀어서 리사이클러뷰 삭제 - 한 줄씩 분석해보기
        // itemtouchhelper 클래스는 swipe해서 사라지게 하거나 drag & drop을 지원한다.
        // RecyclerView 및 Callback 클래스와 함께 작동
        // RecyclerView는 ItemDecoration을 통해서 item view를 꾸며줄 수 있다.
        // ItemTouchHelper는 ItemDecoration을 상속받은 클래스로, item view의 swipe 및 drag&drop 기능을 추가한 utility class이다.
        ItemTouchHelper.SimpleCallback SwipesimpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) { //dragdirs > drag direction

            // user가 아이템을 drag할 때 onmove 호출
            // 리사이클러뷰와 뷰홀더 타겟을 매개변수로 받는다.
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            // user가 아이템을 swipe할 때 onswiped 호출
            // 뷰홀더와 direction을 매개변수로 받는다.
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

//                Toast.makeText(getApplicationContext(), "viewHolder.getAdapterPosition() : " + viewHolder.getAdapterPosition(), Toast.LENGTH_SHORT).show();

                // 삭제할 때 &&&로 잘라준 값들을 아래 배열에 담아준다.
                String[] splitmainReceiveinfo = get_maincontents.split("&&&");
                Log.v("명심할 것 splitmainReceiveinfo 확인 알림 로그","splitmainReceiveinfo : " + splitmainReceiveinfo);

                // 그 콘텐츠들이 잘 출력되는지 확인
                for(int i = 0; i < splitmainReceiveinfo.length; i++){

                    String checksplit = splitmainReceiveinfo[i];
                    Log.v("명심할 것 다이어로그 checksplit 확인 알림 로그","checksplit "  +  checksplit);
                    Log.v("명심할 것 다이어로그 splitmainReceiveinfo.length 확인 알림 로그","splitmainReceiveinfo.length "  +  splitmainReceiveinfo.length);

                }

                // 유저정보가 해당 아이디를 포함하고 있다면
                if(userinfo.contains(useridString)){

                    // 맨 처음에 저장된 값은 맨 밑에 있다. 이 아이템의 포지션은 arraylist의 길이에서 1을 빼준 값과 같다.
                    // 고로 맨 처음에 저장된 값, 맨 밑에 있는 값을 삭제하려면
                    if(viewHolder.getAdapterPosition() == 0){

//                        Toast.makeText(getApplicationContext(), "position 값 다시 확인 : " + viewHolder.getAdapterPosition(), Toast.LENGTH_SHORT).show();

                        // 남아있는 값이 한 개인지 여러 개인지 판단은 어떻게? >
                        // 남아있는 값이 한 개일 때 삭제할 경우 >> &&& 이건 빼고 그냥 값을 "" 공백으로 처리
                        if(mdatawiseArrayList.size() == 1){

                            // 삭제하려는 값을 삭제는 못하고 대신 &&& + 해당 인덱스에 있는 값을 공백으로 바꿔준다.
                            String deletemaincontent = get_maincontents.replace(splitmainReceiveinfo[viewHolder.getAdapterPosition()],"");
                            Log.v("main adapter deletemaincontent 확인 알림 로그", "" + deletemaincontent);

//                            Toast.makeText(getApplicationContext(), "아이템 한 개일 때 첫번째 값 삭제" + splitmainReceiveinfo[viewHolder.getAdapterPosition()], Toast.LENGTH_SHORT).show();

                            // shared에 공백으로 바꾸고 난 다음의 콘텐츠를 넣어준다.
                            UsermainPrefEditor.putString(useridString, deletemaincontent);

                            // 파일에 최종 반영함
                            UsermainPrefEditor.commit();

                            // 6. ArraylistT에서 해당 데이터를 삭제
                            Log.v("명심할 것 adapter 확인 알림 로그","ArraylistT에서 해당 데이터를 삭제");
                            // 해당 포지션에 있는 값을 제거한다.
                            mdatawiseArrayList.remove(viewHolder.getAdapterPosition());
                            Log.v("명심할 것 adapter getAdapterPosition() 확인 알림 로그","getAdapterPosition()" + viewHolder.getAdapterPosition());

                            // 7. 어댑터에서 recyclerview에 반영하도록 한다.
                            mwiseAdapter.notifyItemRemoved(viewHolder.getAdapterPosition()); // 특정 Position에 데이터를 하나 제거할 경우.
                            mwiseAdapter.notifyItemRangeChanged(viewHolder.getAdapterPosition(), mdatawiseArrayList.size()); // 특정 영역을 데이터가 바뀌었을 경우. position 3~10번까지의 데이터만 바뀌었을 경우 사용 하면 된다.

                            Log.v("명심할 것 adapter 확인 알림 로그","어댑터에서 recyclerview에 반영하도록 한다.");
                            Toast.makeText(getApplicationContext(), "'할 수 있다' 문구가 삭제되었습니다.", Toast.LENGTH_SHORT).show();

                            mwiseAdapter.notifyDataSetChanged();


                            // 남아있는 값이 여러 개일때 삭제할 경우 >> &&&까지 target으로 정해서 함께 "" 공백 처리
                        } else {

                            // 삭제하려는 값을 삭제는 못하고 대신 &&& + 해당 인덱스에 있는 값을 공백으로 바꿔준다. 다만, 현재는 첫번째값을 삭제하면 안된다.
                            String deletemaincontent = get_maincontents.replace(splitmainReceiveinfo[viewHolder.getAdapterPosition()] + "&&&","");
                            Log.v("main adapter deletemaincontent 확인 알림 로그", "" + deletemaincontent);

                            Toast.makeText(getApplicationContext(), "'할 수 있다' 문구가 삭제되었습니다.", Toast.LENGTH_SHORT).show();

                            // shared에 공백으로 바꾸고 난 다음의 콘텐츠를 넣어준다.

                            UsermainPrefEditor.putString(useridString, deletemaincontent);

                            // 파일에 최종 반영함
                            UsermainPrefEditor.commit();

                            // 6. ArraylistT에서 해당 데이터를 삭제
                            Log.v("명심할 것 adapter 확인 알림 로그","ArraylistT에서 해당 데이터를 삭제");
                            // 해당 포지션에 있는 값을 제거한다.
                            mdatawiseArrayList.remove(viewHolder.getAdapterPosition());
                            Log.v("명심할 것 adapter getAdapterPosition() 확인 알림 로그","getAdapterPosition()" + viewHolder.getAdapterPosition());

                            // 7. 어댑터에서 recyclerview에 반영하도록 한다.
                            mwiseAdapter.notifyItemRemoved(viewHolder.getAdapterPosition()); // 특정 Position에 데이터를 하나 제거할 경우.
                            mwiseAdapter.notifyItemRangeChanged(viewHolder.getAdapterPosition(), mdatawiseArrayList.size()); // 특정 영역을 데이터가 바뀌었을 경우. position 3~10번까지의 데이터만 바뀌었을 경우 사용 하면 된다.

                            Log.v("명심할 것 adapter 확인 알림 로그","어댑터에서 recyclerview에 반영하도록 한다.");

                            mwiseAdapter.notifyDataSetChanged();

                        }

                        // 그 외에 포지션에 위치한 값을 삭제하려면.
                    } else {

                        // 삭제하려는 값을 삭제는 못하고 대신 &&& + 해당 인덱스에 있는 값을 공백으로 바꿔준다. 다만, 현재는 첫번째값을 삭제하면 안된다.
                        String deletemaincontent = get_maincontents.replace("&&&" + splitmainReceiveinfo[viewHolder.getAdapterPosition()],"");
                        Log.v("main adapter deletemaincontent 확인 알림 로그", "" + deletemaincontent);

//                        Toast.makeText(getApplicationContext(), " 첫번째 아이템 제외한 포지션에 위치한 아이템 삭제 "+ "&&&" + splitmainReceiveinfo[viewHolder.getAdapterPosition()], Toast.LENGTH_SHORT).show();

                        // shared에 공백으로 바꾸고 난 다음의 콘텐츠를 넣어준다.
                        UsermainPrefEditor.putString(useridString, deletemaincontent);

                        // 파일에 최종 반영함
                        UsermainPrefEditor.commit();

                        // 6. ArraylistT에서 해당 데이터를 삭제
                        Log.v("명심할 것 adapter 확인 알림 로그","ArraylistT에서 해당 데이터를 삭제");
                        // 해당 포지션에 있는 값을 제거한다.
                        mdatawiseArrayList.remove(viewHolder.getAdapterPosition());
                        Log.v("명심할 것 adapter getAdapterPosition() 확인 알림 로그","getAdapterPosition()" + viewHolder.getAdapterPosition());

                        // 7. 어댑터에서 recyclerview에 반영하도록 한다.
                        mwiseAdapter.notifyItemRemoved(viewHolder.getAdapterPosition()); // 특정 Position에 데이터를 하나 제거할 경우.
                        mwiseAdapter.notifyItemRangeChanged(viewHolder.getAdapterPosition(), mdatawiseArrayList.size()); // 특정 영역을 데이터가 바뀌었을 경우. position 3~10번까지의 데이터만 바뀌었을 경우 사용 하면 된다.

                        Log.v("명심할 것 adapter 확인 알림 로그","어댑터에서 recyclerview에 반영하도록 한다.");
                        Toast.makeText(getApplicationContext(), "'할 수 있다' 문구가 삭제되었습니다.", Toast.LENGTH_SHORT).show();

                        mwiseAdapter.notifyDataSetChanged();

                    }

                }

//                // swipe되면 해당 포지션에있는 값 삭제
//                // 삭제되는 아이템의 포지션을 가져온다.
//                mdatawiseArrayList.remove(viewHolder.getLayoutPosition());
//                // 갱신 - 어댑터에게 알린다.
//                mwiseAdapter.notifyItemRemoved(viewHolder.getLayoutPosition());

            }
        };

        // itemtouchhelper 객체를 생성해주고 이 때 swipesimplecallback을 매개변수로 넣어준다.
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(SwipesimpleCallback);
        // 그리고 이 객체에 attach 메쏘드를 통해 리사이클러뷰를 붙여준다.
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        // 회원정보가 useridString 값을 가지고 있으면
        if(userinfo.contains(useridString)){

            // 그 아이디를 key값으로 한
            // String get_maincontents
            get_maincontents = UsermainPreffile.getString(useridString, "");
            Log.v("명심할 것 다이어로그 추가 알림 로그","get_maincontents "  +  get_maincontents);

            // 이 조건문 안 걸어주면 공백인 아이템이 출력된다.
            if(get_maincontents.equals("") == false){

                // splitmainsentences 배열에 get_maincontents를 &&&로 잘라준다.
                String[] splitmainsentences = get_maincontents.split("&&&");

                // 그 잘라준 값들을 배열의 길이만큼 반복해주고 그때마다 어레이리스트에 담긴다.
                for(int i = 0; i< splitmainsentences.length; i ++){

                    // get_maincontent에 콘텐츠 개수만큼 받아주고
                    String get_maincontent = splitmainsentences[i];
                    Log.v("명심할 것 다이어로그 추가 알림 로그","get_maincontent "  +  get_maincontent);

                    // ArrayList에 추가하고
                    // 데이터 클래스 객체 생성 - 생성자는 입력값
                    // get_maincontent에 입력받은 값이 string 타입으로 들어가게 되는데 이게 데이터 클래스인 datawisesentences로 넘어간다.
                    // 데이터 클래스 생성자엔 입력한 값을 return해 결과를 출력해준다.
                    DataWisesentences datawisesentences = new DataWisesentences(get_maincontent);

                    // 입력된 값이 추가됨
                    mdatawiseArrayList.add(datawisesentences); //첫 줄에 삽입
                    // mdatakeepArrayList.add(dataKeepsentences); //마지막 줄에 삽입

                    // 어댑터에서 RecyclerView에 반영하도록 합니다.
                    mwiseAdapter.notifyDataSetChanged(); //변경된 데이터를 화면에 반영

                }

            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("onResume 호출 확인 로그","mainpageadapter onResume 호출");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v("onStop 호출 확인 로그","mainpageadapter onStop 호출");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("onDestroy 호출 확인 로그","mainpageadapter onDestroy 호출");

    }
}