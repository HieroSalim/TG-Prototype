package com.example.domicilio.control

import com.example.domicilio.services.listener.APIListener
import com.example.domicilio.services.model.DoctorModel
import com.example.domicilio.services.model.MessageModel
import com.example.domicilio.services.model.ObjectModel
import com.example.domicilio.services.model.ProfileModel
import com.example.domicilio.services.repository.remote.DoctorService
import com.example.domicilio.services.repository.remote.RetrofitClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DoctorRepository {

    private lateinit var mMedicRemote: DoctorService
    private val mRemote = RetrofitClient.createService(DoctorService::class.java)

    fun validate(numero: String, listener: APIListener<ObjectModel>) {
        mMedicRemote = RetrofitClient.createMedicService(DoctorService::class.java)
        val filter = HashMap<String, String>()
        filter["tipo"] = "CRM"
        filter["uf"] = "SP"
        filter["q"] = numero
        filter["chave"] = "7576831950"
        filter["destino"] = "json"
        val call: Call<ObjectModel> = mMedicRemote.validate(filter)
        //Assíncrona
        call.enqueue(object : Callback<ObjectModel> {
            override fun onResponse(call: Call<ObjectModel>, response: Response<ObjectModel>) {
                if (response.code() != 200) {
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    listener.onFailure(jObjError.getString("erro"))
                } else {
                    response.body()?.let {
                        if (it.item.toString() == "[]" || it.item.toString().length == 2) {
                            listener.onFailure("Esse registro não existe!")
                        } else {
                            listener.onSuccess(it)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ObjectModel>, t: Throwable) {
                listener.onFailure(t.toString())
            }

        })
    }

    fun register(
        token: String, typeProfessional: String, professionalID: String, specialization: String,
        cnhNumber: String,
        typeCNH: String,
        CPF: String,
        listener: APIListener<DoctorModel>
    ) {

        val call: Call<DoctorModel> = mRemote.register(
            "Bearer $token", typeProfessional, professionalID, specialization, cnhNumber, typeCNH, CPF
        )
        call.enqueue(object : Callback<DoctorModel> {
            override fun onResponse(call: Call<DoctorModel>, response: Response<DoctorModel>) {
                if (response.code() != 201) {
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    listener.onFailure(jObjError.getString("mensagem"))
                } else {
                    response.body()?.let { listener.onSuccess(it) }
                }
            }

            override fun onFailure(call: Call<DoctorModel>, t: Throwable) {
                listener.onFailure("Ocorreu um erro inesperado. Tente novamente mais tarde.")
            }

        })
    }

    fun searchDoctorsOn(token: String, typeProfessional: String, dateHour: String, listener: APIListener<ObjectModel>){
        val type = "CRM"
        val call: Call<ObjectModel> = mRemote.searchDoctorsOn("Bearer $token", type, dateHour)
        call.enqueue(object : Callback<ObjectModel> {
            override fun onResponse(call: Call<ObjectModel>, response: Response<ObjectModel>) {
                if(response.code() != 200){
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    listener.onFailure(jObjError.getString("mensagem"))
                } else{
                    response.body()?.let { listener.onSuccess(it) }
                }
            }

            override fun onFailure(call: Call<ObjectModel>, t: Throwable) {
                listener.onFailure("Ocorreu um erro inesperado. Tente novamente mais tarde.")
            }
        })
    }

    fun loadProfile(token: String, idProfile: Int, listener: APIListener<ProfileModel>){
        val call: Call<ProfileModel> = mRemote.loadProfile("Bearer $token", idProfile)
        call.enqueue(object : Callback<ProfileModel> {
            override fun onResponse(call: Call<ProfileModel>, response: Response<ProfileModel>) {
                if(response.code() != 200){
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    listener.onFailure(jObjError.getString("mensagem"))
                } else{
                    response.body()?.let { listener.onSuccess(it) }
                }
            }

            override fun onFailure(call: Call<ProfileModel>, t: Throwable) {
                listener.onFailure("Ocorreu um erro inesperado. Tente novamente mais tarde.")
            }

        })
    }

    fun addProfile(token: String, description: String, user: String, price: Float,
                   listener: APIListener<MessageModel>){
        val call: Call<MessageModel> = mRemote.addProfile("Bearer $token", description,
            user, price)

        call.enqueue(object : Callback<MessageModel>{
            override fun onResponse(call: Call<MessageModel>, response: Response<MessageModel>) {
                if(response.code() != 200){
                    listener.onFailure("Ocorreu um erro inesperado. Tente novamente mais tarde.")
                }else{
                    response.body()?.let { listener.onSuccess(it) }
                }
            }

            override fun onFailure(call: Call<MessageModel>, t: Throwable) {
                listener.onFailure("Ocorreu um erro inesperado. Tente novamente mais tarde.")
            }

        })
    }

}