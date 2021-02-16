package com.daniel.common;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.daniel.common.restClient.Garage;
import com.daniel.common.restClient.GarageController;
import com.daniel.common.roomSql.AppUsage;
import com.daniel.common.roomSql.DB;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public abstract class Activity_Main extends AppCompatActivity {

    private TextView garageName;
    private TextView garageAddress;
    private TextView garageOpen;
    private TextView garageCars;
    private TextView usageTime;
    private DB db;

    private long startTime;
    private long endTime;
    private long totalUsageTime;
    private String start;
    private String end;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.db = DB.getInstance(getApplicationContext());

        this.garageName = findViewById(R.id.main_TXT_garage_name);
        this.garageAddress = findViewById(R.id.main_TXT_garage_address);
        this.garageOpen = findViewById(R.id.main_TXT_garage_open);
        this.garageCars = findViewById(R.id.main_TXT_garage_car_list);
        this.usageTime = findViewById(R.id.main_TXT_usage_time);

        GarageController garageController = new GarageController();
        garageController.start(new GarageController.Garage_callback() {
            @Override
            public void garageDetails(Garage garage) {
                garageName.setText("Garage Name: " + garage.getName());
                garageAddress.setText("Garage Address: " + garage.getAddress());
                garageOpen.setText(String.format("Garage %s", garage.isOpen() ? "Open" : "Closed"));

                String carsStr = "";
                if (garage.getCars() != null)
                    for (String car : garage.getCars()) {
                        carsStr += "- " + car + "\n";
                    }
                garageCars.setText(carsStr);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        insertTime();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateTime();
    }

    private void insertTime() {
        this.start = new SimpleDateFormat("HH:mm:ss").format(new Date());
        this.end = new SimpleDateFormat("HH:mm:ss").format(new Date());
        this.endTime = System.currentTimeMillis();
        this.totalUsageTime = this.endTime - this.startTime;

        new Thread(new Runnable() {
            @Override
            public void run() {
                db.usageDao().insertAll(new AppUsage(start, end, totalUsageTime));
            }
        }).start();
    }

    private void updateTime() {
        this.startTime = System.currentTimeMillis();
        this.start = new SimpleDateFormat("HH:mm:ss").format(new Date());

        getCurrentUsageTime();
    }

    private void getCurrentUsageTime() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                long totalTime = 0;
                List<AppUsage> usageList = db.usageDao().getAll();
                for (AppUsage appUsage : usageList) {
                    totalTime += appUsage.getTotalUsageTime();
                }
                usageTime.setText("Total Usage Time: " + timeConverter(totalTime));
            }
        }).start();
    }

    private String timeConverter(long usageTimeInMills) {
        String time = "";
        if (usageTimeInMills != 0) {
            DateFormat formatter = new SimpleDateFormat("HH:mm:ss", Locale.US);
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            time = formatter.format(new Date(usageTimeInMills));
        }
        return time;
    }
}