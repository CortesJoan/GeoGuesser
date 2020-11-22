package com.itb.androidquiz;

import java.util.ArrayList;

public class QuestionModel {
    int position;
    int numberOfCorrectAnswers = 0;
    int questionText;

    public ResponseModel[] getRespones() {
        return respones;
    }

    public void setRespones(ResponseModel[] respones) {
        this.respones = respones;
    }

    public void setAnswerPosition(ArrayList<Integer> answerPosition) {
        this.answerPosition = answerPosition;
    }

    ResponseModel[] respones;
    ArrayList<Integer> answerNum;
    ArrayList<Integer> answerPosition = new ArrayList<>();

    public int getNumberOfCorrectAnswers() {
        return numberOfCorrectAnswers;
    }

    public void setNumberOfCorrectAnswers(int numberOfCorrectAnswers) {
        this.numberOfCorrectAnswers = numberOfCorrectAnswers;
    }

    public QuestionModel(int questionText, ResponseModel[] responses) {

        this.questionText = questionText;
        this.answerNum = checkTheCorrectAnswer(responses);
        for (ResponseModel respons : responses) {


            if (respons.isAnswer()) {
                answerPosition.add(respons.getPosition());
            }
        }
        this.respones=responses;
    }

    public ArrayList<Integer> getAnswerPosition() {
        return answerPosition;
    }

    public int getQuestionText() {
        return questionText;
    }

    public void setQuestionText(int questionText) {
        this.questionText = questionText;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    public ArrayList<Integer> getAnswerNum() {
        return answerNum;
    }

    public void setAnswerNum(ArrayList<Integer> answerNum) {
        this.answerNum = answerNum;
    }


    ArrayList<Integer> checkTheCorrectAnswer(ResponseModel[] responses2) {
         ArrayList<Integer> correctAnswersPostions = new ArrayList<>();

        for (ResponseModel responseModel : responses2) {
            if (responseModel.isAnswer()) {
                numberOfCorrectAnswers++;

                correctAnswersPostions.add(responseModel.getPosition());

            }
        }
        if (correctAnswersPostions.size() == numberOfCorrectAnswers) {
            return correctAnswersPostions;
        }
        return new ArrayList<>(0);
    }
}
