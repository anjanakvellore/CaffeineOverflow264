package com.example.caffeineoverflow264.ui.calculator;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CalculatorViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CalculatorViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is caculator fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}