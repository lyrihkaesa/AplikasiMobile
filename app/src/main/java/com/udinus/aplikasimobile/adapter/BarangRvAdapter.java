package com.udinus.aplikasimobile.adapter;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udinus.aplikasimobile.databinding.ItemRowProductBinding;
import com.udinus.aplikasimobile.repository.model.Barang;

import java.util.ArrayList;

public class BarangRvAdapter extends RecyclerView.Adapter<BarangRvAdapter.BarangRvHolder> {
    private final ArrayList<Barang> barangArrayList;

    private BarangRvAdapter.OnItemClickCallback onItemClickCallback;

    public BarangRvAdapter(ArrayList<Barang> barangArrayList) {
        this.barangArrayList = barangArrayList;
    }

    public void setOnItemClickCallback(BarangRvAdapter.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public BarangRvHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRowProductBinding binding = ItemRowProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BarangRvAdapter.BarangRvHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BarangRvHolder holder, int position) {
        // Mendapatkan barang pada ArrayList<Barang> sesuai dengan position/index-nya.
        Barang barang = barangArrayList.get(position);

        // Memasukan isi dari variabel barang/Barang ke dalam TextView
        // yang ada dalam holder item_row_barang.xml
        holder.binding.tvItemKode.setText(barang.getCode());
        holder.binding.tvItemNama.setText(barang.getName());
        holder.binding.tvItemSatuan.setText(barang.getSatuan());
        holder.binding.tvItemHarga.setText(barang.getPriceRp());

        holder.itemView.setOnClickListener(view -> onItemClickCallback.onItemClicked(barang));

    }

    @Override
    public int getItemCount() {
        return barangArrayList.size();
    }

    // Class Holder untuk List Barang RecyclerView
    public static class BarangRvHolder extends RecyclerView.ViewHolder {

        ItemRowProductBinding binding;

        public BarangRvHolder(@NonNull ItemRowProductBinding binding) {
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
         * @param data Instance dari Barang yang diklik pada RecyclerView.
         */
        void onItemClicked(Barang data);
    }
}