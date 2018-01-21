package xyz.remexre.optimalschedulewidget

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
