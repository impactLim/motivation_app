package com.impact.mycando.recyclerview_will;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.impact.mycando.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class AdapterWill extends RecyclerView.Adapter<AdapterWill.ViewHolder> {

    // context 권한 필요
    private Context mwillContext;
    private ArrayList<DataWill> dataWillArrayList;
    private static final int REQ_EDIT_WILL = 2;


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


    // 어댑터 생성자 - 리사이클러뷰의 데이터들이 담길 리스트 초기화
    AdapterWill(ArrayList<DataWill> datawillArraylist,WillActivity willActivity )
    {
        dataWillArrayList = datawillArraylist;
        mwillContext = willActivity;
        Log.v("will adapter 확인 알림 로그","어댑터 생성자");
    }

    // 뷰홀더
    public class ViewHolder extends RecyclerView.ViewHolder {

        // 리사이클러뷰에 필요한 것들 선언
        // 오늘의 각오
        TextView tv_willcontents;
        // 날짜
        TextView dateNow;
        // 아이템뷰 클릭 시
        View clickView;
        // 옵션 버튼
        ImageButton btn_option;
        // 이미지
        ImageView iv_willimage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // 리사이클러뷰에 보여질 아이템 뷰들을 참조해준다.
            // 오늘의 각오
            tv_willcontents = itemView.findViewById(R.id.item_mywill);
            // 날짜
            dateNow = itemView.findViewById(R.id.item_willdate);
            // 아이템 클릭
            clickView = itemView;
            //옵션 버튼
            btn_option = itemView.findViewById(R.id.item_option);
            // 이미지
            iv_willimage = itemView.findViewById(R.id.will_imageview);
            Log.v("will adapter 확인 알림 로그","뷰홀더 아이템뷰 참조");

        }

    }

    // 이 아이템 뷰에 한 컬럼 당 들어갈 에이아웃이 객체화 되어서 들어갈 것임
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // 레이아웃 인플레이터 서비스를 사용하기 위해 context 가져옴 - 시스템 정보들에 대한 권한
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 한 컬럼에 들어갈 레이아웃을 객체화시켜서 view에 담는다.
        View view = inflater.inflate(R.layout.item_will2, parent, false) ;
        // 객체화 시킨 걸 뷰홀더에 담아준다.
        AdapterWill.ViewHolder vh = new AdapterWill.ViewHolder(view) ;
        Log.v("will adapter 확인 알림 로그","oncreateviewholder 알림");

        return vh;
    }

    // 리스트에 있는 데이터와 리사이클러뷰 연결
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        DataWill item = dataWillArrayList.get(position);

        // 데이터와 리사이클러뷰 아이템 연결시켜줌
        holder.tv_willcontents.setText(item.getWillContentsStr());
        holder.dateNow.setText(dataWillArrayList.get(position).getDateStr());
        holder.iv_willimage.setImageURI(Uri.parse(item.getWillimageStr()));
//        holder.iv_image.setImageURI(Uri.parse(dataitem.getDreamimageUriStr()));

        Log.v("will adapter 확인 알림 로그","onbindviewholder 알림");

        // 아이템클릭이벤트를 처리해주기 위해
        holder.clickView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Context context = v.getContext();
