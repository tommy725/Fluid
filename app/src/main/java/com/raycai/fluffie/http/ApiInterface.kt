package com.raycai.fluffie.http

import com.raycai.fluffie.http.response.CategoryListResponse
import com.raycai.fluffie.http.response.ProductDetailResponse
import com.raycai.fluffie.http.response.ProductListResponse
import com.raycai.fluffie.http.response.ProductReviewsResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("/products/{brand_id}/{refined_category}")
    fun getProductList(@Path("brand_id") brand_id: String, @Path("refined_category") refined_category: String): Call<ProductListResponse>

    @GET("/products")
    fun getProductList(): Call<ProductListResponse>

    @GET("/products")
    fun getProductList(@Query("limit") limit: Int): Call<ProductListResponse>

    @GET("/products/{product_id}")
    fun getProductDetail(@Path("product_id") product_id: String): Call<ProductDetailResponse>

    @GET("/review/{product_id}")
    fun getProductReview(@Path("product_id") product_id: String): Call<ProductReviewsResponse>

    @GET("/review")
    fun getAllReviews(): Call<ProductReviewsResponse>

    @GET("/master_categories")
    fun getCategoryList(): Call<CategoryListResponse>

}