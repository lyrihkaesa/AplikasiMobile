package com.udinus.aplikasimobile.adapter;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udinus.aplikasimobile.databinding.ItemRowGuruBinding;
import com.udinus.aplikasimobile.repository.model.Guru;
import com.udinus.aplikasimobile.utils.AppUtils;

import java.util.ArrayList;

public class GuruRecycleViewAdapter extends RecyclerView.Adapter<GuruRecycleViewAdapter.GuruRecycleViewHolder> {
    private final ArrayList<Guru> guruArrayList;

    private GuruRecycleViewAdapter.OnItemClickCallback onItemClickCallback;

    public GuruRecycleViewAdapter(ArrayList<Guru> guruArrayList) {
        this.guruArrayList = guruArrayList;
    }

    public void setOnItemClickCallback(GuruRecycleViewAdapter.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public GuruRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRowGuruBinding binding = ItemRowGuruBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new GuruRecycleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GuruRecycleViewHolder holder, int position) {
        Guru guru = guruArrayList.get(position);

        holder.binding.textViewNama.setText(guru.getNama());
        holder.binding.textViewNip.setText(guru.getNip());
        holder.binding.textViewStatus.setText(guru.getStatus());
        holder.binding.textViewGaji.setText(AppUtils.convertGajiToText(guru.getGaji()));

        holder.itemView.setOnClickListener(view -> onItemClickCallback.onItemClicked(guru));
    }

    @Override
    public int getItemCount() {
        return guruArrayList.size();
    }

    public static class GuruRecycleViewHolder extends RecyclerView.ViewHolder {
        ItemRowGuruBinding binding;
        public GuruRecycleViewHolder(@NonNull ItemRowGuruBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(Guru data);
    }
}