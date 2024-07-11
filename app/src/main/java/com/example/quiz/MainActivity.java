package com.example.quiz;


import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView totalQuestionsTextView;
    TextView questionTextView;
    Button ansA, ansB, ansC, ansD;
    Button submitBtn, addQuestionBtn;

    int score = 0;
    int totalQuestion = QuestionAnswer.question.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalQuestionsTextView = findViewById(R.id.total_question);
        questionTextView = findViewById(R.id.question);
        ansA = findViewById(R.id.ans_A);
        ansB = findViewById(R.id.ans_B);
        ansC = findViewById(R.id.ans_C);
        ansD = findViewById(R.id.ans_D);
        submitBtn = findViewById(R.id.submit_btn);
        addQuestionBtn = findViewById(R.id.add_question_btn);

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
        addQuestionBtn.setOnClickListener(this);

        totalQuestionsTextView.setText("Total questions: " + totalQuestion);

        loadNewQuestion();
    }

    @Override
    public void onClick(View view) {
        ansA.setBackgroundColor(Color.WHITE);
        ansB.setBackgroundColor(Color.WHITE);
        ansC.setBackgroundColor(Color.WHITE);
        ansD.setBackgroundColor(Color.WHITE);

        Button clickedButton = (Button) view;
        if (clickedButton.getId() == R.id.submit_btn) {
            if (selectedAnswer.equals(QuestionAnswer.correctAnswers[currentQuestionIndex])) {
                score++;
            }
            currentQuestionIndex++;
            loadNewQuestion();
        } else if (clickedButton.getId() == R.id.add_question_btn) {
            showAddQuestionDialog();
        } else {
            selectedAnswer = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.MAGENTA);
        }
    }

    private void showAddQuestionDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_question, null);
        dialogBuilder.setView(dialogView);

        EditText newQuestionEditText = dialogView.findViewById(R.id.edit_new_question);
        EditText newChoiceAEditText = dialogView.findViewById(R.id.edit_new_choice_a);
        EditText newChoiceBEditText = dialogView.findViewById(R.id.edit_new_choice_b);
        EditText newChoiceCEditText = dialogView.findViewById(R.id.edit_new_choice_c);
        EditText newChoiceDEditText = dialogView.findViewById(R.id.edit_new_choice_d);
        EditText newCorrectAnswerEditText = dialogView.findViewById(R.id.edit_new_correct_answer);

        dialogBuilder.setTitle("Add New Question");
        dialogBuilder.setPositiveButton("Add", (dialog, which) -> {
            String newQuestion = newQuestionEditText.getText().toString();
            String newChoiceA = newChoiceAEditText.getText().toString();
            String newChoiceB = newChoiceBEditText.getText().toString();
            String newChoiceC = newChoiceCEditText.getText().toString();
            String newChoiceD = newChoiceDEditText.getText().toString();
            String newCorrectAnswer = newCorrectAnswerEditText.getText().toString();

            QuestionAnswer.addNewQuestion(newQuestion, newChoiceA, newChoiceB, newChoiceC, newChoiceD, newCorrectAnswer);

            totalQuestion++; // Increment the total number of questions
            loadNewQuestion();
        });
        dialogBuilder.setNegativeButton("Cancel", null);

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }


    private void loadNewQuestion() {
        if (currentQuestionIndex == totalQuestion) {
            finishQuiz();
            return;
        }

        questionTextView.setText(QuestionAnswer.question[currentQuestionIndex]);
        ansA.setText(QuestionAnswer.choices[currentQuestionIndex][0]);
        ansB.setText(QuestionAnswer.choices[currentQuestionIndex][1]);
        ansC.setText(QuestionAnswer.choices[currentQuestionIndex][2]);
        ansD.setText(QuestionAnswer.choices[currentQuestionIndex][3]);
    }

    private void finishQuiz() {
        String passStatus = (score > totalQuestion * 0.60) ? "Passed" : "Failed";

        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Score is " + score + " out of " + totalQuestion)
                .setPositiveButton("Restart", (dialogInterface, i) -> restartQuiz())
                .setCancelable(false)
                .show();
    }

    private void restartQuiz() {
        score = 0;
        currentQuestionIndex = 0;
        loadNewQuestion();
    }
}
