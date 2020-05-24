package com.impact.mycando.recyclerview_dreamboard;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.impact.mycando.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class AdapterDreamboard extends RecyclerView.Adapter<AdapterDreamboard.ViewHolder> {

    // context 이용하기 위해서
    private Context mdreamboardContext;
    // 데이터 추가 수정 삭제 위해
    private ArrayList<DataDreamboard> dataDreamboardArrayList;
    // 수정하기 위해 넘겨주는 REQUEST 값
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

    String receive_dreamcontentsInfo;

    // 어댑터 생성자 느낌
    AdapterDreamboard(ArrayList<DataDreamboard> dreamboardArrayList,DreamboxActivity Dreamboxactivity){
        dataDreamboardArrayList = dreamboardArrayList;
        mdreamboardContext = Dreamboxactivity;
    }

    // 뷰홀더 - 아이템 뷰를 저장하는 뷰홀더 클래스
    // 리스트 항목 하나의 view를 만들고 보존하는 일을 한다
    // 리사이클러뷰에 들어갈 뷰 홀더, 그리고 그 뷰홀더에 들어갈 아이템뷰들을 지정
    public class ViewHolder extends RecyclerView.ViewHolder {

        // 꿈 텍스트뷰
        TextView tv_dream;
//        // 꿈 설명 텍스트뷰 - 아이템뷰에 없음
//        TextView tv_dreamexpalin;

        // 아이템 클릭
        View clickView;
        // 옵션 버튼
        ImageButton btn_option;

        // 꿈 이미지
        ImageView iv_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // 꿈 참조
            tv_dream = itemView.findViewById(R.id.item_dream);
//            // 꿈 설명 참조 - 아이템뷰에 없음
//            this.tv_dreamexpalin = itemView.findViewById(R.id.)
            // 이 아이템 클릭 시 이벤트 처리해주기 위해서 itemview 참조
            clickView = itemView;
            // 옵션 버튼
            btn_option = itemView.findViewById(R.id.item_option);

            // 꿈 이미지
            iv_image = itemView.findViewById(R.id.item_image);

        }
    }

    // 뷰홀더 객체 생성
    @NonNull
    @Override
    public AdapterDreamboard.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_box, parent, false) ;
        AdapterDreamboard.ViewHolder vh = new AdapterDreamboard.ViewHolder(view) ;
        //layout inflater 선언 - recyclerview_item.xml 파일을 객체로 만들어서 쓸 수 있게

        Log.v("feed adapter 알림", "recyclerview는 adapter에게 viewholder 객체를 받는다.");

        return vh;
    }

    DataDreamboard dataitem;

    // 아이템 - 데이터 연결시켜줌
    @Override
    public void onBindViewHolder(@NonNull AdapterDreamboard.ViewHolder holder, final int position) {

        // (?) 해당 위치에 있는 어레이리스트 인덱스를 데이터클래스 객체에 값을 받아준다.
        dataitem = dataDreamboardArrayList.get(position);
        Log.v("onbindviewholder 확인 로그 알림"," dataitem : " + dataitem +  "  dataDreamboardArrayList.size()  " + dataDreamboardArrayList.size() + " position " + position  );

        for(int i = 0 ; i < dataDreamboardArrayList.size() ; i++){
            Log.v("AdapterDreamboard 어레이리스트 값 확인하기 확인 로그 알림","dataDreamboardArrayList 안에 있는 값 확인하기 " + "list[" + i + "] = " + dataDreamboardArrayList.get(i) + "  이미지 확인 " + dataDreamboardArrayList.get(i).getDreamimageUriStr());
            Log.v("AdapterDreamboard 어레이리스트 이미지 확인 확인 로그 알림", " 이미지 확인 " + "dataDreamboardArrayList[" + i + "] = " + dataDreamboardArrayList.get(i).getDreamimageUriStr());
            Log.v("AdapterDreamboard 어레이리스트 텍스트 확인 확인 로그 알림", " 텍스트 확인 " + "dataDreamboardArrayList[" + i + "] = " + dataDreamboardArrayList.get(i).getDreamnameStr());
        }

        // 아이템에 있는 tv_dream 텍스트뷰에 입력한 꿈을 settext해준다.
        holder.tv_dream.setText(dataitem.getDreamnameStr());
        Log.v("onbindviewholder 확인 로그 알림"," dataitem.getDreamnameStr() : " + dataitem.getDreamnameStr() );

        // 아이템에 있는 iv_image 이미지뷰에 이미지를 uri로 set해준다.
//        if(Uri.parse(dataitem.getDreamimageUriStr()) != null){
        holder.iv_image.setImageURI(Uri.parse(dataitem.getDreamimageUriStr()));
        Log.v("AdapterDreamboard uri 확인 알림 로그 "," Uri.parse(dataitem.getDreamimageUriStr()) : " + Uri.parse(dataitem.getDreamimageUriStr()));
//        }


        // 아이템클릭이벤트를 처리해주기 위해
        holder.clickView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
//                Toast.makeText(context, position +"", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(mdreamboardContext,DreamitemclickActivity.class);
                // 해당 포지션에 맞는 꿈 넘겨주기
                // dataitem = dataDreamboardArraylist.get(position)
                intent.putExtra("click_dreamname", dataDreamboardArrayList.get(position).getDreamnameStr());
                Log.v("AdapterDreamboard 확인 알림 로그 ", " dataDreamboardArrayList.get(position).dreamnameStr : " + dataDreamboardArrayList.get(position).getDreamnameStr() + "  dataitem.getDreamnameStr() : " + dataitem.getDreamnameStr());

                // 꿈 설명 넘겨주기
                intent.putExtra("click_dreamexplain", dataDreamboardArrayList.get(position).getDreamexplainStr());
                Log.v("AdapterDreamboard 확인 알림 로그 ", " dataDreamboardArrayList.get(position).getDreamexplainStr() : " + dataDreamboardArrayList.get(position).getDreamexplainStr());

                // 꿈 이미지 넘겨주기
                intent.putExtra("click_dreamimage", dataDreamboardArrayList.get(position).getDreamimageUriStr());
                Log.v("AdapterDreamboard 확인 알림 로그 ", " dataDreamboardArrayList.get(position).getDreamimageUriStr() : " + dataDreamboardArrayList.get(position).getDreamimageUriStr());

                // 클릭했을 때 나오는 화면으로 position 값을 넘겨준다.
                intent.putExtra("click_position", position);
                Log.v("AdapterDreamboard 확인 알림 로그 ", " position : " + position);

//                // 이미지 클릭 화면에 position 값을 넘겨준다.
//                intent.putExtra("contents_position", position);

                mdreamboardContext.startActivity(intent);
            }
        });


        // 로그인했을 때 같이 넘겨준 ID
        // SharedPreferences userid
        userid = mdreamboardContext.getSharedPreferences("useridFile", MODE_PRIVATE);
        // 로그인할 때 가져온 id가 useridString이다.
        // String useridString
        useridString = userid.getString("userid", "");

        // 회원가입한 정보 shared 객체를 가져와 닉네임을 불러와준다.
        userinfo = mdreamboardContext.getSharedPreferences("userinfoFile", MODE_PRIVATE);

        // 추가 버튼을 눌렀을 때 저장되어서 화면에 있는 리사이클러뷰에 불러와져야함.
        // 그러기 위해 shared 파일을 하나 더 만들어줌
        // SharedPreferences UserdreamPreffile
        UserdreamPreffile = mdreamboardContext.getSharedPreferences("UserdreamPrefFile", 0);

        // editor라는게 필요해서 만들어줬다.
        // SharedPreferences.Editor UserdreamPrefEditor
        UserdreamPrefEditor = UserdreamPreffile.edit();

        // String receive_dreamcontentsInfo
        receive_dreamcontentsInfo = UserdreamPreffile.getString(useridString, "");

        // 수정, 삭제 옵션 주기 위한 버튼
        holder.btn_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String option[] = {"수정", "삭제"};

                // 어댑터에선 activity를 다룰 수 없다.
                // context를 활용해 어댑터에서 activity를 이용할 수 있게.
                AlertDialog.Builder builder = new AlertDialog.Builder(mdreamboardContext);

                // 제목 설정
                builder.setTitle("드림보드");

                // setitems : 표시할 항목의 배열과 Onclicklistener를 매개 변수로 받는다.
                // onclicklistener는 사용자가 항목을 선택하는 경우에 실행되는 동작을 정의
                builder.setItems(option, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int choose) {

                        if(choose == 0){

                            // 0번 수정을 클릭하면 글쓰기 화면으로 들어가서 입력했던 값을 수정할 수 있도록
                            Intent intent = new Intent(mdreamboardContext,EditdreamActivity.class);

                            // 수정을 눌렀을 때 해당 포지션에 있는 꿈 (값)을 넘겨준다.
                            intent.putExtra("EditDreamname", dataDreamboardArrayList.get(position).getDreamnameStr()); // holder.title.getText(); 대신에 mData.get(position).getTitleStr())
//                            Toast.makeText(mdreamboardContext, "수정할 포스팅 위치값 : " + position, Toast.LENGTH_SHORT).show();
                            Log.v("Dreamboard adapter 수정 확인 알림 로그","" + dataDreamboardArrayList.get(position).getDreamnameStr());

                            // 수정 눌렀을 때 해당 포지션에 있는 꿈 설명 (값)을 넘겨준다.
                            intent.putExtra("EditDreamexplain", dataDreamboardArrayList.get(position).getDreamexplainStr()); // holder.title.getText(); 대신에 mData.get(position).getTitleStr())
                            Log.v("Dreamboard adapter 수정 확인 알림 로그","" + dataDreamboardArrayList.get(position).getDreamexplainStr());

                            // 수정 눌렀을 때 해당 포지션에 있는 꿈 이미지 uri 넘겨주기
                            intent.putExtra("EditDreamimage", dataDreamboardArrayList.get(position).getDreamimageUriStr());
                            Log.v("Dreamboard adapter 수정 확인 알림 로그 ", " dataDreamboardArrayList.get(position).getDreamimageUriStr() : " + dataDreamboardArrayList.get(position).getDreamimageUriStr());

                            // "EditDream_position"이라는 key와 위치값을 전달해준다.
                            intent.putExtra("EditDream_position", position);
                            Log.v("Dreamboard adapter 수정 확인 알림 로그"," " + position);

                            // adapter에서 startActivityforresult를 호출하는 방식이 조금 다르다.
                            ((Activity) mdreamboardContext).startActivityForResult(intent, REQ_EDIT_DREAM); // private static final int REQ_EDIT_DREAM = 2

                        }else{

                            String[] splitReceivedreaminfo = receive_dreamcontentsInfo.split("&&&&&&");

                            // 맨 처음에 저장된 값은 맨 밑에 있다. 이 아이템의 포지션은 arraylist의 길이에서 1을 빼준 값과 같다.
                            // 고로 맨 처음에 저장된 값, 맨 밑에 있는 값을 삭제하려면
                            if(position == dataDreamboardArrayList.size() -1){

//                                Toast.makeText((mdreamboardContext), "position 값 : " + position, Toast.LENGTH_SHORT).show();

                                // 남아있는 값이 한 개인지 여러 개인지 판단은 어떻게? >
                                // 남아있는 값이 한 개일 때 삭제할 경우 >> &&&&&& 이건 빼고 그냥 값을 "" 공백으로 처리
                                if(dataDreamboardArrayList.size() == 1){

                                    // 삭제하려는 값을 삭제는 못하고 대신 &&&&&& + 해당 인덱스에 있는 값을 공백으로 바꿔준다. 다만, 현재는 첫번째값을 삭제하면 안된다.
                                    String deleteDreamcontent = receive_dreamcontentsInfo.replace(splitReceivedreaminfo[splitReceivedreaminfo.length - 1 - position],"");

                                    UserdreamPrefEditor.putString(useridString, deleteDreamcontent);

                                    // 파일에 최종 반영함
                                    UserdreamPrefEditor.commit();

                                    // 6. ArraylistT에서 해당 데이터를 삭제
                                    // 해당 포지션에 있는 값을 제거한다.
                                    dataDreamboardArrayList.remove(position);

                                    // 7. 어댑터에서 recyclerview에 반영하도록 한다.
                                    notifyItemRemoved(position); // 특정 Position에 데이터를 하나 제거할 경우.
                                    notifyItemRangeChanged(position, dataDreamboardArrayList.size()); // 특정 영역을 데이터가 바뀌었을 경우. position 3~10번까지의 데이터만 바뀌었을 경우 사용 하면 된다.
                                    Toast.makeText(mdreamboardContext, "삭제되었습니다.", Toast.LENGTH_SHORT).show();

                                    notifyDataSetChanged();

                                    // 남아있는 값이 여러 개일때 삭제할 경우 >> &&&까지 target으로 정해서 함께 "" 공백 처리
                                } else {

                                    // 삭제하려는 값을 삭제는 못하고 대신 &&&&&& + 해당 인덱스에 있는 값을 공백으로 바꿔준다. 다만, 현재는 첫번째값을 삭제하면 안된다.
                                    String deleteDreamcontent = receive_dreamcontentsInfo.replace(splitReceivedreaminfo[splitReceivedreaminfo.length - 1 - position] + "&&&&&&","");

                                    UserdreamPrefEditor.putString(useridString, deleteDreamcontent);

                                    // 파일에 최종 반영함
                                    UserdreamPrefEditor.commit();

                                    // 6. ArraylistT에서 해당 데이터를 삭제
                                    // 해당 포지션에 있는 값을 제거한다.
                                    dataDreamboardArrayList.remove(position);

                                    // 7. 어댑터에서 recyclerview에 반영하도록 한다.
                                    notifyItemRemoved(position); // 특정 Position에 데이터를 하나 제거할 경우.
                                    notifyItemRangeChanged(position, dataDreamboardArrayList.size()); // 특정 영역을 데이터가 바뀌었을 경우. position 3~10번까지의 데이터만 바뀌었을 경우 사용 하면 된다.
                                    Toast.makeText(mdreamboardContext, "삭제되었습니다.", Toast.LENGTH_SHORT).show();

                                    notifyDataSetChanged();

                                }

                                // 그 외에 포지션에 위치한 값을 삭제하려면.
                            } else {

                                // 삭제하려는 값을 삭제는 못하고 대신 &&&&&& + 해당 인덱스에 있는 값을 공백으로 바꿔준다. 다만, 현재는 첫번째값을 삭제하면 안된다.
                                String deleteDreamcontent = receive_dreamcontentsInfo.replace("&&&&&&" + splitReceivedreaminfo[splitReceivedreaminfo.length - 1 - position],"");

                                UserdreamPrefEditor.putString(useridString, deleteDreamcontent);

                                // 파일에 최종 반영함
                                UserdreamPrefEditor.commit();

                                // 6. ArraylistT에서 해당 데이터를 삭제
                                // 해당 포지션에 있는 값을 제거한다.
                                dataDreamboardArrayList.remove(position);

                                // 7. 어댑터에서 recyclerview에 반영하도록 한다.
                                notifyItemRemoved(position); // 특정 Position에 데이터를 하나 제거할 경우.
                                notifyItemRangeChanged(position, dataDreamboardArrayList.size()); // 특정 영역을 데이터가 바뀌었을 경우. position 3~10번까지의 데이터만 바뀌었을 경우 사용 하면 된다.
                                Toast.makeText(mdreamboardContext, "삭제되었습니다.", Toast.LENGTH_SHORT).show();

                                notifyDataSetChanged();

                            }

//                            // 나머지 삭제를 클릭하면 그냥 삭제되도록
//                            // 해당 position값을 삭제하고
//                            dataDreamboardArrayList.remove(position);
//                            Log.v("AdapterDreamBoard 삭제 알림 로그","삭제할 때 position 제대로 받는지 확인 " + position + " dataDreamboardArrayList.size " + dataDreamboardArrayList.size() );
//
//                            // 특정 영역의 데이터를 제거할 경우, 특정 Position에 데이터를 하나 제거할 경우
//                            notifyItemRemoved(position);
//                            // https://gogorchg.tistory.com/entry/Android-RecyclerView-Adpater-Refresh  // position을 0을 받아야 하는데 1로 받고 있음 // 왜? // notifyitemremoved도 찾아보고 arraylist도 다시 찾아봤는데 이쪽은 아닌 것 같음
//                            // (null != dataDreamboardArrayList ? dataDreamboardArrayList.size() : 0); 이거 문제인가? // 이것도 dataDreamboardArrayList.size()
//
//                            Toast.makeText(mdreamboardContext, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
//
//                            // 삭제하고 나서 아이템 포지션이 제대로 안받는 이유가 notifydatasetchanged 갱신을 안해줘서였음 ;;
//                            notifyDataSetChanged();

                        }

                    }
                });

                builder.show(); // 알림창 띄우기

            }
        });

    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        // ? : 삼항연산자
        return (null != dataDreamboardArrayList ? dataDreamboardArrayList.size() : 0); // dataDreamboardArrayList.size()
    }



//    삼항연산자의 형식
//   (boolean) ? c(true일경우) : d(false일경우)
//    boolean 조건이 참(true)일 경우 c
//    boolean 조건이 거짓(false)일 경우 d


}


