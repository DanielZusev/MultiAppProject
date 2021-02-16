package com.daniel.common.restClient;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GarageAPI {

    @GET("WypPzJCt")
    Call<Garage> loadGarageDetails();
}
