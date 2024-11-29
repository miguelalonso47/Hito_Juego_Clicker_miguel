package com.example.hitojuegoclicker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText usernameInput;
    private Button startGameButton;
    private Button viewScoresButton;
    private Button contactUsButton;  // Botón para "Contáctanos"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameInput = findViewById(R.id.usernameInput);
        startGameButton = findViewById(R.id.startGameButton);
        viewScoresButton = findViewById(R.id.viewScoresButton);
        contactUsButton = findViewById(R.id.contactUsButton);  // Enlaza el botón

        startGameButton.setOnClickListener(v -> {
            String username = usernameInput.getText().toString().trim();

            if (username.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter a username", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("USERNAME", username);
                intent.putExtra("SCORE", 0);
                startActivity(intent);
            }
        });

        viewScoresButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ScoresActivity.class);
            startActivity(intent);
        });

        // Navega a la actividad "Contact Us"
        contactUsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ContactUsActivity.class);
            startActivity(intent);
        });
    }
}
