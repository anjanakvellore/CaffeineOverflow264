package com.example.caffeineoverflow264.ui.log;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.caffeineoverflow264.repository.service.api.DatabaseHelper;

public class LogViewModel extends ViewModel {

    public LiveData<Integer> getCaffeineAmount(int coffeeId) {
        return new MutableLiveData<Integer>(DatabaseHelper.getCaffeineAmount(coffeeId));
    }

    public LiveData<Cursor> getLogDetailsOnOneDay(String dateClicked) {
        return new MutableLiveData<Cursor>(DatabaseHelper.getLogDetailsOnOneDay(dateClicked));
    }

    public LiveData<Cursor> getLogDetails() {
        return new MutableLiveData<>(DatabaseHelper.getLogDetails());
    }

    public LiveData<String> getCoffeeNameById(int coffeeID) {
        return new MutableLiveData<String>(DatabaseHelper.getCoffeeNameById(coffeeID));
    }

    public LiveData<Cursor> getCoffeeList() {
        return new MutableLiveData<Cursor>(DatabaseHelper.getCoffeeList());
    }

    public void insertCoffeeItem(String coffeeName, int caffeineAmount) {
        DatabaseHelper.insertCoffeeItem(coffeeName, caffeineAmount);
    }

    public LiveData<Integer> getCoffeeIdByName(String coffeeName) {
        return new MutableLiveData<Integer>(DatabaseHelper.getCoffeeIdByName(coffeeName));
    }

    public void insertIntoLog(String dateClicked, int coffeeId, int oz) {
        DatabaseHelper.insertIntoLog(dateClicked, coffeeId, oz);
    }


}