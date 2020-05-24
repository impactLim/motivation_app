package com.impact.mycando.recyclerview_wisesentences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.impact.mycando.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class AdapterWisesentences extends RecyclerView.Adapter<AdapterWisesentences.wiseViewHolder> {

    // arraylist가 담길 데이터 타입 - 데이터 클래스 - 클래스 형태로 담긴다.
    private ArrayList<DataWisesentences> mWiseList;
    // context 통해서 해줘야 하는 작업이 있습니다.ㅋㅋㅎㅎㅎ;
    private Context mwiseContext;


    // 회원가입한 정보 shared 객체를 아이디, 비밀번호 입력값과 비교하기 위해 가지고 와야함
    SharedPreferences userinfo;

    // 로그인했을 때 같이 넘겨준 아이디 파일
    SharedPreferences userid;
    // 아이디파일에 담겨져 있는 string
    String useridString;

    // 문장들 저장해둔 파일
    SharedPreferences UsermainPreffile;
    SharedPreferences.Editor UsermainPrefEditor;

    String editmaincontents;

    // 이 어댑터 생성자 - 컨텍스트, arraylist
    public AdapterWisesentences(Context context, ArrayList<DataWisesentences> dataWisesentencesArrayList){

        mWiseList = dataWisesentencesArrayList;
        mwiseContext = context;

    }

    // 화면에 표시될 아이템 뷰들을 담을 뷰홀더
    // 리스트 항목 하나의 view를 만들고 보존하는 일을 한다.
    // 리사이클러뷰에 들어갈 뷰 홀더, 그리고 그 뷰홀더에 들어갈 아이템들을 지정
    public class wiseViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        protected TextView sentence;

        public wiseViewHolder(@NonNull View itemView) {
            super(itemView);
            // 텍스트뷰 참조
            this.sentence = (TextView) itemView.findViewById(R.id.tv_wisesentences);
            // 컨텍스트메뉴 리스너
            itemView.setOnCreateContextMenuListener(this);
        }

        // 컨텍스트 메뉴 생성
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            // 수정, 삭제 메뉴 옵션
            MenuItem Edit = menu.add(Menu.NONE, 1001, 1, "수정");
//            MenuItem Delete = menu.add(Menu.NONE, 1002, 2, "삭제");
            // 각 옵션마다 달려있는 리스너
            Edit.setOnMenuItemClickListener(onEditMenu);
//            Delete.setOnMenuItemClickListener(onEditMenu);
        }

        // 4. 컨텍스트 메뉴에서 항목 클릭시 동작을 설정합니다.
        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Log.v("배경화면 문구 추가 adapter 알림","onMenuItemclick 확안 알림 로그");

                switch (item.getItemId()) {
                    case 1001:  // 5. 수정 항목을 선택시
                        Log.v("배경화면 문구 추가 adapter 알림","수정 항목 선택시 - 확인 알림 로그");

                        //AlertDialog를 생성하기 위해서 먼저 AlertDialog.Builder 객체를 생성하고 이 객체의 메소드들을 호출해서 속성을 지정하고 생성한다
                        AlertDialog.Builder builder = new AlertDialog.Builder(mwiseContext);

                        // 다이얼로그를 보여주기 위해 activity_addsentences_dialog.xml 파일을 사용합니다.
                        View view = LayoutInflater.from(mwiseContext)
                                .inflate(R.layout.activity_addsentences_dialog, null, false);

                        // AlertDialog 도 제목 부분과 메시지 부분의 분리해서 사용자가 직접 디자인 할수 있습니다.
                        // inflate 를 사용해서 View 위젯을 만든후 AlertDialog 에서 지원하는 setCustomTitle(), setView() 함수를 이용하는 것.

                        builder.setView(view);
                        builder.setTitle("'할 수 있다' 문구 수정");
                        //		일반적으로 변수라고 하는 것은 프로그램 실행 중에 변수의 값이 변할 수 있다는 것을 말한다.
                        //		하지만 변수가 처음 정해진 값 이외에 다른 값을 가지면 안되는 경우가 있다면 변수 이름 앞에 final을 붙여서
                        //		상수처럼 사용 할 수 있다.
                        //		객체마다 메모리 잡을 필요가 없기때문에 static 붙여준다. 접근명시자 또한 public으로 해줌

                        final Button ButtonSubmit = (Button) view.findViewById(R.id.button_dialog_submit);
                        final Button ButtonCancel = (Button) view.findViewById(R.id.button_dialog_cancel);
                        final EditText editTextSentence = (EditText) view.findViewById(R.id.edittext_dialog_sentence);
//                        final EditText editTextEnglish = (EditText) view.findViewById(R.id.edittext_dialog_endlish);
//                        final EditText editTextKorean = (EditText) view.findViewById(R.id.edittext_dialog_korean);

                        // 6. 해당 줄에 입력되어 있던 데이터를 불러와서 다이얼로그에 보여줍니다.
                        // arraylist의 get임.
                        editTextSentence.setText(mWiseList.get(getAdapterPosition()).getWisesentence());
//                        editTextEnglish.setText(mList.get(getAdapterPosition()).getEnglish());
//                        editTextKorean.setText(mList.get(getAdapterPosition()).getKorean());

                        Log.v("배경화면 문구 추가 adapter 알림 - 로그 꼭 확인","mWiseList.get(getAdapterPosition()).getWisesentence() - 입력받은 값" + mWiseList.get(getAdapterPosition()).getWisesentence() + " mWiseList.get(getAdapterPosition()) :  " + mWiseList.get(getAdapterPosition()) + " getAdapterPosition() : position값" + getAdapterPosition());
                        Log.v("배경화면 문구 추가 adapter 알림","해당 줄에 입력되어 있던 데이터 불러와서 다이얼로그 보여줌");

                        final AlertDialog dialog = builder.create();
                        ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                            // 7. 수정 버튼을 클릭하면 현재 UI에 입력되어 있는 내용으로
                            public void onClick(View v) {

                                // 로그인했을 때 같이 넘겨준 ID
                                // SharedPreferences userid
                                userid = mwiseContext.getSharedPreferences("useridFile", MODE_PRIVATE);
                                // 로그인할 때 가져온 id가 useridString이다.
                                // String useridString
                                useridString = userid.getString("userid", "");

                                // 회원가입한 정보 shared 객체를 가져와 닉네임을 불러와준다.
                                userinfo = mwiseContext.getSharedPreferences("userinfoFile", MODE_PRIVATE);

                                // 추가 버튼을 눌렀을 때 저장되어서 화면에 있는 리사이클러뷰에 불러와져야함.
                                // 그러기 위해 shared 파일을 하나 더 만들어줌
                                // SharedPreferences UsermainPref
                                UsermainPreffile = mwiseContext.getSharedPreferences("UsermainPrefFile", 0);

                                // editor라는게 필요해서 만들어줬다.
                                // SharedPreferences.Editor UsermainPrefEditor
                                UsermainPrefEditor = UsermainPreffile.edit();

                                // 현재 shared에 담겨있는 콘텐츠를 receive 해준다.
                                String receive_wisecontents =  UsermainPreffile.getString(useridString, "");

                                // receive 해준 걸 잘라서 string 배열에 담아준다.
                                String[] splitEditreceiveinfo = receive_wisecontents.split("&&&");

                                // 회원정보가 해당 아이디를 가지고 있다면,
                                if(userinfo.contains(useridString)) {

                                    // 명심할 것이 null이라면
                                    if(editTextSentence.getText().toString().equals("")){

                                        Toast.makeText((mwiseContext), "배경화면 문구를 입력해주세요.", Toast.LENGTH_SHORT).show();

                                        // 내용이 들어가 있으면
                                    } else{

                                        //  저장 없이 arraylist에서만 추가 수정 삭제할 때
                                        String strwiseSentence = editTextSentence.getText().toString();

                                        // string 문구를 매개변수로 받는 데이터 클래스의 객체 생성
                                        DataWisesentences dataWisesentences = new DataWisesentences(strwiseSentence); // , strEnglish, strKorean
                                        Log.v("배경화면 문구 추가 adapter 알림","수정 버튼을 클릭하면 현재 UI에 입력되어 있는 내용으로");

                                        // 8. ListArray에 있는 데이터를 변경하고 set 해줌
                                        mWiseList.set(getAdapterPosition(), dataWisesentences);
                                        Log.v("배경화면 문구 추가 adapter 알림","ListArray에 있는 데이터를 변경하고");

                                        // 해당 포지션에 있는 아이템이 바뀌게 한다.
                                        // 9. 어댑터에서 RecyclerView에 반영하도록 합니다.
                                        notifyItemChanged(getAdapterPosition());
                                        Log.v("명심할 것 adapter 확인 알림 로그","어댑터에서 RecyclerView에 반영하도록 합니다.");

                                        // 바뀐 값 저장시켜줄 코드
                                        // 해당 유저가 가지고 있는 기록을 receive_keepcontents로 받아왔다.
                                        // receive_keepcontents로 수정할 게시물의 포지션에 있는 값을 배열 인덱스 값으로 가져와서 수정할 값인 strKeepsentence를 넣어주고
                                        // editkeepcontents로 받아와준다.
                                        // String editmaincontents;

                                        // receive해준 것 중에서 해당 포지션에 있는 값(splitEditreceiveinfo[getAdapterPosition()])을 내가 수정한 값인 strwisesentence로 바꿔준 값이 editmaincontents
                                        editmaincontents = receive_wisecontents.replace(splitEditreceiveinfo[getAdapterPosition()],
                                                strwiseSentence);
                                        Log.v("editmaincontents 확인 알림 로그 ", "" + strwiseSentence);

                                        // 해당 포지션에 있는 값을 수정한 걸 저장 >> 잠시만, 근데 이게 왜 돌아가지? 키값에 editkeepcontents로 덮어씌워지는거고
                                        // 여기엔 해당 포지션의 변경된 값만 들어가는거지, 다른 값들은 안 들어가 있는데.
                                        // 저 editkeepcontents가 뜻하는게 뭔데? 해당포지션의 값만 수정하고 나머지 값들은 그대로 출력되는거다.
                                        // 그러니 가능한 것. 신기한데.
                                        UsermainPrefEditor.putString(useridString, editmaincontents);

                                        // 파일에 수정한 걸 최종 반영함
                                        UsermainPrefEditor.commit();

                                        // 저장까지 다 반영하고 다이어로그 사라지게 하는게 좋겠지
                                        dialog.dismiss();
                                        Toast.makeText((mwiseContext), "'할 수 있다' 문구가 수정되었습니다.", Toast.LENGTH_SHORT).show();

                                    }

                                }


//                                String strSentence = editTextSentence.getText().toString();
////                                String strEnglish = editTextEnglish.getText().toString();
////                                String strKorean = editTextKorean.getText().toString();
//
//                                // string 문구를 매개변수로 받는 데이터 클래스의 객체 생성
//                                DataWisesentences dataWisesentences = new DataWisesentences(strSentence); // , strEnglish, strKorean
//                                Log.v("배경화면 문구 추가 adapter 알림","수정 버튼을 클릭하면 현재 UI에 입력되어 있는 내용으로");
//
//                                // 8. ListArray에 있는 데이터를 변경하고 set 해줌
//                                mWiseList.set(getAdapterPosition(), dataWisesentences);
//                                Log.v("배경화면 문구 추가 adapter 알림","ListArray에 있는 데이터를 변경하고");
//
//                                // 9. 어댑터에서 RecyclerView에 반영하도록 합니다.
//                                notifyItemChanged(getAdapterPosition());
//                                Log.v("배경화면 문구 추가 adapter 알림","어댑터에서 RecyclerView에 반영하도록 합니다.");
//                                Toast.makeText((mwiseContext), "명언이 수정되었습니다.", Toast.LENGTH_SHORT).show();
//
//                                dialog.dismiss();

                            }
                        });

                        // 수정 눌렀을 때 나오는 삭제와 수정 중 취소를 누르면 취소됨
                        ButtonCancel.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        dialog.show();

                        break;

