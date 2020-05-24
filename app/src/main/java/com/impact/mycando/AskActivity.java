package com.impact.mycando;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class AskActivity extends AppCompatActivity implements OnMapReadyCallback {

    //이메일
    Button btn_sendemail;
    //문자
    Button btn_sendmessage;
    //전화
    Button btn_call;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask);

        // 뒤로가기 버튼을 누르면 로그인 화면으로 감
        Button story_btn = (Button) findViewById(R.id.story_btn);
        story_btn.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent(getApplicationContext(), Helpyoutube.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );

        // SupportMapFragment는 Fragment의 서브 클래스로, Fragment에 지도를 배치할 수 있도록 해준다.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // 이메일 버튼을 누르면 이메일 쓰기 화면에 들어갈 수 있다.
        btn_sendemail = (Button ) findViewById(R.id.send_email);
        btn_sendemail.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        sendEmail("impacts.lab@gmail.com");

                    }
                }
        );

        // 문자 버튼을 눌러 문자쓰기를 할 수 있다.
        btn_sendmessage = (Button ) findViewById(R.id.send_message);
        btn_sendmessage.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        Go_Message("010-1234-5678");

                    }
                }
        );

        // 전화 버튼을 눌러 전화를 할 수 있다.
        btn_call = (Button ) findViewById(R.id.call);
        btn_call.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        Go_Call();

                    }
                }
        );

    }

    // 이메일로 문의 기능 - 메쏘드
    public void sendEmail(String email){
        try{
            // 안드로이드는 여러가지 action들을 정의하고 있는데 그 중에 ACTION_SEND를 시용하면 activity 간에 data를 전달할 수 있으며 심지어 서로다른 process내의 activity들 간에도 가능하다.
            //출처: https://callmansoft.tistory.com/entry/간단한-Data를-다른-App에-보내기 [소프트 앤 소퍼]
            // intent.action_send 데이터 보낼 때 사용
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            // 이메일 쓸 때 받을 사람 이메일을 무엇으로 설정해줄지 인텐트에 담아 보내준다
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
            // 이메일 쓸 때 기본 제목을 emailintent에 담아보내준다  - extra_subject 미리 설정할 제목
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "< 할 수 있다 > 문의하기");
            // 이메일 쓸 때 어떤 내용을 기본으로 해줄 건지 emailintent에 함께 담아준다. 미리 설정할 내용
            emailIntent.putExtra(Intent.EXTRA_TEXT, "앱 버전 (AppVersion):" + "\n기기명 (Device):\n안드로이드 OS (Android OS):\n내용 (Content):\n");
            // 어떤 타입의 데이터를 보낼지 설정
            emailIntent.setType("plain/Text");
            // createChooser() 메서드에 전달된 인텐트에 응답하는 앱의 목록을 대화상자에 표시하고 제공된 텍스트를 대화상자의 제목으로 사용합니다.
            // Intent.createChooser()를 intent에 넘기면 항상 선택창이 나타나게 된다.
            // 심지어 default 로 정해져 있는 action이 있어도 선택창이 나타난다.
            // 출처: https://callmansoft.tistory.com/entry/간단한-Data를-다른-App에-보내기 [소프트 앤 소퍼]
            startActivity(Intent.createChooser(emailIntent, "어떤 이메일을 사용하시겠습니까?"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // 문자로 문의기능
    private void Go_Message(final String number) {
        // 다이어로그 만들어준다.
        new AlertDialog.Builder(this)
                .setTitle("문자문의").setMessage("문의를 하시겠습니까?")
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        try{
                            // https://m.blog.naver.com/PostView.nhn?blogId=gyeom__&logNo=220873942276&proxyReferer=https%3A%2F%2Fwww.google.com%2F
                            // Uri는 프로토콜 :// 접속주소 || 데이터종류 ://데이터내용과 같은 방식을 말하며 mailto://메일주소, ftp://파일서버주소,
                            // 위의 설명에 이어서 uri는 tel:전화번호처럼 데이터에 대한 내용을 표현하는 한 방법, 인터넷의 주소를 말하는 URL 또한 URI의 한 부분.
                            Uri smsUri = Uri.parse("sms:" + number);
                            // 표현 방식 : new intent(how,what)
                            // how : action이란 string 값 : 데이터를 처리하기 위한 방법 - 상수
                            // what : uri(uri.parse(string)) = 처리할 데이터
                            Intent sendIntent = new Intent(Intent.ACTION_SENDTO, smsUri);
                            sendIntent.putExtra("sms_body", "< 할 수 있다 > 문의하기" + "\n기기명 (Device):\n안드로이드 OS (Android OS):\n내용 (Content):\n" );
                            startActivity(sendIntent);

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                })
                .show();

    }

    // 전화걸어 문의 기능
    private void Go_Call() {
        new AlertDialog.Builder(this)
                .setTitle("전화문의").setMessage("문의를 하시겠습니까?")
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_CALL);
                        // 데이터 URI만 설정하려면 setData()를 호출.
                        // MIME 유형만 설정하려면 setType()을 호출.
                        // 필요한 경우, setDataAndType()을 사용하여 두 가지를 모두 명시적으로 설정.
                        // Uri 데이터를 표현하는 한 방법
                        intent.setData(Uri.parse("tel:01012345678"));
                        // Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:12345"));
                        startActivity(intent);
                        // https://wowon.tistory.com/97

                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                })
                .show();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        // LatLng : 위도와 경도로 좌표를 생성
        LatLng office = new LatLng(37.3874688, -122.0925629);

        // MarkerOptions 구글 맵에 표시할 마커에 대한 옵션 설정
        // https://mailmail.tistory.com/19
        MarkerOptions markerOptions = new MarkerOptions();
        //  Position 위치(필수)
        // 지도에서 마커의 위치에 대한 LatLng 값입니다. 이는 Marker 객체의 유일한 필수 속성입니다.
        markerOptions.position(office);
        markerOptions.title("< 할 수 있다 > 사무실 ");
        markerOptions.snippet(" 실리콘밸리 어딘가");
        //.showInfoWindow(); 추가하면 화면에 마카가 뜸
        mMap.addMarker(markerOptions).showInfoWindow();

        // CameraUpdateFactory
        // movecamera : camera 좌표를 서울역 근처로 옮겨 봅니다
        mMap.moveCamera(CameraUpdateFactory.newLatLng(office));
        // animateCamera() 는 근거리에선 부드럽게 변경합니다
        //출처: https://bitsoul.tistory.com/145 [Happy Programmer~]
        // 15로 설정하면 적당하게 zoom 되는듯.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));

        // 마커클릭 이벤트 처리
        // GoogleMap 에 마커클릭 이벤트 설정 가능.
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // 마커 클릭시 호출되는 콜백 메서드
                Toast.makeText(getApplicationContext(), marker.getTitle(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}
