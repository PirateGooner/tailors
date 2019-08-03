package com.project.android.tailor;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginViewModel extends ViewModel {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public enum AuthenticationState {
        UNAUTHENTICATED,
        AUTHENTICATED,
        INVALID_AUTHENTICATION
    }

    public enum AccountCreationState {
        CREATED,
        CREATION_FAILURE,
        NOT_CREATED
    }

    public enum LoginStatus {
        LOGIN_SUCCESS,
        LOGIN_FAILURE,
        WAITING_TO_LOGIN
    }

    final MutableLiveData<AccountCreationState> accountCreationState =
            new MutableLiveData<>();

    final MutableLiveData<AuthenticationState> authenticationState =
            new MutableLiveData<AuthenticationState>();

    final MutableLiveData<LoginStatus> loginStatus=
            new MutableLiveData<>();

    final String email = "";
    final String password = "";

    public LoginViewModel() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            authenticationState.setValue(AuthenticationState.AUTHENTICATED);
        } else {
            authenticationState.setValue(AuthenticationState.UNAUTHENTICATED);
            accountCreationState.setValue(AccountCreationState.NOT_CREATED);
        }
    }

    public void authenticate(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("MyActivity", "User logged in");
                            authenticationState.setValue(AuthenticationState.AUTHENTICATED);
                            loginStatus.setValue(LoginStatus.LOGIN_SUCCESS);
                        } else {
                            Log.w("MyActivity","log in failure",task.getException());
                            loginStatus.setValue(LoginStatus.LOGIN_FAILURE);
                        }
                    }
                });
    }

    public void refuseAuthentication() {
        authenticationState.setValue(AuthenticationState.UNAUTHENTICATED);
    }

    public void createAccount(final String email, final String password, final String username, final String gender, final String role) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("MyActivity", "Account creation successful");
                    UserModel user = new UserModel(username, password, email, gender, role);
                    createUserRecord(user);
                    // authenticationState.setValue(AuthenticationState.AUTHENTICATED);
                } else {
                    Log.w("MyActivity", "createUserWithEmail:failure", task.getException());
                    accountCreationState.setValue(AccountCreationState.CREATION_FAILURE);
                }
            }
        });
    }

    public void createUserRecord(UserModel user) {
        db.collection("users").document(user.getEmail())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("MyActivity", "User record creation successful");
                        accountCreationState.setValue(AccountCreationState.CREATED);
                        authenticationState.setValue(AuthenticationState.AUTHENTICATED);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("MyActivity", "Error creating user record", e);
                        accountCreationState.setValue(AccountCreationState.CREATION_FAILURE);
                    }
                });
    }

    public void signOut() {
        mAuth.signOut();
        authenticationState.setValue(AuthenticationState.UNAUTHENTICATED);
        loginStatus.setValue(LoginStatus.WAITING_TO_LOGIN);
        accountCreationState.setValue(AccountCreationState.NOT_CREATED);
    }

}
