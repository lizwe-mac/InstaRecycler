package com.lizwe.instarecycler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lizwe.instarecycler.models.Property
import com.lizwe.instarecycler.network.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(){
    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var myAdapter: RecyclerView.Adapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("response","Before layoutMager")
        manager = LinearLayoutManager(this)
        Log.d("response","After LayoutMager")
        Log.d("response","Before getData")
        getAllData()
        Log.d("response",getAllData().toString())
    }

    fun getAllData(){

        Api.retrofitService.getAllData().enqueue(object: Callback<List<Property>>{

            override fun onResponse(

                call: Call<List<Property>>,
                response: Response<List<Property>>

            ) {
                Log.d("response","before successful response")
                if(response.isSuccessful){
                    Log.d("response","successful")
                    recyclerView = findViewById<RecyclerView>(R.id.recycler_view).apply{
                        myAdapter = MyAdapter(response.body()!!)
                        layoutManager = manager
                        adapter = myAdapter
                    }
                }
                else{
                    Log.d("response","Response was not successful")
                }
            }

            override fun onFailure(call: Call<List<Property>>, t: Throwable) {
                Log.d("response","failed to call property")
                t.printStackTrace()
            }
        })
    }
}