package com.zam.mathforkids.play;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.zam.mathforkids.play.game_mode.GameModeActivity;
import com.zam.mathforkids.R;
import com.zam.mathforkids.utils.AppConstants;

public class PlayMenuActivity extends AppCompatActivity implements View.OnTouchListener{

    private SharedPreferences sharedPreferences;
    private ImageView ivBackPMA, ivAddition, ivSubtraction, ivMultiplication, ivDivision;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_menu);

        sharedPreferences = getApplicationContext().getSharedPreferences(AppConstants.OPERATION, MODE_PRIVATE);

        ivBackPMA = findViewById(R.id.ivBackPMA);
        ivAddition = findViewById(R.id.ivAddition);
        ivSubtraction = findViewById(R.id.ivSubtraction);
        ivMultiplication = findViewById(R.id.ivMultiplication);
        ivDivision = findViewById(R.id.ivDivision);

        setupViews();
    }

    private void setupViews() {
        ivBackPMA.setOnTouchListener(this);
        ivAddition.setOnTouchListener(this);
        ivSubtraction.setOnTouchListener(this);
        ivMultiplication.setOnTouchListener(this);
        ivDivision.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ImageView imageView = (ImageView) v;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            imageView.setColorFilter(getColor(R.color.transparent));
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            imageView.clearColorFilter();

            SharedPreferences.Editor editor = sharedPreferences.edit();

            switch (v.getId()) {
                case R.id.ivBackPMA:
                    finish();
                    break;
                case R.id.ivAddition:
                    Intent intentAddition = new Intent(PlayMenuActivity.this, GameModeActivity.class);
                    editor.putString(AppConstants.KEY_OPERATION, AppConstants.ADDITION);
                    startActivity(intentAddition);
                    break;
                case R.id.ivSubtraction:
                    Intent intentSubtraction = new Intent(PlayMenuActivity.this, GameModeActivity.class);
                    editor.putString(AppConstants.KEY_OPERATION, AppConstants.SUBTRACTION);
                    startActivity(intentSubtraction);
                    break;
                case R.id.ivMultiplication:
                    Intent intentMultiplication = new Intent(PlayMenuActivity.this, GameModeActivity.class);
                    editor.putString(AppConstants.KEY_OPERATION, AppConstants.MULTIPLICATION);
                    startActivity(intentMultiplication);
                    break;
                case R.id.ivDivision:
                    Intent intentDivision = new Intent(PlayMenuActivity.this, GameModeActivity.class);
                    editor.putString(AppConstants.KEY_OPERATION, AppConstants.DIVISION);
                    startActivity(intentDivision);
                    break;
            }

            editor.apply();
        }
        return true;
    }
}