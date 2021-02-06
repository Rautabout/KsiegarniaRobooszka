package com.example.ksiegarniarobooszka.View.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ksiegarniarobooszka.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_basket.*

class BasketFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_basket,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser!=null){
            textView_userName.text=mAuth.currentUser!!.email
        }
        else{
            textView_userName.text="Jesteś nie zalogowany!"
        }
    }
}