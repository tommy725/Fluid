package com.raycai.fluffie.ui.home.product.review.attachment

import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.dam.bestexpensetracker.util.AppLog
import com.raycai.fluffie.HomeActivity
import com.raycai.fluffie.app.MyApp
import com.raycai.fluffie.base.BaseFragment
import com.raycai.fluffie.data.model.ReviewAttachment
import com.raycai.fluffie.data.model.SelectedFile
import com.raycai.fluffie.databinding.FragmentReviewAttachmentBinding
import java.io.InputStream

class ReviewAttachmentFragment : BaseFragment() {

    private val TAG = ReviewAttachmentFragment::class.java.simpleName
    private lateinit var binding: FragmentReviewAttachmentBinding

    private val a = ReviewAttachment()

    companion object {
        fun newInstance(a: ReviewAttachment): Fragment {
            val bundle = Bundle()
            bundle.putInt("type", a.type)
            bundle.putString("url", a.url)
            bundle.putBoolean("enable_remove", a.enableRemove)

            val fragment = ReviewAttachmentFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentReviewAttachmentBinding.inflate(inflater)
        binding.fragment = this
        binding.lifecycleOwner = this
        initData()
        return binding.root
    }

    private fun initData() {
        val type: Int = arguments?.getInt("type")!!
        val url: String = arguments?.getString("url")!!
        val enableRemove: Boolean = arguments?.getBoolean("enable_remove")!!
        log("attachment type: $type")
        log("attachment url: $url")
        log("enableRemove: $enableRemove")
        a.type = type
        a.url = url
        a.enableRemove = enableRemove

        if (a.isAddAttachment()) {
            binding.ivClose.visibility = View.GONE
            binding.layoutAdd.visibility = View.VISIBLE
        } else {
            if (a.enableRemove) {
                binding.ivClose.visibility = View.VISIBLE
            } else {
                binding.ivClose.visibility = View.GONE
            }
            binding.layoutAdd.visibility = View.GONE
        }

        if (!TextUtils.isEmpty(a.url)){
            if (a.isImage() || a.isVideo()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (a.isImage()) {
                        readImage(Uri.parse(a.url)) {
                            binding.ivBg.setImageBitmap(it)
                        }
                    } else {
                        createVideoThumb(Uri.parse(a.url)) {
                            Glide.with(requireContext()).load(it).into(binding.ivBg)
                        }
                    }
                } else {
                    Glide.with(this)
                        .load(Uri.parse(url))
                        .into(binding.ivBg)
                }
            }
        }
    }

    private fun createVideoThumb(uri: Uri, onReceived: (picturePath: String) -> Unit) {
        requireContext().contentResolver.let { contentResolver: ContentResolver ->
            contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                .let {
                    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                    val cursor = contentResolver.query(uri, filePathColumn, null, null, null)
                    cursor?.moveToFirst()

                    val columnIndex = cursor?.getColumnIndex(filePathColumn[0])
                    val picturePath = cursor?.getString(columnIndex!!)
                    cursor?.close()
                    onReceived(picturePath!!)
                }
        }
    }

    private fun readImage(uri: Uri, onReceived: (mBitmap: Bitmap) -> Unit) {
        requireContext().contentResolver.let { contentResolver: ContentResolver ->
            contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            contentResolver.openInputStream(uri)?.use { inputStream: InputStream? ->
                if (inputStream != null) {
                    val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)
                    onReceived(bitmap)
                }
            }
        }
    }

    fun onCloseClicked(view: View) {
        var removeAttachment: SelectedFile? = null
        MyApp.instance.selectedFiles.forEach {
            if (it.url == a.url) {
                removeAttachment = it
            }
        }

        (activity as HomeActivity).reviewAttachmentListener?.onRemoveAttachment(removeAttachment)
    }

    fun onAttachClicked(view: View) {
        (activity as HomeActivity).reviewAttachmentListener?.onClicked(a)
    }

    private fun log(msg: String) {
        AppLog.log(TAG, msg)
    }

}