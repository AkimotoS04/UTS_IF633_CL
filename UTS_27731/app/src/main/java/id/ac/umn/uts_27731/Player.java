package id.ac.umn.uts_27731;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Player extends AppCompatActivity {

    Button btnPlay, btnNext, btnPrev;
    TextView txtname, txtstart, txtstop;
    SeekBar seekmusic;
    ImageView images;

    String sname;
    public static final String EXTRA_NAME = "song_name";
    static MediaPlayer mediaPlayer;
    int position;
    ArrayList<File> mySongs;

    Handler handler = new Handler();
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        btnPlay = findViewById(R.id.playsong_button);
        btnNext = findViewById(R.id.nextsong_button);
        btnPrev = findViewById(R.id.prevsong_button);
        txtname = findViewById(R.id.tv_sn);
        txtstart = findViewById(R.id.tv_startsong);
        txtstop = findViewById(R.id.tv_endsong);
        seekmusic = findViewById(R.id.sb_timesong);
        images = findViewById(R.id.iv_music);

        if(mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        Intent i = getIntent();
        Bundle bundle = i.getExtras();

        mySongs = (ArrayList) bundle.getParcelableArrayList("songs");
        String songName = i.getStringExtra("songname");
        position = bundle.getInt("pos", 0);
        txtname.setSelected(true);
        Uri uri = Uri.parse(mySongs.get(position).toString());
        sname = mySongs.get(position).getName();
        txtname.setText(sname);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mediaPlayer.start();
        //set max on seek bar
        seekmusic.setMax(mediaPlayer.getDuration());
        //handler delay 0.5 sec
        handler.postDelayed(runnable,0);

        runnable = new Runnable() {
            @Override
            public void run() {
                //seekbar progress
                seekmusic.setProgress(mediaPlayer.getCurrentPosition());
                //handler delay 0.5 sec
                handler.postDelayed(this,500);
            }
        };

        //Song Duration variable
        int duration = mediaPlayer.getDuration();
        //ms to s & m
        String sDuration = convertFormat(duration);
        //set duration
        txtstop.setText(sDuration);

        seekmusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser) {
                    mediaPlayer.seekTo(progress);
                }
                txtstart.setText(convertFormat(mediaPlayer.getCurrentPosition()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    btnPlay.setBackgroundResource(R.drawable.ic_play_button);
                    mediaPlayer.pause();
                    mediaPlayer.pause();
                    handler.removeCallbacks(runnable);
                } else {
                    btnPlay.setBackgroundResource(R.drawable.ic_pause_button);
                    mediaPlayer.start();
                    //set max on seek bar
                    seekmusic.setMax(mediaPlayer.getDuration());
                    //start handler
                    handler.postDelayed(runnable, 0);
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                position = ((position + 1)%mySongs.size());
                Uri u = Uri.parse(mySongs.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), u);
                sname = mySongs.get(position).getName();
                txtname.setText(sname);
                mediaPlayer.start();
                btnPlay.setBackgroundResource(R.drawable.ic_pause_button);
                startAnimation(images);
                //set max on seek bar
                seekmusic.setMax(mediaPlayer.getDuration());
                //start handler
                handler.postDelayed(runnable, 0);
                //ms to s & m
                String sDuration = convertFormat(duration);
                //set duration
                txtstop.setText(sDuration);
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                btnNext.performClick();
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                position = ((position - 1)<0)?(mySongs.size()-1):(position-1);
                Uri u = Uri.parse(mySongs.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), u);
                sname = mySongs.get(position).getName();
                txtname.setText(sname);
                mediaPlayer.start();
                btnPlay.setBackgroundResource(R.drawable.ic_pause_button);
                startAnimation(images);
                //set max on seek bar
                seekmusic.setMax(mediaPlayer.getDuration());
                //start handler
                handler.postDelayed(runnable, 0);
                //ms to s & m
                String sDuration = convertFormat(duration);
                //set duration
                txtstop.setText(sDuration);
            }
        });
    }

    public void  startAnimation(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(images, "rotation", 0f, 360f);
        animator.setDuration(1000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator);
        animatorSet.start();
    }

    private String convertFormat(int duration) {
        return String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration) - (TimeUnit.MILLISECONDS.toMinutes(duration)) * 60);
    }

}