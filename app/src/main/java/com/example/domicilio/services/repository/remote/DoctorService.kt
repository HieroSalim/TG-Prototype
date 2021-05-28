package com.example.domicilio.services.repository.remote

import com.example.domicilio.services.model.DoctorModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface DoctorService {

    @GET("index.php")
    fun validate(@QueryMap filter: HashMap<String,String>):Call<DoctorModel>

}