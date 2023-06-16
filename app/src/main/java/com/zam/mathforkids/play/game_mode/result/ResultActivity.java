package com.zam.mathforkids.play.game_mode.result;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zam.mathforkids.R;
import com.zam.mathforkids.play.game_mode.duel.DuelActivity;
import com.zam.mathforkids.play.game_mode.practice.PracticeActivity;
import com.zam.mathforkids.play.game_mode.quiz.QuizActivity;
import com.zam.mathforkids.play.game_mode.time.TimeActivity;
import com.zam.mathforkids.utils.AppConstants;

public class ResultActivity extends AppCompatActivity implements View.OnTouchListener {

    private TextView tv1RA, tv2RA, tvAns1RA, tvAns2RA, tvCorrectRA, tvWrongRA;
    private ImageView ivRestartRA, ivExitRA;
    private String activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tv1RA = findViewById(R.id.tv1RA);
        tv2RA = findViewById(R.id.tv2RA);

        tvAns1RA = findViewById(R.id.tvAns1RA);
        tvAns2RA = findViewById(R.id.tvAns2RA);

        tvCorrectRA = findViewById(R.id.tvCorrectRA);
        tvWrongRA = findViewById(R.id.tvWrongRA);

        ivRestartRA = findViewById(R.id.ivRestartRA);
        ivExitRA = findViewById(R.id.ivExitRA);

        setupViews();
    }

    private void setupViews() {
        activity = getIntent().getStringExtra(AppConstants.ACTIVITY);

        if (activity.equals(AppConstants.DUEL_ACTIVITY)) {
            setupDuelResult();
        }
        else {
            setupResult();
        }

        ivRestartRA.setOnTouchListener(this);
        ivExitRA.setOnTouchListener(this);
    }

    private void setupDuelResult() {
        tv1RA.setText(getText(R.string.player_1));

        tv2RA.setText(getText(R.string.player_2));
        tv2RA.setTextColor(getColor(R.color.green));

        tvAns1RA.setText(
                getText(R.string.equal_sign) + getIntent().getStringExtra(AppConstants.PLAYER_1)
        );

        tvAns2RA.setText(
                getText(R.string.equal_sign) + getIntent().getStringExtra(AppConstants.PLAYER_2)
        );
        tvAns2RA.setTextColor(getColor(R.color.green));

        tvCorrectRA.setText(getIntent().getStringExtra(AppConstants.C1_QUESTIONS));

        tvWrongRA.setText(getIntent().getStringExtra(AppConstants.C2_QUESTIONS));
        tvWrongRA.setTextColor(getColor(R.color.green));
    }

    private void setupResult() {
        tv1RA.setText(getText(R.string.correct));
        tv2RA.setText(getText(R.string.wrong));

        tvAns1RA.setText(
                getText(R.string.equal_sign) + getIntent().getStringExtra(AppConstants.CORRECT)
        );
        tvAns2RA.setText(
                getText(R.string.equal_sign) + getIntent().getStringExtra(AppConstants.WRONG)
        );

        tvCorrectRA.setText(getIntent().getStringExtra(AppConstants.C_QUESTIONS));
        tvWrongRA.setText(getIntent().getStringExtra(AppConstants.W_QUESTIONS));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ImageView imageView = (ImageView) v;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            imageView.setColorFilter(getColor(R.color.transparent));
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            imageView.clearColorFilter();

            if (v.getId() == R.id.ivRestartRA) {
                Intent intentRestart;

                switch (activity) {
                    case AppConstants.PRACTICE_ACTIVITY:
                        intentRestart = new Intent(ResultActivity.this, PracticeActivity.class);
                        break;
                    case AppConstants.QUIZ_ACTIVITY:
                        intentRestart = new Intent(ResultActivity.this, QuizActivity.class);
                        break;
                    case AppConstants.DUEL_ACTIVITY:
                        intentRestart = new Intent(ResultActivity.this, DuelActivity.class);
                        break;
                    default:
                        intentRestart = new Intent(ResultActivity.this, TimeActivity.class);
                        break;
                }
                startActivity(intentRestart);
            }
            finish();
        }
        return true;
    }
}