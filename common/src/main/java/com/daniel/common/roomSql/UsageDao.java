package com.daniel.common.roomSql;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UsageDao {
    @Query("SELECT * FROM userAppUsage")
    List<AppUsage> getAll();

    @Insert
    void insertAll(AppUsage... appUsages);
}
