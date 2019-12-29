package com.deyanm.shopy.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.deyanm.shopy.LoginActivity;
import com.deyanm.shopy.R;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private RelativeLayout changePass, logout;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;

    public static ProfileFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        initViewWidgets(root);
        mAuth = FirebaseAuth.getInstance();

        mAuthListner = firebaseAuth -> {
            if (firebaseAuth.getCurrentUser() == null) {
                startActivity(new Intent(root.getContext(), LoginActivity.class));
            }
        };

        logout.setOnClickListener(v -> mAuth.signOut());

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }

    private void initViewWidgets(View view) {
        changePass = view.findViewById(R.id.changePasswordRelative);
        logout = view.findViewById(R.id.logoutRelative);
    }
}