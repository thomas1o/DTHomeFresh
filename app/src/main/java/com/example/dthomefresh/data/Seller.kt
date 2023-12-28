package com.example.dthomefresh.data

data class Seller(

    var uid: String = "",
    var name: String = "",
    var contact: String = "",
    var address: String = "",
    var nearbyLandmark: String = "",
    var items: List<Item>? = null

)