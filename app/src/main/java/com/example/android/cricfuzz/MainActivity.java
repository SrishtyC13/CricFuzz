package com.example.android.cricfuzz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView bgapp,clover;
    Animation bganim,cloveranim,frombottom;
    LinearLayout textSplash,texthome,menus;
    Button liveScoreButton,quizButton;

    public void liveScore(View view){
        Intent intent=new Intent(this,liveScore.class);
        startActivity(intent);
    }    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frombottom=AnimationUtils.loadAnimation(this,R.anim.frombottom);
        bgapp=(ImageView)findViewById(R.id.bgapp);
        clover=(ImageView)findViewById(R.id.clover);
        textSplash=(LinearLayout)findViewById(R.id.textsplash);
        texthome=(LinearLayout)findViewById(R.id.texthome);
        menus=(LinearLayout)findViewById(R.id.menus);


        bgapp.animate().translationY(-1700).setDuration(800).setStartDelay(300);
        clover.animate().translationX(-2500).alpha(0).setDuration(800).setStartDelay(600);
        textSplash.animate().translationY(140).alpha(0).setDuration(800).setStartDelay(300);

        texthome.startAnimation(frombottom);
        menus.startAnimation(frombottom);
        liveScoreButton=(Button)findViewById(R.id.liveScoreButton);
        liveScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,liveScore.class);
                startActivity(intent);
            }
        });

    }
}
