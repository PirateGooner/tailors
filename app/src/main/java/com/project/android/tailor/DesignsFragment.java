package com.project.android.tailor;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class DesignsFragment extends Fragment {

    DesignsPagerAdapter designsPagerAdapter;
    ViewPager viewPager;
    private LoginViewModel loginViewModel;

    public DesignsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_designs, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);

/*

        loginViewModel= ViewModelProviders.of(requireActivity()).get(LoginViewModel.class);

        final NavController navController= Navigation.findNavController(view);
        loginViewModel.authenticationState.observe(getViewLifecycleOwner(),new Observer<LoginViewModel.AuthenticationState>(){
            public void onChanged(LoginViewModel.AuthenticationState authenticationState){

                switch(authenticationState){
                    case AUTHENTICATED:
                        setupView(view,savedInstanceState);

                        break;
                    case UNAUTHENTICATED:
                        navController.navigate(R.id.designs_fragment_to_login_fragment);
                        break;
                }

            }
        });

*/

        designsPagerAdapter=new DesignsPagerAdapter(getChildFragmentManager());
        viewPager=view.findViewById(R.id.viewPager_design);
        viewPager.setAdapter(designsPagerAdapter);

        TabLayout tabLayout=view.findViewById(R.id.tabLayout_design);
        tabLayout.setupWithViewPager(viewPager);



    }

    public void showWelcomeMessage(){
        Log.d("MyActivity","show welcome message called");
    }


    public void setupView(View view,Bundle savedInstanceState){
        designsPagerAdapter=new DesignsPagerAdapter(getChildFragmentManager());
        viewPager=view.findViewById(R.id.viewPager_design);
        viewPager.setAdapter(designsPagerAdapter);

        TabLayout tabLayout=view.findViewById(R.id.tabLayout_design);
        tabLayout.setupWithViewPager(viewPager);
    }

}
