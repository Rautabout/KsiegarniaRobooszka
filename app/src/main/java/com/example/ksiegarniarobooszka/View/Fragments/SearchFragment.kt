package com.example.ksiegarniarobooszka.View.Fragments

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ksiegarniarobooszka.R
import com.example.ksiegarniarobooszka.View.Book
import com.example.ksiegarniarobooszka.View.BookListAdapter
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment(){

    private lateinit var bookAdapter: BookListAdapter
    private lateinit var bookLayoutManager: LinearLayoutManager
    private lateinit var bookRecyclerView: RecyclerView
    private lateinit var products:DatabaseReference
    private lateinit var fragmentView:View
    var listOfItems = ArrayList<Book>()
    var listOfCurrentItems = ArrayList<Book>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val firebase = FirebaseDatabase.getInstance()
        products = firebase.getReference("products")
        bookLayoutManager = LinearLayoutManager(context)
        bookAdapter= BookListAdapter(listOfItems, context!!)
        fragmentView = LayoutInflater.from(activity).inflate(R.layout.fragment_search, container, false)
        bookRecyclerView = fragmentView?.findViewById(R.id.recycler_search)
        bookRecyclerView?.setHasFixedSize(true)
        bookRecyclerView?.layoutManager = LinearLayoutManager(context)
        products?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(context,"There is a problem with database.", Toast.LENGTH_LONG)
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0!!.exists()){
                    for (h in p0.children){
                        val bal = h.getValue(Book::class.java)
                        listOfItems?.add(bal!!)
                    }
                    val adapter = BookListAdapter(listOfItems,context!!)
                    bookRecyclerView?.setAdapter(adapter)
                }
            }
        })


        return inflater.inflate(R.layout.fragment_search,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let { context ->
            val list = mutableListOf(
                "Wszystkie",
                "Kryminały",
                "Poradniki",
                "Horrory",
                "Komiksy",
                "Fantastyka",
                "Thrillery",
                "Science-Fiction"
            )
            val adapter: ArrayAdapter<String> = object : ArrayAdapter<String>(
                context, android.R.layout.simple_spinner_dropdown_item,
                list
            ) {
                override fun getDropDownView(
                    position: Int,
                    convertView: View?,
                    parent: ViewGroup
                ): View {
                    val view: TextView =
                        super.getDropDownView(position, convertView, parent) as TextView
                    view.setTypeface(Typeface.MONOSPACE, Typeface.BOLD)
                    view.setTextColor(Color.parseColor("#3F6D99"))

                    if (position == spinnerBookCategories.selectedItemPosition) {
                        view.background = ColorDrawable(Color.parseColor("#80018786"))
                    }
                    return view
                }

            }
            spinnerBookCategories.adapter = adapter
        }

        spinnerBookCategories?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                editTextAuthor.setText("")
                editTextTitle.setText("")
                view?.hideKeyboard()
                listOfCurrentItems = ArrayList<Book>()
                var genre = ""
                when (position) {
                    0 -> listOfCurrentItems = listOfItems
                    1 -> genre = "crime"
                    2 -> genre = "guide"
                    3 -> genre = "horror"
                    4 -> genre = "comic"
                    5 -> genre = "fantasy"
                    6 -> genre = "thriller"
                    7 -> genre = "Sci/fi"
                }
                if(genre != "" ) {
                    for (item in listOfItems) {
                        if (item.genre == genre) {
                            listOfCurrentItems.add(item)
                        }
                    }
                }

                val adapter = BookListAdapter(listOfCurrentItems,context!!)
                bookRecyclerView?.setAdapter(adapter)
            }

        }

        bookRecyclerView=recycler_search.apply {
            this.layoutManager = bookLayoutManager
            this.adapter = bookAdapter
        }

        buttonSearch.setOnClickListener {
            view.hideKeyboard()
            if((editTextAuthor.text.toString()!="")&&(editTextTitle.text.toString()!=""))
            {

            }
        }
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

}