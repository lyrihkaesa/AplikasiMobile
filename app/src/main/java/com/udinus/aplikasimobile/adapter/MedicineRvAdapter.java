package com.udinus.aplikasimobile.adapter;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udinus.aplikasimobile.database.model.Medicine;
import com.udinus.aplikasimobile.databinding.ItemRowMedicineBinding;
import com.udinus.aplikasimobile.utils.AppUtils;

import java.util.ArrayList;

public class MedicineRvAdapter extends RecyclerView.Adapter<MedicineRvAdapter.MedicineRvHolder> {
    private final ArrayList<Medicine> medicineArrayList;

    private MedicineRvAdapter.OnItemClickCallback onItemClickCallback;

    public MedicineRvAdapter(ArrayList<Medicine> medicineArrayList) {
        this.medicineArrayList = medicineArrayList;
    }

    public void setOnItemClickCallback(MedicineRvAdapter.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public MedicineRvHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRowMedicineBinding binding = ItemRowMedicineBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MedicineRvAdapter.MedicineRvHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineRvHolder holder, int position) {
        // Mendapatkan medicine pada ArrayList<Medicine> sesuai dengan position/index-nya.
        Medicine medicine = medicineArrayList.get(position);

        // Memasukan isi dari variabel medicine/Medicine ke dalam TextView
        // yang ada dalam holder item_row_medicine.xml
        holder.binding.tvItemCode.setText(medicine.getCode());
        holder.binding.tvItemName.setText(medicine.getName());
        holder.binding.tvItemSatuan.setText(medicine.getSatuan());
        holder.binding.tvItemPrice.setText(AppUtils.convertPriceToRpText(medicine.getPrice()));
        holder.binding.tvItemExpired.setText(AppUtils.simpleDateFormat.format(medicine.getExpired()));

        holder.itemView.setOnClickListener(view -> onItemClickCallback.onItemClicked(medicine));

    }

    @Override
    public int getItemCount() {
        return medicineArrayList.size();
    }

    // Class Holder untuk List Medicine RecyclerView
    public static class MedicineRvHolder extends RecyclerView.ViewHolder {

        ItemRowMedicineBinding binding;

        public MedicineRvHolder(@NonNull ItemRowMedicineBinding binding) {
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
         * @param data Instance dari Medicine yang diklik pada RecyclerView.
         */
        void onItemClicked(Medicine data);
    }
}