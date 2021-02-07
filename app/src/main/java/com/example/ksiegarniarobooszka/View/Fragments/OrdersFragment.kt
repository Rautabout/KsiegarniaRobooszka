package com.example.ksiegarniarobooszka.View.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ksiegarniarobooszka.R
import com.google.firebase.database.FirebaseDatabase

class OrdersFragment : Fragment(){


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val firebase = FirebaseDatabase.getInstance()




        return inflater.inflate(R.layout.fragment_orders,container,false)
    }
}