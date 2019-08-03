package com.project.android.tailor;


import android.content.Context;
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
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateAccountFragment extends Fragment {

    public View createAccFragment;
    public OnCreateAccountFragmentListener mListener;
    public Spinner mSpinner;
    public UserViewModel userViewModel;

    interface OnCreateAccountFragmentListener {
        void onCreateAccountButtonClicked(String username, String password,
                                          String email, String gender);
    }


    public CreateAccountFragment() {
        // Required empty public constructor
    }

    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_account_fragment, container, false);

        MaterialButton createAccountButton = view.findViewById(R.id.btnCreateAcc_createAccPage);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onCreateAccountButtonClicked();
            }
        });

        createAccFragment = view;

        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSpinner = (Spinner) requireActivity().findViewById(R.id.spinnerRole_createAccount);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.userRoles_array, R.layout.spinner_textview);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setSelection(0, false);

        userViewModel=ViewModelProviders.of(requireActivity()).get(UserViewModel.class);

    }

    public void onCreateAccountButtonClicked() {
        String username = ((TextInputEditText) createAccFragment.findViewById(R.id.editTxtUsername_createAccount)).getText().toString();
        String password = ((TextInputEditText) createAccFragment.findViewById(R.id.editTxtPassword_createAccount)).getText().toString();
        String emailAddress = ((TextInputEditText) createAccFragment.findViewById(R.id.editTxtEmail_createAccount)).getText().toString();
        RadioButton male = createAccFragment.findViewById(R.id.radioBtnMale_createAccount);
        RadioButton female = createAccFragment.findViewById(R.id.radioBtnFemale_createAccount);

        String gender;
        if (male.isChecked()) {
            gender = "male";
        } else {
            gender = "female";
        }

        String spinnerSelection;
        TextView spinnerTextView = (TextView) mSpinner.getSelectedView();
        spinnerSelection = spinnerTextView.getText().toString();

        LoginViewModel loginViewModel = ViewModelProviders.of(requireActivity()).get(LoginViewModel.class);
        final NavController navController = Navigation.findNavController(createAccFragment);

        loginViewModel.createAccount(emailAddress,password,username,gender,spinnerSelection);

        loginViewModel.accountCreationState.observe(getViewLifecycleOwner(),new Observer<LoginViewModel.AccountCreationState> (){
            public void onChanged(LoginViewModel.AccountCreationState accountCreationState){
                switch(accountCreationState){
                    case CREATED:
                        userViewModel.setUser();
                        navController.navigate(R.id.action_create_account_fragment_to_designs_fragment);
                        break;
                    case CREATION_FAILURE:
                        Toast.makeText(requireActivity(),"Account creation failed",Toast.LENGTH_LONG);
                }
            }
        });
    }

}
