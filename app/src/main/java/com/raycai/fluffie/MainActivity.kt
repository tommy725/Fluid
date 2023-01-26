package com.raycai.fluffie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.raycai.fluffie.base.BaseActivity
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

class MainActivity : BaseActivity() {

    override val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val prefs = Prefs(this)

        if (prefs.loggedIn){
            startHomeActivity()
        }else{
            if (FirebaseAuth.getInstance().currentUser != null){
                Log.i(TAG, "onCreate: Log out current user.")
                FirebaseAuth.getInstance().signOut()
            }

            loadSplashFragment()
        }
    }

    private fun startHomeActivity(){
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    override fun onBackPressed() {
        when (getCurrentFragment()) {
            is PhoneLoginFragment, is ResetPwFragment, is PwLoginFragment, is CreateAccFragment -> {
                loadPreLoginFragment(false)
            }
            is UpdatePwFragment -> {
                loadResetPasswordFragment(false)
            }
            is TermsFragment -> {
                when (appFlow[appFlow.size - 2]) {
                    PhoneLoginFragment::class.java.canonicalName -> {
                        loadPhoneLoginFragment(false)
                    }
                    PwLoginFragment::class.java.canonicalName -> {
                        loadPasswordLoginFragment(false)
                    }
                    CreateAccFragment::class.java.canonicalName -> {
                        loadCreateAccFragment(false)
                    }
                }
            }
            is CreatePwFragment -> {
                loadCreateAccFragment(false)
            }
            is CreateAccTwoFragment -> {
                loadCreatePasswordFragment(false)
            }
            is CreateAccThreeFragment -> {
                loadCreateAccountTwoFragment(false)
            }
            is LoginAnimFragment -> {
                //do nothing
            }
            is PreLoginFragment -> {
                finish()
            }
            else -> {
                super.onBackPressed()
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Fragment Loads">
    fun loadUpdatePasswordFragment(isEnter: Boolean) {
        if (getCurrentFragment() !is UpdatePwFragment) {
            loadFragment(UpdatePwFragment(), isEnter)
        }
    }

    fun loadCreatePasswordFragment(isEnter: Boolean) {
        if (getCurrentFragment() !is CreatePwFragment) {
            loadFragment(CreatePwFragment(), isEnter)
        }
    }

    fun loadCreateAccountThreeFragment(isEnter: Boolean) {
        if (getCurrentFragment() !is CreateAccThreeFragment) {
            loadFragment(CreateAccThreeFragment(), isEnter)
        }
    }

    fun loadCreateAccountTwoFragment(isEnter: Boolean) {
        if (getCurrentFragment() !is CreateAccTwoFragment) {
            loadFragment(CreateAccTwoFragment(), isEnter)
        }
    }

    fun loadTermsFragment(isEnter: Boolean) {
        if (getCurrentFragment() !is TermsFragment) {
            loadFragment(TermsFragment(), isEnter)
        }
    }

    fun loadCreateAccFragment(isEnter: Boolean) {
        if (getCurrentFragment() !is CreateAccFragment) {
            loadFragment(CreateAccFragment(), isEnter)
        }
    }

    fun loadLoginAnimationFragment() {
        if (getCurrentFragment() !is LoginAnimFragment) {
            loadFragment(LoginAnimFragment(), true)
        }
    }

    fun loadResetPasswordFragment(isEnter: Boolean) {
        if (getCurrentFragment() !is ResetPwFragment) {
            loadFragment(ResetPwFragment(), isEnter)
        }
    }

    fun loadPasswordLoginFragment(isEnter: Boolean) {
        if (getCurrentFragment() !is PwLoginFragment) {
            loadFragment(PwLoginFragment(), isEnter)
        }
    }

    fun loadPhoneLoginFragment(isEnter: Boolean) {
        if (getCurrentFragment() !is PhoneLoginFragment) {
            loadFragment(PhoneLoginFragment(), isEnter)
        }
    }

    fun loadPreLoginFragment(isEnter: Boolean) {
        if (getCurrentFragment() !is PreLoginFragment) {
            loadFragment(PreLoginFragment(), isEnter)
        }
    }

    private fun loadSplashFragment() {
        if (getCurrentFragment() !is SplashFragment) {
            loadFragment(SplashFragment(), true)
        }
    }
    // </editor-fold>
}