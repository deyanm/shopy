package com.deyanm.shopy.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.deyanm.shopy.R;
import com.deyanm.shopy.ui.home.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddPostActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDBReference;
    private FirebaseAuth firebaseAuth;
    private EditText nameEt, catEt, descEt, dateEt, cityEt;
    private String TAG = AddPostActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mDBReference = firebaseDatabase.getReference("posts");
        initViewWidgets();
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
        catEt = findViewById(R.id.category_et);
        descEt = findViewById(R.id.desc_et);
        dateEt = findViewById(R.id.date_et);
        cityEt = findViewById(R.id.city_et);
    }

    public void newPost(View view) {
        mDBReference.push().setValue(new Post(nameEt.getText().toString(), catEt.getText().toString(), dateEt.getText().toString(), descEt.getText().toString(), cityEt.getText().toString(), firebaseAuth.getCurrentUser().getUid())).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d(TAG, task.isSuccessful() + "");
            }
        });
        finish();
    }
}
