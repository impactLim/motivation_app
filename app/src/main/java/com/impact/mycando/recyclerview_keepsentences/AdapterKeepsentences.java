package com.impact.mycando.recyclerview_keepsentences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextMenu;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class AdapterKeepsentences extends RecyclerView.Adapter<AdapterKeepsentences.KeepViewHolder> {


    // arraylist가 담길 데이터 타입 - 데이터 클래스 - 클래스 형태로 담긴다.
    private ArrayList<DataKeepsentences> mKeepList;
    // context 통해서 해줘야 하는 작업이 있습니다.ㅋㅋㅎㅎㅎ;
    private Context mkeepContext;


    // 회원가입한 정보 shared 객체를 아이디, 비밀번호 입력값과 비교하기 위해 가지고 와야함
    SharedPreferences userinfo;

    // 로그인했을 때 같이 넘겨준 아이디 파일
    SharedPreferences userid;
    // 아이디파일에 담겨져 있는 string
    String useridString;

    // 문장들 저장해둔 파일
    SharedPreferences UserkeepPreffile;
    SharedPreferences.Editor UserkeepPrefEditor;

    // 문장들 저장해둔 파일에서 로그인한 유저를 key값으로 한 value값을 받아와준다.
    String receive_keepcontents;

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


    // 이 어댑터 생성자 - 컨텍스트, arraylist
    public AdapterKeepsentences(Context context, ArrayList<DataKeepsentences> dataKeepsentencesArrayList) {

        mKeepList = dataKeepsentencesArrayList;
        mkeepContext = context;

    }

    public class KeepViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        protected TextView keepsentence;
        protected TextView dateNow;

        public KeepViewHolder(@NonNull View itemView) {
            super(itemView);
            // 텍스트뷰 참조
            this.keepsentence = (TextView) itemView.findViewById(R.id.keepsentences_text);
            this.dateNow = itemView.findViewById(R.id.keep_itemdate);

            // 컨텍스트메뉴 리스너
            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            // 3. 메뉴 추가.  컨텍스트 메뉴를 생성하고 메뉴 항목 선택시 호출되는 리스너를 등록. ID 1001 - 1002로 어떤 메뉴를 선택했는지 리스너에서 구분하게 됩니다.

            MenuItem Edit = menu.add(Menu.NONE, 1001, 1, "수정");
            MenuItem Delete = menu.add(Menu.NONE, 1002, 2, "삭제");
            Edit.setOnMenuItemClickListener(onEditMenu);
            Delete.setOnMenuItemClickListener(onEditMenu);

            Log.v("명심할 것 adapter 확인 알림 로그 ","메뉴 추가");
        }

        // 4. 컨텍스트 메뉴에서 항목 클릭시 동작을 설정합니다.
        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                // 회원가입한 정보 shared 객체를 가져와 닉네임을 불러와준다.
                userinfo = mkeepContext.getSharedPreferences("userinfoFile", MODE_PRIVATE);

                // 로그인했을 때 같이 넘겨준 ID
                // SharedPreferences userid
                userid = mkeepContext.getSharedPreferences("useridFile", MODE_PRIVATE);
                // 로그인할 때 가져온 id가 useridString이다.
                // String useridString
                useridString = userid.getString("userid", "");

                // 추가 버튼을 눌렀을 때 저장되어서 화면에 있는 리사이클러뷰에 불러와져야함.
                // 그러기 위해 shared 파일을 하나 더 만들어줌
                // SharedPreferences UserkeepPref
                UserkeepPreffile = mkeepContext.getSharedPreferences("UserkeepPrefFile", 0);

                // editor라는게 필요해서 만들어줬다.
                // SharedPreferences.Editor UserkeepPrefEditor
                UserkeepPrefEditor = UserkeepPreffile.edit();

                // String receive_keepcontents
                // 이 string 값에 해당 유저가 작성한 컨텐츠들이 문자열로 담겨있다.
                receive_keepcontents = UserkeepPreffile.getString(useridString, "");
                Log.v("명심할 것receive_keepcontents 확인 알림 로그","receive_keepcontents : " + receive_keepcontents);

                Log.v("명심할 것 adapter 확인 알림 로그","onMenuItemclick");

                switch (item.getItemId()) {
                    case 1001:  // 5. 편집 항목을 선택시
                        Log.v("명심할 것 adapter 확인 알림 로그","수정 항목 선택시");

                        //AlertDialog를 생성하기 위해서 먼저 AlertDialog.Builder 객체를 생성하고 이 객체의 메소드들을 호출해서 속성을 지정하고 생성한다
                        AlertDialog.Builder builder = new AlertDialog.Builder(mkeepContext);

                        // 다이얼로그를 보여주기 위해 activity_addkeepsentences_dialog 파일을 사용.
                        View view = LayoutInflater.from(mkeepContext)
                            .inflate(R.layout.activity_addkeepsentences_dialog, null, false);

                        // AlertDialog 도 제목 부분과 메시지 부분의 분리해서 사용자가 직접 디자인 가능.
                        // inflate 를 사용해서 View 위젯을 만든후 AlertDialog 에서 지원하는 setCustomTitle(), setView() 함수를 이용

                        builder.setView(view);
                        builder.setTitle("'명심할 것' 수정");
                        //		일반적으로 변수라고 하는 것은 프로그램 실행 중에 변수의 값이 변할 수 있다는 것을 말한다.
                        //		하지만 변수가 처음 정해진 값 이외에 다른 값을 가지면 안되는 경우가 있다면 변수 이름 앞에 final을 붙여서
                        //		상수처럼 사용 할 수 있다.
                        //		객체마다 메모리 잡을 필요가 없기때문에 static 붙여준다. 접근명시자 또한 public으로 해줌

                        final Button ButtonSubmit = (Button) view.findViewById(R.id.button_dialog_submit);
                        final Button ButtonCancel = (Button) view.findViewById(R.id.button_dialog_cancel);
                        final EditText editKeeptextsentence = (EditText) view.findViewById(R.id.edittext_dialog_keepsentence);
//                        final EditText editTextEnglish = (EditText) view.findViewById(R.id.edittext_dialog_endlish);
//                        final EditText editTextKorean = (EditText) view.findViewById(R.id.edittext_dialog_korean);
                        // 다음에 날짜도 저장하려면 날짜를 불러오는 등의 작업도 함께 해줘야함

                        // 6. 해당 줄에 입력되어 있던 데이터를 불러와서 다이얼로그에 보여줍니다.
                        editKeeptextsentence.setText(mKeepList.get(getAdapterPosition()).getKeepsentence());
//                        editTextEnglish.setText(mList.get(getAdapterPosition()).getEnglish());
//                        editTextKorean.setText(mList.get(getAdapterPosition()).getKorean());
                        Log.v("명심할 것 adapter 확인 알림 로그","해당 줄에 입력되어 있던 데이터 불러와서 다이얼로그 보여줌");

                        final AlertDialog dialog = builder.create();
                        ButtonSubmit.setOnClickListener(new View.OnClickListener() {

                            // 7. 수정 버튼을 클릭하면 현재 UI에 입력되어 있는 내용으로

                            public void onClick(View v) {

                                String[] splitEditreceiveinfo = receive_keepcontents.split("&&&&&&");

                                // 회원정보가 해당 아이디를 가지고 있다면,
                                if(userinfo.contains(useridString)) {

                                    // 명심할 것이 null이라면
                                    if(editKeeptextsentence.getText().toString().equals("")){

                                        Toast.makeText((mkeepContext), "명심할 것을 입력해주세요.", Toast.LENGTH_SHORT).show();

                                        // 내용이 들어가 있으면
                                    } else{

                                        //  저장 없이 arraylist에서만 추가 수정 삭제할 때
                                        String strKeepsentence = editKeeptextsentence.getText().toString();

                                        // 내가 입력한 값을 string 타입으로 받아준다. 이걸 데이터 클래스 생성자로 넣어주면 string 타입을 매개변수로 받는 데이터클래스의 생성자가 입력값을 그대로 출력해준다.
                                        DataKeepsentences dataKeepsentences = new DataKeepsentences(strKeepsentence, dateStr); // , strEnglish, strKorean
                                        Log.v("명심할 것 adapter 확인 알림 로그","수정 버튼을 클릭하면 현재 UI에 입력되어 있는 내용으로");

                                        // 그러면 해당 포지션에 있는, 어레이리스트에 담겨있는 값을 입력한 내용으로 다시 set - 바꿔준다.
                                        // 8. ListArray에 있는 데이터를 변경하고
                                        mKeepList.set(getAdapterPosition(), dataKeepsentences);
                                        Log.v("명심할 것 adapter 확인 알림 로그","Arraylist에 있는 데이터를 변경하고");

                                        // 해당 포지션에 있는 아이템이 바뀌게 한다.
                                        // 9. 어댑터에서 RecyclerView에 반영하도록 합니다.
                                        notifyItemChanged(getAdapterPosition());
                                        Log.v("명심할 것 adapter 확인 알림 로그","어댑터에서 RecyclerView에 반영하도록 합니다.");

                                        // 바뀐 값 저장시켜줄 코드
                                        // 해당 유저가 가지고 있는 기록을 receive_keepcontents로 받아왔다.
                                        // receive_keepcontents로 수정할 게시물의 포지션에 있는 값을 배열 인덱스 값으로 가져와서 수정할 값인 strKeepsentence를 넣어주고
                                        // editkeepcontents로 받아와준다.
                                        String editkeepcontents;

                                        // String editkeepcontents;
                                        editkeepcontents = receive_keepcontents.replace(splitEditreceiveinfo[splitEditreceiveinfo.length -1 - getAdapterPosition()],
                                                strKeepsentence + "##!" + dateStr);
                                        Log.v("editkeepcontents 확인 알림 로그 ", "" + editkeepcontents);

                                        // 해당 포지션에 있는 값을 수정한 걸 저장 >> 잠시만, 근데 이게 왜 돌아가지? 키값에 editkeepcontents로 덮어씌워지는거고
                                        // 여기엔 해당 포지션의 변경된 값만 들어가는거지, 다른 값들은 안 들어가 있는데.
                                        // 저 editkeepcontents가 뜻하는게 뭔데? 해당포지션의 값만 수정하고 나머지 값들은 그대로 출력되는거다.
                                        // 그러니 가능한 것. 신기한데.
                                        UserkeepPrefEditor.putString(useridString, editkeepcontents);

                                        // 파일에 수정한 걸 최종 반영함
                                        UserkeepPrefEditor.commit();

                                        // 저장까지 다 반영하고 다이어로그 사라지게 하는게 좋겠지
                                        dialog.dismiss();
                                        Toast.makeText((mkeepContext), "명심할 것이 수정되었습니다.", Toast.LENGTH_SHORT).show();

                                    }

                                }


                            }
                        });

                        // 편집 눌렀을 때 나오는 삭제와 편집 중 취소를 누르면 취소됨
                        ButtonCancel.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        dialog.show();

                        break;

                    case 1002: // 5. 삭제 항목을 선택 시

//                        // 포지션값과 size를 알기 위해선
//                        if(getAdapterPosition() == 0){
//                            Toast.makeText((mkeepContext), "mKeepList.size() 1  : " + mKeepList.size(), Toast.LENGTH_SHORT).show();
//                            Toast.makeText((mkeepContext), "position 값 : " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
//                            Toast.makeText((mkeepContext), "mKeepList.size() 2 : " + mKeepList.size(), Toast.LENGTH_SHORT).show();
//                        }else{
//                            Toast.makeText((mkeepContext), "mKeepList.size() 1 : " + mKeepList.size(), Toast.LENGTH_SHORT).show();
//                            Toast.makeText((mkeepContext), "position 값 : " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
//                            Toast.makeText((mkeepContext), "mKeepList.size() 2 : " + mKeepList.size(), Toast.LENGTH_SHORT).show();
//                        }


                        Log.v("명심할 것 adapter 확인 알림 로그","삭제 항목을 선택 시");
//                        Toast.makeText((mkeepContext), "position 값 : " + getAdapterPosition(), Toast.LENGTH_SHORT).show();

                        // 삭제할 때 &&&로 잘라준 값들을 아래 배열에 담아준다.
                        String[] splitReceiveinfo = receive_keepcontents.split("&&&&&&");
                        Log.v("명심할 것 splitReceiveinfo 확인 알림 로그","splitReceiveinfo : " + splitReceiveinfo);

                        for(int i = 0; i < splitReceiveinfo.length; i++){

                            String checksplit = splitReceiveinfo[i];
                            Log.v("명심할 것 다이어로그 checksplit 확인 알림 로그","checksplit "  +  checksplit);
                            Log.v("명심할 것 다이어로그 splitReceiveinfo.length 확인 알림 로그","splitReceiveinfo.length "  +  splitReceiveinfo.length);

                        }

                        // 유저정보가 해당 아이디를 포함하고 있다면
                        if(userinfo.contains(useridString)){

                            // 맨 처음에 저장된 값은 맨 밑에 있다. 이 아이템의 포지션은 arraylist의 길이에서 1을 빼준 값과 같다.
                            // 고로 맨 처음에 저장된 값, 맨 밑에 있는 값을 삭제하려면
                            if(getAdapterPosition() == mKeepList.size() -1){

//                                Toast.makeText((mkeepContext), "position 값 : " + getAdapterPosition(), Toast.LENGTH_SHORT).show();

                                // 남아있는 값이 한 개인지 여러 개인지 판단은 어떻게? >
                                // 남아있는 값이 한 개일 때 삭제할 경우 >> &&&&&& 이건 빼고 그냥 값을 "" 공백으로 처리
                                if(mKeepList.size() == 1){

                                    // 삭제하려는 값을 삭제는 못하고 대신 &&&&&& + 해당 인덱스에 있는 값을 공백으로 바꿔준다. 다만, 현재는 첫번째값을 삭제하면 안된다.
                                    String deletekeepcontent = receive_keepcontents.replace(splitReceiveinfo[splitReceiveinfo.length - 1 - getAdapterPosition()],"");
                                    Log.v("keep adapter deleteContentsInfo 확인 알림 로그", "" + deletekeepcontent);

                                    UserkeepPrefEditor.putString(useridString, deletekeepcontent);

                                    // 파일에 최종 반영함
                                    UserkeepPrefEditor.commit();

                                    // 6. ArraylistT에서 해당 데이터를 삭제
                                    Log.v("명심할 것 adapter 확인 알림 로그","ArraylistT에서 해당 데이터를 삭제");
                                    // 해당 포지션에 있는 값을 제거한다.
                                    mKeepList.remove(getAdapterPosition());
                                    Log.v("명심할 것 adapter getAdapterPosition() 확인 알림 로그","getAdapterPosition()" + getAdapterPosition());

                                    // 7. 어댑터에서 recyclerview에 반영하도록 한다.
                                    notifyItemRemoved(getAdapterPosition()); // 특정 Position에 데이터를 하나 제거할 경우.
                                    notifyItemRangeChanged(getAdapterPosition(), mKeepList.size()); // 특정 영역을 데이터가 바뀌었을 경우. position 3~10번까지의 데이터만 바뀌었을 경우 사용 하면 된다.

                                    Log.v("명심할 것 adapter 확인 알림 로그","어댑터에서 recyclerview에 반영하도록 한다.");
                                    Toast.makeText((mkeepContext), "명심할 것이 삭제되었습니다.", Toast.LENGTH_SHORT).show();

                                    notifyDataSetChanged();

                                    break;

                                // 남아있는 값이 여러 개일때 삭제할 경우 >> &&&까지 target으로 정해서 함께 "" 공백 처리
                                } else {

                                    // 삭제하려는 값을 삭제는 못하고 대신 &&& + 해당 인덱스에 있는 값을 공백으로 바꿔준다. 다만, 현재는 첫번째값을 삭제하면 안된다.
                                    String deletekeepcontent = receive_keepcontents.replace(splitReceiveinfo[splitReceiveinfo.length - 1 - getAdapterPosition()] + "&&&&&&","");
                                    Log.v("keep adapter deleteContentsInfo 확인 알림 로그", "" + deletekeepcontent);

                                    UserkeepPrefEditor.putString(useridString, deletekeepcontent);

                                    // 파일에 최종 반영함
                                    UserkeepPrefEditor.commit();

                                    // 6. ArraylistT에서 해당 데이터를 삭제
                                    Log.v("명심할 것 adapter 확인 알림 로그","ArraylistT에서 해당 데이터를 삭제");
                                    // 해당 포지션에 있는 값을 제거한다.
                                    mKeepList.remove(getAdapterPosition());
                                    Log.v("명심할 것 adapter getAdapterPosition() 확인 알림 로그","getAdapterPosition()" + getAdapterPosition());

                                    // 7. 어댑터에서 recyclerview에 반영하도록 한다.
                                    notifyItemRemoved(getAdapterPosition()); // 특정 Position에 데이터를 하나 제거할 경우.
                                    notifyItemRangeChanged(getAdapterPosition(), mKeepList.size()); // 특정 영역을 데이터가 바뀌었을 경우. position 3~10번까지의 데이터만 바뀌었을 경우 사용 하면 된다.

                                    Log.v("명심할 것 adapter 확인 알림 로그","어댑터에서 recyclerview에 반영하도록 한다.");

                                    notifyDataSetChanged();
                                    Toast.makeText((mkeepContext), "명심할 것이 삭제되었습니다.", Toast.LENGTH_SHORT).show();

                                    break;

                                }


                                // 그 외에 포지션에 위치한 값을 삭제하려면.
                            } else {

                                // 삭제하려는 값을 삭제는 못하고 대신 &&& + 해당 인덱스에 있는 값을 공백으로 바꿔준다. 다만, 현재는 첫번째값을 삭제하면 안된다.
                                String deletekeepcontent = receive_keepcontents.replace("&&&&&&" + splitReceiveinfo[splitReceiveinfo.length - 1 - getAdapterPosition()],"");
                                Log.v("keep adapter deleteContentsInfo 확인 알림 로그", "" + deletekeepcontent);

                                UserkeepPrefEditor.putString(useridString, deletekeepcontent);

                                // 파일에 최종 반영함
                                UserkeepPrefEditor.commit();

                                // 6. ArraylistT에서 해당 데이터를 삭제
                                Log.v("명심할 것 adapter 확인 알림 로그","ArraylistT에서 해당 데이터를 삭제");
                                // 해당 포지션에 있는 값을 제거한다.
                                mKeepList.remove(getAdapterPosition());
                                Log.v("명심할 것 adapter getAdapterPosition() 확인 알림 로그","getAdapterPosition()" + getAdapterPosition());

                                // 7. 어댑터에서 recyclerview에 반영하도록 한다.
                                notifyItemRemoved(getAdapterPosition()); // 특정 Position에 데이터를 하나 제거할 경우.
                                notifyItemRangeChanged(getAdapterPosition(), mKeepList.size()); // 특정 영역을 데이터가 바뀌었을 경우. position 3~10번까지의 데이터만 바뀌었을 경우 사용 하면 된다.

                                Log.v("명심할 것 adapter 확인 알림 로그","어댑터에서 recyclerview에 반영하도록 한다.");

                                notifyDataSetChanged();
                                Toast.makeText((mkeepContext), "명심할 것이 삭제되었습니다.", Toast.LENGTH_SHORT).show();

                                break;

                            }

                        }

//                        // 6. ArraylistT에서 해당 데이터를 삭제
//                        Log.v("명심할 것 adapter 확인 알림 로그","ArraylistT에서 해당 데이터를 삭제");
//                        // 해당 포지션에 있는 값을 제거한다.
//                        mKeepList.remove(getAdapterPosition());
//                        Log.v("명심할 것 adapter getAdapterPosition() 확인 알림 로그","getAdapterPosition()" + getAdapterPosition());
//
//                        // 7. 어댑터에서 recyclerview에 반영하도록 한다.
//                        notifyItemRemoved(getAdapterPosition()); // 특정 Position에 데이터를 하나 제거할 경우.
//                        notifyItemRangeChanged(getAdapterPosition(), mKeepList.size()); // 특정 영역을 데이터가 바뀌었을 경우. position 3~10번까지의 데이터만 바뀌었을 경우 사용 하면 된다.
//
//                        Log.v("명심할 것 adapter 확인 알림 로그","어댑터에서 recyclerview에 반영하도록 한다.");
//
//                        notifyDataSetChanged();
//
//                        break;

                }
                return true;
            }
        };

    }




    @NonNull
    @Override
    public KeepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_keepsentences, parent, false);

        KeepViewHolder viewHolder = new KeepViewHolder(view);

        Log.v("명심할 것 adapter 확인 알림 로그","onCreatViewHolder");

        return viewHolder;    }

    @Override
    public void onBindViewHolder(@NonNull KeepViewHolder holder, int position) {
        holder.keepsentence.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        holder.keepsentence.setText(mKeepList.get(position).getKeepsentence());

        holder.dateNow.setText(mKeepList.get(position).getDateStr());

        Log.v("명심할 것 adapter 확인 알림 로그","onBindViewHolder");
    }

    @Override
    public int getItemCount() {
        Log.v("명심할 것 adapter 확인 알림 로그","getItemCount");

        return (null != mKeepList ? mKeepList.size() : 0);    }


}
