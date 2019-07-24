package com.project.android.tailor;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

public class UserViewModel extends AndroidViewModel {

    private UserRepository userRepository;

    public UserViewModel(Application application){
        super(application);
        userRepository=new UserRepository(application.getApplicationContext());
    }


    public void insert(User user){userRepository.insert(user);}
}
