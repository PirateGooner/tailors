package com.project.android.tailor;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateUserDetailsFragment extends Fragment {

    private TextInputEditText mUsername;
    private TextInputEditText mPassword;
    private TextInputEditText mEmail;
    private RadioButton mRadioButtonMale;
    private RadioButton mRadioButtonFemale;
    private Spinner mSpinner;
    private UserViewModel userViewModel;


    public UpdateUserDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_user_details, container, false);
    }

    public void onViewCreated(View view,Bundle savedInstanceState){
        userViewModel= ViewModelProviders.of(requireActivity()).get(UserViewModel.class);

        mUsername=view.findViewById(R.id.editTxtUsername_updateDetails);
        mPassword=view.findViewById(R.id.editTxtPassword_updateDetails);
        mRadioButtonFemale=view.findViewById(R.id.radioBtnFemale_updateDetails);
        mRadioButtonMale=view.findViewById(R.id.radioBtnMale_updateDetails);

        mSpinner=requireActivity().findViewById(R.id.spinnerRole_updateDetails);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.userRoles_array, R.layout.spinner_textview);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        //mSpinner.setSelection(0, false);

        userViewModel.user.observe(getViewLifecycleOwner(), new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                if(userModel!=null){
                    mUsername.setText(userModel.getUsername());
                    mPassword.setText(userModel.getPassword());

                    if(userModel.getGender()=="male"){
                        mRadioButtonMale.setChecked(true);
                        mRadioButtonFemale.setChecked(false);
                    }else{
                        mRadioButtonMale.setChecked(false);
                        mRadioButtonFemale.setChecked(true);
                    }

                    if(userModel.getRole()=="Customer"){
                        mSpinner.setSelection(0,false);
                    }else{
                        mSpinner.setSelection(1,false);
                    }
                }
            }
        });

        MaterialButton updateDetailsButton=view.findViewById(R.id.btnUpdateDetails_updateDetails);
        updateDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDetailsButtonClicked();
            }
        });
    }

    public void updateDetailsButtonClicked(){
        String username= mUsername.getText().toString();
        String password= mPassword.getText().toString();

        String gender;
        if(mRadioButtonMale.isChecked()){
            gender="male";
        }else{
            gender="female";
        }

        String spinnerSelection;
        TextView selectedSpinnerView=(TextView) mSpinner.getSelectedView();
        spinnerSelection=selectedSpinnerView.getText().toString();

        userViewModel.updateUser(username,password,gender,spinnerSelection);
    }

}
