package com.example.caffeineoverflow264.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caffeineoverflow264.R;
import com.example.caffeineoverflow264.model.CalendarEvent;

import java.util.List;

public class CalendarEventListAdapter extends RecyclerView.Adapter<CalendarEventListAdapter.ViewHolder> {

    private List<CalendarEvent> calendarEvents;
    private OnCalendarEventClickListener listener;


    public CalendarEventListAdapter(List<CalendarEvent> calendarEvents, OnCalendarEventClickListener listener) {
        this.listener = listener;
        this.calendarEvents = calendarEvents;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView calendarEventName;
        TextView calendarEventCount;

        ViewHolder(View itemView) {
            super(itemView);
            calendarEventName = itemView.findViewById(R.id.tvCalendarEventName);
            calendarEventCount = itemView.findViewById(R.id.tvCalendarEventCount);
        }

        public void bind(final CalendarEvent calendarEvent, final OnCalendarEventClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    System.out.println("CalendarEvent is clicked " + calendarEvent.getEventName() );
                    listener.onCalendarEventClick(calendarEvent);
                }
            });
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_event_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CalendarEvent calendarEvent = calendarEvents.get(position);
        holder.calendarEventName.setText(calendarEvent.getEventName());
        holder.calendarEventCount.setText(Integer.toString(calendarEvent.getEventCount()));
        holder.bind(calendarEvent, listener);
    }

    @Override
    public int getItemCount() {
        if (calendarEvents != null) {
            return calendarEvents.size();
        } else {
            return 0;
        }
    }
}
