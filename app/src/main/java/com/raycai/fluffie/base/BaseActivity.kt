package com.raycai.fluffie.base

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.raycai.fluffie.MainActivity
import com.raycai.fluffie.R

open class BaseActivity : AppCompatActivity() {

    protected open val TAG = BaseActivity::class.java.simpleName

    private lateinit var progressDialog: ProgressDialog
    protected var appFlow = arrayListOf<String>()
    protected val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = ProgressDialog(this)

        supportFragmentManager.addOnBackStackChangedListener {
            Log.i(TAG, "Back stack count: ${supportFragmentManager.backStackEntryCount}")
        }
    }


    fun showProgress() {
        try {
            if (progressDialog.isShowing) {
                progressDialog.dismiss()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }


        progressDialog.setMessage("Please wait")
        progressDialog.setCancelable(false)
        progressDialog.show()
    }

    fun hideProgress() {
        progressDialog.dismiss()
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    // <editor-fold defaultstate="collapsed" desc="Common Fragment Functions">
    protected fun getCurrentFragment(): Fragment? {
        return supportFragmentManager.findFragmentById(R.id.fragmentContainer)
    }

    protected fun loadFragment(fragment: Fragment, enterAnimation: Boolean) {
        var ft = supportFragmentManager
            .beginTransaction()

        if (enterAnimation) {
            ft.setCustomAnimations(
                R.anim.fragment_enter,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.fragment_exit
            )
        } else {
            ft.setCustomAnimations(
                R.anim.fade_in,
                R.anim.fade_out
            )
        }

        ft.replace(R.id.fragmentContainer, fragment)
        ft.commit()

        appFlow.add(fragment.javaClass.canonicalName)
        Log.i(TAG, "loadFragment: flow_size: ${appFlow.size} | ${fragment.javaClass.canonicalName}")
    }

    protected fun loadFragmentFromBottom(fragment: Fragment) {
        var ft = supportFragmentManager
            .beginTransaction()

        ft.setCustomAnimations(
            R.anim.fragment_enter_from_bottom,
            R.anim.fade_out,
            R.anim.fade_in,
            R.anim.fragment_exit_to_bottom
        )
        ft.replace(R.id.fragmentContainer, fragment)
        ft.commit()

        appFlow.add(fragment.javaClass.canonicalName)
        Log.i(TAG, "loadFragment: flow_size: ${appFlow.size} | ${fragment.javaClass.canonicalName}")
    }
    // </editor-fold>


    fun showToast(msg:String){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}