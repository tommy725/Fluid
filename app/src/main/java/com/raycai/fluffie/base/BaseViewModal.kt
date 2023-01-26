package com.raycai.fluffie.base

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

open class BaseViewModal : ViewModel() {

//    val showProgress = MutableLiveData<Boolean>()

    protected fun getTodayDateRange(onReceived: (startDate: Date, endDate: Date) -> Unit) {
        val date = Date()

        //start day
        val startDayCal = Calendar.getInstance()
        startDayCal.time = date
        startDayCal.set(Calendar.HOUR_OF_DAY, 1)
        startDayCal.set(Calendar.MINUTE, 0)
        startDayCal.set(Calendar.SECOND, 0)
        startDayCal.set(Calendar.MILLISECOND, 0)

        //end day
        val endDayCal = Calendar.getInstance()
        endDayCal.time = date
        endDayCal.set(Calendar.HOUR_OF_DAY, 23)
        endDayCal.set(Calendar.MINUTE, 59)
        endDayCal.set(Calendar.SECOND, 59)
        endDayCal.set(Calendar.MILLISECOND, 999)

        onReceived(startDayCal.time, endDayCal.time)
    }

    protected fun setStartTimeAndEndTimeForDates(
        startDate: Date,
        endDate: Date,
        onReceived: (startDate: Date, endDate: Date) -> Unit
    ) {
        //start day
        val startDayCal = Calendar.getInstance()
        startDayCal.time = startDate
        startDayCal.set(Calendar.HOUR_OF_DAY, 1)
        startDayCal.set(Calendar.MINUTE, 0)
        startDayCal.set(Calendar.SECOND, 0)
        startDayCal.set(Calendar.MILLISECOND, 0)

        //end day
        val endDayCal = Calendar.getInstance()
        endDayCal.time = endDate
        endDayCal.set(Calendar.HOUR_OF_DAY, 23)
        endDayCal.set(Calendar.MINUTE, 59)
        endDayCal.set(Calendar.SECOND, 59)
        endDayCal.set(Calendar.MILLISECOND, 999)

        onReceived(startDayCal.time, endDayCal.time)
    }

    protected fun getThisWeekDateRange(onReceived: (startDate: Date, endDate: Date) -> Unit) {
        val date = Date()

        //start day
        val startDayCal = Calendar.getInstance()
        startDayCal.time = date
        startDayCal.set(
            Calendar.DAY_OF_WEEK,
            startDayCal.getActualMinimum(Calendar.DAY_OF_WEEK)
        )
        startDayCal.set(Calendar.HOUR_OF_DAY, 1)
        startDayCal.set(Calendar.MINUTE, 0)
        startDayCal.set(Calendar.SECOND, 0)
        startDayCal.set(Calendar.MILLISECOND, 0)

        //end day
        val endDayCal = Calendar.getInstance()
        endDayCal.time = date
        endDayCal.set(
            Calendar.DAY_OF_WEEK,
            endDayCal.getActualMaximum(Calendar.DAY_OF_WEEK)
        )
        endDayCal.set(Calendar.HOUR_OF_DAY, 23)
        endDayCal.set(Calendar.MINUTE, 59)
        endDayCal.set(Calendar.SECOND, 59)
        endDayCal.set(Calendar.MILLISECOND, 999)

        onReceived(startDayCal.time, endDayCal.time)
    }

