package com.example.socialdeal.data.values

import com.google.gson.annotations.SerializedName

data class JsonCurrency (
  @SerializedName("symbol" ) val symbol : String?,
  @SerializedName("code" ) val code : String?
)