package com.itb.androidquiz;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView questionText;
    TextView scoreText;
    TextView progressText;
    Button option1;
    Button option2;
    Button option3;
    Button option4;
    Button pista;
    public static final int PISTESINICIALS = 3;
    int numeroPistes = PISTESINICIALS;

    double score = 0;
    ProgressBar progressBar;
    AlertDialog.Builder finalDialogueBuilder;
    AlertDialog finalDialogue;
    int numeroPreguntaActual = 0;
    QuestionModel preguntaActual;


    static void shuffleArray(QuestionModel[] models) {
        Random rand = new Random();

        for (int i = 0; i < models.length; i++) {
            int randomIndexToSwap = rand.nextInt(models.length);
            QuestionModel temp = models[randomIndexToSwap];
            models[randomIndexToSwap] = models[i];
            models[i] = temp;
        }
        System.out.println(Arrays.toString(models));
    }

    static final QuestionModel[] questionBank = {
            new QuestionModel(R.string.q0, new ResponseModel[]{new ResponseModel(R.string.a1q0, true, 0)
                    , new ResponseModel(R.string.a2q0, false, 1), new ResponseModel(R.string.a3q0, false, 2), new ResponseModel(R.string.a4q0, false, 3)}),
            new QuestionModel(R.string.q1, new ResponseModel[]{new ResponseModel(R.string.a1q1, false, 0)
                    , new ResponseModel(R.string.a2q1, false, 1), new ResponseModel(R.string.a3q1, true, 2), new ResponseModel(R.string.a4q1, false, 3)}),
            new QuestionModel(R.string.q2, new ResponseModel[]{new ResponseModel(R.string.a1q2, true, 0)
                    , new ResponseModel(R.string.a2q2, false, 1), new ResponseModel(R.string.a3q2, false, 2), new ResponseModel(R.string.a4q2, false, 3)}),
            new QuestionModel(R.string.q3, new ResponseModel[]{new ResponseModel(R.string.a1q3, true, 0)
                    , new ResponseModel(R.string.a2q3, false, 1), new ResponseModel(R.string.a3q3, false, 2), new ResponseModel(R.string.a4q3, false, 3)}),

            new QuestionModel(R.string.q4, new ResponseModel[]{new ResponseModel(R.string.a1q4, false, 0)
                    , new ResponseModel(R.string.a2q4, false, 1), new ResponseModel(R.string.a3q4, true, 2), new ResponseModel(R.string.a4q4, false, 3)}),

            new QuestionModel(R.string.q5, new ResponseModel[]{new ResponseModel(R.string.a1q5, false, 0)
                    , new ResponseModel(R.string.a2q5, false, 1), new ResponseModel(R.string.a3q5, true, 2), new ResponseModel(R.string.a4q5, false, 3)}),

            new QuestionModel(R.string.q6, new ResponseModel[]{new ResponseModel(R.string.a1q6, true, 0)
                    , new ResponseModel(R.string.a2q6, false, 1), new ResponseModel(R.string.a3q6, false, 2), new ResponseModel(R.string.a4q6, false, 3)}),

            new QuestionModel(R.string.q7, new ResponseModel[]{new ResponseModel(R.string.a1q7, true, 0)
                    , new ResponseModel(R.string.a2q7, false, 1), new ResponseModel(R.string.a3q7, false, 2), new ResponseModel(R.string.a4q7, false, 3)}),

            new QuestionModel(R.string.q8, new ResponseModel[]{new ResponseModel(R.string.a1q8, false, 0)
                    , new ResponseModel(R.string.a2q8, false, 1), new ResponseModel(R.string.a3q8, true, 2), new ResponseModel(R.string.a4q8, false, 3)}),

   new QuestionModel(R.string.q9, new ResponseModel[]{new ResponseModel(R.string.a1q9, false, 0)
                    , new ResponseModel(R.string.a2q9, false, 1), new ResponseModel(R.string.a3q9, true, 2), new ResponseModel(R.string.a4q9, false, 3)}),


    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        System.out.println();
        if (savedInstanceState != null) {
            numeroPreguntaActual = savedInstanceState.getInt("numeroPreguntaActual");
            score = savedInstanceState.getDouble("score");
            numeroPistes = savedInstanceState.getInt("pistesRestants");
        }else {
            shuffleArray(questionBank);}

        questionText = findViewById(R.id.questionText);
        progressText = findViewById(R.id.progressText);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        scoreText = findViewById(R.id.scoreText);
        progressBar = findViewById(R.id.progressBar);
        pista = findViewById(R.id.hint);


        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprovarResultat(0, preguntaActual);
            }
        });
        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprovarResultat(1, preguntaActual);
            }
        });
        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprovarResultat(2, preguntaActual);
            }
        });
        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprovarResultat(3, preguntaActual);
            }
        });
        pista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donarPista();
            }
        });
        preguntaActual = questionBank[numeroPreguntaActual];
        progressBar.setProgress(progressBar.getMax() / questionBank.length);
        comprovarSiDesactivarPistas();
        actualitzarPantalla();
    }

    private void donarPista() {

        numeroPistes--;
        ArrayList<Integer> p1 = preguntaActual.getAnswerPosition();
        ArrayList<String> respostes = new ArrayList<>();
        for (int i = 0; i < p1.size(); i++) {
            respostes.add(getString(preguntaActual.getRespones()[p1.get(i)].getAnswerText()));


        }
        if (respostes.size() > 1) {
            Toast.makeText(MainActivity.this, "La pregunta te les següents respostes " + respostes.toString(), Toast.LENGTH_SHORT).show();
        } else if (respostes.size() == 1) {
            Toast.makeText(MainActivity.this, "La resposta a la pregunta és " + respostes.toString(), Toast.LENGTH_SHORT).show();

        }
        comprovarSiDesactivarPistas();


    }

    private void comprovarSiDesactivarPistas() {
        if (numeroPistes <= 0) {
            pista.setEnabled(false);
            pista.setText(R.string.outOfHintsText);
            pista.getBackground().setAlpha(124);
        } else {
            pista.setEnabled(true);
            pista.setText(R.string.hintBurttonText);
            pista.getBackground().setAlpha(255);
        }
    }

    private void tornarApreguntaInicial() {
        shuffleArray(questionBank);
        preguntaActual = questionBank[numeroPreguntaActual];
        score = 0;
        numeroPistes = PISTESINICIALS;
        comprovarSiDesactivarPistas();
        numeroPreguntaActual = 0;
        progressBar.setProgress(progressBar.getMax() / questionBank.length);
        actualitzarPantalla();
    }

    private void comprovarResultat(int resposta, QuestionModel preguntaActual) {
        boolean respostaCorrecte = false;
        ArrayList<Integer> p1 = preguntaActual.getAnswerPosition();
        for (int i = 0; i < p1.size(); i++) {
            System.out.println(p1.indexOf(i) == resposta);
            if (p1.get(i) == resposta) {
                respostaCorrecte = true;
            }
        }
        if (respostaCorrecte) {
            Toast.makeText(MainActivity.this, R.string.onCorrectAnswer, Toast.LENGTH_SHORT).show();
            score += 1 * questionBank.length;

        } else {

            Toast.makeText(MainActivity.this, getString(R.string.onIncorrectAnswer), Toast.LENGTH_SHORT).show();
            score -= 0.5 * questionBank.length;
        }
        if (preguntaActual == questionBank[questionBank.length - 1]) {
            scoreText.setText(getString(R.string.scoreText) + " " + score);
            finalDialogueBuilder = new AlertDialog.Builder(MainActivity.this);
            finalDialogueBuilder.setTitle(getString(R.string.congratulationText) + " " + score);
            finalDialogueBuilder.setMessage(R.string.nextText);
            finalDialogueBuilder.setNegativeButton(R.string.negativeButtonText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    tornarApreguntaInicial();
                }
            });
            finalDialogueBuilder.setPositiveButton(R.string.positiveButtonText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();


                }
            });
            finalDialogue = finalDialogueBuilder.create();
            finalDialogue.show();
        } else {
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
        preguntaActual = questionBank[numeroPreguntaActual];

        questionText.setText(preguntaActual.getQuestionText());
        String text = (numeroPreguntaActual + 1) + " of " + questionBank.length;
        scoreText.setText(getString(R.string.scoreText) + " " + score);
        progressText.setText(text);
        option1.setText(preguntaActual.getRespones()[0].getAnswerText());
        option2.setText(preguntaActual.getRespones()[1].getAnswerText());
        option3.setText(preguntaActual.getRespones()[2].getAnswerText());
        option4.setText(preguntaActual.getRespones()[3].getAnswerText());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("numeroPreguntaActual", numeroPreguntaActual);
        outState.putDouble("score", score);
        outState.putInt("pistesRestants", numeroPistes);
    }
}