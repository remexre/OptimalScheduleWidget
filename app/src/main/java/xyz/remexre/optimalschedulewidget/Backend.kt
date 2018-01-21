package xyz.remexre.optimalschedulewidget

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.provider.CalendarContract
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.util.Log
import xyz.remexre.optimalschedulewidget.NoEventException

@SuppressLint("MissingPermission")
fun getNextEvent(context: Context): Pair<String, String> {
    val cr = context.contentResolver

    val projection = arrayOf(CalendarContract.Events.DTSTART,
        CalendarContract.Events.EVENT_LOCATION)

    val now = System.currentTimeMillis()
    val cur = cr.query(CalendarContract.Events.CONTENT_URI, projection,
            "${CalendarContract.Events.DTSTART} > $now",
            null, CalendarContract.Events.DTSTART)
    if(cur.moveToNext()) {
        val start = cur.getLong(0)
        val location = cur.getString(1)
        cur.close()
        return Pair(formatTimeDiff(start - now), location)
    }
    cur.close()
    throw NoEventException()
}

fun checkPermissions(context: Context): Boolean {
    if(ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CALENDAR)
            != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.READ_CALENDAR), 0)
        return true
    }
    return true
}

fun formatTimeDiff(msTotal: Long): String {
    val sTotal = msTotal / 1000
    val mTotal = sTotal / 60
    if(mTotal == 0L) {
        return "Now!"
    }
    val h = mTotal / 60
    val m = mTotal % 60

    val out = arrayListOf<String>()
    if(h == 1L) {
        out.add("1 hour")
    } else if(h > 0) {
        out.add("$h hours")
    }
    if(m == 1L) {
        out.add("1 minute")
    } else if(m > 0) {
        out.add("$m minutes")
    }
    return TextUtils.join(", ", out)
}