//                Toast.makeText(context, position +"", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(mwillContext, WillclickActivity.class);

                // 해당 포지션에 맞는 오늘의 각오 넘겨주기 - willclickactivity로
                // dataitem = dataDreamboardArraylist.get(position)
                intent.putExtra("click_willcontents", dataWillArrayList.get(position).getWillContentsStr());
                Log.v("AdapterWill 확인 알림 로그 ", " dataWillArrayList.get(position).getWillContentsStr() : " + dataWillArrayList.get(position).getWillContentsStr());

                // 목표 작성한 거 보내준다
                intent.putExtra("click_willgoal", dataWillArrayList.get(position).getWillGoalStr());
                Log.v("AdapterWill 확인 알림 로그 ", " dataWillArrayList.get(position).getWillGoalStr() : " + dataWillArrayList.get(position).getWillGoalStr());

                // 목표 이미지 보내준다.
                intent.putExtra("click_image", dataWillArrayList.get(position).getWillimageStr());
                Log.v("AdapterWill 확인 알림 로그 ", " dataWillArrayList.get(position).getWillimageStr() : " + dataWillArrayList.get(position).getWillimageStr());

                mwillContext.startActivity(intent);

            }
        });



        // 로그인했을 때 같이 넘겨준 ID
        // SharedPreferences userid
        userid = mwillContext.getSharedPreferences("useridFile", MODE_PRIVATE);
        // 로그인할 때 가져온 id가 useridString이다.
        // String useridString
        useridString = userid.getString("userid", "");

        // 회원가입한 정보 shared 객체를 가져와 닉네임을 불러와준다.
        userinfo = mwillContext.getSharedPreferences("userinfoFile", MODE_PRIVATE);

        // 추가 버튼을 눌렀을 때 저장되어서 화면에 있는 리사이클러뷰에 불러와져야함.
        // 그러기 위해 shared 파일을 하나 더 만들어줌
        // SharedPreferences  UserWillPreffile
        UserWillPreffile = mwillContext.getSharedPreferences("UserwillPrefFile", 0);

        // editor라는게 필요해서 만들어줬다.
        // SharedPreferences.Editor UserdreamPrefEditor
        UserWillPrefEditor = UserWillPreffile.edit();

        // String receive_willcontentsInfo
        receive_willcontentsInfo = UserWillPreffile.getString(useridString, "");

        // 수정, 삭제 옵션 주기 위한 버튼
        holder.btn_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String option[] = {"수정", "삭제"};

                // 어댑터에선 activity를 다룰 수 없다.
                // context를 활용해 어댑터에서 activity를 이용할 수 있게.
                AlertDialog.Builder Willbuilder = new AlertDialog.Builder(mwillContext);

                // 제목 설정
                Willbuilder.setTitle("오늘의 각오");

                // setitems : 표시할 항목의 배열과 Onclicklistener를 매개 변수로 받는다.
                // onclicklistener는 사용자가 항목을 선택하는 경우에 실행되는 동작을 정의
                Willbuilder.setItems(option, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int choose) {

                        if(choose == 0){

                            // 0번 수정을 클릭하면 글쓰기 화면으로 들어가서 입력했던 값을 수정할 수 있도록
                            Intent intent = new Intent(mwillContext, WilleditActivity.class);

                            // 수정을 눌렀을 때 해당 포지션에 있는 오늘의 각오를 넘겨준다.
                            intent.putExtra("Edit_willcontents", dataWillArrayList.get(position).getWillContentsStr());
//                            Toast.makeText(mwillContext, "수정할 포스팅 위치값 : " + position, Toast.LENGTH_SHORT).show();
                            Log.v("Will adapter 수정 확인 알림 로그"," Edit_willcontents : " + dataWillArrayList.get(position).getWillContentsStr());

                            // 목표를 넘겨준다.
                            intent.putExtra("Edit_willgoal", dataWillArrayList.get(position).getWillGoalStr());
                            Log.v("Will adapter 수정 확인 알림 로그"," Edit_willgoal : " + dataWillArrayList.get(position).getWillGoalStr());

                            // 이미지를 넘겨준다.
                            intent.putExtra("Edit_willimage", dataWillArrayList.get(position).getWillimageStr());
                            Log.v("Will adapter 수정 확인 알림 로그 ", " dataWillArrayList.get(position).getWillimageStr(): " + dataWillArrayList.get(position).getWillimageStr());

                            // 포지션을 넘겨준다.
                            intent.putExtra("Edit_willposition", position);
                            Log.v("Will adapter 수정 확인 알림 로그 ", " Edit_willposition " + position);

                            // adapter에서 startActivityforresult를 호출하는 방식이 조금 다르다.
                            ((Activity) mwillContext).startActivityForResult(intent, REQ_EDIT_WILL); // private static final int REQ_EDIT_DREAM = 2

                        }else{

                            String[] splitReceiveWillinfo = receive_willcontentsInfo.split("&&&&&&");

                            // 맨 처음에 저장된 값은 맨 밑에 있다. 이 아이템의 포지션은 arraylist의 길이에서 1을 빼준 값과 같다.
                            // 고로 맨 처음에 저장된 값, 맨 밑에 있는 값을 삭제하려면
                            if(position == dataWillArrayList.size() -1){

//                                Toast.makeText((mwillContext), "position 값 : " + position, Toast.LENGTH_SHORT).show();

                                // 남아있는 값이 한 개인지 여러 개인지 판단은 어떻게? >
                                // 남아있는 값이 한 개일 때 삭제할 경우 >> &&&&&& 이건 빼고 그냥 값을 "" 공백으로 처리
                                if(dataWillArrayList.size() == 1){

                                    // 삭제하려는 값을 삭제는 못하고 대신 &&&&&& + 해당 인덱스에 있는 값을 공백으로 바꿔준다. 다만, 현재는 첫번째값을 삭제하면 안된다.
                                    String deleteWillcontent = receive_willcontentsInfo.replace(splitReceiveWillinfo[splitReceiveWillinfo.length - 1 - position],"");

                                    UserWillPrefEditor.putString(useridString, deleteWillcontent);

                                    // 파일에 최종 반영함
                                    UserWillPrefEditor.commit();

                                    // 남아있는 값이 여러 개일때 삭제할 경우 >> &&&까지 target으로 정해서 함께 "" 공백 처리
                                } else {

                                    // 삭제하려는 값을 삭제는 못하고 대신 &&&&&& + 해당 인덱스에 있는 값을 공백으로 바꿔준다. 다만, 현재는 첫번째값을 삭제하면 안된다.
                                    String deleteWillcontent = receive_willcontentsInfo.replace(splitReceiveWillinfo[splitReceiveWillinfo.length - 1 - position] + "&&&&&&","");

                                    UserWillPrefEditor.putString(useridString, deleteWillcontent);

                                    // 파일에 최종 반영함
                                    UserWillPrefEditor.commit();

                                }

                                // 그 외에 포지션에 위치한 값을 삭제하려면.
                            } else {

                                // 삭제하려는 값을 삭제는 못하고 대신 &&&&&& + 해당 인덱스에 있는 값을 공백으로 바꿔준다. 다만, 현재는 첫번째값을 삭제하면 안된다.
                                String deleteWillcontent = receive_willcontentsInfo.replace("&&&&&&" + splitReceiveWillinfo[splitReceiveWillinfo.length - 1 - position],"");

                                UserWillPrefEditor.putString(useridString, deleteWillcontent);

                                // 파일에 최종 반영함
                                UserWillPrefEditor.commit();

                            }


                            // 나머지 삭제를 클릭하면 그냥 삭제되도록
                            // 해당 position값을 삭제하고
                            dataWillArrayList.remove(position);
                            Log.v("Will adapter 삭제 알림 로그","삭제할 때 position 제대로 받는지 확인 " + position + " dataWillArrayList.size " + dataWillArrayList.size() );

                            // 특정 영역의 데이터를 제거할 경우, 특정 Position에 데이터를 하나 제거할 경우
                            notifyItemRemoved(position);
                            // https://gogorchg.tistory.com/entry/Android-RecyclerView-Adpater-Refresh

//                            Toast.makeText(mwillContext, "삭제되었습니다.", Toast.LENGTH_SHORT).show();

                            // 삭제하고 나서 아이템 포지션이 제대로 안받는 이유가 notifydatasetchanged 갱신을 안해줘서였음 ;;
                            notifyDataSetChanged();

                        }

                    }
                });

                Willbuilder.show(); // 알림창 띄우기

            }
        });

    }

    // 전체 데이터 갯수 반환
    @Override
    public int getItemCount() {
        Log.v("will adapter 확인 알림 로그"," getItemCount() 알림");
        return (null != dataWillArrayList ? dataWillArrayList.size() : 0);
    }

    // arraylist가 필터가 되는 filterlist 메쏘드
    public void filterList(ArrayList<DataWill> filteredList) {

        // 필터가 된 arraylist
        dataWillArrayList = filteredList;
        notifyDataSetChanged();

    }

}
