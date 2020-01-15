package com.deyanm.shopy.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deyanm.shopy.R;
import com.deyanm.shopy.AddPostActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FloatingActionButton fab;
    private PostViewAdapter postViewAdapter;
    private List<Post> postList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManagerofPostView;

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(this, s -> textView.setText(s));
        recyclerView = root.findViewById(R.id.home_recycler_view);
        layoutManagerofPostView = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManagerofPostView);
        postViewAdapter = new PostViewAdapter(postList, root.getContext(), (value, value2) -> {

        });
        recyclerView.setAdapter(postViewAdapter);
        fab = root.findViewById(R.id.fabHome);
        fab.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), AddPostActivity.class));
        });
        return root;
    }
}