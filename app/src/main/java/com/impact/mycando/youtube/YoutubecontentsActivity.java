package com.impact.mycando.youtube;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.impact.mycando.MainActivity;
import com.impact.mycando.R;

import java.util.Vector;

public class YoutubecontentsActivity extends AppCompatActivity {

    ImageButton btn_back, btn_help;
    RecyclerView recyclerView;
    Vector<YoutubeVideos> youtubeVideos = new Vector<YoutubeVideos>();
    Button youtube01, youtube02, youtube03, youtube04, youtube05, youtube06, youtube07, youtube08, youtube09, youtube10, youtube11, youtube12, youtube13, youtube14, youtube15, youtube16, youtube17;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtubecontents);

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

        youtube01 = (Button)findViewById(R.id.youtube1);
        youtube01.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=LcuO7x0kjpo&feature=emb_title"));
                        startActivity(intent);

                    }
                }
        );

        youtube02 = (Button)findViewById(R.id.youtube2);
        youtube02.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=1utzfa-a5AY&feature=emb_title"));
                        startActivity(intent);

                    }
                }
        );

        youtube03 = (Button)findViewById(R.id.youtube3);
        youtube03.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=uPhHPO98M84&feature=emb_title"));
                        startActivity(intent);

                    }
                }
        );

        youtube04 = (Button)findViewById(R.id.youtube4);
        youtube04.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=IvINqJR4DOo&feature=emb_title"));
                        startActivity(intent);

                    }
                }
        );

        youtube05 = (Button)findViewById(R.id.youtube5);
        youtube05.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=3nEzvTButX8&feature=emb_title"));
                        startActivity(intent);

                    }
                }
        );

        youtube06 = (Button)findViewById(R.id.youtube6);
        youtube06.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=6Elm2mChF0s&feature=emb_title"));
                        startActivity(intent);

                    }
                }
        );

        youtube07 = (Button)findViewById(R.id.youtube7);
        youtube07.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=lViiDpx_uDA&feature=emb_title"));
                        startActivity(intent);

                    }
                }
        );

        youtube08 = (Button)findViewById(R.id.youtube8);
        youtube08.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=uO-3zolO6ww&feature=emb_title"));
                        startActivity(intent);

                    }
                }
        );

        youtube09 = (Button)findViewById(R.id.youtube9);
        youtube09.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=PWsrECn2p7Y&feature=emb_title"));
                        startActivity(intent);

                    }
                }
        );

        youtube10 = (Button)findViewById(R.id.youtube10);
        youtube10.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=Nu1nMc3g3Uk&feature=emb_title"));
                        startActivity(intent);

                    }
                }
        );

        youtube11 = (Button)findViewById(R.id.youtube11);
        youtube11.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=xybrVYunO8w&feature=emb_title"));
                        startActivity(intent);

                    }
                }
        );

        youtube12 = (Button)findViewById(R.id.youtube12);
        youtube12.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=uCjKHv6fEk8&feature=emb_title"));
                        startActivity(intent);

                    }
                }
        );

        youtube13 = (Button)findViewById(R.id.youtube13);
        youtube13.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=3tDtTMkZxEU&feature=emb_title"));
                        startActivity(intent);

                    }
                }
        );

        youtube14 = (Button)findViewById(R.id.youtube14);
        youtube14.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=bvAEJ8G9l9U&feature=emb_title"));
                        startActivity(intent);

                    }
                }
        );

        youtube15 = (Button)findViewById(R.id.youtube15);
        youtube15.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=5cFtGH54aQw&feature=emb_title"));
                        startActivity(intent);

                    }
                }
        );

        youtube16 = (Button)findViewById(R.id.youtube16);
        youtube16.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=JXd4xlL0ER0&feature=emb_title"));
                        startActivity(intent);

                    }
                }
        );

        youtube17 = (Button)findViewById(R.id.youtube17);
        youtube17.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=f-yCsgEQFnc&feature=emb_title"));
                        startActivity(intent);

                    }
                }
        );



//        // 뒤로가기 버튼을 누르면 로그인 화면으로 감
//        btn_help = (ImageButton) findViewById(R.id.help_youtube);
//        btn_help.setOnClickListener(
//                new Button.OnClickListener(){
//                    public void onClick(View v){
//                        Intent intent = new Intent(getApplicationContext(), Helpyoutube.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                }
//        );

//        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager( new LinearLayoutManager(this));
//
//        youtubeVideos.add( new YoutubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/LcuO7x0kjpo\" frameborder=\"0\" allowfullscreen></iframe>") );
//        youtubeVideos.add( new YoutubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/1utzfa-a5AY\" frameborder=\"0\" allowfullscreen></iframe>") );
//        youtubeVideos.add( new YoutubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/uPhHPO98M84\" frameborder=\"0\" allowfullscreen></iframe>") );
//        youtubeVideos.add( new YoutubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/IvINqJR4DOo\" frameborder=\"0\" allowfullscreen></iframe>") );
//        youtubeVideos.add( new YoutubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/3nEzvTButX8\" frameborder=\"0\" allowfullscreen></iframe>") );
//        youtubeVideos.add( new YoutubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/6Elm2mChF0s\" frameborder=\"0\" allowfullscreen></iframe>") );
//        youtubeVideos.add( new YoutubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/lViiDpx_uDA\" frameborder=\"0\" allowfullscreen></iframe>") );
//        youtubeVideos.add( new YoutubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/uO-3zolO6ww\" frameborder=\"0\" allowfullscreen></iframe>") );
//        youtubeVideos.add( new YoutubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/PWsrECn2p7Y\" frameborder=\"0\" allowfullscreen></iframe>") );
//        youtubeVideos.add( new YoutubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/Nu1nMc3g3Uk\" frameborder=\"0\" allowfullscreen></iframe>") );
//        youtubeVideos.add( new YoutubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/xybrVYunO8w\" frameborder=\"0\" allowfullscreen></iframe>") );
//        youtubeVideos.add( new YoutubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/uCjKHv6fEk8\" frameborder=\"0\" allowfullscreen></iframe>") );
//        youtubeVideos.add( new YoutubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/3tDtTMkZxEU\" frameborder=\"0\" allowfullscreen></iframe>") );
//        youtubeVideos.add( new YoutubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/bvAEJ8G9l9U\" frameborder=\"0\" allowfullscreen></iframe>") );
//        youtubeVideos.add( new YoutubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/5cFtGH54aQw\" frameborder=\"0\" allowfullscreen></iframe>") );
//        youtubeVideos.add( new YoutubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/JXd4xlL0ER0\" frameborder=\"0\" allowfullscreen></iframe>") );
//        youtubeVideos.add( new YoutubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/f-yCsgEQFnc\" frameborder=\"0\" allowfullscreen></iframe>") );
//
//        VideoAdapter videoAdapter = new VideoAdapter(youtubeVideos);
//
//        recyclerView.setAdapter(videoAdapter);

    }

//    protected void onResume() {
//        super.onResume();
//
//        try {
//            Class.forName("android.webkit.WebView")
//                    .getMethod("onResume", (Class[]) null)
//                    .invoke(webView, (Object[]) null);
//
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    protected void onPause() {
//        super.onPause();
//        try {
//            Class.forName("android.webkit.WebView")
//                    .getMethod("onPause", (Class[]) null)
//                    .invoke(webView, (Object[]) null);
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//    }

}
