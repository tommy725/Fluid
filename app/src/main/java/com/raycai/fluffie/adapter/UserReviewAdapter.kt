package com.raycai.fluffie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.dam.bestexpensetracker.data.constant.Const
import com.raycai.fluffie.R
import com.raycai.fluffie.data.model.Product
import com.raycai.fluffie.data.model.UserReview
import com.raycai.fluffie.http.response.ProductReviewsResponse
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat

class UserReviewAdapter(private val reviews: List<ProductReviewsResponse.ProductReview>) :
    RecyclerView.Adapter<UserReviewAdapter.ViewHolder>() {

    open var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.li_review, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(reviews[position], listener)
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        private val cvRoot: CardView = itemView.findViewById(R.id.cvRoot)
        private val ivProductImg: ImageView = cvRoot.findViewById(R.id.ivProductImg)
        private val tvName: TextView = cvRoot.findViewById(R.id.tvName)
        private val tvBrand: TextView = cvRoot.findViewById(R.id.tvBrand)
        private val ivProfilePic: ImageView = cvRoot.findViewById(R.id.ivProfilePic)
        private val tvRating: TextView = cvRoot.findViewById(R.id.tvRating)
        private val tvTimeAgo: TextView = cvRoot.findViewById(R.id.tvTimeAgo)


        fun setData(ur: ProductReviewsResponse.ProductReview, listener: Listener?) {
//            Picasso.with(itemView.context).load("ur.").
            tvName.text = ur.title
            tvBrand.text = ur.desc
            tvRating.text = "${ur.rating}"
            val dobDisplay = SimpleDateFormat(Const.DATE_FORMAT_DISPLAY).format(ur.created_at)
            tvTimeAgo.text = dobDisplay
            cvRoot.setOnClickListener {
                listener?.onUserReviewClicked(ur)
            }
        }
    }

    interface Listener {
        fun onUserReviewClicked(ur: ProductReviewsResponse.ProductReview)
    }
}