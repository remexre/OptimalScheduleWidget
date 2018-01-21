package xyz.remexre.optimalschedulewidget

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.util.*

class MainActivity : AppCompatActivity() {
    private val timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.onUpdate()

        val nextMinute = GregorianCalendar().apply {
            set(Calendar.SECOND, 0)
            add(Calendar.MINUTE, 0)
        }

        this.timer.scheduleAtFixedRate(UpdateTask(this), nextMinute.time,
                60 * 1000)
    }

    class UpdateTask(private val mainActivity: MainActivity): TimerTask() {
        override fun run() {
            mainActivity.onUpdate()
        }
    }

    private fun onUpdate() {
        runOnUiThread {
            setContentView(R.layout.widget)
            val timeUntil = findViewById<TextView>(R.id.time_until)
            val locationOf = findViewById<TextView>(R.id.location_of)

            try {
                val tmp = getNextEvent(this)

                timeUntil.text = tmp.first
                locationOf.text = tmp.second
            } catch(e: NoEventException) {
                // TODO
                throw RuntimeException("TODO")
            } catch(e: SecurityException) {
                // TODO
                throw RuntimeException("TODO")
            }
        }
    }
}
