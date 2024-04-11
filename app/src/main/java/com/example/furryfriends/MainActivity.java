package com.example.furryfriends;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize TabLayout and ViewPager
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);

        // Create an adapter for the ViewPager
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Add fragments for each tab
        viewPagerAdapter.addFragment(new ProductFragment(), "Product 1");
        viewPagerAdapter.addFragment(new ProductFragment(), "Product 2");
        viewPagerAdapter.addFragment(new ProductFragment(), "Product 3");
        viewPagerAdapter.addFragment(new ProductFragment(), "Product 4");

        // Set the adapter for the ViewPager
        viewPager.setAdapter(viewPagerAdapter);

        // Connect the TabLayout with the ViewPager
        tabLayout.setupWithViewPager(viewPager);
    }
}

class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}

class ProductFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        // Initialize UI elements
        TextView productNameTextView = view.findViewById(R.id.product_name_text_view);
        TextView locationTextView = view.findViewById(R.id.location_text_view);
        TextView dateTextView = view.findViewById(R.id.date_text_view);
        ImageView productImageView = view.findViewById(R.id.product_image_view);

        // Set product details
        productNameTextView.setText("Product Name");
        locationTextView.setText("Location");
        dateTextView.setText("Date");

        // Load product image
        Picasso.get().load("Product Image URL").into(productImageView);

        return view;
    }
}