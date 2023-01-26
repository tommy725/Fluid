package com.raycai.fluffie.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayout
import com.raycai.fluffie.R
import com.raycai.fluffie.databinding.TvProductLabelBinding
import com.raycai.fluffie.http.response.ProductListResponse
import com.squareup.picasso.Picasso
import org.w3c.dom.Text


class ProductAdapter(private val products: List<ProductListResponse.ProductDetail>) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    open var listener: Listener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.li_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(products[position], listener)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        private val cvRoot: CardView = itemView.findViewById(R.id.cvRoot)
        private val ivProductImg: ImageView = itemView.findViewById(R.id.ivProductImg)
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvBrand: TextView = itemView.findViewById(R.id.tvBrand)
        private val tvRating: TextView = itemView.findViewById(R.id.tvRating)
        private val tvReview: TextView = itemView.findViewById(R.id.tvReview)
        private val flLabels: FlexboxLayout = itemView.findViewById(R.id.flLabels)
        private val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)

        fun setData(p: ProductListResponse.ProductDetail, listener: Listener?) {
            Picasso.with(itemView.context).load(p.img).into(ivProductImg)
            tvName.text = p.title
            if (p.brand != null) {
                tvBrand.text = p.brand!!.brand
            } else {
                tvBrand.text = ""
            }

            tvRating.text = "${Math.round(p.rating)}"
            tvReview.text = "${p.total_reviews} reviews"
            tvPrice.text = "${p.price}"

            val layoutInflater: LayoutInflater = itemView.context.getSystemService (Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            if (p.labels != null) {
                p.labels!!.forEach {
                    val tvLabel = TvProductLabelBinding.inflate(layoutInflater)
                    tvLabel.root.text = it.label
                    flLabels.addView(tvLabel.root)
                }
            }

//            ivProductImg.setim setImageResource(p.imgRes)
//            tvName.text = p.name
//            tvBrand.text = p.brand
//            tvRating.text = "${p.rating}"
//            tvReview.text = "${p.reviews} reviews"
//            tvPrice.text = p.price

            cvRoot.setOnClickListener {
                listener?.onProductClicked(p)
            }
        }
    }

    interface Listener {
        fun onProductClicked(p: ProductListResponse.ProductDetail)
    }
}