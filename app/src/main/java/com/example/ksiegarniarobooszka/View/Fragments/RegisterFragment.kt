package com.example.ksiegarniarobooszka.View.Fragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ksiegarniarobooszka.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_register.*
import java.util.*
import kotlin.collections.HashMap


class RegisterFragment : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_register)


        registerButton.setOnClickListener{
            CreateAccount()
        }

    }

    private fun CreateAccount() {
        val firstName=registerFirstName.text.toString()
        val lastName=registerLastName.text.toString()
        val email=registerEmail.text.toString()
        val password=registerPassword.text.toString()
        when{
            TextUtils.isEmpty(firstName)->Toast.makeText(this,"Enter your first name",Toast.LENGTH_LONG)
            TextUtils.isEmpty(lastName)->Toast.makeText(this,"Enter your last name",Toast.LENGTH_LONG)
            TextUtils.isEmpty(email)->Toast.makeText(this,"Enter your email",Toast.LENGTH_LONG)
            TextUtils.isEmpty(password)->Toast.makeText(this,"Enter your password",Toast.LENGTH_LONG)
            else->{
                val progressDialog=ProgressDialog(this@RegisterFragment)
                progressDialog.setTitle("Register")
                progressDialog.setMessage("Working...")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()
                val mAuth:FirebaseAuth= FirebaseAuth.getInstance()
                mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener{
                        task->if (task.isSuccessful){

                        saveUserInfo(firstName,lastName,email,progressDialog)
                        }
                        else{
                        val message=task.exception!!.toString()
                        Toast.makeText(this,"Error: $message",Toast.LENGTH_LONG)
                        mAuth.signOut()
                        progressDialog.dismiss()
                        }
                    }
            }
        }
    }

    private fun saveUserInfo(firstName: String, lastName: String, email: String,progressDialog:ProgressDialog) {
        val currUserID=FirebaseAuth.getInstance().currentUser!!.uid
        val usersRef:DatabaseReference=FirebaseDatabase.getInstance().reference.child("users")
        val userMap=HashMap<String,Any>()
        userMap["uid"]=currUserID
        userMap["firstname"]=currUserID
        userMap["lastname"]=currUserID
        userMap["email"]=currUserID

        usersRef.child(currUserID).setValue(userMap)
            .addOnCompleteListener { task->
                if(task.isSuccessful)
                {

                    progressDialog.dismiss()
                    Toast.makeText(this,"Success",Toast.LENGTH_LONG)

                    val intent= Intent(this@RegisterFragment,LoginFragment::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                }
                else{
                    val message=task.exception!!.toString()
                    Toast.makeText(this,"Error: $message",Toast.LENGTH_LONG)
                    FirebaseAuth.getInstance().signOut()
                    progressDialog.dismiss()
                }
            }
    }
}
