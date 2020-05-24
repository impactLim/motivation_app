package com.impact.mycando;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class DeepbreathActivity extends AppCompatActivity {

    ImageButton btn_back;


    MediaPlayer mp; // 음악 재생을 위한 객체
    int pos; // 재생 멈춘 시점

    private Button bStart;
    private Button bPause;
    private Button bRestart;
    private Button bStop;

    TextView text_time;
    SeekBar sb; // 음악 재생위치를 나타내는 시크바
    boolean isPlaying = false; // 재생중인지 확인할 변수


    class MyThread extends Thread
    {
        @Override
        public void run() { // 쓰레드가 시작되면 콜백되는 메서드
            // 씨크바 막대가 조금씩 움직이기 (노래 끝날 때까지 반복)
            while(isPlaying) {
                sb.setProgress(mp.getCurrentPosition());
                // 밑에 로그 찍으면 계속 반복
                // Log.v("명상화면 run 확인 알림","명상 thread run");

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deepbreath);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        출처: https://docko.tistory.com/entry/안드로이드-화면Activity를-항상-켜짐-상태로-유지하기 [장똘]

        // 이미지뷰
        ImageView breath_image = (ImageView) findViewById(R.id.imageView2);
        Glide.with(this).load(R.drawable.breathgif).into(breath_image );

        bStart = (Button)findViewById(R.id.buttonStart);
        bPause = (Button)findViewById(R.id.buttonPause);
        bRestart=(Button)findViewById(R.id.buttonRestart);
        bStop  = (Button)findViewById(R.id.buttonFinish);

        text_time = (TextView)findViewById(R.id.textview_time);
        TextView from_textview = (TextView)findViewById(R.id.textview_from);
        from_textview.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=a0p0--RmE8c"));
                        startActivity(intent);

                    }
                }
        );

        sb = (SeekBar)findViewById(R.id.seekBar);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                // 음악 재생 중에만 seekbar를 움직일 수 있다.
                isPlaying = true;
                int ttt = seekBar.getProgress(); // 사용자가 움직여놓은 위치
                mp.seekTo(ttt);
                mp.start();
                new MyThread().start();
                Log.v("심호흡 thread start 확인 알림","  thread start");

            }

            // 재생중이 아니라면 노래 pause
            public void onStartTrackingTouch(SeekBar seekBar)
            {
                isPlaying = false;
                mp.pause();
                Log.v("심호흡 thread pause 확인 알림","  thread pause");
            }

            // 재생중이 아니라면 노래 stop
            public void onProgressChanged(SeekBar seekBar,int progress,boolean fromUser)
            {
                if (seekBar.getMax()==progress) {
                    isPlaying = false;
                    mp.stop();
                    Log.v("심호흡 thread stop 확인 알림","  thread stop");
                }

                // 노래 재생시간 확인 가능
                int m = progress / 60000;
                int s = (progress % 60000) / 1000;
                String strTime = String.format("%02d : %02d", m, s);
                text_time.setText(strTime);
            }
        });


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

        // 재생버튼 클릭이벤트 - MediaPlayer의 객체를 통해 start() 메소드를 이용하여 음악을 재생한다.
        bStart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                // MediaPlayer 객체 초기화 , 재생, MediaPlayer의 create() 메소드를 통해 raw 폴더의 음악을 가져와 객체를 생성
                mp = MediaPlayer.create(
                        getApplicationContext(), // 현재 화면의 제어권자
                        R.raw.breathmusic); // 음악파일
                mp.setLooping(false); // true:무한반복
                mp.start(); // 노래 재생 시작
                Log.v(" thread 노래 재생 시작 확인 알림","  ");
                Toast.makeText(getApplicationContext(), "심호흡 시작", Toast.LENGTH_SHORT).show();

                int a = mp.getDuration(); // 노래의 재생시간(miliSecond)
                sb.setMax(a);// 씨크바의 최대 범위를 노래의 재생시간으로 설정

                new MyThread().start(); // 씨크바 그려줄 쓰레드 시작
                isPlaying = true; // 씨크바 쓰레드 반복 하도록
            }
        });

        // Thread 객체는 ProgressBar에 사용된다.
        // MediaPlayer의 객체를 통해 stop() 메소드를 이용하여 음악을 정지
        bStop.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                // 음악 종료
                isPlaying = false; // 쓰레드 종료
                mp.stop(); // 멈춤
                mp.release(); // 자원 해제
                sb.setProgress(0); // 씨크바 초기화
                Log.v("thread 음악 종료 확인 알림","  ");
                Toast.makeText(getApplicationContext(), "심호흡 종료", Toast.LENGTH_SHORT).show();

            }
        });

        //  일시정지 버튼 클릭 이벤트
        bPause.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                // 일시중지
                pos = mp.getCurrentPosition();
                mp.pause(); // 일시중지
                isPlaying = false; // 쓰레드 정지
                Log.v("thread 음악 일시정지 확인 알림","  ");
                Toast.makeText(getApplicationContext(), "심호흡 일시정지", Toast.LENGTH_SHORT).show();

            }
        });

        bRestart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                // 멈춘 지점부터 재시작
                mp.seekTo(pos); // 일시정지 시점으로 이동
                mp.start(); // 시작
                isPlaying = true; // 재생하도록 flag 변경
                new MyThread().start(); // 쓰레드 시작
                Log.v(" thread 음악 restart 확인 알림","  ");
                Toast.makeText(getApplicationContext(), "심호흡 재시작", Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    protected void onPause()
    {
        super.onPause();
        isPlaying = false; // 쓰레드 정지
        if (mp!=null)
        {
            mp.release(); // 자원해제
            Log.v(" thread 음악 release 확인 알림","  ");

        }
    }

    // 명상 추천 유튜버
    // https://hongku.tistory.com/205
    public void onButtonClickedGoYoutube(View view){

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=5pYx7aqwy6Q&list=PLlZZ6sV1uKKEJpEPkOAKgh4lP1HQpleSM"));
        startActivity(intent);

    }


}
