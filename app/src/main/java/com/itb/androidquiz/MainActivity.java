package com.itb.androidquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView questionText;
    TextView progressText;
    Button trueButton;
    Button falseButton;
    ProgressBar progressBar;
    AlertDialog.Builder finalDialogueBuilder;
    AlertDialog finalDialogue;
    int numeroPreguntaActual = 0;
    QuestionModel preguntaActual = questionBank[numeroPreguntaActual];
 ViewModel  viewModel;
    static final QuestionModel[] questionBank = {
            new QuestionModel(R.string.q0, false),
            new QuestionModel(R.string.q1, false),
            new QuestionModel(R.string.q2, true),
            new QuestionModel(R.string.q3, true),
            new QuestionModel(R.string.q4, true),
            new QuestionModel(R.string.q5, true),
            new QuestionModel(R.string.q6, false),
            new QuestionModel(R.string.q7, false),
            new QuestionModel(R.string.q8, true),
            new QuestionModel(R.string.q9, true),


    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState!=null) numeroPreguntaActual=savedInstanceState.getInt("numeroPreguntaActual");

        questionText = findViewById(R.id.questionText);
        progressText = findViewById(R.id.progressText);
        trueButton = findViewById(R.id.trueButton);
        falseButton = findViewById(R.id.falseButton);
        progressBar = findViewById(R.id.progressBar);
        finalDialogueBuilder= new AlertDialog.Builder(MainActivity.this);
        finalDialogueBuilder.setTitle(R.string.congratulationText);
        finalDialogueBuilder.setMessage(R.string.nextText);
        finalDialogueBuilder.setNegativeButton(R.string.negativeButtonText,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               cambiarPregunta();

            }});
        finalDialogueBuilder.setPositiveButton(R.string.positiveButtonText,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();


            }});
        finalDialogue = finalDialogueBuilder.create();

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprovarResultat(true, preguntaActual);
            }
        });
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprovarResultat(false, preguntaActual);
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
        numeroPreguntaActual = 0;
        progressBar.setProgress(progressBar.getMax() / questionBank.length);
        actualitzarPantalla();
    }

    private void comprovarResultat(boolean resposta, QuestionModel preguntaActual) {
        if (preguntaActual.isAnswer() == resposta) {
            Toast.makeText(MainActivity.this, R.string.onCorrectAnswer, Toast.LENGTH_SHORT).show();


        } else {

            Toast.makeText(MainActivity.this,  getString(R.string.onIncorrectAnswer)+" "+  preguntaActual.isAnswer(), Toast.LENGTH_SHORT).show();
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
        progressText.setText(text);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    outState.putInt("numeroPreguntaActual",numeroPreguntaActual);
    }
}