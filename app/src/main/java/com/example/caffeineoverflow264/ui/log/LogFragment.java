package com.example.caffeineoverflow264.ui.log;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caffeineoverflow264.R;
import com.example.caffeineoverflow264.model.CalendarEvent;
import com.example.caffeineoverflow264.ui.SharedViewModel;
import com.example.caffeineoverflow264.ui.recipe.RecipeFragment;
import com.example.caffeineoverflow264.util.CalendarEventListAdapter;
import com.example.caffeineoverflow264.util.OnCalendarEventClickListener;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LogFragment extends Fragment {

    private String TAG = "DEBUG-CALENDAR";

    private Date currDateClicked = new Date();
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM yyyy", Locale.getDefault());

    private List<CalendarEvent> calendarEvents = new ArrayList<>();
    private CalendarEventListAdapter calendarEventAdapter;

    private LogViewModel logViewModel;
    private SharedViewModel sharedViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        logViewModel =
                ViewModelProviders.of(this).get(LogViewModel.class);
        View root = inflater.inflate(R.layout.fragment_log, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffe95451")));
        return root;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        sharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);

        // Generate calendar UI
        generateCalendar();

        // Handle recyclerView
        RecyclerView recyclerView = view.findViewById(R.id.rvCalendarEventList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        calendarEventAdapter = new CalendarEventListAdapter(calendarEvents, new OnCalendarEventClickListener() {
            @Override
            public void onCalendarEventClick(CalendarEvent calendarEvent) {
                Log.d(TAG, "Calendar event: " + calendarEvent.getEventName());
                // TODO FIXME
                // Show a dialog which will either directs us to the recipe search or amazon
                chooseActionDialog(calendarEvent);
            }
        });
        recyclerView.setAdapter(calendarEventAdapter);

        // Add event listener for the add event button
        Button btnAddEvent = view.findViewById(R.id.btnAddEvent);
        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Clicked date is: " + currDateClicked.toString());
                addEventDialog();
            }
        });
    }

    private void addEventToCalendar(String eventName, String eventCount) {
        final CompactCalendarView compactCalendarView = (CompactCalendarView) getView().findViewById(R.id.calendarView);
        // We can change color here
        Event event = new Event(Color.GREEN, currDateClicked.getTime(),
                new CalendarEvent(eventName, Integer.valueOf(eventCount)));
        compactCalendarView.addEvent(event);
    }

    private void updateEventListRecycler(List<Event> events) {
        // Handle recyclerView
        calendarEvents.clear();
        for (Event thisEvent : events) {
            calendarEvents.add((CalendarEvent) thisEvent.getData());
        }
        // Notify the recyclerViewAdapter about the data update
        calendarEventAdapter.notifyDataSetChanged();
    }

    private void generateCalendar() {
        CompactCalendarView compactCalendarView = (CompactCalendarView) getView().findViewById(R.id.calendarView);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                currDateClicked = dateClicked;
                List<Event> events = compactCalendarView.getEvents(dateClicked);
                Log.d(TAG, "Day was clicked: " + dateClicked + " with events " + events);
                // Update recycler view
                updateEventListRecycler(events);
            }
            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                Log.d(TAG, "Month was scrolled to: " + firstDayOfNewMonth);
                System.out.println("MIA       firstDayOfNewMonth : " + firstDayOfNewMonth);
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));
            }
        });
    }

    private void addEventDialog() {
        CompactCalendarView compactCalendarView = (CompactCalendarView) getView().findViewById(R.id.calendarView);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View eventDialogView = inflater.inflate(R.layout.dialog_add_event, null);
        EditText eventName = (EditText) eventDialogView.findViewById(R.id.diaglogEventName);
        EditText eventCount = (EditText) eventDialogView.findViewById(R.id.dialogEventCount);
        builder.setView(eventDialogView)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String eventNameStr = eventName.getText().toString();
                        String eventCountStr = eventCount.getText().toString();
                        Log.d(TAG, "Event Name: " + eventNameStr + ", Event Count: " + eventCountStr);
                        // Add event to calendar
                        addEventToCalendar(eventNameStr, eventCountStr);
                        // Update recycler view
                        updateEventListRecycler(compactCalendarView.getEvents(currDateClicked));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    private void changeIcon(){
        BottomNavigationView btnNavView = this.getView().getRootView().findViewById(R.id.nav_view) ;
        btnNavView.getMenu().findItem(R.id.navigation_recipe).setChecked(true);
    }

    private void chooseActionDialog(CalendarEvent calendarEvent) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View eventDialogView = inflater.inflate(R.layout.dialog_action_for_event, null);
        builder.setView(eventDialogView)
                .setPositiveButton("get recipes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Log.e(TAG, "calendarEvent " + calendarEvent.getEventName());
                        sharedViewModel.selectEvent(calendarEvent.getEventName());
                        RecipeFragment recipeFragment = new RecipeFragment();
                        getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                                recipeFragment).commit();
                        changeIcon();
                    }
                })
                .setNegativeButton("go to amazon", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .show();

    }

}