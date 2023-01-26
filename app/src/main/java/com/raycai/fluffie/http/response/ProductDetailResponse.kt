package com.raycai.fluffie.http.response

class ProductDetailResponse(status: Boolean) : BaseResponse(status) {
    var data: ProductDetail? = null

    class ProductDetail {
        var _id = ""
        var id = ""
        var title = ""
        var slug = ""
        var prod_link = ""
        var price = 0
        var img = ""
        var details = ""
        var category: MutableList<Category>? = null
        var refined_category: RefinedCategory? = null
        var master_category_id: RefinedCategory.MasterCategory? = null
        var brand: Brand? = null
        var key_benefits: MutableList<KeyBenefit>? = null
        var ingredient = ""
        var prod_claims: MutableList<String>? = null
        var created_at = ""
        var updated_at = ""
        var updatedAt = ""

        class Category {
            var _id = ""
            var id = ""
            var product_category = ""
            var created_at = ""
            var updated_at = ""
        }

        class Brand {
            var _id = ""
            var id = ""
            var brand = ""
            var created_at = ""
            var updated_at = ""
        }

        class RefinedCategory {
            var _id = ""
            var id = ""
            var refined_category = ""
            var master_category_id: MasterCategory? = null
            var created_at = ""
            var updated_at = ""

            class MasterCategory {
                var _id = ""
                var id = ""
                var master_category = ""
                var created_at = ""
                var updated_at = ""
            }
        }

        class KeyBenefit {
            var _id = ""
            var id = ""
            var benefit = ""
            var created_at = ""
            var updated_at = ""
        }
    }
}