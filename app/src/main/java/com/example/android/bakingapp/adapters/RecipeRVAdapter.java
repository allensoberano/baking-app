package com.example.android.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Recipe;

import java.util.List;

public class RecipeRVAdapter extends RecyclerView.Adapter<RecipeRVAdapter.ViewHolder> {

    private List<Recipe> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    // data is passed into the constructor
    public RecipeRVAdapter(List<Recipe> data, Context context) {
        this.mData = data;
        //this.mClickListener = listener;
        this.context = context;
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
        holder.myTextView.setText((CharSequence) mData.get(position));
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
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);

            myTextView = (TextView) itemView.findViewById(R.id.tv_recipe_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //if (mClickListener != null)
            mClickListener.onItemClick(getAdapterPosition());
        }
    }


}
