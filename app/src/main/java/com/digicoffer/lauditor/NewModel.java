package com.digicoffer.lauditor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewModel extends ViewModel {
    private final MutableLiveData<String> selectItem = new MutableLiveData<>();
    public void setData(String item){
        selectItem.setValue(item);
    }

    public LiveData<String> getselectedItem(){
        return selectItem;
    }
}
