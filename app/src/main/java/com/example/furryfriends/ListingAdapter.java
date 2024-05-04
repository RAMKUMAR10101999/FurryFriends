package com.example.furryfriends;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ListingAdapter extends RecyclerView.Adapter<ListingAdapter.ViewHolder> implements Filterable {
    private List<PetProduct> mPetProductList;
    private List<PetProduct> mPetProductListFiltered;
    private Context mContext;
    private OnItemClickListener mListener;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String PREF_FAVORITE_PREFIX = "favorite_";

    public ListingAdapter(Context context) {
        mContext = context;
        mPetProductList = new ArrayList<>();
        mPetProductListFiltered = new ArrayList<>();
        mPetProductListFiltered.addAll(mPetProductList);
        sharedPreferences = mContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void add(PetProduct petProduct) {
        mPetProductList.add(petProduct);
        mPetProductListFiltered.add(petProduct);
        notifyItemInserted(mPetProductList.size() - 1);
    }

    public void update(PetProduct petProduct) {
        int index = mPetProductList.indexOf(petProduct);
        if (index != -1) {
            mPetProductList.set(index, petProduct);
            mPetProductListFiltered.set(index, petProduct);
            notifyItemChanged(index);
        }
    }

    public void remove(PetProduct petProduct) {
        int index = mPetProductList.indexOf(petProduct);
        if (index != -1) {
            mPetProductList.remove(index);
            mPetProductListFiltered.remove(index);
            notifyItemRemoved(index);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.listing_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PetProduct petProduct = mPetProductListFiltered.get(position);
        holder.bind(petProduct);
    }

    @Override
    public int getItemCount() {
        return mPetProductListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String searchText = constraint.toString().toLowerCase().trim();
                List<PetProduct> filteredList = new ArrayList<>();

                if (searchText.isEmpty()) {
                    filteredList.addAll(mPetProductList);
                } else {
                    for (PetProduct product : mPetProductList) {
                        if (product.getProductName().toLowerCase().contains(searchText)) {
                            filteredList.add(product);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mPetProductListFiltered.clear();
                mPetProductListFiltered.addAll((List<PetProduct>) results.values);
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mProductName;
        private TextView mProductDescription;
        private TextView mLocation;
        private ImageView mProductImage;
        private ImageView mFavoriteButton;

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
                    mListener.onItemClick(mPetProductListFiltered.get(position));
                }
            });

            mFavoriteButton.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && mListener != null) {
                    mListener.onFavoriteClick(mPetProductListFiltered.get(position));
                }
            });
        }

        public void bind(PetProduct petProduct) {
            mProductName.setText(petProduct.getProductName());
            mProductDescription.setText(petProduct.getProductDescription());
            mLocation.setText(petProduct.getLocation());
            Glide.with(mContext).load(petProduct.getImageUrl()).into(mProductImage);

            boolean isFavorite = sharedPreferences.getBoolean(PREF_FAVORITE_PREFIX + petProduct.getProductId(), false);
            petProduct.setFavorite(isFavorite);

            if (petProduct.isFavorite()) {
                mFavoriteButton.setImageResource(R.drawable.ic_fav_heart_closed);
            } else {
                mFavoriteButton.setImageResource(R.drawable.ic_fav);
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(PetProduct petProduct);
        void onFavoriteClick(PetProduct petProduct);
    }
}
