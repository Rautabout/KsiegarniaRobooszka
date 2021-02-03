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
import com.example.ksiegarniarobooszka.R
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let { context ->
            val list = mutableListOf(
                "Wszytskie",
                "Kryminały",
                "Poradniki",
                "Horrory"
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
                        view.background = ColorDrawable(Color.parseColor("#275388"))
                    }
                    return view
                }

            }
            spinnerBookCategories.adapter = adapter
        }
    }
}