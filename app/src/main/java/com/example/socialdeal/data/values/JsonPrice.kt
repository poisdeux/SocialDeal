package com.example.socialdeal.data.values

import com.google.gson.annotations.SerializedName

data class JsonPrice (
  @SerializedName("amount" ) val amount: Int?,
  @SerializedName("currency" ) val currency: JsonCurrency?
)