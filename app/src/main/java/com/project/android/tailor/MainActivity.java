package com.project.android.tailor;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private LoginViewModel model;
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;
    private UserViewModel userViewModel;
    private View navDrawerHeader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //CollapsingToolbarLayout collapsingToolbarLayout=findViewById(R.id.collapsingToolbarLayout);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navView);
        navController = Navigation.findNavController(this, R.id.fragmentNavHost_main);

        appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph())
                        .setDrawerLayout(drawerLayout)
                        .build();

        NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration);
        //NavigationUI.setupWithNavController(toolbar, navController,drawerLayout);
        NavigationUI.setupWithNavController(navigationView, navController);

        navDrawerHeader=navigationView.inflateHeaderView(R.layout.header_nav_drawer);

        model= ViewModelProviders.of(this).get(LoginViewModel.class);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                int destId = destination.getId();

                if (destId == R.id.login_fragment || destId == R.id.create_account_fragment) {
                    toolbar.setVisibility(View.GONE);
                    drawerLayout.setDrawerLockMode(drawerLayout.LOCK_MODE_LOCKED_CLOSED);
                }
                else {
                    toolbar.setVisibility(View.VISIBLE);
                    drawerLayout.setDrawerLockMode(drawerLayout.LOCK_MODE_UNLOCKED);
                }

            }
        });

        userViewModel=ViewModelProviders.of(this).get(UserViewModel.class);

        //Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);

/*
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        LoginFragment loginFragment = new LoginFragment();
        fragmentTransaction
                .add(R.id.frame_main_activity, loginFragment, "login_fragment")
                // .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                //.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
*/

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


    public void onResume() {
        super.onResume();
        Context context=getApplicationContext();

        final TextView username_navHeader=(TextView) navDrawerHeader.findViewById(R.id.txtViewUsername_drawerHeader);
        final TextView userEmail_navHeader=(TextView) navDrawerHeader.findViewById(R.id.txtViewEmail_drawerHeader);

        model.authenticationState.observe(this,new Observer<LoginViewModel.AuthenticationState>(){
            public void onChanged(LoginViewModel.AuthenticationState authenticationState){

                switch(authenticationState){
                    case AUTHENTICATED:
                        //setupView(view,savedInstanceState);
                        userViewModel.setUser();
                        break;
                    case UNAUTHENTICATED:
                        navController.navigate(R.id.action_global_login_graph);
                        break;
                }

            }
        });


        userViewModel.user.observe(this, new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                if(userModel.getEmail()!=null){
                    userEmail_navHeader.setText(userModel.getEmail());
                    username_navHeader.setText(userModel.getUsername());
                }else{
                    Log.d("MyActivity","In onResume userModel null?");
                }
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp(){
        return NavigationUI.navigateUp(navController,appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void onBackPressed(){
        DrawerLayout drawerLayout=(DrawerLayout) findViewById(R.id.drawerLayout);

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar_options_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.toolbar_logout_option:
                model.signOut();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
