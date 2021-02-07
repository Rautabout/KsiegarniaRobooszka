package com.example.ksiegarniarobooszka.ViewModel.Adapters


import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ksiegarniarobooszka.Model.Book
import com.example.ksiegarniarobooszka.Model.BookCart
import com.example.ksiegarniarobooszka.R
import com.example.ksiegarniarobooszka.View.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_user.*


class BookListAdapter(private val dataArray: ArrayList<Book>, private val con: Context):RecyclerView.Adapter<BookListAdapter.BookHolder>() {
    inner class BookHolder(view: View) : RecyclerView.ViewHolder(view)

    val firebase = FirebaseDatabase.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.book_list_one_row, parent, false)
        return BookHolder(view)
    }

    override fun getItemCount(): Int {
        return dataArray.size
    }
    override fun onBindViewHolder(holder: BookHolder, position: Int) {


        var imageBook = holder.itemView.findViewById<ImageView>(R.id.imageBook)
        var textViewTitle = holder.itemView.findViewById<TextView>(R.id.textViewTitle)
        var textViewAuthor = holder.itemView.findViewById<TextView>(R.id.textViewAuthor)
        var textViewPrice = holder.itemView.findViewById<TextView>(R.id.textViewPrice)
        var textViewQuantity = holder.itemView.findViewById<TextView>(R.id.textViewShop)
        var buyButton = holder.itemView.findViewById<ImageButton>(R.id.imageButtonAdd)

        val book = dataArray[position]

        Glide.with(con).load(book.thumbnail_url).into(imageBook);
        textViewTitle.text = book.title
        textViewAuthor.text = book.author
        textViewPrice.text = book.price.toString()
        textViewQuantity.text = book.quantity.toString()

        buyButton.setOnClickListener {
            val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
            val uid=FirebaseAuth.getInstance().uid?:""
            if (mAuth.currentUser==null){
                Toast.makeText(con,"Musisz się zalogować, żeby dodać produkt.", Toast.LENGTH_SHORT).show()
            }
            else {
                val currentTransaction = FirebaseDatabase.getInstance().getReference("currentTransactions/$uid")
                if ((currentTransaction!=null)&&(book.quantity!!>0))
                {
                    val currentBook=currentTransaction.child(book.id.toString())
                    val bookCart = BookCart(book.author, book.id, 1, book.price, book.quantity, book.title)
                    currentBook.setValue(bookCart).addOnSuccessListener {
                        Toast.makeText(con, "Książka została dodana!", Toast.LENGTH_SHORT).show()
                    }
                }
                else
                {
                    Toast.makeText(con,"Książka aktualnie nie jest dostępna.", Toast.LENGTH_SHORT).show()
                }
            }

        }

    }

}