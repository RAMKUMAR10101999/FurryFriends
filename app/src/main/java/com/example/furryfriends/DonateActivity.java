package com.example.furryfriends;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

public class DonateActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText etProductName, etDescription, etUsername, etLocation, etContact;
    private ImageView ivProductImage;
    private Button btnSelectImage, btnDonate;

    private Uri imageUri;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        etProductName = findViewById(R.id.etProductName);
        etDescription = findViewById(R.id.etDescription);
        etUsername = findViewById(R.id.etUsername);
        etLocation = findViewById(R.id.etLocation);
        etContact = findViewById(R.id.etContact);
        ivProductImage = findViewById(R.id.ivProductImage);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnDonate = findViewById(R.id.btnDonate);

        databaseReference = FirebaseDatabase.getInstance().getReference("pet_products");
        storageReference = FirebaseStorage.getInstance().getReference("product_images");

        btnSelectImage.setOnClickListener(v -> openImagePicker());
        btnDonate.setOnClickListener(v -> donateProduct());
    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            ivProductImage.setImageURI(imageUri);
            btnSelectImage.setEnabled(true);
        }
    }

    private void donateProduct() {
        String productName = etProductName.getText().toString().trim();
        String productDescription = etDescription.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String location = etLocation.getText().toString().trim();
        String contactDetails = etContact.getText().toString().trim();

        if (productName.isEmpty() || productDescription.isEmpty() || username.isEmpty() || location.isEmpty() || contactDetails.isEmpty() || imageUri == null) {
            Toast.makeText(this, "Please fill in all fields and select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        StorageReference imageRef = storageReference.child(System.currentTimeMillis() + ".jpg");
        imageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        String imageUrl = uri.toString();
                        PetProduct product = new PetProduct(productName, productDescription, username, location, contactDetails, imageUrl);
                        String productId = databaseReference.push().getKey();
                        if (productId != null) {
                            databaseReference.child(productId).setValue(product);
                            Toast.makeText(this, "Product donated successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(this, "Error donating product", Toast.LENGTH_SHORT).show();
                        }
                    });
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error uploading image", Toast.LENGTH_SHORT).show());
    }
}
