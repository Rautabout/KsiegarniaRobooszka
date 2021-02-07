package com.example.ksiegarniarobooszka.ViewModel.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.ksiegarniarobooszka.Model.BookCart
import com.example.ksiegarniarobooszka.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.lang.Integer.parseInt

class CartListAdapter (private val dataArray: ArrayList<BookCart>):RecyclerView.Adapter<CartListAdapter.CartHolder>() {

    inner class CartHolder(view: View) : RecyclerView.ViewHolder(view)

    val firebase = FirebaseDatabase.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartListAdapter.CartHolder{
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.shopping_cart_list_one_row, parent, false)
        return CartHolder(view)
    }

    override fun getItemCount(): Int {
        return dataArray.size
    }
    override fun onBindViewHolder(holder: CartListAdapter.CartHolder, position: Int) {

        val uid= FirebaseAuth.getInstance().uid?:""
        val currentTransaction = FirebaseDatabase.getInstance().getReference("currentTransactions/$uid")

        var textViewTitle = holder.itemView.findViewById<TextView>(R.id.textViewTitleSC)
        var textViewAuthor = holder.itemView.findViewById<TextView>(R.id.textViewAuthorSC)
        var textViewPrice = holder.itemView.findViewById<TextView>(R.id.textViewPriceSC)
        var editTextNumber = holder.itemView.findViewById<EditText>(R.id.editTextCountSC)
        var delButton = holder.itemView.findViewById<ImageButton>(R.id.imageButtonDeleteSC)

        val bookCart = dataArray[position]

        textViewTitle.text = bookCart.title
        textViewAuthor.text = bookCart.author
        textViewPrice.text = bookCart.price.toString()
        editTextNumber.setText(bookCart.number!!.toString())

        delButton.setOnClickListener {

            if (currentTransaction!=null)
            {
                currentTransaction.child(bookCart.id.toString()).removeValue()
            } }

        editTextNumber.addTextChangedListener {
            if(editTextNumber.getText().toString()!="")
            {
                currentTransaction.child(bookCart.id.toString()).child("number").setValue(Integer.parseInt(editTextNumber.text.toString()))
            }
            if(Integer.parseInt(editTextNumber.getText().toString())>bookCart.quantity!!)
            {
                editTextNumber.setText(bookCart.quantity.toString())
            }
        }

        }
    }

