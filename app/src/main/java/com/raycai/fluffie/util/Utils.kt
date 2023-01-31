package com.raycai.fluffie.util

import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Base64
import com.dam.bestexpensetracker.data.constant.Const
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class Utils {

    companion object {

        fun convertDate(longDate: Long): String {
            val sdfDate = SimpleDateFormat(Const.DATE_FORMAT_DISPLAY)
            return "${sdfDate.format(Date(longDate))}"
        }

        fun convertAmount(amount: Double): String {
            val formatter: NumberFormat = DecimalFormat("#,###.00")
            return formatter.format(amount)
        }

        fun convertAmount(displayAmount: String): Double {
            return try {
                displayAmount.replace(",", "").toDouble()
            } catch (e: Exception) {
                0.0
            }
        }

        fun toDisplayTime(longTime: Long): String {
            val date = Date(longTime)
            val sdfTime = SimpleDateFormat(Const.TIME_FORMAT_DISPLAY)
            return "${sdfTime.format(date)}"
        }

        fun toDisplayDateTime(longDate: Long): String {
            val date = Date(longDate)
            val sdfDate = SimpleDateFormat(Const.DATE_FORMAT_DISPLAY)
            val sdfTime = SimpleDateFormat(Const.TIME_FORMAT_DISPLAY)
            return "${sdfDate.format(date)} at ${sdfTime.format(date)}"
        }

        fun getScreenWidth(): Int {
            return Resources.getSystem().displayMetrics.widthPixels
        }

        fun getScreenHeight(): Int {
            return Resources.getSystem().displayMetrics.heightPixels
        }

        fun isInternetOn(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val network = connectivityManager.activeNetwork ?: return false
                val activeNetwork =
                    connectivityManager.getNetworkCapabilities(network) ?: return false
                return when {
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            } else {
                @Suppress("DEPRECATION") val networkInfo =
                    connectivityManager.activeNetworkInfo ?: return false
                @Suppress("DEPRECATION")
                return networkInfo.isConnected
            }
        }

        fun isEmail(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun encode(txt: String): String {
            var e = txt
            val times = 2 * 2
            for (i in 1..times) {
                e = hexToBase64(e)
            }
            return e
        }

        fun decode(encText: String): String {
            var e = encText
            val times = 2 * 2
            for (i in 1..times) {
                e = base64ToHex(e)
            }
            return e
        }

        private fun hexToBase64(txt: String): String {
            val data: ByteArray = txt.toByteArray()
            return Base64.encodeToString(data, Base64.NO_PADDING)
        }

        private fun base64ToHex(base64Txt: String): String {
            val data: ByteArray = Base64.decode(base64Txt, Base64.NO_PADDING)
            return String(data)
        }

        fun roundOffDecimal(number: Double): Double? {
            val df = DecimalFormat("#.#")
            df.roundingMode = RoundingMode.CEILING
            return df.format(number).toDouble()
        }
    }
}