package com.example.domicilio.view

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.domicilio.R
import com.example.domicilio.control.DoctorRepository
import com.example.domicilio.services.listener.APIListenerValidate
import com.example.domicilio.services.model.DoctorModel
import com.example.domicilio.services.repository.local.SecurityPreferences
import kotlinx.android.synthetic.main.activity_register_doctor.*

class RegisterDoctor : AppCompatActivity(), View.OnClickListener {
    private val mDoctorRepository = DoctorRepository()
    private lateinit var mSecurityPreferences: SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_doctor)

        supportActionBar?.title = "Cadastro médico"
        mSecurityPreferences = SecurityPreferences(this)

        //Inicializa eventos
        setListeners()
        populateSpinner()
    }

    private fun populateSpinner() {
        val typeProfessionals: MutableList<String> = arrayListOf()
        typeProfessionals.add("CRM")

        val adapterProfessionals =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, typeProfessionals)
        typeProfessional.adapter = adapterProfessionals

        val typesCNH: MutableList<String> = arrayListOf()
        typesCNH.add("A")
        typesCNH.add("B")
        typesCNH.add("C")
        typesCNH.add("D")
        typesCNH.add("E")
        typesCNH.add("A/B")
        typesCNH.add("A/C")
        typesCNH.add("A/D")
        typesCNH.add("A/E")

        val adapterCNH = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, typesCNH)
        typeCNH.adapter = adapterCNH
    }

    private fun setListeners() {
        buttonRegister.setOnClickListener(this)
        buttonSkip.setOnClickListener(this)
        registro.addTextChangedListener(object : TextWatcher {
            var timer: CountDownTimer? = null
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                timer?.cancel()
                timer = object : CountDownTimer(700, 1200) {
                    override fun onTick(millisUntilFinished: Long) {}

                    override fun onFinish() {
                        if (registro.text.toString() != " " && registro.text.toString() != "") {
                            mDoctorRepository.validate(registro.text.toString(), object :
                                APIListenerValidate {
                                override fun onSuccess(model: DoctorModel) {
                                    valid.setImageResource(R.drawable.ic_check)
                                    setVisibility()
                                    val profissao = model.item.toString().split(",")[3].split("=")
                                    doctor_specialization.setText(profissao[1])
                                }

                                override fun onFailure(msg: String) {
                                    valid.setImageResource(R.drawable.ic_refused)
                                       setVisibility(value = false)
                                    Toast.makeText(this@RegisterDoctor, msg, Toast.LENGTH_SHORT)
                                        .show()
                                }

                            })
                        } else {
                            setVisibility(valueValid = false, value = false)
                        }
                    }
                }.start()
            }
        })
    }

    private fun setVisibility(valueValid: Boolean = true, value: Boolean = true) {
        valid.visibility = if (valueValid) View.VISIBLE else View.GONE
        doctor_specialization.visibility = if (value) View.VISIBLE else View.GONE
        typeCNHtext.visibility = if (value) View.VISIBLE else View.GONE
        typeCNH.visibility = if (value) View.VISIBLE else View.GONE
        NumeroCNH.visibility = if (value) View.VISIBLE else View.GONE
        buttonRegister.visibility = if (value) View.VISIBLE else View.GONE
    }

    override fun onClick(v: View) {
        if (v.id == R.id.buttonRegister) {
            val spinnerTypeProfessional = typeProfessional.selectedItem.toString()
            val professionalID = registro.text.toString()
            val specialization = doctor_specialization.text.toString()
            val typeCNH = typeCNH.selectedItem.toString()
            val cnhNumber = NumeroCNH.text.toString()
            val CPF = mSecurityPreferences.get("CPF")
            val token = mSecurityPreferences.get("token")

            mDoctorRepository.register(token,
                spinnerTypeProfessional,
                professionalID,
                specialization,
                cnhNumber,
                typeCNH,
                CPF,
                object : APIListenerValidate {
                    override fun onSuccess(model: DoctorModel) {
                        mSecurityPreferences.remove("CPF")
                        startActivity(Intent(this@RegisterDoctor, MainActivity::class.java))
                        finish()
                    }

                    override fun onFailure(msg: String) {
                        Toast.makeText(this@RegisterDoctor, msg, Toast.LENGTH_SHORT).show()
                    }
                })
        }
        if(v.id == R.id.buttonSkip){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
