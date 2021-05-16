package com.example.domicilio.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.domicilio.R
import com.example.domicilio.control.UserRepository
import com.example.domicilio.services.listener.APIListenerUser
import com.example.domicilio.services.model.UserModel
import com.example.domicilio.services.repository.local.SecurityPreferences

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mSecurityPreferences: SecurityPreferences
    private val mUserRepository : UserRepository = UserRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        //Inicializando variaveis
        mSecurityPreferences = SecurityPreferences(this)
        loadUser(mSecurityPreferences.get("token"))

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Abrir a tela de Agendamento", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_appointments, R.id.nav_chat), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    private fun loadUser(token: String){
        mUserRepository.loadSession(token, object : APIListenerUser {
            override fun onSuccess(model: UserModel) {
                mSecurityPreferences.store("user",model.user)
                mSecurityPreferences.store("typeUser",model.typeUser)
                mSecurityPreferences.store("email",model.email)
                mSecurityPreferences.store("name",model.name)
            }

            override fun onFailure(str: String) {
                if(str == "Falha na autenticação"){
                    mSecurityPreferences.remove("token")
                    mSecurityPreferences.remove("user")
                    mSecurityPreferences.remove("typeUser")
                    mSecurityPreferences.remove("email")
                    mSecurityPreferences.remove("name")
                    startActivity(Intent(this@MainActivity, ActivityLogin::class.java))
                    finish()
                }else{
                    Toast.makeText(this@MainActivity,str,Toast.LENGTH_LONG).show()
                }
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onClick(v: View) {
        if(v.id == R.id.nav_chat)
            startActivity(Intent(this, ChatMainActivity::class.java))
    }
}