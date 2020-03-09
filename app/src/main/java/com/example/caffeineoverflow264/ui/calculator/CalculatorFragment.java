package com.example.caffeineoverflow264.ui.calculator;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.caffeineoverflow264.MainActivity;
import com.example.caffeineoverflow264.R;
import com.example.caffeineoverflow264.repository.service.ReminderBroadcast;
import com.example.caffeineoverflow264.repository.service.api.DatabaseHelper;
import com.example.caffeineoverflow264.ui.SharedViewModel;

import java.util.Calendar;

public class CalculatorFragment extends Fragment {

    private CalculatorViewModel calculatorViewModel;
    private SharedViewModel sharedViewModel;

    DatabaseHelper db;
    private Button Enter_button, Calculate_button, Remind_button;
    //private Calculate calculate = new Calculate();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        System.out.println("MIA       Calculator Fragment -> onCreateView()");

        calculatorViewModel =
                ViewModelProviders.of(this).get(CalculatorViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calculator, container, false);
        return root;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        System.out.println("MIA       Calculator Fragment -> onViewCreated()");

        sharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        db = new DatabaseHelper(getActivity().getApplicationContext());

        Enter_button = (Button) getView().findViewById(R.id.Enter_button);
        Enter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText height_entry = (EditText) getView().findViewById(R.id.height_entry);
                String height_entry_answer = height_entry.getText().toString();

                EditText weight_entry = (EditText) getView().findViewById(R.id.weight_entry);
                String weight_entry_answer = weight_entry.getText().toString();

                EditText age_entry = (EditText) getView().findViewById(R.id.age_entry);
                String age_entry_answer = age_entry.getText().toString();

                db.insertUser(Double.parseDouble(height_entry_answer),Double.parseDouble
                        (weight_entry_answer),Integer.parseInt(age_entry_answer));
            }
        });

        Calculate_button = (Button) getView().findViewById(R.id.Calculate_button);
        Calculate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView result = (TextView) getView().findViewById(R.id.result);
                double cups = 0.0;//calculate.calculateCups();
                result.setText(Double.toString(cups));
            }
        });

        Remind_button = (Button) getView().findViewById(R.id.Remind_button);
        Remind_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText remind_entry = (EditText) getView().findViewById(R.id.remind_entry);
                Double remind_entry_answer = Double.parseDouble
                        (remind_entry.getText().toString());
                //
                Intent intent = new Intent(getContext(), ReminderBroadcast.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(),
                        0,intent,0);
                AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);

                /* long timeAtButtonClick = System.currentTimeMillis();
                long tenSeconds = 1000 * 10; //millis*/

                //  alarmManager.set(AlarmManager.RTC_WAKEUP,timeAtButtonClick+tenSeconds,pendingIntent);

                // Set the alarm to start at approximately the mentioned time
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY, 8);
                calendar.set(Calendar.MINUTE, 0);

                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        1000 * 60 * 20, pendingIntent);

            }
        });
    }
}