package com.deyanm.shopy.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.deyanm.shopy.R;
import com.deyanm.shopy.ui.model.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class AddProductActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDBReference;
    private FirebaseAuth firebaseAuth;
    private EditText nameEt, descEt, shopEt, codeEt, priceEt, minPrEt;
    private Spinner spinner;

    private String TAG = AddProductActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mDBReference = firebaseDatabase.getReference("products");
        initViewWidgets();
        List<String> list = new LinkedList<>();
        list.add("Electronic Accessories");
        list.add("Automotive");
        list.add("Electronic Devices");
        list.add("Home and Life-style");
        list.add("Home Appliance");
        list.add("Health and Beauty");
        list.add("Baby and Toys");
        list.add("Groceries and Pets");
        list.add("Women Fashion");
        list.add("Man Fashion");
        list.add("Fashion Accessories");
        list.add("Sports and Travel");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViewWidgets() {
        nameEt = findViewById(R.id.name_et);
        descEt = findViewById(R.id.desc_et);
        codeEt = findViewById(R.id.code_et);
        priceEt = findViewById(R.id.price_et);
        shopEt = findViewById(R.id.shop_et);
        minPrEt = findViewById(R.id.min_pr_et);
        spinner = findViewById(R.id.category_la);
    }

    public void newPost(View view) {
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        mDBReference.push().setValue(new Product(nameEt.getText().toString(), shopEt.getText().toString(), descEt.getText().toString(), spinner.getSelectedItem().toString(), Integer.parseInt(codeEt.getText().toString()), Float.parseFloat(priceEt.getText().toString()), Float.parseFloat(minPrEt.getText().toString()), currentDate)).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, task.isSuccessful() + "");
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}
