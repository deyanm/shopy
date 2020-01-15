package com.deyanm.shopy.ui.category;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deyanm.shopy.R;
import com.deyanm.shopy.ui.model.Product;
import com.deyanm.shopy.util.ActivityUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {

    private static final String TAG = CategoryFragment.class.getSimpleName();
    private CategoryViewModel categoryViewModel;
    private DatabaseReference mDBReference;
    private FirebaseAuth firebaseAuth;
    private RecyclerView recyclerViewTop;
    private RecyclerView.Adapter recyclerViewAdapter;
    private ImageView addNewProduct;
    private List<Product> productList;
    private ProgressDialog progressDialog;

    public static CategoryFragment newInstance() {

        Bundle args = new Bundle();

        CategoryFragment fragment = new CategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        categoryViewModel =
                ViewModelProviders.of(this).get(CategoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_category, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        mDBReference = FirebaseDatabase.getInstance().getReference();
        addNewProduct = root.findViewById(R.id.addNewProduct);
        addNewProduct.setOnClickListener(view -> {
            startActivityForResult(new Intent(getActivity(), AddProductActivity.class), 1);
        });
        productList = new ArrayList<>();
        recyclerViewTop = root.findViewById(R.id.rcvHome1);
        recyclerViewTop.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManagerOfrecyclerViewTop = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewTop.setLayoutManager(layoutManagerOfrecyclerViewTop);
        progressDialog = ActivityUtils.displayProgressDialog(getContext(), "Loading data...");

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        getNewArrivals();
    }

    private void getNewArrivals() {
        progressDialog.show();
        productList.clear();
        if (recyclerViewAdapter == null) {
            recyclerViewAdapter = new RecyclerViewAdapter(productList, this.getContext(), false, product -> {
                itemDetailActivity(product);
            });
            recyclerViewTop.setAdapter(recyclerViewAdapter);
        }
        recyclerViewAdapter.notifyDataSetChanged();

        mDBReference.child("products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Product product = data.getValue(Product.class);
                    productList.add(product);
                }
                recyclerViewAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("The read failed: ", databaseError.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    private void itemDetailActivity(Product product) {
        Intent intent = new Intent(getActivity(), ItemDetailActivity.class);
        intent.putExtra("product", product);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                getNewArrivals();
                Toast.makeText(getContext(), "Successfully added product", Toast.LENGTH_SHORT).show();
            }
        }
    }
}