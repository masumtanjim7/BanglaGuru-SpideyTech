package com.banglaguru.app.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.banglaguru.app.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private NavController navController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentContainerView navHost = findViewById(R.id.nav_host_fragment);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(navHost.getId());
        navController = navHostFragment.getNavController();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.homeFragment) {
                navController.popBackStack(R.id.homeFragment, false);
                navController.navigate(R.id.homeFragment);
                return true;
            } else if (id == R.id.grammarTab) {
                Bundle args = new Bundle();
                args.putString("category", "grammar");
                navController.navigate(R.id.lessonListFragment, args);
                return true;
            } else if (id == R.id.literatureTab) {
                Bundle args = new Bundle();
                args.putString("category", "literature");
                navController.navigate(R.id.lessonListFragment, args);
                return true;
            } else if (id == R.id.mcqSetupFragment) {
                navController.navigate(R.id.mcqSetupFragment);
                return true;
            } else if (id == R.id.moreFragment) {
                navController.navigate(R.id.moreFragment);
                return true;
            }
            return false;
        });
    }
}
