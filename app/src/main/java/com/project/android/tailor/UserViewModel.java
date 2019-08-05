package com.project.android.tailor;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.WriteBatch;

import javax.annotation.Nullable;

public class UserViewModel extends ViewModel {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    final MutableLiveData<UserModel> user = new MutableLiveData<>();
    ListenerRegistration userDocRegistration = null;

    Boolean userPasswordUpdated=false;
    boolean userRecordUpdated=false;

    public UserViewModel() {

    }


    public void setUser() {
        if (userDocRegistration != null) {
            userDocRegistration.remove();
        }

        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            String userEmail = firebaseUser.getEmail();
            if (userEmail != null) {
                final DocumentReference userDocRef = db.collection("users").document(userEmail);
                userDocRegistration = userDocRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("MyActivity", "user document listener failed", e);
                            return;
                        }

                        if (snapshot != null && snapshot.exists()) {
                            user.setValue(snapshot.toObject(UserModel.class));
                        } else {
                            Log.d("MyActivity", "from settUser, current user data null");
                        }
                    }
                });
            }else{
                Log.d("MyActivity","UserViewModel setUser, user not null but user email null");
            }
        } else {
            Log.d("MyActivity", "UserViewModel setUser, user not logged in");
        }
    }

    public boolean updateUser(final String username,final String password,final String gender,final String role) {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        userPasswordUpdated=false;

        if (firebaseUser != null) {
            final String firebaseUserEmail=firebaseUser.getEmail();
            if(firebaseUserEmail!=null){
                /*
                firebaseUser.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.d("MyActivity","User email updated");
                            userEmailUpdated=true;
                        }
                    }
                });

                 */

                firebaseUser.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.d("MyActivity","User password updated");
                            userPasswordUpdated=true;
                            updateUserRecord(firebaseUserEmail,username,gender,role,password);
                        }
                    }
                });

            }
        } else {
            Log.d("MyActivity", "UserViewModel updateUser, user not logged in");
        }

        if (userRecordUpdated) return true;
        else return false;
    }

    public void updateUserRecord(final String userEmail, final String username,final String gender,final String role,final String password){
        userRecordUpdated=false;

        final DocumentReference userDocRef=db.collection("users").document(userEmail);
        WriteBatch batch=db.batch();

        batch.update(userDocRef,"username",username);
        batch.update(userDocRef,"password",password);
        batch.update(userDocRef,"gender",gender);
        batch.update(userDocRef,"role",role);

        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("MyActivity","User records updated");
                userRecordUpdated=true;
            }
        });

        /*
        userDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                final UserModel user=documentSnapshot.toObject(UserModel.class);
               // Query designsQuery=db.collection("designs").whereEqualTo("uploader",user.getUsername());

                WriteBatch batch=db.batch();
                batch.update(userDocRef,"username",username);
                batch.update(userDocRef,"password",password);
                batch.update(userDocRef,"gender",gender);
                batch.update(userDocRef,"role",role);

                batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("MyActivity","User records updated");
                    }
                });

            }
        });
*/

    }
/*
    public void getUser(){
        FirebaseUser firebaseUser=mAuth.getCurrentUser();
        if(firebaseUser!=null){
            String userEmail=firebaseUser.getEmail();
        }else{
            Log.d("MyActivity","UserViewModel getUser user not logged in");
        }
    }

 */
}
