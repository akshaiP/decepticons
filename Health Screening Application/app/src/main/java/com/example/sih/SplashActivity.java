package com.example.sih;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SplashActivity extends AppCompatActivity {

    private TextView appName;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        appName = findViewById(R.id.app_name);

       // Typeface typeface = ResourcesCompat.getFont(this, R.font.blacklist);
      //  appName.setTypeface(typeface);

      //  Animation anim = AnimationUtils.loadAnimation(this, R.anim.myanim);
      //  appName.setAnimation(anim);

        mAuth= FirebaseAuth.getInstance();

       DbQuery.g_firestore = FirebaseFirestore.getInstance();

        new Thread() {

            @Override
            public void run()
            {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(SplashActivity.this,loginActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();

            }


        }.start();
    }
}