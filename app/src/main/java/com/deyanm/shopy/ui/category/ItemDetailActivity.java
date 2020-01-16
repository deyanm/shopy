package com.deyanm.shopy.ui.category;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.deyanm.shopy.R;
import com.deyanm.shopy.ui.model.CartItem;
import com.deyanm.shopy.ui.model.Product;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;

public class ItemDetailActivity extends AppCompatActivity {

    private static final String TAG = ItemDetailActivity.class.getSimpleName();
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private BottomSheetDialog addToCartDialog;
    private Button addToCartButton;
    private Button confirmAddToCart, checkoutBut;
    private ImageView itemDetailImage;
    private Product mProduct;
    private TextView itemName, itemPrice, itemQuantity, discountText, discountedPrice, shopName, shopName2;
    private TextView selectedQty, itemDetailQty, itemDetailPriceText, itemDetailPrice, itemDetailDiscountedPrice, itemDetailDiscountPriceText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        getSupportActionBar().setTitle("Item Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViewWidgets();

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users/");
        if (getIntent() != null) {
            mProduct = (Product) getIntent().getSerializableExtra("product");
        }

        addToCartButton.setOnClickListener(v -> {
            createAddToCartDialog();
        });
        bindData();
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
        addToCartButton = findViewById(R.id.addToCartButton);
        itemPrice = findViewById(R.id.viewPrice);
        itemName = findViewById(R.id.viewTitle);
        itemQuantity = findViewById(R.id.quantity_text);
        discountText = findViewById(R.id.itemDetailPromotion);
        discountText.bringToFront();
        discountedPrice = findViewById(R.id.viewDiscountedPrice);
        shopName = findViewById(R.id.seller_shop);
        shopName2 = findViewById(R.id.viewShop);
    }

    private void createAddToCartDialog() {
        View view = getLayoutInflater().inflate(R.layout.add_to_cart_layout, null);
        addToCartDialog = new BottomSheetDialog(this, R.style.ThemeBottomSheetDialog);
        addToCartDialog.setContentView(view);
        addToCartDialog.show();

        Button increaseBut = addToCartDialog.findViewById(R.id.increaseQtyBut);
        Button decreaseBut = addToCartDialog.findViewById(R.id.decreaseQtyBut);
        confirmAddToCart = addToCartDialog.findViewById(R.id.confirmAddToCart);
        checkoutBut = addToCartDialog.findViewById(R.id.checkoutNowButton);
        itemDetailImage = addToCartDialog.findViewById(R.id.itemDetailImage);
        selectedQty = addToCartDialog.findViewById(R.id.selectedQuantity);
        itemDetailQty = addToCartDialog.findViewById(R.id.itemDetailQuantityText);
        itemDetailPrice = addToCartDialog.findViewById(R.id.itemDetailPriceText);
        itemDetailDiscountedPrice = addToCartDialog.findViewById(R.id.itemDetailDiscountedPriceText);
        itemDetailDiscountPriceText = addToCartDialog.findViewById(R.id.itemDetailDiscountPriceText);
        ImageButton closeBut = addToCartDialog.findViewById(R.id.closeItemDetailBut);

        itemDetailImage.bringToFront();

        itemDetailQty.setText(R.string.chooseAProductVariant);
        itemDetailPrice.setText(R.string.chooseAProductVariant);
        itemDetailPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        itemDetailDiscountedPrice.setText(R.string.chooseAProductVariant);
        itemDetailDiscountPriceText.setVisibility(View.GONE);
        itemDetailPrice.setVisibility(View.GONE);

        closeBut.setOnClickListener(view1 -> addToCartDialog.dismiss());

        confirmAddToCart.setOnClickListener(view12 -> {
            addToCart();
        });

        increaseBut.setOnClickListener(view13 -> {
            int qty = Integer.parseInt(selectedQty.getText().toString());
            if (qty < mProduct.getQuantity()) {
                qty++;
            }
            selectedQty.setText(Integer.toString(qty));
        });

        decreaseBut.setOnClickListener(view14 -> {
            int qty = Integer.parseInt(selectedQty.getText().toString());
            if (qty > 1) {
                qty--;
            }
            selectedQty.setText(Integer.toString(qty));
        });

        checkoutBut.setOnClickListener(view15 -> {
            addToCart();
            addToCartDialog.dismiss();

            Intent resultIntent = new Intent();
            resultIntent.putExtra("checkout", "111");

            setResult(ItemDetailActivity.RESULT_OK, resultIntent);
            finish();

        });
    }

    private void bindData() {
        itemName.setText(mProduct.getName());
        itemPrice.setText(String.valueOf(mProduct.getPrice()));
        itemPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        if (mProduct.getDiscount() == 0) {
            discountText.setVisibility(View.GONE);
            itemPrice.setVisibility(View.GONE);
        }
        double discountPrice = mProduct.getPrice() - (mProduct.getPrice() * (mProduct.getDiscount() / 100.0));
        DecimalFormat df2 = new DecimalFormat("0.00");
        discountedPrice.setText("$" + df2.format(discountPrice));
        itemQuantity.setText(Integer.toString(mProduct.getQuantity()) + " units");
        shopName.setText(mProduct.getShopName());
        shopName2.setText(mProduct.getShopName());
    }

    private void addToCart() {
        databaseReference.child(mAuth.getCurrentUser().getUid()).child("cart").child(String.valueOf(mProduct.getCode())).setValue(new CartItem(mProduct, Integer.parseInt(selectedQty.getText().toString()))).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "Successfully added");
                if (addToCartDialog.isShowing()) {
                    addToCartDialog.dismiss();
                }
            } else {
                Toast.makeText(ItemDetailActivity.this, "Failure adding to cart!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
