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

import java.util.ArrayList;
import java.util.List;

public class ListingAdapter extends RecyclerView.Adapter<ListingAdapter.ViewHolder> {
    private List<PetProduct> mPetProductList;
    private Context mContext;
    private OnItemClickListener mListener;
    private ImageView favoriteIcon;
    private boolean isFavorite = false; // Track favorite status

    public ListingAdapter(Context context) {
        mContext = context;
        mPetProductList = new ArrayList<>();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void add(PetProduct petProduct) {
        mPetProductList.add(petProduct);
        notifyItemInserted(mPetProductList.size() - 1);
    }

    public void update(PetProduct petProduct) {
        int index = mPetProductList.indexOf(petProduct);
        if (index != -1) {
            mPetProductList.set(index, petProduct);
            notifyItemChanged(index);
        }
    }

    public void remove(PetProduct petProduct) {
        int index = mPetProductList.indexOf(petProduct);
        if (index != -1) {
            mPetProductList.remove(index);
            notifyItemRemoved(index);
        }
    }

    public PetProduct getItem(int position) {
        if (position >= 0 && position < mPetProductList.size()) {
            return mPetProductList.get(position);
        }
        return null;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.listing_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PetProduct petProduct = mPetProductList.get(position);
        holder.bind(petProduct);
        holder.setupFavoriteIcon(petProduct);

        holder.mFavoriteButton.setOnClickListener(v -> {
            if (position != RecyclerView.NO_POSITION && mListener != null) {
                mListener.onFavoriteClick(mPetProductList.get(position));
            }
        });
    }


    @Override
    public int getItemCount() {
        return mPetProductList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mProductName;
        private TextView mProductDescription;
        private TextView mLocation;
        private ImageView mProductImage;
        private ImageView mFavoriteButton; // Add favorite button ImageView

        public ViewHolder(View itemView) {
            super(itemView);
            mProductName = itemView.findViewById(R.id.product_name);
            mProductDescription = itemView.findViewById(R.id.product_description);
            mLocation = itemView.findViewById(R.id.location);
            mProductImage = itemView.findViewById(R.id.product_image);
            mFavoriteButton = itemView.findViewById(R.id.favorite_button);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && mListener != null) {
                    mListener.onItemClick(mPetProductList.get(position));
                }
            });

            // Set click listener for favorite button
            mFavoriteButton.setOnClickListener(view -> {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && mListener != null) {
                        mListener.onFavoriteClick(mPetProductList.get(position));
                    }
            });
        }

        public void bind(PetProduct petProduct) {
            mProductName.setText(petProduct.getProductName());
            mProductDescription.setText(petProduct.getProductDescription());
            mLocation.setText(petProduct.getLocation());
            Glide.with(mContext).load(petProduct.getImageUrl()).into(mProductImage);
        }

        public void setupFavoriteIcon(PetProduct petProduct) {
            if (petProduct.isFavorite()) {
                mFavoriteButton.setImageResource(R.drawable.ic_fav_heart_closed); // Set filled favorite icon if already favorite
            } else {
                mFavoriteButton.setImageResource(R.drawable.ic_fav); // Set empty favorite icon if not favorite
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(PetProduct petProduct);
        void onFavoriteClick(PetProduct petProduct); // Add favorite button click listener
    }
}
