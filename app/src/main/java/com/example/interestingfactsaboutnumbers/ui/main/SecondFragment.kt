package com.example.interestingfactsaboutnumbers.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.interestingfactsaboutnumbers.*
import com.squareup.okhttp.Callback
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.IOException
import kotlin.math.log


class SecondFragment : Fragment(){

    lateinit var _view : View
    var value : JSONObject? = null
    lateinit var numberFact : TextView
    lateinit var fact : TextView
    var gate = true
    lateinit var backButton : Button



    companion object {
        fun newInstance() = SecondFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _view = inflater.inflate(R.layout.second_fragment, container, false)
        return  _view
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var gate = true;
        initializeVar()

        if (arguments?.getString("method").equals("getFact")){
            makeRequest(arguments?.getInt("number"))
            validateData()

        }else if (arguments?.getString("method").equals("randomFact")){
            makeRequest(null)
            validateData()

        }else if(arguments?.getString("method").equals("history")){
            showData(arguments?.getInt("numberFact"), arguments?.getString("fact") )

        }


        backButton.setOnClickListener {
            requireActivity().onBackPressed()

        }



    }
    private fun validateData(){
        while(gate){
            if (value != null){
                fact.text = value!!.getString("text").replace(value!!.getInt("number").toString(), "")
                numberFact.text = value!!.getInt("number").toString()
                gate = false
                GlobalScope.launch {
                    val db = Room.databaseBuilder(
                        context!!.applicationContext,
                        AppDatabase::class.java, "database").build()
                   val factDao = db.factDao()
                    factDao.insert(Fact( System.currentTimeMillis().toInt(), value!!.getInt("number"), value!!.getString("text").replace(value!!.getInt("number").toString(), "")))
                }
            }
        }
    }
    private fun showData(number: Int?, factString: String?){
        initializeVar()

        numberFact.text = number.toString()
        fact.text = factString
    }
    private fun initializeVar(){
        numberFact = requireView().findViewById(R.id.number)
        fact = requireView().findViewById(R.id.fact)
        backButton = requireView().findViewById(R.id.button_back)
    }

    fun makeRequest(number : Int?)  {
        val client = OkHttpClient()
        lateinit var request : Request
        if(number != null){
             request = Request.Builder()
                .url("http://numbersapi.com/$number?json")
                .build()
        }else {
            request = Request.Builder()
                .url("http://numbersapi.com/random/math?json")
                .build()

        }

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(request: Request?, e: IOException?) {
                Log.d("Fail", e!!.printStackTrace().toString())

            }

            override fun onResponse(response: Response?)  {
                 value = JSONObject(response!!.body().string().toString())


            }
        })

    }




}