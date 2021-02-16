package com.daniel.common.roomSql;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {AppUsage.class}, version = 1)
public abstract class DB extends RoomDatabase {
    private static final String DATABASE_NAME = "usageList";
    private static DB sInstance;

    public static DB getInstance(Context context) {
        if (sInstance == null) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        DB.class, DB.DATABASE_NAME)
                        .build();
        }
        return sInstance;
    }

    public abstract UsageDao usageDao();
}
