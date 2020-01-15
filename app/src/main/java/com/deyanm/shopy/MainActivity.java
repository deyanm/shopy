package com.deyanm.shopy;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.deyanm.shopy.ui.cart.CartFragment;
import com.deyanm.shopy.ui.category.CategoryFragment;
import com.deyanm.shopy.ui.home.HomeFragment;
import com.deyanm.shopy.ui.profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private Fragment currentFragment;
    private BottomNavigationView navView;

    private static final String TAG_FRAGMENT_ONE = "Home";
    private static final String TAG_FRAGMENT_TWO = "Category";
    private static final String TAG_FRAGMENT_THREE = "Shopping Cart";
    private static final String TAG_FRAGMENT_FOUR = "Profile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(menuItem -> {
            Fragment fragment;
            switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    fragment = fragmentManager.findFragmentByTag(TAG_FRAGMENT_ONE);
                    if (fragment == null) {
                        fragment = HomeFragment.newInstance();
                    }
                    replaceFragment(fragment, TAG_FRAGMENT_ONE);
                    getSupportActionBar().setTitle(TAG_FRAGMENT_ONE);
                    break;
                case R.id.navigation_category:
                    fragment = fragmentManager.findFragmentByTag(TAG_FRAGMENT_TWO);
                    if (fragment == null) {
                        fragment = CategoryFragment.newInstance();
                    }
                    replaceFragment(fragment, TAG_FRAGMENT_TWO);
                    getSupportActionBar().setTitle(TAG_FRAGMENT_TWO);
                    break;
                case R.id.navigation_cart:
                    fragment = fragmentManager.findFragmentByTag(TAG_FRAGMENT_THREE);
                    if (fragment == null) {
                        fragment = CartFragment.newInstance();
                    }
                    replaceFragment(fragment, TAG_FRAGMENT_THREE);
                    getSupportActionBar().setTitle(TAG_FRAGMENT_THREE);
                    break;
                case R.id.navigation_profile:
                    fragment = fragmentManager.findFragmentByTag(TAG_FRAGMENT_FOUR);
                    if (fragment == null) {
                        fragment = ProfileFragment.newInstance();
                    }
                    replaceFragment(fragment, TAG_FRAGMENT_FOUR);
                    getSupportActionBar().setTitle(TAG_FRAGMENT_FOUR);
                    break;
            }
            return true;
        });
        navView.setSelectedItemId(R.id.navigation_home);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_cart, R.id.navigation_profile)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);
    }


    private void replaceFragment(@NonNull Fragment fragment, @NonNull String tag) {
        if (!fragment.equals(currentFragment)) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment, tag)
                    .commit();
            currentFragment = fragment;
        }
    }
}
