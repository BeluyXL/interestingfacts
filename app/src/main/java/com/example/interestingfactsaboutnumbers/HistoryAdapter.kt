package com.example.interestingfactsaboutnumbers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.interestingfactsaboutnumbers.ui.main.SecondFragment

class HistoryAdapter (private val mFacts: List<Fact>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>()
{
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    private var mClickListener: ItemClickListener? = null
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val text_number = itemView.findViewById<TextView>(R.id.text_number)
        val text_fact = itemView.findViewById<TextView>(R.id.text_fact)
        val history_constraint = itemView.findViewById<ConstraintLayout>(R.id.history_constraint)
    }

    // ... constructor and member variables
    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.history_item, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    fun setClickListener(itemClickListener: ItemClickListener) {
        this.mClickListener = itemClickListener
    }

    // parent activity will implement this method to respond to click events
    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(viewHolder: HistoryAdapter.ViewHolder, position: Int) {
        // Get the data model based on position
        val fact: Fact = mFacts.get(position)
        // Set item views based on your views and data model
        val number = viewHolder.text_number
        number.text = fact.number.toString()
        val text_fact = viewHolder.text_fact
        text_fact.text = fact.fact
        viewHolder.history_constraint.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("method", "history")
            bundle.putInt("numberFact", fact.number!!)
            bundle.putString("fact", fact.fact)
            val secondFragment = SecondFragment()
            secondFragment.arguments = bundle
            (viewHolder.itemView.context as AppCompatActivity).supportFragmentManager.beginTransaction().replace(R.id.container, secondFragment).addToBackStack(null).commit()
        }



    }

    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return mFacts.size
    }
}