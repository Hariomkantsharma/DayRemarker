package com.haridroid.dayremarker;

import static com.haridroid.dayremarker.MainActivity.year;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.text.TextWatcher;
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


    public RecyclerDayAdapter(Context context, ArrayList<itemStructure> arrItems, ArrayList<itemStructure> year) {
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

//        //onclick
//        holder.noteET.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                MyDBhelper dBhelper= MyDBhelper.getInstance(context);
//                SQLiteDatabase db= dBhelper.getWritableDatabase();
//                String note= holder.noteET.getText().toString();
//                String month= arrItems.get(position).month;
//                String day= arrItems.get(position).day;
//                dBhelper.update(month, day, note, dayOfWeek);
//                db.close();
//
//            }
//        });
//        //on change in string of edittext
        holder.noteET.addTextChangedListener(new TextWatcher() {
            int posi= holder.getAbsoluteAdapterPosition();
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String newNote = s.toString();
                        arrItems.get(posi).note= newNote;



                        int yearIndex= -1;

                        for(int i=0; i<year.size(); i++){
                            if(year.get(i).day.equals(arrItems.get(posi).day) && year.get(i).month.equals(arrItems.get(posi).month)){
                                year.get(i).note= newNote;
                                yearIndex= i;
                                break;

                            }
                        }

                        if(yearIndex!=-1) {
                            year.get(yearIndex).note = newNote;
                            arrItems.get(posi).note = newNote;
                            notifyItemChanged(posi);
                        }
                    }
                });



            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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
