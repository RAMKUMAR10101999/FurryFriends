package com.example.furryfriends;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PetFirstAidActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_first_aid);

        // Initialize buttons for each first aid topic
        Button poisoningButton = findViewById(R.id.poisoning_button);
        Button injuryButton = findViewById(R.id.injury_button);
        Button heatstrokeButton = findViewById(R.id.heatstroke_button);
        Button seizureButton = findViewById(R.id.seizure_button);
        Button chokingButton = findViewById(R.id.choking_button);
        Button breathingButton = findViewById(R.id.breathing_button);
        Button heartbeatButton = findViewById(R.id.heartbeat_button);

        // Set click listeners for each button
        poisoningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink("https://ebusiness.avma.org/files/productdownloads/mcm-client-brochures-household-hazards-2022.pdf");
            }
        });

        injuryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink("https://vcahospitals.com/know-your-pet/first-aid-for-bleeding-in-dogs");
            }
        });

        heatstrokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink("https://www.rspca.org.uk/adviceandwelfare/pets/dogs/health/heatstroke#:~:text=Emergency%20First%20Aid%20for%20dogs&text=Move%20the%20dog%20to%20a,water%20is%20better%20than%20nothing.");
            }
        });

        seizureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink("https://www.pdsa.org.uk/pet-help-and-advice/pet-health-hub/other-veterinary-advice/first-aid-for-fitsseizures-in-pets#:~:text=Keep%20the%20room%20as%20cool,in%20the%20past%2024%20hours.");
            }
        });

        chokingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink("https://www.mooresvilleanimalhospital.com/site/blog/2022/03/15/choking-dog-heimlich-maneuver#:~:text=Carefully%20hold%20your%20dog%20on,that%20was%20causing%20the%20issue.");
            }
        });

        breathingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink("https://vcahospitals.com/know-your-pet/first-aid-for-dogs");
            }
        });

        heartbeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink("https://www.redcross.org/take-a-class/cpr/performing-cpr/pet-cpr#:~:text=If%20you%20do%20not%20see,begin%20CPR%20with%20chest%20compressions.&text=Place%20your%20hands%20on%20your,directly%20over%20the%20first%20hand.");
            }
        });
    }

    private void openLink(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
