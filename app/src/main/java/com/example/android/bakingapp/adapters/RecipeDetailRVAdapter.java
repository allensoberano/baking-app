package com.example.android.bakingapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Recipe;

public class RecipeDetailRVAdapter extends RecyclerView.Adapter<RecipeDetailRVAdapter.ViewHolder> {

    private final Recipe mRecipe;
    private final ItemClickListener mClickListener;

    // data is passed into the constructor
    public RecipeDetailRVAdapter(Recipe data, ItemClickListener listener) {
        this.mRecipe = data;
        this.mClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_detail_list_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.stepDescription.setText(mRecipe.getSteps().get(position).getShortDescription());

//        if (mRecipe != null) {
//            Picasso.get()
//                    .load(imageURLS.get(position))
//                    .fit()
//                    .placeholder(R.drawable.ic_cake_black_24dp)
//                    .error(R.drawable.ic_cake_black_24dp)
//                    .into(holder.desertImage);
//        }
//
//        holder.dessertName.setText(mRecipe.getName());

    }

    @Override
    public int getItemCount() {

        if (mRecipe.getSteps() != null) {
            return mRecipe.getSteps().size(); //+1 for ingredients list
        } else {
            return 0;
        }
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }

    // stores and recycles views as they are scrolled off screen
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView stepDescription;

        ViewHolder(View itemView) {
            super(itemView);


            stepDescription = itemView.findViewById(R.id.tv_step_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            mClickListener.onItemClick(getAdapterPosition());

        }
    }





}
