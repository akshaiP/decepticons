package com.example.sih;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    private List<Question> questions;


    public QuestionAdapter(List<Question> questions) {
        this.questions = questions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Question question = questions.get(position);
        holder.questionTextView.setText(question.getQuestionText());
        holder.answerRadioGroup.setOnCheckedChangeListener(null); // Reset listener to prevent multiple calls
        holder.answerRadioGroup.check(question.getUserAnswer() ? R.id.yesRadioButton : R.id.noRadioButton);
        holder.answerRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            boolean answer = checkedId == R.id.yesRadioButton;
            question.setUserAnswer(answer);
        });
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView questionTextView;
        RadioGroup answerRadioGroup;

        public ViewHolder(View itemView) {
            super(itemView);
            questionTextView = itemView.findViewById(R.id.questionTextView);
            answerRadioGroup = itemView.findViewById(R.id.answerRadioGroup);
        }
    }
}