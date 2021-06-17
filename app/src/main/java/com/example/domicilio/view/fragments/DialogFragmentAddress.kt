package com.example.domicilio.view.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.domicilio.R
import com.example.domicilio.control.AddressRepository
import com.example.domicilio.services.repository.local.SecurityPreferences

class DialogFragmentAddress : DialogFragment() {

    private val mAddressRepository = AddressRepository()
    private lateinit var mSecurityPreferences: SecurityPreferences

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view: View = this.getLayoutInflater().inflate(R.layout.fragment_dialog_address, null)
        mSecurityPreferences = SecurityPreferences(this.requireContext())
        val button = view.findViewById<Button>(R.id.addAddress)

        val token = mSecurityPreferences.get("token")
        val rua = view.findViewById<EditText>(R.id.street)
        val bairro = view.findViewById<EditText>(R.id.neighborhood)
        val cidade = view.findViewById<EditText>(R.id.city)
        val uf = view.findViewById<EditText>(R.id.state)
        val cep = view.findViewById<EditText>(R.id.cepNumber)

        val alert = AlertDialog.Builder(activity)
        alert.setView(view)

        button!!.setOnClickListener{
            mAddressRepository.add(context,"Bearer $token", rua.text.toString(),
                bairro.text.toString(), cidade.text.toString(), uf.text.toString(),
                cep.text.toString(), mSecurityPreferences.get("user")
            )
            activity?.finish()
        }

        return alert.create()
    }
}