package com.task.stackview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.task.stackview.R;
import com.task.stackview.adapter.CardAdapter;
import com.task.stackview.view.CardStackView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LayoutInflater inflater = LayoutInflater.from(this);

        CardStackView sv = (CardStackView)findViewById(R.id.my_stack_view);
        sv.setAdapter(new CardAdapter(R.drawable.girl_0, R.drawable.girl_1, R.drawable.girl_2, R.drawable.girl_3));
        View emptyView = inflater.inflate(R.layout.empty_page, null);
        sv.setEmptyView(emptyView);
    }

}
