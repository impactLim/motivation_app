package com.impact.mycando.recyclerview_goalmanagement;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.impact.mycando.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class AdapterGoalmanagement extends RecyclerView.Adapter<AdapterGoalmanagement.goalviewholder> {

    private ArrayList<DataGoalmanagement> mGoalList;
    private Context mGoalContext;


    // 회원가입한 정보 shared 객체를 아이디, 비밀번호 입력값과 비교하기 위해 가지고 와야함
    SharedPreferences userinfo;

    // 로그인했을 때 같이 넘겨준 아이디 파일
    SharedPreferences userid;
    // 아이디파일에 담겨져 있는 string
    String useridString;

    //
    SharedPreferences goalManagementPref;
    SharedPreferences.Editor goalManagementEditor;
    String receive_goallists;


    boolean linecheck;

    String[] splitReceiveGoalinfo;

    public AdapterGoalmanagement(Context context, ArrayList<DataGoalmanagement> mDatagoalList) {

        mGoalList = mDatagoalList;
        mGoalContext = context;

    }


    public class goalviewholder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        TextView goal;

        public goalviewholder(@NonNull View itemView) {
            super(itemView);

            this.goal = (TextView) itemView.findViewById(R.id.tv_goal);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            MenuItem Edit = menu.add(Menu.NONE, 1001, 1, "완료");
            MenuItem Delete = menu.add(Menu.NONE, 1002, 2, "삭제");
            Edit.setOnMenuItemClickListener(onEditMenu);
            Delete.setOnMenuItemClickListener(onEditMenu);

        }

        boolean compeleteCheck(){

            return  linecheck = true;
        }

        // 4. 컨텍스트 메뉴에서 항목 클릭시 동작을 설정합니다.
        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                // 회원가입한 정보 shared 객체를 가져와 닉네임을 불러와준다.
                userinfo = mGoalContext.getSharedPreferences("userinfoFile", MODE_PRIVATE);

                // 로그인했을 때 같이 넘겨준 ID
                // SharedPreferences userid
                userid = mGoalContext.getSharedPreferences("useridFile", MODE_PRIVATE);
                // 로그인할 때 가져온 id가 useridString이다.
                // String useridString
                useridString = userid.getString("userid", "");

                // 추가 버튼을 눌렀을 때 저장되어서 화면에 있는 리사이클러뷰에 불러와져야함.
                // 그러기 위해 shared 파일을 하나 더 만들어줌
                // SharedPreferences goalManagementPref;
                goalManagementPref = mGoalContext.getSharedPreferences("goalManagementFile", 0);

                // editor라는게 필요해서 만들어줬다.
                // SharedPreferences.Editor goalManagementEditor;
                goalManagementEditor =  goalManagementPref.edit();

                // String receive_goallists
                // 이 string 값에 해당 유저가 작성한 컨텐츠들이 문자열로 담겨있다.
                receive_goallists = goalManagementPref.getString(useridString, "");
                Log.v("receive_goallists 확인 알림 로그","receive_goallists : " + receive_goallists);


                switch (item.getItemId()) {
                    case 1001:  // 5. 완료 항목을 선택시
                        Log.v("adapter 알림","완료 항목 선택시");

                        goal.setPaintFlags(goal.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                        Log.v("goal.getPaintFlags() 확인 알림 로그","goal.getPaintFlags() : " + goal.getPaintFlags());
                        Log.v("goal.getPaintFlags() 확인 알림 로그","Paint.STRIKE_THRU_TEXT_FLAG : " + Paint.STRIKE_THRU_TEXT_FLAG);

                        compeleteCheck();
                        Log.v("목표관리 adapter linecheck 확인 알림 로그","linecheck : " + linecheck);

                        // 삭제할 때 &&&로 잘라준 값들을 아래 배열에 담아준다.
                        // String[] splitReceiveGoalinfo
                        splitReceiveGoalinfo = receive_goallists.split("&&&");

                        for(int i = 0; i < splitReceiveGoalinfo.length; i++){

                            String get_goal = splitReceiveGoalinfo[i].split("###")[0];
                            Log.v("목표관리 adapter get_goal  확인 알림 로그","get_goal  "  + get_goal );
                            Log.v("목표관리 adapter splitReceiveGoalinfo.length확인 알림 로그","splitReceiveGoalinfo.length "  +  splitReceiveGoalinfo.length);
                            Boolean get_successcheck = Boolean.valueOf(splitReceiveGoalinfo[i].split("###")[1]);
                            Log.v("목표관리 adapter get_goal  확인 알림 로그","get_successcheck  "  + get_successcheck );

                        }

                        String editgoalcontents;

                        // String editkeepcontents;
                        editgoalcontents = receive_goallists.replace(splitReceiveGoalinfo[splitReceiveGoalinfo.length -1 - getAdapterPosition()].split("###")[1],
                                "" + linecheck);
                        Log.v("receive_goallists 확인 알림 로그 ", "" + receive_goallists);
                        Log.v("splitReceiveGoalinfo[splitReceiveGoalinfo.length -1 - getAdapterPosition()] 확인 알림 로그", "" + splitReceiveGoalinfo[splitReceiveGoalinfo.length -1 - getAdapterPosition()]);
                        Log.v("splitReceiveGoalinfo[splitReceiveGoalinfo.length -1 - getAdapterPosition()].split(\"###\")[1] 확인 알림 로그 ",
                                "" + splitReceiveGoalinfo[splitReceiveGoalinfo.length -1 - getAdapterPosition()].split("###")[1]);
                        Log.v("editgoalcontents 확인 알림 로그 ", "" + editgoalcontents);

                        goalManagementEditor.putString(useridString, editgoalcontents);

                        // 파일에 수정한 걸 최종 반영함
                        goalManagementEditor.commit();

                        DataGoalmanagement dataGoalmanagement = new DataGoalmanagement(splitReceiveGoalinfo[splitReceiveGoalinfo.length -1 - getAdapterPosition()].split("###")[0],
                                Boolean.valueOf(splitReceiveGoalinfo[splitReceiveGoalinfo.length -1 - getAdapterPosition()].split("###")[1]));

                        mGoalList.set(getAdapterPosition(), dataGoalmanagement);

                        // notifyItemChanged(getAdapterPosition()); > 반영되었다가 꺼짐
                        // notifyDataSetChanged(); > 아예 반영 x





//                        //  저장 없이 arraylist에서만 추가 수정 삭제할 때
//                        String strwiseSentence = editTextSentence.getText().toString();
//
//                        // string 문구를 매개변수로 받는 데이터 클래스의 객체 생성
//                        DataWisesentences dataWisesentences = new DataWisesentences(strwiseSentence); // , strEnglish, strKorean
//                        Log.v("배경화면 문구 추가 adapter 알림","수정 버튼을 클릭하면 현재 UI에 입력되어 있는 내용으로");
//
//                        // 8. ListArray에 있는 데이터를 변경하고 set 해줌
//                        mWiseList.set(getAdapterPosition(), dataWisesentences);
//                        Log.v("배경화면 문구 추가 adapter 알림","ListArray에 있는 데이터를 변경하고");
//
//                        // 해당 포지션에 있는 아이템이 바뀌게 한다.
//                        // 9. 어댑터에서 RecyclerView에 반영하도록 합니다.
//                        notifyItemChanged(getAdapterPosition());
//                        Log.v("명심할 것 adapter 확인 알림 로그","어댑터에서 RecyclerView에 반영하도록 합니다.");



                        break;

                    case 1002: // 5. 삭제 항목을 선택 시


                        Log.v("목표관리 adapter 확인 알림 로그","삭제 항목을 선택 시");
//                        Toast.makeText((mGoalContext), "position 값 : " + getAdapterPosition(), Toast.LENGTH_SHORT).show();

                        // 삭제할 때 &&&로 잘라준 값들을 아래 배열에 담아준다.
                        // String[] splitReceiveGoalinfo
                        splitReceiveGoalinfo = receive_goallists.split("&&&");
                        Log.v("목표관리 adapter 확인 알림 로그","splitReceiveinfo : " + splitReceiveGoalinfo);

                        for(int i = 0; i < splitReceiveGoalinfo.length; i++){

                            String checksplit = splitReceiveGoalinfo[i];
                            Log.v("목표관리 adapter checksplit 확인 알림 로그","checksplit "  +  checksplit);
                            Log.v("목표관리 adapter splitReceiveGoalinfo.length확인 알림 로그","splitReceiveGoalinfo.length "  +  splitReceiveGoalinfo.length);

                        }

                        // 유저정보가 해당 아이디를 포함하고 있다면
                        if(userinfo.contains(useridString)){

                            // 맨 처음에 저장된 값은 맨 밑에 있다. 이 아이템의 포지션은 arraylist의 길이에서 1을 빼준 값과 같다.
                            // 고로 맨 처음에 저장된 값, 맨 밑에 있는 값을 삭제하려면
                            if(getAdapterPosition() == mGoalList.size() -1){

                                Toast.makeText((mGoalContext), "삭제되었습니다.", Toast.LENGTH_SHORT).show();

                                // 남아있는 값이 한 개인지 여러 개인지 판단은 어떻게? >
                                // 남아있는 값이 한 개일 때 삭제할 경우 >> &&& 이건 빼고 그냥 값을 "" 공백으로 처리
                                if(mGoalList.size() == 1){

                                    // 삭제하려는 값을 삭제는 못하고 대신 &&& + 해당 인덱스에 있는 값을 공백으로 바꿔준다. 다만, 현재는 첫번째값을 삭제하면 안된다.
                                    String deletegoalcontent = receive_goallists.replace(splitReceiveGoalinfo[splitReceiveGoalinfo.length - 1 - getAdapterPosition()],"");
                                    Log.v("목표관리 adapter deleteContentsInfo 확인 알림 로그", "" + deletegoalcontent);

                                    goalManagementEditor.putString(useridString, deletegoalcontent);

                                    // 파일에 최종 반영함
                                    goalManagementEditor.commit();

                                    // 6. ArraylistT에서 해당 데이터를 삭제
                                    Log.v("목표관리 adapter 확인 알림 로그","ArraylistT에서 해당 데이터를 삭제");
                                    // 해당 포지션에 있는 값을 제거한다.
                                    mGoalList.remove(getAdapterPosition());
                                    Log.v("목표관리 adapter getAdapterPosition() 확인 알림 로그","getAdapterPosition()" + getAdapterPosition());

                                    // 7. 어댑터에서 recyclerview에 반영하도록 한다.
                                    notifyItemRemoved(getAdapterPosition()); // 특정 Position에 데이터를 하나 제거할 경우.
                                    notifyItemRangeChanged(getAdapterPosition(), mGoalList.size()); // 특정 영역을 데이터가 바뀌었을 경우. position 3~10번까지의 데이터만 바뀌었을 경우 사용 하면 된다.

                                    Log.v("목표관리 adapter 확인 알림 로그","어댑터에서 recyclerview에 반영하도록 한다.");

                                    notifyDataSetChanged();

                                    break;

                                    // 남아있는 값이 여러 개일때 삭제할 경우 >> &&&까지 target으로 정해서 함께 "" 공백 처리
                                } else {

                                    // 삭제하려는 값을 삭제는 못하고 대신 &&& + 해당 인덱스에 있는 값을 공백으로 바꿔준다. 다만, 현재는 첫번째값을 삭제하면 안된다.
                                    String deletegoalcontent = receive_goallists.replace(splitReceiveGoalinfo[splitReceiveGoalinfo.length - 1 - getAdapterPosition()] + "&&&","");
                                    Log.v("목표관리 adapter deleteContentsInfo 확인 알림 로그", "" + deletegoalcontent);

                                    goalManagementEditor.putString(useridString, deletegoalcontent);

                                    // 파일에 최종 반영함
                                    goalManagementEditor.commit();

                                    // 6. ArraylistT에서 해당 데이터를 삭제
                                    Log.v("목표관리 adapter 확인 알림 로그","ArraylistT에서 해당 데이터를 삭제");
                                    // 해당 포지션에 있는 값을 제거한다.
                                    mGoalList.remove(getAdapterPosition());
                                    Log.v("목표관리 adapter getAdapterPosition() 확인 알림 로그","getAdapterPosition()" + getAdapterPosition());

                                    // 7. 어댑터에서 recyclerview에 반영하도록 한다.
                                    notifyItemRemoved(getAdapterPosition()); // 특정 Position에 데이터를 하나 제거할 경우.
                                    notifyItemRangeChanged(getAdapterPosition(), mGoalList.size()); // 특정 영역을 데이터가 바뀌었을 경우. position 3~10번까지의 데이터만 바뀌었을 경우 사용 하면 된다.

                                    Log.v("목표관리 adapter 확인 알림 로그","어댑터에서 recyclerview에 반영하도록 한다.");

                                    notifyDataSetChanged();

                                    break;

                                }

                                // 그 외에 포지션에 위치한 값을 삭제하려면.
                            } else {

                                // 삭제하려는 값을 삭제는 못하고 대신 &&& + 해당 인덱스에 있는 값을 공백으로 바꿔준다. 다만, 현재는 첫번째값을 삭제하면 안된다.
                                String deletegoalcontent = receive_goallists.replace("&&&" + splitReceiveGoalinfo[splitReceiveGoalinfo.length - 1 - getAdapterPosition()],"");
                                Log.v("keep adapter deleteContentsInfo 확인 알림 로그", "" + deletegoalcontent);

                                goalManagementEditor.putString(useridString, deletegoalcontent);

                                // 파일에 최종 반영함
                                goalManagementEditor.commit();

                                // 6. ArraylistT에서 해당 데이터를 삭제
                                Log.v("목표관리 adapter 확인 알림 로그","ArraylistT에서 해당 데이터를 삭제");
                                // 해당 포지션에 있는 값을 제거한다.
                                mGoalList.remove(getAdapterPosition());
                                Log.v("목표관리 adapter getAdapterPosition() 확인 알림 로그","getAdapterPosition()" + getAdapterPosition());

                                // 7. 어댑터에서 recyclerview에 반영하도록 한다.
                                notifyItemRemoved(getAdapterPosition()); // 특정 Position에 데이터를 하나 제거할 경우.
                                notifyItemRangeChanged(getAdapterPosition(), mGoalList.size()); // 특정 영역을 데이터가 바뀌었을 경우. position 3~10번까지의 데이터만 바뀌었을 경우 사용 하면 된다.

                                Log.v("목표관리 adapter 확인 알림 로그","어댑터에서 recyclerview에 반영하도록 한다.");

                                notifyDataSetChanged();

                                break;

                            }

                        }

                }
                return true;
            }
        };

    }

    @NonNull
    @Override
    public goalviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goal, parent,false);

        goalviewholder goalvh = new goalviewholder(view);

        return goalvh;

    }

