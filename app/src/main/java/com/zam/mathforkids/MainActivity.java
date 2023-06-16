package com.zam.mathforkids;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.zam.mathforkids.play.PlayMenuActivity;
import com.zam.mathforkids.settings.OptionsActivity;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{

    private ImageView ivPlay, ivOptions, ivExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen.installSplashScreen(this);
        setContentView(R.layout.activity_main);

        ivPlay = findViewById(R.id.ivPlay);
        ivOptions = findViewById(R.id.ivOptions);
        ivExit = findViewById(R.id.ivExitMA);

        setupViews();
    }

    private void setupViews() {
        ivPlay.setOnTouchListener(this);
        ivOptions.setOnTouchListener(this);
        ivExit.setOnTouchListener(this);
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
                case R.id.ivPlay:
                    Intent intentPlayMenu = new Intent(MainActivity.this, PlayMenuActivity.class);
                    startActivity(intentPlayMenu);
                    break;
                case R.id.ivOptions:
                    Intent intentOptionsActivity = new Intent(MainActivity.this, OptionsActivity.class);
                    startActivity(intentOptionsActivity);
                    break;
                case R.id.ivExitMA: finishAndRemoveTask();
                    break;
            }
        }
        return true;
    }
}