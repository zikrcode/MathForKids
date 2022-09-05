package com.zam.mathforkids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PracticeActivity extends AppCompatActivity implements View.OnTouchListener{

    private SharedPreferences sharedPreferences1, sharedPreferences2;
    private int n=0, ansCorrect=0, ansWrong=0, c=0, max, min, ans;
    private  String s, question;
    private  Question[] practiceQuestions;
    private ImageView ivBackPA, ivA, ivB, ivC, ivD;
    private TextView tvNumberOfQuestionPA, tvCorrectPA, tvWrongPA, tvX, tvOperationPA, tvY, tvA, tvB, tvC, tvD;
    private String correct="", wrong="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        sharedPreferences1 = getApplicationContext().getSharedPreferences("DIGIT_RANGE",MODE_PRIVATE);
        sharedPreferences2 = getApplicationContext().getSharedPreferences("OPERATION",MODE_PRIVATE);

        min = sharedPreferences1.getInt("MIN",10);
        max = sharedPreferences1.getInt("MAX",20);
        s = sharedPreferences2.getString("Operation","+");

        practiceQuestions = new Question[10];
        for (int i=0; i<10; i++){
            practiceQuestions[i] = new Question(s,max,min);

        }

        ivBackPA = findViewById(R.id.ivBackPA);
        ivBackPA.setOnTouchListener(this);

        tvNumberOfQuestionPA = findViewById(R.id.tvNumberOfQuestionPA);
        tvCorrectPA = findViewById(R.id.tvCorrectPA);
        tvWrongPA = findViewById(R.id.tvWrongPA);

        tvX = findViewById(R.id.tvX);
        tvOperationPA = findViewById(R.id.tvOperationPA);
        tvY = findViewById(R.id.tvY);

        switch (s){
            case "+": tvOperationPA.setText("+"); break;
            case "-": tvOperationPA.setText("-"); break;
            case "×": tvOperationPA.setText("×"); break;
            case "÷": tvOperationPA.setText("÷"); break;
        }

        tvA = findViewById(R.id.tvA);
        tvB = findViewById(R.id.tvB);
        tvC = findViewById(R.id.tvC);
        tvD = findViewById(R.id.tvD);

        ivA = findViewById(R.id.ivA);
        ivA.setOnTouchListener(this);
        ivB = findViewById(R.id.ivB);
        ivB.setOnTouchListener(this);
        ivC = findViewById(R.id.ivC);
        ivC.setOnTouchListener(this);
        ivD = findViewById(R.id.ivD);
        ivD.setOnTouchListener(this);

        setUpQuestion();
    }

    private void setUpQuestion() {
        ivA.clearColorFilter();
        ivB.clearColorFilter();
        ivC.clearColorFilter();
        ivD.clearColorFilter();

        tvNumberOfQuestionPA.setText(String.valueOf(n+1)+"/10");

        tvX.setText(practiceQuestions[n].getA());
        tvY.setText(practiceQuestions[n].getB());

        c = practiceQuestions[n].getC();
        int[] random = practiceQuestions[n].getRandom();
        question = practiceQuestions[n].getQuestion();

        tvA.setText(String.valueOf(random[0]));
        tvB.setText(String.valueOf(random[1]));
        tvC.setText(String.valueOf(random[2]));
        tvD.setText(String.valueOf(random[3]));

        switch ((int) (Math.random() * 5)){
            case 1: ans=ivA.getId(); tvA.setText(String.valueOf(c)); break;
            case 2: ans=ivB.getId(); tvB.setText(String.valueOf(c)); break;
            case 3: ans=ivC.getId(); tvC.setText(String.valueOf(c)); break;
            default: ans=ivD.getId(); tvD.setText(String.valueOf(c)); break;
        }
    }

    private boolean nextQuestion=true;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ImageView imageView = (ImageView) v;

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            imageView.setColorFilter(getColor(R.color.transparent));
        }
        if(event.getAction() == MotionEvent.ACTION_UP){
            imageView.clearColorFilter();

            if (imageView.getId()==R.id.ivBackPA){
                finish();
            }
            else {
                if (imageView.getId()==ans){
                    if (nextQuestion){
                        correct+=question+"\n";
                        ansCorrect++;
                    }
                    nextQuestion=true;
                    n++;
                    if (n>9){
                        Intent intent = new Intent(PracticeActivity.this, ResultActivity.class);
                        intent.putExtra("ACTIVITY","PA");
                        intent.putExtra("CORRECT",String.valueOf(ansCorrect));
                        intent.putExtra("WRONG",String.valueOf(ansWrong));
                        intent.putExtra("C_QUESTIONS",correct);
                        intent.putExtra("W_QUESTIONS",wrong);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        setUpQuestion();
                    }
                }
                else {
                    imageView.setColorFilter(getColor(R.color.transparent2));
                    if (nextQuestion){
                        changeAns(imageView.getId());
                        wrong+=question+"\n";
                        ansWrong++;
                        nextQuestion=false;
                    }
                }
                updateScore();
            }
        }
        return true;
    }

    private void changeAns(int id) {
        int x=question.indexOf('=')+1;

        switch (id){
            case R.id.ivA: question=question.substring(0,x)+tvA.getText(); break;
            case R.id.ivB: question=question.substring(0,x)+tvB.getText(); break;
            case R.id.ivC: question=question.substring(0,x)+tvC.getText(); break;
            case R.id.ivD: question=question.substring(0,x)+tvD.getText(); break;
        }
    }

    private void updateScore() {
        tvCorrectPA.setText(String.valueOf(ansCorrect));
        tvWrongPA.setText(String.valueOf(ansWrong));
    }
}