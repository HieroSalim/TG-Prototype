package com.example.domicilio.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.domicilio.R
import com.example.domicilio.control.AddressRepository
import com.example.domicilio.control.DoctorRepository
import com.example.domicilio.services.listener.APIListener
import com.example.domicilio.services.listener.AppointmentListener
import com.example.domicilio.services.model.AddressModel
import com.example.domicilio.services.model.ObjectModel
import com.example.domicilio.services.repository.local.SecurityPreferences
import com.example.domicilio.view.adapters.MedicAdapter
import com.example.domicilio.view.fragments.DialogFragmentAddress
import com.google.gson.internal.LinkedTreeMap
import kotlinx.android.synthetic.main.activity_appointment.*
import kotlinx.android.synthetic.main.activity_register_doctor.*
import kotlinx.android.synthetic.main.activity_register_doctor.typeProfessional

class ActivityDoctorEdit : AppCompatActivity(), View.OnClickListener {

    private val mAddressRepository = AddressRepository()
    private lateinit var mSecurityPreferences: SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_edit)

        mSecurityPreferences = SecurityPreferences(this)

        setListeners()
        populateSpinner()
    }

    private fun setListeners(){
        addAddress.setOnClickListener(this)
    }

    private fun populateSpinner(){
        val Address: MutableList<AddressModel> = arrayListOf()
        val token = mSecurityPreferences.get("token")
        mAddressRepository.listByUser("Bearer $token", mSecurityPreferences.get("user"),object :
            APIListener<ObjectModel> {
            override fun onSuccess(result: ObjectModel) {
                val list = result.dados as ArrayList<LinkedTreeMap<String, AddressModel>>

                list.forEach {
                    Address.add(AddressModel(it["street"].toString(), it["idAddress"].toString().split('.')[0].toInt()))
                }
                val adapterAdress =
                    ArrayAdapter(this@ActivityDoctorEdit, android.R.layout.simple_spinner_dropdown_item, Address)
                address.adapter = adapterAdress
            }

            override fun onFailure(str: String) {
                Toast.makeText(this@ActivityDoctorEdit, str, Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onClick(v: View) {
        if(v.id == R.id.addAddress){
            val dialog = DialogFragmentAddress()
            dialog.show(supportFragmentManager, "Endereço")
        }
    }
}