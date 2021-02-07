package com.example.ksiegarniarobooszka.View.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ksiegarniarobooszka.Model.Book
import com.example.ksiegarniarobooszka.R
import com.example.ksiegarniarobooszka.ViewModel.Adapters.BookListAdapter
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private lateinit var newBookAdapter: BookListAdapter
    private lateinit var newBookLayoutManager: LinearLayoutManager
    private lateinit var newBookRecyclerView: RecyclerView
    private lateinit var products: DatabaseReference
    private lateinit var fragmentView: View
    var listOfItems = ArrayList<Book>()
    var listOfNewItems = ArrayList<Book>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val firebase = FirebaseDatabase.getInstance()
        products = firebase.getReference("products")
        newBookLayoutManager = LinearLayoutManager(context)
        newBookAdapter = BookListAdapter(listOfItems, requireContext())
        fragmentView =
            LayoutInflater.from(activity).inflate(R.layout.fragment_home, container, false)
        newBookRecyclerView = fragmentView.findViewById(R.id.recycles_new_books)
        newBookRecyclerView.setHasFixedSize(true)
        newBookRecyclerView.layoutManager = LinearLayoutManager(context)

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newBookRecyclerView = recycles_new_books.apply {
            this.layoutManager = newBookLayoutManager
            this.adapter = newBookAdapter
        }

        products.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(context, "Wystąpił problem z bazą danych.", Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                Log.d("w onDataChange", "Jestem ")
                if (p0.exists()) {
                    for (h in p0.children) {
                        val bal = h.getValue(Book::class.java)
                        listOfItems.add(bal!!)
                    }
                    if (listOfItems.size > 5) {
                        Log.d("w forze", "Jestem ")
                        for (i in 1..5) {
                            listOfNewItems.add(listOfItems[listOfItems.size - i])
                        }
                    } else {
                        listOfNewItems = listOfItems
                    }
                    if(context!=null)
                    {
                        val adapter = BookListAdapter(listOfNewItems, context!!)
                        newBookRecyclerView.setAdapter(adapter)
                    }

                }
            }
        })
    }
}