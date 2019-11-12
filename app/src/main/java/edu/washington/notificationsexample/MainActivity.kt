package edu.washington.notificationsexample

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

import kotlinx.android.synthetic.main.activity_main.*
import android.content.Context.NOTIFICATION_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.app.ComponentActivity.ExtraData
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.graphics.createBitmap

enum class NotificationID {
    BASIC,
    AIRPLANE
}

class MainActivity : AppCompatActivity() {
    var totalNotifications = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

    fun displayNotificationSimple(v: View) {
        val builder = buildBasicNotification()
        showBasicNotification(builder)
    }

    fun displayNotificationExpanded(v: View) {

        // Intent for when the notification is tapped
        val intent = Intent(this, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        // Build and show a notification with extra content
        val notification = NotificationCompat.Builder(this, "expanded")
            .setSmallIcon(android.R.drawable.ic_menu_add)
            .setContentTitle("Expanded Notification Title")
            .setContentText("Content Text")
            .setContentIntent(pendingIntent)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.picture_icon))
            .setStyle(NotificationCompat.BigTextStyle().bigText("hello hello hello hello"))

        showBasicNotification(notification)
    }

    fun displayNotificationWithActions(v: View) {
        // Build and show a notification with action buttons that can
        // launch MainActivity and SecondActivity

        val intent = Intent(this, SecondActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val notification = NotificationCompat.Builder(this, "actions")
            .setSmallIcon(android.R.drawable.ic_menu_add)
            .setContentTitle("Action Notification Title")
            .setContentText("Content Text")
            .addAction(android.R.drawable.ic_menu_add, "Button", pendingIntent )
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.picture_icon))
            .setStyle(NotificationCompat.BigTextStyle().bigText("hi hi hi hi"))

        // Add an action button that can reset the notification count

        showBasicNotification(notification)

    }

    private fun addNotificationsForAirplaneMode() {

        // Create a standalone BroadcastReceiver that can listen for changes to airplane mode
        // Instantiate and register your BroadcastReceiver here
        // Action: "android.intent.action.AIRPLANE_MODE"
    }

    private fun buildBasicNotification(): NotificationCompat.Builder {
        totalNotifications++

        var builder = NotificationCompat.Builder(this, "basic")
            .setSmallIcon(android.R.drawable.ic_menu_add)
            .setContentTitle("Basic Notification Title")
            .setContentText(totalNotifications.toString())
            .setAutoCancel(true)

        return builder
    }

    private fun showBasicNotification(builder: NotificationCompat.Builder) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NotificationID.BASIC.ordinal, builder.build())
    }
}
