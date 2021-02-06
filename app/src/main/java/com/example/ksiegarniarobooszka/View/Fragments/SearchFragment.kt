package com.example.ksiegarniarobooszka.View.Fragments

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ksiegarniarobooszka.R
import com.example.ksiegarniarobooszka.View.Book
import com.example.ksiegarniarobooszka.View.BookListAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment(){

    private lateinit var bookAdapter: BookListAdapter
    private lateinit var bookLayoutManager: LinearLayoutManager
    private lateinit var bookRecyclerView: RecyclerView
    private lateinit var products:DatabaseReference
    var listOfItems = ArrayList<Book>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val firebase = FirebaseDatabase.getInstance()
        products = firebase.getReference("products")
        bookLayoutManager = LinearLayoutManager(context)
        listOfItems.add(Book("Ktoś z długim nazwiskiem, żeby zobaczyć, co się dzieje","Kryminał",1,21.50,15,"https://firebasestorage.googleapis.com/v0/b/ksiegarniarobooszka.appspot.com/o/AW-Trasa%20promocyjna.jpg?alt=media&token=284c8b77-7730-4855-a1ca-062ae72c86ac","Tytuł"))
        listOfItems.add(Book("Jakub Ćwiek","Kryminał",2,45.50,10,"https://firebasestorage.googleapis.com/v0/b/ksiegarniarobooszka.appspot.com/o/J%C4%86-K%C5%82amca.jpg?alt=media&token=ac98cb43-65a6-408b-92d2-67c490c4969c","Kłamca"))
        bookAdapter= BookListAdapter(listOfItems, context!!)


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

        bookRecyclerView=recycler_search.apply {
            this.layoutManager = bookLayoutManager
            this.adapter = bookAdapter
        }


//        val firebase = FirebaseDatabase.getInstance()
//        products = firebase.getReference("products")
//        bookRecyclerView = view.findViewById(R.id.recycler_search)



    }

}