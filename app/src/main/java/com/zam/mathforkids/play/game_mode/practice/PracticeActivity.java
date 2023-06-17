/*
 *
 * Copyright (C) 2023 Zokirjon Mamadjonov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zam.mathforkids.play.game_mode.practice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zam.mathforkids.model.Question;
import com.zam.mathforkids.R;
import com.zam.mathforkids.play.game_mode.result.ResultActivity;
import com.zam.mathforkids.utils.AppConstants;

public class PracticeActivity extends AppCompatActivity implements View.OnTouchListener{

    private SharedPreferences sharedPreferencesDigitRange, sharedPreferencesOperation;
    private ImageView ivBackPA, ivA, ivB, ivC, ivD;
    private TextView tvNumberOfQuestionPA, tvCorrectPA, tvWrongPA, tvX, tvOperationPA, tvY, tvA, tvB, tvC, tvD;
    private int n = 0, ansCorrect = 0, ansWrong = 0, c = 0, max, min, ans;
    private  String operation, question;
    private  Question[] practiceQuestions;
    private String correct = "", wrong = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        sharedPreferencesDigitRange = getApplicationContext().getSharedPreferences(AppConstants.DIGIT_RANGE, MODE_PRIVATE);
        sharedPreferencesOperation = getApplicationContext().getSharedPreferences(AppConstants.OPERATION, MODE_PRIVATE);

        ivBackPA = findViewById(R.id.ivBackPA);

        tvNumberOfQuestionPA = findViewById(R.id.tvNumberOfQuestionPA);
        tvCorrectPA = findViewById(R.id.tvCorrectPA);
        tvWrongPA = findViewById(R.id.tvWrongPA);

        tvX = findViewById(R.id.tvX);
        tvOperationPA = findViewById(R.id.tvOperationPA);
        tvY = findViewById(R.id.tvY);

        tvA = findViewById(R.id.tvA);
        tvB = findViewById(R.id.tvB);
        tvC = findViewById(R.id.tvC);
        tvD = findViewById(R.id.tvD);

        ivA = findViewById(R.id.ivA);
        ivB = findViewById(R.id.ivB);
        ivC = findViewById(R.id.ivC);
        ivD = findViewById(R.id.ivD);

        setupViews();
    }

    private void setupViews() {
        min = sharedPreferencesDigitRange.getInt(AppConstants.KEY_MIN, AppConstants.KEY_MIN_DEF);
        max = sharedPreferencesDigitRange.getInt(AppConstants.KEY_MAX, AppConstants.KEY_MAX_DEF);
        operation = sharedPreferencesOperation.getString(AppConstants.KEY_OPERATION, AppConstants.ADDITION);

        practiceQuestions = new Question[10];
        for (int i = 0; i < 10; i++) {
            practiceQuestions[i] = new Question(operation, max, min);
        }

        switch (operation) {
            case AppConstants.ADDITION: tvOperationPA.setText(AppConstants.ADDITION); break;
            case AppConstants.SUBTRACTION: tvOperationPA.setText(AppConstants.SUBTRACTION); break;
            case AppConstants.MULTIPLICATION: tvOperationPA.setText(AppConstants.MULTIPLICATION); break;
            case AppConstants.DIVISION: tvOperationPA.setText(AppConstants.DIVISION); break;
        }

        ivBackPA.setOnTouchListener(this);
        ivA.setOnTouchListener(this);
        ivB.setOnTouchListener(this);
        ivC.setOnTouchListener(this);
        ivD.setOnTouchListener(this);

        setUpQuestion();
    }

    private void setUpQuestion() {
        ivA.clearColorFilter();
        ivB.clearColorFilter();
        ivC.clearColorFilter();
        ivD.clearColorFilter();

        tvNumberOfQuestionPA.setText((n + 1) + "/10");

        tvX.setText(practiceQuestions[n].getA());
        tvY.setText(practiceQuestions[n].getB());

        c = practiceQuestions[n].getC();
        int[] random = practiceQuestions[n].getRandom();
        question = practiceQuestions[n].getQuestion();

        tvA.setText(String.valueOf(random[0]));
        tvB.setText(String.valueOf(random[1]));
        tvC.setText(String.valueOf(random[2]));
        tvD.setText(String.valueOf(random[3]));

        switch ((int) (Math.random() * 5)) {
            case 1: ans = ivA.getId(); tvA.setText(String.valueOf(c)); break;
            case 2: ans = ivB.getId(); tvB.setText(String.valueOf(c)); break;
            case 3: ans = ivC.getId(); tvC.setText(String.valueOf(c)); break;
            default: ans = ivD.getId(); tvD.setText(String.valueOf(c)); break;
        }
    }

    private boolean nextQuestion = true;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ImageView imageView = (ImageView) v;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            imageView.setColorFilter(getColor(R.color.transparent));
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            imageView.clearColorFilter();

            if (imageView.getId() == R.id.ivBackPA) {
                finish();
            }
            else {
                if (imageView.getId() == ans) {
                    if (nextQuestion) {
                        correct += question + "\n";
                        ansCorrect++;
                    }
                    nextQuestion = true;
                    n++;
                    if (n == 10) {
                        Intent intent = new Intent(PracticeActivity.this, ResultActivity.class);
                        intent.putExtra(AppConstants.ACTIVITY, AppConstants.PRACTICE_ACTIVITY);
                        intent.putExtra(AppConstants.CORRECT, String.valueOf(ansCorrect));
                        intent.putExtra(AppConstants.WRONG, String.valueOf(ansWrong));
                        intent.putExtra(AppConstants.C_QUESTIONS, correct);
                        intent.putExtra(AppConstants.W_QUESTIONS, wrong);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        setUpQuestion();
                    }
                }
                else {
                    imageView.setColorFilter(getColor(R.color.transparent_red));
                    if (nextQuestion) {
                        changeAns(imageView.getId());
                        wrong += question + "\n";
                        ansWrong++;
                        nextQuestion = false;
                    }
                }
                updateScore();
            }
        }
        return true;
    }

    private void changeAns(int id) {
        int x = question.indexOf(AppConstants.EQUAL) + 1;

        switch (id) {
            case R.id.ivA: question = question.substring(0, x) + tvA.getText(); break;
            case R.id.ivB: question = question.substring(0, x) + tvB.getText(); break;
            case R.id.ivC: question = question.substring(0, x) + tvC.getText(); break;
            case R.id.ivD: question = question.substring(0, x) + tvD.getText(); break;
        }
    }

    private void updateScore() {
        tvCorrectPA.setText(String.valueOf(ansCorrect));
        tvWrongPA.setText(String.valueOf(ansWrong));
    }
}