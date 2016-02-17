package rta.ae.sharekni;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

import rta.ae.sharekni.OnBoardDir.OnboardingActivity;

import rta.ae.sharekni.R;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final VideoView videoView = (VideoView) findViewById(R.id.videoView);
        videoView.setVideoPath("http://www.sdg.ae/v.mp4");

        MediaController mediaController= new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        videoView.start();

//        try {
//
//            VideoView videoHolder = new VideoView(this);
//            setContentView(videoHolder);
//            Uri video = Uri.parse("https://www.thenewboston.com/forum/project_files/006_testVideo.mp4");
//
//            videoHolder.setVideoPath("http://www.sdg.ae/intro.mp4");
//
//
//            videoHolder.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                public void onCompletion(MediaPlayer mp) {
//                    jump();
//                }
//            });
//            videoHolder.start();
//        } catch (Exception ex) {
//            jump();
//        }
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        jump();
//        return true;
//    }

    private void jump() {
//it is safe to use this code even if you
//do not intend to allow users to skip the splash
        if (isFinishing())
            return;
        startActivity(new Intent(this, OnboardingActivity.class));
        finish();
    }





}
