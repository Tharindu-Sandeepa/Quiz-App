package com.example.quiz;

public class QuestionAnswer {


    public static String question[] ={
            "Which company owns the android?",
            "Which one is not the programming language?",


    };

    public static String choices[][] = {
            {"Google","Apple","Nokia","Samsung"},
            {"Java","Kotlin","Notepad","Python"},


    };

    public static String correctAnswers[] = {
            "Google",
            "Notepad"


    };

    public static void addNewQuestion(String newQuestion, String choiceA, String choiceB, String choiceC, String choiceD, String correctAnswer) {
        String[] newQuestions = new String[question.length + 1];
        String[][] newChoices = new String[choices.length + 1][4];
        String[] newCorrectAnswers = new String[correctAnswers.length + 1];

        System.arraycopy(question, 0, newQuestions, 0, question.length);
        newQuestions[question.length] = newQuestion;

        for (int i = 0; i < choices.length; i++) {
            System.arraycopy(choices[i], 0, newChoices[i], 0, choices[i].length);
        }
        newChoices[choices.length] = new String[]{choiceA, choiceB, choiceC, choiceD};

        System.arraycopy(correctAnswers, 0, newCorrectAnswers, 0, correctAnswers.length);
        newCorrectAnswers[correctAnswers.length] = correctAnswer;

        question = newQuestions;
        choices = newChoices;
        correctAnswers = newCorrectAnswers;
    }
}
