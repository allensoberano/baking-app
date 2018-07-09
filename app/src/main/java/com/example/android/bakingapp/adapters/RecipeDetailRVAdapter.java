package com.example.android.bakingapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;

import java.util.List;

public class RecipeDetailRVAdapter extends RecyclerView.Adapter<RecipeDetailRVAdapter.ViewHolder> {

    private Recipe mRecipe;
    private ItemClickListener mClickListener;
    private List<Ingredient> mIngredients;

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

        TextView stepDescription;
        TextView desertServings;

        ViewHolder(View itemView) {
            super(itemView);


            stepDescription = itemView.findViewById(R.id.tv_step_title);
            //dessertName = itemView.findViewById(R.id.tv_recipe_title);
            //desertServings = itemView.findViewById(R.id.tv_recipe_servings);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            mClickListener.onItemClick(getAdapterPosition());

        }
    }

    private String buildString(List<Ingredient> ingredients){

        //Builds a string of integredients with line breaks
        StringBuilder builder = new StringBuilder();
        for (Ingredient ingredient : ingredients) {
            builder.append(ingredient.getQuantity() + " "+ ingredient.getMeasure() + " " + ingredient.getIngredient() + "\n");
        }

        return builder.toString();
    }

//    private void setTitleActionBar() {
//        ((MainActivity) getActivity())
//                .setActionBarTitle(mRecipeSent.getName());
//    }

}
