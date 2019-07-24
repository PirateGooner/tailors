package com.project.android.tailor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

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
