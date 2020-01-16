package com.deyanm.shopy.ui.cart;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.deyanm.shopy.R;
import com.deyanm.shopy.ui.model.CartItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    private static final String TAG = CartFragment.class.getSimpleName();
    private CartViewModel cartViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CheckBox selectAllChkBox;
    private TextView subtotal, orderDialogText, orderDialogAnswer, emptyText;
    private ImageView emptyImg;
    private Button finalChkOutBtn;
    private String uid;
    private List<CartItem> cartItemList;

    private CartItemAdapter cartAdapter;
    private View view;
    int RecyclerViewItemPosition;
    double cartsubtotal = 0;
    private boolean isEmpty = false;
    private SwipeRefreshLayout refreshLayout;
    private boolean doneLoading = false;
    private final DecimalFormat df2 = new DecimalFormat("0.00");
    private Dialog orderDialog;
    private FirebaseAuth firebaseAuth;

    private FirebaseAuth mAuth;
    private DatabaseReference mReference;

    public static CartFragment newInstance() {

        Bundle args = new Bundle();

        CartFragment fragment = new CartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        selectAllChkBox.setChecked(false);
        finalChkOutBtn.setEnabled(false);
        getCartItemsFirebase();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cartViewModel =
                ViewModelProviders.of(this).get(CartViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cart, container, false);

        mAuth = FirebaseAuth.getInstance();
        mReference = FirebaseDatabase.getInstance().getReference();
        recyclerView = root.findViewById(R.id.shoppingCartRecycleView);
        selectAllChkBox = root.findViewById(R.id.shopCartSelectAllChkBox);
        subtotal = root.findViewById(R.id.subTotal);
        finalChkOutBtn = root.findViewById(R.id.finalChkOutBtn);
        refreshLayout = root.findViewById(R.id.cartRefresh);
        emptyImg = root.findViewById(R.id.shopCartEmptyImg);
        emptyText = root.findViewById(R.id.shopCartEmptyText);

        refreshLayout.setColorSchemeResources(R.color.orange);
        cartItemList = new ArrayList<>();

        refreshLayout.setOnRefreshListener(() -> {
            getCartItemsFirebase();
            refreshLayout.setRefreshing(false);
        });
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManagerOfrecyclerView = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManagerOfrecyclerView);

        selectAllChkBox.setChecked(false);

        selectAllChkBox.setOnClickListener(view -> {
            if (cartAdapter == null) {
                getCartItemsFirebase();
            } else {
                cartAdapter.chkAll(selectAllChkBox.isChecked());
                subtotal.setText("$" + df2.format(cartAdapter.calculateTotal()));
                if (selectAllChkBox.isChecked() && isEmpty) {
                    finalChkOutBtn.setEnabled(true);
                } else {
                    finalChkOutBtn.setEnabled(false);
                }
            }

        });

//        finalChkOutBtn.setOnClickListener(view -> {
//            Toast.makeText(getContext(), "UID" + Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail(), Toast.LENGTH_SHORT).show();
//            int getItemCount;
//            if (cartAdapter == null || !doneLoading) {
//                getCartItemsFirebase();
//            } else {
//                boolean[] checkList = cartAdapter.getCheckedList();
//                List<CartItem> returnedCartItem = cartAdapter.getCartItemList(); //checked item get from adapter, further code implementation please refer "CartItemAdapter.java"
//
//                JSONArray JSONUploadArray = new JSONArray();
//
//                getItemCount = checkList.length;
//                Answers.getInstance().logStartCheckout(new StartCheckoutEvent()
//                        .putCurrency(Currency.getInstance("MYR"))
//                        .putTotalPrice(new BigDecimal(df2.format(cartAdapter.calculateTotal())))
//                        .putItemCount(getItemCount));
//                for (int a = 0; a < checkList.length; a++) {
//                    if (checkList[a]) {  //if checked the item
//                        Log.d("order qty", returnedCartItem.get(a).getCartQty());
//                        Log.d("order cartID", returnedCartItem.get(a).getCartID());
//                        Log.d("order prodVar", returnedCartItem.get(a).getProdVariant());
//
//                        //get quantity
//                        //cartID
//                        //product variant
//                        JSONObject JSONPlaceOrderProdcut = new JSONObject();
//                        try {
//                            JSONPlaceOrderProdcut.put("cartQty", returnedCartItem.get(a).getCartQty());
//                            JSONPlaceOrderProdcut.put("cartID", returnedCartItem.get(a).getCartID());
//                            JSONPlaceOrderProdcut.put("prodVariant", returnedCartItem.get(a).getProdVariant());
//
//                            JSONUploadArray.put(JSONPlaceOrderProdcut);
//
//
//                        } catch (JSONException e) {
//                            Crashlytics.logException(e);
//                            // handle your exception here!
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//
//        });
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        return root;
    }

    private void getCartItemsFirebase() {
        selectAllChkBox.setChecked(false);
        finalChkOutBtn.setEnabled(false);

        subtotal.setText("$0.00");
        cartAdapter = null;

        mReference.child("users").child(mAuth.getCurrentUser().getUid()).child("cart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cartItemList.clear();
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    CartItem item = snap.getValue(CartItem.class);
                    cartItemList.add(item);
                }
                cartAdapter = new CartItemAdapter(cartItemList, getActivity(), new CartItemAdapter.ItemCartClick() {
                    @Override
                    public void deleteFromCart(int prod_code) {
                        mReference.getRoot().child("users").child(mAuth.getCurrentUser().getUid()).child("cart").child(Integer.toString(prod_code)).removeValue();

                    }

                    @Override
                    public void checkTotal(boolean isChecked) {
                        selectAllChkBox.setChecked(isChecked);
                    }

                    @Override
                    public void totalChange(String total) {
                        subtotal.setText("$" + df2.format(Double.parseDouble(total)));
                        if (Double.parseDouble(total) == 0) {
                            finalChkOutBtn.setEnabled(false);
                        } else {
                            finalChkOutBtn.setEnabled(true);
                        }
                    }
                });

                recyclerView.setAdapter(cartAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}