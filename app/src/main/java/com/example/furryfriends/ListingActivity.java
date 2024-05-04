package com.example.furryfriends;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ListingActivity extends AppCompatActivity implements ListingAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private ListingAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SearchView mSearchView;

    private DatabaseReference mDatabaseReference;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String PREF_FAVORITE_PREFIX = "favorite_";

    private List<PetProduct> mPetProductsListFull; // Full list to use for filtering

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);

        mRecyclerView = findViewById(R.id.recyclerView);
        mSearchView = findViewById(R.id.searchView);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ListingAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(this);
        mSearchView = findViewById(R.id.searchView);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("pet_products");
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        mPetProductsListFull = new ArrayList<>(); // Initialize full list

        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                PetProduct petProduct = dataSnapshot.getValue(PetProduct.class);
                if (petProduct != null) {
                    mPetProductsListFull.add(petProduct); // Add to full list
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

        // Setup SearchView listener
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText); // Apply filter on adapter
                return false;
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
            boolean isFavorite = !petProduct.isFavorite();
            petProduct.setFavorite(isFavorite);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(PREF_FAVORITE_PREFIX + petProduct.getProductId(), isFavorite);
            editor.apply();

            mAdapter.update(petProduct);
        } else {
            Toast.makeText(this, "Error: Product not found", Toast.LENGTH_SHORT).show();
        }
    }
}
