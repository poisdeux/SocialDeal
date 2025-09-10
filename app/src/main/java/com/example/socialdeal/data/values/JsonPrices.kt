package com.example.socialdeal.data.values

import com.google.gson.annotations.SerializedName

data class JsonPrices (
  @SerializedName("price" ) val price: JsonPrice?,
  @SerializedName("from_price" ) val fromPrice: JsonPrice?,
  @SerializedName("price_label" ) val priceLabel: String?,
  @SerializedName("discount_label" ) val discountLabel: String?
)