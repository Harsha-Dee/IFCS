package com.example.ifsc;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APICall {

    //https://ifsc.razorpay.com/KARB0000001
    @GET("API/V1/IFSC/{code}/")
    Call<DataModel> getData(@Path("code") String ifsc_code);
}
