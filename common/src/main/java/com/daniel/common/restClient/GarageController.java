package com.daniel.common.restClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GarageController implements Callback<Garage> {

    public interface Garage_callback{
        void garageDetails(Garage garage);
    }

    private Garage_callback garage_callback;

    static final String BASE_URL = "https://pastebin.com/raw/";

    public void start(Garage_callback garage_callback) {
        this.garage_callback = garage_callback;

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        GarageAPI garageAPI = retrofit.create(GarageAPI.class);

        Call<Garage> call = garageAPI.loadGarageDetails();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<Garage> call, Response<Garage> response) {
        if (response.isSuccessful()) {
            Garage garageDetails = response.body();
            System.out.println(garageDetails.toString());
            if (this.garage_callback != null)
                this.garage_callback.garageDetails(garageDetails);
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<Garage> call, Throwable t) {
        t.printStackTrace();
    }
}

