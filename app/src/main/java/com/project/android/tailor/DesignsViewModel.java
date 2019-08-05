package com.project.android.tailor;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class DesignsViewModel extends ViewModel {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    final MutableLiveData<QuerySnapshot> allDesignsSnapshot = new MutableLiveData<>();
    Query allDesignsQuery=db.collection("designs");
    ListenerRegistration allDesignsRegistration=null;

    final MutableLiveData<QuerySnapshot> likedDesignsSnapshot=new MutableLiveData<>();
    ListenerRegistration likedDesignsRegistration=null;

    public DesignsViewModel() {

    }

    public void setData() {
        if(allDesignsRegistration!=null){
            allDesignsRegistration.remove();
        }

        allDesignsRegistration =allDesignsQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("MyActivity", "allDesignsSnapshot listener error", e);
                    return;
                }
                //Log.d("MyActivity","setValue called");
                allDesignsSnapshot.setValue(queryDocumentSnapshots);
            }
            });

        /*
        db.collection("designs")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("MyActivity", "allDesignsSnapshot listener error", e);
                            return;
                        }
                        Log.d("MyActivity","setValue called");
                        allDesignsSnapshot.setValue(queryDocumentSnapshots);
                    }
                });

         */
    }

    public void setNull(){
        allDesignsSnapshot.setValue(null);
    }

    public StorageReference getReference(String path) {
        return FirebaseStorage.getInstance().getReference().child(path);
    }

    public void saveLike(DesignsModel designsModel) {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        ArrayList<String> usersWhoLiked = designsModel.getUsersWhoLiked();
        int designID = designsModel.getDesignID();
        int numberOfLikes = designsModel.getNumberOfLikes();
        numberOfLikes += 1;
        DocumentReference docRef = db.collection("designs").document(Integer.toString(designID));
        WriteBatch batch = db.batch();

        if (firebaseUser != null) {
            String userEmail = firebaseUser.getEmail();
            if (userEmail != null) {
                if (!(usersWhoLiked.contains(userEmail))) {
                    batch.update(docRef, "numberOfLikes", numberOfLikes);
                    batch.update(docRef, "usersWhoLiked", FieldValue.arrayUnion(userEmail));

                    batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        }
                    });
                }
            } else {
                Log.d("MyActivity", "At DesignsViewModel saveLike, firebase user not null but email not available");
            }

        }else{
            Log.d("MyActivity","At DesignsViewModel saveLike, firebase user not authenticated");
        }
    }

    public boolean hasUserLikedDesign(ArrayList<String> usersWhoLiked){
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if(firebaseUser!=null){
            String userEmail=firebaseUser.getEmail();
            if(userEmail !=null){
                if(usersWhoLiked.contains(userEmail)){
                    return true;
                }else{
                    return false;
                }
            }else{
                Log.d("MyActivity", "At DesignsViewModel hasUserLikedDesign, firebase user not null but email not available");
                return false;
            }
        }else{
            Log.d("MyActivity", "At DesignsViewModel hasUserLikedDesign, firebase user is null");
            return false;
        }
    }

    public void setLikedDesigns(){
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        Query likedDesignsQuery;

        if(firebaseUser!=null){
            String userEmail=firebaseUser.getEmail();
            if(userEmail!=null){
                likedDesignsQuery=db.collection("designs").whereArrayContains("usersWhoLiked",userEmail);

                if(likedDesignsRegistration!=null){
                    likedDesignsRegistration.remove();
                }

                likedDesignsRegistration=likedDesignsQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("MyActivity", "likedDesignsSnapshot listener error", e);
                            return;
                        }

                        //Log.d("MyActivity","setLikedDesignsSnapshot called");
                        likedDesignsSnapshot.setValue(queryDocumentSnapshots);
                    }
                });

            }else{
                Log.d("MyActivity","At DesignsViewModel setLikedDesigns, user not null but user email null");
            }
        }else{
            Log.d("MyActivity", "At DesignsViewModel setLikedDesigns, firebase user is null");
        }
    }

    public void setLikedDesignsSnapshotNull(){
        likedDesignsSnapshot.setValue(null);
    }


}
