package com.fadergs.swipeapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import br.pro.adalto.quiztouch.OnSwipeTouchListener;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout tela;
    TextView tvSwipe;
    TextView tvSim;
    TextView tvNao;
    Button button;

    int step;


    String[] perguntas = {
            "Inter é da seria A?",
            "Grêmio é da série B?",
            "Brasil de pelotas é da série A?",
            "1 + 1 é 2?",
            "O céu é azul?"
    };

    String[] gabarito = {"sim", "nao", "nao", "sim", "sim"};

    String[] respostasUser = {null, null, null, null, null};

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tela = findViewById(R.id.tela);
        tvSim = findViewById(R.id.tvSim);
        tvNao = findViewById(R.id.tvNao);
        tvSwipe = findViewById(R.id.tvSwipe);

        step = -1;


        tela.setOnTouchListener(new OnSwipeTouchListener(this) {

            @Override
            public void onSwipeBottom() {
                if (step >= 0) {
                    super.onSwipeBottom();
                    respostasUser[step] = "nao";
                    tvNao.setText("NÃO");
                    tvSim.setText("");

                }
                checkQuestions(respostasUser);
            }

            @Override
            public void onSwipeTop() {
                if (step >= 0) {
                    super.onSwipeTop();
                    respostasUser[step] = "sim";
                    tvSim.setText("SIM");
                    tvNao.setText("");
                }
                checkQuestions(respostasUser);

            }

            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                tvNao.setText("");
                tvSim.setText("");

                if (step <= 0) {
                    step = perguntas.length - 1;
                } else {
                    step--;
                }
                tvSwipe.setText(perguntas[step]);
            }

            @Override
            public void onSwipeRight() {
                tvNao.setText("");
                tvSim.setText("");
                super.onSwipeRight();
                if (step == perguntas.length - 1 || step < 0) {
                    step = 0;
                } else {
                    step++;

                }
                tvSwipe.setText(perguntas[step]);
            }
        });

    }

    int manyAnserws(String[] respostasUser, String[] gabarito) {
        int total = 0;
        for (int i = 0; i < respostasUser.length; i++) {
            if (respostasUser[i].equals(gabarito[i])) {
                total++;
            }
        }
        return total;
    }

    void checkQuestions(String[] respostasUser) {
        if (isDone(respostasUser)) {
            AlertDialog.Builder alerta = new AlertDialog.Builder(this);
            alerta.setTitle("Resultado: ");
            alerta.setMessage("Você acertou " + manyAnserws(respostasUser, gabarito) + " de " + gabarito.length);
            alerta.setNeutralButton("OK", null);
            alerta.show();
        }

    }

    private boolean isDone(String[] respostasUser) {
        boolean todasRespondidas = true;
        for (String resposta : respostasUser) {
            if (resposta == null) {
                todasRespondidas = false;
            }
        }

        return todasRespondidas;
    }

}
