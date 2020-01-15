package com.deyanm.shopy.ui.cart;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.deyanm.shopy.R;
import com.deyanm.shopy.ui.model.CartItem;

import java.text.DecimalFormat;
import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {

    private final List<CartItem> cartItems;
    private final Context context;
    private final boolean[] checked;
    private final ItemCartClick mCallback;
    private final DecimalFormat df2 = new DecimalFormat("0.00");

    public CartItemAdapter(List<CartItem> cartItems, Context context, ItemCartClick listener) {
        this.cartItems = cartItems;
        this.context = context;
        this.mCallback = listener;
        checked = new boolean[cartItems.size()];
    }

    @Override
    public CartItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CartItemAdapter.ViewHolder Viewholder, final int position) {
        CartItem cartItem = cartItems.get(position);
        Viewholder.shopName.setText(cartItem.getProduct().getShopName());
        Viewholder.itemName.setText(cartItem.getProduct().getName());

        Viewholder.itemPrice.setText("RM " + df2.format((cartItem.getProduct().getPrice())));
        Viewholder.itemPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        Viewholder.itemQty.setText(cartItem.getProduct().getQuantity());
        Viewholder.deleteBut.setOnClickListener(view -> {
            mCallback.deleteFromCart(cartItem.getProduct().getCode());
        });

        Viewholder.stocklimitText.setText(cartItem.getProduct().getQuantity());
        if (cartItem.getProduct().getQuantity() <= 5) {
            Viewholder.stocklimitText.setTextColor(context.getResources().getColor(R.color.scarletRed));
        }

        Viewholder.cartChkBox.setOnClickListener(view -> {
            checked[Viewholder.getAdapterPosition()] = Viewholder.cartChkBox.isChecked();
            mCallback.checkItem(Viewholder.cartChkBox.isChecked());
        });


//        Viewholder.incBut.setOnClickListener(view -> {
//            int currentQty = Integer.parseInt(cartItems.get(Viewholder.getAdapterPosition()).getCartQty());
//            Log.d("currentQty:", Integer.toString(Viewholder.getAdapterPosition()));
//            if (Integer.parseInt(cartItems.get(Viewholder.getAdapterPosition()).getCartQty()) < Integer.parseInt(cartItems.get(Viewholder.getAdapterPosition()).getLimitqty())) {
//                currentQty = Integer.parseInt(cartItems.get(Viewholder.getAdapterPosition()).getCartQty());
//                currentQty++;
//                Viewholder.itemQty.setText(Integer.toString(currentQty));
//            }
//            cartItems.get(Viewholder.getAdapterPosition()).setCartQty(Integer.toString(currentQty));
//            mCallback.onClick(Double.toString(calculateTotal()));
//        });
//        Viewholder.decBut.setOnClickListener(view -> {
//            int currentQty = Integer.parseInt(cartItems.get(Viewholder.getAdapterPosition()).getCartQty());
//            Log.d("currentQty:", Integer.toString(Viewholder.getAdapterPosition()));
//            if (Integer.parseInt(cartItems.get(Viewholder.getAdapterPosition()).getCartQty()) > 1) {
//                currentQty = Integer.parseInt(cartItems.get(Viewholder.getAdapterPosition()).getCartQty());
//                currentQty--;
//                Viewholder.itemQty.setText(Integer.toString(currentQty));
//            }
//            cartItems.get(Viewholder.getAdapterPosition()).setCartQty(Integer.toString(currentQty));
//            mCallback.onClick(Double.toString(calculateTotal()));
//
//        });


        Viewholder.cartChkBox.setChecked(checked[Viewholder.getAdapterPosition()]);
        Viewholder.txtDiscount.setText("-" + cartItem.getProduct().getDiscount() + "% off");
        Viewholder.discountedtxtPrice.setText("RM" + df2.format(Double.parseDouble(cartItem.getProduct().getDiscountPrice())));


        if (cartItem.getProduct().getDiscount() == 0) {
            Viewholder.txtDiscount.setVisibility(View.INVISIBLE);
            Viewholder.itemPrice.setVisibility(View.INVISIBLE);
        } else {
            Viewholder.txtDiscount.setVisibility(View.VISIBLE);
            Viewholder.itemPrice.setVisibility(View.VISIBLE);
        }

    }


    private boolean cartIsChecked(int index) {
        return checked[index];
    }

    @Override
    public int getItemCount() {

        return cartItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        Button incBut, decBut;
        ImageView shopIcon, itemImage;
        TextView shopName, itemName, itemPrice, itemQty, subTotal, stocklimitText, txtDiscount, discountedtxtPrice;
        CheckBox cartChkBox;
        ImageButton deleteBut;


        ViewHolder(View itemView) {
            super(itemView);
            incBut = itemView.findViewById(R.id.cartIncQty);
            decBut = itemView.findViewById(R.id.cartDecQty);
            shopIcon = itemView.findViewById(R.id.cartShopIcon);
            itemImage = itemView.findViewById(R.id.VolleyImageView);
            shopName = itemView.findViewById(R.id.cartShopName);
            itemName = itemView.findViewById(R.id.txtTitle);
            itemPrice = itemView.findViewById(R.id.txtPrice);
            itemQty = itemView.findViewById(R.id.cartQty);
            cartChkBox = itemView.findViewById(R.id.cartChkBox);
            subTotal = itemView.findViewById(R.id.subTotal);
            deleteBut = itemView.findViewById(R.id.removecartBut);
            stocklimitText = itemView.findViewById(R.id.stockLimitText);
            txtDiscount = itemView.findViewById(R.id.txtDiscount);
            discountedtxtPrice = itemView.findViewById(R.id.discountedtxtPrice);
        }
    }

    public double calculateTotal() {
        double cartsubtotal = 0;
        for (int a = 0; a < cartItems.size(); a++) {
            if (cartIsChecked(a)) {
                cartsubtotal = cartsubtotal + cartItems.get(a).getCartQuantity() * Double.parseDouble(cartItems.get(a).getProduct().getDiscountPrice());
            }
        }
        Log.d("total", df2.format(cartsubtotal));
        return cartsubtotal;
    }


    private boolean loopChk() {
        for (int a = 0; a < cartItems.size(); a++) {
            if (!cartIsChecked(a)) {
                return false;
            }
        }
        return true;
    }

    public interface ItemCartClick {
        void deleteFromCart(int prod_code);
        void checkItem(boolean isChecked);
    }
}
