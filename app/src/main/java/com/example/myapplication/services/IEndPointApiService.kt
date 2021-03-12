package com.example.myapplication.services
import com.example.myapplication.R
import com.example.myapplication.models.CompanyDetailItem
import com.example.myapplication.models.CompanyListItem
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import kotlin.coroutines.coroutineContext

interface IEndPointApiService {
    @GET("test.php")
    fun getCompanies(): Call<MutableList<CompanyListItem>>

    @GET("test.php")
    fun getCompanyDetail(@Query("id") id: Long): Call<MutableList<CompanyDetailItem>>



    companion object {
        fun create(): IEndPointApiService {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://lifehack.studio/test_task/")
                .build()

            return retrofit.create(IEndPointApiService::class.java)
        }
    }
}