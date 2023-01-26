package com.raycai.fluffie.data.model

import com.raycai.fluffie.R

class Product {

    var imgRes: Int = 0
    var name: String? = null
    var brand: String? = null
    var rating: String? = null
    var reviews: Int? = null
    var price: String? = null


    open fun tempProducts() : ArrayList<Product>{
        val products = ArrayList<Product>()

        val p1 = Product()
        p1.name = "A-Passioni™ Retinol Cream"
        p1.imgRes = R.drawable.product_1
        p1.brand = "Drunk Elephant"
        p1.rating = "4.8"
        p1.reviews = 415
        p1.price = "$88 NZD"

        val p2 = Product()
        p2.name = "Wrinkle Correction Serum"
        p2.imgRes = R.drawable.product_2
        p2.brand = "Olay"
        p2.rating = "4.7"
        p2.reviews = 416
        p2.price = "$67 NZD"

        val p3 = Product()
        p3.name = "The Power Couple"
        p3.imgRes = R.drawable.product_3
        p3.brand = "Sunday Riley"
        p3.rating = "4.6"
        p3.reviews = 417
        p3.price = "$101 NZD"

        val p4 = Product()
        p4.name = "Truth serum"
        p4.imgRes = R.drawable.product_4
        p4.brand = "Ole Henriksen"
        p4.rating = "4.5"
        p4.reviews = 418
        p4.price = "$58 NZD"

        products.add(p1)
        products.add(p2)
        products.add(p3)
        products.add(p4)

        return products
    }
}