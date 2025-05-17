package com.example.kameranezo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity {

    private static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 1001;

    private EditText regiJelszoEditText, ujJelszo1EditText, ujJelszo2EditText;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        regiJelszoEditText = findViewById(R.id.regijelszo);
        ujJelszo1EditText = findViewById(R.id.ujJelszo1);
        ujJelszo2EditText = findViewById(R.id.ujjelszo2);
        auth = FirebaseAuth.getInstance();
    }

    public void passwordChange(View view) {
        String regiJelszo = regiJelszoEditText.getText().toString();
        String ujJelszo1 = ujJelszo1EditText.getText().toString();
        String ujJelszo2 = ujJelszo2EditText.getText().toString();

        if (regiJelszo.isEmpty() || ujJelszo1.isEmpty() || ujJelszo2.isEmpty()) {
            Toast.makeText(this, "Minden mezőt ki kell tölteni!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!ujJelszo1.equals(ujJelszo2)) {
            Toast.makeText(this, "Az új jelszavak nem egyeznek!", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser user = auth.getCurrentUser();
        if (user != null && user.getEmail() != null) {
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), regiJelszo);

            user.reauthenticate(credential).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    user.updatePassword(ujJelszo1).addOnCompleteListener(updateTask -> {
                        if (updateTask.isSuccessful()) {
                            Toast.makeText(this, "Jelszó sikeresen frissítve!", Toast.LENGTH_SHORT).show();
                            sendPasswordChangedNotification();  // Értesítés küldése
                        } else {
                            Toast.makeText(this, "Hiba a jelszó frissítésekor!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(this, "Hibás régi jelszó!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void sendPasswordChangedNotification() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) { // API 33+
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, NOTIFICATION_PERMISSION_REQUEST_CODE);
                return;
            }
        }
        actuallySendNotification();
    }

    private void actuallySendNotification() {
        String CHANNEL_ID = "password_change_channel";
        String channelName = "Jelszó változás értesítések";

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Jelszó változtatás")
                .setContentText("Sikeresen megváltoztattad a jelszavadat!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        notificationManager.notify(1, builder.build());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                actuallySendNotification();
            } else {
                Toast.makeText(this, "Értesítési engedély szükséges a figyelmeztetésekhez", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
