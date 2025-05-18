package com.example.kameranezo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {

    private LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        lottieAnimationView = findViewById(R.id.lottieAnimationView);

        lottieAnimationView.setAnimation("Animation-1744452629615.json");
        lottieAnimationView.playAnimation();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.EditTextUsername), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void login(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void register(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
