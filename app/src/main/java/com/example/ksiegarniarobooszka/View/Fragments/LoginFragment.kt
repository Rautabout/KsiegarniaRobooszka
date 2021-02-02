package com.example.ksiegarniarobooszka.View.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.ksiegarniarobooszka.View.MainActivity
import com.example.ksiegarniarobooszka.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class LoginFragment : AppCompatActivity() {
    private lateinit var loginButton: Button
    private lateinit var loginRegisterButton: Button
    private lateinit var loginEmail:EditText
    private lateinit var loginPassword:EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_login)
        loginButton=findViewById(R.id.loginButton)
        loginRegisterButton=findViewById(R.id.loginRegisterButton)







        loginButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val intent = Intent(this@LoginFragment, MainActivity::class.java)
                startActivity(intent)
            }
        })

        loginRegisterButton.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                val intent =Intent(this@LoginFragment,RegisterFragment::class.java)
                startActivity(intent)
            }
        })
    }
}