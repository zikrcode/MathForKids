package com.zam.mathforkids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends AppCompatActivity implements View.OnTouchListener {

    private String activity;
    private TextView tv1RA, tv2RA, tvAns1RA, tvAns2RA, tvCorrectRA, tvWrongRA;
    private ImageView ivRestartRA, ivExitRA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        activity = getIntent().getStringExtra("ACTIVITY");

        tv1RA = findViewById(R.id.tv1RA);
        tv2RA = findViewById(R.id.tv2RA);

        tvAns1RA = findViewById(R.id.tvAns1RA);
        tvAns2RA = findViewById(R.id.tvAns2RA);

        tvCorrectRA = findViewById(R.id.tvCorrectRA);
        tvWrongRA = findViewById(R.id.tvWrongRA);

        if (activity.equals("DA")){
            duelResult();
        }
        else {result();}

        ivRestartRA = findViewById(R.id.ivRestartRA);
        ivRestartRA.setOnTouchListener(this);

        ivExitRA = findViewById(R.id.ivExitRA);
        ivExitRA.setOnTouchListener(this);
    }

    private void duelResult() {
        tv1RA.setText("Player 1");
        tv2RA.setTextColor(getColor(R.color.green));
        tv2RA.setText("Player 2");
        tvAns1RA.setText("="+getIntent().getStringExtra("PLAYER 1"));
        tvAns2RA.setTextColor(getColor(R.color.green));
        tvAns2RA.setText("="+getIntent().getStringExtra("PLAYER 2"));
        tvCorrectRA.setText(getIntent().getStringExtra("C1_QUESTIONS"));
        tvWrongRA.setTextColor(getColor(R.color.green));
        tvWrongRA.setText(getIntent().getStringExtra("C2_QUESTIONS"));
    }

    private void result() {
        tv1RA.setText("Correct");
        tv2RA.setText("Wrong");
        tvAns1RA.setText("="+getIntent().getStringExtra("CORRECT"));
        tvAns2RA.setText("="+getIntent().getStringExtra("WRONG"));
        tvCorrectRA.setText(getIntent().getStringExtra("C_QUESTIONS"));
        tvWrongRA.setText(getIntent().getStringExtra("W_QUESTIONS"));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ImageView imageView = (ImageView) v;

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            imageView.setColorFilter(getColor(R.color.transparent));
        }

        if(event.getAction() == MotionEvent.ACTION_UP){
            imageView.clearColorFilter();

            if (v.getId()==R.id.ivRestartRA){
                Intent intentRestart;

                if (activity.equals("PA")){
                    intentRestart = new Intent(ResultActivity.this, PracticeActivity.class);
                }
                else if (activity.equals("QA")) {
                    intentRestart = new Intent(ResultActivity.this, QuizActivity.class);
                }
                else if (activity.equals("DA")) {
                    intentRestart = new Intent(ResultActivity.this, DuelActivity.class);
                }
                else {
                    intentRestart = new Intent(ResultActivity.this, TimeActivity.class);
                }
                startActivity(intentRestart);
            }
            /*else {
                //Intent intentExit = new Intent(ResultActivity.this, GameModeActivity.class);
                //intentExit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //startActivity(intentExit);
            }*/
            //Toast.makeText(ResultActivity.this, "RRRRRRRRR", Toast.LENGTH_SHORT).show();
            finish();
        }
        return true;
    }
}