package com.example.caffeineoverflow264.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.caffeineoverflow264.model.Result;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Result> selectedResult = new MutableLiveData<Result>();
    private final MutableLiveData<String> selectedEvent = new MutableLiveData<String>();
    private final MutableLiveData<Double> maxCaffinie = new MutableLiveData<Double>(-1.00);

    public void selectResult(Result result) {
        selectedResult.setValue(result);
    }
    public void selectEvent(String eventName) {
        selectedEvent.setValue(eventName);
    }
    public void setMaxCaffinie(double mg) {
        maxCaffinie.setValue(mg);
    }

    public LiveData<Result> getSelectedResult() {
        return selectedResult;
    }
    public LiveData<String> getSelectedEvent() {
        return selectedEvent;
    }
    public LiveData<Double> getMaxCaffinie() {
        return maxCaffinie;
    }
}