package com.remees.findmyip.feature_findip.data.remote.dto

import com.google.gson.annotations.SerializedName

data class IpInfoDto(
    val ip: String,
    val network: String,
    val version: String,
    val city: String,
    val region: String,
    @SerializedName("region_code")
    val regionCode: String,
    val country: String,
    @SerializedName("country_name")
    val countryName: String,
    @SerializedName("country_code")
    val countryCode: String,)
