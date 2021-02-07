package com.example.ksiegarniarobooszka.View.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.ksiegarniarobooszka.R
import com.example.ksiegarniarobooszka.View.MainActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_user.*

class LoginFragment : Fragment(){


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login,container,false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                        val intent = Intent(activity, MainActivity::class.java)
                        startActivity(intent)
                }
                else{
                    val message = task.exception!!.toString()
                    Toast.makeText(activity, "Nie ma takiego użytkownika w bazie.", Toast.LENGTH_LONG).show()
                }
                }

            }
        }


        loginRegisterButton.setOnClickListener {
            val intent = Intent(activity, RegisterFragment::class.java)
            startActivity(intent)
        }
    }
}