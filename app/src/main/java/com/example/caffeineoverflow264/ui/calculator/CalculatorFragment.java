package com.example.caffeineoverflow264.ui.calculator;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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

import static android.content.Context.INPUT_METHOD_SERVICE;

public class CalculatorFragment extends Fragment {

    private CalculatorViewModel calculatorViewModel;
    private SharedViewModel sharedViewModel;

    DatabaseHelper db;
    private ImageButton Calculate_button, Enter_button;
    private Button Remind_button;

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
        this.getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        sharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        db = new DatabaseHelper(getActivity().getApplicationContext());

        Enter_button = (ImageButton) getView().findViewById(R.id.Enter_button);
        Enter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText height_entry = (EditText) getView().findViewById(R.id.height_entry);
                String height_entry_answer = height_entry.getText().toString();

                EditText weight_entry = (EditText) getView().findViewById(R.id.weight_entry);
                String weight_entry_answer = weight_entry.getText().toString();

                EditText age_entry = (EditText) getView().findViewById(R.id.age_entry);
                String age_entry_answer = age_entry.getText().toString();

                if(!height_entry_answer.trim().isEmpty() && !weight_entry_answer.trim().isEmpty() && !age_entry_answer.trim().isEmpty()) {
                    db.insertUser(Double.parseDouble(height_entry_answer), Double.parseDouble
                            (weight_entry_answer), Integer.parseInt(age_entry_answer));

                    //Set maxCaffeine for Log
                    sharedViewModel.setMaxCaffinie(calculatorViewModel.maxCaffeine
                            (Integer.parseInt(age_entry_answer), Double.parseDouble
                                    (weight_entry_answer)));

                    // Show a toast to notify user data has been sent to database
                    Toast toast = Toast.makeText(getActivity(), "Saved!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });

        Calculate_button = (ImageButton) getView().findViewById(R.id.Calculate_button);
        Calculate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView result = (TextView) getView().findViewById(R.id.result);
                double caffeine = calculatorViewModel.calculateCaffeine();
                result.setText(Double.toString(caffeine));
            }
        });

        EditText eReminderTime = (EditText) getView().findViewById(R.id.eReminderTime) ;
        eReminderTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    // TODO: handle exception
                }
                // TimePicker
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                int setHour,setMinute;
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        eReminderTime.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true); //24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


        Remind_button = (Button) getView().findViewById(R.id.Remind_button);
        Remind_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eReminderTime_answer = eReminderTime.getText().toString();
                int setHour = Integer.parseInt(eReminderTime_answer.split(":")[0]);
                int setMinute = Integer.parseInt(eReminderTime_answer.split(":")[1]);
                System.out.println("H"+setHour+"M"+setMinute);
                Intent intent = new Intent(getContext(), ReminderBroadcast.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(),
                        0,intent,0);
                AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);

                // Set the alarm to start at approximately the mentioned time
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY,setHour);
                calendar.set(Calendar.MINUTE, setMinute);

                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        1000 * 60 * 20, pendingIntent);

                Toast toast = Toast.makeText(getActivity(), "Start!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.RIGHT, 100, 500);
                toast.show();
            }
        });
    }
}