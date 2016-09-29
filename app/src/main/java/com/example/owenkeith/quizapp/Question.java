package com.example.owenkeith.quizapp;

/**
 *
 * Created by csaper6 on 9/15/16.
 */
public class Question {
    private int questionID;
    private boolean isAnswerTrue;

    public Question(boolean isAnswerTrue, int questionID) {
        this.isAnswerTrue = isAnswerTrue;
        this.questionID = questionID;
    }

    public int getQuestionID() {
        return questionID;
    }

    public boolean isAnswerTrue() {
        return isAnswerTrue;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public void setAnswerTrue(boolean answerTrue) {
        isAnswerTrue = answerTrue;
    }

    public boolean checkAnswer(boolean answer){
        return (answer=isAnswerTrue);
    }
}
