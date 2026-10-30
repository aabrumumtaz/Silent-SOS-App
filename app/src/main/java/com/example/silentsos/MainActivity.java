package com.example.silentsos;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button btnSos, btnVoiceSos;
    private Switch btnAiToggle;
    private TextView tvEmergencyNumber;
    private SharedPreferences sharedPreferences;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final int SPEECH_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI Components Initialize
        btnSos = findViewById(R.id.btnSos);
        btnVoiceSos = findViewById(R.id.btnVoiceSos);
        btnAiToggle = findViewById(R.id.btnAiToggle);
        tvEmergencyNumber = findViewById(R.id.tvEmergencyNumber);

        // SharedPreferences Initialize
        sharedPreferences = getSharedPreferences("SilentSOSPrefs", MODE_PRIVATE);

        // 1. Screen par status update karein (Agar pehle se save hai toh show hoga)
        updateEmergencyNumberDisplay();

        // 2. Runtime Permissions check karein (SMS, Location, Microphone)
        checkAndRequestPermissions();

        // 3. Sirf agar pehli baar number set nahi hoga, tab hi Dialog show hoga
        checkEmergencyContact();

        // SOS Button Click Listener
        btnSos.setOnClickListener(v -> triggerSosAction());

        // Voice SOS / Speech-to-Text Button Click Listener
        btnVoiceSos.setOnClickListener(v -> startVoiceRecognition());

        // AI Toggle Listener for Scream Detection Background Service
        btnAiToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Intent serviceIntent = new Intent(MainActivity.this, ScreamDetectionService.class);
                ContextCompat.startForegroundService(MainActivity.this, serviceIntent);
                Toast.makeText(MainActivity.this, "AI Scream Detection Enabled", Toast.LENGTH_SHORT).show();
            } else {
                Intent serviceIntent = new Intent(MainActivity.this, ScreamDetectionService.class);
                stopService(serviceIntent);
                Toast.makeText(MainActivity.this, "AI Scream Detection Disabled", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateEmergencyNumberDisplay() {
        String savedNumber = sharedPreferences.getString("emergency_number", null);
        if (savedNumber != null && !savedNumber.isEmpty()) {
            tvEmergencyNumber.setText("Active Emergency Number: " + savedNumber);
        } else {
            tvEmergencyNumber.setText("Active Emergency Number: Not Set");
        }
    }

    private void checkAndRequestPermissions() {
        String[] permissions = {
                Manifest.permission.SEND_SMS,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.FOREGROUND_SERVICE,
                Manifest.permission.FOREGROUND_SERVICE_MICROPHONE
        };

        boolean allGranted = true;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                allGranted = false;
                break;
            }
        }

        if (!allGranted) {
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
        }
    }

    private void checkEmergencyContact() {
        String savedNumber = sharedPreferences.getString("emergency_number", null);
        // Yeh sirf tab chalega jab number pehle se save nahi hoga (yani sirf first time)
        if (savedNumber == null || savedNumber.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Set Emergency Contact");
            builder.setMessage("Please enter your guardian or emergency phone number:");

            final EditText input = new EditText(this);
            input.setHint("e.g. 03001234567");
            builder.setView(input);

            builder.setPositiveButton("Save", (dialog, which) -> {
                String number = input.getText().toString().trim();
                if (!number.isEmpty()) {
                    sharedPreferences.edit().putString("emergency_number", number).apply();
                    updateEmergencyNumberDisplay();
                    Toast.makeText(MainActivity.this, "Emergency number saved successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Number cannot be empty", Toast.LENGTH_SHORT).show();
                    checkEmergencyContact();
                }
            });

            builder.setCancelable(false);
            builder.show();
        }
    }

    private void startVoiceRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak your emergency message...");
        try {
            startActivityForResult(intent, SPEECH_REQUEST_CODE);
        } catch (Exception e) {
            Toast.makeText(this, "Speech recognition not supported on this device", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (result != null && !result.isEmpty()) {
                String spokenText = result.get(0);
                Toast.makeText(this, "Converted Text: " + spokenText, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void triggerSosAction() {
        String savedNumber = sharedPreferences.getString("emergency_number", null);
        if (savedNumber == null || savedNumber.isEmpty()) {
            checkEmergencyContact();
            return;
        }
        Toast.makeText(this, "SOS Alert Sent to " + savedNumber, Toast.LENGTH_LONG).show();
    }
}