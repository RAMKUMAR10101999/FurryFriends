package com.example.furryfriends;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.ViewHolder> {
    private Context mContext;
    private List<PetProduct> mWishlistItems;

    public WishListAdapter(Context context, List<PetProduct> wishlistItems) {
        mContext = context;
        mWishlistItems = wishlistItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.wishlist_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PetProduct wishlistItem = mWishlistItems.get(position);
        holder.bind(wishlistItem);
    }

    @Override
    public int getItemCount() {
        return mWishlistItems.size();
    }

    public interface OnItemClickListener {
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mProductName;
        private TextView mProductDescription;
        private TextView mLocation;
        private ImageView mProductImage;

        public ViewHolder(View itemView) {
            super(itemView);
            mProductName = itemView.findViewById(R.id.wishlist_product_name);
            mProductDescription = itemView.findViewById(R.id.wishlist_product_description);
            mLocation = itemView.findViewById(R.id.wishlist_location);
            mProductImage = itemView.findViewById(R.id.wishlist_product_image);
        }

        public void bind(PetProduct wishlistItem) {
            mProductName.setText(wishlistItem.getProductName());
            mProductDescription.setText(wishlistItem.getProductDescription());
            mLocation.setText(wishlistItem.getLocation());
            Glide.with(mContext).load(wishlistItem.getImageUrl()).into(mProductImage);
        }
    }
}
