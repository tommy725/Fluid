package com.raycai.fluffie

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
import com.bumptech.glide.manager.SupportRequestManagerFragment
import com.raycai.fluffie.app.MyApp
import com.raycai.fluffie.base.BaseActivity
import com.raycai.fluffie.data.model.*
import com.raycai.fluffie.databinding.ActivityHomeBinding
import com.raycai.fluffie.http.response.CategoryListResponse
import com.raycai.fluffie.http.response.ProductDetailResponse
import com.raycai.fluffie.ui.bottomsheet.CommentBottomSheetDialog
import com.raycai.fluffie.ui.home.HomeFragment
import com.raycai.fluffie.ui.home.browse.BrowseFragment
import com.raycai.fluffie.ui.home.index.IndexFragment
import com.raycai.fluffie.ui.home.productfilter.ProductFilterFragment
import com.raycai.fluffie.ui.home.productsearch.ProductSearchFragment
import com.raycai.fluffie.ui.home.product.ProductFragment
import com.raycai.fluffie.ui.home.product.claims.ClaimsFragment
import com.raycai.fluffie.ui.home.product.prosorcon.ProsOrConFragment
import com.raycai.fluffie.ui.home.product.prosorcon.filter.ProsOrConFilterFragment
import com.raycai.fluffie.ui.home.product.review.ReviewFragment
import com.raycai.fluffie.ui.home.product.review.attachment.AttachmentListener
import com.raycai.fluffie.ui.home.product.review.attachment.ReviewAttachmentFragment
import com.raycai.fluffie.ui.home.product.reviewconfirm.ReviewConfirmFragment
import com.raycai.fluffie.ui.home.product.reviewexpand.ReviewExpandFragment
import com.raycai.fluffie.ui.home.product.reviews.ReviewsFragment
import com.raycai.fluffie.ui.home.product.summaries.SummariesFragment
import com.raycai.fluffie.ui.home.profile.ProfileFragment
import com.raycai.fluffie.ui.home.userfilter.UserFilterFragment
import com.raycai.fluffie.util.AppConst
import io.ak1.pix.PixFragment
import io.ak1.pix.helpers.PixEventCallback
import io.ak1.pix.helpers.addPixToActivity
import io.ak1.pix.models.Flash
import io.ak1.pix.models.Mode
import io.ak1.pix.models.Options
import io.ak1.pix.models.Ratio
import kotlin.math.log

class HomeActivity : BaseActivity() {

    override val TAG = HomeActivity::class.java.simpleName

    private lateinit var binding: ActivityHomeBinding
    private var white = 0

    var userFilter: UserFilter? = null
    var selectedProductId = ""
    var selectedProduct: ProductDetailResponse.ProductDetail? = null
    var selectedCon: Con2? = null
    var selectedPros: Pros? = null
    var selectedProductCategory: CategoryListResponse.Category? = null
    var categoryList = ArrayList<CategoryListResponse.Category>()

