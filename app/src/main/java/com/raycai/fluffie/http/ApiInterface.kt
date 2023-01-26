package com.raycai.fluffie.http

import com.raycai.fluffie.http.response.CategoryListResponse
import com.raycai.fluffie.http.response.ProductDetailResponse
import com.raycai.fluffie.http.response.ProductListResponse
import com.raycai.fluffie.http.response.ProductReviewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("/products")
    fun getProductList(): Call<ProductListResponse>

    @GET("/products/{product_id}")
    fun getProductDetail(@Path("product_id") product_id: String): Call<ProductDetailResponse>

    @GET("/review/{product_id}")
    fun getProductReview(@Path("product_id") product_id: String): Call<ProductReviewsResponse>

    @GET("/review")
    fun getAllReviews(): Call<ProductReviewsResponse>

    @GET("/master_categories")
    fun getCategoryList(): Call<CategoryListResponse>

}