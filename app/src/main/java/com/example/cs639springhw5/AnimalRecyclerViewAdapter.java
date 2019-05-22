package com.example.cs639springhw5;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AnimalRecyclerViewAdapter extends RecyclerView.Adapter {

    Context mContext;
    List<AnimalDisplay> mAnimalDisplays;
    private int mSelectedPosition = Adapter.NO_SELECTION;
    View.OnClickListener mClickListener ;


    public AnimalRecyclerViewAdapter(Context context, List<AnimalDisplay> animalDisplays, View.OnClickListener listener) {
        mContext = context;
        mAnimalDisplays = animalDisplays;
        mClickListener = listener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.list_item, null);
        RecyclerViewHolder holder = new RecyclerViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AnimalDisplay animalDisplay = mAnimalDisplays.get(position);
        RecyclerViewHolder viewHolder = (RecyclerViewHolder) holder;
        if(animalDisplay.getIcon() == 0){
            viewHolder.mImageView.setVisibility(View.GONE);
            viewHolder.mCheckBoxView.setVisibility(View.GONE);
        }else{

            viewHolder.mCheckBoxView.setChecked(animalDisplay.isChecked);
            viewHolder.mImageView.setVisibility(animalDisplay.isChecked? View.VISIBLE:View.GONE);
            viewHolder.mImageView.setImageResource(animalDisplay.getIcon());}

            viewHolder.itemView.setOnClickListener(mClickListener);
            viewHolder.itemView.setTag(position);

            viewHolder.mCheckBoxView.setOnClickListener(mClickListener);
            viewHolder.mCheckBoxView.setTag(position);

            viewHolder.mAnimalNameTextView.setText(animalDisplay.getName());
            viewHolder.mAnimalBioTextView.setText(animalDisplay.getBio());

    }

    @Override
    public int getItemCount() {
        return mAnimalDisplays.size();
    }

    public void setSelectedPosition(int position) {
        mSelectedPosition =  position ==  mSelectedPosition ? Adapter.NO_SELECTION : position;
        notifyDataSetChanged();
    }

    public AnimalDisplay getItem(int position) {
        return mAnimalDisplays.get(position);
    }


    private class RecyclerViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;
        TextView mAnimalNameTextView;
        TextView mAnimalBioTextView;
        CheckBox mCheckBoxView;

        RecyclerViewHolder(View view) {
            super(view);
            mImageView = view.findViewById(R.id.animal_icon);
            mAnimalNameTextView = view.findViewById(R.id.animal_name);
            mAnimalBioTextView = view.findViewById(R.id.animal_bio);
            mCheckBoxView = view.findViewById(R.id.checkbox);
        }
    }
}
