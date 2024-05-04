package com.example.furryfriends;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class SetupProfileActivity extends AppCompatActivity {

    private EditText usernameEditText, emailEditText, contactNumberEditText, locationEditText;
    private Uri imageUri;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_profile);

        usernameEditText = findViewById(R.id.usernameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        contactNumberEditText = findViewById(R.id.contactNumberEditText);
        locationEditText = findViewById(R.id.locationEditText);
        Button updateProfileButton = findViewById(R.id.updateProfileButton);

        storageReference = FirebaseStorage.getInstance().getReference("uploads");
        databaseReference = FirebaseDatabase.getInstance().getReference("users")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());

        // Fetch existing user data from Firebase and populate EditText fields
        databaseReference.get().addOnSuccessListener(dataSnapshot -> {
            if (dataSnapshot.exists()) {
                String username = dataSnapshot.child("username").getValue(String.class);
                String email = dataSnapshot.child("email").getValue(String.class);
                String contactNumber = dataSnapshot.child("contactNumber").getValue(String.class);
                String location = dataSnapshot.child("location").getValue(String.class);

                // Populate EditText fields with existing data
                usernameEditText.setText(username);
                emailEditText.setText(email);
                contactNumberEditText.setText(contactNumber);
                locationEditText.setText(location);
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(SetupProfileActivity.this, "Failed to fetch user data", Toast.LENGTH_SHORT).show();
        });

        updateProfileButton.setOnClickListener(view -> uploadProfile());
    }

    private void uploadProfile() {
        String username = usernameEditText.getText().toString().trim();
        String petName = emailEditText.getText().toString().trim();
        String contactNumber = contactNumberEditText.getText().toString().trim();
        String location = locationEditText.getText().toString().trim();

        // Check if at least one field is filled or an image is selected
        if (imageUri == null && TextUtils.isEmpty(username) && TextUtils.isEmpty(petName) &&
                TextUtils.isEmpty(contactNumber) && TextUtils.isEmpty(location)) {
            Toast.makeText(this, "Please fill in at least one field or select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        StorageReference fileReference;
        if (imageUri != null) {
            fileReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
            fileReference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                uriTask.addOnSuccessListener(downloadUri -> {
                    User user = new User(username, petName, contactNumber, location, downloadUri.toString());
                    databaseReference.setValue(user).addOnSuccessListener(aVoid -> {
                        Toast.makeText(SetupProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                    }).addOnFailureListener(e -> Toast.makeText(SetupProfileActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show());
                });
            }).addOnFailureListener(e -> Toast.makeText(SetupProfileActivity.this, "Upload failed", Toast.LENGTH_SHORT).show());
        } else {
            User user = new User(username, petName, contactNumber, location, null);
            databaseReference.setValue(user).addOnSuccessListener(aVoid -> {
                Toast.makeText(SetupProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                finish(); // Finish the current activity
            }).addOnFailureListener(e -> Toast.makeText(SetupProfileActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show());
        }
    }

    private String getFileExtension(Uri uri) {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(getContentResolver().getType(uri));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
