package com.project.android.tailor;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import javax.annotation.Nullable;

public class DesignsViewModel extends ViewModel {

    FirebaseFirestore db=FirebaseFirestore.getInstance();

    final MutableLiveData<QuerySnapshot> allDesignsSnapshot=new MutableLiveData<>();

    public DesignsViewModel(){

    }

    public void setData(){
        db.collection("designs")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if(e != null){
                            Log.w("MyActivity","allDesignsSnapshot listener error",e);
                            return;
                        }

                        allDesignsSnapshot.setValue(queryDocumentSnapshots);
                    }
                });
    }

    public StorageReference getReference(String path){
        return FirebaseStorage.getInstance().getReference().child(path);
    }
}
