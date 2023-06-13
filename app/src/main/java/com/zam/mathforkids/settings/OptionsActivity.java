package com.zam.mathforkids.settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.slider.Slider;
import com.zam.mathforkids.R;

public class OptionsActivity extends AppCompatActivity implements View.OnTouchListener, Slider.OnChangeListener{

    private SharedPreferences sharedPreferences;
    private ImageView ivBackOA;
    private TextView tvSecondsOA, tvMinOA, tvMaxOA;
    private Slider sSecondsOA, sMinOA, sMaxOA;
    private int seconds=10000, min=10, max=20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        sharedPreferences = getApplicationContext().getSharedPreferences("DIGIT_RANGE",MODE_PRIVATE);

        setContentView(R.layout.activity_options);

        ivBackOA = findViewById(R.id.ivBackOA);
        ivBackOA.setOnTouchListener(this);

        seconds = sharedPreferences.getInt("SECONDS",10000);
        tvSecondsOA = findViewById(R.id.tvSecondsOA);
        tvSecondsOA.setText(String.valueOf(seconds/1000));
        min = sharedPreferences.getInt("MIN",10);
        tvMinOA = findViewById(R.id.tvMinOA);
        tvMinOA.setText(String.valueOf(min));
        max = sharedPreferences.getInt("MAX",20);
        tvMaxOA = findViewById(R.id.tvMaxOA);
        tvMaxOA.setText(String.valueOf(max));

        sSecondsOA = findViewById(R.id.sSecondsOA);
        sSecondsOA.setValue((sharedPreferences.getInt("SECONDS",10000))/1000);
        sSecondsOA.addOnChangeListener(this);
        sMinOA = findViewById(R.id.sMinOA);
        sMinOA.setValue(sharedPreferences.getInt("MIN",10));
        sMinOA.addOnChangeListener(this);
        sMaxOA = findViewById(R.id.sMaxOA);
        sMaxOA.setValue(sharedPreferences.getInt("MAX",20));
        sMaxOA.addOnChangeListener(this);

        /*sMinOA.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            @SuppressLint("RestrictedApi")
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {

            }
        });

        sMaxOA.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            @SuppressLint("RestrictedApi")
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {

            }
        });

        tbOA = findViewById(R.id.tbOA);
        tbOA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    uiMode=AppCompatDelegate.MODE_NIGHT_YES;
                }
                else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    uiMode=AppCompatDelegate.MODE_NIGHT_NO;
                }
            }
        });*/
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ImageView imageView = (ImageView) v;

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            imageView.setColorFilter(getColor(R.color.transparent));
        }
        if(event.getAction() == MotionEvent.ACTION_UP){
            imageView.clearColorFilter();
            finish();
        }
        return true;
    }

    @Override
    @SuppressLint("RestrictedApi")
    public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {

        if (slider.getId()==R.id.sSecondsOA){
            tvSecondsOA.setText(String.valueOf((int)value));
            seconds = (int) value * 1000;
        }
        if (slider.getId()==R.id.sMinOA){
            if (value<max){
                tvMinOA.setText(String.valueOf((int)value));
                min = (int) value;
            }
            else {
                sMinOA.setValue(min);
            }
        }
        if (slider.getId()==R.id.sMaxOA){
            if (min<value){
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