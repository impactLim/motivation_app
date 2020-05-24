package com.impact.mycando.recyclerview_goalmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.impact.mycando.MainActivity;
import com.impact.mycando.R;

import java.util.ArrayList;

public class GoalmanagementActivity extends AppCompatActivity {

    private ArrayList<DataGoalmanagement> mDatagoalList;
    private AdapterGoalmanagement madapterGoal;
    private RecyclerView mgoalRecyclerView;

    // 회원가입한 정보 shared 객체를 아이디, 비밀번호 입력값과 비교하기 위해 가지고 와야함
    SharedPreferences userinfo;

    // 로그인했을 때 같이 넘겨준 아이디 파일
    SharedPreferences userid;
    // 아이디파일에 담겨져 있는 string
    String useridString;

    SharedPreferences goalManagementPref;
    SharedPreferences.Editor goalManagementEditor;
    String get_goallists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goalmanagement);

        mgoalRecyclerView = findViewById(R.id.recyclerview_goalmanagement);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mgoalRecyclerView.setLayoutManager(mLinearLayoutManager);

        mDatagoalList = new ArrayList<>();

        // adapter 생성자 - 데이터클래스에서 필요한 이유
        madapterGoal = new AdapterGoalmanagement(this, mDatagoalList);

        mgoalRecyclerView.setAdapter(madapterGoal);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mgoalRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mgoalRecyclerView.addItemDecoration(dividerItemDecoration);



        // 로그인했을 때 같이 넘겨준 ID
        // SharedPreferences userid
        userid = getSharedPreferences("useridFile", MODE_PRIVATE);
        // 로그인할 때 가져온 id가 useridString이다.
        // String useridString
        useridString = userid.getString("userid", "");

        // 회원가입한 정보 shared 객체를 가져와 닉네임을 불러와준다.
        userinfo = getSharedPreferences("userinfoFile", MODE_PRIVATE);

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

        // SharedPreferences goalManagementPref
        goalManagementPref = getSharedPreferences("goalManagementFile", MODE_PRIVATE);
        // SharedPreferences.Editor goalManagementEditor
        goalManagementEditor = goalManagementPref.edit();


        // 회원정보가 useridString 값을 가지고 있으면
        if(userinfo.contains(useridString)){

            // 그 아이디를 key값으로 한
            // String get_goallists
            get_goallists = goalManagementPref.getString(useridString, "");
            Log.v("goallists 다이어로그 추가 알림 로그","get_goallists "  +  get_goallists);

            // 이 조건문 안 걸어주면 공백인 아이템이 출력된다.
            if(get_goallists.equals("") == false){

                // splitgoallists 배열에 get_goallists를 &&&로 잘라준다.
                String[] splitgoallists = get_goallists.split("&&&");

                // 그 잘라준 값들을 배열의 길이만큼 반복해주고 그때마다 어레이리스트에 담긴다.
                for(int i = 0; i< splitgoallists.length; i ++){

                    // get_goalcontent에 콘텐츠 개수만큼 받아주고
                    String get_goalcontent = splitgoallists[i].split("###")[0];
                    // boolean 값을 받아온다.
                    boolean get_goalcheck = Boolean.parseBoolean(splitgoallists[i].split("###")[1]);
                    Log.v("goallists 다이어로그 추가 알림 로그","get_goalcontent "  +  get_goalcontent + " get_goalcheck " + get_goalcheck );

                    // ArrayList에 추가하고
                    // 데이터 클래스 객체 생성 - 생성자는 입력값
                    // get_goalcontent에 입력받은 값이 string 타입으로 들어가게 되는데 이게 데이터 클래스인 datawisesentences로 넘어간다.
                    // 데이터 클래스 생성자엔 입력한 값을 return해 결과를 출력해준다.
                    DataGoalmanagement datagoalList = new DataGoalmanagement(get_goalcontent, get_goalcheck);

                    // 입력된 값이 추가됨
                    mDatagoalList.add(0, datagoalList); //첫 줄에 삽입
                    // mdatakeepArrayList.add(dataKeepsentences); //마지막 줄에 삽입

                    // 어댑터에서 RecyclerView에 반영하도록 합니다.
                    madapterGoal.notifyDataSetChanged(); //변경된 데이터를 화면에 반영

                }

            }

        }


    }
}
