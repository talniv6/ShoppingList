package com.example.shoppinglist;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemsRepository {

    private MutableLiveData<List<Item>> items;

    public static ItemsRepository get(){
        MutableLiveData<List<Item>> items = new MutableLiveData<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("shopping-list");

        ItemsRepository itemsRepository = new ItemsRepository(items);

        collectionReference.get().addOnCompleteListener(task -> {
            List<Item> itemsList = new ArrayList<>();
            for (DocumentSnapshot document : task.getResult().getDocuments()) {
                String itemName = document.getString("item_name");
                long creationTime = document.contains("creation_time") ? document.getLong("creation_time") : System.currentTimeMillis();
                String id = document.getId();
                itemsList.add(new Item(id, itemName, creationTime));
            }
            Collections.sort(itemsList, (t1, t2) -> Long.compare(t1.getCreationTime(), t2.getCreationTime()));
            items.postValue(itemsList);
        });

//        collectionReference.addSnapshotListener((value, error) -> {
//            List<Item> itemsList = new ArrayList<>();
//            for (DocumentSnapshot document : value.getDocuments()) {
//                String itemName = document.getString("item_name");
//                long creationTime = document.contains("creation_time") ? document.getLong("creation_time") : System.currentTimeMillis();
//                String id = document.getId();
//                itemsList.add(new Item(id, itemName, creationTime));
//            }
//            Collections.sort(itemsList, (t1, t2) -> Long.compare(t1.getCreationTime(), t2.getCreationTime()));
//            items.postValue(itemsList);
//        });

        return itemsRepository;
    }

    private ItemsRepository(MutableLiveData<List<Item>> items) {
        this.items = items;
    }

    public MutableLiveData<List<Item>> getItems() {
        return items;
    }

    public Item add(String itemName){
        String itemId = String.valueOf(itemName.hashCode());
        long creationTime = System.currentTimeMillis();

        Map<String, Object> data = new HashMap<>();
        data.put("item_name", itemName);
        data.put("creation_time", creationTime);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("shopping-list")
                .document(itemId)
                .set(data);

        return new Item(itemId, itemName, creationTime);
    }

    public void delete(Item item){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("shopping-list").document(item.getId()).delete();
    }
}