package com.example.hitojuegoclicker;

public class Score implements Comparable<Score> {
    private String username;
    private int score;

    public Score(String username, int score) {
        this.username = username;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    // Implementa el método compareTo para ordenar las puntuaciones de mayor a menor
    @Override
    public int compareTo(Score other) {
        // Ordena de mayor a menor
        return Integer.compare(other.getScore(), this.score); // Cambiar el orden para obtener de mayor a menor
    }
}