package com.example.furryfriends;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
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

public class ProfileActivity extends AppCompatActivity {

    private TextView usernameTextView, emailTextView, contactTextView, locationTextView;
    private Button viewFavoriteButton; // Add viewFavoriteButton reference
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize views and database reference
        usernameTextView = findViewById(R.id.usernameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        contactTextView = findViewById(R.id.contactTextView);
        locationTextView = findViewById(R.id.locationTextView);

        databaseReference = FirebaseDatabase.getInstance().getReference("users")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());


        // Retrieve and display user data including favorite donation
        retrieveUserData();
    }

    private void retrieveUserData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve user data from dataSnapshot
                    String username = dataSnapshot.child("username").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);
                    String contact = dataSnapshot.child("contactNumber").getValue(String.class);
                    String location = dataSnapshot.child("location").getValue(String.class);
                    String favoriteDonation = dataSnapshot.child("favoriteDonation").getValue(String.class); // Assuming this field exists

                    // Display user data in TextViews
                    usernameTextView.setText("Username: " + username);
                    emailTextView.setText("Email: " + email);
                    contactTextView.setText("Contact: " + contact);
                    locationTextView.setText("Location: " + location);

                } else {
                    // Handle case where data doesn't exist
                    Log.d("Firebase", "Data does not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
                Log.e("Firebase", "Error retrieving data: " + databaseError.getMessage());
            }
        });
    }
}
