package com.example.ksiegarniarobooszka.View.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ksiegarniarobooszka.Model.User
import com.example.ksiegarniarobooszka.R
import com.example.ksiegarniarobooszka.View.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_register.*
import java.util.*


class RegisterFragment : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_register)

        val mAuth:FirebaseAuth=FirebaseAuth.getInstance()


        registerButton.setOnClickListener {
            val firstName = registerFirstName.text.toString().trim()
            val lastName = registerLastName.text.toString().trim()
            val email = registerEmail.text.toString().trim()
            val password = registerPassword.text.toString().trim()
            if (mAuth.currentUser != null) {
                //TODO
            }
            if (firstName.isEmpty()) {
                registerFNameWrapper.error = "Wprowadź swoje imię"
                registerFNameWrapper.requestFocus()
                return@setOnClickListener
            }
            else if (lastName.isEmpty()) {
                registerLNameWrapper.error = "Wprowadź swoje nazwisko"
                registerLNameWrapper.requestFocus()
                return@setOnClickListener
            }
            else if (email.isEmpty()) {
                registerEmailWrapper.error = "Wprowadź swój e-mail"
                registerEmailWrapper.requestFocus()
                return@setOnClickListener
            }
            else if (password.isEmpty()) {
                registerPasswordWrapper.error = "Wprowadź swoje hasło"
                registerPasswordWrapper.requestFocus()
                return@setOnClickListener
            }else {
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val uid=FirebaseAuth.getInstance().uid?:""
                        val user = User(uid,firstName, lastName, email)
                        val ref=FirebaseDatabase.getInstance().getReference("users/$uid")
                        ref.setValue(user).addOnSuccessListener {
                            Toast.makeText(this, "User created", Toast.LENGTH_LONG).show()
                            val intent = Intent(this@RegisterFragment, MainActivity::class.java)
                            startActivity(intent)
                        }

                    } else {
                        val message = task.exception!!.toString()
                        Toast.makeText(this, "Error: $message", Toast.LENGTH_LONG).show()
                        mAuth.signOut()
                    }

                }
            }
        }
    }

}
