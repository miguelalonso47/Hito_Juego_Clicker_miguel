package com.example.hitojuegoclicker;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class JsonHelper {

    public static List<PlayerScore> getScoresFromJson(Context context, String fileName) {
        List<PlayerScore> scores = new ArrayList<>();

        try {
            FileInputStream fis = context.openFileInput(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            reader.close();

            // Parsear el JSON
            JSONArray jsonArray = new JSONArray(jsonBuilder.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String username = jsonObject.getString("username");
                int score = jsonObject.getInt("score");
                scores.add(new PlayerScore(username, score));
            }
        } catch (Exception e) {
            try {
                InputStream is = context.getAssets().open(fileName);
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder jsonBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonBuilder.append(line);
                }
                reader.close();

                // Parsear el JSON
                JSONArray jsonArray = new JSONArray(jsonBuilder.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String username = jsonObject.getString("username");
                    int score = jsonObject.getInt("score");
                    scores.add(new PlayerScore(username, score));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return scores;
    }


    public static PlayerScore findPlayerScore(Context context, String fileName, String username) {
        List<PlayerScore> scores = getScoresFromJson(context, fileName);
        for (PlayerScore score : scores) {
            if (score.getUsername().equalsIgnoreCase(username)) {
                return score;
            }
        }
        return null;
    }

    public static void saveScoresToJson(Context context, String fileName, List<PlayerScore> scores) {
        try {
            // Crear un JSONArray a partir de la lista de PlayerScore
            JSONArray jsonArray = new JSONArray();
            for (PlayerScore playerScore : scores) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("username", playerScore.getUsername());
                jsonObject.put("score", playerScore.getScore());
                jsonArray.put(jsonObject);
            }

            // Escribir el JSONArray al archivo scores.json
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(jsonArray.toString().getBytes());
            fos.close();

            Toast.makeText(context, "Scores saved successfully!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error saving scores", Toast.LENGTH_SHORT).show();
        }
    }

}
