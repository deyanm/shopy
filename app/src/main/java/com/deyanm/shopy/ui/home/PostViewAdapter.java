package com.deyanm.shopy.ui.home;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.deyanm.shopy.R;
import com.deyanm.shopy.ReviewActivity;

import java.util.List;

public class PostViewAdapter extends RecyclerView.Adapter<PostViewAdapter.ViewHolder> {

    private Context context;
    private Dialog shareDialog;
    private ImageView shareFB, shareTwitter, shareInsta;
    private TextView shareShareTo;

    public static final int RequestPermissionCode = 1;

    private List<Post> postList;
    private OnItemClick mCallback;


    public PostViewAdapter(List<Post> getDataAdapter, Context context, OnItemClick listener) {
        this.postList = getDataAdapter;
        this.context = context;
        this.mCallback = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_card_layout, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final PostViewAdapter.ViewHolder Viewholder, final int position) {

        final Post dataAdapterOBJ = postList.get(position);

//        Viewholder.postUserName.setText(dataAdapterOBJ.getUsername());

        Viewholder.postDate.setText(dataAdapterOBJ.getPostdate());
        Viewholder.postDetail.setText(dataAdapterOBJ.getPostDesc());
        Viewholder.totalLikes.setText(dataAdapterOBJ.getTotal_likes() + " likes");
        Viewholder.totalComments.setText(dataAdapterOBJ.getTotal_comment() + " comments");

        Viewholder.postSeeMore.setOnClickListener(v -> {
            String postID = dataAdapterOBJ.getPostID();
            Intent intent = new Intent(context, ReviewActivity.class);
            intent.putExtra("postID", postID);

            ((Activity) context).startActivityForResult(intent, 101);
        });

        Viewholder.commentBtn.setOnClickListener(v -> {
            postID = dataAdapterOBJ.getPostID();
            mCallback.onClick("comment", postID);
            Log.d("Seeee ID", "postID" + postID);
        });

//        Viewholder.likeBtn.setPressed(dataAdapterOBJ.isLiked());

//        if (dataAdapterOBJ.isLiked()) {
//            Viewholder.likeBtn.setText(R.string.liked);
//            Viewholder.likeBtn.setBackgroundColor(Color.GRAY);
//
//        } else {
//            Viewholder.likeBtn.setText(R.string.like);
//
//        }

//        Viewholder.likeBtn.setOnClickListener(v -> {
//            dataAdapterOBJ.setLiked(Viewholder.likeBtn.isPressed());
//            //liked[position] = Viewholder.likeBtn.isPressed();
//            //temp[position] = liked[position];
//
//            if (dataAdapterOBJ.isLiked()) {
//                if (Viewholder.likeBtn.getText().toString().equals(context.getString(R.string.like))) {
//                    Viewholder.likeBtn.setText(R.string.liked);
//                    Viewholder.likeBtn.setBackgroundColor(Color.GRAY);
//                    //postID=dataAdapterOBJ.getPostID();
//                    //mCallback.onClick("like",postID);
//                    //Log.d("Seeee ID like","postID"+postID);
//
//                } else if (Viewholder.likeBtn.getText().toString().equals(context.getString(R.string.liked))) {
//                    Viewholder.likeBtn.setText(R.string.like);
//                }
//            }
//        });

        Viewholder.shareBtn.setOnClickListener(v -> shareDialog.show());

    }

    @Override
    public int getItemCount() {

        return postList != null ? postList.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView postUserName;
        TextView postDetail;
        TextView postDate;
        TextView postSeeMore;
        TextView totalLikes;
        TextView totalComments;
        ImageView postUserPic;
        ImageView postProductPic;
        Button likeBtn;
        Button commentBtn;
        Button shareBtn;

        ViewHolder(View itemView) {

            super(itemView);
            postUserName = itemView.findViewById(R.id.post_user_name);
            postDetail = itemView.findViewById(R.id.post_content);
            postDate = itemView.findViewById(R.id.post_date);
            postSeeMore = itemView.findViewById(R.id.post_see_more);
            postUserPic = itemView.findViewById(R.id.post_profile_pic);
            postProductPic = itemView.findViewById(R.id.post_pic);
            likeBtn = itemView.findViewById(R.id.like_button);
            commentBtn = itemView.findViewById(R.id.comment_button);
            shareBtn = itemView.findViewById(R.id.share_button);
            totalLikes = itemView.findViewById(R.id.totalLikes);
            totalComments = itemView.findViewById(R.id.totalComments);

        }
    }

    private String postID;
    private String username;

    public void addList(List<Post> dataAdapter) {
        postList.addAll(dataAdapter);
        notifyItemRangeChanged(0, postList.size());
    }

    public void refreshList() {
        notifyItemRangeChanged(0, postList.size());
    }

    public List<Post> getListData() {
        return postList;
    }

    public interface OnItemClick {
        void onClick(String value, String value2);
    }
}

