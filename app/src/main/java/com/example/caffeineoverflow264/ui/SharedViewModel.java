package com.example.caffeineoverflow264.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.caffeineoverflow264.model.Result;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Result> selected = new MutableLiveData<Result>();

    public void select(Result result) {
        selected.setValue(result);
    }

    public LiveData<Result> getSelected() {
        return selected;
    }
}