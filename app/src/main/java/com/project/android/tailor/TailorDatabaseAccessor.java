package com.project.android.tailor;

import android.content.Context;

import androidx.room.Room;

public class TailorDatabaseAccessor {

    private static TailorDatabase tailorDatabaseInstance;
    private static final String TAILOR_DB_NAME="tailor_db";

    private TailorDatabaseAccessor() {};

    public static TailorDatabase getInstance(Context context){
        if(tailorDatabaseInstance==null){
            tailorDatabaseInstance= Room.databaseBuilder(context,
                    TailorDatabase.class,TAILOR_DB_NAME).build();
        }

        return tailorDatabaseInstance;
    }
}
