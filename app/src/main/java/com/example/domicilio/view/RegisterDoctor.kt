package com.example.domicilio.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import com.example.domicilio.R
import com.example.domicilio.control.DoctorRepository
import com.example.domicilio.services.listener.APIListenerValidate
import com.example.domicilio.services.model.DoctorModel
import kotlinx.android.synthetic.main.activity_register_doctor.*

class RegisterDoctor : AppCompatActivity(), View.OnClickListener {
    private val mDoctorRepository = DoctorRepository()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_doctor)

        if(supportActionBar !=null){
            supportActionBar!!.hide()
        }

        //Inicializa eventos
        setListeners()
        populateSpinner()
    }

    fun populateSpinner(){
        val list: MutableList<String> = arrayListOf()
        list.add("CRM")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list)
        typeProfessional.adapter = adapter
    }

    fun setListeners(){
        buttonRegister.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if(v.id == R.id.buttonRegister){
            mDoctorRepository.validate(crm.text.toString(),object: APIListenerValidate {
                override fun onSuccess(model: DoctorModel) {
                    Log.d("Nome",model.name)
                }

                override fun onFailure(string: String) {
                    Log.d("Teste", string)
                }

            })
        }
    }
}