package com.example.furryfriends;

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

import java.util.ArrayList;
import java.util.List;

public class WishListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private WishListAdapter mAdapter;
    private List<PetProduct> mWishlistItems;

    private DatabaseReference mDatabaseReference;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String PREF_FAVORITE_PREFIX = "favorite_";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);

        mRecyclerView = findViewById(R.id.wishlist_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mWishlistItems = new ArrayList<>();
        mAdapter = new WishListAdapter(this, mWishlistItems);
        mRecyclerView.setAdapter(mAdapter);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("pet_products");
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                PetProduct petProduct = dataSnapshot.getValue(PetProduct.class);
                if (petProduct != null && isProductFavorited(petProduct.getProductId())) {
                    mWishlistItems.add(petProduct);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                PetProduct petProduct = dataSnapshot.getValue(PetProduct.class);
                if (petProduct != null) {
                    if (isProductFavorited(petProduct.getProductId())) {
                        if (!mWishlistItems.contains(petProduct)) {
                            mWishlistItems.add(petProduct);
                            mAdapter.notifyDataSetChanged();
                        }
                    } else {
                        mWishlistItems.remove(petProduct);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                PetProduct petProduct = dataSnapshot.getValue(PetProduct.class);
                if (petProduct != null) {
                    mWishlistItems.remove(petProduct);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(WishListActivity.this, "Failed to load data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isProductFavorited(String productId) {
        return sharedPreferences.getBoolean(PREF_FAVORITE_PREFIX + productId, false);
    }
}
