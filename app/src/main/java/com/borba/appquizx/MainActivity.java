package com.borba.appquizx;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private String respostaCorreta;
    private int respostaCorretaContador = 0;
    private int quizCount = 1;

    static final private int QUIZCOUNT = 3;

    ArrayList<ArrayList<String>> quizArray = new ArrayList<>();

    String quizData[][] = {
            {"Paraná", "Curitiba", "Londrina", "Arapongas", "Astorga"},
            {"São Paulo", "São Paulo", "Rancharia", "Assis", "Campinas"},
            {"Minas Gerais", "Belo Horizonte", "Ouro Preto", "SRS", "Itajubá"}

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < quizData.length; i++) {
            ArrayList<String> tmpArray = new ArrayList<>();
            tmpArray.add(quizData[i][0]);
            tmpArray.add(quizData[i][1]);
            tmpArray.add(quizData[i][2]);
            tmpArray.add(quizData[i][3]);
            tmpArray.add(quizData[i][4]);

            quizArray.add((tmpArray));
        }
        showNextQuiz();
    }

    public void showNextQuiz() {

        TextView contadorLabel = (TextView) findViewById(R.id.contadorLabel);
        contadorLabel.setText("Q" + quizCount);

        Random random = new Random();
        int randomNum = random.nextInt(quizArray.size());

        ArrayList<String> quiz = quizArray.get(randomNum);

        TextView perguntaLabel = (TextView) findViewById(R.id.perguntaLabel);
        perguntaLabel.setText(quiz.get(0));
        respostaCorreta = quiz.get(1);

        quiz.remove(0);
        Collections.shuffle(quiz);

        Button respostaBtn1 = (Button) findViewById(R.id.respostaBtn1);
        respostaBtn1.setText(quiz.get(0));
        Button respostaBtn2 = (Button) findViewById(R.id.respostaBtn2);
        respostaBtn2.setText(quiz.get(1));
        Button respostaBtn3 = (Button) findViewById(R.id.respostaBtn3);
        respostaBtn3.setText(quiz.get(2));
        Button respostaBtn4 = (Button) findViewById(R.id.respostaBtn4);
        respostaBtn4.setText(quiz.get(3));

        quizArray.remove(randomNum);
    }

    public void checkResposta(View view) {
        Button respostaBtn = (Button) findViewById(view.getId());
        String btnText = respostaBtn.getText().toString();

        String alertTitle;

        if (btnText.equals(respostaCorreta)) {
            alertTitle = "Correto!!! :)";
            respostaCorretaContador++;
        } else {
            alertTitle = "Você errou :(";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(alertTitle);
        builder.setMessage("Resposta: " + respostaCorreta + "\nPotuação: " + respostaCorretaContador);
        builder.setPositiveButton("PRÓXIMO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (quizCount != QUIZCOUNT) {
                    quizCount++;
                    showNextQuiz();
                } else {
                    resFinal();
                }
            }
        });
        builder.setCancelable(false);
        builder.setNegativeButton("DESISTIR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                resFinal();
            }
        });
        builder.show();
    }

    public void resFinal() {
        String finalScore;
        if (respostaCorretaContador == QUIZCOUNT) {
            finalScore = "Você acertou todas parabens !!!";
        } else {
            finalScore = "Você não acertou todas";
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("RESULTADO FINAL");
        builder.setMessage(finalScore + "\nPontuação" + respostaCorretaContador);
        builder.setNeutralButton("RECOMEÇAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent restartIntent = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage(getBaseContext().getPackageName());
                restartIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(restartIntent);
            }
        });
        builder.setCancelable(false);
        builder.show();
    }
}