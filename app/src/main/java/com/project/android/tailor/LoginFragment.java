package com.project.android.tailor;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment{

    private NavController navController;
    private LoginViewModel loginViewModel;
    private UserViewModel userViewModel;

    public LoginFragment() {
        // Required empty public constructor
    }




    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.login_fragment,container,false);
        //MaterialButton createAccountButton=view.findViewById(R.id.button_loginPage_createAccount);
        /*
        createAccountButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                ((NavigationHelper) getActivity()).navigateTo(new CreateAccountFragment(),true);
            }
        });
*/
        return view;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState){
        navController= Navigation.findNavController(view);
        loginViewModel= ViewModelProviders.of(requireActivity()).get(LoginViewModel.class);
        userViewModel=ViewModelProviders.of(requireActivity()).get(UserViewModel.class);

        final TextView userEmail_Header=(TextView) requireActivity().findViewById(R.id.txtViewUsername_drawerHeader);
        final TextView userPassword_Header=(TextView) requireActivity().findViewById(R.id.txtViewEmail_drawerHeader);

        MaterialButton createAccButton=view.findViewById(R.id.btnCreateAcc_login);
        createAccButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                navController.navigate(R.id.login_fragment_to_create_account_fragment);
            }
        });

        final TextInputEditText email=view.findViewById(R.id.editTxtEmail_login);
        final TextInputEditText password=view.findViewById(R.id.editTxtPassword_login);

        MaterialButton loginButton=view.findViewById(R.id.btnLogin_login);
        loginButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                loginViewModel.authenticate(email.getText().toString(),password.getText().toString());
            }
        });

        loginViewModel.loginStatus.observe(getViewLifecycleOwner(), new Observer<LoginViewModel.LoginStatus> (){
            public void onChanged(LoginViewModel.LoginStatus loginStatus){
                switch(loginStatus){
                    case LOGIN_SUCCESS:
                        /*
                        userViewModel.getUser().observe(getViewLifecycleOwner(), new Observer<UserModel>() {
                            @Override
                            public void onChanged(UserModel userModel) {
                                if(userModel.getEmail()!=null){
                                    Log.d("MyActivity",userModel.getEmail());
                                }else{
                                    Log.d("MyActivity","In onResume userModel null?");
                                }
                            }
                        });
                         */
                        userViewModel.setUser();
                        navController.navigate(R.id.login_fragment_to_designs_fragment);
                        break;
                    case LOGIN_FAILURE:
                        Toast.makeText(requireActivity(),"Login Failure",Toast.LENGTH_LONG).show();
                }
            }
        });


    }


/*
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnCreateAcc_login:
                break;
        }
    }

    public void navigateTo(){
        navController.navigate(R.id.login_fragment_to_create_account_fragment);
    }
    */
}
