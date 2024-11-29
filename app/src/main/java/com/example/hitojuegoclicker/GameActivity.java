package com.example.hitojuegoclicker;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class GameActivity extends AppCompatActivity {
    private int score = 0;             // Puntuación actual
    private int clicks = 0;            // Contador de clics
    private int level = 1;             // Nivel inicial
    private int pointsPerClick = 1;    // Puntos por clic iniciales
    private long timeLeftInMillis = 30000; // Tiempo inicial: 30 segundos
    private CountDownTimer countDownTimer; // Temporizador

    private TextView timeDisplay;      // Muestra el tiempo restante
    private TextView levelDisplay;     // Muestra el nivel actual
    private TextView usernameDisplay;  // Muestra el nombre del usuario
    private Button clickButton;        // Botón de clic

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        usernameDisplay = findViewById(R.id.usernameDisplay);
        TextView scoreDisplay = findViewById(R.id.scoreDisplay);
        timeDisplay = findViewById(R.id.timeDisplay);
        levelDisplay = findViewById(R.id.levelDisplay);
        clickButton = findViewById(R.id.clickButton);
        Button saveScoreButton = findViewById(R.id.saveScoreButton);
        Button backToMenuButton = findViewById(R.id.backToMenuButton2);

        // Recupera datos del intent
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String username = bundle.getString("USERNAME", "Unknown User");  // Se pasa el nombre de usuario aquí

            usernameDisplay.setText("Player: " + username);  // Muestra el nombre de usuario
            scoreDisplay.setText("Score: " + score);

            // Inicia el temporizador
            startGameTimer();

            // Configura el botón de clic
            clickButton.setOnClickListener(v -> {
                clicks++;
                score += pointsPerClick;
                scoreDisplay.setText("Score: " + score);

                // Verifica si se sube de nivel
                if (clicks >= level * 50) { // Cada 50 clics por nivel
                    level++;
                    pointsPerClick = level; // Incrementa puntos por clic
                    levelDisplay.setText("Level: " + level);

                    // Suma 5 segundos al tiempo
                    timeLeftInMillis += 5000;
                    timeDisplay.setText("Time: " + (timeLeftInMillis / 1000) + "s");

                    // Reinicia el temporizador con el nuevo tiempo
                    restartTimer();
                }
            });

            // Botón para guardar la puntuación
            saveScoreButton.setOnClickListener(v -> saveScore(username, score));
        }

        // Botón para volver al menú
        backToMenuButton.setOnClickListener(v -> {
            Intent intent = new Intent(GameActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    // Inicia el temporizador del juego
    private void startGameTimer() {
        levelDisplay.setText("Level: " + level);
        timeDisplay.setText("Time: " + (timeLeftInMillis / 1000) + "s");

        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                timeDisplay.setText("Time: " + (millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                endGame();
            }
        }.start();
    }

    // Reinicia el temporizador con el nuevo tiempo
    private void restartTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Cancela el temporizador actual y lo reinicia con el nuevo tiempo restante.
        }

        // Reinicia el temporizador con el nuevo valor de tiempo
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                timeDisplay.setText("Time: " + (millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                endGame();
            }
        }.start();
    }

    // Guarda la puntuación en el archivo JSON
    private void saveScore(String username, int finalScore) {
        List<PlayerScore> scores = JsonHelper.getScoresFromJson(GameActivity.this, "scores.json");
        boolean found = false;

        for (PlayerScore playerScore : scores) {
            if (playerScore.getUsername().equalsIgnoreCase(username)) {
                if (finalScore > playerScore.getScore()) {
                    playerScore.setScore(finalScore); // Actualiza si la nueva puntuación es mayor
                }
                found = true;
                break;
            }
        }

        if (!found) {
            scores.add(new PlayerScore(username, finalScore)); // Agrega nuevo jugador si no existe
        }

        JsonHelper.saveScoresToJson(GameActivity.this, "scores.json", scores);
        Toast.makeText(GameActivity.this, "Score saved!", Toast.LENGTH_SHORT).show();
    }

    // Finaliza el juego cuando se acaba el tiempo
    private void endGame() {
        String username = usernameDisplay.getText().toString().replace("Player: ", ""); // Obtén el nombre
        clickButton.setEnabled(false); // Desactiva el botón para evitar más clics
        Toast.makeText(this, "Time's up! Your final score is: " + score, Toast.LENGTH_LONG).show();
        saveScore(username, score);

        Intent intent = new Intent(GameActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
