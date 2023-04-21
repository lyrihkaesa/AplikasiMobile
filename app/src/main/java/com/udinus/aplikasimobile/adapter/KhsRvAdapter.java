package com.udinus.aplikasimobile.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udinus.aplikasimobile.database.model.Khs;
import com.udinus.aplikasimobile.databinding.ItemRowKhsBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Class adapter untuk RecyclerView yang menampilkan daftar KHS.
 * Rv = RecycleView
 */
public class KhsRvAdapter extends RecyclerView.Adapter<KhsRvAdapter.KhsRvHolder> {
    private final ArrayList<Khs> khsArrayList;
    private OnItemClickCallback onItemClickCallback;

    /**
     * Konstruktor untuk membuat adapter dengan data KHS yang ditampilkan.
     *
     * @param khsArrayList ArrayList yang berisi data KHS yang akan ditampilkan.
     */
    public KhsRvAdapter(ArrayList<Khs> khsArrayList) {
        this.khsArrayList = khsArrayList;
    }

    /**
     * Method untuk mengatur callback ketika salah satu item pada RecyclerView diklik.
     *
     * @param onItemClickCallback Interface yang akan dipanggil ketika item pada RecyclerView diklik.
     */
    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    /**
     * Method yang dipanggil ketika RecyclerView memerlukan ViewHolder baru.
     *
     * @param parent   ViewGroup yang menampung ViewHolder baru.
     * @param viewType Tipe View pada ViewHolder.
     * @return Instance dari KhsRvHolder yang berisi view untuk setiap item pada RecyclerView.
     */
    @NonNull
    @Override
    public KhsRvHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRowKhsBinding binding = ItemRowKhsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new KhsRvHolder(binding);
    }

    /**
     * Method yang dipanggil ketika RecyclerView perlu menampilkan data pada posisi tertentu.
     *
     * @param holder   ViewHolder untuk item pada posisi tertentu pada RecyclerView.
     * @param position Posisi item pada RecyclerView.
     */
    @Override
    public void onBindViewHolder(@NonNull KhsRvHolder holder, int position) {
        Khs khs = khsArrayList.get(position);
        holder.binding.tvNo.setText(String.valueOf(position + 1));
        holder.binding.tvCode.setText(khs.getCodeMatkul());
        holder.binding.tvName.setText(khs.getNameMatkul());
        holder.binding.tvSks.setText(String.valueOf(khs.getSks()));
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        holder.binding.tvGrade.setText(String.valueOf(decimalFormat.format(khs.getGrade())));
        holder.binding.tvLetterGrade.setText(khs.getLetterGrade());

        holder.itemView.setOnClickListener(view -> onItemClickCallback.onItemClicked(khs));
    }

    /**
     * Method yang mengembalikan jumlah item pada RecyclerView.
     *
     * @return Jumlah item pada RecyclerView.
     */
    @Override
    public int getItemCount() {
        return khsArrayList.size();
    }

    /**
     * Class holder untuk setiap item pada RecyclerView yang menampilkan KHS.
     */
    public static class KhsRvHolder extends RecyclerView.ViewHolder {
        ItemRowKhsBinding binding;

        /**
         * Konstruktor kelas KhsRvHolder.
         *
         * @param binding Objek binding yang berisi view untuk setiap item pada RecyclerView.
         */
        public KhsRvHolder(@NonNull ItemRowKhsBinding binding) {
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
         * @param data Instance dari Khs yang diklik pada RecyclerView.
         */
        void onItemClicked(Khs data);
    }
}