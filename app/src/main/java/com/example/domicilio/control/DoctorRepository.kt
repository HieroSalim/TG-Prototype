package com.example.domicilio.control

import com.example.domicilio.services.listener.APIListenerValidate
import com.example.domicilio.services.model.DoctorModel
import com.example.domicilio.services.repository.remote.DoctorService
import com.example.domicilio.services.repository.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DoctorRepository {

    private lateinit var mMedicRemote: DoctorService

    fun validate(numero: String, listener: APIListenerValidate){
        mMedicRemote = RetrofitClient.createMedicService(DoctorService::class.java)
        val filter = HashMap<String,String>()
        filter.put("tipo","CRM")
        filter.put("uf","SP")
        filter.put("q",numero)
        filter.put("chave","7576831950")
        filter.put("destino","json")
        val call: Call<DoctorModel> = mMedicRemote.validate(filter)
        //Assíncrona
        call.enqueue(object : Callback<DoctorModel> {
            override fun onResponse(call: Call<DoctorModel>, response: Response<DoctorModel>) {
                response.body()?.let { listener.onSuccess(it) }
            }

            override fun onFailure(call: Call<DoctorModel>, t: Throwable) {
                listener.onFailure(t.toString())
            }

        })
    }

}