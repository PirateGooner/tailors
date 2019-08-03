package com.project.android.tailor;

public class UserModel {
    private String username=null;
    private String password=null;
    private String email=null;
    private String gender=null;
    private String role=null;

    public UserModel(){

    }

    public UserModel(String username,String password,String email,String gender,String role){
        this.username=username;
        this.password=password;
        this.email=email;
        this.gender=gender;
        this.role=role;
    }

    public String getUsername(){
        return username;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

    public String getGender(){
        return gender;
    }

    public String getRole(){
        return role;
    }
}
