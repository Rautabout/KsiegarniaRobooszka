package com.example.ksiegarniarobooszka.View.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ksiegarniarobooszka.View.MainActivity
import com.example.ksiegarniarobooszka.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*


class LoginFragment : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_login)
        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()


        loginButton.setOnClickListener{
            val email = loginEmail.text.toString().trim()
            val password = loginPassword.text.toString().trim()
            if (email.isEmpty()) {
                loginEmailWrapper.error = "Wprowadź swój e-mail"
                loginEmailWrapper.requestFocus()
                return@setOnClickListener
            }
            else if (password.isEmpty()) {
                loginPasswordWrapper.error = "Wprowadź swoje hasło"
                loginPasswordWrapper.requestFocus()
                return@setOnClickListener
            }
            else {
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                    task ->if(task.isSuccessful){
                    val intent = Intent(this@LoginFragment, MainActivity::class.java)
                    startActivity(intent)
                }
                    else{
                    val message = task.exception!!.toString()
                    Toast.makeText(this, "Error: $message", Toast.LENGTH_LONG).show()
                }
                }

            }
        }


        loginRegisterButton.setOnClickListener {
            val intent = Intent(this@LoginFragment, RegisterFragment::class.java)
            startActivity(intent)
        }
    }
}