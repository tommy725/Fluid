package com.raycai.fluffie.http.response

class ProductListResponse(status: Boolean) : BaseResponse(status) {
    var data: MutableList<ProductDetail>? = null

    class ProductDetail {
        var _id = ""
        var id = ""
        var title = ""
        var slug = ""
        var prod_link = ""
        var price = 0.0
        var img = ""
        var brand: Brand? = null
        var labels: MutableList<Label>? = null
        var filtered_labels: MutableList<FilteredLabel>? = null

        var rating = 0.0
        var total_reviews = 0
    }

    class Brand {
        var _id = ""
        var id = ""
        var brand = ""
        var created_at = ""
        var updated_at = ""
    }

    class Label {
        var _id = ""
        var id = ""
        var label = ""
        var display_label = ""
        var category = ""
        var created_at = ""
        var updated_at = ""
    }
    class FilteredLabel {
        var label = ""
        var reviews = 0
    }
}