package com.example.caffeineoverflow264.ui.calculator;

import android.database.Cursor;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.caffeineoverflow264.model.User;
import com.example.caffeineoverflow264.repository.service.api.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CalculatorViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CalculatorViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is calculator fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public double maxCaffeine(int age, double weight){
        double maxCaffeine;
        if(age <= 15)
            maxCaffeine = weight * 2.5;
        else if (age < 50)
            maxCaffeine = weight * 5;
        else
            maxCaffeine = weight * 3;

        return maxCaffeine;
    }


    public LiveData<User> getUserDetails() {
        Cursor cursor = DatabaseHelper.getUserDetails();
        if(cursor.moveToLast()){
            double height = cursor.getDouble(1);
            double weight = cursor.getDouble(2);
            int age = cursor.getInt(3);

            return new MutableLiveData<User>(new User(height,weight,age));

        }
        return new MutableLiveData<User>(null);
    }

    //To calculate Caffeine left
    public double calculateCaffeine(){
        double caffeine, weight, maxCaffeine,caffeineIntake;
        int age;

        caffeineIntake = 0.0;

        //Calculate maximum Caffeine in mg per user;
        Cursor userCursor = DatabaseHelper.getUserDetails();
        if (!userCursor.moveToFirst()) {
            System.out.println("No user recorded");
            return 0.0;
        }
        userCursor.moveToLast();
        weight = userCursor.getDouble(2);
        age = userCursor.getInt(3);
        maxCaffeine = maxCaffeine(age,weight);
        Date currDateClicked = new Date();
        System.out.println(currDateClicked.toString());
        String dateString = new SimpleDateFormat("MM/dd/yyyy").format(currDateClicked);
        Cursor logCursor = DatabaseHelper.getLogDetailsOnOneDay(dateString);
        if (!logCursor.moveToFirst()) {
            System.out.println("No intake recorded");
            return maxCaffeine;
        }

        do {
            System.out.println("Intake recorded");
            int coffeeId = logCursor.getInt(2);
            // Get caffeine amount per oz for this coffeeId
            int caffineAmount = DatabaseHelper.getCaffeineAmount(coffeeId);
            int oz = logCursor.getInt(3);
            caffeineIntake += oz * caffineAmount;
        } while (logCursor.moveToNext());

        caffeine = maxCaffeine - caffeineIntake;

        return caffeine;
    }
}