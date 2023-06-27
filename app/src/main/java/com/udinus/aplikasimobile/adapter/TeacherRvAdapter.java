package com.udinus.aplikasimobile.adapter;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udinus.aplikasimobile.databinding.ItemRowTeacherBinding;
import com.udinus.aplikasimobile.repository.model.Teacher;

import java.util.ArrayList;

public class TeacherRvAdapter extends RecyclerView.Adapter<TeacherRvAdapter.TeacherRvHolder> {
    private final ArrayList<Teacher> teacherArrayList;

    private TeacherRvAdapter.OnItemClickCallback onItemClickCallback;

    public TeacherRvAdapter(ArrayList<Teacher> teacherArrayList) {
        this.teacherArrayList = teacherArrayList;
    }

    public void setOnItemClickCallback(TeacherRvAdapter.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public TeacherRvHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRowTeacherBinding binding = ItemRowTeacherBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TeacherRvAdapter.TeacherRvHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherRvHolder holder, int position) {
        // Mendapatkan teacher pada ArrayList<Teacher> sesuai dengan position/index-nya.
        Teacher teacher = teacherArrayList.get(position);

        // Memasukan isi dari variabel teacher/Teacher ke dalam TextView
        // yang ada dalam holder item_row_teacher.xml
        holder.binding.tvItemCode.setText(teacher.getEmployeeCode());
        holder.binding.tvItemName.setText(teacher.getEmployeeName());
        holder.binding.tvItemPosition.setText(teacher.getPosition());

        holder.itemView.setOnClickListener(view -> onItemClickCallback.onItemClicked(teacher));

    }

    @Override
    public int getItemCount() {
        return teacherArrayList.size();
    }

    // Class Holder untuk List Teacher RecyclerView
    public static class TeacherRvHolder extends RecyclerView.ViewHolder {

        ItemRowTeacherBinding binding;

        public TeacherRvHolder(@NonNull ItemRowTeacherBinding binding) {
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
        void onItemClicked(Teacher data);
    }
}