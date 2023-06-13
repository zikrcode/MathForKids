package com.zam.mathforkids.play.game_mode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.zam.mathforkids.play.game_mode.duel.DuelActivity;
import com.zam.mathforkids.play.game_mode.practice.PracticeActivity;
import com.zam.mathforkids.play.game_mode.quiz.QuizActivity;
import com.zam.mathforkids.R;
import com.zam.mathforkids.play.game_mode.time.TimeActivity;

public class GameModeActivity extends AppCompatActivity implements View.OnTouchListener{

    private ImageView ivBackGMA, ivPractice, ivQuiz, ivDuel, ivTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_game_mode);

        ivBackGMA = findViewById(R.id.ivBackGMA);
        ivBackGMA.setOnTouchListener(this);

        ivPractice = findViewById(R.id.ivPractice);
        ivPractice.setOnTouchListener(this);

        ivQuiz = findViewById(R.id.ivQuiz);
        ivQuiz.setOnTouchListener(this);

        ivDuel = findViewById(R.id.ivDuel);
        ivDuel.setOnTouchListener(this);

        ivTime = findViewById(R.id.ivTime);
        ivTime.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ImageView imageView = (ImageView) v;
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            imageView.setColorFilter(getColor(R.color.transparent));
        }
        if(event.getAction() == MotionEvent.ACTION_UP){
            imageView.clearColorFilter();

            switch (v.getId()){
                case R.id.ivBackGMA:
                    finish();
                    break;
                case R.id.ivPractice:
                    Intent intentPractice = new Intent(GameModeActivity.this, PracticeActivity.class);
                    startActivity(intentPractice);
                    break;
                case R.id.ivQuiz:
                    Intent intentQuiz = new Intent(GameModeActivity.this, QuizActivity.class);
                    startActivity(intentQuiz);
                    break;
                case R.id.ivDuel:
                    Intent intentDuel = new Intent(GameModeActivity.this, DuelActivity.class);
                    startActivity(intentDuel);
                    break;
                case R.id.ivTime:
                    Intent intentTime = new Intent(GameModeActivity.this, TimeActivity.class);
                    startActivity(intentTime);
                    break;
            }
        }
        return true;
    }
}