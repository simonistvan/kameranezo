package com.example.kameranezo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity {

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
}
