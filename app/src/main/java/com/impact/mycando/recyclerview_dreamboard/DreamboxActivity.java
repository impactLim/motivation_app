package com.impact.mycando.recyclerview_dreamboard;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.impact.mycando.MainActivity;
import com.impact.mycando.R;

import java.util.ArrayList;

public class DreamboxActivity extends AppCompatActivity {

    // 리사이클러뷰 선언
    RecyclerView dreamboardRecyclerview;
    // 어댑터 선언
    AdapterDreamboard dreamboardAdapter; //  static
    // 꿈을 담을 어레이리스트 선언
    ArrayList<DataDreamboard> dreamboardArrayList; // static
    // 값이 1인 인수로 어떤 화면으로 갔다가 돌아와서 값을 출력하는지 알 수 있다.
    private static final int REQ_ADD_DREAM = 1;
    private static final int REQ_EDIT_DREAM = 2;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dreambox);

        // 리사이클러뷰 연결
        dreamboardRecyclerview = findViewById(R.id.recyclerview_dreambox);

        // 그리드레이아웃 2줄로 연결
        GridLayoutManager mGridlayoutmanager = new GridLayoutManager(this, 2);
        dreamboardRecyclerview.setLayoutManager(mGridlayoutmanager);

//        // 구분선 주기
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(dreamboardRecyclerview.getContext(),
//                mGridlayoutmanager.getOrientation());
//
//        dreamboardRecyclerview.addItemDecoration(dividerItemDecoration);

        // 꿈 담을 어레이리스트 객체 생성
        dreamboardArrayList = new ArrayList<>();

        // 어댑터 객체 생성할 때 생성자로 어레이리스트 객체 넣어줌
        dreamboardAdapter = new AdapterDreamboard(dreamboardArrayList, this);

        // 리사이클러뷰에 어댑터 껴줌
        dreamboardRecyclerview.setAdapter(dreamboardAdapter);

        // 꿈 추가하기 버튼 눌렀을 때
        Button adddream_btn = (Button) findViewById(R.id.adddream_button);
        adddream_btn.setOnClickListener(new Button.OnClickListener(){

            public void onClick(View v){

                // 꿈 추가하는 화면으로 고고
                Intent i = new Intent(getApplicationContext(), Dreamboxaddgoal.class);
                startActivityForResult(i, REQ_ADD_DREAM);
                // 1. startActivityForResult()로 Activity 호출하기
                // 기존에 startActivity()로 호출하던 것을 startActivityForResult()로 호출을 하면서 인수를 하나 추가해 줍니다.
                // 이 인수는 0보다 크거나 같은 integer 값으로 추후 onActivityResult() 메소드에도 동일한 값이 전달되며
                // 이를 통해 하나의 onActivityResult() 메소드에서 (만약 있다면) 여러 개의 startActivityForResult()를 구분할 수 있습니다.

            }

        });

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


        // 회원정보가 useridString 값을 가지고 있으면
        if(userinfo.contains(useridString)){

            // 그 아이디를 key값으로 한
            // String get_dreamcontents
            get_dreamcontents = UserdreamPreffile.getString(useridString, "");

            // 이 조건문 안 걸어주면 공백인 아이템이 출력된다.
            if(get_dreamcontents.equals("") == false){
                // splitkeepsentences 배열에 get_keepcontents를 &&&로 잘라준다.
                String[] splitdreamcontents = get_dreamcontents.split("&&&&&&");

                // 그 잘라준 값들을 배열의 길이만큼 반복해주고 그때마다 어레이리스트에 담긴다.
                for(int i = 0; i< splitdreamcontents.length; i ++){

                    // get_keepcontent에 길이만큼 받아주고
                    // 꿈 이름
                    String get_dreamname = splitdreamcontents[i].split("@@@")[0];

                    // 꿈 콘텐츠
                    String get_dreamcontent = splitdreamcontents[i].split("@@@")[1];

                    // 꿈 이미지
                    String get_dreamimage = splitdreamcontents[i].split("@@@")[2];

                    // string 타입에 담긴 str_dreamname 값을 데이터클래스의 객체가 생성될 때 생성자로 넣어줌
                    DataDreamboard dataDreamboard = new DataDreamboard(get_dreamname, get_dreamcontent,get_dreamimage);
                    // Log.v("드림보드 리사이클러뷰 화면 확인 알림 로그","dataDreamboard");

                    // 그럼 입력값이 데이터클래스에 갔다가 출력되어 어레이리스트에 첫 줄부터 쌓이게 됨
                    dreamboardArrayList.add(0, dataDreamboard); //첫 줄에 삽입
                    // dreamboardArrayList.add(dataDreamboard); // 마지막 줄에 삽입

                    // 어댑터에서 RecyclerView에 데이터를 반영하도록 합니다.
                    dreamboardAdapter.notifyDataSetChanged(); //변경된 데이터를 화면에 반영 -- notifyitemchanged로 특정 position을 제대로 짚어주는 게 좋긴 함 - 이건 다음에 해보자.

                }

            }

        }

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

    }

    // 입력한 꿈에 대한 결과값을 리사이클러뷰에 하나씩 쌓아줌
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 3. onActivityResult()에서 결과 확인하기
        // 안드로이드에서는 onActivityResult() 메소드를 통해 호출된 Activity에서 저장한 결과를 돌려줍니다.
        // 이 때 requestCode는 처음 startActivityForResult()의 두번째 인수 값이며,
        // resultCode는 호출된 Activity에서 설정한 성공(RESULT_OK)/실패(RESULT_CANCEL) 값입니다.
        // 이를 통해 어떤 호출 (REQUST_TEST) 이었는지와 결과가 어떠한지 (RESULT_OK)를 알 수 있으므로 그에 맞는 동작을 진행할 수 있습니다.
        // 마지막으로 세번째 인수 Intent는 호출된 Activity에서 저장한 결과입니다.

        // 요청코드가 1번이고 결과코드가 OK일 때
        if (requestCode == REQ_ADD_DREAM) {
            if (resultCode == Activity.RESULT_OK) {

                // "dreamname" 키값에 담긴 꿈을 string 타입의 str_dreamname으로 담아줌
                String str_dreamname = data.getStringExtra("dreamname");

                // adddream화면으로부터 "dreamexplain" 키값에 담긴 꿈 메모를 string 타입의 str_dreamexplain에 넣어줌
                String str_dreamexplain = data.getStringExtra("dreamexplain");
                Log.v("드림보드 리사이클러뷰 화면 추가 확인 알림 로그", "String str_dreamname : " + str_dreamname + " str_dreamexplain : " + str_dreamexplain);

                // adddream화면으로부터 "dreameuri" 키값에 담긴 이미지 uri를 string 타입의 str_dreameimageuri에 넣어줌
                String str_dreameimageuri = data.getStringExtra("dreamuri");

                // string 타입에 담긴 str_dreamname 값을 데이터클래스의 객체가 생성될 때 생성자로 넣어줌
                DataDreamboard dataDreamboard = new DataDreamboard(str_dreamname, str_dreamexplain, str_dreameimageuri);
                Log.v("드림보드 리사이클러뷰 화면 추가 확인 알림 로그", "String str_dreamname : " + str_dreamname + " str_dreamexplain : " + str_dreamexplain + " str_dreameimageuri : " + str_dreameimageuri);

                // Log.v("드림보드 리사이클러뷰 화면 확인 알림 로그","dataDreamboard");

                // 그럼 입력값이 데이터클래스에 갔다가 출력되어 어레이리스트에 첫 줄부터 쌓이게 됨
                dreamboardArrayList.add(0, dataDreamboard); //첫 줄에 삽입
                // dreamboardArrayList.add(dataDreamboard); // 마지막 줄에 삽입

                // 어댑터에서 RecyclerView에 데이터를 반영하도록 합니다.
                dreamboardAdapter.notifyDataSetChanged(); //변경된 데이터를 화면에 반영 -- notifyitemchanged로 특정 position을 제대로 짚어주는 게 좋긴 함 - 이건 다음에 해보자.

            } else {

//                Toast.makeText(DreamboxActivity.this, "Failed", Toast.LENGTH_SHORT).show();

            }
        }

        else{

            if (requestCode == REQ_EDIT_DREAM && resultCode == Activity.RESULT_OK) {

                // edit 화면으로부터 수정된 꿈 이름을 받아와줌
                String str_editdream = data.getStringExtra("editdreamname2") ;

                // edit 화면으로부터 수정된 꿈 메모를 받아와줌
                String str_editdreamexplain = data.getStringExtra("editdreamexplain2") ;

                // edit 화면으로부터 수정된 꿈 이미지를 받아와줌
                String str_editdreameimage = data.getStringExtra("editdreameimageuri2") ;

                // editdream 화면으로부터 "editposition2" 키에 담긴 position 값을 받아준다.
                int edit_position = data.getIntExtra("editposition2", 0);
                Log.v("드림보드 리사이클러뷰 화면 수정 확인 알림 로그", "String str_editdream : " + str_editdream + " str_editdreamexplain  : " + str_editdreamexplain );

                // recycleritem 데이터클래스 생성자로 Edit_title과 Edit_contents를 받아준다.
                // recycleritem 데이터클래스를 보면 String 값을 두 개 넣어주고 반환해주기 때문이다.
                DataDreamboard editdatadreamboard = new DataDreamboard(str_editdream, str_editdreamexplain, str_editdreameimage);

                // 수정할 때 해당 position 값을 넣어주고, ri를 입력해준다.
                dreamboardArrayList.set(edit_position, editdatadreamboard);
                Toast.makeText(getApplicationContext(), " 수정되었습니다. ", Toast.LENGTH_SHORT).show();

                // 어댑터에서 RecyclerView에 데이터를 반영하도록 합니다.
                dreamboardAdapter.notifyDataSetChanged();

            }else{

//                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();

            }

        }

    }

}

