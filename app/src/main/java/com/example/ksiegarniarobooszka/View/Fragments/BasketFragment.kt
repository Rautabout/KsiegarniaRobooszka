package com.example.ksiegarniarobooszka.View.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ksiegarniarobooszka.Model.BookCart
import com.example.ksiegarniarobooszka.R
import com.example.ksiegarniarobooszka.View.MainActivity
import com.example.ksiegarniarobooszka.ViewModel.Adapters.CartListAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_basket.*
import kotlinx.android.synthetic.main.fragment_search.*
import java.math.RoundingMode
import java.text.DecimalFormat

class BasketFragment : Fragment(){

    private lateinit var basketBookAdapter: CartListAdapter
    private lateinit var basketBookLayoutManager: LinearLayoutManager
    private lateinit var basketBookRecyclerView: RecyclerView
    private lateinit var fragmentView:View
    var listOfItems = ArrayList<BookCart>()
    var toPay = 0.0
    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val uid=FirebaseAuth.getInstance().uid?:""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        basketBookLayoutManager = LinearLayoutManager(context)
        basketBookAdapter= CartListAdapter(listOfItems)
        fragmentView = LayoutInflater.from(activity).inflate(R.layout.fragment_basket, container, false)
        basketBookRecyclerView = fragmentView.findViewById(R.id.recycler_shoppingCart)
        basketBookRecyclerView.setHasFixedSize(true)
        basketBookRecyclerView.layoutManager = LinearLayoutManager(context)


        return inflater.inflate(R.layout.fragment_basket,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()


        if (mAuth.currentUser!=null){
            textView_userName.text=mAuth.currentUser!!.email!!.split("@")[0]
        }
        else{
            textView_userName.text="-"
        }
        basketBookRecyclerView=recycler_shoppingCart.apply {
            this.layoutManager = basketBookLayoutManager
            this.adapter = basketBookAdapter
        }

        buttonBuy.setOnClickListener {
            if(listOfItems.size>0) {
                Toast.makeText(context, "Dziękujemy za zakupy!", Toast.LENGTH_SHORT).show()
                textView_priceValue.text = "0"
                val userTransactions = FirebaseDatabase.getInstance().getReference("transactions/$uid")
                val currentTransaction = FirebaseDatabase.getInstance().getReference("currentTransactions/$uid")
                val products = FirebaseDatabase.getInstance().getReference("products")
                val pushedId = userTransactions.push().getKey()
                for(item in listOfItems)
                {
                    val bookID = item.id!! - 1
                    val number = item.quantity!! - item.number!!
                    products.child(bookID.toString()).child("quantity").setValue(number)
                    userTransactions.child(pushedId!!).child(item.id!!.toString()).setValue(item)
                }
                listOfItems = ArrayList<BookCart>()
                val adapter = CartListAdapter(listOfItems)
                basketBookRecyclerView.setAdapter(adapter)
                currentTransaction.removeValue()
            }
            else{
                Toast.makeText(context, "Musisz dodać coś do koszyka.", Toast.LENGTH_SHORT).show()
            }

        }

        if (mAuth.currentUser!=null) {
            val currentTransaction = FirebaseDatabase.getInstance().getReference("currentTransactions/$uid")
            currentTransaction.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Toast.makeText(context,"Wystąpił problem z bazą danych.", Toast.LENGTH_LONG).show()
                }

                override fun onDataChange(p0: DataSnapshot) {
                    listOfItems = ArrayList<BookCart>()
                    toPay = 0.0
                    if(p0.exists()){
                        for (h in p0.children){
                            val book = h.getValue(BookCart::class.java)
                            listOfItems.add(book!!)
                            try {
                                toPay += book.price!! * book.number!!
                            }
                            catch(e:Exception) { }
                        }
                    }
                    val adapter = CartListAdapter(listOfItems)
                    basketBookRecyclerView.setAdapter(adapter)

                    val df = DecimalFormat("#.##")
                    df.roundingMode=RoundingMode.CEILING
                    try {
                        textView_priceValue.setText(df.format(toPay).toString())
                    }
                    catch (e:Exception) {}
                }
            })
        }
    }
}