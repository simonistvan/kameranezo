package com.example.kameranezo;

import android.content.Intent; // Add hozzá az Intent importálását
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.welcomeTitle), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void loginIn(View view) {
        // EditText mezők kiolvasása
        EditText usernameEditText = findViewById(R.id.editText); // Felhasználónév mező
        EditText passwordEditText = findViewById(R.id.editTextTextPassword); // Jelszó mező

        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Ezt itt természetesen ki kell cserélni egy valódi bejelentkezési logikával
        if (!username.isEmpty() && !password.isEmpty()) {
            // Feltételezve, hogy a bejelentkezés sikeres:

            // Üzenet a sikeres bejelentkezésről
            Toast.makeText(this, "Bejelentkezés sikeres!", Toast.LENGTH_SHORT).show();

            // Irányítás egy másik Activity-re
            Intent intent = new Intent(LoginActivity.this, WelcomePage.class); // Itt cseréld le a TargetActivity-t a kívánt Activity-re
            startActivity(intent);
            finish(); // Ez bezárja a bejelentkezési Activity-t
        } else {
            // Ha a mezők üresek, akkor hibaüzenet
            Toast.makeText(this, "Kérjük, töltse ki a felhasználónevet és a jelszót.", Toast.LENGTH_SHORT).show();
        }
    }
}