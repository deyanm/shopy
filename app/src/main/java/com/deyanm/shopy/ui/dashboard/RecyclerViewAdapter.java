package com.deyanm.shopy.ui.dashboard;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.deyanm.shopy.R;
import com.deyanm.shopy.ui.model.Product;

import java.text.DecimalFormat;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final Context context;

    private final List<Product> productList;

    private final boolean big;


    public RecyclerViewAdapter(List<Product> getDataAdapter, Context context, boolean big) {
        this.productList = getDataAdapter;
        this.context = context;
        this.big = big;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (big) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_layout, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_layout_small, parent, false);
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder Viewholder, int position) {
        Product product = productList.get(position);
        Viewholder.itemCardTitle.setText(product.getName());

        DecimalFormat df2 = new DecimalFormat("0.00");
        double formatPrice = product.getPrice();

        Viewholder.itemCardPrice.setText("RM " + df2.format(formatPrice));
        Viewholder.itemCardPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        Viewholder.itemCardSeller.setText(product.getShopName());
        Viewholder.itemCardPromotion.setText("-" + product.getDiscount() + "%");
        if (product.getDiscount() > 0) {
            Viewholder.itemCardPromotion.bringToFront();
            Viewholder.itemCardPromotion.setVisibility(View.VISIBLE);
            Viewholder.itemCardPrice.setVisibility(View.VISIBLE);
        } else {
            Viewholder.itemCardPromotion.setVisibility(View.INVISIBLE);
            Viewholder.itemCardPrice.setVisibility(View.INVISIBLE);
        }

        if (product.getDiscountPrice() != null) {
            double discountPrice = Double.parseDouble(product.getDiscountPrice());
            Viewholder.discountedPrice.setText("RM " + df2.format(discountPrice));
        }

    }

    @Override
    public int getItemCount() {

        return productList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final TextView itemCardTitle;
        final TextView itemCardPrice;
        final TextView itemCardSeller;
        final TextView itemCardPromotion;
        final TextView discountedPrice;
        final ImageView itemCardImage;

        ViewHolder(View itemView) {
            super(itemView);
            itemCardTitle = itemView.findViewById(R.id.itemCardTitle);
            itemCardPrice = itemView.findViewById(R.id.itemCardPrice);
            itemCardSeller = itemView.findViewById(R.id.itemCardSeller);
            itemCardPromotion = itemView.findViewById(R.id.itemCardPromotion);
            itemCardImage = itemView.findViewById(R.id.itemCardImage);
            discountedPrice = itemView.findViewById(R.id.discountedCardPrice);


        }
    }

    public void addList(List<Product> dataAdapter) {
        productList.addAll(dataAdapter);
        notifyItemRangeChanged(0, productList.size());
    }

    public void refreshList() {
        notifyItemRangeChanged(0, productList.size());
    }

    public List<Product> getListData() {
        return productList;
    }

}
