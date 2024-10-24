package com.haridroid.dayremarker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class RecyclerDayAdapter extends RecyclerView.Adapter<RecyclerDayAdapter.ViewHolder> {
    Context context;
    ArrayList<itemStructure> arrItems;


    public RecyclerDayAdapter(Context context, ArrayList<itemStructure> arrItems) {
        this.context = context;
        this.arrItems = arrItems;
    }

    @NonNull
    @Override
    public RecyclerDayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_row,parent,false);
        ViewHolder VH =new ViewHolder(v);
        return VH;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerDayAdapter.ViewHolder holder, int position) {
        String day = arrItems.get(position).day;
        String dayOfWeek = arrItems.get(position).dayOfWeek;
        String itemDay= day+"\n"+dayOfWeek;
        if(dayOfWeek.equals("Sun")){
            holder.dayBtn.setBackgroundResource(R.drawable.custom_btn_2);
        }
        else{
            holder.dayBtn.setBackgroundResource(R.drawable.custom_btn_1);
        }
        holder.dayBtn.setText(itemDay);
        holder.noteET.setText(arrItems.get(position).note);

        holder.noteET.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        }
    }

    @Override
    public int getItemCount() {
        return arrItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button dayBtn;
        EditText noteET;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dayBtn=itemView.findViewById(R.id.dayBtn);
            noteET=itemView.findViewById(R.id.noteET);
        }
    }
}
