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

package com.zam.mathforkids.play.game_mode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
        setContentView(R.layout.activity_game_mode);

        ivBackGMA = findViewById(R.id.ivBackGMA);
        ivPractice = findViewById(R.id.ivPractice);
        ivQuiz = findViewById(R.id.ivQuiz);
        ivDuel = findViewById(R.id.ivDuel);
        ivTime = findViewById(R.id.ivTime);

        setupViews();
    }

    private void setupViews() {
        ivBackGMA.setOnTouchListener(this);
        ivPractice.setOnTouchListener(this);
        ivQuiz.setOnTouchListener(this);
        ivDuel.setOnTouchListener(this);
        ivTime.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ImageView imageView = (ImageView) v;
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            imageView.setColorFilter(getColor(R.color.transparent));
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            imageView.clearColorFilter();

            switch (v.getId()) {
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