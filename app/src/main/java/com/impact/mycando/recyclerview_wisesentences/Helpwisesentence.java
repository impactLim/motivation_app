package com.impact.mycando.recyclerview_wisesentences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.impact.mycando.R;

public class Helpwisesentence extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpwisesentence);


        // 명언 추가하러 가기 버튼 누르면 명언메인페이지로 이동
        Button btn_writewise = (Button) findViewById(R.id.gowritewise_button);
        btn_writewise.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        Intent intent = new Intent(getApplicationContext(), MainpagesentencesActivity.class);
                        startActivity(intent);
                        finish();

                    }
                }
        );

    }
}
