package com.zam.mathforkids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{

    private ImageView ivPlay, ivOptions, ivExit;//, ivMore, ivShare, ivRate, ivPrivacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivPlay = findViewById(R.id.ivPlay);
        ivPlay.setOnTouchListener(this);

        ivOptions = findViewById(R.id.ivOptions);
        ivOptions.setOnTouchListener(this);

        ivExit = findViewById(R.id.ivExitMA);
        ivExit.setOnTouchListener(this);

        /*ivMore = findViewById(R.id.ivMore);
        ivMore.setOnTouchListener(this);

        ivShare = findViewById(R.id.ivShare);
        ivShare.setOnTouchListener(this);

        ivRate = findViewById(R.id.ivRate);
        ivRate.setOnTouchListener(this);

        ivPrivacy = findViewById(R.id.ivPrivacy);
        ivPrivacy.setOnTouchListener(this);*/
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ImageView imageView = (ImageView) v;

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            imageView.setColorFilter(getColor(R.color.transparent));
        }

        if(event.getAction() == MotionEvent.ACTION_UP){
            imageView.clearColorFilter();

            switch (v.getId()){
                case R.id.ivPlay:
                    Intent intentPlayMenu = new Intent(MainActivity.this, PlayMenuActivity.class);
                    startActivity(intentPlayMenu);
                    break;
                case R.id.ivOptions:
                    Intent intentOptionsActivity = new Intent(MainActivity.this, OptionsActivity.class);
                    startActivity(intentOptionsActivity);
                    break;
                case R.id.ivExitMA: finishAndRemoveTask(); break;
                /*case R.id.ivMore:
                    Intent intentMore = new Intent(Intent.ACTION_VIEW);
                    intentMore.setData(Uri.parse("https://play.google.com/store/apps/dev?id=5817203498005300153"));
                    startActivity(intentMore);
                    break;
                case R.id.ivShare:
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_message));
                    sendIntent.setType("text/plain");

                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    startActivity(shareIntent);
                    break;
                case R.id.ivRate:
                    Intent intentRate = new Intent(Intent.ACTION_VIEW);
                    intentRate.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.zam.mathforkids"));
                    startActivity(intentRate);
                    break;
                case R.id.ivPrivacy:
                    Intent intentPrivacy = new Intent(Intent.ACTION_VIEW);
                    intentPrivacy.setData(Uri.parse("https://zikrandmehr.github.io/Privacy-Policy-math-for-kids/"));
                    startActivity(intentPrivacy);
                    break;*/
            }
        }
        return true;
    }
}