//    private static void setStrikethrough(TextView textView, boolean strikethrough) {
//        int flags = textView.getPaintFlags();
//        if (strikethrough) {
//            flags |= Paint.STRIKE_THRU_TEXT_FLAG;
//        } else {
//            flags &= (~Paint.STRIKE_THRU_TEXT_FLAG);
//        }
//        textView.setPaintFlags(flags);
//    }



    @Override
    public void onBindViewHolder(@NonNull goalviewholder holder, int position) {

        DataGoalmanagement dataGoalmanagement = mGoalList.get(position);

        holder.goal.setGravity(Gravity.CENTER);

        holder.goal.setText(mGoalList.get(position).getConcentrationGoal());
        Log.v("mGoalList.get(position).getConcentrationGoal() 확인 알림 로그 ㅈㅂ", mGoalList.get(position).getConcentrationGoal());

        // https://github.com/avh4/outline-android/blob/master/app/src/main/java/net/avh4/outline/OutlineAdapter.java
        Boolean goalcheck = mGoalList.get(position).isGoalcheck();
        Log.v("goalcheck 확인 알림 로그 ㅈㅂ", "goalcheck  " + goalcheck);

        int flags = holder.goal.getPaintFlags();

        if (goalcheck) {
            flags |= Paint.STRIKE_THRU_TEXT_FLAG;
            Log.v("goalcheck 확인 알림 로그 ㅈㅂ", "goalcheck 2  " + goalcheck);

        } else {
            Log.v("goalcheck 확인 알림 로그 ㅈㅂ", "goalcheck 3  " + goalcheck);

            flags &= (~Paint.STRIKE_THRU_TEXT_FLAG);
        }

        holder.goal.setPaintFlags(flags);

//        setStrikethrough(holder.goal, mGoalList.get(position).isGoalcheck());

//        mGoalList.get(position).isGoalcheck();
//
//        Boolean goalcheck = mGoalList.get(position).isGoalcheck();
//
//        if(goalcheck == true){
//            holder.goal.setPaintFlags(holder.goal.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
//        }


//        boolean goalcheck2 = Boolean.parseBoolean(splitReceiveGoalinfo[splitReceiveGoalinfo.length -1 - position].split("###")[1]);
//
//        if(goalcheck2 = true ){
//            holder.goal.setPaintFlags(holder.goal.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
//        }

    }

    @Override
    public int getItemCount() {
        Log.v("adapter 알림","getItemCount");

        return (null != mGoalList ? mGoalList.size() : 0);    }


}
