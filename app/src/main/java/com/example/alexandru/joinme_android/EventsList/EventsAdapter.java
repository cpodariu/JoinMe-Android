package com.example.alexandru.joinme_android.EventsList;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alexandru.joinme_android.R;
import com.example.alexandru.joinme_android.ShowEventFragment;
import com.example.alexandru.joinme_android.domain.Event;
import com.example.alexandru.joinme_android.domain.response.EventResponse;

/**
 * Created by cpodariu on 11/18/17.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";

    private EventResponse mDataSet;
    public FragmentManager manager;

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        View v;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            this.v = v;
            textView = (TextView) v.findViewById(R.id.textView);
        }

        public void setListener(View.OnClickListener l)
        {
            v.setOnClickListener(l);
        }

        public TextView getTextView() {
            return textView;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     */
    public EventsAdapter(EventResponse dataSet, FragmentManager manager) {
        mDataSet = dataSet;
        this.manager = manager;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.event_row_item, viewGroup, false);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        viewHolder.getTextView().setText(mDataSet.getEvents().get(position).getName());
        viewHolder.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Event e= mDataSet.getEvents().get(position);

                FragmentTransaction ft = manager.beginTransaction();
                ShowEventFragment eventFragment=new ShowEventFragment(e);
                ft.replace(R.id.frag_container_id,eventFragment, "NewFragmentTag");
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (mDataSet == null)
            return 0;
        return mDataSet.getEvents().size();
    }

    public void setData(EventResponse e)
    {
        this.mDataSet = e;
        this.notifyDataSetChanged();
    }
}