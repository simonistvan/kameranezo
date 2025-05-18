package com.example.kameranezo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class NameChanger extends AppCompatActivity {

    private EditText editTextName;
    private Button btnChangeName;

    private static final String CHANNEL_ID = "name_change_channel";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.namechanger);

        createNotificationChannel();

        editTextName = findViewById(R.id.editText);
        btnChangeName = findViewById(R.id.materialButton);

        btnChangeName.setOnClickListener(view -> {
            String newName = editTextName.getText().toString().trim();
            if (newName.isEmpty()) {
                Toast.makeText(NameChanger.this, "Kérlek, adj meg egy nevet!", Toast.LENGTH_SHORT).show();
                return;
            }
            changeUserName(newName);
        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Név változtatás";
            String description = "Értesítések a név frissítésről";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void changeUserName(String newName) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseFirestore.getInstance()
                .collection("users")
                .document(userId)
                .update("name", newName)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(NameChanger.this, "Név sikeresen frissítve!", Toast.LENGTH_SHORT).show();
                    sendNameChangedNotification();

                    Intent intent = new Intent(NameChanger.this, WelcomePage.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(NameChanger.this, "Hiba történt: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }

    private void sendNameChangedNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Notification.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new Notification.Builder(this, CHANNEL_ID);
        } else {
            builder = new Notification.Builder(this);
        }

        builder.setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Név változtatás")
                .setContentText("A neved sikeresen frissült.")
                .setAutoCancel(true);

        notificationManager.notify(1, builder.build());
    }
}
