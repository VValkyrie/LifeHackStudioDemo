package com.example.myapplication.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.DetailBinding
import com.example.myapplication.models.CompanyDetailItem
import com.example.myapplication.services.IEndPointApiService
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailActivity : AppCompatActivity() {
	private lateinit var binding: DetailBinding
	lateinit var result: CompanyDetailItem

	private val apiService by lazy {
		IEndPointApiService.create()
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = DetailBinding.inflate(layoutInflater)
		val view = binding.root
		setContentView(view)

		supportActionBar?.setDisplayHomeAsUpEnabled(true)

		val id: Long = intent.extras?.getLong("id")!!
		loadData(id)
	}

	private fun loadData(id: Long) {
		apiService.getCompanyDetail(id).enqueue(object : Callback<MutableList<CompanyDetailItem>> {
			override fun onResponse(
					call: Call<MutableList<CompanyDetailItem>>,
					response: Response<MutableList<CompanyDetailItem>>
			) {
				result = response.body()!![0]

				if(result.name != null && result.name != ""){
					binding.titleView.text = result.name
				}
				else binding.titleView.visibility = View.GONE


				if(result.description != null && result.description != ""){
					binding.contentView.text = result.description
				}
				else binding.contentView.visibility = View.GONE


				if(result.phone != null && result.phone != ""){
					binding.phone.text = result.phone
					binding.phone.setOnClickListener { startNewPhoneIntent(result.phone!!, this@DetailActivity) }
				}
				else binding.phone.visibility = View.GONE


				if(result.www != null && result.www != ""){
					binding.www.text = result.www
					binding.www.setOnClickListener { startNewWebIntent(result.www!!, this@DetailActivity) }
				}
				else binding.www.visibility = View.GONE


				if(result.img != null || result.img != ""){
					Picasso
						.get()
						.load(resources.getString(R.string.base_url) + result.img)
						.fit()
						.into(binding.imageView)
				}
				else{
					binding.imageView.setImageResource(R.drawable.no_image_placeholder)
				}



			}

			override fun onFailure(call: Call<MutableList<CompanyDetailItem>>, t: Throwable) {
				val contentView = findViewById<TextView>(R.id.contentView)
				contentView.text = applicationContext.getString(R.string.json_error)
			}
		})
	}

	fun startNewPhoneIntent(phone: String, activity: Activity) =
	phone.also {
		Intent(Intent.ACTION_DIAL).also { intent ->
			intent.data = Uri.parse("tel:$it")
			activity.startActivity(intent)
		}
	}

	fun startNewWebIntent(url: String, activity: Activity) =
	url.also {
		var actualUrl = it
		if (!it.startsWith("http://") && !it.startsWith("https://"))
			actualUrl = "http://$it"
		Intent(Intent.ACTION_VIEW, Uri.parse(actualUrl)).also { intent ->
			activity.startActivity(intent)
		}
	}




}
