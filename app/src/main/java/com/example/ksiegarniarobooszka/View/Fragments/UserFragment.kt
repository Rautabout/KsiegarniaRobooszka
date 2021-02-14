package com.example.ksiegarniarobooszka.View.Fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.ksiegarniarobooszka.R
import com.example.ksiegarniarobooszka.View.MainActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragment : Fragment(){


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user,container,false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mAuth:FirebaseAuth= FirebaseAuth.getInstance()         //jezeli chcemy aktualnie zalogowanego usera to pobieramy instancje firebaseAuth
        if (mAuth.currentUser==null){ //WAZNE, jesli nie ma zalogowanego uzytkownika to wtedy instancja z firebase jest nullem (przydatne przy sprawdzeniu czy uzytkownik jest zalogowany zeby zrealizowac koszyk)
            Log.d("User", "current User: none")
        }else {
            currentUser.text="Witaj, ${mAuth.currentUser!!.email}" //tutaj edittext w fragment_user
            Log.d("User", "current User: ${mAuth.currentUser!!.email}")
        }

        logOutButton.setOnClickListener{
            mAuth.signOut()
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }
    }


}