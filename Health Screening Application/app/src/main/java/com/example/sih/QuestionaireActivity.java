package com.example.sih;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sih.R.id;

import java.util.ArrayList;
import java.util.List;

public class QuestionaireActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private QuestionAdapter questionAdapter;
    private List<Question> questions;
    private Button nextButton;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionaire);

        recyclerView = findViewById(R.id.questionRecyclerView);
        nextButton = findViewById(R.id.nextButton);

        questions = new ArrayList<>();
        questions.add(new Question("Have you felt loneliness and overthinking?"));
        questions.add(new Question("Have you been feeling more anxious or stressed than usual recently?"));
        questions.add(new Question("Have you been experiencing persistent feelings of sadness or \n" +
                "depression lately?"));
        questions.add(new Question("Have you been sleeping well and feeling rested?"));
        questions.add(new Question("Have you been experiencing changes in your appetite than usual?"));
        questions.add(new Question("Have you been losing confidence in yourself?"));
        questions.add(new Question("Have you been less socialized than usual?"));
        questions.add(new Question("Have you been able to cope with the demands and decisions in your \n" +
                "life?"));
        questions.add(new Question("Have you been thinking of yourself as a worthless person?"));
        questions.add(new Question("Does your emoCons prevent you from normal funcConing in day-to\u0002day life?"));
        questions.add(new Question("Have you been ever received treatment for depression, anxiety, or \n" +
                "other mental health problem in the past?\n"));




        questionAdapter = new QuestionAdapter(questions);
        recyclerView.setAdapter(questionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int score = calculateScore();
                Intent intent = new Intent(QuestionaireActivity.this, ScoreActivity.class);
                intent.putExtra("SCORE", score);
                startActivity(intent);
            }
        });
    }

    private int calculateScore() {
        int score = 0;
        for (Question question : questions) {
            if (question.getUserAnswer()) {
                score++;
            }
        }
        return score;
    }
}