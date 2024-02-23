package com.example.sih;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    TextView scoreTextView;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        scoreTextView = findViewById(R.id.si03);
        progressBar=findViewById(R.id.progress_bar);

        Intent intent = getIntent();
        if (intent != null) {
            int score = intent.getIntExtra("SCORE", 0);
            scoreTextView.setText("Your Score: " + score);
            int j= score*10;
            progressBar.setProgress(j);
        }

    }
}