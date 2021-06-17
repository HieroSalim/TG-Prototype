package com.example.domicilio.view

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.domicilio.R
import com.example.domicilio.control.AddressRepository
import com.example.domicilio.control.UserRepository
import com.example.domicilio.services.listener.APIListener
import com.example.domicilio.services.model.AddressModel
import com.example.domicilio.services.model.MessageModel
import com.example.domicilio.services.model.ObjectModel
import com.example.domicilio.services.model.UserModel
import com.example.domicilio.services.repository.local.SecurityPreferences
import com.example.domicilio.view.fragments.DialogFragmentAddress
import com.google.gson.internal.LinkedTreeMap
import kotlinx.android.synthetic.main.activity_account_config.*
import kotlinx.android.synthetic.main.activity_account_config.addAddress
import kotlinx.android.synthetic.main.activity_appointment.*
import kotlinx.android.synthetic.main.activity_account_config.address as address1


class ActivityAccountConfig : AppCompatActivity(), View.OnClickListener {

    private val mUserRepository = UserRepository()
    private val mAddressRepository = AddressRepository()
    private lateinit var mSecurityPreferences: SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_config)
        supportActionBar?.title = "Editar informações de perfil"

        val parent = findViewById<View>(R.id.parent)
        mSecurityPreferences = SecurityPreferences(this)

        setListeners()
        populateSpinner()
        loadData()
    }

    private fun loadData(){
        user_name_edit.setText(mSecurityPreferences.get("name"))
        val token = mSecurityPreferences.get("token")
        mUserRepository.loadData("Bearer $token", mSecurityPreferences.get("user"),
            object : APIListener<UserModel>{
                override fun onSuccess(result: UserModel) {
                    user_phone_edit.setText(result.cell)
                }

                override fun onFailure(str: String) {
                    Toast.makeText(this@ActivityAccountConfig, str, Toast.LENGTH_SHORT).show()
                }

            })
    }

    private fun setListeners(){
        update_button.setOnClickListener(this)
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
                    ArrayAdapter(this@ActivityAccountConfig, android.R.layout.simple_spinner_dropdown_item, Address)
                address.adapter = adapterAdress
            }

            override fun onFailure(str: String) {
                Toast.makeText(this@ActivityAccountConfig, str, Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onClick(v: View) {
        if(v.id == R.id.addAddress){
            val dialog = DialogFragmentAddress()
            dialog.show(supportFragmentManager, "Endereço")
        }
        if(v.id == R.id.update_button){
            mUserRepository.editData(
                mSecurityPreferences.get("token"), mSecurityPreferences.get("user"),
                user_name_edit.text.toString(), user_phone_edit.text.toString(),
                object : APIListener<MessageModel>{
                    override fun onSuccess(result: MessageModel) {
                        Toast.makeText(this@ActivityAccountConfig, result.msg, Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(str: String) {
                        Toast.makeText(this@ActivityAccountConfig, str, Toast.LENGTH_SHORT).show()
                    }

                }
            )
        }
    }

}