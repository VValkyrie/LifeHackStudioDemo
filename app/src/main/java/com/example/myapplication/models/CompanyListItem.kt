package com.example.myapplication.models

import android.graphics.Bitmap

data class CompanyListItem(val id: Long,
                           val name: String?,
                           val img: String?,
                           var image: Bitmap?)