package com.example.hitojuegoclicker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class ContactUsActivity extends AppCompatActivity {

    private EditText nameInput;  // Campo para nombre
    private EditText emailInput;  // Campo para email
    private EditText messageInput;  // Campo para mensaje
    private Button sendButton;  // Botón para enviar (simulado)
    private Button backToMenuButton;  // Botón para volver al menú

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        nameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);
        backToMenuButton = findViewById(R.id.backToMenuButton);

        sendButton.setOnClickListener(v -> {


        });

        // Botón para volver al menú principal
        backToMenuButton.setOnClickListener(v -> {
            Intent intent = new Intent(ContactUsActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}

