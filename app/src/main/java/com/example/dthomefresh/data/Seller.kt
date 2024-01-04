package com.example.dthomefresh.data

data class Seller(

    var uid: String = "",
    var ownerName: String = "",
    var brandName: String = "",
    var phoneNumber: String = "",
    var whatsappNumber: String = "",
    var address: String = "",
    var nearbyLandmark: String = "",
    var items: List<Item>? = null

)