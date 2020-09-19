package com.example.to_doapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//Responsible for displaying the data for the model into a row in the recycle view
public class ItemAdapter extends  RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    public interface OnLongClickListener{
        void onItemLongClicked(int position);

    }

    List<String> items;
    OnLongClickListener longClickListener;



    public ItemAdapter(List<String> items,   OnLongClickListener longClickListener) {
        this.items = items;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    //Responsible for creating each view
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //use layout inflator to inflate a view
        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);


        //wrap it inside a view holder and return it
        return new ViewHolder(todoView);


    }
    //responsible for binding data to a particular view holder
    @Override
    //Takes data as a position and put into a view holder
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Grab the item at the position
        String item = items.get(position);



        //bind the item into the specified view holder
        holder.bind(item);

    }

    @Override
    //Number of items  available in the data
    //Tells recyler how many items are in the list.

    public int getItemCount() {
        return items.size();
    }

    //Container to provide easy access to views that represent each row in the list
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvItem;
        //OnLongClickListener longClickListener;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1);

        }
//update the view inside of the view holder, with the data of string item
        public void bind(String item) {
            tvItem.setText(item);
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //Notify the user about the position that was long pressed
                    longClickListener.onItemLongClicked(getAdapterPosition());
                    return true;
                }
            });

        }
    }

}
