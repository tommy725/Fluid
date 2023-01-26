package com.raycai.fluffie.base

import android.R.attr.*
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.icu.util.Calendar
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.dam.bestexpensetracker.data.constant.Const
import com.raycai.fluffie.HomeActivity
import com.raycai.fluffie.MainActivity
import com.raycai.fluffie.R
import com.raycai.fluffie.util.Utils
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.log


open class BaseFragment : Fragment() {


    private var toast: Toast? = null
    protected var dialog: Dialog? = null

    protected val handler = Handler(Looper.getMainLooper())

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    protected fun addDelay(millis: Long, onCompleted: () -> Unit) {
        Thread {
            kotlin.run {
                Thread.sleep(millis)
                handler.post { onCompleted() }
            }
        }.start()
    }

    protected fun isSafe(): Boolean {
        return !(this.isRemoving || this.activity == null || this.isDetached || !this.isAdded || this.view == null)
    }

    protected fun showPrivacyPolicy() {
        val url = "https://www.dreamappmkr.com/money-records-expense-tracker-privacy-policy/"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    protected fun showTerms() {
        val url = "https://www.dreamappmkr.com/money-records-expense-tracker-terms-and-conditions/"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    protected fun toDisplayTimeFormat(date: Date): String {
        val sf = SimpleDateFormat(Const.TIME_FORMAT_DISPLAY)
        return sf.format(date).uppercase()
    }

    protected fun toDisplayDateFormat(date: Date): String {
        val sf = SimpleDateFormat(Const.DATE_FORMAT_DISPLAY)
        return sf.format(date).uppercase()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    protected fun showTimePicker(date: Date, onTimePicked: (date: Date) -> Unit) {
        val calendar = Calendar.getInstance()
        calendar.time = date
        val hour = calendar[Calendar.HOUR_OF_DAY]
        val minute = calendar[Calendar.MINUTE]

        TimePickerDialog(requireContext(), { timePicker, selectedHour, selectedMinute ->
            calendar.set(Calendar.HOUR_OF_DAY, selectedHour)
            calendar.set(Calendar.MINUTE, selectedMinute)
            onTimePicked(calendar.time)
        }, hour, minute, false).show()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    protected fun showDatePicker(date: Date, onDatePicked: (date: Date) -> Unit) {
        val calendar = Calendar.getInstance()
        calendar.time = date

        val date = DatePickerDialog.OnDateSetListener { view, year, month, day ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            onDatePicked(calendar.time)
        }

        DatePickerDialog(
            requireContext(), date,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    protected fun overrideBackPress(onBackPressed: () -> Unit) {
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        })
    }

    override fun onDestroyView() {
        if (dialog != null && dialog!!.isShowing) {
            dialog!!.dismiss()
        }
        super.onDestroyView()
    }

    protected fun showMsgDialog(title: String, msg: String, pBtn: String, okCLicked: () -> Unit) {
        hideDialogIfShowing()
        dialog = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(msg)
            .setCancelable(false)
            .setPositiveButton(pBtn) { dialog, _ ->
                dialog.dismiss()
                okCLicked()
            }.create()
        dialog!!.show()


        hideDialogIfShowing()

        dialog = Dialog(requireContext())
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCancelable(false)
        dialog?.setContentView(R.layout.dialog_msg)

        val tvTitle = dialog?.findViewById(R.id.tvTitle) as TextView
        val tvDesc = dialog?.findViewById(R.id.tvDesc) as TextView
        val btnP = dialog?.findViewById(R.id.btnP) as TextView
        tvTitle.text = title
        tvDesc.text = msg
        btnP.text = pBtn
        btnP.setOnClickListener {
            dialog?.dismiss()
            okCLicked()
        }

        dialog?.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.window!!.setLayout(
            Utils.getScreenWidth(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog?.show()
    }

    protected fun showMsgDialog(title: String, msg: String, okCLicked: () -> Unit) {
        showMsgDialog(title, msg, "OK") {
            okCLicked()
        }
    }

    protected fun showConfirmDialog(
        title: String,
        msg: String,
        pBtn: String,
        nBtn: String,
        posClicked: () -> Unit,
        negClicked: () -> Unit
    ) {
        hideDialogIfShowing()

        dialog = Dialog(requireContext())
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCancelable(false)
        dialog?.setContentView(R.layout.dialog_options)

        val tvTitle = dialog?.findViewById(R.id.tvTitle) as TextView
        val tvDesc = dialog?.findViewById(R.id.tvDesc) as TextView
        val btnP = dialog?.findViewById(R.id.btnP) as TextView
        val btnN = dialog?.findViewById(R.id.btnN) as TextView
        tvTitle.text = title
        tvDesc.text = msg
        btnP.text = pBtn
        btnP.setOnClickListener {
            dialog?.dismiss()
            posClicked()
        }

        btnN.text = nBtn
        btnN.setOnClickListener {
            dialog?.dismiss()
            negClicked()
        }

        dialog?.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.window!!.setLayout(
            Utils.getScreenWidth(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog?.show()
    }

    private fun hideDialogIfShowing() {
        try {
            if (dialog != null && dialog!!.isShowing) {
                dialog!!.dismiss()
            }
        } catch (e: Exception) {
        }
    }

    fun showProgress() {
        hideDialogIfShowing()
        (activity as BaseActivity).showProgress()
    }

    fun hideProgress() {
        (activity as BaseActivity).hideProgress()
    }

    fun isInternetOn(): Boolean {
        return Utils.isInternetOn(requireContext())
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    fun showToast(msg: String) {
        if (toast != null) {
            toast?.cancel()
        }
        toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
        toast?.show()
    }

    val Int.pxToDp: Int
        get() = (this / Resources.getSystem().displayMetrics.density).toInt()

    val Int.dpToPx: Float
        get() = (this * Resources.getSystem().displayMetrics.density).toFloat()

    fun onBackPressed(){
        if(activity is HomeActivity){
            (activity as HomeActivity).onBackPressed()
        }else if (activity is MainActivity){
            (activity as MainActivity).onBackPressed()
        }
    }
}