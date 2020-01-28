package com.example.movix.UIElements.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movix.AppLogic.Adapters.BookingsAdapter
import com.example.movix.Database.BookingsTable

import com.example.movix.R
import kotlinx.android.synthetic.main.fragment_history.view.*

/**
 * A simple [Fragment] subclass.
 */
class HistoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_history, container, false)
        val data = BookingsTable().getAllBookings(context!!).asReversed()
        root.recBookings.layoutManager = LinearLayoutManager(context)
        root.recBookings.adapter = BookingsAdapter(context!!,data)
        return root
    }


}
