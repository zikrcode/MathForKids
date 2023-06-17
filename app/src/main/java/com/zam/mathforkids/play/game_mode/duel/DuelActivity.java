/*
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

package com.zam.mathforkids.play.game_mode.duel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zam.mathforkids.model.Question;
import com.zam.mathforkids.R;
import com.zam.mathforkids.play.game_mode.result.ResultActivity;
import com.zam.mathforkids.utils.AppConstants;

public class DuelActivity extends AppCompatActivity implements View.OnTouchListener{

    private SharedPreferences sharedPreferencesDigitRange, sharedPreferencesOperation;
    private ImageView ivA1, ivB1, ivC1, ivD1, ivA2, ivB2, ivC2, ivD2;
    private TextView tvA1, tvB1, tvC1, tvD1, tvX1, tvOperation1, tvY1, tvCorrectDA1, tvCorrectDA2, tvX2, tvOperation2, tvY2, tvA2, tvB2, tvC2, tvD2;
    private int n = 0, ansCorrect1 = 0, ansCorrect2 = 0,  c = 0, r1, r2, r3, r4, max, min, ans1, ans2;
    private  String operation, question;
    private Question[] duelQuestions;
    private ProgressBar progressBar;
    private String correct1 = "", correct2 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duel);

        sharedPreferencesDigitRange = getApplicationContext().getSharedPreferences(AppConstants.DIGIT_RANGE, MODE_PRIVATE);
        sharedPreferencesOperation = getApplicationContext().getSharedPreferences(AppConstants.OPERATION, MODE_PRIVATE);

        ivA1 = findViewById(R.id.ivA1);
        ivB1 = findViewById(R.id.ivB1);
        ivC1 = findViewById(R.id.ivC1);
        ivD1 = findViewById(R.id.ivD1);

        tvA1 = findViewById(R.id.tvA1);
        tvB1 = findViewById(R.id.tvB1);
        tvC1 = findViewById(R.id.tvC1);
        tvD1 = findViewById(R.id.tvD1);

        tvX1 = findViewById(R.id.tvX1);
        tvOperation1 = findViewById(R.id.tvOperation1);
        tvY1 = findViewById(R.id.tvY1);
        tvCorrectDA1 = findViewById(R.id.tvCorrectDA1);

        progressBar = findViewById(R.id.progressBar);

        tvCorrectDA2 = findViewById(R.id.tvCorrectDA2);
        tvX2 = findViewById(R.id.tvX2);
        tvOperation2 = findViewById(R.id.tvOperation2);
        tvY2 = findViewById(R.id.tvY2);

        tvA2 = findViewById(R.id.tvA2);
        tvB2 = findViewById(R.id.tvB2);
        tvC2 = findViewById(R.id.tvC2);
        tvD2 = findViewById(R.id.tvD2);

        ivA2 = findViewById(R.id.ivA2);
        ivB2 = findViewById(R.id.ivB2);
        ivC2 = findViewById(R.id.ivC2);
        ivD2 = findViewById(R.id.ivD2);

        setupViews();
    }

    private void setupViews() {
        min = sharedPreferencesDigitRange.getInt(AppConstants.KEY_MIN, AppConstants.KEY_MIN_DEF);
        max = sharedPreferencesDigitRange.getInt(AppConstants.KEY_MAX, AppConstants.KEY_MAX_DEF);
        operation = sharedPreferencesOperation.getString(AppConstants.KEY_OPERATION, AppConstants.ADDITION);

        duelQuestions = new Question[10];
        for (int i = 0; i < 10; i++) {
            duelQuestions[i] = new Question(operation, max, min);
        }

        switch (operation) {
            case AppConstants.ADDITION:
                tvOperation1.setText(AppConstants.ADDITION);
                tvOperation2.setText(AppConstants.ADDITION);
                break;
            case AppConstants.SUBTRACTION:
                tvOperation1.setText(AppConstants.SUBTRACTION);
                tvOperation2.setText(AppConstants.SUBTRACTION);
                break;
            case AppConstants.MULTIPLICATION:
                tvOperation1.setText(AppConstants.MULTIPLICATION);
                tvOperation2.setText(AppConstants.MULTIPLICATION);
                break;
            case AppConstants.DIVISION:
                tvOperation1.setText(AppConstants.DIVISION);
                tvOperation2.setText(AppConstants.DIVISION);
                break;
        }

        ivA1.setOnTouchListener(this);
        ivB1.setOnTouchListener(this);
        ivC1.setOnTouchListener(this);
        ivD1.setOnTouchListener(this);

        ivA2.setOnTouchListener(this);
        ivB2.setOnTouchListener(this);
        ivC2.setOnTouchListener(this);
        ivD2.setOnTouchListener(this);

        setUpQuestion();
    }

    private void setUpQuestion() {
        progressBar.setProgress((n + 1) * 10);

        c = duelQuestions[n].getC();
        int[] random = duelQuestions[n].getRandom();
        question = duelQuestions[n].getQuestion();

        r1 = random[0];
        r2 = random[0];
        r3 = random[0];
        r4 = random[0];

        tvA1.setText(String.valueOf(r1));
        tvB1.setText(String.valueOf(r2));
        tvC1.setText(String.valueOf(r3));
        tvD1.setText(String.valueOf(r4));

        tvX1.setText(duelQuestions[n].getA());
        tvY1.setText(duelQuestions[n].getB());

        tvX2.setText(duelQuestions[n].getA());
        tvY2.setText(duelQuestions[n].getB());

        tvA2.setText(String.valueOf(r1));
        tvB2.setText(String.valueOf(r2));
        tvC2.setText(String.valueOf(r3));
        tvD2.setText(String.valueOf(r4));

        switch ((int) (Math.random() * 5)) {
            case 1:
                ans1 = ivA1.getId();
                ans2 = ivA2.getId();
                tvA1.setText(String.valueOf(c));
                tvA2.setText(String.valueOf(c));
                break;
            case 2:
                ans1 = ivB1.getId();
                ans2 = ivB2.getId();
                tvB1.setText(String.valueOf(c));
                tvB2.setText(String.valueOf(c));
                break;
            case 3:
                ans1 = ivC1.getId();
                ans2 = ivC2.getId();
                tvC1.setText(String.valueOf(c));
                tvC2.setText(String.valueOf(c));
                break;
            default:
                ans1 = ivD1.getId();
                ans2 = ivD2.getId();
                tvD1.setText(String.valueOf(c));
                tvD2.setText(String.valueOf(c));
                break;
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

            if (imageView.getId() == ans1) {
                n++;
                correct1 += question + "\n";
                ansCorrect1++;
                if (n < 10) {
                    setUpQuestion();
                }
            }
            if (imageView.getId() == ans2) {
                n++;
                correct2 += question + "\n";
                ansCorrect2++;
                if (n < 10) {
                    setUpQuestion();
                }
            }
            if (n == 10) {
                Intent intent = new Intent(DuelActivity.this, ResultActivity.class);
                intent.putExtra(AppConstants.ACTIVITY, AppConstants.DUEL_ACTIVITY);
                intent.putExtra(AppConstants.PLAYER_1, String.valueOf(ansCorrect1));
                intent.putExtra(AppConstants.PLAYER_2, String.valueOf(ansCorrect2));
                intent.putExtra(AppConstants.C1_QUESTIONS, correct1);
                intent.putExtra(AppConstants.C2_QUESTIONS, correct2);
                startActivity(intent);
                finish();
            }
            updateScore();
        }
        return true;
    }

    private void updateScore() {
        tvCorrectDA1.setText(String.valueOf(ansCorrect1));
        tvCorrectDA2.setText(String.valueOf(ansCorrect2));
    }
}