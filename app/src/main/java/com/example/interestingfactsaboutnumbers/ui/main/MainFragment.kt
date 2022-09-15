package com.example.interestingfactsaboutnumbers.ui.main

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.interestingfactsaboutnumbers.*
import com.squareup.okhttp.Callback
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import kotlinx.coroutines.*
import org.json.JSONObject
import java.io.IOException
import java.util.*


class MainFragment : Fragment() {
    lateinit var edittext : EditText
    lateinit var _view : View
    lateinit var get_fact : Button
    lateinit var random_fact : Button
    lateinit var adapter : HistoryAdapter

    companion object {
        fun newInstance() = MainFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _view = inflater.inflate(R.layout.fragment_main, container, false)
        return  _view
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initializeVar()
        var db : AppDatabase
        var factDao : FactDAO

        GlobalScope.launch {
            db = Room.databaseBuilder(
                context!!.applicationContext,
                AppDatabase::class.java, "database").build()
            factDao = db.factDao()
            val rvContacts = _view.findViewById<View>(R.id.recycler_history) as RecyclerView
             adapter = HistoryAdapter(factDao.getAll().reversed())
            rvContacts.adapter = adapter

            rvContacts.layoutManager = LinearLayoutManager(context)
        }

        get_fact.setOnClickListener {

            if (edittext.text.isNotEmpty()){
                val bundle = Bundle()
                bundle.putString("method", "getFact")
                bundle.putInt("number", Integer.parseInt(edittext.text.toString()))
                val secondFragment = SecondFragment()
                secondFragment.arguments = bundle
                requireFragmentManager().beginTransaction().replace(R.id.container, secondFragment).addToBackStack(null).commit()

            }
        }

        random_fact.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("method", "randomFact")
            val secondFragment = SecondFragment()
            secondFragment.arguments = bundle
            requireFragmentManager().beginTransaction().replace(R.id.container, secondFragment).addToBackStack(null).commit()
        }


    }

    private fun initializeVar(){
        edittext =  _view.findViewById(R.id.enter_number)
        get_fact = _view.findViewById(R.id.get_fact)
        random_fact = _view.findViewById(R.id.random_fact)


    }




}