package com.example.ksiegarniarobooszka.View


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ksiegarniarobooszka.R


class BookListAdapter(private val dataArray: ArrayList<Book>, private val con: Context):RecyclerView.Adapter<BookListAdapter.BookHolder>() {
    inner class BookHolder(view: View) : RecyclerView.ViewHolder(view)

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
        var bmp: Bitmap

        val book = dataArray[position]

        //imageBook.setImageURI(book.image.toUri())
//        val imageUrl = book.image
//        val `in`: InputStream = URL(imageUrl).openStream()
//        bmp = BitmapFactory.decodeStream(`in`)
//
//        imageBook.setImageBitmap(bmp)
        //Picasso.get().load(book.image).into(imageBook)
        Glide.with(con).load(book.image).into(imageBook);
        textViewTitle.text = book.title
        textViewAuthor.text = book.author
        textViewPrice.text = book.price.toString()
        textViewQuantity.text = book.quantity.toString()

    }

}
