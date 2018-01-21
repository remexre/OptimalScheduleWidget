package xyz.remexre.optimalschedulewidget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews

/**
 * Implementation of App Widget functionality.
 */
class Widget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    companion object {

        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager,
                                     appWidgetId: Int) {
            var views: RemoteViews
            try {
                val tmp = getNextEvent(context)

                views = RemoteViews(context.packageName, R.layout.widget)
                views.setTextViewText(R.id.time_until, tmp.first)
                views.setTextViewText(R.id.location_of, tmp.second)
            } catch(e: NoEventException) {
                // TODO
                views = throw RuntimeException("TODO")
            } catch(e: SecurityException) {
                // TODO
                views = throw RuntimeException("TODO")
            }

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

