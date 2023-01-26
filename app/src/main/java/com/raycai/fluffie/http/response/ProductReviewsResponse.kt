package com.raycai.fluffie.http.response

class ProductReviewsResponse(status: Boolean) : BaseResponse(status) {
    var data: MutableList<ProductReview>? = null

    class ProductReview {
        var _id = ""
        var id = ""
        var review_id = ""
        var prod_id = ""
        var title = ""
        var name = ""
        var desc = ""
        var rating = 0.0
        var country = ""
        var sentiment = false
        var labels: MutableList<Level>? = null
        var created_at = ""
        var updated_at = ""
    }

    class Level {
        var _id = ""
        var part = ""
        var level = ""
    }
}