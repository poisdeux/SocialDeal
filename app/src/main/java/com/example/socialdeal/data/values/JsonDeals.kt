package com.example.socialdeal.data.values

import com.google.gson.annotations.SerializedName

data class JsonDeals(
    @SerializedName("num_deals") val numDeals: Int?,
    @SerializedName("deals") val deals: List<JsonDeal>?
)