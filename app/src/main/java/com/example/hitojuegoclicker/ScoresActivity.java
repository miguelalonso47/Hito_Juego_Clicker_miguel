package com.example.hitojuegoclicker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        // Configura RecyclerView
        RecyclerView scoresRecyclerView = findViewById(R.id.scoresRecyclerView);
        scoresRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Carga las puntuaciones desde el archivo JSON y ordena la lista
        List<Score> scores = loadScoresFromFile();
        Collections.sort(scores);  // Ordena la lista de puntuaciones de mayor a menor

        // Configura el adaptador del RecyclerView
        ScoreAdapter adapter = new ScoreAdapter(scores);
        scoresRecyclerView.setAdapter(adapter);

        // Configura el botón "Back to Menu"
        Button backToMenuButton = findViewById(R.id.backToMenuButton2);
        backToMenuButton.setOnClickListener(v -> {
            Intent intent = new Intent(ScoresActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Cierra la actividad actual (ScoresActivity)
        });
    }

    // Cargar las puntuaciones desde el archivo JSON
    private List<Score> loadScoresFromFile() {
        List<Score> scores = new ArrayList<>();
        try (FileInputStream fis = openFileInput("scores.json")) {
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            String jsonString = new String(buffer);

            JSONArray scoresArray = new JSONArray(jsonString);
            for (int i = 0; i < scoresArray.length(); i++) {
                JSONObject scoreObject = scoresArray.getJSONObject(i);
                String username = scoreObject.getString("username");
                int score = scoreObject.getInt("score");
                scores.add(new Score(username, score));
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return scores;
    }
}
