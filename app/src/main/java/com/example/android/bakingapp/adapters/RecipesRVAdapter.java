package com.example.android.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecipesRVAdapter extends RecyclerView.Adapter<RecipesRVAdapter.ViewHolder> {

    private List<Recipe> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    // data is passed into the constructor
    public RecipesRVAdapter(List<Recipe> data, ItemClickListener listener) {
        this.mData = data;
        this.mClickListener = listener;
    }

    // inflates the cell layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_list_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ArrayList<String> imageURLS = new ArrayList<String>();
        imageURLS.add("https://c1.staticflickr.com/1/69/194933662_006fe7fae8.jpg");

        if (mData != null) {
            Picasso.get()
                    .load(imageURLS.get(0))
                    .fit()
                    .placeholder(R.drawable.ic_cake_black_24dp)
                    .error(R.drawable.ic_cake_black_24dp)
                    .into(holder.desertImage);

            holder.desertName.setText(mData.get(position).getName());
            holder.desertServings.setText(mData.get(position).getServings());
        }
    }

    // total number of cells
    @Override
    public int getItemCount() {
        if (mData == null) {
            return 4;
        } else {
            return mData.size();
        }
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }


    // stores and recycles views as they are scrolled off screen
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView desertImage;
        TextView desertName;
        TextView desertServings;

        ViewHolder(View itemView) {
            super(itemView);


            desertImage = itemView.findViewById(R.id.iv_recipe_image);
            desertName = itemView.findViewById(R.id.tv_recipe_title);
            desertServings = itemView.findViewById(R.id.tv_recipe_servings);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //if (mClickListener != null)
            mClickListener.onItemClick(getAdapterPosition());

        }
    }


}
