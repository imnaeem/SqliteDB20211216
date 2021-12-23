package haqnawaz.org.sqlitedb20211216;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myRecyclerViewAdapter extends RecyclerView.Adapter<myRecyclerViewAdapter.MyViewHolder> {
    public MyAdapterListener onClickListener;
    ArrayList<StudentModel> Students;

    public myRecyclerViewAdapter(ArrayList<StudentModel> students) {
        this.Students = students;
    }

    public void setOnItemClickListener(MyAdapterListener listener)
    {
        this.onClickListener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewAge;
        TextView textViewStatus;
        Button deleteButton;
        Button updateButton;
        StudentModel data;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.name);
            textViewAge = itemView.findViewById(R.id.age);
            textViewStatus = itemView.findViewById(R.id.status);
            deleteButton = itemView.findViewById(R.id.deletebtn);
            updateButton = itemView.findViewById(R.id.updatebtn);

            //deleteButton.setOnClickListener(view -> onClickListener.delbtnOnClick(itemView, getAdapterPosition()));

            //updateButton.setOnClickListener(view -> onClickListener.updbtnOnClick(itemView, getAdapterPosition()));

        }

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemview, parent, false);
        return new MyViewHolder(itemView);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.data=Students.get(position);
        holder.textViewName.setText(holder.data.getName());
        holder.textViewAge.setText(String.valueOf(holder.data.getAge()));
        if (holder.data.isActive())
            holder.textViewStatus.setText("Active");
        else
            holder.textViewStatus.setText("Inactive");

        holder.deleteButton.setOnClickListener(view -> onClickListener.onRecyclerViewItemClicked(position, view.getId()));


    }

    @Override
    public int getItemCount() {
        return Students.size();
    }

    public void deleteRecord(int position)
    {
        Students.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,Students.size());
    }



}
