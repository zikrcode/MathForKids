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

package com.zam.mathforkids.settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.slider.Slider;
import com.zam.mathforkids.R;
import com.zam.mathforkids.utils.AppConstants;

public class OptionsActivity extends AppCompatActivity implements View.OnTouchListener, Slider.OnChangeListener{

    private SharedPreferences sharedPreferences;
    private ImageView ivBackOA;
    private TextView tvSecondsOA, tvMinOA, tvMaxOA;
    private Slider sSecondsOA, sMinOA, sMaxOA;
    private int seconds = 10000, min = 10, max = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getApplicationContext().getSharedPreferences(AppConstants.DIGIT_RANGE, MODE_PRIVATE);
        setContentView(R.layout.activity_options);

        tvSecondsOA = findViewById(R.id.tvSecondsOA);
        sSecondsOA = findViewById(R.id.sSecondsOA);

        tvMinOA = findViewById(R.id.tvMinOA);
        sMinOA = findViewById(R.id.sMinOA);

        tvMaxOA = findViewById(R.id.tvMaxOA);
        sMaxOA = findViewById(R.id.sMaxOA);

        setupViews();
    }

    private void setupViews() {
        ivBackOA = findViewById(R.id.ivBackOA);
        ivBackOA.setOnTouchListener(this);

        setupTimeLimit();
        setupMinNumber();
        setupMaxNumber();
    }

    private void setupTimeLimit() {
        seconds = sharedPreferences.getInt(AppConstants.KEY_SECONDS, AppConstants.KEY_SECONDS_DEF);

        tvSecondsOA.setText(String.valueOf(seconds / 1000));
        sSecondsOA.setValue(seconds / 1000f);
        sSecondsOA.addOnChangeListener(this);
    }

    private void setupMinNumber() {
        min = sharedPreferences.getInt(AppConstants.KEY_MIN, AppConstants.KEY_MIN_DEF);

        tvMinOA.setText(String.valueOf(min));
        sMinOA.setValue(min);
        sMinOA.addOnChangeListener(this);
    }

    private void setupMaxNumber() {
        max = sharedPreferences.getInt(AppConstants.KEY_MAX, AppConstants.KEY_MAX_DEF);

        tvMaxOA.setText(String.valueOf(max));
        sMaxOA.setValue(max);
        sMaxOA.addOnChangeListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ImageView imageView = (ImageView) v;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            imageView.setColorFilter(getColor(R.color.transparent));
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            imageView.clearColorFilter();
            finish();
        }

        return true;
    }

    @Override
    @SuppressLint("RestrictedApi")
    public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {

        if (slider.getId() == R.id.sSecondsOA) {
            tvSecondsOA.setText(String.valueOf((int)value));
            seconds = (int) value * 1000;
        }
        if (slider.getId() == R.id.sMinOA) {
            if (value < max) {
                tvMinOA.setText(String.valueOf((int)value));
                min = (int) value;
            }
            else {
                sMinOA.setValue(min);
            }
        }
        if (slider.getId() == R.id.sMaxOA) {
            if (min < value){
                tvMaxOA.setText(String.valueOf((int)value));
                max = (int) value;
            }
            else {
                sMaxOA.setValue(max);
            }
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(AppConstants.KEY_SECONDS, seconds);
        editor.putInt(AppConstants.KEY_MIN, min);
        editor.putInt(AppConstants.KEY_MAX, max);
        editor.apply();
    }
}