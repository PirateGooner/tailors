package com.project.android.tailor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class DesignsRecyclerAdapter extends RecyclerView.Adapter<DesignsRecyclerAdapter.DesignCardsViewHolder>{

    class DesignCardsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public MaterialCardView cardView;
        public ImageView designImageView;
        public ImageView likesImageView;
        public TextView usernameTxtView;
        public TextView descriptionTxtView;
        public TextView likesTxtView;
        public DesignsRecyclerAdapter mAdapter;

        public DesignCardsViewHolder(View itemView, DesignsRecyclerAdapter adapter){
            super(itemView);
            cardView=(MaterialCardView) itemView;
            designImageView=itemView.findViewById(R.id.imageView_cardDesigns);
            likesImageView=itemView.findViewById(R.id.imageViewLikes_cardDesigns);
            usernameTxtView=itemView.findViewById(R.id.txtViewUsername_cardDesigns);
            descriptionTxtView=itemView.findViewById(R.id.txtViewDescription_cardDesigns);
            likesTxtView=itemView.findViewById(R.id.txtViewLikes_cardDesigns);
            this.mAdapter=adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int mPosition=getLayoutPosition();
            if(!(dataList.isEmpty())){
                designsViewModel.saveLike(dataList.get(mPosition));
            }
        }
    }

    private LayoutInflater mInflater;
    public ArrayList<DesignsModel> dataList;
    private Context context;
    private DesignsViewModel designsViewModel;

    public DesignsRecyclerAdapter(Context context, ArrayList<DesignsModel> dataList,DesignsViewModel designsViewModel){
        mInflater=LayoutInflater.from(context);
        this.context=context;
        this.dataList=new ArrayList<DesignsModel>();
        this.designsViewModel=designsViewModel;
    }

    public DesignCardsViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView=mInflater.inflate(R.layout.card_view_designs,parent,false);
        return new DesignCardsViewHolder(itemView,this);
    }

    public void onBindViewHolder(DesignCardsViewHolder holder,int position){
        if(!(dataList.isEmpty())){
            DesignsModel design= dataList.get(position);

            StorageReference reference=designsViewModel.getReference(design.getUri());
            Glide.with(context)
                    .load(reference)
                    .into(holder.designImageView);

            if(designsViewModel.hasUserLikedDesign(design.getUsersWhoLiked())) {
                holder.likesImageView.setImageResource(R.drawable.ic_thumbs_up_green);
            }else{
                holder.likesImageView.setImageResource(R.drawable.ic_thumbs_up_holo_light);
            }

            holder.usernameTxtView.setText(design.getUploader());
            holder.descriptionTxtView.setText(design.getDescription());
            holder.likesTxtView.setText(Integer.toString(design.getNumberOfLikes()));
            /*
            StorageReference reference= FirebaseStorage.getInstance().getReference().child("users/paul@gmail.com/Shirt1.jpg");

            Glide.with(context)
                    .load(reference)
                    .into(holder.designImageView);

            holder.likesImageView.setImageResource(R.drawable.ic_thumbs_up_holo_light);
            holder.usernameTxtView.setText("Paul");
            holder.descriptionTxtView.setText("This is a shirt");
            holder.likesTxtView.setText(Integer.toString(10));
             */
        }
    }

    public int getItemCount(){
        if(!(dataList.isEmpty())){
            return dataList.size();
        }else{
            return 0;
        }
    }
}
