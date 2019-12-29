package com.deyanm.shopy;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.deyanm.shopy.ui.cart.CartFragment;
import com.deyanm.shopy.ui.dashboard.DashboardFragment;
import com.deyanm.shopy.ui.home.HomeFragment;
import com.deyanm.shopy.ui.profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private Fragment currentFragment;

    private static final String TAG_FRAGMENT_ONE = "cart_fragment";
    private static final String TAG_FRAGMENT_TWO = "dashboard_fragment";
    private static final String TAG_FRAGMENT_THREE = "home_fragment";
    private static final String TAG_FRAGMENT_FOUR = "profile_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(menuItem -> {
            Fragment fragment;
            switch (menuItem.getItemId()) {
                case R.id.navigation_cart:
                    fragment = fragmentManager.findFragmentByTag(TAG_FRAGMENT_ONE);
                    if (fragment == null) {
                        fragment = CartFragment.newInstance();
                    }
                    replaceFragment(fragment, TAG_FRAGMENT_ONE);

                    break;
                case R.id.navigation_dashboard:

                    fragment = fragmentManager.findFragmentByTag(TAG_FRAGMENT_TWO);
                    if (fragment == null) {
                        fragment = DashboardFragment.newInstance();
                    }
                    replaceFragment(fragment, TAG_FRAGMENT_TWO);

                    break;
                case R.id.navigation_home:

                    fragment = fragmentManager.findFragmentByTag(TAG_FRAGMENT_THREE);
                    if (fragment == null) {
                        fragment = HomeFragment.newInstance();
                    }
                    replaceFragment(fragment, TAG_FRAGMENT_THREE);

                    break;
                case R.id.navigation_profile:

                    fragment = fragmentManager.findFragmentByTag(TAG_FRAGMENT_FOUR);
                    if (fragment == null) {
                        fragment = ProfileFragment.newInstance();
                    }
                    replaceFragment(fragment, TAG_FRAGMENT_FOUR);

                    break;
            }
            return true;
        });
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_cart, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }


    private void replaceFragment(@NonNull Fragment fragment, @NonNull String tag) {
        if (!fragment.equals(currentFragment)) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment, tag)
                    .commit();
            currentFragment = fragment;
        }
    }
}
