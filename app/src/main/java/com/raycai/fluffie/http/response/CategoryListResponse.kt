package com.raycai.fluffie.http.response

class CategoryListResponse(status: Boolean) : BaseResponse(status) {
    var data: MutableList<Category>? = null

    class Category {
        var _id = ""
        var id = ""
        var master_category = ""
        var created_at = ""
        var updated_at = ""
        var refind_category: MutableList<RefinedCategory>? = null
    }

    class RefinedCategory {
        var _id = ""
        var id = ""
        var refined_category = ""
        var master_category_id = ""
        var created_at = ""
        var updated_at = ""
    }
}