package com.zam.mathforkids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class PlayMenuActivity extends AppCompatActivity implements View.OnTouchListener{

    private SharedPreferences sharedPreferences;
    private ImageView ivBackPMA, ivAddition, ivSubtraction, ivMultiplication, ivDivision;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_menu);

        sharedPreferences = getApplicationContext().getSharedPreferences("OPERATION",MODE_PRIVATE);

        ivBackPMA=findViewById(R.id.ivBackPMA);
        ivBackPMA.setOnTouchListener(this);

        ivAddition = findViewById(R.id.ivAddition);
        ivAddition.setOnTouchListener(this);

        ivSubtraction = findViewById(R.id.ivSubtraction);
        ivSubtraction.setOnTouchListener(this);

        ivMultiplication = findViewById(R.id.ivMultiplication);
        ivMultiplication.setOnTouchListener(this);

        ivDivision =findViewById(R.id.ivDivision);
        ivDivision.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ImageView imageView = (ImageView) v;

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            imageView.setColorFilter(getColor(R.color.transparent));
        }
        if(event.getAction() == MotionEvent.ACTION_UP){
            imageView.clearColorFilter();

            SharedPreferences.Editor editor = sharedPreferences.edit();

            switch (v.getId()){
                case R.id.ivBackPMA: finish(); break;
                case R.id.ivAddition:
                    Intent intentAddition = new Intent(PlayMenuActivity.this, GameModeActivity.class);
                    editor.putString("Operation", "+");
                    startActivity(intentAddition);
                    break;
                case R.id.ivSubtraction:
                    Intent intentSubtraction = new Intent(PlayMenuActivity.this, GameModeActivity.class);
                    editor.putString("Operation", "-");
                    startActivity(intentSubtraction);
                    break;
                case R.id.ivMultiplication:
                    Intent intentMultiplication = new Intent(PlayMenuActivity.this, GameModeActivity.class);
                    editor.putString("Operation", "×");
                    startActivity(intentMultiplication);
                    break;
                case R.id.ivDivision:
                    Intent intentDivision = new Intent(PlayMenuActivity.this, GameModeActivity.class);
                    editor.putString("Operation", "÷");
                    startActivity(intentDivision);
                    break;
            }

            editor.apply();
        }
        return true;
    }
}