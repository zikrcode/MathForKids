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

package com.zam.mathforkids.play.game_mode.quiz;

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

public class QuizActivity extends AppCompatActivity implements View.OnTouchListener{

    private SharedPreferences sharedPreferencesDigitRange, sharedPreferencesOperation;
    private ImageView ivBackQA, ivAQA, ivBQA, ivCQA, ivDQA;
    private TextView tvNumberOfQuestionQA, tvXQA, tvOperationQA, tvYQA, tvAQA, tvBQA, tvCQA, tvDQA;
    private int n = 0, ansCorrect = 0, c = 0, max, min, ans;
    private  String operation, question;
    private Question[] quizQuestions;
    private String correct = "", wrong = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        sharedPreferencesDigitRange = getApplicationContext().getSharedPreferences(AppConstants.DIGIT_RANGE, MODE_PRIVATE);
        sharedPreferencesOperation = getApplicationContext().getSharedPreferences(AppConstants.OPERATION, MODE_PRIVATE);

        ivBackQA = findViewById(R.id.ivBackQA);

        tvNumberOfQuestionQA = findViewById(R.id.tvNumberOfQuestionQA);

        tvXQA = findViewById(R.id.tvXQA);
        tvOperationQA = findViewById(R.id.tvOperationQA);
        tvYQA = findViewById(R.id.tvYQA);

        tvAQA = findViewById(R.id.tvAQA);
        tvBQA = findViewById(R.id.tvBQA);
        tvCQA = findViewById(R.id.tvCQA);
        tvDQA = findViewById(R.id.tvDQA);

        ivAQA = findViewById(R.id.ivAQA);
        ivBQA = findViewById(R.id.ivBQA);
        ivCQA = findViewById(R.id.ivCQA);
        ivDQA = findViewById(R.id.ivDQA);

        setupViews();
    }

    private void setupViews() {
        min = sharedPreferencesDigitRange.getInt(AppConstants.KEY_MIN, AppConstants.KEY_MIN_DEF);
        max = sharedPreferencesDigitRange.getInt(AppConstants.KEY_MAX, AppConstants.KEY_MAX_DEF);
        operation = sharedPreferencesOperation.getString(AppConstants.KEY_OPERATION, AppConstants.ADDITION);

        quizQuestions = new Question[10];
        for (int i = 0; i < 10; i++) {
            quizQuestions[i] = new Question(operation, max, min);
        }

        switch (operation) {
            case AppConstants.ADDITION: tvOperationQA.setText(AppConstants.ADDITION); break;
            case AppConstants.SUBTRACTION: tvOperationQA.setText(AppConstants.SUBTRACTION); break;
            case AppConstants.MULTIPLICATION: tvOperationQA.setText(AppConstants.MULTIPLICATION); break;
            case AppConstants.DIVISION: tvOperationQA.setText(AppConstants.DIVISION); break;
        }

        ivBackQA.setOnTouchListener(this);
        ivAQA.setOnTouchListener(this);
        ivBQA.setOnTouchListener(this);
        ivCQA.setOnTouchListener(this);
        ivDQA.setOnTouchListener(this);

        setUpQuestion();
    }

    private void setUpQuestion() {
        tvNumberOfQuestionQA.setText((n + 1) + "/10");

        tvXQA.setText(quizQuestions[n].getA());
        tvYQA.setText(quizQuestions[n].getB());

        c = quizQuestions[n].getC();
        int[] random = quizQuestions[n].getRandom();
        question = quizQuestions[n].getQuestion();

        tvAQA.setText(String.valueOf(random[0]));
        tvBQA.setText(String.valueOf(random[1]));
        tvCQA.setText(String.valueOf(random[2]));
        tvDQA.setText(String.valueOf(random[3]));

        switch ((int) (Math.random() * 5)) {
            case 1: ans = ivAQA.getId(); tvAQA.setText(String.valueOf(c)); break;
            case 2: ans = ivBQA.getId(); tvBQA.setText(String.valueOf(c)); break;
            case 3: ans = ivCQA.getId(); tvCQA.setText(String.valueOf(c)); break;
            default: ans = ivDQA.getId(); tvDQA.setText(String.valueOf(c)); break;
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

            if (imageView.getId() == R.id.ivBackQA) {
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
                    Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                    intent.putExtra(AppConstants.ACTIVITY, AppConstants.QUIZ_ACTIVITY);
                    intent.putExtra(AppConstants.CORRECT, String.valueOf(ansCorrect));
                    intent.putExtra(AppConstants.WRONG, String.valueOf(10 - ansCorrect));
                    intent.putExtra(AppConstants.C_QUESTIONS, correct);
                    intent.putExtra(AppConstants.W_QUESTIONS, wrong);
                    startActivity(intent);
                    finish();
                }
                else {
                    setUpQuestion();
                }
            }
        }
        return true;
    }

    private void changeAns(int id) {
        int x = question.indexOf(AppConstants.EQUAL) + 1;

        switch (id) {
            case R.id.ivAQA: question = question.substring(0, x) + tvAQA.getText(); break;
            case R.id.ivBQA: question = question.substring(0, x) + tvBQA.getText(); break;
            case R.id.ivCQA: question = question.substring(0, x) + tvCQA.getText(); break;
            case R.id.ivDQA: question = question.substring(0, x) + tvDQA.getText(); break;
        }
    }
}