    protected fun getThisMonthDateRange(onReceived: (startDate: Date, endDate: Date) -> Unit) {
        val date = Date()

        //start date of month
        val startOfMonthCal = Calendar.getInstance()
        startOfMonthCal.time = date
        startOfMonthCal.set(
            Calendar.DAY_OF_MONTH,
            startOfMonthCal.getActualMinimum(Calendar.DAY_OF_MONTH)
        )
        startOfMonthCal.set(Calendar.HOUR_OF_DAY, 0)
        startOfMonthCal.set(Calendar.MINUTE, 0)
        startOfMonthCal.set(Calendar.SECOND, 0)
        startOfMonthCal.set(Calendar.MILLISECOND, 0)

        //end date of month
        val endOfMonthCal = Calendar.getInstance()
        endOfMonthCal.time = date
        endOfMonthCal.set(
            Calendar.DAY_OF_MONTH,
            endOfMonthCal.getActualMaximum(Calendar.DAY_OF_MONTH)
        )
        endOfMonthCal.set(Calendar.HOUR_OF_DAY, 23)
        endOfMonthCal.set(Calendar.MINUTE, 59)
        endOfMonthCal.set(Calendar.SECOND, 59)
        endOfMonthCal.set(Calendar.MILLISECOND, 999)

        onReceived(startOfMonthCal.time, endOfMonthCal.time)
    }

    protected fun getLastMonthDateRange(onReceived: (startDate: Date, endDate: Date) -> Unit) {
        val startOfMonthCal = Calendar.getInstance()
        startOfMonthCal.add(Calendar.MONTH, -1)
        startOfMonthCal[Calendar.DATE] = 1
        startOfMonthCal[Calendar.HOUR_OF_DAY] = 0
        startOfMonthCal[Calendar.MINUTE] = 0
        startOfMonthCal[Calendar.SECOND] = 0
        startOfMonthCal[Calendar.MILLISECOND] = 0

        val endOfMonthCal = Calendar.getInstance()
        endOfMonthCal.add(Calendar.MONTH, -1)
        endOfMonthCal.set(
            Calendar.DAY_OF_MONTH,
            endOfMonthCal.getActualMaximum(Calendar.DAY_OF_MONTH)
        )
        endOfMonthCal[Calendar.HOUR_OF_DAY] = 23
        endOfMonthCal[Calendar.MINUTE] = 59
        endOfMonthCal[Calendar.SECOND] = 59
        endOfMonthCal[Calendar.MILLISECOND] = 999

        onReceived(startOfMonthCal.time, endOfMonthCal.time)
    }

    protected fun getThisYearDateRange(onReceived: (startDate: Date, endDate: Date) -> Unit) {
        val date = Date()

        //start date of month
        val startOfMonthCal = Calendar.getInstance()
        startOfMonthCal.time = date
        startOfMonthCal.set(
            Calendar.MONTH,
            startOfMonthCal.getActualMinimum(Calendar.MONTH)
        )
        startOfMonthCal.set(
            Calendar.DAY_OF_MONTH,
            startOfMonthCal.getActualMinimum(Calendar.DAY_OF_MONTH)
        )
        startOfMonthCal.set(Calendar.HOUR_OF_DAY, 0)
        startOfMonthCal.set(Calendar.MINUTE, 0)
        startOfMonthCal.set(Calendar.SECOND, 0)
        startOfMonthCal.set(Calendar.MILLISECOND, 0)

        //end date of month
        val endOfMonthCal = Calendar.getInstance()
        endOfMonthCal.time = date
        endOfMonthCal.set(
            Calendar.MONTH,
            endOfMonthCal.getActualMaximum(Calendar.MONTH)
        )
        endOfMonthCal.set(
            Calendar.DAY_OF_MONTH,
            endOfMonthCal.getActualMaximum(Calendar.DAY_OF_MONTH)
        )
        endOfMonthCal.set(Calendar.HOUR_OF_DAY, 23)
        endOfMonthCal.set(Calendar.MINUTE, 59)
        endOfMonthCal.set(Calendar.SECOND, 59)
        endOfMonthCal.set(Calendar.MILLISECOND, 999)

        onReceived(startOfMonthCal.time, endOfMonthCal.time)
    }

    protected fun isExistsInArray(list: ArrayList<String>, value:String): Boolean {
        list.forEach {
            if (value == it){
                return true
            }
        }

        return false
    }

    private fun log(msg: String) {
        Log.i("BaseViewModal", msg)
    }

}