package com.digicoffer.lauditor;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.digicoffer.lauditor.Groups.GroupModels.ViewGroupModel;
import com.digicoffer.lauditor.Webservice.AsyncTaskCompleteListener;
import com.digicoffer.lauditor.Webservice.HttpResultDo;

import java.util.ArrayList;

public class NewModel extends ViewModel implements AsyncTaskCompleteListener {
    private final MutableLiveData<String> selectItem = new MutableLiveData<>();
    public void setData(String item){
        selectItem.setValue(item);
    }
//    public ArrayList<ViewGroupModel> getlis
    public LiveData<String> getselectedItem(){
        return selectItem;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onAsyncTaskComplete(HttpResultDo httpResult) {

    }
}