//                    case 1002: // 5. 삭제 항목을 선택 시
//
//                        Log.v("배경화면 문구 추가 adapter 알림","삭제 항목을 선택 시");
//
//                        // 6. ArraylistT에서 해당 데이터를 삭제
//                        Log.v("배경화면 문구 추가 adapter 알림","ArraylistT에서 해당 데이터를 삭제");
//                        mWiseList.remove(getAdapterPosition());
//
//                        // 7. 어댑터에서 recyclerview에 반영하도록 한다.
//                        notifyItemRemoved(getAdapterPosition());
//                        notifyItemRangeChanged(getAdapterPosition(), mWiseList.size());
//
//                        Log.v("배경화면 문구 추가 adapter 알림","어댑터에서 recyclerview에 반영하도록 한다.");
//                        break;

                }
                return true;
            }
        };

    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    // 리사이클러뷰에 들어갈 뷰 홀더를 할당하는 함수, 뷰홀더는 실제 레이아웃 파일(recycler_item)과 매핑되어야 한다.
    // 리사이클러뷰가 아이템뷰레이아웃인 (recycler_item xml 재활용할 수 있도록 이 viewholder라는 클래스에 xml에 대한 정보를 넣어준다.
    // xml 파일을 객체로 만들어서 쓰는게 layout inflater다. 객체로 만들려면 context(안드로이드 권한이)가 필요하다.
    // 아이템마다 고유값을 만들어주기 위해 xml파일을 객체로 만들어줘야 한다.
    @NonNull
    @Override
    public wiseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mainsentences, parent, false);

        wiseViewHolder viewHolder = new wiseViewHolder(view);
        Log.v("배경화면 문구 추가 adapter 알림","onCreatViewHolder");

        return viewHolder;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    // 실제 각 뷰 홀더에 들어갈 데이터를 연결해주는 함수
    // oncreate에서 뷰홀더 객체를 생성하여 리턴해주었던 각 객체에 들어갈 데이터들을 연결시켜줘야 한다.
    @Override
    public void onBindViewHolder(@NonNull wiseViewHolder holder, int position) {

        holder.sentence.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25); // 텍스트 사이즈를 25로
        holder.sentence.setGravity(Gravity.CENTER); // 아이템뷰들을 가운데 정렬
        holder.sentence.setText(mWiseList.get(position).getWisesentence()); // arraylist가 해당 포지션 받아서 그 포지션에 있는 명언을 set시켜줌
        Log.v("배경화면 문구 추가 adapter onbindviewholder 알림 - 로그 꼭 확인","(mWiseList.get(position).getWisesentence() : " + mWiseList.get(position).getWisesentence() + " position : " + position );

    }

    @Override
    public int getItemCount() {
        Log.v("배경화면 문구 추가 adapter 알림","getItemCount");

        // 이건 무슨 말? 제대로 알아보기
        return (null != mWiseList ? mWiseList.size() : 0);    }

}
