package com.example.kameranezo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseUser;

public class WelcomePage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView welcomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome_page);

        mAuth = FirebaseAuth.getInstance();
        welcomeText = findViewById(R.id.profilTitle);

        // Rendszer bár ablakok betöltése
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.welcomeTitle), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Felhasználói adatlekérés
        getUserData();
    }

    private void getUserData() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users")
                    .document(user.getUid())
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String name = documentSnapshot.getString("name");
                            welcomeText.setText("Üdvözlünk, " + name + "!");
                        } else {
                            Toast.makeText(WelcomePage.this, "A felhasználói adatok nem találhatók.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(WelcomePage.this, "Hiba az adatlekérés során: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    });
        } else {
            Toast.makeText(WelcomePage.this, "Nincs bejelentkezett felhasználó.", Toast.LENGTH_SHORT).show();
        }
    }
    public void openMenua(View view) {

        startActivity(new Intent(this , CameraList.class));
    }

    public void openProfile(View view) {
        startActivity(new Intent(this, Profile.class));
    }

    public void openMenu(View view) {
        startActivity(new Intent(this, CameraList.class));
    }

    public void namechanging(View view) {
        startActivity(new Intent(this, NameChanger.class));
    }
}
