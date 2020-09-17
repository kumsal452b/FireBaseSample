package com.example.firebasesample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> {
    Context context;
    ArrayList<ImagesItems> arrayList;

    public ItemAdapter(Context context, ArrayList<ImagesItems> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.images_items,parent,false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        ImagesItems currentItem=arrayList.get(position);
        String imageUrl=currentItem.ImagesURLgetter();
        String imageName=currentItem.ImageNameGetter();
        holder.textView.setText(imageName);
        Picasso.with(context).load(imageUrl).centerCrop().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public TextView textView;
        public TextView textView2;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image_view);
            textView=itemView.findViewById(R.id.text_view_name);
        }
    }
}
