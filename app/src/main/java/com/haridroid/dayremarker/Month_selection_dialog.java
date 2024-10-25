package com.haridroid.dayremarker;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

public class Month_selection_dialog extends Dialog {

    public Month_selection_dialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.month_selection_window);
        setCancelable(false);

        Button janBtn= findViewById(R.id.janBtn);
        Button febBtn= findViewById(R.id.febBtn);
        Button marBtn= findViewById(R.id.marchBtn);
        Button aprBtn= findViewById(R.id.aprilBtn);
        Button mayBtn= findViewById(R.id.mayBtn);
        Button junBtn= findViewById(R.id.juneBtn);
        Button julBtn= findViewById(R.id.julyBtn);
        Button augBtn= findViewById(R.id.augBtn);
        Button sepBtn= findViewById(R.id.septBtn);
        Button octBtn= findViewById(R.id.octBtn);
        Button novBtn= findViewById(R.id.novBtn);
        Button decBtn= findViewById(R.id.decBtn);

        janBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.setMonth(0);
                dismiss();
            }
        });

        febBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.setMonth(1);
                dismiss();
            }
        });
        marBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.setMonth(2);
                dismiss();
            }
        });
        aprBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.setMonth(3);
                dismiss();
            }
        });
        mayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.setMonth(4);
                dismiss();
            }
        });

        junBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.setMonth(5);
                dismiss();
            }
        });

        julBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.setMonth(6);
                dismiss();
            }
        });


        augBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.setMonth(7);
                dismiss();
            }
        });

        sepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.setMonth(8);
                dismiss();
            }
        });


        octBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.setMonth(9);
                dismiss();
            }
        });

        novBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.setMonth(10);
                dismiss();
            }
        });

        decBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.setMonth(11);
                dismiss();
            }
        });




    }
}
