package com.project.android.tailor;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="users")
public class User {
    @PrimaryKey(autoGenerate=true)
    @NonNull
    @ColumnInfo(name="user_ID")
    int userID;
    @ColumnInfo(name="user_name")
    String username;
    @ColumnInfo(name="password")
    String password;
    @ColumnInfo(name="email")
    String email;
    @ColumnInfo(name="gender")
    String gender;

    User(String username,String password,String email,String gender){
        userID=0;
        this.username=username;
        this.password=password;
        this.email=email;
        this.gender=gender;
    }
}
