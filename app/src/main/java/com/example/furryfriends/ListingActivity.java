package com.example.furryfriends;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListingActivity extends AppCompatActivity implements ListingAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private ListingAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private DatabaseReference mDatabaseReference;
    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String PREF_FAVORITE_PREFIX = "favorite_";


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ListingAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(this);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("pet_products");

        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                PetProduct petProduct = dataSnapshot.getValue(PetProduct.class);
                if (petProduct != null) {
                    mAdapter.add(petProduct);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                PetProduct petProduct = dataSnapshot.getValue(PetProduct.class);
                if (petProduct != null) {
                    mAdapter.update(petProduct);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                PetProduct petProduct = dataSnapshot.getValue(PetProduct.class);
                if (petProduct != null) {
                    mAdapter.remove(petProduct);
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ListingActivity.this, "Failed to load data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(PetProduct petProduct) {
        if (petProduct != null) {
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra("selected_product", petProduct);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Error: Selected product is null", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFavoriteClick(PetProduct petProduct) {
        if (petProduct != null) {
            // Toggle favorite status
            boolean isFavorite = !petProduct.isFavorite();
            petProduct.setFavorite(isFavorite);

            // Store favorite status using SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(PREF_FAVORITE_PREFIX + petProduct.getProductId(), isFavorite);
            editor.apply();

            mAdapter.update(petProduct); // Update the RecyclerView
        } else {
            // Handle null petProduct
        }
    }
}
