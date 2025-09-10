package com.example.socialdeal.data.values

import com.google.gson.annotations.SerializedName

data class JsonDeal(
  @SerializedName("unique") val unique: String?,
  @SerializedName("title") val title: String?,
  @SerializedName("image") val image: String?,
  @SerializedName("sold_label") val soldLabel: String?,
  @SerializedName("company") val company: String?,
  @SerializedName("city") val city: String?,
  @SerializedName("prices") val prices: JsonPrices?
)