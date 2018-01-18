package me.duhblea.focusablerecyclerview.app;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.duhblea.focusablerecyclervivew.R;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private List<ECEFPoint> points;
    private IRecyclerViewClickListener listener;

     RecyclerAdapter(List<ECEFPoint> points, IRecyclerViewClickListener listener)
    {
        this.points = points;
        this.listener = listener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false);

        return new MyViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ECEFPoint point = points.get(position);

        holder.name.setText(point.getName());



    }

    @Override
    public int getItemCount() {
        return points.size();
    }


     public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name;
        private IRecyclerViewClickListener listener;
         MyViewHolder(View itemView, IRecyclerViewClickListener clickListener) {
            super(itemView);
            listener = clickListener;
            itemView.setOnClickListener(this);
            name = itemView.findViewById(R.id.label);
        }

         @Override
         public void onClick(View view) {
            listener.onClick(getAdapterPosition());
         }
     }
}
