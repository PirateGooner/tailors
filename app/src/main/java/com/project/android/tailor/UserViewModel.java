package com.project.android.tailor;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class UserViewModel extends ViewModel {
    FirebaseAuth mAuth= FirebaseAuth.getInstance();
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    private DocumentReference userDocRef;

    final MutableLiveData<UserModel> user= new MutableLiveData<>();

   public UserViewModel(){

    }


    public void setUser(){
        FirebaseUser firebaseUser=mAuth.getCurrentUser();
        if(firebaseUser!=null){
            String userEmail=firebaseUser.getEmail();
            final DocumentReference userDocRef=db.collection("users").document(userEmail);
            userDocRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                    if(e != null){
                        Log.w("MyActivity","user document listener failed",e);
                        return;
                    }

                    if (snapshot!=null && snapshot.exists()){
                        user.setValue(snapshot.toObject(UserModel.class));
                    }else{
                        Log.d("MyActivity","from settUser, current user data null");
                    }
                }
            });
        }else{
            Log.d("MyActivity","UserViewModel getdata, user not logged in");
        }
    }
}
