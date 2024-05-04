package com.example.furryfriends;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class TrainingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        // Initialize buttons for each training topic
        Button sitButton = findViewById(R.id.sit_button);
        Button stayButton = findViewById(R.id.stay_button);
        Button lieDownButton = findViewById(R.id.lie_down_button);
        Button comeWhenCalledButton = findViewById(R.id.come_button);
        Button walkNicelyButton = findViewById(R.id.walk_nicely_button);
        Button leaveButton = findViewById(R.id.leave_button);
        Button toiletTrainingButton = findViewById(R.id.toilet_training_button);

        // Set click listeners for each button
        sitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink("https://www.rspca.org.uk/adviceandwelfare/pets/dogs/training/sit");
            }
        });

        stayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink("https://www.rspca.org.uk/adviceandwelfare/pets/dogs/training/stay");
            }
        });

        lieDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink("https://www.rspca.org.uk/adviceandwelfare/pets/dogs/training/liedown");
            }
        });

        comeWhenCalledButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink("https://www.rspca.org.uk/adviceandwelfare/pets/dogs/training/come");
            }
        });

        walkNicelyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink("https://www.rspca.org.uk/adviceandwelfare/pets/dogs/training/walknicely");
            }
        });

        leaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink("https://www.rspca.org.uk/adviceandwelfare/pets/dogs/training/leave");
            }
        });
        toiletTrainingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink("https://www.rspca.org.uk/adviceandwelfare/pets/dogs/training/toilettraining");
            }
        });
    }

    private void openLink(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
