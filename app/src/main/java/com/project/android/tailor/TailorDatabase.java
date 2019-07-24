package com.project.android.tailor;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities={User.class},version=1,exportSchema = false)
public abstract class TailorDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
