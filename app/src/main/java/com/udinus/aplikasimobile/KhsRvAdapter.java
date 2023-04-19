package com.udinus.aplikasimobile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_khs, parent, false);
        return new KhsRvHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KhsRvHolder holder, int position) {
        Khs khs = khsArrayList.get(position);
        holder.tvNo.setText(String.valueOf(position + 1));
        holder.tvCode.setText(khs.getCodeMatkul());
        holder.tvName.setText(khs.getNameMatkul());
        holder.tvSks.setText(String.valueOf(khs.getSks()));
        holder.tvGrade.setText(String.valueOf(khs.getGrade()));
        holder.tvLetterGrade.setText(khs.getLetterGrade());

        holder.itemView.setOnClickListener(view -> onItemClickCallback.onItemClicked(khs));
    }

    @Override
    public int getItemCount() {
        return khsArrayList.size();
    }

    // Class Holder untuk List Khs RecyclerView
    public static class KhsRvHolder extends RecyclerView.ViewHolder {

        TextView tvNo, tvCode, tvName, tvSks, tvGrade, tvLetterGrade;

        public KhsRvHolder(@NonNull View itemView) {
            super(itemView);
            tvNo = itemView.findViewById(R.id.tv_no);
            tvCode = itemView.findViewById(R.id.tv_code);
            tvName = itemView.findViewById(R.id.tv_name);
            tvSks = itemView.findViewById(R.id.tv_sks);
            tvGrade = itemView.findViewById(R.id.tv_grade);
            tvLetterGrade = itemView.findViewById(R.id.tv_letter_grade);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(Khs data);
    }
}