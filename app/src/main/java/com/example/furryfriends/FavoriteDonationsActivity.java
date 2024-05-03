package com.example.furryfriends;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class FavoriteDonationsActivity extends AppCompatActivity {

    private TextView favoriteDonationsTextView;
    private DatabaseReference favoriteDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_donations);

        favoriteDonationsTextView = findViewById(R.id.favoriteDonationsTextView);
        // Initialize the database reference for favorite donations
        favoriteDatabaseReference = FirebaseDatabase.getInstance().getReference("favorite_donations")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());

        // Retrieve and display favorite donations
        retrieveFavoriteDonations();
    }

    private void retrieveFavoriteDonations() {
        favoriteDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    StringBuilder favoritesBuilder = new StringBuilder();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        // Assuming each favorite donation is stored with a unique key and contains relevant information
                        String donationKey = snapshot.getKey();
                        String donationInfo = snapshot.getValue(String.class);
                        favoritesBuilder.append("Donation Key: ").append(donationKey).append("\n")
                                .append("Donation Info: ").append(donationInfo).append("\n\n");
                    }
                    favoriteDonationsTextView.setText(favoritesBuilder.toString());
                } else {
                    favoriteDonationsTextView.setText("No favorite donations found.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });
    }
}
