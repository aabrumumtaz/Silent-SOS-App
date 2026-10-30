package com.example.silentsos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    private EditText etFullName, etSignupEmail, etSignupPassword, etEmergencyContact;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // UI elements ko bind karna
        etFullName = findViewById(R.id.etFullName);
        etSignupEmail = findViewById(R.id.etSignupEmail);
        etSignupPassword = findViewById(R.id.etSignupPassword);
        etEmergencyContact = findViewById(R.id.etEmergencyContact);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> {
            String name = etFullName.getText().toString().trim();
            String email = etSignupEmail.getText().toString().trim();
            String password = etSignupPassword.getText().toString().trim();
            String contact = etEmergencyContact.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || contact.isEmpty()) {
                Toast.makeText(SignupActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                // Emergency Contact ko SharedPreferences mein save karna
                SharedPreferences prefs = getSharedPreferences("SOSPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("saved_contact", contact);
                editor.apply();

                Toast.makeText(SignupActivity.this, "Account Registered Successfully!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}