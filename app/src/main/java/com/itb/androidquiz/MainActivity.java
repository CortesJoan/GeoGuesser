package com.itb.androidquiz;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {
    TextView questionText;
    TextView scoreText;
 //   TextView progressText;
    Button option1;
    Button option2;
    Button option3;
    Button option4;
    Button pista;
    double score=0;
   ProgressBar progressBar;
    AlertDialog.Builder finalDialogueBuilder;
    AlertDialog finalDialogue;
    int numeroPreguntaActual = 0;
    QuestionModel preguntaActual = questionBank[numeroPreguntaActual];
 ViewModel  viewModel;

    static void shuffleArray(QuestionModel[] arn) {
     Random rand = new Random();

        for (int i = 0; i < arn.length; i++) {
            int randomIndexToSwap = rand.nextInt(arn.length);
            QuestionModel temp = arn[randomIndexToSwap];
            arn[randomIndexToSwap] = arn[i];
            arn[i] = temp;
        }
        System.out.println(Arrays.toString(arn));
    }
    static final QuestionModel[] questionBank = {
            new QuestionModel(R.string.q0, new ResponseModel[]{new ResponseModel(R.string.a1q0,false,1)
                    ,new ResponseModel(R.string.a2q0,false,2),new ResponseModel(R.string.a3q0,true,3),new ResponseModel(R.string.a4q0,false,4)}),
            new QuestionModel(R.string.q1, new ResponseModel[]{new ResponseModel(R.string.a1q0,false,1)
                    ,new ResponseModel(R.string.a2q0,false,2),new ResponseModel(R.string.a3q0,true,3),new ResponseModel(R.string.a4q0,false,4)}),
            new QuestionModel(R.string.q2, new ResponseModel[]{new ResponseModel(R.string.a1q0,false,1)
                    ,new ResponseModel(R.string.a2q0,false,2),new ResponseModel(R.string.a3q0,true,3),new ResponseModel(R.string.a4q0,false,4)}),
            new QuestionModel(R.string.q3, new ResponseModel[]{new ResponseModel(R.string.a1q0,false,1)
                    ,new ResponseModel(R.string.a2q0,false,2),new ResponseModel(R.string.a3q0,true,3),new ResponseModel(R.string.a4q0,false,4)}),
            new QuestionModel(R.string.q4, new ResponseModel[]{new ResponseModel(R.string.a1q0,false,1)
                    ,new ResponseModel(R.string.a2q0,false,2),new ResponseModel(R.string.a3q0,true,3),new ResponseModel(R.string.a4q0,false,4)}),
            new QuestionModel(R.string.q5, new ResponseModel[]{new ResponseModel(R.string.a1q0,false,1)
                    ,new ResponseModel(R.string.a2q0,true,2),new ResponseModel(R.string.a3q0,false,3),new ResponseModel(R.string.a4q0,true,4)}),
            new QuestionModel(R.string.q0, new ResponseModel[]{new ResponseModel(R.string.a1q0,false,1)
                    ,new ResponseModel(R.string.a2q0,false,2),new ResponseModel(R.string.a3q0,false,3),new ResponseModel(R.string.a4q0,true,4)}),


    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        shuffleArray(questionBank);

        System.out.println();
        if (savedInstanceState!=null) numeroPreguntaActual=savedInstanceState.getInt("numeroPreguntaActual");

        questionText = findViewById(R.id.questionText);
     //   progressText = findViewById(R.id.progressText);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4= findViewById(R.id.option4);
        scoreText=findViewById(R.id.scoreText);
       progressBar = findViewById(R.id.progressBar);
        finalDialogueBuilder= new AlertDialog.Builder(MainActivity.this);
        finalDialogueBuilder.setTitle(R.string.congratulationText);
        finalDialogueBuilder.setMessage(R.string.nextText);
        finalDialogueBuilder.setNegativeButton(R.string.negativeButtonText,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              // cambiarPregunta();
                tornarApreguntaInicial();
            }});
        finalDialogueBuilder.setPositiveButton(R.string.positiveButtonText,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();


            }});
        finalDialogue = finalDialogueBuilder.create();

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprovarResultat(1, preguntaActual);
            }
        });
        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprovarResultat(2, preguntaActual);
            }
        });
        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprovarResultat(3, preguntaActual);
            }
        });  option4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    comprovarResultat(4, preguntaActual);
                }
        });
        if (viewModel==null) {
            viewModel = new ViewModelProvider(this).get(QuizViewModel.class);
            actualitzarPantalla();
        }else {
            tornarApreguntaInicial();
        }
    }

    private void tornarApreguntaInicial() {
        shuffleArray(questionBank);
score=0;
        numeroPreguntaActual = 0;
preguntaActual= questionBank[numeroPreguntaActual];
       progressBar.setProgress(progressBar.getMax() / questionBank.length);
        actualitzarPantalla();
    }

    private void comprovarResultat(int resposta, QuestionModel preguntaActual) {
        boolean respostaCorrecte=false;
            ArrayList<Integer> p1 = preguntaActual.getAnswerPosition();
        for (int i = 0; i < p1.size() ; i++) {
            System.out.println(p1.indexOf(i) == resposta);
                  if (p1.get(i) == resposta){
                      respostaCorrecte=true;
                  }
        }
        if (respostaCorrecte) {
            Toast.makeText(MainActivity.this, R.string.onCorrectAnswer, Toast.LENGTH_SHORT).show();
            score+=1;

        } else {

            Toast.makeText(MainActivity.this,  getString(R.string.onIncorrectAnswer), Toast.LENGTH_SHORT).show();
        score-=0.5;
        }
        if (preguntaActual==questionBank[questionBank.length-1]){
            finalDialogue.show();
        }else {
            cambiarPregunta();
        }
    }

    private void cambiarPregunta() {
        if (numeroPreguntaActual == questionBank.length - 1) {

            numeroPreguntaActual = -1;
         progressBar.setProgress(0);
        }
        progressBar.incrementProgressBy(progressBar.getMax() / questionBank.length);
        numeroPreguntaActual += 1;
        preguntaActual = questionBank[numeroPreguntaActual];
        actualitzarPantalla();
    }

    void actualitzarPantalla() {
        questionText.setText(preguntaActual.getQuestionText());
        String text = (numeroPreguntaActual + 1) + " of " + questionBank.length;
        scoreText.setText(getString(R.string.scoreText) + " "+ score);
//        progressText.setText(text);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    outState.putInt("numeroPreguntaActual",numeroPreguntaActual);
    }
}