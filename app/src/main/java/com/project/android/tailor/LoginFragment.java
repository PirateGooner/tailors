package com.project.android.tailor;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    public LoginFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.login_fragment,container,false);
        MaterialButton createAccountButton=view.findViewById(R.id.button_loginPage_createAccount);
        createAccountButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                ((NavigationHelper) getActivity()).navigateTo(new CreateAccountFragment(),true);
            }
        });

        return view;
    }

}
