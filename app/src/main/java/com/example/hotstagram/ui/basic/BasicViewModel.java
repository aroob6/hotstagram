package com.example.hotstagram.ui.basic;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BasicViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public BasicViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is basic fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}