package com.example.diary_bae.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diary_bae.R;
import com.example.diary_bae.pojo.EntryDetails;

import java.util.ArrayList;

public class EntriesAdapter extends RecyclerView.Adapter<EntriesAdapter.EntryDetailsViewHolder> implements Filterable {
    LayoutInflater mInflater;
    Activity mActivity;
    ArrayList<EntryDetails> mEntryDetails;
    ArrayList<EntryDetails> mEntryDetailsFull;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public EntriesAdapter(Context context, Activity activity, ArrayList<EntryDetails> entryDetails) {
        mInflater = LayoutInflater.from(context);
        mActivity = activity;
        mEntryDetails = entryDetails;
        mEntryDetailsFull = new ArrayList<>(entryDetails);
    }

    @NonNull
    @Override
    public EntryDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.entry_item_list, parent, false);
        return new EntryDetailsViewHolder(mItemView,mActivity);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryDetailsViewHolder holder, int position) {
        holder.entryTitle.setText(mEntryDetails.get(position).getEntryTitle());
        holder.entryDate.setText(mEntryDetails.get(position).getEntryDate());
        holder.entryTime.setText(mEntryDetails.get(position).getEntryTime());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getLayoutPosition());
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mEntryDetails.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                ArrayList<EntryDetails> filteredList = new ArrayList<>();
                if (charSequence == null || charSequence.length()==0) {
                    filteredList.addAll(mEntryDetailsFull);
                } else {
                    String filterPattern = charSequence.toString().toLowerCase().trim();
                    for (EntryDetails row : mEntryDetailsFull) {
                        Log.e("tag",row.getEntryTitle());
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getEntryTitle().toLowerCase().contains(filterPattern.toLowerCase()) ||
                                row.getEntryDate().toLowerCase().startsWith(filterPattern.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mEntryDetails.clear();
                mEntryDetails.addAll((ArrayList)filterResults.values);
                notifyDataSetChanged();
            }
        };
    }


    public static class EntryDetailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {
        TextView entryTitle;
        TextView entryDate;
        TextView entryTime;
        Activity mActivity;
        public EntryDetailsViewHolder(@NonNull View itemView, Activity activity) {
            super(itemView);
            mActivity = activity;
            entryTitle = itemView.findViewById(R.id.tvEntryTitle);
            entryDate = itemView.findViewById(R.id.tvDate);
            entryTime = itemView.findViewById(R.id.tvTime);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {//TODO context menu

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuInflater inflater = mActivity.getMenuInflater();
            inflater.inflate(R.menu.entry_item_context_menu, menu);
        }
    }
}