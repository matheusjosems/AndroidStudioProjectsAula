package br.uniceub.pdm.myapplicationdesafio;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageButton;
import android.widget.SeekBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
//Agregaçoes
    private ImageButton playPauseButton;
    private MediaPlayer mp;
    private SeekBar seekBar;
    private Handler handler = new Handler();

    //Tipos primitivos
    private boolean isPlaying = false;

    //Metodos
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playPauseButton = findViewById(R.id.playPauseButton);
        mp = MediaPlayer.create(this, R.raw.lion_king_theme_song);
        seekBar = findViewById(R.id.seekbar);

        playPauseButton.setOnClickListener(v -> {
            if (isPlaying){
                playPauseButton.setImageResource(R.drawable.ic_play);
                mp.pause();
            }else{
                playPauseButton.setImageResource(R.drawable.ic_pause);
                mp.start();
            }
            isPlaying=!isPlaying;
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekbar, int progress, boolean fromUser){
                if (fromUser)
                    mp.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar){}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar){}
        });

    }
    private void atualizaBarraProgresso(){
        seekBar.setMax(mp.getDuration());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mp != null && isPlaying) {
                    seekBar.setProgress(mp.getCurrentPosition());
                    handler.postDelayed(this, 1000);
                    playPauseButton.setOnClickListener(v -> {
                        if (isPlaying) {
                            playPauseButton.setImageResource(R.drawable.ic_play);
                            mp.pause();
                        } else {
                            playPauseButton.setImageResource(R.drawable.ic_pause);
                            mp.start();
                            atualizaBarraProgresso();
                        }
                        isPlaying = !isPlaying;
                    });
                }
            }
        },1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mp !=  null){
            mp.release();
            mp = null;
        }
    }

}








