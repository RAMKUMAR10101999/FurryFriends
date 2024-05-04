package com.example.furryfriends;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetailsActivity extends AppCompatActivity {
    private TextView productNameTextView;
    private TextView productDescriptionTextView;
    private TextView locationTextView;
    private ImageView productImageView;
    private ImageView favoriteIcon; // Add favorite icon ImageView

    private PetProduct selectedProduct;
    private boolean isFavorite = false; // Track favorite status

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        productNameTextView = findViewById(R.id.product_name);
        productDescriptionTextView = findViewById(R.id.product_description);
        locationTextView = findViewById(R.id.location);
        productImageView = findViewById(R.id.product_image);

        // Get the selected product from intent extras
        if (getIntent().hasExtra("selected_product")) {
            selectedProduct = getIntent().getParcelableExtra("selected_product");
            if (selectedProduct != null) {
                displayProductDetails(selectedProduct);
                //setupFavoriteIcon();
            } else {
                // Handle the case where the selected product is null
                Toast.makeText(this, "Error: Selected product is null", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Handle the case where the selected product is not present in the intent extras
            Toast.makeText(this, "Error: Selected product not found in intent extras", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayProductDetails(PetProduct product) {
        if (productNameTextView != null) {
            productNameTextView.setText(product.getProductName());
        } else {
            // Handle the case where the product name TextView is null
            Toast.makeText(this, "Error: Product name TextView is null", Toast.LENGTH_SHORT).show();
        }
        if (productDescriptionTextView != null) {
            productDescriptionTextView.setText(product.getProductDescription());
        } else {
            // Handle the case where the product description TextView is null
            Toast.makeText(this, "Error: Product description TextView is null", Toast.LENGTH_SHORT).show();
        }
        if (locationTextView != null) {
            locationTextView.setText(product.getLocation());
        } else {
            // Handle the case where the location TextView is null
            Toast.makeText(this, "Error: Location TextView is null", Toast.LENGTH_SHORT).show();
        }
        if (productImageView != null && product.getImageUrl() != null) {
            Glide.with(this).load(product.getImageUrl()).into(productImageView);
        } else {
            // Handle the case where the product image ImageView or URL is null
            Toast.makeText(this, "Error: Product image ImageView or URL is null", Toast.LENGTH_SHORT).show();
        }
    }

//    private void setupFavoriteIcon() {
//        if (selectedProduct.isFavorite()) {
//            favoriteIcon.setImageResource(R.drawable.iconsfavorite); // Set filled favorite icon if already favorite
//            isFavorite = true;
//        } else {
//            favoriteIcon.setImageResource(R.drawable.unfavorite); // Set empty favorite icon if not favorite
//            isFavorite = false;
//        }
//
//        // Set click listener for favorite icon
//        favoriteIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toggleFavoriteStatus(); // Toggle favorite status on icon click
//            }
//        });
//    }
//
//    private void toggleFavoriteStatus() {
//        if (isFavorite) {
//            favoriteIcon.setImageResource(R.drawable.unfavorite); // Set empty favorite icon
//            isFavorite = false;
//        } else {
//            favoriteIcon.setImageResource(R.drawable.iconsfavorite); // Set filled favorite icon
//            isFavorite = true;
//        }
//
//        // Update favorite status in the selected product object (if needed)
//        selectedProduct.setFavorite(isFavorite);
//
//        // Implement further logic here, such as updating the database
//        // For example: updateFavoriteStatus(selectedProduct.getId(), isFavorite);
//    }
}
