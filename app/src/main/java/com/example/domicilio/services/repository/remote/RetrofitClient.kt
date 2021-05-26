package com.example.domicilio.services.repository.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitClient private constructor() {
    companion object{

        private lateinit var retrofit: Retrofit

        private const val baseurl = "http://10.0.0.105:3000:3000/"
        private const val medicurl = "https://www.consultacrm.com.br/api/index.php?"

        private fun getRetrofitInstance(): Retrofit{
            val httpClient = OkHttpClient.Builder()
            if (!Companion::retrofit.isInitialized){
                retrofit = Retrofit.Builder()
                    .baseUrl(baseurl)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }

        fun <S> createService(serviceClass: Class<S>): S {
            return getRetrofitInstance().create(serviceClass)
        }

        private fun getRetrofitMedicInstance(tipoRegistro: String): Retrofit{
            val httpClient = OkHttpClient.Builder()
            if (!Companion::retrofit.isInitialized){
                retrofit = Retrofit.Builder()
                    .baseUrl(medicurl+"tipo=$tipoRegistro&uf=SP&q=bessa&chave=7576831950&destino=json")
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }

        fun <S> createMedicService(serviceClass: Class<S>, tipoRegistro: String): S {
            return getRetrofitMedicInstance(tipoRegistro = "CRM").create(serviceClass)
        }

    }
}