package com.raycai.fluffie

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.raycai.fluffie.app.MyApp
import com.raycai.fluffie.base.BaseActivity
import com.raycai.fluffie.data.model.SelectedFile
import com.raycai.fluffie.data.prefs.Prefs
import com.raycai.fluffie.ui.onboarding.animation.LoginAnimFragment
import com.raycai.fluffie.ui.onboarding.createacc.CreateAccFragment
import com.raycai.fluffie.ui.onboarding.createaccthree.CreateAccThreeFragment
import com.raycai.fluffie.ui.onboarding.createacctwo.CreateAccTwoFragment
import com.raycai.fluffie.ui.onboarding.createpw.CreatePwFragment
import com.raycai.fluffie.ui.onboarding.phonelogin.PhoneLoginFragment
import com.raycai.fluffie.ui.onboarding.prelogin.PreLoginFragment
import com.raycai.fluffie.ui.onboarding.pwlogin.PwLoginFragment
import com.raycai.fluffie.ui.onboarding.resetpw.ResetPwFragment
import com.raycai.fluffie.ui.onboarding.terms.TermsFragment
import com.raycai.fluffie.ui.onboarding.updatepw.UpdatePwFragment
import com.raycai.fluffie.ui.splash.SplashFragment
import io.ak1.pix.helpers.PixEventCallback
import io.ak1.pix.helpers.addPixToActivity
import io.ak1.pix.models.Flash
import io.ak1.pix.models.Mode
import io.ak1.pix.models.Options
import io.ak1.pix.models.Ratio

class GalleryActivity : BaseActivity() {

    override val TAG = GalleryActivity::class.java.simpleName
    var prefs: Prefs? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        prefs = Prefs(this)
        loadGalleryFragment()
    }

    private fun loadGalleryFragment() {
        val options = Options().apply {
            ratio = Ratio.RATIO_AUTO
            count = MyApp.instance.maxSelectableFiles
            spanCount = 4
            path = "Fluffie/Camera"
            isFrontFacing = false
            mode = Mode.All
            flash = Flash.Auto
            preSelectedUrls = ArrayList<Uri>()
        }

        addPixToActivity(R.id.fragmentContainer, options) {
            when (it.status) {
                PixEventCallback.Status.SUCCESS -> {
                    for (uri in it.data) {
                        log("Uri: $uri")
                        val mimeType: String? = uri.let { returnUri -> contentResolver.getType(returnUri) }
                        log("Type: $mimeType" )

                        val stringUrl = "$uri"

                        val f = SelectedFile()
                        f.url = stringUrl
                        f.mimeType = mimeType

                        MyApp.instance.selectedFiles.add(f)
                    }

                    log("success")
                    finish()
                }
                PixEventCallback.Status.BACK_PRESSED -> {
                    log("back pressed")
                    finish()
                }
            }
        }
    }

    private fun log(msg: String) {
        Log.i(TAG, msg)
    }
}