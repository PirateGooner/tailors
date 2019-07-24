package com.project.android.tailor;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateAccountFragment extends Fragment {

    public View createAccFragment;
    public OnCreateAccountFragmentListener mListener;

    interface OnCreateAccountFragmentListener {
        void onCreateAccountButtonClicked(String username,String password,
                String email,String gender);
    }


    public CreateAccountFragment() {
        // Required empty public constructor
    }

    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof OnCreateAccountFragmentListener){
            mListener=(OnCreateAccountFragmentListener) context;
        }else{
            throw new ClassCastException(context.toString());
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.create_account_fragment,container,false);

        MaterialButton createAccountButton=view.findViewById(R.id.btnCreateAcc_createAccPage);
        createAccountButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                onCreateAccountButtonClicked();
            }
        });

        createAccFragment=view;
        return view;
    }

    public void onCreateAccountButtonClicked(){
         String username=((TextInputEditText) createAccFragment.findViewById(R.id.editTxtUsername_createAccount)).getText().toString();
         String password=((TextInputEditText) createAccFragment.findViewById(R.id.editTxtPassword_createAccount)).getText().toString();
         String emailAddress=((TextInputEditText) createAccFragment.findViewById(R.id.editTxtEmail_createAccount)).getText().toString();
         RadioButton male=createAccFragment.findViewById(R.id.radioBtnMale_createAccount);
         RadioButton female=createAccFragment.findViewById(R.id.radioBtnFemale_createAccount);
         String gender;

         if(male.isChecked()){
             gender="male";
         } else{
             gender="female";
         }

         mListener.onCreateAccountButtonClicked(username,password,
                 emailAddress,gender);
    }

}
