package me.duhblea.focusablerecyclerview.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import me.duhblea.focusablerecyclerview.view.FocusableRecyclerView;
import me.duhblea.focusablerecyclervivew.R;

public class MainActivity extends AppCompatActivity implements IRecyclerViewClickListener {

    private ArrayList<ECEFPoint> xyzPos = new ArrayList<>();

    private FocusableRecyclerView<ECEFPoint> focusableRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeVars();

        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(xyzPos, this);

        focusableRecyclerView = findViewById(R.id.focusableRecyclerView);
        ECEFDetailsProvider detailsProvider = new ECEFDetailsProvider(this);
        focusableRecyclerView.setDetailsProvider(detailsProvider);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        focusableRecyclerView.getRecyclerView().setLayoutManager(layoutManager);
        focusableRecyclerView.getRecyclerView().setItemAnimator(new DefaultItemAnimator());
        focusableRecyclerView.getRecyclerView().setAdapter(recyclerAdapter);
    }

    private void initializeVars()
    {
        xyzPos.add(new ECEFPoint("Number 1", 309.234, 9825.323, -2356.74643));
        xyzPos.add(new ECEFPoint("Number 2", 2394.2342, 9785.8684, 7645.4667));
        xyzPos.add(new ECEFPoint("Number 3", 986.32, -124.753, 4432.111));
        xyzPos.add(new ECEFPoint("Number 4", 234.1256, 87543.73, -86212.366));
        xyzPos.add(new ECEFPoint("Number 5", -963.44, 5321.42188, -5620.2356));
        xyzPos.add(new ECEFPoint("Number 6", -54321.346, 2542.4563, -42124.212));
        xyzPos.add(new ECEFPoint("Number 7", 214.56, 2.33333, 8.0975432));
        xyzPos.add(new ECEFPoint("Number 8", 8912.4121, 1.178949, -8712.4));
        xyzPos.add(new ECEFPoint("Number 9", 01912.3123, 98.88, -12482.1));
        xyzPos.add(new ECEFPoint("Number 10", 12.32, -87.33, -98.1));
    }

    @Override
    public void onClick(int pos) {

        if (pos > -1)
        {
            ECEFPoint point = xyzPos.get(pos);
            focusableRecyclerView.focusItem(point);

        }

    }
}
