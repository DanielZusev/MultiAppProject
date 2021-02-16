package com.daniel.common.roomSql;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "userAppUsage")
public class AppUsage {

@PrimaryKey(autoGenerate = true)
    private long id;

@ColumnInfo(name = "StartTime")
    private String startTime;

@ColumnInfo(name = "EndTime")
    private String endTime;

@ColumnInfo(name = "TotalUsageTime")
    private long totalUsageTime;


    public AppUsage() {
    }

    public AppUsage( String startTime, String endTime, long totalUsageTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalUsageTime = totalUsageTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public long getTotalUsageTime() {
        return totalUsageTime;
    }

    public void setTotalUsageTime(long totalUsageTime) {
        this.totalUsageTime = totalUsageTime;
    }
}
