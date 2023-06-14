package com.zam.mathforkids.play.game_mode.time;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zam.mathforkids.Question;
import com.zam.mathforkids.R;
import com.zam.mathforkids.play.game_mode.result.ResultActivity;

public class TimeActivity extends AppCompatActivity implements View.OnTouchListener{

    private SharedPreferences sharedPreferencesDigitRange, sharedPreferencesOperation;
    private ImageView ivBackTA, ivATA, ivBTA, ivCTA, ivDTA;
    private TextView tvNumberOfQuestionTA, tvTimerTA, tvXTA, tvOperationTA, tvYTA, tvATA, tvBTA, tvCTA, tvDTA;
    private MyTimer myTimer;
    private int n = 0, ansCorrect = 0, c, max, min, ans;
    private  String operation, question;
    private Question[] timeQuestions;
    private String correct = "", wrong = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        sharedPreferencesDigitRange = getApplicationContext().getSharedPreferences("DIGIT_RANGE" ,MODE_PRIVATE);
        sharedPreferencesOperation = getApplicationContext().getSharedPreferences("OPERATION", MODE_PRIVATE);

        ivBackTA = findViewById(R.id.ivBackTA);
        tvTimerTA = findViewById(R.id.tvTimerTA);

        tvNumberOfQuestionTA = findViewById(R.id.tvNumberOfQuestionTA);

        tvXTA = findViewById(R.id.tvXTA);
        tvOperationTA = findViewById(R.id.tvOperationTA);
        tvYTA = findViewById(R.id.tvYTA);

        tvATA = findViewById(R.id.tvATA);
        tvBTA = findViewById(R.id.tvBTA);
        tvCTA = findViewById(R.id.tvCTA);
        tvDTA = findViewById(R.id.tvDTA);

        ivATA = findViewById(R.id.ivATA);
        ivBTA = findViewById(R.id.ivBTA);
        ivCTA = findViewById(R.id.ivCTA);
        ivDTA = findViewById(R.id.ivDTA);

        setupViews();
    }

    private void setupViews() {
        myTimer =  new MyTimer(sharedPreferencesDigitRange.getInt("SECONDS",10000),1000);
        min = sharedPreferencesDigitRange.getInt("MIN",10);
        max = sharedPreferencesDigitRange.getInt("MAX",20);
        operation = sharedPreferencesOperation.getString("Operation","+");

        timeQuestions = new Question[10];
        for (int i = 0; i < 10; i++) {
            timeQuestions[i] = new Question(operation, max, min);
        }

        switch (operation) {
            case "+": tvOperationTA.setText("+"); break;
            case "-": tvOperationTA.setText("-"); break;
            case "×": tvOperationTA.setText("×"); break;
            case "÷": tvOperationTA.setText("÷"); break;
        }

        ivBackTA.setOnTouchListener(this);
        ivATA.setOnTouchListener(this);
        ivBTA.setOnTouchListener(this);
        ivCTA.setOnTouchListener(this);
        ivDTA.setOnTouchListener(this);

        setUpQuestion();
    }

    private void setUpQuestion() {
        tvNumberOfQuestionTA.setText((n + 1) + "/10");

        myTimer.start();

        tvXTA.setText(timeQuestions[n].getA());
        tvYTA.setText(timeQuestions[n].getB());

        c = timeQuestions[n].getC();
        int[] random = timeQuestions[n].getRandom();
        question = timeQuestions[n].getQuestion();

        tvATA.setText(String.valueOf(random[0]));
        tvBTA.setText(String.valueOf(random[1]));
        tvCTA.setText(String.valueOf(random[2]));
        tvDTA.setText(String.valueOf(random[3]));

        switch ((int) (Math.random() * 5)) {
            case 1: ans = ivATA.getId(); tvATA.setText(String.valueOf(c)); break;
            case 2: ans = ivBTA.getId(); tvBTA.setText(String.valueOf(c)); break;
            case 3: ans = ivCTA.getId(); tvCTA.setText(String.valueOf(c)); break;
            default: ans = ivDTA.getId(); tvDTA.setText(String.valueOf(c)); break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ImageView imageView = (ImageView) v;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            imageView.setColorFilter(getColor(R.color.transparent));
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            imageView.clearColorFilter();

            myTimer.cancel();

            if (imageView.getId() == R.id.ivBackTA) {
                finish();
            }
            else {
                n++;
                if (imageView.getId() == ans) {
                    ansCorrect++;
                    correct += question + "\n";
                }
                else {
                    changeAns(imageView.getId());
                    wrong += question + "\n";
                }
                if (n == 10) {
                    finishResult();
                }
                else {
                    setUpQuestion();
                }
            }
        }
        return true;
    }

    private void changeAns(int id) {
        int x = question.indexOf('=') + 1;

        switch (id){
            case R.id.ivATA: question = question.substring(0, x) + tvATA.getText(); break;
            case R.id.ivBTA: question = question.substring(0, x) + tvBTA.getText(); break;
            case R.id.ivCTA: question = question.substring(0, x) + tvCTA.getText(); break;
            case R.id.ivDTA: question = question.substring(0, x) + tvDTA.getText(); break;
        }
    }

    private void finishResult() {
        Intent intent = new Intent(TimeActivity.this, ResultActivity.class);
        intent.putExtra("ACTIVITY","TA");
        intent.putExtra("CORRECT",String.valueOf(ansCorrect));
        intent.putExtra("WRONG",String.valueOf(10 - ansCorrect));
        intent.putExtra("C_QUESTIONS",correct);
        intent.putExtra("W_QUESTIONS",wrong);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        myTimer.cancel();
        finish();
    }

    private class MyTimer extends CountDownTimer {

        public MyTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tvTimerTA.setText(getString(R.string.seconds_remaining) + millisUntilFinished / 1000);
        }

        @Override
        public void onFinish() {
            n++;
            wrong += getString(R.string.not_solved) + "\n";
            if (n == 10) {
                finishResult();
            }
            else {
                setUpQuestion();
            }
        }
    }
}