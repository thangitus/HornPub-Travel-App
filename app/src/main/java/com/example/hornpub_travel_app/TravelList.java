package com.example.hornpub_travel_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class TravelList extends AppCompatActivity {

    private List<TravelListData> mTravelList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TravelListAdapter travelListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_list);
        prepareTravelListData();
        recyclerView = (RecyclerView) findViewById(R.id.myRecyclerView);
        travelListAdapter = new TravelListAdapter(this, mTravelList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(travelListAdapter);
    }

    private void prepareTravelListData() {
        TravelListData travelListData = new TravelListData("demo", "TP. Hồ Chí Minh - Vũng Tàu", "20/07/2020 - 25/07/2020", "2 người lớn", "3.000.000 VNĐ");
        mTravelList.add(travelListData);

        travelListData = new TravelListData("demo", "TP. Hồ Chí Minh - Vũng Tàu", "20/07/2020 - 25/07/2020", "2 người lớn", "3.000.000 VNĐ");
        mTravelList.add(travelListData);

        travelListData = new TravelListData("demo", "TP. Hồ Chí Minh - Vũng Tàu", "20/07/2020 - 25/07/2020", "2 người lớn", "3.000.000 VNĐ");
        mTravelList.add(travelListData);

        travelListData = new TravelListData("demo", "TP. Hồ Chí Minh - Vũng Tàu", "20/07/2020 - 25/07/2020", "2 người lớn", "3.000.000 VNĐ");
        mTravelList.add(travelListData);

        travelListData = new TravelListData("demo", "TP. Hồ Chí Minh - Vũng Tàu", "20/07/2020 - 25/07/2020", "2 người lớn", "3.000.000 VNĐ");
        mTravelList.add(travelListData);

        travelListData = new TravelListData("demo", "TP. Hồ Chí Minh - Vũng Tàu", "20/07/2020 - 25/07/2020", "2 người lớn", "3.000.000 VNĐ");
        mTravelList.add(travelListData);
    }
}
