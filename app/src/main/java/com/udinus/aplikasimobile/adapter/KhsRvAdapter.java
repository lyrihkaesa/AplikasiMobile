package com.udinus.aplikasimobile.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udinus.aplikasimobile.database.model.Khs;
import com.udinus.aplikasimobile.databinding.ItemRowKhsBinding;

import java.util.ArrayList;

public class KhsRvAdapter extends RecyclerView.Adapter<KhsRvAdapter.KhsRvHolder> {
    private final ArrayList<Khs> khsArrayList;
    private OnItemClickCallback onItemClickCallback;

    public KhsRvAdapter(ArrayList<Khs> khsArrayList) {
        this.khsArrayList = khsArrayList;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }


    @NonNull
    @Override
    public KhsRvHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRowKhsBinding binding = ItemRowKhsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new KhsRvHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull KhsRvHolder holder, int position) {
        Khs khs = khsArrayList.get(position);
        holder.binding.tvNo.setText(String.valueOf(position + 1));
        holder.binding.tvCode.setText(khs.getCodeMatkul());
        holder.binding.tvName.setText(khs.getNameMatkul());
        holder.binding.tvSks.setText(String.valueOf(khs.getSks()));
        holder.binding.tvGrade.setText(String.valueOf(khs.getGrade()));
        holder.binding.tvLetterGrade.setText(khs.getLetterGrade());

        holder.itemView.setOnClickListener(view -> onItemClickCallback.onItemClicked(khs));
    }

    @Override
    public int getItemCount() {
        return khsArrayList.size();
    }

    // Class Holder untuk List Khs RecyclerView
    public static class KhsRvHolder extends RecyclerView.ViewHolder {

        ItemRowKhsBinding binding;

        public KhsRvHolder(@NonNull ItemRowKhsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(Khs data);
    }
}