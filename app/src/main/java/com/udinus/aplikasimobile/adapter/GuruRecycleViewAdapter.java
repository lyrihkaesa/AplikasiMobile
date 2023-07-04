package com.udinus.aplikasimobile.adapter;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udinus.aplikasimobile.databinding.ItemRowTeacherBinding;
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
        ItemRowTeacherBinding binding = ItemRowTeacherBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new GuruRecycleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GuruRecycleViewHolder holder, int position) {
        // Mendapatkan teacher pada ArrayList<Teacher> sesuai dengan position/index-nya.
        Guru guru = guruArrayList.get(position);

        // Memasukan isi dari variabel teacher/Teacher ke dalam TextView
        // yang ada dalam holder item_row_teacher.xml
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

    // Class Holder untuk List Teacher RecyclerView
    public static class GuruRecycleViewHolder extends RecyclerView.ViewHolder {

        ItemRowTeacherBinding binding;

        public GuruRecycleViewHolder(@NonNull ItemRowTeacherBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    /**
     * Interface untuk callback ketika item pada RecyclerView diklik.
     */
    public interface OnItemClickCallback {
        /**
         * Method yang dipanggil ketika item pada RecyclerView diklik.
         *
         * @param data Instance dari Teacher yang diklik pada RecyclerView.
         */
        void onItemClicked(Guru data);
    }
}