    var reviewAttachmentListener: AttachmentListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        binding.activity = this
        binding.lifecycleOwner = this
        setContentView(binding.root)
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentLifecycleCallbacks, true)
        initComponents()
        loadProductSearchFragment(true)
        //loadProductSearchFragment(true)
        //loadBrowseFragment(true)

        //selectedProduct = Product().tempProducts()[2]
        //loadProductFragment(true)
        //loadReviewFragment(true)
        //loadProfileFragment(true)
        //loadReviewExpandFragment(true)
        //loadProsOrConFragment(true);
    }

    private fun initComponents() {
        white = ContextCompat.getColor(this, R.color.white)

        //init user filter if not initialized
        if (userFilter == null) {
            userFilter = UserFilter()
            userFilter?.skinType = AppConst.defaultSkinType
        }
    }

    override fun onBackPressed() {
        val cF = getCurrentFragment()

        if (cF is ReviewFragment) {
            //call this method to stop all recordings and playbacks
            cF.stopRecordAndPlayback()
        }

        when (cF) {
            is ProductFilterFragment, is UserFilterFragment, is ProductFragment -> {
                loadBrowseFragment(false)
            }
            is ProsOrConFragment, is ReviewFragment, is ReviewExpandFragment -> {
                loadProductFragment(false)
            }
            is ProsOrConFilterFragment -> {
                loadProsOrConFragment(false)
            }
            is ReviewConfirmFragment -> {
                loadReviewFragment(false)
            }
            is ProductSearchFragment -> {
                loadBrowseFragment(false)
            }
            else -> {
                super.onBackPressed()
            }
        }
    }

    override fun onDestroy() {
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentLifecycleCallbacks)
        super.onDestroy()
    }

    fun setBottomNavigationVisibility(isVisible: Boolean) {
        log("setBottomNavigationVisibility() isVisible: $isVisible")
        if (isVisible) {
            handler.postDelayed({
                binding.layoutBottomNav.visibility = View.VISIBLE
            }, 300)
        } else {
            binding.layoutBottomNav.visibility = View.GONE
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Fragment Lifestyle Callbacks">
    private var fragmentLifecycleCallbacks = object : FragmentLifecycleCallbacks() {
        override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
            log("onFragmentAttached() $f")
            if (f !is SupportRequestManagerFragment) {
                //bottom navigation visibility
                if (f is HomeFragment
                    || f is ProductSearchFragment
                    || f is BrowseFragment
                    || f is ProductFragment
                    || f is ClaimsFragment
                    || f is SummariesFragment
                    || f is ReviewsFragment
                    || f is IndexFragment
                    || f is ProfileFragment){
                    setBottomNavigationVisibility(true)
                }else{
                    setBottomNavigationVisibility(false)
                }
            }
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Bottom navigation functions">
    fun onHomeClicked(view: View) {
        view.hideKeyboard()
        if (getCurrentFragment() is HomeFragment) {
            loadHomeFragment(false)
        } else {
            loadHomeFragment(true)
        }
    }

    fun onSearchClicked(view: View) {
        view.hideKeyboard()
        if (getCurrentFragment() is ProductSearchFragment) {
            loadProductSearchFragment(false)
        } else {
            loadProductSearchFragment(true)
        }
    }

    fun onAddClicked(view: View) {
        view.hideKeyboard()

    }

    fun onIndexClicked(view: View) {
        view.hideKeyboard()
        loadIndexFragment(true)
    }

    fun onProfileClicked(view: View) {
        view.hideKeyboard()
        loadProfileFragment(true)
    }

    private fun resetBottomNavigation() {
        val defaultColor = ContextCompat.getColor(this, R.color.bottom_nav_default)
        setBottomNavIconColor(binding.ivHome, defaultColor)
        setBottomNavIconColor(binding.ivBrowse, defaultColor)
        setBottomNavIconColor(binding.ivIndex, defaultColor)
        setBottomNavIconColor(binding.ivProfile, defaultColor)
        binding.tvHome.setTextColor(defaultColor)
        binding.tvBrowse.setTextColor(defaultColor)
        binding.tvIndex.setTextColor(defaultColor)
        binding.tvProfile.setTextColor(defaultColor)
    }

    private fun setBottomNavIconColor(imageView: ImageView, color: Int) {
        imageView.setColorFilter(color, android.graphics.PorterDuff.Mode.SRC_IN)
    }

    private fun selectBottomNavTab(idx: Int) {
        resetBottomNavigation()
        when (idx) {
            0 -> {
                setBottomNavIconColor(binding.ivHome, white)
                binding.tvHome.setTextColor(white)
            }
            1 -> {
                setBottomNavIconColor(binding.ivBrowse, white)
                binding.tvBrowse.setTextColor(white)
            }
            2 -> {
                setBottomNavIconColor(binding.ivIndex, white)
                binding.tvIndex.setTextColor(white)
            }
            3 -> {
                setBottomNavIconColor(binding.ivProfile, white)
                binding.tvProfile.setTextColor(white)
            }
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Fragment Loads">
    fun loadReviewExpandFragment(isEnter: Boolean) {
        if (getCurrentFragment() !is ReviewExpandFragment) {
            setBottomNavigationVisibility(false)
            loadFragment(ReviewExpandFragment(), isEnter)
        }
    }

    fun loadReviewConfirmFragment(isEnter: Boolean) {
        if (getCurrentFragment() !is ReviewConfirmFragment) {
            setBottomNavigationVisibility(false)
            loadFragment(ReviewConfirmFragment(), isEnter)
        }
    }

    fun loadReviewFragment(isEnter: Boolean) {
        if (getCurrentFragment() !is ReviewFragment) {
            if (isEnter) {
                MyApp.instance.maxSelectableFiles = 5
                MyApp.instance.rating = 0.0
                MyApp.instance.selectedFiles.clear()
                MyApp.instance.recording = null
            }

            setBottomNavigationVisibility(false)
            loadFragment(ReviewFragment(), isEnter)
        }
    }

    fun loadProsOrConFilterFragment(isEnter: Boolean) {
        if (getCurrentFragment() !is ProsOrConFilterFragment) {
            loadFragment(ProsOrConFilterFragment(), isEnter)
        }
    }

    fun loadProsOrConFragment(isEnter: Boolean) {
        if (getCurrentFragment() !is ProsOrConFragment) {
            loadFragment(ProsOrConFragment(), isEnter)
        }
    }

    fun loadProductFragment(isEnter: Boolean) {
        if (getCurrentFragment() !is ProductFragment) {
            loadFragment(ProductFragment(), isEnter)
        }
    }

    fun loadProductSearchFragment(isEnter: Boolean) {
        selectBottomNavTab(1)
        if (getCurrentFragment() !is ProductSearchFragment) {
            loadFragment(ProductSearchFragment(), isEnter)
        }
    }

    fun loadUserFilterFragment() {
        if (getCurrentFragment() !is UserFilterFragment) {
            loadFragmentFromBottom(UserFilterFragment())
        }
    }

    fun loadProductFilterFragment() {
        if (getCurrentFragment() !is ProductFilterFragment) {
            loadFragmentFromBottom(ProductFilterFragment())
        }
    }

    private fun loadProfileFragment(isEnter: Boolean) {
        selectBottomNavTab(3)
        if (getCurrentFragment() !is ProfileFragment) {
            loadFragment(ProfileFragment(), isEnter)
        }
    }

    fun loadBrowseFragment(isEnter: Boolean) {
        selectBottomNavTab(1)
        if (getCurrentFragment() !is BrowseFragment) {
            loadFragment(BrowseFragment(), isEnter)
        }
    }

    private fun loadIndexFragment(isEnter: Boolean) {
        selectBottomNavTab(2)
        if (getCurrentFragment() !is IndexFragment) {
            loadFragment(IndexFragment(), isEnter)
        }
    }

    private fun loadHomeFragment(isEnter: Boolean) {
        selectBottomNavTab(0)
        if (getCurrentFragment() !is HomeFragment) {
            loadFragment(HomeFragment(), isEnter)
        }
    }
    // </editor-fold>

    private fun log(msg: String) {
        Log.i(TAG, msg)
    }
}