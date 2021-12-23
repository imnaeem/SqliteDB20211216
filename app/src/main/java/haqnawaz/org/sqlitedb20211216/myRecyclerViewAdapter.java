package haqnawaz.org.sqlitedb20211216;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class myRecyclerViewAdapter extends RecyclerView.Adapter<myRecyclerViewAdapter.MyViewHolder> {
    ArrayList<StudentModel> Students;

    public myRecyclerViewAdapter(ArrayList<StudentModel> students) {
        this.Students = students;

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewAge;
        TextView textViewStatus;
        StudentModel data;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.name);
            textViewAge = itemView.findViewById(R.id.age);
            textViewStatus = itemView.findViewById(R.id.status);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder

            (@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemview, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,
                                 int position) {
        holder.data=Students.get(position);
        holder.textViewName.setText(holder.data.getName());
        holder.textViewAge.setText(String.valueOf(holder.data.getAge()));
        if (holder.data.isActive())
            holder.textViewStatus.setText("Active");
        else
            holder.textViewStatus.setText("Inactive");
    }

    @Override
    public int getItemCount() {
        return Students.size();
    }
}
