package com.itb.androidquiz;

public class ResponseModel {
    public ResponseModel(int answerText, boolean answer,int position) {
        this.answerText = answerText;
        this.answer = answer;
        this.position=position;
    }

    int answerText;
    boolean answer;
    int position;

    public int getAnswerText() {
        return answerText;
    }

    public void setAnswerText(int answerText) {
        this.answerText = answerText;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isAnswer() {
        return answer;
    }
}
