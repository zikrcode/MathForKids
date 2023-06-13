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

public class OptionsActivity extends AppCompatActivity implements View.OnTouchListener, Slider.OnChangeListener{

    private SharedPreferences sharedPreferences;
    private ImageView ivBackOA;
    private TextView tvSecondsOA, tvMinOA, tvMaxOA;
    private Slider sSecondsOA, sMinOA, sMaxOA;
    private int seconds = 10000, min = 10, max = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getApplicationContext().getSharedPreferences("DIGIT_RANGE",MODE_PRIVATE);
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
        seconds = sharedPreferences.getInt("SECONDS",10000);

        tvSecondsOA.setText(String.valueOf(seconds/1000));
        sSecondsOA.setValue(seconds/1000f);
        sSecondsOA.addOnChangeListener(this);
    }

    private void setupMinNumber() {
        min = sharedPreferences.getInt("MIN",10);

        tvMinOA.setText(String.valueOf(min));
        sMinOA.setValue(min);
        sMinOA.addOnChangeListener(this);
    }

    private void setupMaxNumber() {
        max = sharedPreferences.getInt("MAX",20);

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
        editor.putInt("SECONDS", seconds);
        editor.putInt("MIN", min);
        editor.putInt("MAX", max);
        editor.apply();
    }
}