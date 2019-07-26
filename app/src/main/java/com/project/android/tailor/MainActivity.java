package com.project.android.tailor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationHelper, CreateAccountFragment.OnCreateAccountFragmentListener {

    private UserViewModel mUserViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserViewModel= ViewModelProviders.of(this).get(UserViewModel.class);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        LoginFragment loginFragment = new LoginFragment();
        fragmentTransaction
                .add(R.id.frame_main_activity, loginFragment, "login_fragment")
                // .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                //.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();


/*
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    Map<String,Object> user=new HashMap<>();
    user.put("fname","Paul");
    user.put("lname","P");
    user.put("password","H");

    db.collection("users")
            .add(user)
            .addOnSuccessListener(new OnSuccessListener<DocumentReference>(){
                public void onSuccess(DocumentReference documentReference){
                    Log.d("MyApp","DocumentSnapshot added with ID: "+documentReference.getId());
                }
            })
            .addOnFailureListener(new OnFailureListener(){
                public void onFailure(@NonNull Exception e){
                    Log.w("MyApp","Error adding document",e);
                }
            });
            */
    }

    public void navigateTo(Fragment fragment, boolean addToBackstack) {
        //LoginFragment loginFragment=(LoginFragment) getSupportFragmentManager().findFragmentByTag("login_fragment");

        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right,
                                R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .replace(R.id.frame_main_activity, fragment);

        if (addToBackstack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

    public void onCreateAccountButtonClicked(String username, String password,
                                             String email, String gender) {

        User user=new User(username,password,email,gender);
        mUserViewModel.insert(user);
    }
}
