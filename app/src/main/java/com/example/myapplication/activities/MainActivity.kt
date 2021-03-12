package com.example.myapplication.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.adapters.DataAdapter
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.models.CompanyListItem
import com.example.myapplication.services.IEndPointApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var dataSource = mutableListOf<CompanyListItem>()
    var dataAdapter: DataAdapter? = null

    private val apiService by lazy {
        IEndPointApiService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        init()
        loadData()
    }

    private fun init() {
        dataAdapter = DataAdapter(dataSource, object : DataAdapter.Callback {
            override fun onItemClicked(item: CompanyListItem) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra("id", item.id)
                startActivity(intent)
            }
        }, this)

        binding.recyclerView.adapter = dataAdapter
    }

    private fun loadData() {
        apiService.getCompanies().enqueue(object : Callback<MutableList<CompanyListItem>> {

            override fun onFailure(call: Call<MutableList<CompanyListItem>>, t: Throwable) {

            }

            override fun onResponse(call: Call<MutableList<CompanyListItem>>, response: Response<MutableList<CompanyListItem>>) {
                dataSource.clear()
                dataSource.addAll(response.body() as MutableList<CompanyListItem>)
                dataAdapter?.notifyDataSetChanged()
            }
        })
    }
}
