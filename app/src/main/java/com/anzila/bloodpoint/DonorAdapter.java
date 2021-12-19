package com.anzila.bloodpoint;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anzila.bloodpoint.databinding.DonorItemBinding;

import java.util.List;

public class DonorAdapter extends RecyclerView.Adapter<DonorAdapter.DonorVH> {

    List<donordata> items;

    public DonorAdapter(List<donordata> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public DonorVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DonorVH(DonorItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DonorVH holder, int position) {
        donordata item = items.get(position);
        holder.binding.name.setText(item.getName());
        holder.binding.addr.setText(item.getAddress());
        holder.binding.grp.setText(item.getBlood_group());
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public void updateList(List<donordata> list){
        this.items = list;
        notifyDataSetChanged();
    }

    class DonorVH extends RecyclerView.ViewHolder{

        DonorItemBinding binding;
        public DonorVH(@NonNull DonorItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
