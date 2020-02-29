package com.example.caffeineoverflow264.ui.recipe;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RecipeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RecipeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is recipe search fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}