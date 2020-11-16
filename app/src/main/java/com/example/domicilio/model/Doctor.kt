package com.example.domicilio.model

class Doctor(var CRM: Long, var specialization: String, var CNH: Long, var typeCNH: String,
             name: String, email: String, address: String, cel: String, CPF: Long) : User(name, email, address, cel, CPF)