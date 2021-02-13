package com.example.shoppinglist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppViewModel extends ViewModel {

    private final ItemsRepository itemsRepository;

    public AppViewModel() {
        itemsRepository = ItemsRepository.get();
    }

    public LiveData<List<Item>> getItems() {
        return itemsRepository.getItems();
    }

    public void deleteItem(Item item){
        itemsRepository.delete(item);
    }

    public Item addItem(String itemName, String user){
        return itemsRepository.add(itemName, user);
    }
}
