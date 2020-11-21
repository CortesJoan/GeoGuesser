package com.itb.androidquiz;

import java.util.ArrayList;

public class QuestionModel {
    int position;
    int numberOfCorrectAnswers = 0;
    int questionText;
    ArrayList<Integer> answerNum;
    ArrayList<Integer> answerPosition = new ArrayList<>() ;

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


            //  r.getAnswerText();
            if (respons.isAnswer()) {
                answerPosition.add(respons.getPosition());
            }
        }
//        responses[questionText].getPosition();
    }

    public   ArrayList<Integer> getAnswerPosition() {
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
        int numberOfCurrentCorrectAnswers = 0;
        ArrayList<Integer> correctAnswersPostions = new ArrayList<Integer>();
        //  int[] correctAnswersPostions=new int[numberOfCorrectAnswers];
        for (ResponseModel responseModel : responses2) {
            if (responseModel.isAnswer()) {
                numberOfCorrectAnswers++;
                //correctAnswersPostions[i]=responses2[i].getPosition();
                correctAnswersPostions.add(responseModel.getPosition());

            }
        }
        if (correctAnswersPostions.size() == numberOfCorrectAnswers) {
            return correctAnswersPostions;
        }
        return new ArrayList<Integer>(0);
    }
}
